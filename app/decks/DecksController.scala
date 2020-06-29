package decks

import javax.inject.Inject
import java.util.UUID
import play.api.mvc.{BaseController, ControllerComponents}
import play.api.libs.json.Json

class DecksController @Inject()(
  decksDao: DecksDao,
  val controllerComponents: ControllerComponents
) extends BaseController {

  def getById(id: UUID) = Action {
    decksDao.findById(id)
      .map(Json.toJson[Deck])
      .fold(NotFound(s"Deck not found: ${id}"))(Ok(_))
  }

}
