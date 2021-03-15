package har

import io.circe.parser._
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers

import org.scalatest.EitherValues._
import org.scalatest.OptionValues._
import har.circe._

class HarCirceTest extends AnyFunSuiteLike with Matchers {
  test("entry") {
    val parseResult = parse("""
        |{
        |  "pageref": "page_1",
        |  "startedDateTime": "1970-01-01T12:34:56.000+00:00",
        |  "request": {
        |    "bodySize": 0,
        |    "method": "GET",
        |    "url": "https://example.com",
        |    "httpVersion": "",
        |    "headers": [],
        |    "cookies": [],
        |    "queryString": [],
        |    "headersSize": 0
        |  },
        |  "response": {
        |    "status": 0,
        |    "statusText": "",
        |    "httpVersion": "",
        |    "headers": [],
        |    "cookies": [],
        |    "content": {},
        |    "redirectURL": "",
        |    "bodySize": -1
        |  },
        |  "cache": {},
        |  "timings": {},
        |  "time": 0
        |}
        |""".stripMargin)

    val entry = parseResult.value.as[Entry].value
    entry.pageref.value shouldBe "page_1"
    entry.time shouldBe 0.0
    entry.startedDateTime shouldBe "1970-01-01T12:34:56.000+00:00"
    entry.request.method shouldBe "GET"
    entry.request.url shouldBe "https://example.com"
    entry.request.postData shouldBe None
    entry.response.status shouldBe Some(0)
    entry.response.statusText shouldBe Some("")
    entry.response.httpVersion shouldBe Some("")
    entry.response.content shouldBe None
    entry.response.redirectURL shouldBe Some("")
    entry.response.headersSize shouldBe None
    entry.response.bodySize.value shouldBe -1
    entry.response.comment shouldBe None
    entry.cache shouldBe None
    entry.timings shouldBe None
    entry.serverIPAddress shouldBe None
    entry.connection shouldBe None
    entry.comment shouldBe None
    entry.chromeInitiator shouldBe None
  }
}
