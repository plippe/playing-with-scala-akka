package records

import com.github.tototoshi.csv._
import java.io.File
import javax.inject.Singleton

@Singleton
class RecordsWriter {

  val CSV_HEADERS = List("id", "information")
  def toCsvFile(records: List[Record]): File = {
    val rows = CSV_HEADERS +:
      records.map(r => List(r.id.toString, r.information))

    val file = File.createTempFile("records", ".csv")
    val csvWriter = CSVWriter.open(file)
    csvWriter.writeAll(rows)
    csvWriter.close

    file
  }

}
