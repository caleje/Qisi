package qisi
import cats.Applicative
import cats.std.all._

case class Phoneme(code: String, example: String, translation: String)
object Phoneme {
  val allPhonemes = List(
    Phoneme("AA", "odd", "AA D"),
    Phoneme("AE", "at", "AE T"),
    Phoneme("AH", "hut", "HH AH T"),
    Phoneme("AO", "ought", "AO T"),
    Phoneme("AW", "cow", "K AW"),
    Phoneme("AY", "hide", "HH AY D"),
    Phoneme("B", "be", "B IY"),
    Phoneme("CH", "cheese", "CH IY Z"),
    Phoneme("D", "dee", "D IY"),
    Phoneme("DH", "thee", "DH IY"),
    Phoneme("EH", "Ed", "EH D"),
    Phoneme("ER", "hurt", "HH ER T"),
    Phoneme("EY", "ate", "EY T"),
    Phoneme("F", "fee", "F IY"),
    Phoneme("G", "green", "G R IY N"),
    Phoneme("HH", "he", "HH IY"),
    Phoneme("IH", "it", "IH T"),
    Phoneme("IY", "eat", "IY T"),
    Phoneme("JH", "gee", "JH IY"),
    Phoneme("K", "key", "K IY"),
    Phoneme("L", "lee", "L IY"),
    Phoneme("M", "me", "M IY"),
    Phoneme("N", "knee", "N IY"),
    Phoneme("NG", "ping", "P IH NG"),
    Phoneme("OW", "oat", "OW T"),
    Phoneme("OY", "toy", "T OY"),
    Phoneme("P", "pee", "P IY"),
    Phoneme("R", "read", "R IY D"),
    Phoneme("S", "sea", "S IY"),
    Phoneme("SH", "she", "SH IY"),
    Phoneme("T", "tea", "T IY"),
    Phoneme("TH", "theta", "TH EY T AH"),
    Phoneme("UH", "hood", "HH UH D"),
    Phoneme("UW", "two", "T UW"),
    Phoneme("V", "vee", "V IY"),
    Phoneme("W", "we", "W IY"),
    Phoneme("Y", "yield", "Y IY L D"),
    Phoneme("Z", "zee", "Z IY"),
    Phoneme("ZH", "seizure", "S IY ZH ER"))
  val phonemesByCode = allPhonemes map { p => p.code -> p} toMap
  def phonemeOptFromStrings(phonemeStrings: Seq[String]): Option[Seq[Phoneme]] = {
    val upperCasePhonemeStrings = phonemeStrings map ( s => s.toUpperCase)
    val phonemeOpts = upperCasePhonemeStrings map phonemesByCode.get
    Applicative[Option].sequence[List, Phoneme](phonemeOpts.toList)
  }
}