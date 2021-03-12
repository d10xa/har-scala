package har.codegen.http4s

import har._

import scala.meta._

trait Codegen {
  def generate(request: Request): String
}

object Codegen {

  def generate(request: Request): String =
    SimpleFmt.format(termRequestWithImports(request).toString())

  private lazy val ignoreHeaders =
    Set(":method", ":authority", ":scheme", ":path")

  def addEntity(t: Term.Apply, r: Request): Term.Apply =
    r.postData match {
      case Some(postData) =>
        q"""$t.withEntity(${postData.text})(
                     EntityEncoder.simple()((s: String) =>
                       Chunk.bytes(s.getBytes(DefaultCharset.nioCharset))
                     )
                   )"""
      case None => t
    }

  def termMethod(s: String): Term.Select = q"Method.${Term.Name(s)}"

  def termHeader(k: String, v: String): Term.Apply =
    q"""Header($k, $v)"""

  def termHeaders(list: List[(String, String)]): Term.Apply = {
    val terms: List[Term.Apply] = list.map((termHeader _).tupled)
    q"""Headers.of(..$terms)"""
  }

  def termRequest(r: Request): Term.Apply =
    q"""Request(
          uri = Uri.unsafeFromString(${r.url}),
          method = ${termMethod(r.method)},
          headers = ${termHeaders(extractHeadersTuples(r))}
        )"""

  def termRequestWithImports(request: Request): Term.Block =
    q"""
         import fs2.Chunk
         import org.http4s.DefaultCharset
         import org.http4s.EntityEncoder
         import org.http4s.Header
         import org.http4s.Headers
         import org.http4s.Method
         import org.http4s.Request
         import org.http4s.Uri

         def request[F[_]]: Request[F] =${termRequestWithEntity(request)}
         """

  def termRequestWithEntity(r: Request): Term.Apply =
    addEntity(termRequest(r), r)

  def headerToTuple(header: har.Header): (String, String) =
    (header.name, header.value)

  def extractHeadersTuples(request: Request): List[(String, String)] =
    request.headers
      .filterNot(h => ignoreHeaders.contains(h.name.toLowerCase))
      .map(headerToTuple)
      .toList

}
