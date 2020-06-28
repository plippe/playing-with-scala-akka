package requests

import java.util.UUID
import play.api.data.validation.{Constraints, Invalid}
import play.api.libs.json.Json

case class Request(id: UUID, email: String)

object Request {
  implicit def requestPlayJsonWrites = Json.writes[Request]

  def fromForm(form: RequestForm): Either[Throwable, Request] =
    Constraints.emailAddress.apply(form.email) match {
      case _: Invalid => Left(new Throwable(s"Invalid email: ${form.email}"))
      case _ => Right(
        Request(
          id = UUID.randomUUID,
          email = form.email,
        )
      )
    }
}

case class RequestForm(email: String)

object RequestForm {
  implicit def requestFormPlayJsonReads = Json.reads[RequestForm]
}
