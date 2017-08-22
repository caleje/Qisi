package qisi

import java.io.InputStream

trait EnglishEntriesLoader {
  def enEntries: Seq[EnglishEntry]
}

object EnglishEntriesLoaderImpl extends EnglishEntriesLoader {

  lazy val enEntries: Seq[EnglishEntry] = {
    val enStream : InputStream = getClass.getResourceAsStream("/EnglishCmu/cmudict_SPHINX_40.txt")
    val enLines = scala.io.Source.fromInputStream( enStream ).getLines.toSeq
    enEntriesFromLines(enLines)
  }

  def enEntriesFromLines(enLines: Seq[String]): Seq[EnglishEntry] = {
    val enRegex =  """^([^\t]+)\t([\w ]+)""".r
    val parsedEnEntries = enLines collect { case line @ enRegex(w, p) => ParsedEnglishEntry(w, p.split(" "), line)}
    parsedEnEntries map( e => {
      val phonemes = e.phonemeStrings map Phoneme.phonemesByCode.get
      EnglishEntry(e, phonemes)
    })
  }

}
