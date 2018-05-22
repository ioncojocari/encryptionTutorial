package com.example.demo;

import org.json.JSONArray;
import org.json.simple.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class EncryptionDot {
    private static final String modulusBase64="qZKzP/cSCnf/CrWHw7BjQoiOOCR7S5JkXuDZMNGFnSFCp1NFNMAu+NRL5d7CPjZb4YzEXJZETLIlsyqIujajogoNwIl0tbQVEgVzQteVy78xm16PXEmSOq0mBOHFywpyu4kJZ/ktHW2MOv3A8+8x3m6hupOh+xosALU1ZwGUd5lex2VgxNgj0acP0TDCeFTgJYCg6SEJ4YjutlBKb40eVaOaTnA0vFzpu53GCYKbIY+JZj4MZ6Asst7mMG4g5Hn9WhvGyFzACfMcqgNHUr2rPYgK79sxeIy0lWw0PoiN6Z4PhYNrGmNJZ4Hh13LjuNtY1U4ePjXmM7zESS6qE/Um5Q==";
    //private static final String modulusBase64="ANeV/sIqweLYA+OLpOn8TJFWgk66+mGF8l1e4bB+NVp93IrvAZw3LONjLMR24ofIV3xe/cNppcfcB6CzlxcliwE=";
    private static final String privateKeyBase64="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCK3dpN+mdn+3MeAAedS6hVmdIj91fT8ovjDpwhah7MYQSuJyFTFTZlvLTptHRXi5JqAyukPNss1R+hOoNaTRaiNmh7iXzxhfCTQ6LFNyaFnGwzPzbUOuaovExxB6DFQ975WGUUlIedv4kAobW9KTvoHFlf1wST/cAHdq3gsfsjL6OInu50OQivbKF/ZYmnZ54nGqpNrco5OfhCLqmZxKyagL0O59w9R9NoeziyelpIy5teY0eRYy6TmcCmd+gJeBEHRTTcPCXAs4sNAxUqv0+oILJFz8gD7iCms9D8cAZ0tbKQ/L8oQmPyg5a+A/G78al7uBAQr5D+LanNuDk5KQO/AgMBAAECggEAG72d8p3nMlqE8KVI4B9RDWihFhY/MVPupfkxL2uno2RuLuwaOCKwrzo3Wch2xVZYdV0UxsZxck9Q9kBeJRfpQZZk4OjiUM1U9dY5GfSeeWp6IvKp3VO26FZVXu8LKt0YGe+Sob3aKaUtlTa4u4UVNpUTNZYakAXM6rDGo3BqnuiFoORaKyGSBIiN+vsE7lXqImM33zDDTPYZ1u1ZjFwhKNNMIihy8ydZ4DEW7kFxcsauh6NIK1GBpTGbqeBdiLm9glMi1d9XA0TUWlwhnWlvBYVCiIYlXF/3I/ZnXu+jUa4abEaaVn0mnI04onKbf3MvPEdkWfubK1Seirt0A1XioQKBgQDRRrzp8x4nVg6iQfrmHFiZBLRCKQqLCn58iejJdfZOHnMQzve728EKbCIQj8yj4zM255NAbotdgLAXBriiIe7V+plxSMkus1VWV+fZsi+Uxgag+GvwLIlHHR3BuaX5QjKm8D2m3P5gBCY0pd12BuLjKk6yEPsN370tGg2sY3tt9QKBgQCp3tK0p/u6W0WzdT42zAASWM/MIkdfc7DylmvhC4bFoeaf3PvWUYaHw0Vip1M5A0aNo3A3qD8K5pQ7+cXazklc6mZdLAFg57/TlFMULS4bvBVfo7oUBKenyy3PpTQ5cQ6Ag41McUncKgvv++3lLhVK027UeABv5gzNvvfuFJrGYwKBgDZmeP0noZMAH9W16Q6gF7fPLTx1hrQWCkM9ZjHYZCpyZ97arjM7MLcQ3NoJR1zi1pFTKUf6dGfd5+hUavJad8dP2UtFbGajX2+Ockp4sz2tLFPzv7szGIxP2UhehfKBbsFOefhCQi2bZoMRCrx/lcMPYF2RZHF606ZBu4QwaAlxAoGBAKFprAoh/R7Nj+PAqodjPUYxwfKVOADJbAB3UrcbPssY1o5da6fw+8aRlKLzoC7yn94CyaT5aD4xb/W1l34curVhvjcWk5DDZhHkRTVM4R1VkXG5oB1Zdv4IINXIg2F5vm/7kQ5sL+TWx1UYrKszKORbGFsoVDIABXaT2f8O8StFAoGBAJB7gvF0GhoOxwklb/j7xAAS7UTUIpuyGqpTF748E6hjxGwGLUBYxd/7VHHu0d2k9bGFYrU6FnEfDV+jLdv5OhfaWSzWO4eQV4GRnvV9favYv1smBrRBhHuiBQCYMbxrNr6biJr9scXRWWo2EJme6mT4jYlugDQQYh35ecaT4Y+k";
    KeyFactory kf;
    RSAPublicKey publicKey;
    RSAPrivateKey privateKey;
    Cipher cipherEncrypt ;
    Cipher cipherDecrypt;
    public EncryptionDot() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException {
        kf = KeyFactory.getInstance("RSA");
        publicKey=getRsaPublikKey();
        privateKey=getRsaPrivateKey();
        cipherEncrypt = Cipher.getInstance("RSA");
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, publicKey);
        cipherDecrypt=Cipher.getInstance("RSA");
        cipherDecrypt.init(Cipher.DECRYPT_MODE,privateKey);
        System.out.println(modulusBase64.length());
    }
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        EncryptionDot main=new EncryptionDot();
        main.tryDecodeNet();
        List<JSONObject> jsonArray=new ArrayList<>();
        JSONObject obj = new JSONObject();
//                obj.put("date","2018-01-01");
//        obj.put("dateTo","2018-11-01");
        obj.put("sum",50);
        obj.put("description","invoice nr 5");
        obj.put("correspondentCount",112113814);
        JSONObject obj2 = new JSONObject();
        jsonArray.add(obj);
        obj2.put("sum",50);
        obj2.put("description","invoice nr 5");
        obj2.put("correspondentCount",112113814);
        jsonArray.add(obj2);
        String text=new String();
        if(jsonArray.size()>1) {
            text+="[ ";
            for (int i = 0; i < jsonArray.size() - 1; i++){
                text+=jsonArray.get(i)+ " , ";
            }
            text+=jsonArray.get(jsonArray.size()-1);
            text+=" ]";
        }else{
            text="["+jsonArray.get(0)+"]";
        }
        System.out.println(text);
//
//        jsonArray.add(obj);
//        JSONObject obj2 = new JSONObject();
//        obj2.put("sum",5000);
//        obj2.put("description","invoice nr 5");
//        obj2.put("correspondentCount",841858747);
//        jsonArray.add(obj2);
       // String text=jsonArray.toJSONObject("").to;
       System.out.println(obj);
        System.out.println(text.length());
        text="AKtFhNE8f2BcgwuoJqVfab9jz30Th9NPPJ0cJ6ozEtbcB9/nZ5IG+BlplOv7//nFJHCvGsKWahghvSR/txoSNwo=";
        byte[] encoded=main.encode(text.getBytes());
        System.out.println(Base64.getEncoder().encodeToString(encoded));
        byte[] decoded=main.decode(encoded);
        System.out.println(new String(decoded));
        System.out.println();
        System.out.println();
        main.tryDecodeNet();
    }

    public void tryDecodeNet() throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        String encoded="ABt0vKzuJcIZq9UDMsD0sXIjSPp5+Cml7p0iKk8Vmb6kmlonMiQosq+YwGNQjEi66W702zSOTx3uQ+dL1O6gwekCdhSJvfC1/1OrO4CGybtfoKAkyoSYeSG6jPV8wn+rLjgY9F8G4KxoOeWKKq3DkURryT2+e/s9T9JgSExS5RLJJEOXViLx2h/XlKMcM7m6xs1EpklKZacSxscpz2SxUvCSfJgr0q2NJFztpSCjcwLHauXL97VSrmZPckhFEiYh1w0HWwDoS58HsNl4SaiK6Zs1oXWYQbvG/eS7SrpJ8gTSgC5UOBLi8lSTZkjOduVq9jUvUi5SP4fcXHrVdAGTsXg=";
        byte[] encodedBytes=Base64.getDecoder().decode(encoded);
        System.out.println("encoded length:"+encodedBytes.length);
        byte[] newArr=new byte[encodedBytes.length-1];
        for(int i=0;i<newArr.length;i++){
            newArr[i]=encodedBytes[i+1];
        }
        byte[] decoded=decode(newArr);
        System.out.println("decoded length:"+decoded.length);
        System.out.println(new String(decoded,"UTF-8"));
    }

    public byte[] encode(byte[] data) throws BadPaddingException, IllegalBlockSizeException {
        return cipherEncrypt.doFinal(data);
    }

    public byte[] decode(byte[] data) throws BadPaddingException, IllegalBlockSizeException {
        return cipherDecrypt.doFinal(data);
    }

    public RSAPublicKey getRsaPublikKey() throws InvalidKeySpecException {
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(1,Base64.getDecoder().decode(modulusBase64)), new BigInteger(1,Base64.getDecoder().decode("AQAB")));
        return (RSAPublicKey) kf.generatePublic(publicKeySpec);
    }

    public RSAPrivateKey getRsaPrivateKey() throws InvalidKeySpecException {
        PKCS8EncodedKeySpec rsaPrivKey = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBase64));
        return (RSAPrivateKey) kf.generatePrivate(rsaPrivKey);
    }
}
