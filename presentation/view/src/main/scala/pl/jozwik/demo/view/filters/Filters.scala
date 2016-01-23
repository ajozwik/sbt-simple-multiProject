package pl.jozwik.demo.view.filters

import javax.inject.Inject

import play.api.http.HttpFilters
import play.api.mvc.{Result, RequestHeader, Filter}
import play.filters.gzip.GzipFilter
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future

class AccessLogFilter extends Filter {
  def apply(nextFilter: (RequestHeader) => Future[Result])(rh: RequestHeader): Future[Result] = {
    val start = System.currentTimeMillis
    nextFilter(rh).map {
      result =>
        val requestTime = System.currentTimeMillis - start
        result.withHeaders("Request-Time" -> s"$requestTime ms")
    }
  }
}

class Filters @Inject() (gzip: GzipFilter, log: AccessLogFilter) extends HttpFilters {
  val filters = Seq(gzip, log)
}
