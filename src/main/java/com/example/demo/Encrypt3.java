package com.example.demo;



import sun.security.rsa.RSAPublicKeyImpl;
import sun.security.x509.X509Key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Encrypt3 {
    public final static String privateKey="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCZnniFBgGB9HJfflGmitVSHJqI7iPiZ0Bc2onUU3fE2DoqgikVld4WxBZiCn3qH30jIAEgQMdlEXpotvNJShfJ/agUjVHkusiY7olhzyWsFxwOiAR4D26MUTEsRAcf+Dy6vpC0sgRI5R0z6CmAIT1DVqgm7FUltOh6oFOCNxjM01TamGlJzdplRXZmnFY8Fp/1z8UJg+qPxSqAp9osagNLPh64TaADX1a+ygzQ/cjc7YTYZCUPLgTLRGYgF5Y/YlhxHq472sv7a1wJRapnD2+p0eTxZC5vxpalJqajNfimlFGn+KSr+dLH7QZKexHAcybsbUz55CJATcIeSFBk/HZ/AgMBAAECggEAQwsQWi3m1oqSp+5/QAnzU6S8JOqZqqOZSWzvHkVXTgd4SzkHKS6BALqPcjZWAanAeWXsV+EtpIReQfkmcGPvwe38Pwv2I7cI+QWR26gGLvbe/dCK8sL5Yi/FICaIXYF/L5kfmlze1dl4R0EZa5wWGp9lP6eA+bXciDgUeg4Fus4LUHtmGzBw3PIODsZ0uAb7TnKCGlTCz/PEG6iBtdYxouUqIrkB3Vo0MRujTPmJRWYU2ae3kG8WBmVLIb+d5K/TKhIKK+xrGWO6kiWjJYncqhFa2YfCX/w2H8BiK8YgjtdzAVmejzQPd4ojssxtCfoE+IFI0Ue5nx5wDmWcli+xsQKBgQDN4jF8bN7XgdvnsClkx8MAK24i8vYh5MMPpHtiNm0pt2YWtmmT4Hw5oSjVHCe/WyYL0uHJNfR2EV8+9aDS5OL2B1+ZBBS2/xbVIz0WsGOegxn3iaRiThB/YdxCw3dqRi1fSHRpMasl8ChjVlwDPt+GEgKg0cOlhf2IJF0O6Y0RDQKBgQC/A15vWtx3LX7neb4dBU7sFs0/hL5kb03A3Bi6yBvStjTR7hB42EsxfjloLSh35KIqRy6t55fopZDctPlVx5sUD9+WseyBhSy71R2G/lG4YIm8JvFUcuwULypdwCD415X91yRthWsPVj25hKbRcVlaQ/Ux/w64tLpQc8XjTsCKuwKBgDaaJ6+tHAihuxWeRcwhopjX69S/BQIGLqlmYQayEo9+wUeCx0tdvPR3xmGGnxAD93opSpJXiecKQm1rLWThKURAvlzRlUDXNFIAj4IF89WlRWy+B2e+9mciKA1Y08HOrNfZWm5Pqulb4Qo/bBW8RgHDowjNuQqcF5pUso1y6WO9AoGAAhKASMb8k1JsCQjaFiiDla4dpe3DvwUyG8CHiCc5ngyaABkMFrOKBUxwRQAQa1SQqdLB3+MeU8jQDITuG48h0JLngxQFE9alCJikTF2f91ccx2zIu3jPtNxaIlA+S1TTbMCTxZhn8++klcmpoaYQKRmJpHQ27awQRWUsJW4qSpcCgYBCU/s/fi0Hbbl70D2Tsf8yebQxnrn8bT37vnlI3KUQ2ZsiBPWvSjv18lHHQM0mUtkJMfJrAde5O4j/WId2V/wlA7ihOOIF9aBOED0epQnF/No1cc0991NYFADLM81h4dzgaVMJ5m+JKNhCbgNq1hiSX8/CZHwN65lb6p8TotvQrQ==";
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
        String str="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpe5YQnL72qgATYN3FoCSciNlQTYvUmEZE5iZajwZlNIT5Vw+Ig2HRHnY+dE9hrRr/4MSYBE2jczzYauOpMAPEfang2OlfLcKg7q4bBUJjJJ10E47rOpsRdmgRpaQoDpi4Uem54d1172DgqhH8T31EGMGSzS7fXSu9V8gKmO3UctT4HTeXoTb45Jb2TbmNQ3TOLczR00EueIbnY+NPHv9+mR5Er2F05rN51Wh9RFwImDDPBBRGZHExDab++Bi1ORMn/d1NXWABLcb6FHMG8KB21V0Itf9k0dkSDeq44du0vHU5aFuZJZU0YDM5sIfSB2QzW/8jqlPkiD4d/+1GB8+HAgMBAAECggEACSrnNiTUa5FMib/OnzcHY/Q/FQnsy2YlPurYHkPoS6conjgoXfEFZINqT/yIorbzY+3rlHF/JZ1BKE+32tn/Cizl+ISjMwPNqFjg0BNbwPMlFTdNnsm/w5UNEg9MruIDPs0ngGeITC3TprNsX6KJGxrftaolo+1tRUOvTWtySfCJQzNBnNEn37w/lY3TlUZJgZbpv5gfuZJaKwfWAYGk3/nxGGg1TUeJ/HeLPpJGSbiFC+qyzovZzWk7+ppFNJb4jku5nx1gJ2tO4tQkd3nA7gbjWsEnUriqA1EC5M/A6JGGz7phJSYR8U51H2e3cA1jkLCVcpM6W0LoPj4WbMzR0QKBgQD8XE0ScAXpWzIIaqRZY5pTGEmsLUP+g7Nm/pm8iwpSbW1Uk8EUXd9IQ5ECtPHM6Y7ZEbAm4S8mqpuj8Iqhrp8/Y7esvLoFzgWa5nrY7eiLraIlHg7TV8L7x0kSj8OCzxklu9Weyexes9oQwS3/0AI9UUFCMlib+5gjQvDoucl7WQKBgQCr7U4+tpudRr4Wih2PBceMvbtyR9h2d34DI+7VbYVgCq9bd3+eFEvb3bGtxZzzNfE9bAhpObAHx7l53F1yfIOlmjmhYBw4Rh2t1f/Dk5A/jX3uygwxw9VmpS35LOYvRz2IcwQfj4PNw5UNUmRt4gqq9SiFZfgId0DgWepbW42l3wKBgQDT6M3vRZIxOhrxZltLlI/qZuK2aexGtGDZg8GkyTyJqsm707a2W307OUFzfG3vG9CQBoJco4rsdSbsqvg+fFvM5vc9Xdz3MuESCafusRgs8kjN0kewwGlC8z0xEPejyDPds7TV+ck6XNwoZY8y2ICI7MpXw98UXFiMg5G4WwoNQQKBgGKGguGTmkgeQ079ZYUR5dJ05JfQLm9teOxpyjPLKLTqSqXbLGo17WcZe/o+lfwWvKHbaFKM+aDrxxHbxD2Yt4TOhhpBzso8/EMjsQeuZ+rTtgiDB0v5kzDYlikGnv8XD19Dsmg3wWvImoFg5ybFdCGWjtsrnWGUnbw0Kwahx+H3AoGAcLHN5l60q/wI/kTJ/P/LKUEFCDjn0mjuUWpebqCnuoC/KO54xiyKBKJmfV2zIH7smM99lOoLoriVslhsT1pLyvTpMZu569i8aSgVQBFVSPagUw/4qlzc6nljOW6O1ux93AvV8XIITzQ6yNU1XiKqV3XNAy/TLhDXX8DlInJRkSY=";
        String publicKeyString="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqXuWEJy+9qoAE2DdxaAknIjZUE2L1JhGROYmWo8GZTSE+VcPiINh0R52PnRPYa0a/+DEmARNo3M82GrjqTADxH2p4NjpXy3CoO6uGwVCYySddBOO6zqbEXZoEaWkKA6YuFHpueHdde9g4KoR/E99RBjBks0u310rvVfICpjt1HLU+B03l6E2+OSW9k25jUN0zi3M0dNBLniG52PjTx7/fpkeRK9hdOazedVofURcCJgwzwQURmRxMQ2m/vgYtTkTJ/3dTV1gAS3G+hRzBvCgdtVdCLX/ZNHZEg3quOHbtLx1OWhbmSWVNGAzObCH0gdkM1v/I6pT5Ig+Hf/tRgfPhwIDAQAB";
        PublicKey publicKey=kf.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString)));
        Cipher cipherEncr=Cipher.getInstance("RSA");
        cipherEncr.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] encrypted=cipherEncr.doFinal("wtfs".getBytes());

        PrivateKey privateKey =
                kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(str)));
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        String decoded2="hIIJdsDkxI9ChiJ0QTYDUhTz9cL3pcIQJlGiZrQQ6ytSLAq/AW1FGqvuZ76IbXKeKhuE0+DKy9fR0YI9mBt6URqS7CrGeGuoox66PNt+Sy+zrvMPg5FWm1lzCORNyusfg0iGkmLE0CGB8lcgHkLPsClKV1+1TL7icCvpksv4vpM=";
//        byte[] bytes=cipher.doFinal(Base64.getDecoder().decode(decoded2));
        //System.out.println(new String(bytes));
        byte[] modulus=Base64.getDecoder().decode("qRGstKpzosQkKdhsigzpa7hQl+TvtMMOGaSc8K9CjDNBicGMYpwgpcGHedWHeUk2UDqaUk+KDcxcU/s86B+iwUYVztfcjAbQQvmkb/5KMh0XYhBaLD3BZMjTWtU+pBDuOtqqYfMOeZBLZbQ16/eZjzGh9LqUbDfZl1rNN8YcM9Y=");
        byte[] exmponent=Base64.getDecoder().decode("ABAQ");
        BigInteger bigIntegerExponent=new BigInteger(exmponent);
        System.out.println(bigIntegerExponent);
       // RSAPublicKey publicKey1=new RSAPublicKeyImpl();

       // kf.generatePublic(publicKey1)

    }
}
