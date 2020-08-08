package utils.fujitask.scalikejdbc

import scalikejdbc._
import utils.fujitask.{ReadTransaction, ReadWriteTransaction}

abstract class ScalikeJDBCTransaction(val session: DBSession)

class ScalikeJDBCReadTransaction(session: DBSession) extends ScalikeJDBCTransaction(session) with ReadTransaction

class ScalikeJDBCReadWriteTransaction(session: DBSession) extends ScalikeJDBCTransaction(session) with ReadWriteTransaction
