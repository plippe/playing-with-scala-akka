package scryfall

import javax.inject.Inject
import play.api.libs.ws.WSClient
import scala.concurrent.{ExecutionContext, Future}

class Client @Inject()(wsClient: WSClient) {
  def card(set: String, card: String)(implicit ec: ExecutionContext): Future[Card] =
    wsClient.url(s"https://api.scryfall.com/cards/${set}/${card}?format=json")
      .get
      .map(_.json.as[Card])
}
