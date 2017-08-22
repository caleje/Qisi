package qisi

import java.io.InputStream

trait ChineseEntriesLoader {
  def chEntries: Seq[ChineseEntry]
}

object ChineseEntriesLoaderImpl extends ChineseEntriesLoader {
  private val chRegex = """^([^\[#]+) \[([^\]]+)\] /(.+)/$""".r
  private lazy val chStream : InputStream = getClass.getResourceAsStream("/ChineseCcEdict/cedict_ts.u8")
  private lazy val chLines = scala.io.Source.fromInputStream( chStream ).getLines.toSeq
  private lazy val parsedChEntries: Seq[ParsedChineseEntry] = chLines collect {
    case line @ chRegex(word: String, p: String, definition: String) =>
      val pinyinStrings = p.replaceAll("([^a-zA-Z ])", "").replaceAll("  ", " ") split " "
      ParsedChineseEntry(word, pinyinStrings, definition, line)
  }

  lazy val chEntries: Seq[ChineseEntry] = parsedChEntries map ( e => {
    val phonemeStrings = e.pinyinStrings flatMap ChineseToPhonemeString.translate
    val phonemes = phonemeStrings map Phoneme.phonemesByCode.get
    ChineseEntry(e, phonemes)
  })
}
