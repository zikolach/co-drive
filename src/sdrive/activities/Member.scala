package sdrive.activities

case class Member(id: Long, name: String) {
  var locations: List[Position] = Nil
}
