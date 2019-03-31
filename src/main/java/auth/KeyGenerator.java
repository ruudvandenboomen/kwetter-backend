/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class KeyGenerator {

    private static final String SECRET_KEY = "secret";
    private static final String SHA_256 = "SHA-256";

    public String generateKey() throws NoSuchAlgorithmException {
        return getHashText(SECRET_KEY);
    }

    protected String getHashText(final String text) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
        byte[] hash = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(hash);

        return encoded;
    }
}
