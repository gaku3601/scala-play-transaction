package infrastructure.queryservice

import scalikejdbc._
import utils.fujitask.scalikejdbc.ask
import utils.fujitask.{ReadTransaction, Task}

case class ReadAllUser(id: Long, name: String, age: Int, fullCount: Int)

class UserQueryService {
  def readAll(page: Int): Task[ReadTransaction, List[ReadAllUser]] =
    ask.map { implicit session =>
      val sql = sql"""select *, count(id) over() as full_count from users order by id desc offset ${(page - 1) * 10} limit 10 """
      sql.map(rs => ReadAllUser(rs.long("id"), rs.string("name"), rs.int("age"), rs.int("full_count"))).list.apply()
    }
}
