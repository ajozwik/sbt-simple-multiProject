package pl.jozwik.demo

import com.typesafe.scalalogging.StrictLogging
import org.scalatest._
import org.scalatest.prop.Checkers

trait AbstractSpecScalaCheck extends AbstractSpec with Checkers

trait AbstractSpec extends WordSpecLike with Matchers with StrictLogging

