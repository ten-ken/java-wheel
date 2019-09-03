/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/
package com.kcppc.core.utils.wxpay;

import com.alibaba.fastjson.JSONObject;
import com.ken.sys.common.config.WxPayConfig;
import com.ken.sys.common.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.*;

/**
 * <ul>
 * <li>Title: 匠桥ERP系统-WXRequestUtil</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2019/3/5 0005 上午 11:44
 */
@Component
public class WXRequestUtil {

    @Autowired
    private WxPayConfig wxPayConfig;

    // 日志处理
    private static Logger logger = Logger.getLogger(WxPayConfig.class);

    private static WXRequestUtil wXRequestUtil =null;

    public static WXRequestUtil getInstance(){
        if(wXRequestUtil==null){
            wXRequestUtil =new WXRequestUtil();
        }
        return wXRequestUtil;
    }

    /**
     * 功能描述: 获取openId
     * @param code
     * @return: com.alibaba.fastjson.JSONObject
     * @author: swc
     * @date: 2019/3/5 0005 上午 11:45
     */
    public  JSONObject getOpenId(String code) throws Exception{
        if (code == null || code.equals("")) {
            throw new Exception("code is null.");
        }
        JSONObject json =null;
        //拼接参数
        String param = "?appid="+ wxPayConfig.appid+"&secret="+wxPayConfig.miniSecret+"&js_code="+code+"&grant_type="+wxPayConfig.grantType;
        String openIdUrl =wxPayConfig.openidUrl+param;
        String str = HttpClientUtils.sendGet(openIdUrl);
        if(StringUtils.isNotBlank(str)){
            return JSONObject.parseObject(str);
        }
        return  json;
    }

    /**
     * 功能描述: 除去数组中的空值和签名参数
     * @param packageParams
     * @return: java.util.Map<java.lang.String,java.lang.String>
     * @author: swc
     * @date: 2019/3/2 16:32
     */
    public Map<String,String> paraFilter(Map<String,String> packageParams) {
        Map<String,String> map =new HashMap<String,String>();
        Set<Map.Entry<String, String>> sets = packageParams.entrySet();
        for(Map.Entry<String, String> set:sets){
            String key = set.getKey();
            if(!EmptyUtils.isNullOrEmpty(packageParams.get(key))){
                map.put(key,packageParams.get(key));
            }
        }
        return map;
    }

    /**
     * 功能描述:把数组所有元素,"按照"参数=参数值"的模式用"＆"字符拼接成字符串
     * @param packageParams
     * @return: java.lang.String
     * @author: swc
     * @date: 2019/3/2 16:32
     */
    public  String createLinkString(Map<String,String> packageParams) {
        String urlParams = formatUrlMap(packageParams,false,true);
        return urlParams;
    }

    /**
     * 功能描述: 针对统一下单的签名
     * @param params
     * @param signType 加密方式
     * @return: java.lang.String
     * @author: swc
     * @date: 2019/3/2 18:11
     */
    public  String signToUnifiedOrder(Map<String,String> params,String signType) {
        String stringA = createLinkString(params);
        String stringSignTemp = stringA+"&key="+wxPayConfig.payKey;//注：key为商户平台设置的密钥key
        String sign =null;
        if(WxPayConfig.encrypMD5.equalsIgnoreCase(signType) || signType==null){
            sign= MD5Utils.MD5Encrpytion(stringSignTemp).toUpperCase();// 注：MD5签名方式--默认方式
        }
        if(WxPayConfig.encrypHMACSHA256.equalsIgnoreCase(signType)){
            sign = HmacSHA256Utils.sha256_HMAC(stringSignTemp,wxPayConfig.payKey).toUpperCase();//注：HMAC-SHA256签名方式
        }
        return sign;
    }


    /**
     * 功能描述: 对所有传入参数按照字段名的Unicode码从小到大排序（字典序），并且生成url参数串
     * @param paraMap   要排序的Map对象
     * @param urlEncode   是否需要URLENCODE
     * @param keyToLower    是否需要将Key转换为全小写
     *        true:key转化成小写，false:不转化
     * @return: java.lang.String
     * @author: swc
     * @date: 2019/3/2 17:12
     */
    public  String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower) {
        String buff = "";
        Map<String, String> tmpMap = paraMap;
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>(){
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds){
                if (com.ken.sys.common.util.StringUtils.isNotBlank(item.getKey())){
                    String key = item.getKey();
                    String val = item.getValue();
                    if (urlEncode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    if (keyToLower){
                        buf.append(key.toLowerCase() + "=" + val);
                    } else {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }
            }
            buff = buf.toString();
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e){
            return null;
        }
        return buff;
    }


    /**
     * 功能描述:调取请求
     * @param pay_url   请求地址
     * @param reqType  get 或 post 请求
     * @param xml
     * @return: java.lang.String
     * @author: swc
     * @date: 2019/3/2 20:27
     */
    public  String httpRequest(String pay_url, String reqType, String xml) {
        return HttpClientUtils.postXMWithUrlConn(pay_url,reqType,xml);
    }


    /**
     *
     * 功能描述: 验证签名的主方法 &&&&&
     * @param map
     * @param sign
     * @return: boolean
     * @author: swc
     * @date: 2019/3/2 21:21
     */
    public boolean verifySign(Map map,String sign,String signType ) {
        String generateSign= signToUnifiedOrder(map,signType);
        return sign.equals(generateSign);
    }


//    public static void main(String[] args) throws Exception{
//        Map <String,String> packageParams = new HashMap<String,String>();
//
//        int f =Integer.parseInt("010");
//        System.out.println( String.valueOf(f));
//
//        packageParams.put("appid",WxPayConfig.appid);
//        packageParams.put("mch_id",WxPayConfig.mchId);
//        packageParams.put("body","测试专用");
//        packageParams.put("spbill_create_ip","192.168.0.106");
//        packageParams.put("notify_url",WxPayConfig.notify_url);//回调地址
//        packageParams.put("nonce_str", "yjgwi9cdgeun858");
//        packageParams.put("openid","oPtYc5J0CC4KB8v8nx9lKRoY_p00");
//        packageParams.put("out_trade_no","CC1903040000024");
//        packageParams.put("total_fee","10");
//        packageParams.put("trade_type","JSAPI");
//        packageParams.put("sign_type",WxPayConfig.encrypMD5);
//
//        String mysign =signToUnifiedOrder(packageParams,WxPayConfig.encrypMD5);
//        packageParams.put("sign",mysign);
//        String xml =MapToXmlStrUtil.generateXmlsort(packageParams,true);
//        System.out.println(xml);
//        System.out.println(mysign);
//
//        String stringSignTemp ="appId=wxd678efh567hg6787&nonceStr=5K8264ILTKCH16CQ2502SI8ZNMTM67VS&package=prepay_id=wx2017033010242291fcfe0db70013231072&signType=MD5&timeStamp=1490840662&key=qazwsxedcrfvtgbyhnujmikolp111111";
//        String sigm = MD5Util.MD5Encode(stringSignTemp).toUpperCase();//"8EA82DC4C947B2EDD433BE1C070E37E" //注：MD5签名方式
//        System.out.println(">>"+sigm);                                // 8EA82DC4C947B2EDD433BE1C070E37EE
////        //3b75c142aebaa911f2571284e6327564469634657300b8e27da7b7443c3b1381
////        String ss=  HmacSHA256Utils.sha256_HMAC(stringSignTemp,"192006250b4c09247ec02edce69f6a2d");//
////        //BDE998DF04A39347D3C819ACA2ACC31DF23FD3950DD8FF81E6E8440E0A0133E8
////        System.out.println(ss);
////
////        String xmsl="<xml><appid><![CDATA[wx717aee3eba053d3d]]></appid><body><![CDATA[测试专用001]]></body><mch_id><![CDATA[1526982581]]></mch_id><nonce_str><![CDATA[2thlv9u0fxcrvexjuveul1rixkz0bt3r]]></nonce_str><notify_url><![CDATA[http://192.168.0.106:8080/chouchou/miniprogram/wxnotify]]></notify_url><openid><![CDATA[oPtYc5J0CC4KB8v8nx9lKRoY_p00]]></openid><out_trade_no><![CDATA[CC1903040000030]]></out_trade_no><sign>AF5B2BC5D4A4DC9707E71A128F151771698095AC41EBF8BC2E63EEA8F00EDA82</sign><spbill_create_ip><![CDATA[192.168.0.103]]></spbill_create_ip><total_fee><![CDATA[10]]></total_fee><trade_type><![CDATA[JSAPI]]></trade_type></xml>";
////        String result = PayUtil.httpRequest(WxPayConfig.pay_url,"POST",xmsl);
//
//        //System.out.println(result);
//    }

//    public static Map getOnLinePayOrderInfo(String orderNo) throws Exception{
//        WxPayConfig wxPayConfig =new WxPayConfig();
//
//        String nonce_str = StringUtils.getRandomStringByLength(32);
//        Map <String,String> packageParams = new HashMap<String,String>();
//        packageParams.put("appid",wxPayConfig.appid);
//        packageParams.put("mch_id",wxPayConfig.mchId);
//        packageParams.put("nonce_str",nonce_str);
//        packageParams.put("out_trade_no",orderNo); //商户订单号
//        String sign = PayUtil.getInstance().signToUnifiedOrder(packageParams,WxPayConfig.encrypMD5);
//        packageParams.put("sign",sign);
//        String xml =MapToXmlStrUtil.generateXml(packageParams,true,"sign");
//        String result = PayUtil.getInstance().httpRequest(wxPayConfig.orderQueryUrl,"POST",xml);
//        Map map = PayUtil.getInstance().xmlToMap("");
//        return map;
//    }
}
