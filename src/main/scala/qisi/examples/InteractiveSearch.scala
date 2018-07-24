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
  val searchSpaceGivenEntryAndIndex = (entries: Seq[EnglishEntry], index: Map[Seq[Phoneme], Seq[EnglishEntry]]) => {
    entries.flatMap(e => index.getOrElse(e.phonemes, Seq.empty)).map(e => s"${e.word} (${Phoneme.toString(e.phonemes)})")
  }
  def searchSpaceGivenPhonemesAndIndex(phonemes: Seq[Phoneme], index: Map[Seq[Phoneme], Seq[EnglishEntry]]): Seq[String] = {
    index.getOrElse(phonemes, Seq.empty).map(e => {
      s"${e.word} (${Phoneme.toString(e.phonemes)})"
    })
  }

  var current = searchSpaceGivenEntries(entriesIndexer.enEntries, entriesIndexer.enEntries)
  var numLines = 20

  val show = () => {
    println(current.take(numLines).mkString("\n"))
    current = current.drop(numLines)
  }

  def handleFindByPhonemes(phonemesString: String, index: Map[Seq[Phoneme], Seq[EnglishEntry]], description: String): Unit = {
    val phonemesOpt = phonemesString.split(" ").map(_.toUpperCase).map(Phoneme.phonemesByCode.get)
    if(phonemesOpt.length < 1 || phonemesOpt.exists(_.isEmpty)) {
      println(s"Illegal phonemes string: $phonemesString")
    } else {
      val phonemes = phonemesOpt.map(_.get)
      current = searchSpaceGivenPhonemesAndIndex(phonemes, index)
      println(description)
      show()
    }
  }

  def handleFindWord(word: String): Unit = {
    entriesIndexer.enEntriesByWord.get(word.toUpperCase) match {
      case Some(entries) =>
        val entriesString = entries.map(_.toStringDetailed).mkString("\n")
        println(entriesString)
      case None =>
        println(s"$word was not found in the dictionary")
    }
  }

  def handleFindByWord(word: String, index: Map[Seq[Phoneme], Seq[EnglishEntry]], description: String): Unit = {
    entriesIndexer.enEntriesByWord.get(word.toUpperCase) match {
      case Some(entries) =>
        current = searchSpaceGivenEntryAndIndex(entries, index)
        println(description)
        show()
      case None =>
        println(s"$word was not found in the dictionary")
    }
  }

  def handleFindNearbyWordsByWord(word: String, description: String): Unit = {
    entriesIndexer.enEntriesByWord.get(word.toUpperCase) match {
      case Some(entries) =>
        val nearbyWordPhonemes = entries.flatMap(e => NearbyWordsGenerator.generate(e.phonemes))
        current = nearbyWordPhonemes.flatMap(wordPhonemes => {
          val distance = EditDistance.editDist(entries.head.phonemes, wordPhonemes)
          val wordDescriptions = searchSpaceGivenPhonemesAndIndex(wordPhonemes, entriesIndexer.enEntriesByPhonemes)
          wordDescriptions.map(wordDescription => s"$wordDescription, Distance: $distance")
        })
        println(description)
        show()
      case None =>
        println(s"$word was not found in the dictionary")
    }
  }

  def handleComboFindByword(word: String): Unit = {
    entriesIndexer.enEntriesByWord.get(word.toUpperCase) match {
      case Some(entries) =>
        val nearbyWordPhonemes = entries.flatMap(e => NearbyWordsGenerator.generate(e.phonemes))
        val nearby = nearbyWordPhonemes.flatMap(wordPhonemes => {
          val distance = EditDistance.editDist(entries.head.phonemes, wordPhonemes)
          val wordDescriptions = searchSpaceGivenPhonemesAndIndex(wordPhonemes, entriesIndexer.enEntriesByPhonemes)
          wordDescriptions.map(wordDescription => s"$wordDescription, Distance: $distance")
        })
        val containing = searchSpaceGivenEntryAndIndex(entries, entriesIndexer.enEntriesByPhonemes)
        val combo = searchSpaceGivenEntries(entries, entriesIndexer.enEntries) ++ searchSpaceGivenEntries(entriesIndexer.enEntries, entries)
        current = (nearby ++ containing ++ combo).distinct
        println("Finding words using all strategies")
        show()
      case None =>
        println(s"$word was not found in the dictionary")
    }
  }

  def handleNg(): Unit = {
    val ihng = Phoneme.phonemeOptFromStrings(Seq("IH", "NG")).get
    val wordsEndingWithIhng = entriesIndexer.enEntriesByEndingPhonemes(ihng)

    val bases = wordsEndingWithIhng.map(word => {
      val phonemes = word.phonemes
      val nonIhng = phonemes.take(phonemes.length - 2)
      nonIhng
    })

    val ahn = Phoneme.phonemeOptFromStrings(Seq("AH", "N")).get
    val basePlusAhn = bases.map(_ ++ ahn)
    val wordsContainingBasePlusAhns = basePlusAhn.flatMap(basePlusAhn => {
      val entries = entriesIndexer.enEntriesByPhonemes.get(basePlusAhn)
      entries match {
        case Some(actualEntries) => for {
          basePlusAhn <- actualEntries
          basePlusIhng <- entriesIndexer.enEntriesByPhonemes(basePlusAhn.phonemes.dropRight(2) ++ ihng)
        } yield s"${basePlusAhn.word} ${basePlusIhng.word}"
        case None => Seq.empty[String]
      }
    })
    current = wordsContainingBasePlusAhns
    println("Now showing [IH NG]/[AH N] puns")
    show()
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
    val FindWordRegex = "w (.+)".r
    val FindNearbyWords = "n (.+)".r
    val FindNearbyContainingAndComboWordsRegex = "a (.+)".r

    scala.io.StdIn.readLine("> ") match {
      case h if h == "h" =>
        println(
          """
            |h             help
            |m             next page
            |l             list phonemes
            |w [word]      find word and display information
            |g [word]      make pun from word and other words
            |c [word]      find words containing the phonemes of word
            |e [word]      find words ending with the phonemes of word
            |s [word]      find words starting with the phonemes of word
            |n [word]      find words that are off by one phoneme (insert, remove, or patch)
            |a [word]      combination of c, g, and n
            |cp [phonemes] find words containing phonemes
            |ep [phonemes] find words ending with phonemes
            |sp [phonemes] find words starting with phonemes
            |ng            find words [IH NG] => [AH N] puns
          """.stripMargin)
      case m if m == "m" =>
        show()
      case l if l == "l" =>
        println(Phoneme.allPhonemes.map(p => p.code + " " + p.example).mkString("\n"))
      case FindWordRegex(word) =>
        handleFindWord(word)
      case LinesRegex(numLinesString) =>
        numLines = numLinesString.toInt
        println(s"Now showing $numLines at a time")
      case FindWordsContainingWordPhonemesRegex(word) =>
        handleFindByWord(word, entriesIndexer.enEntriesBySubsequencePhonemes, s"Now showing words that contains the sounds in $word")
      case FindWordsContainingPhonemesRegex(phonemesString) =>
        handleFindByPhonemes(phonemesString, entriesIndexer.enEntriesBySubsequencePhonemes, s"Now showing words that contains the sounds $phonemesString")
      case FindWordsStartingWithWordPhonemesRegex(word) =>
        handleFindByWord(word, entriesIndexer.enEntriesByStartingPhonemes, s"Now showing words starting with words that sound like $word")
      case FindWordsStartingWithPhonemesRegex(phonemesString) =>
        handleFindByPhonemes(phonemesString, entriesIndexer.enEntriesByStartingPhonemes, s"Now showing words starting with sounds $phonemesString")
      case FindWordsEndingWithWordPhonemesRegex(word) =>
        handleFindByWord(word, entriesIndexer.enEntriesByEndingPhonemes, s"Now showing words ending with words that sound like $word")
      case FindWordsEndingWithPhonemesRegex(phonemesString) =>
        handleFindByPhonemes(phonemesString, entriesIndexer.enEntriesByEndingPhonemes, s"Now showing words ending with sounds like $phonemesString")
      case FindNearbyWords(word) =>
        handleFindNearbyWordsByWord(word, s"Now showing words that are off by one phoneme from $word")
      case FindNearbyContainingAndComboWordsRegex(word) =>
        handleComboFindByword(word)
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
      case ng if ng == "ng" =>
        handleNg()
      case _ @ command =>
        println(s"Did not recognized command $command")
    }
  } while(true)
}
