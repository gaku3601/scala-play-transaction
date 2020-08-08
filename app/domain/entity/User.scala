package domain.entity

import play.api.libs.json.{Json, Writes}

case class User(id: Long, name: String, age: Int)

object User {
  implicit val writes: Writes[User] = Json.writes[User]
}
