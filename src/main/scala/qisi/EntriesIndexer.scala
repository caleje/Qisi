package qisi
import util.Subsequence

class EntriesIndexer(englishEntriesLoader: EnglishEntriesLoader, chineseEntriesLoader: ChineseEntriesLoader) {

  val enEntries: Seq[EnglishEntry] = englishEntriesLoader.enEntries
  val enEntriesByWord: Map[String, Seq[EnglishEntry]] = englishEntriesLoader.enEntries groupBy {_.word}
  val enEntriesByPhonemes: Map[Seq[Option[Phoneme]], Seq[EnglishEntry]] = {
    val phonemeSubsequenceEntry = englishEntriesLoader.enEntries.flatMap(e => Subsequence.subseqs(e.phonemes).map((_, e)))
    val phonemeSubsequenceEntriesByPhonemes = phonemeSubsequenceEntry groupBy {_._1}
    val entriesByPhonemes = phonemeSubsequenceEntriesByPhonemes mapValues {_.map(_._2)}
    entriesByPhonemes
  }
  val chEntries: Seq[ChineseEntry] = chineseEntriesLoader.chEntries
  val chEntriesByWord: Map[String, Seq[ChineseEntry]] = chineseEntriesLoader.chEntries groupBy {_.word}
  val chEntriesByPinyin: Map[Seq[String], Seq[ChineseEntry]] = chineseEntriesLoader.chEntries groupBy {_.entry.pinyinStrings}
  val chEntriesByPhonemes: Map[Seq[Option[Phoneme]], Seq[ChineseEntry]] = chineseEntriesLoader.chEntries groupBy {_.phonemes}
}

object EntriesIndexer {
  def apply(): EntriesIndexer = {
    new EntriesIndexer(EnglishEntriesLoaderImpl, ChineseEntriesLoaderImpl)
  }
}

