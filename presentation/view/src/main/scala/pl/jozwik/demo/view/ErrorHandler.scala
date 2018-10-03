package pl.jozwik.demo.view

import com.typesafe.scalalogging.StrictLogging
import javax.inject.{ Inject, Singleton }
import play.api.http.HttpErrorHandler
import play.api.mvc._

import scala.concurrent.Future

@Singleton
class ErrorHandler @Inject() extends HttpErrorHandler with StrictLogging {

  def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful {
      val path = request.path
      if (path.endsWith("/")) {
        val p = path.substring(0, path.length - 1)
        Results.Redirect(Call(request.method, p))
      } else {
        val cutSlash = request.uri.substring(1)
        Results.Status(statusCode)(s"$cutSlash $statusCode $message")
      }
    }
  }

  def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Future.successful {
      val path = request.path
      if (path.endsWith("/")) {
        val p = path.substring(0, path.length - 1)
        Results.Redirect(Call(request.method, p))
      } else {
        Results.BadRequest(s"$exception")
      }
    }
  }
}