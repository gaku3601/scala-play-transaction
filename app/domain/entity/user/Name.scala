package domain.entity.user

case class Name(value: String) {
  require(value.length >= 4, "名前は4文字以上で入力してください")
  require(value.length <= 60, "名前は60文字以下で入力してください")
}