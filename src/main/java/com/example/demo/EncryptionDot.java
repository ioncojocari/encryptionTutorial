package com.example.demo;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptionDot {

    private static final String modulusBase64="ANeV/sIqweLYA+OLpOn8TJFWgk66+mGF8l1e4bB+NVp93IrvAZw3LONjLMR24ofIV3xe/cNppcfcB6CzlxcliwE=";
    private static final String privateKeyBase64="MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEA15X+wirB4tgD44uk6fxMkVaCTrr6YYXyXV7hsH41Wn3ciu8BnDcs42MsxHbih8hXfF79w2mlx9wHoLOXFyWLAQIDAQABAkEAgaj859q/cadp71j1fqT3iBH3nVJndHATLIT8SCIgBKPC4Dkg/xSmfK4NJ/1gxMJ4jUhS2klNmo0iOBDQc8llYQIhAPszA12NisgOZbFcF1ub17f1lG86pS5oYU5oWgvyu8HjAiEA27S+lbcjcmJxpklJE3SshdxPNMjoyTwT6x5ZzMm7xMsCIQCA/mM+CU85CFukRjwgaNjPuKZur9jfCe7eCqwi8pUELQIgNw5xIAV3vYdL4ZbF2DlJrnXJ53ldVGhYuXeumzIGescCIQDE+3+1+MHrz5HCeHeb0PJ10G9WWDT5wDsz+oDuxmHw1Q==";
    KeyFactory kf;
    RSAPublicKey publicKey;
    RSAPrivateKey privateKey;
    Cipher cipherEncrypt ;
    Cipher cipherDecrypt;
    public EncryptionDot() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException {
        kf = KeyFactory.getInstance("RSA");
        publicKey=getRsaPublikKey();
        privateKey=getRsaPrivateKey();
        cipherEncrypt = Cipher.getInstance("RSA/ECB/NoPadding");
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, publicKey);
        cipherDecrypt=Cipher.getInstance("RSA/ECB/NoPadding");
        cipherDecrypt.init(Cipher.DECRYPT_MODE,privateKey);
    }
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        EncryptionDot main=new EncryptionDot();
        String text="this is my text";
        byte[] encoded=main.encode(text.getBytes());
        byte[] decoded=main.decode(encoded);
        System.out.println(new String(decoded));
    }

    public byte[] encode(byte[] data) throws BadPaddingException, IllegalBlockSizeException {
        return cipherEncrypt.doFinal(data);
    }

    public byte[] decode(byte[] data) throws BadPaddingException, IllegalBlockSizeException {
        return cipherDecrypt.doFinal(data);
    }

    public RSAPublicKey getRsaPublikKey() throws InvalidKeySpecException {
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(Base64.getDecoder().decode(modulusBase64)), new BigInteger(Base64.getDecoder().decode("AQAB")));
        return (RSAPublicKey) kf.generatePublic(publicKeySpec);
    }

    public RSAPrivateKey getRsaPrivateKey() throws InvalidKeySpecException {
        PKCS8EncodedKeySpec rsaPrivKey = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBase64));
        return (RSAPrivateKey) kf.generatePrivate(rsaPrivKey);
    }
}
