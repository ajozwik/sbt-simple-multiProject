package pl.jozwik.demo.view

import com.typesafe.scalalogging.StrictLogging
import play.api.GlobalSettings
import play.api.mvc.{Result, RequestHeader}

import scala.concurrent.Future

object Global extends GlobalSettings with StrictLogging {

  override def onError(request: RequestHeader, ex: Throwable): Future[Result] = {
    logger.error("", ex)
    super.onError(request, ex)
  }
}
