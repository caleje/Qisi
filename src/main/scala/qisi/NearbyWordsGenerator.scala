package qisi

import util.Sequence

object NearbyWordsGenerator {

  private def combinationsByInserting(phonemes: Seq[Option[Phoneme]]): Seq[Seq[Option[Phoneme]]] = {
    Phoneme.allPhonemes.flatMap(p => Sequence.sequencesByInserting(phonemes, Some(p)))
  }

  private def combinationsByRemoving(phonemes: Seq[Option[Phoneme]]): Seq[Seq[Option[Phoneme]]] = {
    Sequence.sequencesByRemoving(phonemes)
  }

  private def combinationsByPatching(phonemes: Seq[Option[Phoneme]]): Seq[Seq[Option[Phoneme]]] = {
    Phoneme.allPhonemes.flatMap(p => Sequence.sequencesByPatching(phonemes, Some(p))).distinct
  }

  def generate(phonemes: Seq[Option[Phoneme]]): Seq[Seq[Option[Phoneme]]] = {
    combinationsByInserting(phonemes) ++
    combinationsByRemoving(phonemes) ++
    combinationsByPatching(phonemes)
  }
}
