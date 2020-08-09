package controllers.user

import domain.entity.user.{Age, Name, User}
import play.api.libs.json.{Json, Writes}

object UserResponse {
  implicit lazy val nameWrites: Writes[Name] = (n: Name) => Json.toJson(n.value)
  implicit lazy val userWrites: Writes[User] = Json.writes[User]
  implicit lazy val ageWrites: Writes[Age] = (n: Age) => Json.toJson(n.value)
}
