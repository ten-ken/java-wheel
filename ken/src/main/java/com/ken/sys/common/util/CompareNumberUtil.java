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

import java.math.BigDecimal;

/**
 * <ul>
 * <li>Title: ken-CompareNumberUtil</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author ken
 * @version V1.0
 * @date 2019/9/1 21:14
 */
public class CompareNumberUtil {

    public static <T> boolean greaterThan(T t1, T t2) {
        return  common(t1,t2,">");
    }

    public static <T> boolean equal(T t1, T t2) {
        return  common(t1,t2,"=");
    }

    public static <T> boolean lessThan(T t1, T t2) {
        return  common(t1,t2,"<");
    }

    private static <T> boolean common(T t1, T t2,String type){
        if(t1==null || t2==null){
            return false;
        }else if(t1 instanceof Number && t2 instanceof Number){
            int i = new BigDecimal(t1.toString()).compareTo(new BigDecimal(t2.toString()));
            if(type.equalsIgnoreCase(">")){
                return i>0?true:false;
            } else if(type.equalsIgnoreCase("<")){
                return i>0?true:false;
            }else{
                return i==0?true:false;
            }
        }else{
            return false;
        }
    }
}
