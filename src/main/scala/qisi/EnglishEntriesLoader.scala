package qisi

import java.io.InputStream

trait EnglishEntriesLoader {
  def enEntries: Seq[EnglishEntry]
}

object EnglishEntriesLoaderImpl extends EnglishEntriesLoader {

  val enEntries: Seq[EnglishEntry] = {
    val enStream : InputStream = getClass.getResourceAsStream("/EnglishCmu/cmudict_SPHINX_40.txt")
    val enLines = scala.io.Source.fromInputStream( enStream ).getLines.toSeq
    enEntriesFromLines(enLines)
  }

  def enEntriesFromLines(enLines: Seq[String]): Seq[EnglishEntry] = {
    val enRegex =  """^([^\t]+)\t([\w ]+)""".r
    val parsedEnEntries = enLines collect { case line @ enRegex(w, p) => ParsedEnglishEntry(w, p.split(" "), line)}
    for {
      entry <- parsedEnEntries
      phonemes <- Phoneme.phonemeOptFromStrings(entry.phonemeStrings)
    } yield EnglishEntry(entry, phonemes)
  }

}
