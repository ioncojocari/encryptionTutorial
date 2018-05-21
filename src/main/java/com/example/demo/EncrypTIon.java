package com.example.demo;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static javax.swing.UIManager.getInt;


public class EncrypTIon {
    private static final String pK="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApeBGMz9rjKwJSL+6E8yDJbYMzfCY7j6C40v+VQLgS577+9gQhrAI8HXQnuCq6m3HpNTx/vVR1+jrr5A5ZWCb6VHHp9f00gmXxu4EeGvrMSbR/7j/Kk2NDfEc9FAO82Gv5/jGYk3BBsXmSzs6qjpXtngNmkhCMK0nUFEktYBQe0C/MsfDXjuahbm4EF51eq/2l+2qoy639c8duiD2KOJSf3aG6qIetD/mLE64AGQIinjbz44hAp1AFGz5OIo3e2qgsBAVi3+WQqeYm3vUdMuMWCrx90NR0gc49fjfsjXPPrMwHBvsfbwXV5CY0eK11LL8A0tOpwaCQym7YGOBLqGnOQIDAQAB";


    private static final String prK="MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAhU/h5OfJW16rlQXrqwqnBhGFNtW6ZraITNh70B/siyfM4r0k9ADXQJNVBSTyKyv9qClqwdl0wC1yrjn6kyI42QIDAQABAkB1wkmVrVXF20uPuQojLaa6ipF1Dbi5cHXYA8bi4sBNbpOlt3CExkIRvXMEyw9joR+mrRHjvvjnqVxpmEPSzLBtAiEAzZAE0umK8la6wbNDpGQSpCUYiFBtTCiH0E9+1jRDVVsCIQCmBZkfGglBzwBkpdwjU7BORrtk2OedzCP//ZeT4erc2wIhAIYCvvJVKK1GV2hSGuYAC5sRSAYOEMMWpTvrp2+0ELWDAiAn5W5/h90zoFAC38y//IuAtASyXmGCjR0VllZHpmGvQwIhAKQX9mghyqZ8XPl3DkNPx5iOyqJeJ1eSolPFSciJ7BEq";
    private static String publicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsi3i9hrQRntgo8zBn4bkV3UHcGH2vWlITkP+FKA0dLjSUQ8ZV79/jn1835VlfUda446QGCdlkpfrI7ow4GRD1Q2ii2cTTaSF7s9qqX3oHnqCneyWnpWlTnduUk2lcewcEML5w9OnUC2bFBn0zr6zimK/ApKu9UCDyEL+gNgf5LSuAvPVw96vgzQAD7mah1FiatC+tdqRFO9bRo/w7GPS8xJaLvJD0CPRbR/eheC7+QFhOfpTK9ZlVTMdEhRcL14/zpN6io4w6jjG2Al72QKhD3GFMLEdTft3//WBK6c0V9F28beq1ILkOa7PD0ehLeOd86qkbWmXsL7t6BBSf3GANwIDAQAB";
    private static String privateKey="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCyLeL2GtBGe2CjzMGfhuRXdQdwYfa9aUhOQ/4UoDR0uNJRDxlXv3+OfXzflWV9R1rjjpAYJ2WSl+sjujDgZEPVDaKLZxNNpIXuz2qpfegeeoKd7JaelaVOd25STaVx7BwQwvnD06dQLZsUGfTOvrOKYr8Ckq71QIPIQv6A2B/ktK4C89XD3q+DNAAPuZqHUWJq0L612pEU71tGj/DsY9LzElou8kPQI9FtH96F4Lv5AWE5+lMr1mVVMx0SFFwvXj/Ok3qKjjDqOMbYCXvZAqEPcYUwsR1N+3f/9YErpzRX0Xbxt6rUguQ5rs8PR6Et453zqqRtaZewvu3oEFJ/cYA3AgMBAAECggEAMBhGvRt7wKqo6U3prb/hfuHuAh3PVk1G4SllOfgMcL06t/qVYqQkMDgbLtbKsEhEdkwTBRINTsaHB/vIGam2zZmCmJJYGYshQ9bk+Z8sup1jCHHGLkRCfK2sL7cZw2hyIuweMwe8j93fkEt5Ux0SpXYGDRG+/CBP8gxzWh/8a+UOAGdfgY07qfSGsJG1OhACHlQaNPQfSHPHpG5qKa86gkd+bnFFMYGaOSoax1OJb3ej5hJPSmi04yeBcOr6bIPKKTO8EvK11AWZ7d7JQ7Vju+RnZKDnxriFBCpcXY+CuCHCe96LiAS9ZRVk+2RB5LDUmi/rrCSfWVnhQzWwfzNeQQKBgQDcWgi7ScgRqNlIVWBLm3fyuTmnAXJXYM9RRKcgq3rcpplTV+G0r1ugAGl5Vt5+ikbdiwefNZPTmMwjcDFaHzzDhRNgzanZN36SjZwB/tcsKfZ1UrEKMvUa7cdFR9f6vI7X2ts4YFM9ZTdOaIABiY7LCJPwF4+uaSx4rl228J3s8QKBgQDPAULcEAflYfL6G2rolR5YxBxHtKmw2m4gLLcUuyULdo5CsegD7i2kUSAJpTxneNfQVBB3K+Qq7slBsa76be6B29ZU+V8MnBLFL8LIgwrJxHRxxB760+/SIpmpjkGLosdJbHWXuWJ74ZhwSZDQ6v/bliI5ykjFGfC3ULGKf2bfpwKBgDybnw80qg0BblJQ+V2Sc5mVjN/8q+CQxjVKNtXrlK2gPUgJQhGasg7+TgSL+kK/8eH53azS5cgrnOqab1ikgugccEoOB4uxkeTpgmGiILkLCbkXd46gmX5ArLDHpAZZWsjQb7+/7uVSM7YjVCIYHeiGgXpmYKTOVOcUvr4UzLthAoGAIQBCeHLVz2veIKFeWdXsEJqzJ3tV0iwAgoCeb1+meD1eUKOsKXQ/MoL8aI0/I//P6BZ+yYDlzzJiQ2bgTmxYKbuyX7mhvZDkLSuAEhdSEPiQ19I8XiCLiySY+r8DFA/PVZVE6ftUCAvfEYeo3qWYRJmauzvUfhe0mIT0yO+mpuECgYEAvgUrwfSbC71RuxsuI/ZPW9oaN09I2sPzSbF/fN7YfM25uK1+Y0H2q+wPnnqcf+ZKrHjmFgxwN0W+98U7dYBnUOab+Z1lnV7Dt/gcCdXNZWCt2o2lfOGfQ3+hgsdjhasgrEMFm+SwsIVGA+DxiHMerVD3titZ4t+LblVait5jwvw=";

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512);
        KeyPair pair=keyGen.genKeyPair();
       // toByte(new BigInteger("12"));
        byte[] publicKey = pair.getPublic().getEncoded();
        RSAPublicKey rsaPublicKey= (RSAPublicKey) pair.getPublic();
        RSAPrivateKey rsaPrivateKey= (RSAPrivateKey) pair.getPrivate();
        System.out.println("---Modulus");
        System.out.println(encode(rsaPublicKey.getModulus()));
        System.out.println("---Public Exponent");
        System.out.println(encode(rsaPublicKey.getPublicExponent()));
        System.out.println("---Private Key");
        System.out.println(Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded()));

        System.out.println();
        System.out.println(encode(rsaPublicKey.getModulus()).length());
        System.out.println(new String(Base64.getEncoder().encode(rsaPublicKey.getEncoded())).length());
        System.out.println("---");
        System.out.println(encode(rsaPublicKey.getPublicExponent()));
        //System.out.println(rsaPublicKey.getPublicExponent());
        byte[] privateKey=pair.getPrivate().getEncoded();
        System.out.println();
        System.out.println(Base64.getEncoder().encodeToString(publicKey));
        System.out.println(Base64.getEncoder().encodeToString(publicKey).length());
        System.out.println();
        System.out.println(Base64.getEncoder().encodeToString(privateKey));
        System.out.println();
        System.out.println("modulus:");
        System.out.println(rsaPublicKey.getModulus());
        System.out.println("--");
        byte[] array=rsaPublicKey.getModulus().toByteArray();
        for(int i=0;i<array.length;i++){
            System.out.println(array[i]);
        }
        String string=new String(org.apache.commons.codec.binary.Base64.encodeInteger(rsaPublicKey.getModulus()));
        System.out.println("modulus length:");
        System.out.println(string.length());
        System.out.println("---");
        System.out.println(string);
        System.out.println("---");
        System.out.println(string.length());
        System.out.println("---modulus");
        System.out.println(rsaPublicKey.getModulus().toString(16));
        System.out.println("---");
        System.out.println(rsaPublicKey.getPublicExponent().toString(16));

        BigInteger modulus = rsaPublicKey.getModulus();
        BigInteger exponent = rsaPublicKey.getPublicExponent();
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec rsaPrivKey = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(prK));

        KeyFactory fact = KeyFactory.getInstance("RSA");
       // PrivateKey privKey = fact.generatePrivate(rsaPrivKey);

        Cipher cipher3 = Cipher.getInstance("RSA");
        cipher3.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        byte[] encodedBytes=cipher3.doFinal("Stris ".getBytes());
        System.out.println(Base64.getEncoder().encodeToString(encodedBytes));
        Cipher cipher4 = Cipher.getInstance("RSA");
        cipher4.init(Cipher.DECRYPT_MODE, kf.generatePrivate(rsaPrivKey));
        System.out.println("AAVg0KoNZ5YgC2S5q5Rj9IG6u0SEYZ5CWvFlUhhfHHotLXiwbIgT6kyA36oM9fNO/36GjEXWkIhvnk7ZNJvybyc=".length());
        System.out.println(new String(cipher4.doFinal(Base64.getDecoder().decode("AAVg0KoNZ5YgC2S5q5Rj9IG6u0SEYZ5CWvFlUhhfHHotLXiwbIgT6kyA36oM9fNO/36GjEXWkIhvnk7ZNJvybyc="))));

    }

    private static String encode(BigInteger bigInt) {
        return Base64.getEncoder().encodeToString(bigInt.toByteArray());
    }
}
