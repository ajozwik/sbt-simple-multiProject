package pl.jozwik.demo.view.controller

import javax.inject.{Inject, Singleton}

import pl.jozwik.demo.domain.{Post, PostRepository}
import play.api.libs.json.Json
import play.api.mvc.{Action, EssentialAction}

@Singleton
class CommentController @Inject() (postRepository: PostRepository) extends AbstractController {

  def posts(user: String): EssentialAction = Action {
    val posts = postRepository.readPosts(user)
    val json = Json.toJson(posts)
    Ok(json)
  }

  def writePost(): EssentialAction = handlePost {
    (post: Post) =>
      postRepository.savePost(post)
  }

}
