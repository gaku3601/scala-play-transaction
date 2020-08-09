package infrastructure.repository

import domain.entity.user.{Age, Name, User}
import scalikejdbc._
import utils.fujitask.scalikejdbc._
import utils.fujitask.{ReadTransaction, ReadWriteTransaction, Task}

class UserRepository {

  def create(user: User): Task[ReadWriteTransaction, User] =
  // TODO: valueなんとかできないか。
    ask.map { implicit session =>
      val sql = sql"""insert into users (name, age) values (${user.name.value}, ${user.age.value})"""
      val id = sql.updateAndReturnGeneratedKey.apply()
      User(id, user.name, user.age)
    }

  def read(id: Long): Task[ReadTransaction, Option[User]] =
    ask.map { implicit session =>
      val sql = sql"""select * from users where id = $id"""
      sql.map(rs => User(rs.long("id"), Name(rs.string("name")), Age(rs.int("age")))).single.apply()
    }

  def readAll: Task[ReadTransaction, List[User]] =
    ask.map { implicit session =>
      val sql = sql"""select * from users"""
      sql.map(rs => User(rs.long("id"), Name(rs.string("name")), Age(rs.int("age")))).list.apply()
    }

  def update(user: User): Task[ReadWriteTransaction, Unit] =
    ask.map { implicit session =>
      val sql = sql"""update users set name = ${user.name.value}, age = ${user.age.value} where id = ${user.id}"""
      sql.update.apply()
    }

  def delete(id: Long): Task[ReadWriteTransaction, Unit] =
    ask.map { implicit session =>
      val sql = sql"""delete users where id = $id"""
      sql.update.apply()
    }

}
