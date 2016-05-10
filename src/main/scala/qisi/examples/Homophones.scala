package qisi.examples

import qisi.{Entries, Translation}

object Homophones {

  def main(args: Array[String]): Unit = {

    scala.io.StdIn.readLine("Press enter to start")
    val enEntries = Entries.enEntries
    val chEntries = Entries.chEntries

    val enEntriesByPhoneme = enEntries groupBy(_.phonemes)
    val enHomophones = enEntriesByPhoneme.filter(_._2.length > 1)
    val chEntriesByPhoneme = chEntries.groupBy(_.phonemes)
    val chHomophones = chEntriesByPhoneme.filter(_._2.length > 1)

    println(s"English #words: ${enEntries.length}, #homophones: ${enHomophones.keys.toSeq.length}")
    println(s"Chinese #words: ${chEntries.length}, #homophones: ${chHomophones.keys.toSeq.length}")

    val sentence = "head shoulder knee and toes knees and toes"
    println(sentence)
    println(Translation.EnglishToPhoneme(sentence))

    val enPhonemes = qisi.Entries.enEntriesByPhonemes.keys.toSet
    val chPhonemes = qisi.Entries.chEntriesByPhonemes.keys.toSet
    val enWithChPhonemes = enPhonemes.intersect(chPhonemes)
    val enWithChPhonemesWords = for {
      phoneme <- enWithChPhonemes.take(100)
      enEntry <- qisi.Entries.enEntriesByPhonemes(phoneme)
      chEntry <- qisi.Entries.chEntriesByPhonemes(phoneme)
    } yield (enEntry.word, enEntry.phonemes, chEntry.word, chEntry.entry.pinyinStrings, chEntry.entry.definition)

    println("First 100 ")
    enWithChPhonemesWords.foreach( x => println(s"En: ${x._1}, Pronounce: ${x._2}, Ch: ${x._3}, ChPinyin: ${x._4}, " +
      s"ChDefn: ${x._5}"))

  }

}
