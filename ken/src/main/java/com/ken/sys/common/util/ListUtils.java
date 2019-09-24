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
 * <li>Title: ListUtils</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author ken
 * @date 2019/8/28 0028 上午 11:24
 */
public class ListUtils {


    /**
     * 功能描述: 将集合按多少数据量 分为几组
     * @param list
     * @param len
     * @return: java.util.List<java.util.List<org.apache.poi.hssf.record.formula.functions.T>>
     * @author: ken
     * @date: 2019/8/28 0028 下午 12:59
    */
    public static <T>List<List<T>>  splitList(List<T> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        List<List<T>> result = new ArrayList<List<T>>();
        int size = list.size();
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }


    /**
     * 功能描述: 移除list中某些字段为空的对象
     * @param list
     * @param keyNames 对象中的字段名称 必须与list的泛型对象属性名一致
     * @return: void
     * @author: ken
     * @date: 2018/12/26 0026 下午 17:23
     */
    public static <T>void removeNullObj(List<T> list, String ...keyNames) {
        if (list != null) {
            List<T> error = new ArrayList<T>();
            for (T t:list) {
                for(String keyName:keyNames){
                    Object obj = ReflectUtils.getFieldValue(t,keyName);
                    if (obj == null) {
                        error.add(t);
                    }
                }
            }
            list.removeAll(error);
        }
    }


    /**
     * 功能描述: list某字段重新设置对象里面的某个值
     * @param list
     * @param oldV  原值
     * @param newV  新值
     * @param flied  字段名称
     * @return: void
     * @author: ken
     * @date: 2019/1/17 0017 下午 13:57
     */
    public static <T>void resetFilesValue(List<T> list,Object oldV, Object newV, String flied) {
        if (!EmptyUtils.isNullOrEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    String str = (String)ReflectUtils.getFieldValue(list.get(i),flied);
                    if (oldV.equals(str)) {
                        ReflectUtils.setFieldValue(list.get(i),flied,newV);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 功能描述: list某字段长度进行控制
     * @param list
     * @param flied
     * @param length
     * @return: void
     * @author: ken
     * @date: 2019/3/8 0008 上午 11:19
     */
    public static <T>void setFieldLength(List<T> list, String flied,int length) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    String str = (String)ReflectUtils.getFieldValue(list.get(i),flied);
                    if(str!=null && str.length()>length){
                        str =str.substring(0,length)+"...";
                        ReflectUtils.setFieldValue(list.get(i),flied,str);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 功能描述: 查看集合内与defaultV的集合值是否匹配
     * @param list
     * @param flied 字段名称
     * @param defaultV 默认值 不能为空
     * @return: boolean
     * @author: ken
     * @date: 2019/3/14 0014 下午 21:07
     */
    public static <T> boolean checkListField(List<T> list,String  flied,String  ...defaultV) {
        boolean flag = false;
        List<String> defaults = Arrays.asList(defaultV);
        if (list != null) {
            for (T t:list) {
                String str = (String) ReflectUtils.getFieldValue(t, flied);
                if (defaults.contains(str)) {
                    return true;
                }
            }
        }
        return  flag;
    }



    /**
     * 功能描述:将list里面的 相关字段（字符串）部分展示
     * @param list
     * @param len 超过多少长度
     * @param fiels 字段
     * @return: java.util.List<T>
     * @author: ken
     * @date: 2019/8/22 0022 上午 10:30
     */
    public static <T> List<T> esclipseListFiles(List<T> list,Integer len, String ...fiels) {
        if(!EmptyUtils.isNullOrEmpty(list)){
            for(T t:list){
                esclipseObjFiles(t,len, fiels);
            }
        }
        return list;
    }


    /**
     * 功能描述:将对象里面的 相关字段（字符串）部分展示
     * @param t 对象
     * @param len 超过多少长度
     * @param fiels 字段
     * @return: T
     * @author: ken
     * @date: 2019/8/22 0022 上午 10:48
     */
    public static <T> T esclipseObjFiles(T t,Integer len, String... fiels) {
        if(len==null || len<=0){
            len = 50;
        }
        try {
            for(String fiel:fiels){
                String  str = (String)ReflectUtils.getFieldValue(t,fiel);
                if(str.length()>len){
                    str = str.substring(0,len)+"...";
                }
                ReflectUtils.setFieldValue(t,fiel,str);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            return t;
        }
    }

    /**
     * 功能描述: 获取父节点下的所有子节点（处理单一的list结构 非树结构）
     * @param resultList 返回的结果集 初始化创建为new ArrayList<T>
     * @param source 目标源
     * @param pValue 父节点的值
     * @param pField 父节点属性字段名
     * @param selfField 自身属性字段名
     * @return: java.util.List<T> 
     * @author: ken
     * @date: 2019/9/24 0024 上午 11:14
    */ 
    public static <T> List<T> findChildNodes(List<T> resultList,List<T> source,Object pValue,String pField, String selfField) {
        for (T t:source) {
            //遍历出父id等于参数的id，add进子节点集合
            if (pValue.equals(ReflectUtils.getFieldValue(t,pField))) {
                //递归遍历下一级
                findChildNodes(resultList,source, ReflectUtils.getFieldValue(t,selfField),pField,selfField);
                resultList.add(t);
            }
        }
        return resultList;
    }


    /**
     * 功能描述: 获取当前节点的所有父节点（处理单一的list结构 非树结构）
     * @param resultList 返回的结果集 初始化创建为new ArrayList<T>
     * @param source 处理的数据源
     * @param selfValue 自身的值
     * @param pField   父节点的属性字段名
     * @param selfField 自身的属性字段名
     * @param containSelf 是否返回包含自己的节点
     * @return: java.util.List<T>
     * @author: ken
     * @date: 2019/9/24 0024 下午 13:57
    */
    public static <T> List<T> findParentNodes(List<T> resultList,List<T> source,Object selfValue,String pField, String selfField,boolean containSelf) {
        List<T> parentNodes = getParentNodes(resultList, source, selfValue, pField, selfField);
        if(!containSelf && parentNodes.size()>0){
            parentNodes.remove(parentNodes.size()-1);
        }
        return parentNodes;
    }

    /**
     * 功能描述:获取当前节点的所有父节点（处理单一的list结构 非树结构）
     * @param resultList 返回的结果集 初始化创建为new ArrayList<T>
     * @param source 处理的数据源
     * @param selfValue 自身的值
     * @param pField   父节点的属性字段名
     * @param selfField 自身的属性字段名
     * @return: java.util.List<T> 
     * @author: ken
     * @date: 2019/9/24 0024 下午 13:59
    */ 
    private static <T> List<T> getParentNodes(List<T> resultList,List<T> source,Object selfValue,String pField, String selfField) {
        for (T t:source) {
            //遍历出父id等于参数的id，add进子节点集合
            if (selfValue.equals(ReflectUtils.getFieldValue(t,selfField))) {
                //递归遍历下一级
                getParentNodes(resultList,source, ReflectUtils.getFieldValue(t,pField),pField,selfField);
                resultList.add(t);
            }
        }
        return resultList;
    }

    /**
     * 功能描述: 获取当前节点的所有兄弟节点
     * @param source 处理的数据源
     * @param selfValue 自身的值
     * @param pField   父节点的属性字段名
     * @param selfField 自身的属性字段名
     * @param containSelf 是否返回包含自己的节点
     * @return: java.util.List<T>
     * @author: ken
     * @date: 2019/9/24 0024 下午 14:26
    */
    public static <T> List<T> findSiblingNodes(List<T> source,Object selfValue,String pField, String selfField,boolean containSelf) {
        Set<T> set =new HashSet<T>();
        T self =null;
        for (T t:source) {
            //遍历出父id等于参数的id，add进子节点集合
            if (selfValue.equals(ReflectUtils.getFieldValue(t,selfField))) {
                self  = t;
                for (T t1:source) {
                    if(ReflectUtils.getFieldValue(t,pField).equals(ReflectUtils.getFieldValue(t1,pField))){
                        System.out.println(ReflectUtils.getFieldValue(t1,"name"));
                        set.add(t1);
                    }
                }
                break;
            }
        }
        if(!containSelf && set.size()>0){
            set.remove(self);
        }
        return new ArrayList<>(set);
    }
}
