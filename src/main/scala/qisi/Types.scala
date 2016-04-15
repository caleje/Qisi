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
    lazy val pinyins = pinyin.replaceAll("([^a-zA-Z ])", "").replaceAll("  ", " ") split (" ") toSeq
    lazy val phonemes = pinyins flatMap ChineseToPhoneme.translate
  }

}