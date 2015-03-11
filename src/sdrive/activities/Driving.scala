package sdrive.activities

import java.util.Date

import android.content.Context
import android.graphics.drawable.GradientDrawable.Orientation
import android.location.{LocationListener, Location, Criteria, LocationManager}
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.view.{Window, Gravity}
import android.widget.{ImageView, LinearLayout}
import org.scaloid.common.{SIntent, STextView, SVerticalLayout, SActivity}
import sdrive.activities.R.drawable
import scala.collection.JavaConversions._
import org.scaloid.common._

import scala.language.postfixOps

class Driving extends SActivity with LocationListener {

  var usernameOpt: Option[String] = None
  var groupOpt: Option[Group] = None


  onCreate {

    val direction = new ImageView(ctx)
    direction.adjustViewBounds(true)
    direction.setImageResource(R.drawable.arrow_up)

    requestWindowFeature(Window.FEATURE_NO_TITLE)

    getIntent.getStringExtra("username") match {
      case s: String if s.length > 0 =>
        usernameOpt = Some(s)
      case _ =>
    }

    getIntent.getLongExtra("groupId", 0) match {
      case groupId: Long if groupId > 0 =>
        groupOpt = DataStorage.findGroup(groupId)
      case 0 =>
    }

    (usernameOpt, groupOpt) match {
      case (None, _) =>
        val intent = SIntent[FirstStart]
        ctx.startActivity(intent)
      case (Some(username), None) =>
        val intent = SIntent[PairingMenu].putExtra("username", username.toString)
        ctx.startActivity(intent)
      case (_, group) =>
    }

    val locationManager: LocationManager = ctx.getSystemService(Context.LOCATION_SERVICE).asInstanceOf[LocationManager]
    val criteria = new Criteria()
    criteria.setAltitudeRequired(true)
    val provider = locationManager.getBestProvider(criteria, false)

    val locationOpt: Option[Location] = Option(locationManager.getLastKnownLocation(provider))

    //    if (locationOpt.isDefined) {
    info("set location listener")
//    locationManager.requestLocationUpdates(provider, 1000, 1, this)
    //    }

    contentView = new SLinearLayout {
      usernameOpt.map(name => {

        this += new SRelativeLayout {
          this += direction
          //          STextView(s"Driving $name")
        }.<<.Weight(0.5f).>>

        this += new SVerticalLayout {
          style {
            case t: STextView =>
              t.textSize(24 dip).gravity(Gravity.CENTER_HORIZONTAL)
          }
          STextView(s"Driving $name")
//          locationOpt.foreach(location => {
//            STextView(s"Location ${location.toString}")
//          })

//          val position = STextView("")
          val angle = STextView("")
          val distance = STextView("")

          SButton("Update").onClick {
            DataStorage.updatePositions()
            groupOpt.foreach(group => {
              val meOpt = group.members.find(member => {
                usernameOpt.contains(member.name)
              })

//              meOpt.foreach(me => {
//                position.text = me.locations.head.toString
//              })

              val angleDistanceOpt: Option[(Double, Double)] = for {
                group <- groupOpt
                me <- group.members.find(m => usernameOpt.contains(m.name))
                master <- group.master
              } yield {
                val dx = me.locations.head.latitude - master.locations.head.latitude
                val dy = me.locations.head.longitude - master.locations.head.longitude
                val a: Double = if (dx != 0) Math.atan2(dy, dx) else 0
                //                val d = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))
                val d = DataStorage.distFrom(me.locations.head.latitude, me.locations.head.longitude,
                  master.locations.head.latitude, master.locations.head.longitude)
                val n: Double = Math.toDegrees(a + Math.PI * 2) % 360
                (n, d)
              }

              angleDistanceOpt.foreach {
                case (n, d) =>
                  angle.text = s"Angle: ${n.round}"

                  if (n < 45) {
                    direction.setImageResource(R.drawable.arrow_right)
                  } else if (n < 135) {
                    direction.setImageResource(R.drawable.arrow_up)
                  } else if (n < 225) {
                    direction.setImageResource(R.drawable.arrow_left)
                  } else if (n < 315) {
                    direction.setImageResource(R.drawable.arrow_down)
                  } else {
                    direction.setImageResource(R.drawable.arrow_right)
                  }

                  distance.text = s"Distance ${d.round}m"

              }


            })
            invalidate()
          }

          groupOpt.foreach(g => {
            STextView(s"GROUP ${g.id}")
          })
        }.<<.Weight(0.5f).>>


      })
    } padding (20 dip) backgroundDrawable drawable.background_gray orientation LinearLayout.VERTICAL

  }

  @Override
  def onLocationChanged(location: Location): Unit = {
    info(location.toString)
    toast(location.toString)
  }

  override def onStatusChanged(s: String, i: Int, bundle: Bundle): Unit = {
    toast(s)
  }

  override def onProviderEnabled(s: String): Unit = {
    toast(s)
  }

  override def onProviderDisabled(s: String): Unit = {
    toast(s)
  }
}
