package qisi

import java.io.InputStream

object Entries {

  private val enRegex =  """^([^\t]+)\t([\w ]+)""".r
  private val chRegex = """^([^\[#]+) \[([^\]]+)\] /(.+)/$""".r

  private lazy val enStream : InputStream = getClass.getResourceAsStream("/EnglishCmu/cmudict_SPHINX_40.txt")
  private lazy val enLines = scala.io.Source.fromInputStream( enStream ).getLines.toSeq
  lazy val parsedEnEntries = enLines collect { case line @ enRegex(w, p) => ParsedEnglishEntry(w, p.split(" "), line)}
  lazy val enEntries = parsedEnEntries map {
    case e =>
      val phonemes = e.phonemeStrings map Phoneme.phonemesByCode.get

      // todo: use .sequence
      EnglishEntry(e, phonemes)
  }
  lazy val enEntriesByWord = enEntries groupBy {_.word}
  lazy val enEntriesByPhonemes = enEntries groupBy {_.phonemes}

  private lazy val chStream : InputStream = getClass.getResourceAsStream("/ChineseCcEdict/cedict_ts.u8")
  private lazy val chLines = scala.io.Source.fromInputStream( chStream ).getLines.toSeq
  lazy val parsedChEntries: Seq[ParsedChineseEntry] = chLines collect {
    case line @ chRegex(word: String, p: String, definition: String) =>
      val pinyinStrings = p.replaceAll("([^a-zA-Z ])", "").replaceAll("  ", " ") split " "
      ParsedChineseEntry(word, pinyinStrings, definition, line)
  }
  lazy val chEntries = parsedChEntries map {
    case e =>
      val phonemeStrings = e.pinyinStrings flatMap ChineseToPhonemeString.translate
      val phonemes = phonemeStrings map Phoneme.phonemesByCode.get
      ChineseEntry(e, phonemes)
  }
  lazy val chEntriesByWord = chEntries groupBy {_.word}
  lazy val chEntriesByPinyin = chEntries groupBy {_.entry.pinyinStrings}
  lazy val chEntriesByPhonemes = chEntries groupBy {_.phonemes}

}

