package scryfall

import play.api.libs.json._

case class Card(
  collectorNumber: String,
  name: String,
  set: String,
  setName: String,
  imageUris: Images,
)

object Card {
  implicit val config = JsonConfiguration(JsonNaming.SnakeCase)
  implicit def scryfallCardJsonReads = Json.reads[Card]
}
