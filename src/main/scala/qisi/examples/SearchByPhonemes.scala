package qisi.examples
import qisi.{Entries, Phoneme}
import util.Subsequence

object SearchByPhonemes {
  def main(args: Array[String]): Unit = {
    val phonemeStrings = List("EH", "N", "JH")
    val phonemes = Phoneme.phonemeOptFromStrings(phonemeStrings)
    val matches = Entries.enEntries.filter( e => Subsequence.hasSubsequence(e.phonemesOpt.get.toList, phonemes.get
      .toList))
    matches.foreach( e => println(e.word))
  }
}
