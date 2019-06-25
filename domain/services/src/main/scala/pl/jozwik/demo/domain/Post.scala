package pl.jozwik.demo.domain

import io.circe.generic.JsonCodec

@JsonCodec
final case class Post(user: String, comment: String)
