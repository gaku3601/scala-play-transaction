package domain.service

import domain.entity.User
import infrastructure.repository.UserRepository
import javax.inject.Inject
import utils.fujitask.scalikejdbc._

import scala.concurrent.Future

class UserService @Inject()(userRepository: UserRepository) {
  def create(name: String): Future[User] =
    userRepository.create(name).run()

  def read(id: Long): Future[Option[User]] =
    userRepository.read(id).run()

  def readAll: Future[List[User]] =
    userRepository.readAll.run()

  def update(user: User): Future[Unit] =
    userRepository.update(user).run()

  def delete(id: Long): Future[Unit] =
    userRepository.delete(id).run()
}

