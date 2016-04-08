package qisi

import qisi.Types.Phoneme

object Translation {
  def EnglishToPhoneme(s: String): Seq[Phoneme] = {
    val words = s.toUpperCase.split(" ")
    words flatMap { word => qisi.Entries.enEntriesByWord(word)} flatMap { e => e.phonemes}
  }
}
