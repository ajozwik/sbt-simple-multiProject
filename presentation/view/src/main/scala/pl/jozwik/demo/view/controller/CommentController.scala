package pl.jozwik.demo.view.controller

import io.circe.generic.auto._
import javax.inject.{ Inject, Singleton }
import pl.jozwik.demo.domain.{ Post, PostRepository }
import play.api.mvc.{ ControllerComponents, EssentialAction }

@Singleton
class CommentController @Inject() (postRepository: PostRepository, components: ControllerComponents)
  extends MyAbstractController(components) {

  def posts(user: String): EssentialAction = Action {
    val posts = postRepository.readPosts(user)
    asJson(posts)
  }

  def writePost(): EssentialAction = handlePost(circe.json[Post]) {
    r =>
      postRepository.savePost(r.body)
      Ok("")
  }

}
