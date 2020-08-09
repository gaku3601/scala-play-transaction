package domain.service

import domain.entity.user.{Age, Name, User}
import infrastructure.repository.UserRepository
import javax.inject.Inject
import utils.fujitask.scalikejdbc._

import scala.concurrent.{ExecutionContext, Future}

class UserService @Inject()(userRepository: UserRepository)(implicit ec: ExecutionContext) {
  def create(name: String, age: Int): Future[User] =
    userRepository.create(User(Name(name), Age(age))).run()

  def read(id: Long): Future[Option[User]] =
    userRepository.read(id).run()

  def readAll: Future[List[User]] =
    userRepository.readAll.run()

  def update(user: User): Future[Unit] = {
    (for {
      _ <- userRepository.update(user)
      _ = throw new Exception("aaa") // 例としてのexception 例外が発生するとロールバックが行われる
    } yield ()).run()
  }

  def delete(id: Long): Future[Unit] =
    userRepository.delete(id).run()
}

