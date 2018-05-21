package com.example.demo;

import sun.security.rsa.RSAPublicKeyImpl;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.Base64;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        System.out.println("wtf");
        KeyPair pair=keyGen.genKeyPair();

        byte[] publicKey = pair.getPublic().getEncoded();

        byte[] privateKey=pair.getPrivate().getEncoded();
        System.out.println(Base64.getEncoder().encodeToString(privateKey));
        StringBuffer retString = new StringBuffer();
        for (int i = 0; i < publicKey.length; ++i) {
            retString.append(Integer.toHexString(0x0100 + (publicKey[i] & 0x00FF)).substring(1));
        }
        System.out.println(Base64.getEncoder().encodeToString(publicKey).length());
        String pkcs8Pem="MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAgtGsAyPOglFwFtkqi4DuLaPOtaU2sqk+AbY/hNcKpvDuEMKbLFotMFB4++xL3l246APPZSxurM01CsZfgwHOOQIDAQABAkB4I8zgOVMfFWFmalyLL4YNfiGJakg4fftHITNmcQMEWm/YZQJoJqfljbsqdAqbcffvAMHz+SeCZeWBFrWPMUMNAiEAydwjUD9mqlhwmuXcZRn3tDzp9zfYfXMk9V6kMoKCDNsCIQCl589+QDULDs36l3z+HcgOx2IY6S3m6Ino2Pwb+MIzewIgKikIVyJm1v1Rx4hq19LsoYB3fWL0nH8IIRd7Q/FuKDECIAD+r97Y8bh8QT30dJptgUqdZXgyxcSxtkoAKxrb1gZLAiEAjdC8SJEbg3nZ4Qi5SEUaqp3PB3SSCbwVYN+B1Wi0Z5w=";

        byte[] pkcs8EncodedBytes= Base64.getDecoder().decode(pkcs8Pem);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privKey = kf.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        String encrypted = "LyXpPGVKdhl+yMKtIC0oAiLx9zQ1MBqtngS73EgY64AHDnO0+str2/zCqwdj1Pv7PW+cdYYBI/8bme3fAsRHee7IewQLsDpVKCmaVcVnQzsQM1KxsNjGddzInEr1Tg==";
        System.out.println(encrypted.length());
     //   byte[] decrypted=cipher.doFinal(encrypted.getBytes());
       // System.out.println(decrypted.length);
      //  System.out.println(new String(decrypted));

        PrivateKey privateKey2 =
                KeyFactory.getInstance("RSA").generatePrivate(new
                        PKCS8EncodedKeySpec(Base64.getDecoder().decode(pkcs8Pem)));

        BigInteger modulus=((RSAPrivateKey)privateKey2).getModulus();
        String modulusBase64=Base64.getEncoder().encodeToString(modulus.toByteArray());
        BigInteger exponent=((RSAPrivateKey)privateKey2).getPrivateExponent();
        String privateExponentBase=Base64.getEncoder().encodeToString(exponent.toByteArray());
        System.out.println();
        System.out.println(modulusBase64);
        System.out.println();
        System.out.println(privateExponentBase);
        System.out.println();
        Cipher cipher2 = Cipher.getInstance("RSA");
        cipher2.init(Cipher.DECRYPT_MODE, privateKey2);

        Cipher ciphirEncrypt=Cipher.getInstance("RSA");
        X509EncodedKeySpec publicKey1=new X509EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        ciphirEncrypt.init(Cipher.ENCRYPT_MODE, keyFactory.generatePublic(publicKey1));
        byte[] encryptedBytes=ciphirEncrypt.doFinal("THis ".getBytes());
        byte[] decryptedBytes=cipher2.doFinal(encryptedBytes);
        System.out.println(new String (decryptedBytes));


//        byte[] decryptedData=cipher.doFinal(encrypted.getBytes());
  //      System.out.println(new String(decryptedData));

    }
}
