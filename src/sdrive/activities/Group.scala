package sdrive.activities

case class Group(id: Long) {
  var master: Option[Member] = None
  var members: List[Member] = Nil
}
