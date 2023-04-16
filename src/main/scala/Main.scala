import scala.concurrent.duration._

import cats.effect._
import cats.syntax.all._
import com.comcast.ip4s._
import cats.data.NonEmptyList

import org.http4s._ 
import org.http4s.ember.server._
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.dsl.io._
import org.http4s.CacheDirective.`no-cache`
import org.http4s.circe._

import io.circe._ 
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.literal._
import io.circe.generic.semiauto._

import fs2.{Stream, Pipe}
import fs2._

object CountingNPayload extends IOApp.Simple {

  val run = IO.println("Hey")

}