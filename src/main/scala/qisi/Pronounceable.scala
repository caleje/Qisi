package qisi

sealed trait Pronounceable {
  def word: String
  def phonemes: Seq[Phoneme]
}

