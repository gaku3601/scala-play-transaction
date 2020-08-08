package domain.entity.user

import play.api.libs.json.{Json, Writes}

case class Age(value: Int) {
  require(value >= 18, "18歳以上でないと本サービスはご利用できません")
  require(value <= 60, "60歳までで年齢を入力してください")
}

object Age {
  implicit lazy val jsonWrites: Writes[Age] = (n: Age) => Json.toJson(n.value)
}
