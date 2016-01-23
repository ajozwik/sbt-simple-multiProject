package pl.jozwik.demo.domain

import play.api.libs.json.Json

object Post {
  implicit val format = Json.format[Post]
}

case class Post(user: String, comment: String)
