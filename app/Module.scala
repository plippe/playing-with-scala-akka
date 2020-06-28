import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport

class Module extends AbstractModule with AkkaGuiceSupport {

  override def configure() = {
    bind(classOf[records.RecordsDao]).to(classOf[records.RandomRecordsDao])
    bind(classOf[files.FilesDao]).to(classOf[files.TemporaryFilesDao])

    bindActor[requests.RequestActor](requests.RequestActor.name)
  }

}
