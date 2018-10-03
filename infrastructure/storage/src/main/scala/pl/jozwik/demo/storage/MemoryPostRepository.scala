package pl.jozwik.demo.storage

import pl.jozwik.demo.domain.{ Post, PostRepository }
import javax.inject.Singleton

@Singleton
class MemoryPostRepository extends PostRepository {

  private var repository = Map.empty[String, Seq[Post]]

  def savePost(post: Post): Unit = {
    val posts = readPosts(post.user)
    repository = repository + (post.user -> (post +: posts))
  }

  def readPosts(user: String): Seq[Post] =
    repository.getOrElse(user, Seq.empty)
}
