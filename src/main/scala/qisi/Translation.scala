package qisi

object Translation {
  def EnglishToPhoneme(s: String): Seq[Phoneme] = {
    val entriesIndexer = new EntriesIndexer(EnglishEntriesLoaderImpl, ChineseEntriesLoaderImpl)
    val words = s.toUpperCase.split(" ")
    words flatMap { word => entriesIndexer.enEntriesByWord(word)} flatMap { e => e.phonemes}
  }
}
