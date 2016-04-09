package qisi

import java.io.InputStream
import qisi.Types._

object Entries {

  private val enRegex =  """^([^\t]+)\t([\w ]+)""".r
  private val chRegex = """^([^\[#]+) \[([^\]]+)\] /(.+)/$""".r

  private val enStream : InputStream = getClass.getResourceAsStream("/EnglishCmu/cmudict_SPHINX_40.txt")
  private val enLines = scala.io.Source.fromInputStream( enStream ).getLines.toSeq
  val enEntries = enLines collect { case line @ enRegex(w, p) => EnglishEntry(w, p.split(" "), line)}
  val enEntriesByWord = enEntries groupBy {_.word}
  val enEntriesByPhonemes = enEntries groupBy {_.phonemes}

  private val chStream : InputStream = getClass.getResourceAsStream("/ChineseCcEdict/cedict_ts.u8")
  private val chLines = scala.io.Source.fromInputStream( chStream ).getLines.toSeq
  val chEntries = chLines collect { case line @ chRegex(w, p, d) => ChineseEntry(w, p, d, line) }
  val chEntriesByWord = chEntries groupBy {_.word}
  val chEntriesByPinyin = chEntries groupBy {_.pinyin}
  val chEntriesByPhonemes = chEntries groupBy {_.phonemes}

}

