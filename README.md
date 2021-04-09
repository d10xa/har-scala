## ammonite / almond

```scala
interp.repositories() ++= Seq(
  coursierapi.MavenRepository
    .of("https://jitpack.io")
)
```

```scala
import $ivy.`com.github.d10xa.har-scala::har-core:0.1.7`
import $ivy.`com.github.d10xa.har-scala::har-circe:0.1.7`
import $ivy.`com.github.d10xa.har-scala::har-html:0.1.7`
import $ivy.`com.github.d10xa.har-scala::har-codegen-http4s:0.1.7`
```

```scala
import har._
import har.circe._
import har.html._
import har.codegen.http4s._
```

## jadd

```
jadd search -f ammonite --repository https://jitpack.io com.github.d10xa.har-scala:har-core%% com.github.d10xa.har-scala:har-circe%% com.github.d10xa.har-scala:har-codegen-http4s%% com.github.d10xa.har-scala:har-html%%
```

## Local usage:

`sbt Compile/fullClasspath/exportToAmmoniteScript`

```scala
import $file.target.`scala-2.13`.`fullClasspath-Compile`
```
