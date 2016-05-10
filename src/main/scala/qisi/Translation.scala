package qisi


object Translation {
  def EnglishToPhoneme(s: String): Seq[Option[Phoneme]] = {
    val words = s.toUpperCase.split(" ")
    words flatMap { word => qisi.Entries.enEntriesByWord(word)} flatMap { e => e.phonemes}
  }
}
