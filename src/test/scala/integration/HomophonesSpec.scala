package integration

import org.scalatest.{FlatSpec, Matchers}
import qisi.Entries

class HomophonesSpec extends FlatSpec {

  "Homophones" should "work" in {

    val enEntries = Entries.enEntries
    val chEntries = Entries.chEntries

    val enEntriesByPhoneme = enEntries groupBy(_.phonemes)
    val enHomophones = enEntriesByPhoneme.filter(_._2.length > 1)
    val chEntriesByPhoneme = chEntries.groupBy(_.phonemes)
    val chHomophones = chEntriesByPhoneme.filter(_._2.length > 1)
  }
}
