package files

import java.io.File
import java.nio.file.{Files, Path, Paths}
import java.util.UUID
import javax.inject.Singleton

trait FilesDao {
  def findById(id: UUID): Option[File]
  def insert(id: UUID, file: File): Unit
}

@Singleton
class TemporaryFilesDao extends FilesDao {

  def findById(id: UUID): Option[File] = {
    val file = path(id).toFile

    if(file.exists) Option(file)
    else Option.empty
  }

  def insert(id: UUID, file: File): Unit =
    Files.copy(file.toPath, path(id))

  private def path(id: UUID): Path =
    Paths.get(System.getProperty("java.io.tmpdir"), id.toString)

}
