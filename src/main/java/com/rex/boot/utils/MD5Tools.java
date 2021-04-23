package com.rex.boot.utils;

import java.io.IOException;
import java.security.MessageDigest;

/**
* @author rex
* @description //TODO 
* @date  
* @param 
* @return $return
*/
public class MD5Tools {

    public byte[] MD5(String data) throws IOException {
        return encryptMD5(data.getBytes("UTF-8"));
    }

    public String MD5Str(String data) throws IOException {
        return byte2hex_lcase(MD5(data));
    }

    public byte[] encryptMD5(byte[] data) throws IOException {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(data);

            return md.digest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    public String byte2hex_lcase(byte[] bytes) {

        return byte2hex(bytes).toLowerCase();
    }

}
