package qisi

import java.io.InputStream
import qisi.Types._

object Entries {

  private val enPattern =  """^([^\t]+)\t([\w ]+)""".r
  private val chPhonemesPattern = """^([^\[#]+) \[([^\]]+)\] /(.+)/$""".r

  private val enStream : InputStream = getClass.getResourceAsStream("/EnglishCmu/cmudict_SPHINX_40.txt")
  private val enLines = scala.io.Source.fromInputStream( enStream ).getLines.toSeq
  val enEntries = enLines collect { case line @ enPattern(w, p) => EnglishEntry(w, p.split(" "), line)}
  val enEntriesByWord = enEntries groupBy {_.word}

  private val chStream : InputStream = getClass.getResourceAsStream("/ChineseCcEdict/cedict_ts.u8")
  private val chLines = scala.io.Source.fromInputStream( chStream ).getLines.toSeq
  val chEntries = chLines collect {case line @ chPhonemesPattern(w, p, d) => ChineseEntry(w, p, d, line)}
  val chEntriesByWord = chEntries groupBy {_.word}
  val chEntriesByPinyin = chEntries groupBy {_.pinyin}

}

