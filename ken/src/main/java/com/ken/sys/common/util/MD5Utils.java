package com.ken.sys.common.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * MD5工具---加密界面
 *
 * @author geosmart
 * @date 2017-01-06
 */
public class MD5Utils {
    private static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static final String MD5Encrpytion(String source) {
        try {
            byte[] strTemp = source.getBytes(Charset.forName("UTF-8"));
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
                str[(k++)] = hexDigits[(byte0 & 0xF)];
            }
            for (int m = 0; m < str.length; ++m) {
                if ((str[m] >= 'a') && (str[m] <= 'z')) {
                    str[m] = (char) (str[m] - ' ');
                }
            }
            return new String(str);
        } catch (Exception e) {
        }
        return null;
    }

    public static final String MD5Encrpytion(byte[] source) {
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(source);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
                str[(k++)] = hexDigits[(byte0 & 0xF)];
            }
            for (int m = 0; m < str.length; ++m) {
                if ((str[m] >= 'a') && (str[m] <= 'z')) {
                    str[m] = (char) (str[m] - ' ');
                }
            }
            return new String(str);
        } catch (Exception e) {
        }
        return null;
    }

//     /** 转换字节数组为16进制字串
//     * @param b 字节数组
//     * @return 16进制字串
//     */
//    public static String byteArrayToHexString(byte[] b) {
//        StringBuilder resultSb = new StringBuilder();
//        for (byte aB : b) {
//            resultSb.append(byteToHexString(aB));
//        }
//        return resultSb.toString();
//    }
//
//    /**
//     * 转换byte到16进制
//     * @param b 要转换的byte
//     * @return 16进制格式
//     */
//    private static String byteToHexString(byte b) {
//        int n = b;
//        if (n < 0) {
//            n = 256 + n;
//        }
//        int d1 = n / 16;
//        int d2 = n % 16;
//        return hexDigits[d1] + hexDigits[d2];
//    }
//
//    /**
//     * MD5编码
//     * @param origin 原始字符串
//     * @return 经过MD5加密之后的结果
//     */
//    public static String MD5Encode(String origin) {
//        String resultString = null;
//        try {
//            resultString = origin;
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return resultString;
//    }

}