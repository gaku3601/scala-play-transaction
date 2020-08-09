package controllers.user

import controllers.user.UserRequest._
import controllers.user.UserResponse._
import domain.entity.user.User
import domain.service.UserService
import infrastructure.queryservice.UserQueryService
import javax.inject.{Inject, Singleton}
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, ControllerComponents, _}
import utils.fujitask.scalikejdbc.readRunner

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents, userService: UserService, userQueryService: UserQueryService)(implicit ec: ExecutionContext) extends BaseController {

  def index(page: Int): Action[AnyContent] = Action.async { implicit request =>
    userQueryService.readAll(page).run() map { users =>
      Ok(Json.toJson(users))
    }
  }

  def show(id: Int): Action[AnyContent] = Action.async { implicit request =>
    userService.read(id) map { user =>
      Ok(Json.toJson(user))
    }
  }

  def create: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[CreateRequest].fold(
      error => {
        Future.successful(BadRequest(JsError.toJson(error)))
      },
      createRequest => {
        userService.create(createRequest.name, createRequest.age) map { response =>
          Ok(Json.obj("status" -> "OK", "data" -> Json.obj("user1" -> response._1, "user2" -> response._2)))
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
        userService.update(User(updateRequest.id, updateRequest.name, updateRequest.age)) map { _ =>
          Ok(Json.obj("status" -> "OK", "data" -> Json.obj()))
        }
      }
    )
  }
}
