package com.jc.lottery.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2019/5/27.
 */

public class AesOrMd5 {

    /**
     * AES加密
     *
     * @param data
     *            将要加密的内容
     * @param key
     *            密钥
     * @return 已经加密的内容
     */
    public static String KEY = "wuxianposyddhtdl";
    public static String encrypt(byte[] data, byte[] key) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return parseByte2HexStr(cipher.doFinal(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String();
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

    public static String md5OrAes(String password){
        String aesPassword = encrypt(password.getBytes(),KEY.getBytes());
        String sign = MD5.MD5Encode(aesPassword, "UTF-8").toUpperCase();
        return sign;
    }

    public static byte[] concat(byte[] firstArray, byte[] secondArray) {
        if (firstArray == null || secondArray == null) {
            return null;
        }
        byte[] bytes = new byte[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0, bytes, 0, firstArray.length);
        System.arraycopy(secondArray, 0, bytes, firstArray.length,
                secondArray.length);
        return bytes;
    }

}
