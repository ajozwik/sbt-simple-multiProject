package pl.jozwik.demo.view.filters

import akka.stream.Materializer
import javax.inject.{ Inject, Singleton }
import play.api.Environment
import play.api.http.HttpFilters
import play.api.mvc.{ Filter, RequestHeader, Result }

import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class AccessLogFilter @Inject() (implicit val mat: Materializer, ex: ExecutionContext) extends Filter {
  def apply(nextFilter: RequestHeader => Future[Result])(rh: RequestHeader): Future[Result] = {
    val start = System.currentTimeMillis
    nextFilter(rh).map {
      result =>
        val requestTime = System.currentTimeMillis - start
        result.withHeaders("Request-Time" -> s"$requestTime ms")
    }
  }
}

class Filters @Inject() (
    logging: AccessLogFilter,
    environment: Environment) extends HttpFilters {
  val filters: Seq[Filter] = environment.mode match {
    case _ =>
      Seq(logging)
  }
}
