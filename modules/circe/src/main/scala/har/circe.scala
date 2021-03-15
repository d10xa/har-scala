package har
import io.circe.ACursor
import io.circe.Decoder
import io.circe.Decoder.Result
import io.circe.Encoder
import io.circe.FailedCursor
import io.circe.HCursor
import io.circe.generic.semiauto.deriveDecoder

object circe extends HarCirceDecoders with HarCirceEncoders

trait HarCirceDecoders {

  def downFieldOption[A](c: ACursor, field: String)(implicit
    decoderA: Decoder[Option[A]]
  ): Result[Option[A]] =
    c.downField(field) match {
      case _: FailedCursor => Right(None)
      case c: HCursor if c.value.asObject.exists(_.isEmpty) =>
        Right(None)
      case c: HCursor => c.as[Option[A]]
    }

  implicit lazy val circeDecoderHar: Decoder[Har] = deriveDecoder
  implicit lazy val circeDecoderLog: Decoder[Log] = deriveDecoder
  implicit lazy val circeDecoderCreator: Decoder[Creator] = deriveDecoder
  implicit lazy val circeDecoderBrowser: Decoder[Browser] = deriveDecoder
  implicit lazy val circeDecoderPage: Decoder[Page] = deriveDecoder
  implicit lazy val circeDecoderPageTimings: Decoder[PageTimings] =
    deriveDecoder

  implicit val circeDecoderEntry: Decoder[Entry] =
    new Decoder[Entry] {
      override def apply(c: HCursor): Result[Entry] =
        for {
          pageref <- downFieldOption[String](c, "pageref")
          startedDateTime <- c.downField("startedDateTime").as[String]
          time <- c.downField("time").as[Double]
          request <- c.downField("request").as[Request]
          response <- c.downField("response").as[Response]
          cache <- downFieldOption[Cache](c, "cache")
          timings <- downFieldOption[Timings](c, "timings")
          serverIPAddress <- downFieldOption[String](c, "serverIpAddress")
          connection <- downFieldOption[String](c, "connection")
          comment <- downFieldOption[String](c, "comment")
          chromeInitiator <-
            downFieldOption[ChromeInitiator](c, "_initiator")
        } yield Entry(
          pageref = pageref,
          startedDateTime = startedDateTime,
          time = time,
          request = request,
          response = response,
          cache = cache,
          timings = timings,
          serverIPAddress = serverIPAddress,
          connection = connection,
          comment = comment,
          chromeInitiator = chromeInitiator
        )
    }

  implicit val circeDecoderRequest: Decoder[Request] =
    new Decoder[Request] {
      override def apply(c: HCursor): Result[Request] = for {
        method <- c.downField("method").as[String]
        url <- c.downField("url").as[String]
        httpVersion <- c.downField("httpVersion").as[String]
        cookies <- c.downField("cookies").as[Vector[Cookie]]
        headers <- c.downField("headers").as[Vector[Header]]
        queryString <- c.downField("queryString").as[Vector[QueryString]]
        postData <- downFieldOption[PostData](c, "postData")
        headersSize <- c.downField("headersSize").as[Int]
        bodySize <- c.downField("bodySize").as[Int]
        comment <- downFieldOption[String](c, "comment")
      } yield Request(
        method = method,
        url = url,
        httpVersion = httpVersion,
        cookies = cookies,
        headers = headers,
        queryString = queryString,
        postData = postData,
        headersSize = headersSize,
        bodySize = bodySize,
        comment = comment
      )
    }

  implicit lazy val circeDecoderResponse: Decoder[Response] =
    new Decoder[Response] {
      override def apply(c: HCursor): Result[Response] = for {
        status <- downFieldOption[Int](c, "status")
        statusText <- downFieldOption[String](c, "statusText")
        httpVersion <- downFieldOption[String](c, "httpVersion")
        cookies <- c.downField("cookies").as[Vector[Cookie]]
        headers <- c.downField("headers").as[Vector[Header]]
        content <- downFieldOption[Content](c, "content")
        redirectURL <- downFieldOption[String](c, "redirectURL")
        headersSize <- downFieldOption[Int](c, "headersSize")
        bodySize <- downFieldOption[Int](c, "bodySize")
        comment <- downFieldOption[String](c, "comment")
      } yield Response(
        status = status,
        statusText = statusText,
        httpVersion = httpVersion,
        cookies = cookies,
        headers = headers,
        content = content,
        redirectURL = redirectURL,
        headersSize = headersSize,
        bodySize = bodySize,
        comment = comment
      )
    }
  implicit lazy val circeDecoderCookie: Decoder[Cookie] = deriveDecoder
  implicit lazy val circeDecoderHeader: Decoder[Header] = deriveDecoder
  implicit lazy val circeDecoderQueryString: Decoder[QueryString] =
    deriveDecoder
  implicit lazy val circeDecoderPostData: Decoder[PostData] = deriveDecoder
  implicit lazy val circeDecoderParam: Decoder[Param] = deriveDecoder
  implicit lazy val circeDecoderContent: Decoder[Content] =
    new Decoder[Content] {
      override def apply(c: HCursor): Result[Content] = for {
        size <- downFieldOption[Int](c, "size")
        compression <- downFieldOption[Int](c, "compression")
        mimeType <- downFieldOption[String](c, "mimeType")
        text <- downFieldOption[String](c, "text")
        encoding <- downFieldOption[String](c, "encoding")
        comment <- downFieldOption[String](c, "comment")
      } yield Content(
        size = size,
        compression = compression,
        mimeType = mimeType,
        text = text,
        encoding = encoding,
        comment = comment
      )
    }
  implicit lazy val circeDecoderCache: Decoder[Cache] = deriveDecoder
  implicit lazy val circeDecoderBeforeAfterRequest
    : Decoder[BeforeAfterRequest] =
    deriveDecoder
  implicit lazy val circeDecoderTimings: Decoder[Timings] =
    new Decoder[Timings] {
      override def apply(c: HCursor): Result[Timings] = for {
        blocked <- downFieldOption[Double](c, "blocked")
        dns <- downFieldOption[Double](c, "dns")
        connect <- downFieldOption[Double](c, "connect")
        send <- c.downField("send").as[Double]
        waitTiming <- c.downField("wait").as[Double]
        receive <- c.downField("receive").as[Double]
        ssl <- downFieldOption[Double](c, "ssl")
        comment <- downFieldOption[String](c, "comment")
      } yield Timings(
        blocked = blocked,
        dns = dns,
        connect = connect,
        send = send,
        waitTiming = waitTiming,
        receive = receive,
        ssl = ssl,
        comment = comment
      )
    }

  implicit lazy val circeDecoderChromeInitiator: Decoder[ChromeInitiator] =
    deriveDecoder
  implicit lazy val circeDecoderChromeInitiatorStack
    : Decoder[ChromeInitiatorStack] =
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
    )(x =>
      (
        x.blocked,
        x.dns,
        x.connect,
        x.send,
        x.waitTiming,
        x.receive,
        x.ssl,
        x.comment
      )
    )
}
