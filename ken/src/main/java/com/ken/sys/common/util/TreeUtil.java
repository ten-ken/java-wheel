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

import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 * <li>Title: ken-TreeUtil</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author ken
 * @version V1.0
 * @date 2019/9/20 20:04
 */
public class TreeUtil {
     private static TreeUtil treeUtil =null;
    //配置值默认值
    private static boolean autoConfig = false;
    private static String setNodeField ="name";
    private static String setNodeValue ="全部";

    /**
     * 功能描述: 需要空的节点 比如下级木有 配置一个"“请选择”为其子节点
     * @param auto 是否配置 默认为false
     * @param nodeField 子节点 字段的属性名称 默认是name
     * @param nodeValue 子节点 的设置值 默认是请选择
     * @return: com.ken.sys.common.util.TreeUtil
     * @author: swc
     * @date: 2019/9/21 14:29
    */
    public static TreeUtil configEmptyNode(boolean auto,String nodeField,String nodeValue){
        treeUtil = new TreeUtil();
        setNodeField =EmptyUtils.isNullOrEmpty(nodeField)?setNodeField:nodeField;
        setNodeValue =EmptyUtils.isNullOrEmpty(nodeValue)?setNodeValue:nodeValue;
        autoConfig =auto;
        return treeUtil;
    }

    /**
     * 功能描述: 
     * @param list 源数据
     * @param pField 父节点的属性名
     * @param selfField 自身节点的属性名
     * @param childField  对象子节点集合的属性名
     * @param backMainValue  自身节点的值匹配 得到对应的所有节点信息
     * @return: cc.mrbird.febs.common.generator.TreeInfoVo<T> 
     * @author: ken
     * @date: 2019/9/20 21:32
    */ 
    public static <T> TreeInfoVo<T> getTree(List<T> list, String pField, String selfField, String childField, Object backMainValue) throws Exception {
        return gettTreeInfoVo(list, pField, selfField, childField, backMainValue,null,false);
    }

    /**
     * 功能描述:
     * @param list 源数据
      * @param pField 父节点的属性名
     * @param selfField 自身节点的属性名
     * @param childField  对象子节点集合的属性名
     * @param backMainValue  自身节点的值匹配 得到对应的所有节点信息
     * @param activeField  禁用或非禁用的属性名称  必填 默认“0”为禁用 去除此节点
     * @return: cc.mrbird.febs.common.generator.TreeInfoVo<T>
     * @author: ken
     * @date: 2019/9/20 21:33
    */
    public static <T> TreeInfoVo<T> getTree(List<T> list, String pField, String selfField, String childField, Object backMainValue, String activeField) throws Exception {
        return gettTreeInfoVo(list, pField, selfField, childField, backMainValue,activeField,false);
    }

    /**
     * 功能描述:
     * @param list 源数据
     * @param pField 父节点的属性名
     * @param selfField 自身节点的属性名
     * @param childField  对象子节点集合的属性名
     * @param backMainValue  父节点的值匹配 得到对应的所有节点信息
     * @return: cc.mrbird.febs.common.generator.TreeInfoVo<T>
     * @author: ken
     * @date: 2019/9/20 21:35
    */
    public static <T> TreeInfoVo<T> getTreeByPid(List<T> list, String pField, String selfField, String childField, Object backMainValue) throws Exception {
        return gettTreeInfoVo(list, pField, selfField, childField, backMainValue,null,true);
    }

    /**
     * 功能描述:
     * @param list 源数据
     * @param pField 父节点的属性名
     * @param selfField 自身节点的属性名
     * @param childField  对象子节点集合的属性名
     * @param backMainValue  父节点的值匹配 得到对应的所有节点信息
     * @param activeField  禁用或非禁用的属性名称  必填 默认“0”为禁用 去除此节点
     * @return: cc.mrbird.febs.common.generator.TreeInfoVo<T>
     * @author: ken
     * @date: 2019/9/20 21:35
    */
    public static <T> TreeInfoVo<T> getTreeByPid(List<T> list, String pField, String selfField, String childField, Object backMainValue, String activeField) throws Exception {
        return gettTreeInfoVo(list, pField, selfField, childField, backMainValue,activeField,true);
    }


    private static <T> TreeInfoVo<T> gettTreeInfoVo(List<T> list, String pField, String selfField, String childField, Object backMainValue, String activeField, boolean assertPid) throws Exception {
        TreeInfoVo treeInfoVo =new TreeInfoVo();
        List<T> souceList  = ObjectUtil.deepCopy(list);//深复制 此处需要实体序列化 不然报错
        treeInfoVo.setTreeList(souceList);

        //是否对禁用的节点进行过滤
        boolean needActive = !EmptyUtils.isNullOrEmpty(activeField);

        List<T> childs  = null;
        List<T> treeNodes  = new ArrayList<T>();//返回的主要信息

        for (T tree : souceList) {
            childs = new ArrayList<T>();
            for (T treeNode : souceList) {
                if (needActive && ReflectUtils.getFieldValue(treeNode,pField).equals(ReflectUtils.getFieldValue(tree,selfField)) && !"0".equals(ReflectUtils.getFieldValue(treeNode,activeField))) {
                    childs.add(treeNode);
                }
                if (!needActive && ReflectUtils.getFieldValue(treeNode,pField).equals(ReflectUtils.getFieldValue(tree,selfField)) ) {
                    childs.add(treeNode);
                }
            }
            if(!assertPid && backMainValue.equals(ReflectUtils.getFieldValue(tree,selfField))){
                treeNodes.add(tree);
            }
            if(assertPid && backMainValue.equals(ReflectUtils.getFieldValue(tree,pField))){
                treeNodes.add(tree);
            }
            ReflectUtils.setFieldValue(tree,childField,childs);
        }

        treeInfoVo.setTrees(treeNodes);

        //是否开启 无子节点 新增个“空白”节点

        if(autoConfig){
            List<T> empty = null;
            T emptyEntity =null;
            for (T tree : souceList) {
                empty =(List<T>)ReflectUtils.getFieldValue(tree,childField);
                empty = EmptyUtils.isNullOrEmpty(empty)? new ArrayList<T>():empty;
                emptyEntity = (T)tree.getClass().newInstance();
                ReflectUtils.setFieldValue(emptyEntity,pField, ReflectUtils.getFieldValue(tree,selfField));
                ReflectUtils.setFieldValue(emptyEntity,setNodeField,setNodeValue);
                empty.add(0,emptyEntity);
                ReflectUtils.setFieldValue(tree,childField,empty);
            }
        }

        return treeInfoVo;
    }





}
