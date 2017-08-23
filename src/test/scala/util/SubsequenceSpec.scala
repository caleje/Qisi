package util

import org.scalatest.{FlatSpec, Matchers}

class SubsequenceSpec extends FlatSpec with Matchers {
  "Subsequence.subseqs" should "generate all subsequences in a Seq" in {

    // Arrange/Act
    val actual = Subsequence.subseqs(Seq(1,2,3))

    // Assert
    val expected = Seq(Seq(1), Seq(2), Seq(3), Seq(1,2), Seq(2,3), Seq(1,2,3))
    actual should contain theSameElementsAs expected
  }

  "Subsequence.startingSubseqs" should "generate all subsequences that a Seq starts with" in {
    // Arrange/Act
    val actual = Subsequence.startingSubseqs(Seq(1,2,3,4))

    // Assert
    val expected = Seq(Seq(1), Seq(1,2), Seq(1,2,3), Seq(1,2,3,4))
    actual should contain theSameElementsAs expected
  }

  "Subsequence.endingSubseqs" should "generate all subsequences that a Seq starts with" in {
    // Arrange/Act
    val actual = Subsequence.endingSubseqs(Seq(1,2,3,4))

    // Assert
    val expected = Seq(Seq(1,2,3,4), Seq(2,3,4), Seq(3, 4), Seq(4))
    actual should contain theSameElementsAs expected
  }
}
