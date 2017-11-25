package qisi

import cats.Applicative
import cats.implicits._

trait PronounceableEntry {
  def word: String
  def phonemes: Seq[Phoneme]
  def toStringDetailed = s"$word, ${Phoneme.toString(phonemes)}"
}

final case class ParsedEnglishEntry(word: String, phonemeStrings: Seq[String], unparsedEntry: String)
final case class ParsedChineseEntry(word: String, pinyinStrings: Seq[String], definition: String, unparsedEntry: String)

final case class EnglishEntry(entry: ParsedEnglishEntry, phonemes: Seq[Phoneme]) extends PronounceableEntry {
  val word: String = entry.word
  override def toString: String = word
}

final case class ChineseEntry(entry: ParsedChineseEntry, phonemes: Seq[Phoneme]) extends PronounceableEntry {
  val word: String = entry.word
  override def toString: String = word
}

