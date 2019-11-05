package com.ken.sys.common.util;


import java.util.List;

public class FileLocationUtil {

    /**
     * 功能描述: 给数据集合设置文件访问路径
     * @param lst
     * @param setNames  需要设置的字段
     * @param webUrl  访问url
     * @return: java.util.List<T> 
     * @author: swc
     * @date: 2019/11/5 0005 下午 15:50
    */ 
    public static <T> List<T> addFilePath(List<T> lst, String[] setNames, String webUrl) {
        if (!EmptyUtils.isNullOrEmpty(lst)) {
            T baseEntity = null;
            Object temp = null;
            for (int j = 0; j < lst.size(); j++) {
                baseEntity = lst.get(j);
                addFilePath(baseEntity,setNames,webUrl);
            }
        }
        return lst;
    }


    /**
     * 功能描述: 给数据对象设置文件访问路径
     * @param baseEntity
     * @param setNames  需要设置的字段
     * @param webUrl  访问url
     * @return: T 
     * @author: swc
     * @date: 2019/11/5 0005 下午 15:51
    */ 
    public static <T>T addFilePath(T baseEntity, String[] setNames, String webUrl) {
        if (!EmptyUtils.isNullOrEmpty(baseEntity)) {
                Object temp = null;
                for(String setName:setNames){
                    if (!EmptyUtils.isNullOrEmpty(setName)) {
                        temp = ReflectUtils.getFieldValue(baseEntity, setName);
                        if (!EmptyUtils.isNullOrEmpty(temp)) {
                            ReflectUtils.setFieldValue(baseEntity, setName,webUrl +  temp);
                        }
                    }
            }
        }
        return baseEntity;
    }

    /**
     * @param lst 给数据集合设置文件访问路径  包含子集
     * @param setNames lst里面需要设置的字段
     * @param subName 需要配置的子集字段 名称
     * @param subSetNames 需要配置的子集字段 对象里面 需要设置的字段名称
     * @param webUrl 访问url
     * @param <T>
     * @return
     */
    public static <T> List<T> addFilePathContainSub(List<T> lst, String[] setNames, String subName, String[] subSetNames, String webUrl) {
        if (!EmptyUtils.isNullOrEmpty(lst)) {
            T baseEntity = null;
            for (int j = 0; j < lst.size(); j++) {
                baseEntity = lst.get(j);
                addFilePath(baseEntity,setNames,webUrl);
                checkDo(subName, subSetNames, webUrl, baseEntity);
            }
        }
        return lst;
    }

    /**
     * @param baseEntity 给数据对象设置文件访问路径  包含子集
     * @param setNames lst里面需要设置的字段
     * @param subName 需要配置的子集字段 名称
     * @param subSetNames 需要配置的子集字段 对象里面 需要设置的字段名称
     * @param webUrl 访问url
     * @param <T>
     * @return
     */
    public static <T> T addFilePathContainSub(T baseEntity, String[] setNames, String subName, String[] subSetNames, String webUrl) {
        if (!EmptyUtils.isNullOrEmpty(baseEntity)) {
            addFilePath(baseEntity,setNames,webUrl);
            checkDo(subName, subSetNames, webUrl, baseEntity);
        }
        return baseEntity;
    }


    /**
     * 功能描述: 针对性的处理 文件访问路径(仅支持实体对象 或者 集合)
     * @param subName  字段名称
     * @param subSetNames 需要处理的字段名称
     * @param webUrl 访问url
     * @param baseEntity  实体对象
     * @return: void 
     * @author: swc
     * @date: 2019/11/5 0005 下午 15:59
    */ 
    private static <T> void checkDo(String subName, String[] subSetNames, String webUrl, T baseEntity) {
        Object subObj;
        if (!EmptyUtils.isNullOrEmpty(subSetNames)) {
            subObj = (List<T>)ReflectUtils.getFieldValue(baseEntity, subName);
            if(subObj instanceof List){
                addFilePath((List<T>)subObj,subSetNames,webUrl);
            }else{
                addFilePath(subObj,subSetNames,webUrl);
            }
        }
    }

}
