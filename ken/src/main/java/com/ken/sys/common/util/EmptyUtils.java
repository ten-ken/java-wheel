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
 * <li>Title: EmptyUtils</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author swc
 * @version V1.0
 * @date 2019/8/10 8:23
 */
public class EmptyUtils {

    public static void main(String[] args) {
        Integer[] a =null;
        System.out.println(EmptyUtils.isNullOrEmpty(a));
    }

    /***
     * 功能描述:对象是否为空--通用 除集合 map 和字符串类型外
     * @param obj
     * @return: boolean
     * @author: swc
     * @date: 2019/8/10 21:02
     */
    public static boolean isNullOrEmpty(Object obj){
        if(obj instanceof String){
          return   isNullOrEmpty((String)obj);
        }
        if(obj instanceof List){
            return  isNullOrEmpty((List)obj);
        }
        if(obj instanceof Map){
            return isNullOrEmpty((Map) obj);
        }
        return obj==null;
    }

    /***
     * 功能描述: 字符串是否为空
     * @param str
     * @return: boolean
     * @author: swc
     * @date: 2019/8/10 21:02
    */
    public static boolean isNullOrEmpty(String str){
        return str==null||"".equals(str);
    }

    /***
     * 功能描述:集合是否为空
     * @param list
     * @return: boolean
     * @author: swc
     * @date: 2019/8/10 21:02
     */
    public static <T> boolean isNullOrEmpty(List<T> list){
        return list==null || list.isEmpty();
    }

    /**
     * 功能描述: map是否为空
     * @param map
     * @return: boolean
     * @author: swc
     * @date: 2019/8/10 21:03
    */
    public static<K,V> boolean isNullOrEmpty(Map<K,V> map){
        return map==null||map.isEmpty();
    }

    /**
     * 功能描述: set是否为空
     * @param set
     * @return: boolean
     * @author: swc
     * @date: 2019/8/10 21:03
     */
    public static<E> boolean isNullOrEmpty(Set<E> set){
        return set==null||set.isEmpty();
    }

    /**
     * 功能描述: set是否为空
     * @param t
     * @return: boolean
     * @author: swc
     * @date: 2019/8/10 21:03
     */
    public static<T> boolean isNullOrEmpty(T[] t){
        return !(t!=null&&t.length>0);
    }
}
