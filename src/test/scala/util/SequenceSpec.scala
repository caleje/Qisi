package util

import org.scalatest.{FlatSpec, Matchers}

class SequenceSpec extends FlatSpec with Matchers {
  "Sequence.subseqs" should "generate all subsequences in a Seq" in {

    // Arrange/Act
    val actual = Sequence.subseqs(Seq(1,2,3))

    // Assert
    val expected = Seq(Seq(1), Seq(2), Seq(3), Seq(1,2), Seq(2,3), Seq(1,2,3))
    actual should contain theSameElementsAs expected
  }

  "Sequence.startingSubseqs" should "generate all subsequences that a Seq starts with" in {
    // Arrange/Act
    val actual = Sequence.startingSubseqs(Seq(1,2,3,4))

    // Assert
    val expected = Seq(Seq(1), Seq(1,2), Seq(1,2,3), Seq(1,2,3,4))
    actual should contain theSameElementsAs expected
  }

  "Sequence.endingSubseqs" should "generate all subsequences that a Seq starts with" in {
    // Arrange/Act
    val actual = Sequence.endingSubseqs(Seq(1,2,3,4))

    // Assert
    val expected = Seq(Seq(1,2,3,4), Seq(2,3,4), Seq(3, 4), Seq(4))
    actual should contain theSameElementsAs expected
  }

  "Sequence.sequencesByInserting" should "generate all sequences where you insert an item" in {
    // Arrange/Act
    val actual = Sequence.sequencesByInserting(Seq(1,2,3), 0)

    // Assert
    val expected = Seq(Seq(0,1,2,3), Seq(1,0,2,3), Seq(1,2,0,3), Seq(1,2,3,0))
    actual should contain theSameElementsAs expected
  }

  "Sequence.sequencesByRemoving" should "generate all sequences where you remove an item" in {
    // Arrange/Act
    val actual = Sequence.sequencesByRemoving(Seq(1,2,3))

    // Assert
    val expected = Seq(Seq(2,3), Seq(1,3), Seq(1,2))
    actual should contain theSameElementsAs expected
  }

  "Sequence.sequencesByPatching" should "generate all sequences where you patching" in {
    // Arrange/Act
    val actual = Sequence.sequencesByPatching(Seq(1,2,3), 4)

    // Assert
    val expected = Seq(Seq(4,2,3), Seq(1,4,3), Seq(1,2,4))
    actual should contain theSameElementsAs expected
  }
}
