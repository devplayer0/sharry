package sharry.backend.auth
import scodec.bits.ByteVector
import sharry.common._

case class AuthConfig(
    serverSecret: ByteVector,
    sessionValid: Duration,
    fixed: AuthConfig.Fixed,
    http: AuthConfig.Http,
    httpBasic: AuthConfig.HttpBasic,
    command: AuthConfig.Command,
    internal: AuthConfig.Internal,
    oauth: Seq[AuthConfig.OAuth]
)

object AuthConfig {

  case class Fixed(enabled: Boolean, user: Ident, password: Password, order: Int)

  case class Http(
      enabled: Boolean,
      url: LenientUri,
      method: String,
      body: String,
      contentType: String,
      order: Int
  )

  case class HttpBasic(enabled: Boolean, url: LenientUri, method: String, order: Int)

  case class Command(
      enabled: Boolean,
      program: Seq[String],
      success: Int,
      order: Int
  )

  case class Internal(enabled: Boolean, order: Int)

  case class OAuth(
      id: Ident,
      enabled: Boolean,
      name: String,
      authorizeUrl: LenientUri,
      tokenUrl: LenientUri,
      userUrl: LenientUri,
      userIdKey: String,
      clientId: String,
      clientSecret: String,
      icon: Option[String]
  )

  object OAuth {

    def github(clientId: String, clientSecret: String): OAuth =
      OAuth(
        Ident.unsafe("github"),
        true,
        "Github",
        LenientUri.unsafe("https://github.com/login/oauth/authorize"),
        LenientUri.unsafe("https://github.com/login/oauth/access_token"),
        LenientUri.unsafe("https://api.github.com/user"),
        "login",
        clientId,
        clientSecret,
        Some("github")
      )
  }

}
