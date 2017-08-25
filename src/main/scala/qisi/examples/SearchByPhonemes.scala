package qisi.examples
import qisi.{ChineseEntriesLoaderImpl, EnglishEntriesLoaderImpl, EntriesIndexer, Phoneme}
import util.Sequence

object SearchByPhonemes {
  def main(args: Array[String]): Unit = {
    val entriesIndexer = new EntriesIndexer(EnglishEntriesLoaderImpl, ChineseEntriesLoaderImpl)
    val phonemeStrings = List("EH", "N", "JH")
    val phonemes = Phoneme.phonemeOptFromStrings(phonemeStrings)
    val matches = entriesIndexer.enEntries.filter( e => Sequence.hasSubsequence(e.phonemesOpt.get.toList, phonemes.get
      .toList))
    matches.foreach( e => println(e.word))
  }
}
