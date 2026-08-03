package api.authentication.oauth.configuration;

public final class OAuthConstants {

    private OAuthConstants() {
        throw new UnsupportedOperationException("Utility class");
    }

    /* ===========================
      OAuth Endpoints
      =========================== */
    public static final String AUTHORIZE_ENDPOINT  = "/authorize";
    public static final String TOKEN_ENDPOINT = "/token";

    /* ===========================
       OAuth Request Parameters
       =========================== */
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String RESPONSE_TYPE = "response_type";
    public static final String GRANT_TYPE = "grant_type";
    public static final String CODE = "code";
    public static final String SCOPE = "scope";
    public static final String STATE = "state";
    public static final String CODE_VERIFIER = "code_verifier";
    public static final String CODE_CHALLENGE = "code_challenge";
    public static final String CODE_CHALLENGE_METHOD = "code_challenge_method";
    public static final String REFRESH_TOKEN = "refresh_token";

     /* ===========================
       OAuth Response Parameters
       =========================== */
    public static final String ACCESS_TOKEN = "access_token";
    public static final String TOKEN_TYPE = "token_type";
    public static final String EXPIRES_IN = "expires_in";

    /* ===========================
       OAuth Header Names
       =========================== */
    public static final String AUTHORIZATION = "Authorization";

    /* ===========================
       OAuth Header Values
       =========================== */
    public static final String BEARER_PREFIX = "Bearer ";

    /* ===========================
       OAuth Response Types
       =========================== */
    public static final String RESPONSE_TYPE_CODE = "code";
}
