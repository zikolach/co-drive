package sdrive.activities

import android.app.ExpandableListActivity
import android.graphics.Color
import android.view.{View, Window, Gravity}
import org.scaloid.common.{SListView, SActivity, STextView, SVerticalLayout}
import sdrive.activities.R.drawable
import org.scaloid.common._

import scala.language.postfixOps

class PairingJoin extends SActivity {

  var groups: List[Group] = Nil

  onCreate {

    groups = DataStorage.findGroups(getIntent.getLongArrayExtra("groups"))
    val username = getIntent.getStringExtra("username")
    requestWindowFeature(Window.FEATURE_NO_TITLE)

    contentView = new SVerticalLayout {
      style {
        case label: STextView => label.textSize(24 sp)
          .gravity(Gravity.CENTER_HORIZONTAL)
          .textColor(Color.WHITE)
        //        case edit: SEditText => edit.singleLine(p = true).textSize(32 sp).gravity(Gravity.CENTER)
        case button: SButton => button.textSize(24 sp).backgroundColor(Color.rgb(255, 140, 0))
      }
      groups.foreach(group => {
        val button = SButton(s"${group.id}${group.master.map(m => s" (${m.name})").getOrElse("")}")
        button.marginTop(10 dip)
        button.onClick((v: View) => {
          val groupId = v.asInstanceOf[SButton].text.toString.takeWhile(!_.isSpaceChar).toLong
          DataStorage.joinGroup(groupId, username)
          val intent = SIntent[Driving]
          intent.put(groupId)
          intent.put(username)
          ctx.startActivity(intent)
        })
      })
    } padding (20 dip) gravity Gravity.CENTER backgroundDrawable drawable.background_gray
  }

}
