package qisi

import java.io.InputStream
import qisi.Types._

object Entries {

  private val enRegex =  """^([^\t]+)\t([\w ]+)""".r
  private val chRegex = """^([^\[#]+) \[([^\]]+)\] /(.+)/$""".r

  private val enStream : InputStream = getClass.getResourceAsStream("/EnglishCmu/cmudict_SPHINX_40.txt")
  private val enLines = scala.io.Source.fromInputStream( enStream ).getLines.toIndexedSeq
  lazy val enEntries = enLines collect { case line @ enRegex(w, p) => EnglishEntry(w, p.split(" "), line)}
  lazy val enEntriesByWord = enEntries groupBy {_.word}
  lazy val enEntriesByPhonemes = enEntries groupBy {_.phonemes}

  lazy private val chStream : InputStream = getClass.getResourceAsStream("/ChineseCcEdict/cedict_ts.u8")
  lazy private val chLines = scala.io.Source.fromInputStream( chStream ).getLines.toIndexedSeq
  lazy val chEntries = chLines collect { case line @ chRegex(w, p, d) => ChineseEntry(w, p, d, line) }
  lazy val chEntriesByWord = chEntries groupBy {_.word}
  lazy val chEntriesByPinyin = chEntries groupBy {_.pinyin}
  lazy val chEntriesByPhonemes = chEntries groupBy {_.phonemes}

}

