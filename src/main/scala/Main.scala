import java.io.InputStream

import qisi.Entries

object Main {


  def main(args: Array[String]): Unit = {
    val enEntries = Entries.enEntries
    val chEntries = Entries.chEntries

    val enEntriesByPhoneme = enEntries groupBy(_.phonemes)
    val enHomophones = enEntriesByPhoneme.filter(_._2.length > 1)
    val chEntriesByPhoneme = chEntries.groupBy(_.phonemes)
    val chHomophones = chEntriesByPhoneme.filter(_._2.length > 1)

    println(s"English #words: ${enEntries.length}, #homophones: ${enHomophones.keys.toSeq.length}")
    println(s"Chinese #words: ${chEntries.length}, #homophones: ${chHomophones.keys.toSeq.length}")
  }

}
