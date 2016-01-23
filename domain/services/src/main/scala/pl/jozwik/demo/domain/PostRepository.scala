package pl.jozwik.demo.domain

trait PostRepository {
  def savePost(post: Post): Unit

  def readPosts(user: String): Seq[Post]
}
