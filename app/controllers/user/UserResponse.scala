package controllers.user

import domain.entity.user.User
import infrastructure.queryservice._
import play.api.libs.json.{Json, Writes}

object UserResponse {
  // 明示的にJson変換可能
  implicit lazy val userWrites: Writes[User] = (u: User) => {
    Json.obj("id" -> u.id, "name" -> u.name.value, "age" -> u.age.value)
  }
  // 自動的に変換してくれる(VOとか使って場合はVO内でもWrite定義が必要)
  implicit lazy val readAllUserWrites: Writes[ReadAllUser] = Json.writes[ReadAllUser]
}
