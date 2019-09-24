/******************************************************************************
 *
 * 作者（author）：ken
 * 微信（weChat）：mlchao1992
 * 个人博客（website）：
 *
 ******************************************************************************
 * 注意：尊重原创
 *****************************************************************************/
package com.ken.sys.common.util;
import java.util.*;
/**
 * <ul>
 * <li>Title: 匠桥ERP系统-MapToXmlStrUtil</li>
 * <li>Description: map转字符串格式的xml </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author ken
 * @version V1.0
 * @date 2019/3/2 19:55
 */
public class MapToXmlUtils {
    private static final String PREFIX_XML = "<xml>";
    private static final String SUFFIX_XML = "</xml>";
    private static final String PREFIX_CDATA = "<![CDATA[";
    private static final String SUFFIX_CDATA = "]]>";

    /**
     * 功能描述: map转xml
     * @param parm
     * @param isAddCDATA 是否加入CDATA
     * @param fileds
     * @return: java.lang.String 
     * @author: ken
     * @date: 2019/8/28 0028 下午 12:43
    */ 
    public static String generateXml(Map<String, String> parm, boolean isAddCDATA,String ...fileds) {
        List<String> list = Arrays.asList(fileds);
        StringBuffer strbuff = new StringBuffer(PREFIX_XML);
        if (!EmptyUtils.isNullOrEmpty(parm)) {
            for (Map.Entry<String, String> entry : parm.entrySet()) {
                if(!EmptyUtils.isNullOrEmpty(entry.getValue())){
                    strbuff.append("<").append(entry.getKey()).append(">");
                    if( isAddCDATA && !EmptyUtils.isNullOrEmpty(list) && list.contains(entry.getKey().toLowerCase())){
                        strbuff.append(PREFIX_CDATA);
                        if (!EmptyUtils.isNullOrEmpty(entry.getValue())) {
                            strbuff.append(entry.getValue());
                        }
                        strbuff.append(SUFFIX_CDATA);
                    }else{
                        if (!EmptyUtils.isNullOrEmpty(entry.getValue())) {
                            strbuff.append(entry.getValue());
                        }
                    }
                    strbuff.append("</").append(entry.getKey()).append(">");
                }
            }
        }
        return strbuff.append(SUFFIX_XML).toString();
    }



    /**
     * 功能描述: map转xml 按ascii码排序
     * @param parm
     * @param isAddCDATA 是否加入CDATA
     * @param fileds
     * @return: java.lang.String
     * @author: ken
     * @date: 2019/8/28 0028 下午 12:43
     */
    public static String generateXmlsort(Map<String, String> parm, boolean isAddCDATA,String ...fileds) {
        List<String> list = Arrays.asList(fileds);
        Map<String, String> tmpMap = parm;
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>(){
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });
        StringBuffer strbuff = new StringBuffer(PREFIX_XML);
        for (Map.Entry<String, String> entry : infoIds) {
            if(!EmptyUtils.isNullOrEmpty(entry.getValue())){
                strbuff.append("<").append(entry.getKey()).append(">");
                if( !EmptyUtils.isNullOrEmpty(list) && list.contains(entry.getKey().toLowerCase())){
                    if (!EmptyUtils.isNullOrEmpty(entry.getValue())) {
                        strbuff.append(entry.getValue());
                    }
                }else{
                    strbuff.append(PREFIX_CDATA);
                    if (!EmptyUtils.isNullOrEmpty(entry.getValue())) {
                        strbuff.append(entry.getValue());
                    }
                    strbuff.append(SUFFIX_CDATA);
                }
                strbuff.append("</").append(entry.getKey()).append(">");
            }
        }
        return strbuff.append(SUFFIX_XML).toString();
    }


}
