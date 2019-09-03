/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/
package com.ken.sys.common.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <ul>
 * <li>Title: 匠桥ERP系统-WxPayConfig</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2019/3/2 15:05
 */
//引入spring 放开相关注解
@Component
public class WxPayConfig {
    //加密方式--MD5
    public static final String encrypMD5="MD5";

    //加密方式--HMAC-SHA256
    public static final String encrypHMACSHA256="HMAC-SHA256";

    //交易类型
    public static final String TRADETYPE = "JSAPI";

    @Value("${minigrogram.appid}")
    public  String appid;

    @Value("${minigrogram.miniSecret}")
    public String miniSecret;

    @Value("${minigrogram.mchId}")
    public String mchId;

    @Value("${minigrogram.granttype}")
    public  String grantType;

    @Value("${minigrogram.paykey}")
    public String payKey;

    @Value("${minigrogram.openidurl}")
    public String openidUrl;

    @Value("${minigrogram.notifyurl}")
    public  String notifyUrl;

    @Value("${minigrogram.payurl}")
    public String payUrl;

    @Value("${minigrogram.orderqueryurl}")
    public String orderQueryUrl;

    public static String getEncrypMD5() {
        return encrypMD5;
    }

    public static String getEncrypHMACSHA256() {
        return encrypHMACSHA256;
    }

    public static String getTRADETYPE() {
        return TRADETYPE;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMiniSecret() {
        return miniSecret;
    }

    public void setMiniSecret(String miniSecret) {
        this.miniSecret = miniSecret;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public String getOpenidUrl() {
        return openidUrl;
    }

    public void setOpenidUrl(String openidUrl) {
        this.openidUrl = openidUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getOrderQueryUrl() {
        return orderQueryUrl;
    }

    public void setOrderQueryUrl(String orderQueryUrl) {
        this.orderQueryUrl = orderQueryUrl;
    }
}
