package domain.service

import domain.entity.user.Name
import infrastructure.repository.UserRepository
import javax.inject.Inject
import utils.fujitask.{ReadTransaction, Task}

import scala.concurrent.ExecutionContext

class UserService @Inject()(userRepository: UserRepository)(implicit ec: ExecutionContext) {
  def checkUserName(name: Name): Task[ReadTransaction, Unit] = {
    userRepository.read(name) map { users =>
      if (users.nonEmpty) {
        throw new Exception("同じユーザ名がすでに登録されています")
      }
    }
  }

  def checkUserName(id: Long, name: Name): Task[ReadTransaction, Unit] = {
    userRepository.read(name) map { users =>
      if (users.exists(user => user.id != id)) {
        throw new Exception("同じユーザ名がすでに登録されています")
      }
    }
  }
}

