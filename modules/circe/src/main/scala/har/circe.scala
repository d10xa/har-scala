package har
import io.circe.Decoder
import io.circe.Encoder
import io.circe.generic.semiauto.deriveDecoder

object circe extends HarCirceDecoders

trait HarCirceDecoders {
  implicit lazy val circeDecoderHar: Decoder[Har] = deriveDecoder
  implicit lazy val circeDecoderLog: Decoder[Log] = deriveDecoder
  implicit lazy val circeDecoderCreator: Decoder[Creator] = deriveDecoder
  implicit lazy val circeDecoderBrowser: Decoder[Browser] = deriveDecoder
  implicit lazy val circeDecoderPage: Decoder[Page] = deriveDecoder
  implicit lazy val circeDecoderPageTimings: Decoder[PageTimings] = deriveDecoder
  implicit lazy val circeDecoderEntry: Decoder[Entry] =
    Decoder.forProduct11(
      "pageref",
      "startedDateTime",
      "time",
      "request",
      "response",
      "cache",
      "timings",
      "serverIPAddress",
      "connection",
      "comment",
      "_initiator"
    )(Entry.apply)
  implicit lazy val circeDecoderRequest: Decoder[Request] = deriveDecoder
  implicit lazy val circeDecoderResponse: Decoder[Response] = deriveDecoder
  implicit lazy val circeDecoderCookie: Decoder[Cookie] = deriveDecoder
  implicit lazy val circeDecoderHeader: Decoder[Header] = deriveDecoder
  implicit lazy val circeDecoderQueryString: Decoder[QueryString] = deriveDecoder
  implicit lazy val circeDecoderPostData: Decoder[PostData] = deriveDecoder
  implicit lazy val circeDecoderParam: Decoder[Param] = deriveDecoder
  implicit lazy val circeDecoderContent: Decoder[Content] = deriveDecoder
  implicit lazy val circeDecoderCache: Decoder[Cache] = deriveDecoder
  implicit lazy val circeDecoderBeforeAfterRequest: Decoder[BeforeAfterRequest] =
    deriveDecoder
  implicit lazy val circeDecoderTimings: Decoder[Timings] =
    Decoder.forProduct8(
      "blocked",
      "dns",
      "connect",
      "send",
      "wait",
      "receive",
      "ssl",
      "comment"
    )(Timings.apply)
  implicit lazy val circeDecoderChromeInitiator: Decoder[ChromeInitiator] =
    deriveDecoder
  implicit lazy val circeDecoderChromeInitiatorStack: Decoder[ChromeInitiatorStack] =
    deriveDecoder
  implicit lazy val circeDecoderChromeInitiatorCallFrame
    : Decoder[ChromeInitiatorCallFrame] =
    deriveDecoder
}

trait HarCirceEncoders {
  implicit lazy val circeEncoderTimings: Encoder[Timings] =
    Encoder.forProduct8(
      "blocked",
      "dns",
      "connect",
      "send",
      "wait",
      "receive",
      "ssl",
      "comment"
    )(
      x =>
        (
          x.blocked,
          x.dns,
          x.connect,
          x.send,
          x.waitTiming,
          x.receive,
          x.ssl,
          x.comment))
}
