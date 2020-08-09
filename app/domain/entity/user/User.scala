package domain.entity.user

case class User(id: Long, name: Name, age: Age)

object User {
  def apply(name: Name, age: Age): User =
    User(0, name, age)

  def apply(name: String, age: Int): User =
    User(0, Name(name), Age(age))
}