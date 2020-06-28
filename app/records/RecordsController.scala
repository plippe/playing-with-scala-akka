package records

import javax.inject.{Inject, Singleton}
import play.api.mvc.{Action, BaseController, ControllerComponents}
import scala.concurrent.ExecutionContext

@Singleton
class RecordsController @Inject()(
  recordsDao: RecordsDao,
  recordsWriter: RecordsWriter,
  val controllerComponents: ControllerComponents
)(implicit ec: ExecutionContext) extends BaseController {

  def get() = Action { req =>
    val all = recordsDao.findAll
    val file = recordsWriter.toCsvFile(all)

    Ok.sendFile(file)
  }

}
