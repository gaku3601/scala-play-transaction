package controllers.user

import controllers.user.UserRequest._
import controllers.user.UserResponse._
import domain.service.UserService
import javax.inject.{Inject, Singleton}
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, ControllerComponents, _}

import scala.concurrent.{ExecutionContext, Future}

// TODO: QueryService考える
// TODO: Messageの実装を軽くやる

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents, userService: UserService)(implicit ec: ExecutionContext) extends BaseController {

  // TODO: ページングどうするか検討する
  def index(): Action[AnyContent] = Action.async { implicit request =>
    userService.readAll map { users =>
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
