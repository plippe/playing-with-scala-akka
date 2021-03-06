package cards

import play.api.libs.json.Json
import scala.concurrent.{ExecutionContext, Future}

case class Card(
  id: String,
  name: String,
  setId: String,
  setName: String,
  image: String,
)

object Card {
  implicit def cardsCardJsonWrite = Json.writes[Card]

  def fromReference(client: scryfall.Client, reference: CardReference)(implicit ec: ExecutionContext): Future[Card] =
    client.card(reference.setId, reference.id)
      .map(fromScryfallCard)

  def fromScryfallCard(card: scryfall.Card): Card =
    Card(
      id = card.collectorNumber,
      name = card.name,
      setId = card.set,
      setName = card.setName,
      image = card.imageUris.small,
    )
}

case class CardReference(
  id: String,
  setId: String,
)

object CardReference {
  def fromCsv(csv: Map[String, String]): Either[Throwable, CardReference] = {
    val card = csv.get("card").toRight(new Throwable("Missing header: card"))
    val set = csv.get("set").toRight(new Throwable("Missing header: set"))

    (card, set) match {
      case (Right(card), Right(set)) => Right(CardReference(card, set))
      case (Left(cardException), _) => Left(cardException)
      case (_, Left(setException)) => Left(setException)
    }
  }
}
