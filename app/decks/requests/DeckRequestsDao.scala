package decks.requests

import java.util.UUID
import javax.inject.Singleton
import scala.util.Random

trait DeckRequestsDao {
  def findById(id: UUID): Option[DeckRequest]
  def insert(deckRequest: DeckRequest): Unit
  def update(deckRequest: DeckRequest): Unit
}

@Singleton
class DeckRequestsDaoMap extends DeckRequestsDao {
  val map = collection.mutable.Map.empty[UUID, DeckRequest]

  def findById(id: UUID): Option[DeckRequest] = map.get(id)
  def insert(deckRequest: DeckRequest): Unit = map.update(deckRequest.id, deckRequest)
  def update(deckRequest: DeckRequest): Unit = map.update(deckRequest.id, deckRequest)
}
