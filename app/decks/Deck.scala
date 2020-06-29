package decks

import java.util.UUID
import play.api.libs.json.Json

case class Deck(
  id: UUID,
  cards: List[_root_.cards.Card],
)

object Deck {
  implicit def decksDeckJsonWrite = Json.writes[Deck]
}
