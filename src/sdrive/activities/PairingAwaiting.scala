package sdrive.activities

import org.scaloid.common.{STextView, SVerticalLayout, SActivity}


class PairingAwaiting extends SActivity {

  onCreate {
    contentView = new SVerticalLayout {
      // TODO: send create group request

      STextView("Awaiting")
    }
  }

}
