package decks.requests

import akka.actor.Actor
import com.github.tototoshi.csv._
import java.util.UUID
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Try, Success, Failure}
import scala.util.chaining._

object DeckRequestsActor {
  final val name = "decks-requests-actor"
}

class DeckRequestsActor @Inject()(
  deckRequestsDao: DeckRequestsDao,
  decksDao: decks.DecksDao,
  client: scryfall.Client,
)(implicit ec: ExecutionContext) extends Actor {

  def receive = {
    case req @ DeckRequest(_, _, DeckRequestStatus.Pending(path)) =>
      deckRequestsDao.update(req.copy(status = DeckRequestStatus.InProgress))

      val reader = CSVReader.open(path.toFile)
      Try(reader.iteratorWithHeaders)
        .fold(Future.failed, csv =>
          csv
            .map(_.map { case (k, v) => k.trim -> v.trim })
            .map(cards.CardReference.fromCsv)
            .map {
              case Left(exceptions) => Future.failed(exceptions.head)
              case Right(reference) => cards.Card.fromReference(client, reference)
            }
            .pipe(Future.sequence(_))
        )
        .map { cards =>
          val deck = decks.Deck(req.deckId, cards.toList)
          decksDao.insert(deck)

          req.copy(status = DeckRequestStatus.Success(deck))
        }
        .recover { case exception =>
          req.copy(status = DeckRequestStatus.Failure(List(exception)))
        }
        .map(deckRequestsDao.update)
        .onComplete(_ => reader.close)
  }

}
