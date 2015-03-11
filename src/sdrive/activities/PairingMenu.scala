package sdrive.activities

import java.net.URL

import android.graphics.Color
import android.view.{Window, Gravity}
import org.json.JSONObject
import org.scaloid.common._
import sdrive.activities.R.drawable
import scala.collection.JavaConversions._

import scala.language.postfixOps

class PairingMenu extends SActivity {
  onCreate {

    requestWindowFeature(Window.FEATURE_NO_TITLE)
    val username = getIntent.getStringExtra("username")
    contentView = new SVerticalLayout {
      style {
        case label: STextView => label.textSize(32 sp)
          .gravity(Gravity.CENTER_HORIZONTAL)
        case button: SButton => button.textSize(24 sp)
          .backgroundColor(Color.rgb(255, 140, 0))
      }

      STextView().text(username.toUpperCase)
      SButton("Start").onClick {
        // TODO: send new group request
        for (s <- Service.getJSON("http://ip.jsontest.com/"))
          toast(s.toString(2))
//        val intent = SIntent[PairingAwaiting]
//        ctx.startActivity(intent)
      }
      SButton("Join").onClick {
        for (s <- Service.postJSON("http://validate.jsontest.com/", new JSONObject("{\n   \"one\": \"two\",\n   \"key\": \"value\"\n}")))
          toast(s.toString(2))
        
//        val intent = SIntent[PairingJoin]
//        ctx.startActivity(intent)
        val intent = SIntent[PairingJoin]
        val groups = DataStorage.fetchGroupsNearby().map(_.id).toArray
        intent.put(groups)
        intent.put(username)
        ctx.startActivity(intent)
      } .marginTop(10 dip)
    } padding (20 dip) gravity Gravity.CENTER backgroundDrawable drawable.background_gray

  }
}
