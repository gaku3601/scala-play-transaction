package usecase.user

import domain.entity.user.{Age, Name, User}
import domain.service.UserService
import infrastructure.queryservice.{ReadAllUser, UserQueryService}
import infrastructure.repository.UserRepository
import javax.inject.Inject
import utils.fujitask.scalikejdbc._

import scala.concurrent.{ExecutionContext, Future}

class UserUsecase @Inject()(userRepository: UserRepository, userService: UserService, userQueryService: UserQueryService)(implicit ec: ExecutionContext) {
  def create(name: String, age: Int): Future[User] = {
    (for {
      _ <- userService.checkUserName(Name(name))
      user <- userRepository.create(User(Name(name), Age(age)))
    } yield user).run()
  }

  def read(id: Long): Future[Option[User]] =
    userRepository.read(id).run()

  def readAll(page: Int): Future[List[ReadAllUser]] =
    userQueryService.readAll(page).run()

  def update(user: User): Future[Unit] = {
    (for {
      _ <- userService.checkUserName(user.id, user.name)
      _ <- userRepository.update(user)
    } yield ()).run()
  }

  def delete(id: Long): Future[Unit] =
    userRepository.delete(id).run()
}
