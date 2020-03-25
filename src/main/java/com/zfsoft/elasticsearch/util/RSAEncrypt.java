package com.zfsoft.elasticsearch.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjian
 */
public class RSAEncrypt {
    private static String YXPT_PUBLIC_KEY_STRING = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCY9T2bu//LyaHgwjUw9xUIcGgRIuMhK8B+2fuxeg5OybBaFbyf3PjhH3Zf5NmY761cAnO6/Tmtb/gR0WlacZ+QBlQ61VZ0zvW0bijmlGg0j9FStVYiSIzv2U7u2TpD0za9m9RSpPqZk7Cle7RqwWxHr2V+S+He5aaqMSWaxv4uXwIDAQAB";

    private static String YXPT_PRIVATE_KEY_STRING = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJj1PZu7/8vJoeDCNTD3FQhwaBEi4yErwH7Z+7F6Dk7JsFoVvJ/c+OEfdl/k2ZjvrVwCc7r9Oa1v+BHRaVpxn5AGVDrVVnTO9bRuKOaUaDSP0VK1ViJIjO/ZTu7ZOkPTNr2b1FKk+pmTsKV7tGrBbEevZX5L4d7lpqoxJZrG/i5fAgMBAAECgYB5MrFLag7Ikg8Tga8WCmH/GtpK4cLo8LsJiH3X5efaZBO5fcnFPhlygDSSzUnh1eAerM641pdSJwuAStdY0/lirdJZyDaLjMOW/Y3aXByfqIhvRac4HsN0nI4DSov7ekrDf2SAwyG/boKRMU4KlsXpJXrOFZoU4QkL+dkOj6F/uQJBAO2rGc8kqoivzi266VsPlX3o698zBF/8jO30lPxZUX/8l7ZO7HMVVjciYX51DeGQcy6sWDzjv57xScgi9NN+0u0CQQCkwXv0529cxaHH5stmC3nW2mqzdV8t39BJfpDvlL8VVQvNZQT7G6uvU8PYLduJc5WzODEkyVnkG5dX8/rkruD7AkB0boAEVKgeslHFYW30qFvKBROYkruv8l9wK4PZZEBi/PGt5Fg9wNQtynAMrXeRa1yiHthTbBdx3C8TFtztx5G5AkBTAplt312gpILKsWIx2/5bXlj8alq2RlovbXGhBJTyLpNvvUIxMtPh1paKSTCfpHj8f4djPX/pCm3KhscWiXYZAkEAzAbmZP1JTDhIqwFCFxKuLBkSVGC1TBKLKvd4tBZ/5MizsPxKYRiuSx4EaHCAew4P/fnVvuAwlUX1J5DBuwM/NQ==";

    private static Map<Integer, String> keyMap = new HashMap<>();  //用于封装随机产生的公钥与私钥

    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
        genKeyPair();
        //加密字符串
        String message = "df723820";
        System.out.println("随机生成的公钥为:" + keyMap.get(0));
        System.out.println("随机生成的私钥为:" + keyMap.get(1));
        String messageEn = encrypt(message,keyMap.get(0));
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn,keyMap.get(1));
        System.out.println("还原后的字符串为:" + messageDe);
    }

    /**
     * 设置基础参数
     * @param YXPT_PUBLIC_KEY_STRING
     * @param YXPT_PRIVATE_KEY_STRING
     */
    public static void setKeyMap(String YXPT_PUBLIC_KEY_STRING, String YXPT_PRIVATE_KEY_STRING) {
        RSAEncrypt.YXPT_PUBLIC_KEY_STRING = YXPT_PUBLIC_KEY_STRING;
        RSAEncrypt.YXPT_PRIVATE_KEY_STRING = YXPT_PRIVATE_KEY_STRING;
        keyMap.put(0,YXPT_PUBLIC_KEY_STRING);  //0表示公钥
        keyMap.put(1,YXPT_PRIVATE_KEY_STRING);  //1表示私钥
    }

    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0,publicKeyString);  //0表示公钥
        keyMap.put(1,privateKeyString);  //1表示私钥
    }
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        byte[] dataReturn = cipherDoFinal(cipher, str.getBytes(), 100);

        String outStr = Base64.encodeBase64String(dataReturn);
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        try {
            // 用私钥解密
            byte[] srcBytes = Base64.decodeBase64(str.getBytes());
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privatekcs8KeySpec = new PKCS8EncodedKeySpec(decoded);
            PrivateKey priKey = keyFactory.generatePrivate(privatekcs8KeySpec);

            // Cipher负责完成加密或解密工作，基于RSA
            Cipher deCipher = Cipher.getInstance("RSA");
            // 根据公钥，对Cipher对象进行初始化
            deCipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] decBytes =  cipherDoFinal(deCipher, srcBytes, 128); //分段加密

            String decrytStr = new String(decBytes);
            return decrytStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分段大小
     *
     * @param cipher
     * @param srcBytes
     * @param segmentSize
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] cipherDoFinal(Cipher cipher, byte[] srcBytes, int segmentSize)
            throws IllegalBlockSizeException, BadPaddingException, IOException {
        if (segmentSize <= 0)
            throw new RuntimeException("分段大小必须大于0");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int inputLen = srcBytes.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > segmentSize) {
                cache = cipher.doFinal(srcBytes, offSet, segmentSize);
            } else {
                cache = cipher.doFinal(srcBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * segmentSize;
        }
        byte[] data = out.toByteArray();
        out.close();
        return data;
    }

}