package pack

import org.scalatest.freespec.AnyFreeSpecLike
import org.scalatest.matchers.should.Matchers


class ExampleSpec extends AnyFreeSpecLike with Matchers{

  "1 + 1" - {

    "should return 2" in {
      (1 + 1) should be(2)
    }
  }

  override def toString: String = s"Verifying ${getClass.getSimpleName}"
}
