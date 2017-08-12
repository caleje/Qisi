package qisi

import cats.Applicative
import cats.implicits._

trait PronounceableEntry {
  def word: String
  def phonemes: Seq[Option[Phoneme]]
  def phonemesOpt: Option[Seq[Phoneme]] = Applicative[Option].sequence[List, Phoneme](phonemes.toList)
}

final case class ParsedEnglishEntry(word: String, phonemeStrings: Seq[String], unparsedEntry: String)
final case class ParsedChineseEntry(word: String, pinyinStrings: Seq[String], definition: String, unparsedEntry: String)

final case class EnglishEntry(entry: ParsedEnglishEntry, phonemes: Seq[Option[Phoneme]]) extends PronounceableEntry {
  val word: String = entry.word
}

final case class ChineseEntry(entry: ParsedChineseEntry, phonemes: Seq[Option[Phoneme]]) extends PronounceableEntry {
  val word: String = entry.word
}