package controllers.user

import controllers.user.UserRequest._
import controllers.user.UserResponse._
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

  def create: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[CreateRequest].fold(
      error => {
        Future.successful(BadRequest(JsError.toJson(error)))
      },
      createRequest => {
        userService.create(createRequest.name, createRequest.age) map { user =>
          Ok(Json.obj("status" -> "OK", "data" -> user))
        }
      }
    )
  }
}
