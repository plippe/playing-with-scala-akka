package scryfall

import play.api.libs.json._

case class Images(
  small: String,
)

object Images {
  implicit def scryfallImagesJsonReads = Json.reads[Images]
}
