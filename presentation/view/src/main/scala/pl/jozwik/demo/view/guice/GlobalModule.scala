package pl.jozwik.demo.view.guice

import com.typesafe.scalalogging.StrictLogging
import net.codingwell.scalaguice.ScalaModule
import pl.jozwik.demo.storage.guice.StorageModule
import play.api.{Configuration, Environment}

class GlobalModule(
    environment: Environment,
    configuration: Configuration
) extends ScalaModule with StrictLogging {
  override def configure(): Unit = {
    install(new StorageModule)
    ()
  }
}
