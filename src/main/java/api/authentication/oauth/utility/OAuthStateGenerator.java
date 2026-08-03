package api.authentication.oauth.utility;

import java.security.SecureRandom;
import java.util.Base64;

public final class OAuthStateGenerator {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private static final int STATE_LENGTH = 32;

    private OAuthStateGenerator() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String generateState(){
        byte[] randomBytes = new byte[STATE_LENGTH];
        SECURE_RANDOM.nextBytes(randomBytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }
}
