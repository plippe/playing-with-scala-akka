package requests

import akka.actor.Actor
import javax.inject.Inject
import play.api.libs.mailer.{Email, MailerClient}

object RequestActor {
  final val name = "request-actor"
}

class RequestActor @Inject()(
  filesDao: files.FilesDao,
  recordsDao: records.RecordsDao,
  recordsWriter: records.RecordsWriter,
  mailerClient: MailerClient,
) extends Actor {
  def receive = {
    case req: Request => handleRequest(req)
  }

  def handleRequest(req: Request): Unit = {
    saveFile(req)
    sendEmail(req)
  }

  private def saveFile(req: Request): Unit = {
    val all = recordsDao.findAll
    val file = recordsWriter.toCsvFile(all)

    filesDao.insert(req.id, file)
  }

  private def sendEmail(req: Request): Unit = {
    val email = Email(
      subject = "Records are ready",
      from = "no-reply@plippe.github.com",
      to = Seq(req.email),
      bodyText = Option(s"Download the records at ${routes.RequestsController.getById(req.id)}"),
    )

    mailerClient.send(email)
  }
}
