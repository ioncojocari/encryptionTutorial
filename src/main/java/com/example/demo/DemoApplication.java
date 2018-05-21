package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
		SpringApplication.run(DemoApplication.class, args);
		test();
	}

	public static void test() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		String modulusBase64 = "y3EAGcatDKiuO+887Tn4rwoSQtJfSthf8x4xQ9lPeOU2XI2p/W++RfobrsGiHeBBZnO5thOPIHQpkv8yjU/vbdYo9QZ4kbrq/HD7ZkHYzTxDVTEgwiLbVh0TQbvvZTtQnBOmtBNw+BVPeNHSvv3nFNyCEaCxqk/E5f2zcy0arY0=";
		String expBase64="AQAB";
		BigInteger modulus = new BigInteger(1,
				Base64.getDecoder().decode(modulusBase64.getBytes("UTF-8")));
		BigInteger pubExp=new BigInteger(1,
				Base64.getDecoder().decode(expBase64.getBytes("UTF-8")));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec ks = new RSAPublicKeySpec(modulus, pubExp);
		RSAPublicKey pubKey = (RSAPublicKey)keyFactory.generatePublic(ks);

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		System.out.println(Base64.getEncoder().encodeToString(cipher.doFinal("SuperJson".getBytes())));
		System.out.println(Base64.getEncoder().encodeToString(cipher.doFinal("SuperJson".getBytes())).length());
		byte[] arr={83,117,112,101,114,74,115,111,110};

		System.out.println(new String(arr));
	}
	//83 117 112 101 114 74 115 111 110
}
