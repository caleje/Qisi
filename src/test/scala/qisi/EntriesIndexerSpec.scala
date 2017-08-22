package qisi

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

class EntriesIndexerSpec extends FlatSpec with Matchers with MockFactory {
  "EntriesIndexer.enEntriesByPhonemes" should "index a word by all subsequences of the word" in {
    // Arrange
    val englishEntriesLoader = stub[EnglishEntriesLoader]
    val chineseEntriesLoader = stub[ChineseEntriesLoader]

    val enLines = Seq(
      "AB\tAE B",
      "BAH\tB AA"
    )
    val enEntries = EnglishEntriesLoaderImpl.enEntriesFromLines(enLines)
    (englishEntriesLoader.enEntries _).when().returns(enEntries)
    (chineseEntriesLoader.chEntries _).when().returns(Seq())

    val entriesIndexer = new EntriesIndexer(englishEntriesLoader, chineseEntriesLoader)

    // Act
    val actual = entriesIndexer.enEntriesByPhonemes

    // Assert
    val ab = enEntries.head
    val bah = enEntries(1)
    val expected = Map(
      Seq("AE").map(Phoneme.phonemesByCode.get) -> Seq(ab),
      Seq("AA").map(Phoneme.phonemesByCode.get) -> Seq(bah),
      Seq("AE", "B").map(Phoneme.phonemesByCode.get) -> Seq(ab),
      Seq("B", "AA").map(Phoneme.phonemesByCode.get) -> Seq(bah),
      Seq("B").map(Phoneme.phonemesByCode.get) -> Seq(ab, bah)
    )
    actual should contain theSameElementsAs expected
  }
}
