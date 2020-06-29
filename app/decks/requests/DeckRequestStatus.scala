package decks.requests

import java.nio.file.Path
import play.api.libs.json._

sealed trait DeckRequestStatus extends Product with Serializable
object DeckRequestStatus {
  implicit def decksRequestsDeckRequestStatusJsonWrite = Writes[DeckRequestStatus] {
    case Pending(path: Path) => Json.toJson(Map("value" -> "pending"))
    case InProgress => Json.toJson(Map("value" -> "in_progress"))
    case Success(deck) => Json.toJson(Map("value" -> "success", "deck_id" -> deck.id.toString))
    case Failure(exceptions) =>
      val errors = Json.toJson(exceptions.map(_.getMessage))
      JsObject(Map("value" -> Json.toJson("failure"), "errors" -> errors))
  }

  case class Pending(path: Path) extends DeckRequestStatus
  case object InProgress extends DeckRequestStatus
  case class Success(deck: decks.Deck) extends DeckRequestStatus
  case class Failure(exceptions: List[Throwable]) extends DeckRequestStatus
}
