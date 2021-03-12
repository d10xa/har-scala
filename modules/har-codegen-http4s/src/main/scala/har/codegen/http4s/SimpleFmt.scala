package har.codegen.http4s

import org.scalafmt.Scalafmt
import org.scalafmt.config.ScalafmtConfig

object SimpleFmt {

  private lazy val cfg: ScalafmtConfig =
    ScalafmtConfig.default
      .copy(maxColumn = 80)
      .forSbt

  def format(code: String): String =
    Scalafmt.format(code, cfg).toEither.getOrElse(code)

}
