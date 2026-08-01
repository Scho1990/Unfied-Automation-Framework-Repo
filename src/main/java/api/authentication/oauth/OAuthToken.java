package api.authentication.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Objects;

public class OAuthToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private long expiresIn;

    private String scope;

    private Instant issuedAt;

    private Instant expiryTime;

    public OAuthToken(){
    }

    public OAuthToken(String accessToken, String refreshToken, String tokenType, long expiresIn, String scope, Instant issuedAt){
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
      this.tokenType = tokenType;
      this.expiresIn = expiresIn;
      this.scope = scope;
      this.issuedAt = issuedAt;
      recalculateExpiryTime();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        recalculateExpiryTime();
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Instant issuedAt) {
        this.issuedAt = issuedAt;
        recalculateExpiryTime();
    }

    public Instant getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Instant expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OAuthToken that = (OAuthToken) o;
        return expiresIn == that.expiresIn
                && Objects.equals(accessToken, that.accessToken)
                && Objects.equals(refreshToken, that.refreshToken)
                && Objects.equals(tokenType, that.tokenType)
                && Objects.equals(scope, that.scope)
                && Objects.equals(issuedAt, that.issuedAt)
                && Objects.equals(expiryTime, that.expiryTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, refreshToken, tokenType, expiresIn, scope, issuedAt, expiryTime);
    }

    @Override
    public String toString() {
        return "OAuthToken{" +
                "accessToken='***masked***'" +
                ", refreshToken='***masked***'" +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", scope='" + scope + '\'' +
                ", issuedAt=" + issuedAt +
                ", expiryTime=" + expiryTime +
                '}';
    }

    public boolean hasRefreshToken() {
        return refreshToken != null && !refreshToken.isBlank();
    }

    /**
     * Recalculates expiryTime whenever issuedAt or expiresIn changes.
     */
    private void recalculateExpiryTime() {
        if (issuedAt != null && expiresIn > 0) {
            this.expiryTime = issuedAt.plusSeconds(expiresIn);
        } else {
            this.expiryTime = null;
        }
    }
}
