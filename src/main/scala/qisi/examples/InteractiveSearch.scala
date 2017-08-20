package qisi.examples

import qisi.Entries

object InteractiveSearch extends App {
  val searchSpace = for {
    word1 <- Entries.enEntries
    word2 <- Entries.enEntries
    word1word2 = word1.phonemes ++ word2.phonemes
    wordsWithSamePhonemes <- Entries.enEntriesByPhonemes.get(word1word2)
  } yield (word1.word, word2.word, wordsWithSamePhonemes)

  val viewable = searchSpace.flatMap(r => r._3.map(r3 => (r._1, r._2, r3.word)))
    .filterNot(r => Set("'EM", "'N").contains(r._1))

  var current = viewable
  var numLines = 20

  val show = () => {
    println(current.take(numLines).mkString("\n"))
    current = current.drop(numLines)
  }

  do {
    val LinesRegex = """l(\d+)""".r
    val SkipToNextWordRegex = "n".r
    val SkipBasedOnRegex = "s(.+)".r
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
      case _ @ command =>
        println(s"Did not recognized command $command")
    }
  } while(true)
}
