package qisi

import util.Sequence

object NearbyWordsGenerator {

  private def combinationsByInserting(phonemes: Seq[Phoneme]): Seq[Seq[Phoneme]] = {
    Phoneme.allPhonemes.flatMap(p => Sequence.sequencesByInserting(phonemes, p))
  }

  private def combinationsByRemoving(phonemes: Seq[Phoneme]): Seq[Seq[Phoneme]] = {
    Sequence.sequencesByRemoving(phonemes)
  }

  private def combinationsByPatching(phonemes: Seq[Phoneme]): Seq[Seq[Phoneme]] = {
    Phoneme.allPhonemes.flatMap(p => Sequence.sequencesByPatching(phonemes, p)).distinct
  }

  def generate(phonemes: Seq[Phoneme]): Seq[Seq[Phoneme]] = {
    combinationsByInserting(phonemes) ++
    combinationsByRemoving(phonemes) ++
    combinationsByPatching(phonemes)
  }
}
