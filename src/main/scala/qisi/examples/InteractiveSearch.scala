package qisi.examples

import qisi._

object InteractiveSearch extends App {
  val entriesIndexer = new EntriesIndexer(EnglishEntriesLoaderImpl, ChineseEntriesLoaderImpl)
  val searchSpaceGivenEntries = (entries1: Seq[EnglishEntry], entries2: Seq[EnglishEntry]) => {
    val combined = for {
      word1 <- entries1
      word2 <- entries2
      word1word2 = word1.phonemes ++ word2.phonemes
      wordsWithSamePhonemes <- entriesIndexer.enEntriesByPhonemes.get(word1word2)
    } yield (word1.word, word2.word, wordsWithSamePhonemes)
    val flattened = combined.flatMap(r => r._3.map(r3 => (r._1, r._2, r3.word)))
    flattened
  }
  val searchSpaceGivenEntryAndIndex = (entries: Seq[EnglishEntry], index: Map[Seq[Option[Phoneme]], Seq[EnglishEntry]]) => {
    entries.flatMap(e => index(e.phonemes)).map(e => (e.word, e.word, e.word))
  }

  var current = searchSpaceGivenEntries(entriesIndexer.enEntries, entriesIndexer.enEntries)
  var numLines = 20

  val show = () => {
    println(current.take(numLines).mkString("\n"))
    current = current.drop(numLines)
  }

  do {
    val LinesRegex = """l (\d+)""".r
    val SkipToNextWordRegex = "n".r
    val SkipBasedOnRegex = "r (.+)".r
    val MakePunFromAWordAndAnotherRegex = "g (.+)".r
    val FindWordsContainingSoundsRegex = "f (.+)".r
    val FindWordsStartingWithSoundsRegex = "s (.+)".r
    val FindWordsEndingWithSoundsRegex = "e (.+)".r
    scala.io.StdIn.readLine("Action? ") match {
      case x if x == "m" =>
        show()
      case LinesRegex(numLinesString) =>
        numLines = numLinesString.toInt
        println(s"Now showing $numLines at a time")
      case SkipToNextWordRegex() =>
        current = current.dropWhile(w => w._1 == current.head._1)
        show()
        println(s"Skipped to next word: ${current.head._1}")
      case SkipBasedOnRegex(regexString) =>
        val regex = regexString.r
        current = current.dropWhile(w => regex.findAllIn(w._1).nonEmpty)
        show()
        println(s"Skipped everything matching $regexString: ${current.head._1}")
      case FindWordsContainingSoundsRegex(word) =>
        entriesIndexer.enEntriesByWord.get(word.toUpperCase) match {
          case Some(entries) =>
            current = searchSpaceGivenEntryAndIndex(entries, entriesIndexer.enEntriesByPhonemes)
            println(s"Now showing words containing $word")
            show()
          case None =>
            println(s"$word was not found in the dictionary")
        }
      case FindWordsStartingWithSoundsRegex(word) =>
        entriesIndexer.enEntriesByWord.get(word.toUpperCase) match {
          case Some(entries) =>
            current = searchSpaceGivenEntryAndIndex(entries, entriesIndexer.enEntriesByStartingPhonemes)
            println(s"Now showing words starting with words that sound like $word")
            show()
          case None =>
            println(s"$word was not found in the dictionary")
        }
      case FindWordsEndingWithSoundsRegex(word) =>
        entriesIndexer.enEntriesByWord.get(word.toUpperCase) match {
          case Some(entries) =>
            current = searchSpaceGivenEntryAndIndex(entries, entriesIndexer.enEntriesByEndingPhonemes)
            println(s"Now showing words ending with words that sound like $word")
            show()
          case None =>
            println(s"$word was not found in the dictionary")
        }
      case MakePunFromAWordAndAnotherRegex(word) =>
        entriesIndexer.enEntriesByWord.get(word.toUpperCase) match {
          case Some(entries) =>
            current = searchSpaceGivenEntries(entries, entriesIndexer.enEntries) ++
              searchSpaceGivenEntries(entriesIndexer.enEntries, entries)
            println(s"Now showing two words combined into a third, one of which is $word")
            show()
          case None =>
            println(s"$word was not found in the dictionary")
        }
      case _ @ command =>
        println(s"Did not recognized command $command")
    }
  } while(true)
}
