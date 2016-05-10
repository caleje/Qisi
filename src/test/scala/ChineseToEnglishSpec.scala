import org.scalatest.FlatSpec
import qisi.ChineseToPhonemeString

class ChineseToEnglishSpec extends FlatSpec {

  "cheng" should "translate" in {
    assert(ChineseToPhonemeString.translate("cheng") == Seq("CH", "UH", "NG"))
  }

  "an" should "translate" in {
    assert(ChineseToPhonemeString.translate("an") == Seq("AE", "N"))
  }
}
