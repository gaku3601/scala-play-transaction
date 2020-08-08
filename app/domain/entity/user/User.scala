package domain.entity.user

import play.api.libs.json.{Json, Writes}

case class User(id: Long, name: Name, age: Age)

object User {
  implicit val writes: Writes[User] = Json.writes[User]

  def apply(name: Name, age: Age): User =
    User(0, name, age)

  def apply(name: String, age: Int): User =
    User(0, Name(name), Age(age))
}
