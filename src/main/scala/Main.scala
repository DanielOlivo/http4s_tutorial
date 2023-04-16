import scala.concurrent.duration._
import scala.util.control.Exception._

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

  case class Count(v: Int)

  
  class Counter(val ref: Ref[IO,Int]) {
      def update = ref.modify(x => (x + 1, x + 1))

      val app = HttpRoutes.of[IO]{
          case GET -> Root / "counter" => for{
              newValue <- update
              response <- Ok(Count(newValue).asJson)
          }yield response 
      }.orNotFound
  }

  object Number {
      def unapply(src : String) : Option[Int] = allCatch.opt(src.toInt)
  }

  val payload = HttpRoutes.of[IO] {
      case GET -> Root / Number(total) / Number(chunkSize) / Number(interval) => 
          Ok(Stream.awakeEvery[IO](interval.seconds).take(total / chunkSize).map(_ => "x" * chunkSize)) 
      case _ => BadRequest()
  }.orNotFound

  val run = IO.println("Hey")

}