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
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <ul>
 * <li>Title: NullUtils</li>
 * <li>Description: 对象或集合（属性）去空 工具类 </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author ken
 * @version V1.0
 * @date 2019/8/1 0001 下午 20:05
 */
public class NullUtils {
    /**
     * 功能描述: 处理集合 将其属性为null 部分变为（拆箱）默认值
     * @param list
     * @param ingoreList  忽略处理的字段
     * @return: void
     * @author: swc
     * @date: 2019/8/1 0001 下午 20:21
     */
    public static <T> List<T> handerList(List<T> list,String ...ingoreList) {
        if(!EmptyUtils.isNullOrEmpty(list)){
            for(T t:list){
                handerObj(t,ingoreList);
            }
        }
        return list;
    }


    /**
     * 功能描述: 处理对象 将其属性为null 部分变为（拆箱）默认值
     * @param t
     * @param ingoreList  忽略处理的字段
     * @return: void
     * @author: swc
     * @date: 2019/8/1 0001 下午 20:21
     */
    public static <T> T handerObj(T t,String ...ingoreList) {
        List<String> ingores =new ArrayList<String>();
        if(ingoreList.length>0){
            ingores =Arrays.asList(ingoreList);
        }
        if(t!=null){
            Field[] fields =  ReflectUtils.getAccessibleField(t);
            for(Field field:fields){
                try {
                    //属性类型
                    String type = field.getGenericType().toString();
                    if(ReflectUtils.getFieldValue(t,field.getName())==null && !ingores.contains(field.getName())){
                        if(type.contains("int") || type.contains("long") || type.contains("double")){
                            ReflectUtils.setFieldValue(t,field.getName(),0);
                        }
                        if(type.contains("String")){
                            ReflectUtils.setFieldValue(t,field.getName(),"");
                        }
                        if(type.contains("Integer")){
                            ReflectUtils.setFieldValue(t,field.getName(),Integer.parseInt("0"));
                        }
                        if(type.contains("Short")){
                            ReflectUtils.setFieldValue(t,field.getName(),Short.parseShort("0"));
                        }
                        if(type.contains("Character")){
                            ReflectUtils.setFieldValue(t,field.getName(),Character.valueOf(' '));
                        }
                        if(type.contains("Long")){
                            ReflectUtils.setFieldValue(t,field.getName(),Long.parseLong("0"));
                        }
                        if(type.contains("BigDecimal")){
                            ReflectUtils.setFieldValue(t,field.getName(),BigDecimal.ZERO);
                        }
                        if(type.contains("Double")){
                            ReflectUtils.setFieldValue(t,field.getName(),Double.parseDouble("0"));
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return t;
        }
        return t;
    }


//    /**
//     * 功能描述: 处理对象 将其属性为null 部分变为（拆箱）默认值
//     * @param t
//     * @param ingoreList  忽略处理的字段
//     * @return: void
//     * @author: swc
//     * @date: 2019/8/1 0001 下午 20:21
//     */
//    public static <T> T handerObj(T t,String ...ingoreList) {
//        handerObj1(t,ingoreList);
//        return t;
//    }

}
