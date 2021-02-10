```
interp.repositories() ++= Seq(
  coursierapi.MavenRepository
    .of("https://dl.bintray.com/d10xa/maven")
)
import $ivy.`ru.d10xa::har-core:0.1.0`
import $ivy.`ru.d10xa::har-circe:0.1.0`
import har._
import har.circe._
```
