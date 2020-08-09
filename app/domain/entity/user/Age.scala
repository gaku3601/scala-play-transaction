package domain.entity.user

case class Age(value: Int) {
  require(value >= 18, "18歳以上でないと本サービスはご利用できません")
  require(value <= 60, "60歳までで年齢を入力してください")
}