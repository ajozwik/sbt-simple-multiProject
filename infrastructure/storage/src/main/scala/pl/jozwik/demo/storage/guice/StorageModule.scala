package pl.jozwik.demo.storage.guice

import net.codingwell.scalaguice.ScalaModule
import pl.jozwik.demo.domain.PostRepository
import pl.jozwik.demo.storage.MemoryPostRepository

class StorageModule extends ScalaModule {
  override def configure(): Unit = {
    bind[PostRepository].to[MemoryPostRepository]
  }
}
