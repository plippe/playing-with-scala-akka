package decks.requests

import akka.actor.{Actor, ActorRef}
import java.util.UUID
import javax.inject.{Inject, Named, Singleton}
import play.api.mvc.{Action, BaseController, ControllerComponents}
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext

@Singleton
class DeckRequestsController @Inject()(
  deckRequestsDao: DeckRequestsDao,
  @Named(DeckRequestsActor.name) deckRequestsActor: ActorRef,
  val controllerComponents: ControllerComponents
)(implicit ec: ExecutionContext) extends BaseController {

  val missingContentType = UnprocessableEntity("Expected 'Content-Type' set to 'multipart/form-data'")
  val missingFile = UnprocessableEntity("Expected content to contain a file")

  def post = Action { req =>
    req.body
      .asMultipartFormData.toRight(missingContentType)
      .flatMap(_.file("file").toRight(missingFile))
      .map(DeckRequest.fromFile)
      .map { deckRequest =>
        deckRequestsDao.insert(deckRequest)
        deckRequestsActor.tell(deckRequest, Actor.noSender)
        Created(Json.toJson(deckRequest))
      }
      .merge
  }

  def getById(id: UUID) = Action {
    deckRequestsDao.findById(id)
      .map(Json.toJson[DeckRequest])
      .fold(NotFound(s"Deck request not found: ${id}"))(Ok(_))
  }

}
