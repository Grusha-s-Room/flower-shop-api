package org.puzre.adapter.auth;

import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

public class JwtTokenUtils {

    public static final String USER_ROLE = "User";

    public static String generateTokenString(JwtClaims claims, String privateKeyPath) throws Exception {
        PrivateKey pk = readPrivateKey(privateKeyPath);
        return generateTokenString(pk, privateKeyPath, claims);
    }

    public static JwtClaims getTokenProperties(String jwt, String publicKeyPath, String issuer, String email, String audience) throws Exception {
        PublicKey pk = readPublicKey(publicKeyPath);
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setExpectedIssuer(issuer)
                .setRequireJwtId()
                .setExpectedAudience(audience)
                .setExpectedSubject(email)
                .setVerificationKey(pk)
                .build();
        return jwtConsumer.processToClaims(jwt);
    }

    private static String generateTokenString(PrivateKey privateKey, String kid, JwtClaims claims) throws Exception {

        long currentTimeInSecs = currentTimeInSecs();

        claims.setIssuedAt(NumericDate.fromSeconds(currentTimeInSecs));
        claims.setClaim(Claims.auth_time.name(), NumericDate.fromSeconds(currentTimeInSecs));

        for (Map.Entry<String, Object> entry : claims.getClaimsMap().entrySet()) {
            System.out.printf("\tAdded claim: %s, value: %s\n", entry.getKey(), entry.getValue());
        }

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(privateKey);
        jws.setKeyIdHeaderValue(kid);
        jws.setHeader("typ", "JWT");
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        return jws.getCompactSerialization();
    }

    public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
        InputStream contentIS = new FileInputStream(pemResName);
        byte[] tmp = new byte[4096];
        int length = contentIS.read(tmp);
        return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
    }

    public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    public static PublicKey readPublicKey(final String pemResName) throws Exception {
        InputStream is = new FileInputStream(pemResName);
        byte[] bytes = is.readAllBytes();
        return decodePublicKey(new String(bytes, StandardCharsets.UTF_8));
    }

    public static PublicKey decodePublicKey(String pemEncoded) throws Exception {
        byte[] encoded = toEncodedBytes(pemEncoded);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(keySpec);
    }

    private static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    private static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    public static int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }

}
