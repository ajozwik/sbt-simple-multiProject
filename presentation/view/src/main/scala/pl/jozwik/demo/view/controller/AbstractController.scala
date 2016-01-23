package pl.jozwik.demo.view.controller

import com.typesafe.scalalogging.StrictLogging
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import play.api.mvc.{Action, BodyParsers, Controller}

trait AbstractController extends Controller with StrictLogging {

  protected def handlePost[K](f: (K => Unit))(implicit rds: Reads[K]) = Action(BodyParsers.parse.json) {
    r =>
      val result = r.body.validate[K](rds) match {
        case JsError(errors) =>
          BadRequest(Json.toJson(errors.mkString("")))
        case JsSuccess(k, _) =>
          f(k)
          Ok("OK")
      }
      result.as(JSON)
  }
}
