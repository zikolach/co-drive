package sdrive.activities

import scala.concurrent.Future
import scala.util.Random
import scala.concurrent.ExecutionContext.Implicits.global

object DataStorage {

  private var ids = 0

  def nextId: Long = {
    ids = ids + 1
    ids
  }

  // mock data
  val members = List(Member(11, "ABC"), Member(22, "CDE"))
  val groups = List(Group(id = 123), Group(234))
  groups.head.master = members.headOption
  groups.head.members = members

  def fetchGroupsNearby(): List[Group] = {
    //    for (s <- Service.postJSON("http://validate.jsontest.com/", new JSONObject("{\n   \"one\": \"two\",\n   \"key\": \"value\"\n}")))
    //      toast(s.toString(2))
    groups
  }

  def findGroups(ids: Array[Long]): scala.List[Group] = {
    groups //.filter(g => ids.contains(g.id))
  }

  def joinGroup(id: Long, username: String): Option[Group] = {
    val groupOpt = groups.find(g => g.id == id)
    groupOpt.foreach(g => {
      g.members ::= Member(nextId, username)
    })
    groupOpt
  }

  def findGroup(id: Long): Option[Group] = {
    groups.find(_.id == id)
  }

  def updatePositions(): Unit = {

    for {
      group <- groups
      master <- group.master
    } {
      master.locations match {
        case Nil => master.locations = List(Position(10, 10))
        case pos :: _ => master.locations ::= Position(pos.latitude + Random.nextDouble() - 0.5, pos.longitude + +Random.nextDouble() - 0.5)
      }
    }

    for {
      group <- groups
      master <- group.master
      member <- group.members
      if member.id != master.id
    } {
      member.locations match {
        case Nil => member.locations = List(Position(10, 10))
        case pos :: _ =>
          val x = Math.signum(master.locations.head.latitude - member.locations.head.latitude)
          val y = Math.signum(master.locations.head.longitude - member.locations.head.longitude)
          member.locations ::= Position(pos.latitude + Random.nextDouble() * x, pos.longitude + Random.nextDouble() * y)
      }
    }

    //    groups.foreach(group => {
    //      group.members.foreach(member => {
    //        member.locations match {
    //          case Nil => member.locations = List(Position(10, 10))
    //          case pos :: _ => member.locations ::= Position(pos.latitude + Random.nextDouble() - 0.5, pos.longitude +  + Random.nextDouble() - 0.5)
    //        }
    //      })
    //    })
  }

  //  Future {
  //    while(true) {
  //      updatePositions()
  //      Thread.sleep(1000)
  //    }
  //  }

  def distFrom(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double = {
    val earthRadius = 6371000;
    //meters
    val dLat = Math.toRadians(lat2 - lat1)
    val dLng = Math.toRadians(lng2 - lng1)
    val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
        Math.sin(dLng / 2) * Math.sin(dLng / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    earthRadius * c
  }

}
