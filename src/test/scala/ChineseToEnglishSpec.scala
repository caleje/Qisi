import org.scalatest.FlatSpec
import qisi.ChineseToPhoneme

class ChineseToEnglishSpec extends FlatSpec {

  "cheng" should "translate" in {
    assert(ChineseToPhoneme.translate("cheng") == Seq("CH", "UH", "NG"))
  }

  "an" should "translate" in {
    assert(ChineseToPhoneme.translate("an") == Seq("AE", "N"))
  }
}
