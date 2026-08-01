package api.authentication.oauth;

import java.security.SecureRandom;
import java.util.Base64;

public final class OAuthStateGenerator {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private static final int STATE_LENGTH = 32;

    private OAuthStateGenerator() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String generateState(){
        byte[] randomnesses = new byte[STATE_LENGTH];
        SECURE_RANDOM.nextBytes(randomnesses);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomnesses);
    }
}
