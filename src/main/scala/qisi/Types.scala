package qisi

object Types {

  type Phoneme = String

  sealed trait Pronounceable {
    def word: String
    def phonemes: Seq[Phoneme]
  }

  final case class EnglishEntry(word: String, phonemes: Seq[Phoneme], rawEntry: String) extends Pronounceable

  final case class ChineseEntry(word: String, pinyin: String, definition: String, rawEntry: String) extends
    Pronounceable {
    private val pinyinSyllables = pinyin.replaceAll("[1-4]", "") split (" ") toSeq
    val phonemes = pinyinSyllables flatMap ChineseToPhoneme.translate
  }

}