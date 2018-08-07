# Useful links
* https://en.wikipedia.org/wiki/Pinyin_table
* https://en.wikipedia.org/wiki/Pinyin
* http://stackoverflow.com/questions/17156967/optimizing-a-regular-expression-to-parse-chinese-pinyin
   

# Regex
^([^\s]+ )+\[([a-zA-Z0-9 ]+)\] /([^/]+)/
^(?<word>[^\[#]+) \[(?<pinyin>[a-zA-Z0-9 ]+)\]
^(?<word>[^\[#]+) \[(?<pinyin>[^\] ]+)\] /(?<definition>.+)/$
^(?<word>[^\[#]+) \[(?<pinyin>[^\]]+)\] /(?<definition>.+)/$

val chPhonemesPattern = "^([^\\[#]+) \\[([^\\]]+)\\] /(.+)/$".r
  def removeTonesBrackets(ph: String): String = ph.filterNot(c=>c=='1'||c=='2'||c=='3'||c=='4'||c=='['||c==']')
  def parseCnLinePhonemes(line: String): Option[ChineseEntry] = {
    line match {
      case chPhonemesPattern(word, pinyin, definition) => Some(ChineseEntry(word, pinyin, definition, line))
      case _ => None
    }
  }

A A [A] /(slang) (Tw) to steal/
AA制 AA制 [A A zhi4] /to split the billto go Dutch/
AB制 AB制 [A B zhi4] /to split the bill (where the male counterpart foots the larger portion of the sum)/(theater) a system where two 
P民 P民 [P min2] /(slang) shitizen/commoner/hoi polloi/
Q Q [Q] /cute (loanword)/(of food) having a pleasant chewiness (like mochi, tapioca pearls, taro balls etc - foods with a springy or gel-like mouthfeel)/
T字帳 T字帐 [T zi4 zhang4] /T-account (accounting)/
T字褲 T字裤 [T zi4 ku4] /thong (underwear)/
T恤 T恤 [T xu4] /T-shirt/
T裇 T裇 [T xu1] /T-shirt/
〧 〧 [qi1] /numeral 7 in Suzhou numeral system 蘇州碼子|苏州码子[Su1 zhou1 ma3 zi5]/
〨 〨 [ba1] /numeral 8 in Suzhou numeral system 蘇州碼子|苏州码子[Su1 zhou1 ma3 zi5]/
〩 〩 [jiu3] /numeral 9 in Suzhou numeral system 蘇州碼子|苏州码子[Su1 zhou1 ma3 zi5]/
㐄 㐄 [kua4] /component in Chinese characters, mirror image of 夂[zhi3]/



# Future ideas
* Create command line interface that takes a sentence and returns the sentence and an annotation of each word that has a chinese pun
* Deal with dirty data
* Figure out how to handle conversion to phonemes better
* Search-ability needs to be more flexible than just map ... need wildcards
* Can have a Chinese phoneme map to more than one English phoneme, e.g. chinese("an") -> english("AE", "AO").  And vice-versa.
* What to do when there's no exact match
* English words with multiple pronounciations?
* Deal with Chinese -i final which depends on what it follows, e.g. zhi vs qi
* Use WordNet to determine meaning of words and identify cognates (since those are less interesting)
* Computational Humor: https://en.wikipedia.org/wiki/Computational_humor
** Apparently there is a "That's what she said" joke detector
* Mash-up with twitter feed
** Take a tweet -- take each word and see if there's a cross-language pun in each word of the tweet
* Type/implicit to make "english sentence" toPhenomes possible.  Detects that english and then translates
* Spoonerism = sentence where two consecutive words' first letters can be swapped and produce another interesting sentence.
* Ability to add to a list of rules to ignore, e.g. ignore word2 = "ING", ignore matching suffix LY when word is LEIGH or LEE
* Need to find an association dictionary to lookup word and get related words.  Like word association cloud.
* When doing searches by word, e.g. find ("NAZI")
* http://www.datamuse.com/api/ for a word finding API
* http://www.rhymer.com/
* nearby word search needs to be smarter about which phonemes can be inserted, removed, or patched.  Soundex has possibilities.
* as I'm looking through results, it'd be nice to be able to file away ideas as they come up
* should make sure that when I file ideas, I can store the formula or search I used to find it
* always have the formula
* consider using machine learning to determine proximity of words rather than imperative
* use lex
* use notebook interface for exploring output
* use reddit as target (and then upvotes helps give feedback)
* use punoff.com as target
* https://api.datamuse.com/words?rel_trg=canoe
* Observation that 1 and 2 syllable words are easier to build from
* WAGGING is two phonemes from WAGON, but still quite close
* FIX: index on Seq[Phoneme], not Seq[Option[Phoneme]]

# Things I've learned
* Regex 
** Can unapply the regex to parse a line
** IntelliJ ctrl-f find can be used to figure out the regex
* Use collect with an anonymous partial function to only get the lines that match
* Promote stuff out of main into their own classes so I can work with them in console
* Should record puns and use machine learning to help score quality

# Patterns
* PATTERN = PAT URN
* TIDE => T + I'D => THOUGHT + I'D
* VAMPIRE => RE + VAMP + IRE => REVAMP + IRE
* STAR => STARCH => STARCH WRECK => STAR TREK
* LONELY => (R for L) => RONERY
* PREREQ => PRE-WRECK
* CANOE => RUDDER => READY OR NOT, HERE I COME => RUDDER OR NOT, HERE I COME
* PUT THAT ECLIPSE WHERE THE SUN DON'T SHINE
* I'M ACHING OF MY CASTLE
* Formula = [find pun], then create story in which pun makes sense
* TAN GENTS = FILIPINO KINGS OF DISTRACTION = TAN GENTS
* [PHONEMES OF WORD ENDING WITH AH N] - [AH N] sounds like a [WORD ENDS WITH IH NG]
    ACCORDION => ACCORDING .... ACCORDING to my sources, tango music...
    ALIEN => ALING IF the US got invaded by martians, we'd have an ALIEN NATION 
    - Input:  Erring -> EH R IH NG
    - EH R IN NG -> EH R AH N
    - EndsWith: EH R AH N, sorted by closest (least phonames different)
    
    
# Notes for Presentation
* Observation: I can generate lots of candidates, but how to filter
** Filter sentences that have unreocgnized words
** Rules that filter out boring things like possessives
* How to generate phrases in a category
* Ideas: highlight the word
* Ideas: how to show results better
* Ideas: how to find "on topic phrases"
* http://punoff.com/traditional-topics-for-punslingers/
* Observation: It's not sufficient to have a sentence that's related to the topic.  The substitution must be relevant
* Resource: https://github.com/niderhoff/nlp-datasets
* Learning: using itertools helpful to deal with large data
* Learning: chaining maps like in scala isn't pythonic and is awkward
* Learning: be careful with iterators/generators cause you can accidentally empty them
* Evolution: instead of just looking for whether a sentence puns with my word, look for whether it contains
** Synonyms
* Evolution: spoonerisms
* Evolution: parallelization