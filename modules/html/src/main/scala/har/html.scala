package har
import scalatags.Text.all._

object html extends HtmlEntry

trait HtmlEntry {

  def makeDetails(
    name: String,
    pairs: List[(String, Modifier)],
    open: Boolean = false
  ): Modifier = {
    val details = tag("details")
    val summary = tag("summary")
    val modifiers = pairs.flatMap { case (k, v) =>
      Vector(div(dt(style := "float: left; clear: left", s"$k: "), dd(v)))
    }
    details(
      if (open) attr("open").empty else attr("open") := "false",
      summary(name),
      dl(modifiers)
    )
  }

  def entryToHtml(e: Entry): String = {
    val request = e.request
    val response = e.response
    scalatags.Text.all
      .html(
        head(),
        body(
          makeDetails(
            "General",
            List(
              "Request URL" -> request.url,
              "Request Method" -> request.method,
              "Status Code" -> span(
                response.status.toString,
                " ",
                response.statusText
              )
            ),
            open = true
          ),
          makeDetails(
            "Response Headers",
            response.headers.map(h => h.name -> span(h.value)).toList
          ),
          makeDetails(
            "Request Headers",
            request.headers.map(h => h.name -> span(h.value)).toList
          ),
          makeDetails(
            "Query String Parameters",
            request.queryString.map(q => q.name -> span(q.value)).toList
          ),
          makeDetails(
            "Response",
            List("Body" -> span(response.content.flatMap(_.text)))
          )
        )
      )
      .toString
  }
}
