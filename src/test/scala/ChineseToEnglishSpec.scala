import org.scalatest.FlatSpec

class ChineseToEnglishSpec extends FlatSpec {

  "cheng" should "translate" in {
    assert(ChineseToEnglish.translate("cheng") == Seq("CH", "UH", "NG"))
  }

  "an" should "translate" in {
    assert(ChineseToEnglish.translate("an") == Seq("AH", "N"))
  }
}
