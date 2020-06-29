package decks.requests

import java.util.UUID
import play.api.mvc.MultipartFormData.FilePart
import play.api.libs.Files.TemporaryFile
import play.api.libs.json.Json

case class DeckRequest(
  id: UUID,
  deckId: UUID,
  status: DeckRequestStatus,
)

object DeckRequest {
  implicit def decksRequestsDeckRequestJsonWrite = Json.writes[DeckRequest]

  def fromFile(file: FilePart[TemporaryFile]): DeckRequest =
    DeckRequest(UUID.randomUUID, UUID.randomUUID, DeckRequestStatus.Pending(file.ref.path))
}
