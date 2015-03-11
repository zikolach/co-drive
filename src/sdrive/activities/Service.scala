package sdrive.activities

import java.io.DataOutputStream
import java.net.{HttpURLConnection, URL}

import org.json.JSONObject

import scala.util.{Failure, Try, Success}

object Service {

  def getJSON(url: String): Option[JSONObject] = {
    Try(new URL(url).openConnection().getInputStream) match {
      case Success(stream) =>
        val s = scala.io.Source.fromInputStream(stream).mkString
        Some(new JSONObject(s))
      case Failure(exception) => None
    }
  }

  def postJSON(url: String, obj: JSONObject): Option[JSONObject] = {
    try {
      val s = obj.toString(2)
      val c = new URL(url).openConnection().asInstanceOf[HttpURLConnection]
      c.setDoOutput( true )
      c.setDoInput ( true )
      c.setInstanceFollowRedirects( false )
      c.setRequestMethod( "POST" )
      c.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded")
      c.setRequestProperty( "charset", "utf-8")
      c.setRequestProperty( "Content-Length", Integer.toString( s.length ))
      c.setUseCaches( false )
      val wr = new DataOutputStream( c.getOutputStream)
      wr.write(s.getBytes)
      val output = scala.io.Source.fromInputStream(c.getInputStream).mkString
      Some(new JSONObject(s))
    } catch {
      case _: Throwable => None
    }
  }

}
