package controllers

import domain.service.UserService
import javax.inject.{Inject, Singleton}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, ControllerComponents, _}

// TODO: これをどこに配置するか
case class PostRequest(body: String, data: String)

object PostRequest {
  // バリデーションを実施する。フロントでバリデーションは必須。不正な操作で送られた場合、ここで弾く程度のものと認識しておく。
  implicit val reads: Reads[PostRequest] = (
    (__ \ "post").read[String].filter(JsonValidationError("必須入力です"))(x => !x.isEmpty) and
      (__ \ "test" \ "data").read[String].filter(JsonValidationError("必須入力です"))(x => !x.isEmpty)
    ) (PostRequest.apply _)
}

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents, userService: UserService) extends BaseController {

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def post: Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[PostRequest].fold(
      error => {
        BadRequest(JsError.toJson(error))
      },
      postRequest => {
        userService.create("Randy")
        Ok(Json.toJson("ok"))
      }
    )
  }
}
