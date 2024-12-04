//> using scala 3.5.2
//> using platform scala-native
//> using nativeLinking -fuse-ld=mold -static
//> using nativeVersion 0.4.17
//> using dep "org.http4s::http4s-core::0.23.29"
//> using dep "org.http4s::http4s-ember-server::0.23.29"
//> using dep "org.http4s::http4s-dsl::0.23.29"
//> using deps "com.armanbilge::epollcat::0.1.6"

import cats.effect.{IO, IOApp, ExitCode}
import com.comcast.ip4s._ // for ipv4 and port
import org.http4s.HttpApp
import org.http4s.ember.server.EmberServerBuilder

object Main extends epollcat.EpollApp.Simple:
  def run: IO[Unit] = for {
    _ <- IO.println("running server")
    s <- EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(HttpApp.notFound) // なにがなんでもNot Foundを返す
      .build
      .useForever
      .as(ExitCode.Success)
      .start
    _ <- IO.println("ready")
    _ <- s.join
  } yield ()
