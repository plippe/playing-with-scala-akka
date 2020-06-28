package requests

import akka.actor.{Actor, ActorRef}
import java.util.UUID
import javax.inject.{Inject, Named, Singleton}
import play.api.mvc.{Action, BaseController, ControllerComponents}
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext

@Singleton
class RequestsController @Inject()(
  filesDao: files.FilesDao,
  @Named(RequestActor.name) requestActor: ActorRef,
  val controllerComponents: ControllerComponents
)(implicit ec: ExecutionContext) extends BaseController {

  val missingContentType = UnprocessableEntity("Expected 'Content-Type' set to 'application/json'")
  val missingRequestForm = UnprocessableEntity("Expected content to contain a request form")

  def post = Action { req =>
    req.body.asJson
      .toRight(missingContentType)
      .flatMap(_.asOpt[RequestForm].toRight(missingRequestForm))
      .flatMap { form =>
        Request.fromForm(form)
          .left.map(throwable => UnprocessableEntity(throwable.getMessage))
      }
      .map { model =>
        requestActor.tell(model, Actor.noSender)
        Created(Json.toJson(model))
      }
      .merge
  }

  def getById(id: UUID) = Action {
    filesDao.findById(id)
      .fold(NotFound(s"Request not found: ${id}"))(Ok.sendFile(_))
  }

}
