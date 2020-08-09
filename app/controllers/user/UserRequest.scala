package controllers.user

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads}

object UserRequest {

  case class CreateRequest(name: String, age: Int)

  object CreateRequest {
    implicit val reads: Reads[CreateRequest] = (
      (JsPath \ "name").read[String] and
        (JsPath \ "age").read[Int]
      ) (CreateRequest.apply _)
  }

}

