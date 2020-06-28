package records

import java.util.UUID
import javax.inject.Singleton
import scala.util.Random

trait RecordsDao {
  def findAll: List[Record]
}

@Singleton
class RandomRecordsDao extends RecordsDao {

  def randomRecord: Record = {
    val MAX_LENGTH = 50
    Record(
      UUID.randomUUID,
      Random.nextString(Random.nextInt(MAX_LENGTH)),
    )
  }

  def findAll: List[Record] = {
    val MIN_LENGTH = 1_000_000
    val MAX_LENGTH = 5_000_000

    0.to(Math.max(MIN_LENGTH, Random.nextInt(MAX_LENGTH)))
      .map(_ => randomRecord)
      .toList
  }

}
