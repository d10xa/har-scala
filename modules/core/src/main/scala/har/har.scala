package har

final case class Har(
  log: Log
)

final case class Log(
  version: String,
  creator: Creator,
  browser: Option[Browser],
  pages: Option[Vector[Page]],
  entries: Vector[Entry],
  comment: Option[String]
)

final case class Creator(
  name: String,
  version: String,
  comment: Option[String]
)

final case class Browser(
  name: String,
  version: String,
  comment: Option[String]
)

final case class Page(
  startedDateTime: String,
  id: String,
  title: Option[String],
  pageTimings: PageTimings,
  comment: Option[String]
)

final case class PageTimings(
  onContentLoad: Option[Double],
  onLoad: Option[Double],
  comment: Option[String]
)

final case class Entry(
  pageref: Option[String],
  startedDateTime: String,
  time: Double,
  request: Request,
  response: Response,
  cache: Option[Cache],
  timings: Option[Timings],
  serverIPAddress: Option[String],
  connection: Option[String],
  comment: Option[String],
  chromeInitiator: Option[ChromeInitiator]
)

final case class Request(
  method: String,
  url: String,
  httpVersion: String,
  cookies: Vector[Cookie],
  headers: Vector[Header],
  queryString: Vector[QueryString],
  postData: Option[PostData],
  headersSize: Int,
  bodySize: Int,
  comment: Option[String]
)

final case class Response(
  status: Option[Int],
  statusText: Option[String],
  httpVersion: Option[String],
  cookies: Vector[Cookie],
  headers: Vector[Header],
  content: Option[Content],
  redirectURL: Option[String],
  headersSize: Option[Int],
  bodySize: Option[Int],
  comment: Option[String]
)

final case class Cookie(
  name: String,
  value: String,
  path: Option[String],
  domain: Option[String],
  expires: Option[String],
  httpOnly: Option[Boolean],
  secure: Option[Boolean],
  comment: Option[String]
)

final case class Header(
  name: String,
  value: String,
  comment: Option[String]
)

final case class QueryString(
  name: String,
  value: String,
  comment: Option[String]
)

final case class PostData(
  mimeType: String,
  params: Option[Vector[Param]],
  text: String,
  comment: Option[String]
)

final case class Param(
  name: String,
  value: Option[String],
  fileName: Option[String],
  contentType: Option[String],
  comment: Option[String]
)

final case class Content(
  size: Option[Int],
  compression: Option[Int],
  mimeType: Option[String],
  text: Option[String],
  encoding: Option[String],
  comment: Option[String]
)

final case class Cache(
  beforeRequest: Option[BeforeAfterRequest],
  afterRequest: Option[BeforeAfterRequest],
  comment: Option[String]
)

final case class BeforeAfterRequest(
  expires: Option[String],
  lastAccess: Option[String],
  eTag: Option[String],
  hitCount: Option[Int],
  comment: Option[String]
)

final case class Timings(
  blocked: Option[Double],
  dns: Option[Double],
  connect: Option[Double],
  send: Double,
  waitTiming: Double,
  receive: Double,
  ssl: Option[Double],
  comment: Option[String]
)

/** @param stack is None for type parser
  */
final case class ChromeInitiator(
  `type`: String,
  stack: Option[ChromeInitiatorStack],
  lineNumber: Option[Int]
)

final case class ChromeInitiatorStack(
  callFrames: List[ChromeInitiatorCallFrame],
  parent: Option[ChromeInitiatorStack],
  description: Option[String]
)

final case class ChromeInitiatorCallFrame(
  functionName: String,
  scriptId: String,
  url: String,
  lineNumber: Int,
  columnNumber: Int
)
