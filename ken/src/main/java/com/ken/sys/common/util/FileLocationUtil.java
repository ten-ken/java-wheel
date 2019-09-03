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

import java.util.List;

public class FileLocationUtil {

    /**
     * 功能描述: 设置对象
     * @param list  集合对象
     * @param fields    需要设置的字段（数组）
     * @param webUrl  前缀url
     * @param subListName 需要处理的子集字段名称（获取集合）
     * @param subFields 需要处理的子集针对的具体字段
     * @return: java.util.List<T> 
     * @author: swc
     * @date: 2019/8/6 0006 下午 14:43
    */ 
    public static <T> List<T> setListFilePath(List<T> list,String[] fields,String webUrl,String subListName,String[] subFields) {
        if (!EmptyUtils.isNullOrEmpty(list)) {
            T baseEntity = null;
            List<T> listImage = null;
            Object temp = null;
            for (int j = 0; j < list.size(); j++) {
                baseEntity = list.get(j);
                setObjFilePath(baseEntity,fields, webUrl, subListName, subFields);
            }
        }
        return list;
    }

    
    /**
     * 功能描述: 处理对象中的文件路径
     * @param baseEntity 对象/实体
     * @param fields    处理的实体属性字段名称
     * @param webUrl    访问前缀
     * @param subListName 子集合的字段名称
     * @param subFields 子集合单对象处理的属性字段名称
     * @return: T 
     * @author: swc
     * @date: 2019/8/6 0006 下午 14:59
    */ 
    public static <T> T setObjFilePath(T baseEntity,String[] fields, String webUrl, String subListName, String[] subFields) {
        try{
            Object temp;
            List<T> listImage;
            if (!EmptyUtils.isNullOrEmpty(baseEntity)) {
                for(String field:fields){
                    temp = ReflectUtils.getFieldValue(baseEntity, field);
                    if (!EmptyUtils.isNullOrEmpty(temp)) {
                        ReflectUtils.setFieldValue(baseEntity, field,webUrl +  temp);
                    }
                }
            }
            if (!EmptyUtils.isNullOrEmpty(subListName)) {
                listImage = (List<T>)ReflectUtils.getFieldValue(baseEntity, subListName);
                setListFilePath(listImage,subFields,webUrl,null,null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return baseEntity;
    }

}
