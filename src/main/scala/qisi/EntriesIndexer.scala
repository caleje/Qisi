package qisi
import util.Sequence

class EntriesIndexer(englishEntriesLoader: EnglishEntriesLoader, chineseEntriesLoader: ChineseEntriesLoader) {

  lazy val enEntries: Seq[EnglishEntry] = englishEntriesLoader.enEntries
  lazy val enEntriesByWord: Map[String, Seq[EnglishEntry]] = englishEntriesLoader.enEntries groupBy {_.word}
  private def enEntriesBySubseqsGenerator(f: Seq[Option[Phoneme]] => Seq[Seq[Option[Phoneme]]]): Map[Seq[Option[Phoneme]], Seq[EnglishEntry]] = {
    val phonemeSubsequenceEntry = englishEntriesLoader.enEntries.flatMap(e => f(e.phonemes).map((_, e)))
    val phonemeSubsequenceEntriesByPhonemes = phonemeSubsequenceEntry groupBy {_._1}
    val entriesByPhonemes = phonemeSubsequenceEntriesByPhonemes mapValues {_.map(_._2)}
    entriesByPhonemes
  }
  lazy val enEntriesByPhonemes: Map[Seq[Option[Phoneme]], Seq[EnglishEntry]] = enEntries groupBy {_.phonemes}
  lazy val enEntriesBySubsequencePhonemes: Map[Seq[Option[Phoneme]], Seq[EnglishEntry]]  = enEntriesBySubseqsGenerator(Sequence.subseqs[Option[Phoneme]])
  lazy val enEntriesByStartingPhonemes: Map[Seq[Option[Phoneme]], Seq[EnglishEntry]] = enEntriesBySubseqsGenerator(Sequence.startingSubseqs[Option[Phoneme]])
  lazy val enEntriesByEndingPhonemes: Map[Seq[Option[Phoneme]], Seq[EnglishEntry]] = enEntriesBySubseqsGenerator(Sequence.endingSubseqs[Option[Phoneme]])

  lazy val chEntries: Seq[ChineseEntry] = chineseEntriesLoader.chEntries
  lazy val chEntriesByWord: Map[String, Seq[ChineseEntry]] = chineseEntriesLoader.chEntries groupBy {_.word}
  lazy val chEntriesByPinyin: Map[Seq[String], Seq[ChineseEntry]] = chineseEntriesLoader.chEntries groupBy {_.entry.pinyinStrings}
  lazy val chEntriesByPhonemes: Map[Seq[Option[Phoneme]], Seq[ChineseEntry]] = chineseEntriesLoader.chEntries groupBy {_.phonemes}
}

object EntriesIndexer {
  def apply(): EntriesIndexer = {
    new EntriesIndexer(EnglishEntriesLoaderImpl, ChineseEntriesLoaderImpl)
  }
}

