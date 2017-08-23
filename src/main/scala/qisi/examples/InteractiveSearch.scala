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
    val flattened = combined.flatMap(r => r._3.map(r3 => s"${r._1}+${r._2} = ${r3.word}"))
    flattened
  }
  val searchSpaceGivenEntryAndIndex = (entries: Seq[EnglishEntry], index: Map[Seq[Option[Phoneme]], Seq[EnglishEntry]]) => {
    entries.flatMap(e => index(e.phonemes)).map(e => s"${e.word} (${Phoneme.toString(e.phonemes)})")
  }
  val searchSpaceGivenPhonemesAndIndex = (phonemes: Seq[Option[Phoneme]], index: Map[Seq[Option[Phoneme]], Seq[EnglishEntry]]) => {
    index(phonemes).map(e => {
      s"${Phoneme.toString(phonemes)}: ${e.word} (${Phoneme.toString(e.phonemes)})"
    })
  }

  var current = searchSpaceGivenEntries(entriesIndexer.enEntries, entriesIndexer.enEntries)
  var numLines = 20

  val show = () => {
    println(current.take(numLines).mkString("\n"))
    current = current.drop(numLines)
  }

  def handleFindByPhonemes(phonemesString: String, index: Map[Seq[Option[Phoneme]], Seq[EnglishEntry]], description: String): Unit = {
    val phonemes = phonemesString.split(" ").map(_.toUpperCase).map(Phoneme.phonemesByCode.get)
    if(phonemes.length < 1 || phonemes.exists(_.isEmpty)) {
      println(s"Illegal phonemes string: $phonemesString")
    } else {
      current = searchSpaceGivenPhonemesAndIndex(phonemes, index)
      println(description)
      show()
    }
  }

  def handleFindByWord(word: String, index: Map[Seq[Option[Phoneme]], Seq[EnglishEntry]], description: String): Unit = {
    entriesIndexer.enEntriesByWord.get(word.toUpperCase) match {
      case Some(entries) =>
        current = searchSpaceGivenEntryAndIndex(entries, index)
        println(description)
        show()
      case None =>
        println(s"$word was not found in the dictionary")
    }
  }

  do {
    val LinesRegex = """l (\d+)""".r
    val MakePunFromAWordAndAnotherRegex = "g (.+)".r
    val FindWordsContainingWordPhonemesRegex = "c (.+)".r
    val FindWordsContainingPhonemesRegex = "cp (.+)".r
    val FindWordsStartingWithWordPhonemesRegex = "s (.+)".r
    val FindWordsStartingWithPhonemesRegex = "sp (.+)".r
    val FindWordsEndingWithWordPhonemesRegex = "e (.+)".r
    val FindWordsEndingWithPhonemesRegex = "ep (.+)".r

    scala.io.StdIn.readLine("Action? ") match {
      case m if m == "m" =>
        show()
      case l if l == "l" =>
        println(Phoneme.allPhonemes.map(p => p.code + " " + p.example).mkString("\n"))
      case LinesRegex(numLinesString) =>
        numLines = numLinesString.toInt
        println(s"Now showing $numLines at a time")
      case FindWordsContainingWordPhonemesRegex(word) =>
        handleFindByWord(word, entriesIndexer.enEntriesByPhonemes, s"Now showing words that contains the sounds in $word")
      case FindWordsContainingPhonemesRegex(phonemesString) =>
        handleFindByPhonemes(phonemesString, entriesIndexer.enEntriesByPhonemes, s"Now showing words that contains the sounds $phonemesString")
      case FindWordsStartingWithWordPhonemesRegex(word) =>
        handleFindByWord(word, entriesIndexer.enEntriesByStartingPhonemes, s"Now showing words starting with words that sound like $word")
      case FindWordsStartingWithPhonemesRegex(phonemesString) =>
        handleFindByPhonemes(phonemesString, entriesIndexer.enEntriesByStartingPhonemes, s"Now showing words starting with sounds $phonemesString")
      case FindWordsEndingWithWordPhonemesRegex(word) =>
        handleFindByWord(word, entriesIndexer.enEntriesByEndingPhonemes, s"Now showing words ending with words that sound like $word")
      case FindWordsEndingWithPhonemesRegex(phonemesString) =>
        handleFindByPhonemes(phonemesString, entriesIndexer.enEntriesByEndingPhonemes, s"Now showing words ending with sounds like $phonemesString")
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
