package api.authentication;

import application.App;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

public class JWTUtil {
    private static String TOKEN_SECRET = "572247f3042d4add9397333a172680143d7363ace4";

    public static String createToken(String email) throws JoseException {
        JsonWebSignature jws = new JsonWebSignature();

        Token login = new Token(email, System.currentTimeMillis());

        jws.setHeader("typ", "JWT");
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setPayload(App.g.toJson(login));
        jws.setKey(new HmacKey(TOKEN_SECRET.getBytes()));
        jws.setDoKeyValidation(false);
        return jws.getCompactSerialization();
    }

    public static String getEmail(String token) throws JoseException {
        if (token == null)
            return null;

        JsonWebSignature receiverJws = new JsonWebSignature();
        receiverJws.setCompactSerialization(token);
        receiverJws.setKey(new HmacKey(TOKEN_SECRET.getBytes()));

        if (!receiverJws.verifySignature())
            return null;

        String plaintext = receiverJws.getPayload();
        Token login = App.g.fromJson(plaintext, Token.class);

        return login.getEmail();
    }
}
