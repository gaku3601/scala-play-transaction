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

  case class UpdateRequest(id: Int, name: String, age: Int)

  object UpdateRequest {
    implicit val reads: Reads[UpdateRequest] = (
      (JsPath \ "id").read[Int] and
        (JsPath \ "name").read[String] and
        (JsPath \ "age").read[Int]
      ) (UpdateRequest.apply _)
  }

}

