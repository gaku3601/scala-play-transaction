package controllers

import domain.service.UserService
import javax.inject.{Inject, Singleton}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, ControllerComponents, _}

import scala.concurrent.{ExecutionContext, Future}

case class PostRequest(name: String, age: Int)

object PostRequest {
  // バリデーションを実施する。フロントでバリデーションは必須。不正な操作で送られた場合、ここで弾く程度のものと認識しておく。
  implicit val reads: Reads[PostRequest] = (
    (__ \ "name").read[String].filter(JsonValidationError("必須入力です"))(x => !x.isEmpty) and
      (__ \ "age").read[Int]
    ) (PostRequest.apply _)
}

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents, userService: UserService)(implicit ec: ExecutionContext) extends BaseController {

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def post: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[PostRequest].fold(
      error => {
        Future.successful(BadRequest(JsError.toJson(error)))
      },
      postRequest => {
        userService.create(postRequest.name, postRequest.age) map { user =>
          Ok(Json.obj("status" -> "OK"))
        }
      }
    )
  }
}
