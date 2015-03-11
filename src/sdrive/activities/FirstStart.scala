package sdrive.activities

import android.graphics.Color
import android.view.{Gravity, Window}
import org.scaloid.common._
import sdrive.activities.R.drawable

import scala.language.postfixOps

class FirstStart extends SActivity {

  onCreate {
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    contentView = new SVerticalLayout {
      style {
        case label: STextView => label.textSize(24 sp)
          .gravity(Gravity.CENTER_HORIZONTAL)
          .textColor(Color.BLACK)
        case edit: SEditText => edit.singleLine(p = true).textSize(32 sp).gravity(Gravity.CENTER)
        case button: SButton => button.textSize(24 sp).backgroundColor(Color.rgb(255, 140, 0))
      }
      STextView("Enter your name")
      val username = SEditText("")
      SButton("Next").onClick {
        val intent = SIntent[Driving]
          .putExtra("username", username.text.toString)
        ctx.startActivity(intent)
      }
    } padding (20 dip) gravity Gravity.CENTER backgroundDrawable drawable.background
  }

}
