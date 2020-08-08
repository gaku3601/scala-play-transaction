package infrastructure.repository

import domain.entity.User
import scalikejdbc._
import utils.fujitask.scalikejdbc._
import utils.fujitask.{ReadTransaction, ReadWriteTransaction, Task}

class UserRepository {

  def create(name: String, age: Int): Task[ReadWriteTransaction, User] =
    ask.map { implicit session =>
      val sql = sql"""insert into users (name, age) values ($name, $age)"""
      val id = sql.updateAndReturnGeneratedKey.apply()
      User(id, name, age)
    }

  def read(id: Long): Task[ReadTransaction, Option[User]] =
    ask.map { implicit session =>
      val sql = sql"""select * from users where id = $id"""
      sql.map(rs => User(rs.long("id"), rs.string("name"), rs.int("age"))).single.apply()
    }

  def readAll: Task[ReadTransaction, List[User]] =
    ask.map { implicit session =>
      val sql = sql"""select * from users"""
      sql.map(rs => User(rs.long("id"), rs.string("name"), rs.int("age"))).list.apply()
    }

  def update(user: User): Task[ReadWriteTransaction, Unit] =
    ask.map { implicit session =>
      val sql = sql"""update users set name = ${user.name}, age = ${user.age} where id = ${user.id}"""
      sql.update.apply()
    }

  def delete(id: Long): Task[ReadWriteTransaction, Unit] =
    ask.map { implicit session =>
      val sql = sql"""delete users where id = $id"""
      sql.update.apply()
    }

}
