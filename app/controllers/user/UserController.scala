package controllers.user

import controllers.user.UserRequest._
import controllers.user.UserResponse._
import domain.entity.user.User
import javax.inject.{Inject, Singleton}
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, ControllerComponents, _}
import usecase.user.UserUsecase

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents, userUsecase: UserUsecase)(implicit ec: ExecutionContext) extends BaseController {

  def index(page: Int): Action[AnyContent] = Action.async { implicit request =>
    userUsecase.readAll(page) map { users =>
      Ok(Json.toJson(users))
    }
  }

  def show(id: Long): Action[AnyContent] = Action.async { implicit request =>
    userUsecase.read(id) map { user =>
      Ok(Json.toJson(user))
    }
  }

  def create: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[CreateRequest].fold(
      error => {
        Future.successful(BadRequest(JsError.toJson(error)))
      },
      createRequest => {
        userUsecase.create(createRequest.name, createRequest.age) map { user =>
          Ok(Json.obj("status" -> "OK", "data" -> Json.obj("user" -> user)))
        }
      }
    )
  }

  def update: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[UpdateRequest].fold(
      error => {
        Future.successful(BadRequest(JsError.toJson(error)))
      },
      updateRequest => {
        userUsecase.update(User(updateRequest.id, updateRequest.name, updateRequest.age)) map { _ =>
          Ok(Json.obj("status" -> "OK", "data" -> Json.obj()))
        }
      }
    )
  }
}
