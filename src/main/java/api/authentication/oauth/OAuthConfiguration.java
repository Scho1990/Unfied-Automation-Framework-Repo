package api.authentication.oauth;

import java.util.Objects;

public class OAuthConfiguration {

    private String authorizationUrl;

    private String tokenUrl;

    private String clientId;

    private String clientSecret;

    private String redirectUri;

    private String scope;

    public OAuthConfiguration() {
    }

   public OAuthConfiguration(String authorizationUrl, String tokenUrl, String clientId, String clientSecret, String redirectUri, String scope) {
        this.authorizationUrl = authorizationUrl;
        this.tokenUrl = tokenUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.scope = scope;
   }

   public String getAuthorizationUrl() {
        return authorizationUrl;
   }

   public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
   }

   public String getTokenUrl() {
        return tokenUrl;
   }

   public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
   }

   public String getClientId() {
        return clientId;
   }

   public void setClientId(String clientId) {
        this.clientId = clientId;
   }

   public String getClientSecret() {
        return clientSecret;
   }

   public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
   }

   public String getRedirectUri() {
        return redirectUri;
   }

   public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
   }

   public String getScope() {
        return scope;
   }

   public void setScope(String scope) {
        this.scope = scope;
   }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OAuthConfiguration that = (OAuthConfiguration) o;
        return Objects.equals(authorizationUrl, that.authorizationUrl)
                && Objects.equals(tokenUrl, that.tokenUrl)
                && Objects.equals(clientId, that.clientId)
                && Objects.equals(clientSecret, that.clientSecret)
                && Objects.equals(redirectUri, that.redirectUri)
                && Objects.equals(scope, that.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorizationUrl, tokenUrl, clientId, clientSecret, redirectUri, scope);
    }

    @Override
    public String toString() {
        return "OAuthConfiguration{" +
                "authorizationUrl='" + authorizationUrl + '\'' +
                ", tokenUrl='" + tokenUrl + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='***masked***'" +
                ", redirectUri='" + redirectUri + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
