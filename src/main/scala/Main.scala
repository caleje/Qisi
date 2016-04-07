import java.io.InputStream

object Main {

  val enLinePattern =  """([\S.]+)\t([\w ]+)""".r
  def parseEnLinePhonemes(line: String): Option[(Seq[String], String)] = {
    val enLinePattern(word, phonemes) = line
    Some((phonemes.split(" ").toSeq, line))
  }

  val chPhonemesPattern = "\\[([a-z\\d ]+)\\]".r
  def removeTonesBrackets(ph: String): String = ph.filterNot(c=>c=='1'||c=='2'||c=='3'||c=='4'||c=='['||c==']')
  def parseCnLinePhonemes(line: String): Option[(Seq[String], String)] = {
    chPhonemesPattern.findFirstIn(line).flatMap(ph => Some((removeTonesBrackets(ph).split(" ").toSeq, line)))
  }
  def main(args: Array[String]): Unit = {

    val enStream : InputStream = getClass.getResourceAsStream("/EnglishCmu/cmudict_SPHINX_40.txt")
    val enLines = scala.io.Source.fromInputStream( enStream ).getLines.toSeq
    val enPhonemeLines = enLines.flatMap(parseEnLinePhonemes).groupBy(_._1)
    val enHomophones = enPhonemeLines.filter(_._2.length > 1)

    println(s"English #words: ${enLines.length}, #homophones: ${enHomophones.keys.toSeq.length}")

    val chStream : InputStream = getClass.getResourceAsStream("/ChineseCcEdict/cedict_ts.u8")
    val chLines = scala.io.Source.fromInputStream( chStream ).getLines.toSeq
    val chPhonemeLines = chLines.flatMap(parseCnLinePhonemes).groupBy(_._1)
    val chHomophones = chPhonemeLines.filter(_._2.length > 1)

    println(s"Chinese #words: ${chLines.length}, #homophones: ${chHomophones.keys.toSeq.length}")

  }

}
