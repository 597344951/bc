package com.zltel.broadcast.incision.sola.utils;

import com.zltel.broadcast.incision.sola.utils.Base64;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by jiapt on 2018/1/17.
 * <p>
 * description:
 */

public class DESUtil {
    private static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
    private static final String KEY_DES = "E4ghj*Gh";
    private static final byte[] IV_DES = { (byte)0x12,(byte) 0x34, (byte)0x56, (byte)0x78, (byte)0x90, (byte)0xAB, (byte)0xCD,(byte) 0xEF };

    public static String encode(String data) {
        return encode(KEY_DES, data);
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  需要加密的业务类型
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception
     */
    public static String encode(String key, String data) {
        if (data == null) {
            return null;
        }
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(IV_DES);
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data.getBytes("UTF-8"));
            byte[] encode = Base64.encode(bytes, Base64.NO_WRAP);
            return new String(encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String decode(String data) {
        return decode(KEY_DES, data);
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  需要解密的业务类型
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static String decode(String key, String data) {
        if (data == null) {
            return null;
        }
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(IV_DES);
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            byte[] decode = Base64.decode(data.getBytes("UTF-8"), Base64.NO_WRAP);
            byte[] bytes = cipher.doFinal(decode);
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
