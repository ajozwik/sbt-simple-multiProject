package pl.jozwik.demo.view.controller

import com.typesafe.scalalogging.StrictLogging
import io.circe.Encoder
import play.api.libs.circe.Circe
import play.api.mvc._
import io.circe.syntax._

import scala.util.{ Failure, Success, Try }

abstract class MyAbstractController(components: ControllerComponents) extends AbstractController(components) with Circe
  with StrictLogging {

  protected def handlePost[K](parser: BodyParser[K])(f: Request[K] => Result) = Action(parser) {
    r =>
      val result = Try(f(r)) match {
        case Success(success) =>
          success
        case Failure(exception) =>
          logger.error(s"$exception", exception)
          BadRequest(s"$exception")
      }
      result.as(JSON)
  }

  def asJson[T](a: T)(implicit tjs: Encoder[T]): Result = Try {
    Ok(a.asJson).as(JSON)
  } match {
    case Success(s) =>
      s
    case Failure(th) =>
      logger.error(s"$a", th)
      BadRequest(th.getMessage)
  }
}
