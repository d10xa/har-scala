```
interp.repositories() ++= Seq(
  coursierapi.MavenRepository
    .of("https://dl.bintray.com/d10xa/maven")
)
```

```
import $ivy.`ru.d10xa::har-core:0.1.0`
import $ivy.`ru.d10xa::har-circe:0.1.0`
import $ivy.`ru.d10xa::har-html:0.1.0`
import $ivy.`ru.d10xa::har-codegen-http4s:0.1.0`
import har._
import har.circe._
import har.html._
import har.codegen.http4s._
```


```
jadd search -f ammonite --repository https://dl.bintray.com/d10xa/maven ru.d10xa:har-core%% ru.d10xa:har-circe%% ru.d10xa:har-codegen-http4s%% ru.d10xa:har-html%%
```
