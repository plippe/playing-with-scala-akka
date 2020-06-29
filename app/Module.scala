import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport

class Module extends AbstractModule with AkkaGuiceSupport {

  override def configure() = {
    bind(classOf[decks.DecksDao]).to(classOf[decks.DecksDaoMap])
    bind(classOf[decks.requests.DeckRequestsDao]).to(classOf[decks.requests.DeckRequestsDaoMap])

    bindActor[decks.requests.DeckRequestsActor](decks.requests.DeckRequestsActor.name)
  }

}
