/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/
package com.ken.sys.common.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * <ul>
 * <li>Title: 匠桥ERP系统-HmacSHA256Utils</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2019/3/2 17:47
 */
public class HmacSHA256Utils {


    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
    /**
     * sha256_HMAC加密
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    public static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    public static void main(String[] args) {
        String stringA ="appid=wx717aee3eba053d3d&body=测试专用&mch_id=1526982581&nonce_str=lvvz7lsn6u8ikptewg00sxvxqtlx7hlc&notify_url=http://192.168.0.106:8080/chouchou/miniprogram/wxnotify&openid=oPtYc5J0CC4KB8v8nx9lKRoY_p00&out_trade_no=CC1903040000024&sign_type=HMAC-SHA256&spbill_create_ip=192.168.0.106&total_fee=10&trade_type=JSAPI&key=wxfewsaasfafsadfsadfsadfasfsdsdf";
        String ss =sha256_HMAC(stringA,"192006250b4c09247ec02edce69f6a2d");
        System.out.println(ss);

    }
}
