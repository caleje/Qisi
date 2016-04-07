object ChineseToEnglish extends PhonemeTranslate {

  private val pinyinInitialsToPhonemes = Map(
  "b"   -> Array("B"), 		//[p]	spit	unaspirated p, as in spit
  "p"   -> Array("P"), 		//[pʰ]	pay	strongly aspirated p, as in pit
  "m"   -> Array("M"), 		//[m]	may	as in English mummy
  "f"   -> Array("F"), 		//[f]	fair	as in English fun
  "d"   -> Array("D"), 		//[t]	stop	unaspirated t, as in stop
  "t"   -> Array("T"), 		//[tʰ]	take	strongly aspirated t, as in top
  "n"   -> Array("N"), 		//[n]	nay	as in English nit
  "l"   -> Array("L"), 		//[l]	lay	as in English love
  "g"   -> Array("G"), 		//[k]	skill	unaspirated k, as in skill
  "k"   -> Array("K"), 		//[kʰ]	kay	strongly aspirated k, as in kill
  "h"   -> Array("H"), 		//[x]	loch	roughly like the Scots ch. English h as in hay or, more closely in some American dialects, hero is an acceptable approximation. The best way to produce this sound is by very slowly making a "k" sound, pausing at the point where there is just restricted air flowing over theback of your tongue (after the release at the beginning of a "k")
  "j"   -> Array("JH"), 		//[tɕ]	churchyard	No equivalent in English, but similar to an unaspirated "-chy-" sound when said
    // quickly. Like q, but unaspirated. Is similar to the English name of the letter G. Not the s in Asia, despite the common English pronunciation of "Beijing". The sequence "ji" word-initially is the same as the Japanese pronunciation of じ(ジ) ji.
  "q"   -> Array("CH"), 		//[tɕʰ]	punch yourself	No equivalent in English. Like punch yourself, with the lips spread wide
    // with ee. Curl the tip of the tongue downwards to stick it at the back of the teeth and strongly aspirate. The sequence "qi" word-initially is the same as the Japanese pronunciation of ち(チ) chi.
  "x"   -> Array("SH"), 		//[ɕ]	push yourself	No equivalent in English. Like -sh y-, with the lips spread and the tip of your tongue curled downwards and stuck to the back of teeth when you say ee. The sequence "xi" is the same as the Japanese pronunciation of し(シ) shi.
  "zh"   -> Array("JH"),   // 	[tʂ]	junk	Rather like ch (a sound between choke, joke, true, and drew, tongue tip curled
    // moreupwards).Voiced in a toneless syllable.
  "ch"   -> Array("CH"),   //	[tʂʰ]	church	as in chin, but with a flat tongue; very similar to nurture in American English,but
    // strongly aspirated.
  "sh"   -> Array("SH"),   //	[ʂ]	shirt	as in shoe, but with a flat tongue; very similar to marsh in American English
  "r"   -> Array("R"), 		//[ɻ]	ray	Similar to the English r in reduce, but with a flat tongue and lightly fricated.
  "z"   -> Array("Z"), 		//[ts]	reads	unaspirated c, similar to something between suds and cats; as in suds in a toneless
    // syllable
  "c"   -> Array("T", "S"), 		//[tsʰ]	hats	like the English ts in cats, but strongly aspirated, very similar to the Czech,
    // Polish and Slovak c.
  "s"   -> Array("S"), 		//[s]	say	as in sun
  "w"   -> Array("W"), 		//[w]	way	as in water.*
  "y"   -> Array("Y") 		//[j], [ɥ]	yea	as in yes. Before a u, pronounce it with rounded lips.*
    )

  private val pinyinFinalsToPhonemes = Map(

      // todo: figure out how to deal w/ this case
      // "i" -> "??", // 	   [ɹ̩], [ɻ̩]	(n/a)	-i is a buzzed continuation of the consonant following z-, c-, s-, zh-, ch-, sh- or r-.(In all other cases, -i has the sound of bee; this is listed below.)

      "a"   -> Array("AA") , // 		[a]	a	like English father, but a bit more fronted
      "e"   -> Array("AH") , // 		[ɤ] ( listen)	e	a back, unrounded vowel (similar to English "duh", but not as open)
      "ai"   -> Array("AY") , // 		[æi̯]	ai	like English eye, but a bit lighter
      "ei"   -> Array("EY") , // 		[ei̯]	ei	as in hey
      "ao"   -> Array("AW") , // 		[ɑu̯]	ao	approximately as in cow; the a is much more audible than the o
      "ou"   -> Array("OW") , // 		[ou̯]	ou	as in so
      "an"   -> Array("AE", "N") , // 		[æn]	an	like British English ban, but more central
      "en"   -> Array("UH", "N") , // 		[ən]	en	as in taken
      "ang"   -> Array("AH", "NG") , // 		[ɑŋ]	ang	as in Austrian Angst.
      "eng"   -> Array("UH", "NG") , // 		[əŋ]	eng	like e in en above but with ng appended
      "ong"   -> Array("AO", "NG") , // 		[ʊŋ]	(n/a)	starts with the vowel sound in book and ends with the velar nasal sound in sing
      "er"   -> Array("AA", "R") , // 		[ɐɚ̯]	er	similar to the sound in bar in American English
      "i"   -> Array("IY") , // 		[i]	yi	like English bee
      "ia"   -> Array("Y", "AA") , // 		[i̯a]	ya	as i + a; like English yard
      "ie"   -> Array("Y", "EH") , // 		[i̯e]	ye	as i + ê; but is very short; e (pronounced like ê) is pronounced longer and carries the main stress (similar to the initial sound in yet)
      "iao"   -> Array("Y", "AW") , // 		[i̯ɑu̯]	yao	as i + ao
      "iu"   -> Array("Y", "UW") , // 		[i̯ou̯]	you	as i + ou
      "ian"   -> Array("Y", "EH", "N") , // 		[i̯ɛn]	yan	as i + an; like English yen
      "in"   -> Array("IH", "N") , // 		[in]	yin	as i + n
      "iang"   -> Array("Y", "AH", "NG") , // 		[i̯ɑŋ]	yang	as i + ang
      "ing"   -> Array("IH", "NG") , // 		[i̯əŋ]	ying	as i + eng
      "iong"   -> Array("Y", "AO", "NG") , // 		[i̯ʊŋ]	yong	as i + ong
      "u"   -> Array("W", "UW") , // 		[u]	wu	like English oo
      "ua"   -> Array("W", "UW", "AA") , // 		[u̯a]	wa	as u + a
      "uo"   -> Array("WU", "OW") , // 		[
      "o"   -> Array("OW") , // 		[
      "uai"   -> Array("WU", "AY") , // 		[u̯æi̯]	wai	as u + ai, as in English why
      "ui"   -> Array("W", "EY") , // 		[u̯ei̯]	wei	as u + ei
      "uan"   -> Array("W AE N ") , // 		[u̯æn]	wan	as u + an
      "un"   -> Array("W", "AH", "N") , // 		[u̯ən]	wen	as u + en; as in English won
      "uang"   -> Array("W", "AH", "NG") , // 		[u̯ɑŋ]	wang	as u + ang
      "ueng"   -> Array("W", "UH", "NG") , // 		[u̯əŋ]	weng	as u + eng
      "u"   -> Array("Y", "UW") , // 		[
      "ü"   -> Array("Y", "UW") , // 		[y] ( listen)	yu	as in German über or French lune.
      "ue"   -> Array("Y", "UW", "EH") , // 		[
      "üe"   -> Array("Y", "UW", "EH") , // 		[	[y̯e]	yue	as ü + ê; the ü is short and light
      "uan"   -> Array("Y UW AE N") , // 		[y̯ɛn]	yuan	as ü + an
      "un"   -> Array("Y", "UW", "N")  // 		[yn]	yun	as ü + n

      // These are "interjections".  Not sure if should really match
      /*"ê"   -> Array("EH") , // 		[ɛ]	(n/a)	as in bet
      "o" -> "??" , // 		[ɔ]	(n/a)	approximately as in British English office; the lips are much more rounded
      "io" -> "??"  // 		[i̯ɔ]	yo	as i + plain continental[clarification needed] "o"*/
    )

   private val pinyinRegex = "(?<init>ch|zh|sh|r|c|b|d|g|f|h|k|j|m|l|n|q|p|s|t|w|y|x|z|)(?<fin>(?:(?<=ch)uang|(?<=ch)ang|(?<=ch)eng|(?<=ch)ong|(?<=ch)uai|(?<=ch)uan|(?<=ch)ai|(?<=ch)an|(?<=ch)ao|(?<=ch)en|(?<=ch)ou|(?<=ch)ua|(?<=ch)ui|(?<=ch)un|(?<=ch)uo|(?<=ch)a|(?<=ch)e|(?<=ch)i|(?<=ch)u)|(?:(?<=zh)uang|(?<=zh)ang|(?<=zh)eng|(?<=zh)ong|(?<=zh)uai|(?<=zh)uan|(?<=zh)ai|(?<=zh)an|(?<=zh)ao|(?<=zh)ei|(?<=zh)en|(?<=zh)ou|(?<=zh)ua|(?<=zh)ui|(?<=zh)un|(?<=zh)uo|(?<=zh)a|(?<=zh)e|(?<=zh)i|(?<=zh)u)|(?:(?<=sh)uang|(?<=sh)ang|(?<=sh)eng|(?<=sh)uai|(?<=sh)uan|(?<=sh)ai|(?<=sh)an|(?<=sh)ao|(?<=sh)ei|(?<=sh)en|(?<=sh)ou|(?<=sh)ua|(?<=sh)ui|(?<=sh)un|(?<=sh)uo|(?<=sh)a|(?<=sh)e|(?<=sh)i|(?<=sh)u)|(?:(?<=c)ang|(?<=c)eng|(?<=c)ong|(?<=c)uan|(?<=c)ai|(?<=c)an|(?<=c)ao|(?<=c)en|(?<=c)ou|(?<=c)ui|(?<=c)un|(?<=c)uo|(?<=c)a|(?<=c)e|(?<=c)i|(?<=c)u)|(?:(?<=b)ang|(?<=b)eng|(?<=b)ian|(?<=b)iao|(?<=b)ing|(?<=b)ai|(?<=b)an|(?<=b)ao|(?<=b)ei|(?<=b)en|(?<=b)ie|(?<=b)in|(?<=b)a|(?<=b)i|(?<=b)o|(?<=b)u)|(?:(?<=d)ang|(?<=d)eng|(?<=d)ian|(?<=d)iao|(?<=d)ing|(?<=d)ong|(?<=d)uan|(?<=d)ai|(?<=d)an|(?<=d)ao|(?<=d)ei|(?<=d)en|(?<=d)ia|(?<=d)ie|(?<=d)iu|(?<=d)ou|(?<=d)ui|(?<=d)un|(?<=d)uo|(?<=d)a|(?<=d)e|(?<=d)i|(?<=d)u)|(?:(?<=g)uang|(?<=g)ang|(?<=g)eng|(?<=g)ong|(?<=g)uai|(?<=g)uan|(?<=g)ai|(?<=g)an|(?<=g)ao|(?<=g)ei|(?<=g)en|(?<=g)ou|(?<=g)ua|(?<=g)ui|(?<=g)un|(?<=g)uo|(?<=g)a|(?<=g)e|(?<=g)u)|(?:(?<=f)ang|(?<=f)eng|(?<=f)iao|(?<=f)an|(?<=f)ei|(?<=f)en|(?<=f)ou|(?<=f)a|(?<=f)o|(?<=f)u)|(?:(?<!sh|ch|zh)(?<=h)uang|(?<!sh|ch|zh)(?<=h)ang|(?<!sh|ch|zh)(?<=h)eng|(?<!sh|ch|zh)(?<=h)ong|(?<!sh|ch|zh)(?<=h)uai|(?<!sh|ch|zh)(?<=h)uan|(?<!sh|ch|zh)(?<=h)ai|(?<!sh|ch|zh)(?<=h)an|(?<!sh|ch|zh)(?<=h)ao|(?<!sh|ch|zh)(?<=h)ei|(?<!sh|ch|zh)(?<=h)en|(?<!sh|ch|zh)(?<=h)ou|(?<!sh|ch|zh)(?<=h)ua|(?<!sh|ch|zh)(?<=h)ui|(?<!sh|ch|zh)(?<=h)un|(?<!sh|ch|zh)(?<=h)uo|(?<!sh|ch|zh)(?<=h)a|(?<!sh|ch|zh)(?<=h)e|(?<!sh|ch|zh)(?<=h)u)|(?:(?<=k)uang|(?<=k)ang|(?<=k)eng|(?<=k)ong|(?<=k)uai|(?<=k)uan|(?<=k)ai|(?<=k)an|(?<=k)ao|(?<=k)en|(?<=k)ou|(?<=k)ua|(?<=k)ui|(?<=k)un|(?<=k)uo|(?<=k)a|(?<=k)e|(?<=k)u)|(?:(?<=j)iang|(?<=j)iong|(?<=j)ian|(?<=j)iao|(?<=j)ing|(?<=j)üan|(?<=j)ia|(?<=j)ie|(?<=j)in|(?<=j)iu|(?<=j)üe|(?<=j)ün|(?<=j)i|(?<=j)ü)|(?:(?<=m)ang|(?<=m)eng|(?<=m)ian|(?<=m)iao|(?<=m)ing|(?<=m)ai|(?<=m)an|(?<=m)ao|(?<=m)ei|(?<=m)en|(?<=m)ie|(?<=m)in|(?<=m)iu|(?<=m)ou|(?<=m)a|(?<=m)e|(?<=m)i|(?<=m)o|(?<=m)u)|(?:(?<=l)iang|(?<=l)ang|(?<=l)eng|(?<=l)ian|(?<=l)iao|(?<=l)ing|(?<=l)ong|(?<=l)uan|(?<=l)ai|(?<=l)an|(?<=l)ao|(?<=l)ei|(?<=l)ia|(?<=l)ie|(?<=l)in|(?<=l)iu|(?<=l)ou|(?<=l)un|(?<=l)uo|(?<=l)üe|(?<=l)a|(?<=l)e|(?<=l)i|(?<=l)o|(?<=l)u|(?<=l)ü)|(?:(?<=n)iang|(?<=n)ang|(?<=n)eng|(?<=n)ian|(?<=n)iao|(?<=n)ing|(?<=n)ong|(?<=n)uan|(?<=n)ai|(?<=n)an|(?<=n)ao|(?<=n)ei|(?<=n)en|(?<=n)ie|(?<=n)in|(?<=n)iu|(?<=n)ou|(?<=n)un|(?<=n)uo|(?<=n)üe|(?<=n)a|(?<=n)e|(?<=n)i|(?<=n)u|(?<=n)ü)|(?:(?<=q)iang|(?<=q)iong|(?<=q)ian|(?<=q)iao|(?<=q)ing|(?<=q)üan|(?<=q)ia|(?<=q)ie|(?<=q)in|(?<=q)iu|(?<=q)üe|(?<=q)ün|(?<=q)i|(?<=q)ü)|(?:(?<=p)ang|(?<=p)eng|(?<=p)ian|(?<=p)iao|(?<=p)ing|(?<=p)ai|(?<=p)an|(?<=p)ao|(?<=p)ei|(?<=p)en|(?<=p)ie|(?<=p)in|(?<=p)ou|(?<=p)a|(?<=p)i|(?<=p)o|(?<=p)u)|(?:(?<=s)ang|(?<=s)eng|(?<=s)ong|(?<=s)uan|(?<=s)ai|(?<=s)an|(?<=s)ao|(?<=s)en|(?<=s)ou|(?<=s)ui|(?<=s)un|(?<=s)uo|(?<=s)a|(?<=s)e|(?<=s)i|(?<=s)u)|(?:(?<=r)ang|(?<=r)eng|(?<=r)ong|(?<=r)uan|(?<=r)an|(?<=r)ao|(?<=r)en|(?<=r)ou|(?<=r)ua|(?<=r)ui|(?<=r)un|(?<=r)uo|(?<=r)e|(?<=r)i|(?<=r)u)|(?:(?<=t)ang|(?<=t)eng|(?<=t)ian|(?<=t)iao|(?<=t)ing|(?<=t)ong|(?<=t)uan|(?<=t)ai|(?<=t)an|(?<=t)ao|(?<=t)ei|(?<=t)ie|(?<=t)ou|(?<=t)ui|(?<=t)un|(?<=t)uo|(?<=t)a|(?<=t)e|(?<=t)i|(?<=t)u)|(?:(?<=w)ang|(?<=w)eng|(?<=w)ai|(?<=w)an|(?<=w)ei|(?<=w)en|(?<=w)a|(?<=w)o|(?<=w)u)|(?:(?<=y)ang|(?<=y)ing|(?<=y)ong|(?<=y)uan|(?<=y)ai|(?<=y)an|(?<=y)ao|(?<=y)in|(?<=y)ou|(?<=y)ue|(?<=y)un|(?<=y)a|(?<=y)e|(?<=y)e|(?<=y)i|(?<=y)o|(?<=y)u)|(?:(?<=x)iang|(?<=x)iong|(?<=x)ian|(?<=x)iao|(?<=x)ing|(?<=x)üan|(?<=x)ia|(?<=x)ie|(?<=x)in|(?<=x)iu|(?<=x)üe|(?<=x)ün|(?<=x)i|(?<=x)ü)|(?:(?<=z)ang|(?<=z)eng|(?<=z)ong|(?<=z)uan|(?<=z)ai|(?<=z)an|(?<=z)ao|(?<=z)ei|(?<=z)en|(?<=z)ou|(?<=z)ui|(?<=z)un|(?<=z)uo|(?<=z)a|(?<=z)e|(?<=z)i|(?<=z)u)|(?:(?<!r|c|b|d|g|f|h|k|j|m|l|n|q|p|s|t|w|y|x|z)a|(?<!r|c|b|d|g|f|h|k|j|m|l|n|q|p|s|t|w|y|x|z)ai|(?<!r|c|b|d|g|f|h|k|j|m|l|n|q|p|s|t|w|y|x|z)an|(?<!r|c|b|d|g|f|h|k|j|m|l|n|q|p|s|t|w|y|x|z)ang|(?<!r|c|b|d|g|f|h|k|j|m|l|n|q|p|s|t|w|y|x|z)ao|(?<!r|c|b|d|g|f|h|k|j|m|l|n|q|p|s|t|w|y|x|z)e|(?<!r|c|b|d|g|f|h|k|j|m|l|n|q|p|s|t|w|y|x|z)ei|(?<!r|c|b|d|g|f|h|k|j|m|l|n|q|p|s|t|w|y|x|z)en|(?<!r|c|b|d|g|f|h|k|j|m|l|n|q|p|s|t|w|y|x|z)eng|(?<!r|c|b|d|g|f|h|k|j|m|l|n|q|p|s|t|w|y|x|z)er|(?<!r|c|b|d|g|f|h|k|j|m|l|n|q|p|s|t|w|y|x|z)o|(?<!r|c|b|d|g|f|h|k|j|m|l|n|q|p|s|t|w|y|x|z)ou))".r

  def translate(s: String): Seq[String] = {
    val pinyinRegex(init, fin) =  s
    val initEnglish = if(!init.isEmpty) pinyinInitialsToPhonemes(init) else Array[String]()
    val finalEnglish = pinyinFinalsToPhonemes(fin)
    initEnglish ++ finalEnglish
  }

}
