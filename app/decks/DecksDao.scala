package decks

import java.util.UUID
import javax.inject.Singleton
import scala.util.Random

trait DecksDao {
  def findById(id: UUID): Option[Deck]
  def insert(deck: Deck): Unit
}

@Singleton
class DecksDaoMap extends DecksDao {
  val map = collection.mutable.Map.empty[UUID, Deck]

  def findById(id: UUID): Option[Deck] = map.get(id)
  def insert(deck: Deck): Unit = map.update(deck.id, deck)
}
