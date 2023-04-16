import org.scalatest.funsuite.AnyFunSuite

import cats.effect._
import cats.syntax.all._
import cats.effect.unsafe.IORuntime

import io.circe._
import io.circe.literal._
import io.circe.syntax._
import io.circe.generic.auto._

import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.ember.server._
import org.http4s.ember.client._

import CountingNPayload._

class HomeworkTests extends AnyFunSuite {

	implicit val runtime = IORuntime.global
	
	test("counter service test") {
		
		implicit val counterDecoder = jsonOf[IO,Count]

		val resultGetter = {
			val request = Request[IO](Method.GET, uri"/counter")
			for{
				ref <- Ref.of[IO, Int](7)
				counter <- IO(new Counter(ref))
				_ <- counter.app.run(request)
				_ <- counter.app.run(request)
				response <- counter.app.run(request)
				result <- response.as[Count]
			} yield result
		}

		assertResult(10)(resultGetter.unsafeRunSync().v)	
	}
	
	test("payload") {
		val request = Request[IO](Method.GET, uri"/100/10/1")
		val resultGetter = for{
			response <- payload.run(request)
			result <- response.as[String]
		} yield result
		
		assertResult("x" * 100)(resultGetter.unsafeRunSync())
	}
	
	test("payload with bad request") {
		val request = Request[IO](Method.GET, uri"/100/mistake/1")
		val resultGetter = for{
			response <- payload.run(request)
		} yield response
		
		assertResult(400)(resultGetter.unsafeRunSync().status.code)
	}
}