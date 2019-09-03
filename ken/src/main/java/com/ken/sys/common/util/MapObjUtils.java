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
import java.lang.reflect.Method;
import java.util.*;

/**
 * <ul>
 * <li>Title: MapObjUtils</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author swc
 * @version V1.0
 * @date 2019/8/27 21:41
 */
public class MapObjUtils {

    /**
     * 功能描述: list转map(指定key和value)--将list的对象转成 对象某个属性值为key 另一个属性值为value的单个map
     * @param list
     * @param keyField
     * @param valueField
     * @param backmap
     * @return: java.util.Map<K,E>
     * @author: swc
     * @date: 2019/8/27 22:23
     */
    public static <K,E,T>Map<K,E> getListResolveMap(List<T> list,String keyField,String valueField,HashMap<K, E> backmap){
        for(T t:list){
            getObjResolveMap(t,keyField, valueField,backmap);
        }
        return  backmap;
    }

    /**
     * 功能描述: 对象转map(指定key和value)---将对象转成对象某个属性值为key 另一个属性值为value的单个map
     * @param t
     * @param keyField
     * @param valueField
     * @param backmap
     * @return: java.util.Map<K,E>
     * @author: swc
     * @date: 2019/8/27 22:23
     */
    public static  <K,E,T>Map<K,E> getObjResolveMap(T t,String keyField, String valueField,HashMap<K, E> backmap) {
        try{
            Method method1 = ReflectUtils.getAccessibleMethodByName(t,ReflectUtils.GETTER_PREFIX+StringUtils.captureName(keyField));
            Method method2 = ReflectUtils.getAccessibleMethodByName(t,ReflectUtils.GETTER_PREFIX+StringUtils.captureName(valueField));
            if(method1!=null && method2!=null){
                backmap.put((K)method1.invoke(t), (E)method2.invoke(t));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return backmap;
    }

    /**
     * 功能描述: list转map(指定key和value,复合组成的key)--将集合里的对象 转成对象某几个属性值为key 另一个属性值为value的单个map
     * @param list
     * @param backmap
     * @param valueField
     * @param separation
     * @param keyFields
     * @return: java.util.Map<K,E>
     * @author: swc
     * @date: 2019/8/27 22:25
     */
    public static <K,E,T>Map<K,E> getListMulResolveMap(List<T> list,HashMap<K, E> backmap,String valueField,String separation,String ...keyFields){
        for(T t:list){
            getObjMulResolveMap(t,backmap,valueField,separation,keyFields);
        }
        return  backmap;
    }

    /**
     * 功能描述: 对象转map(指定key和value,复合组成的key)---将对象转成对象某几个属性值为key 另一个属性值为value的单个map
     * @param t
     * @param backmap
     * @param valueField
     * @param connector
     * @param keyFields
     * @return: java.util.Map<K,E>
     * @author: swc
     * @date: 2019/8/27 22:25
     */
    public static <T, E, K> Map<K,E> getObjMulResolveMap(T t, HashMap<K,E> backmap, String valueField,String connector ,String... keyFields) {
        try{
            if(EmptyUtils.isNullOrEmpty(keyFields)){
                return backmap;
            }
            if(EmptyUtils.isNullOrEmpty(connector)){
                connector="";
            }
            Method method1 = null;
            Method method2 = ReflectUtils.getAccessibleMethodByName(t,ReflectUtils.GETTER_PREFIX+StringUtils.captureName(valueField));

            StringBuffer key =new StringBuffer();
            for(int i=0;i<keyFields.length;i++){
                method1 = ReflectUtils.getAccessibleMethodByName(t,ReflectUtils.GETTER_PREFIX+StringUtils.captureName(keyFields[i]));
                connector = i!=keyFields.length-1?connector:"";
                key.append(method1.invoke(t));
                key.append(connector);
            }

            if(method1!=null && method2!=null){
                backmap.put((K)key.toString(), (E)method2.invoke(t));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return backmap;
    }




    /**
     * 功能描述: 对象转map(指定转换的字段)---将对象转出map
     * @param t
     * @param backmap
     * @param keyFields 指定的字段
     * @return: java.util.Map<K,E>
     * @author: swc
     * @date: 2019/8/27 22:25
     */
    public static <T, E, K> Map<K,E> getObjToMap(T t, HashMap<K,E> backmap,String... keyFields) {
        try{
            if(EmptyUtils.isNullOrEmpty(keyFields)){
                return backmap;
            }
            Method method1 = null;
            StringBuffer key =new StringBuffer();
            for(int i=0;i<keyFields.length;i++){
                method1 = ReflectUtils.getAccessibleMethodByName(t,ReflectUtils.GETTER_PREFIX+StringUtils.captureName(keyFields[i]));
                backmap.put((K)keyFields[i], (E)method1.invoke(t));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return backmap;
    }




    /**
     * 功能描述:对象转map(指定转换的字段)---将对象转出map
     * @param t
     * @param backmap
     * @param keyFields 指定的字段
     * @return: java.util.Map<K,E>
     * @author: swc
     * @date: 2019/8/27 22:25
     */
    public static <T, E, K> Map<K,E> getObjFilterToMap(T t, HashMap<K,E> backmap,String... keyFields) {
        try{
            if(EmptyUtils.isNullOrEmpty(keyFields)){
                return backmap;
            }
            HashMap<String,String> hashMap =new HashMap<String, String>();
            Method method = null;
            Field[] fields = ReflectUtils.getAccessibleField(t);
            for(int i=0;i<fields.length;i++){
                hashMap.put(fields[i].getName(),fields[i].getName());
            }

            StringBuffer key =new StringBuffer();
            for(int i=0;i<keyFields.length;i++){
                if(hashMap.containsKey(keyFields[i])){
                    hashMap.remove(keyFields[i]);
                }
            }
            for(String keys:hashMap.keySet()){
                method = t.getClass().getMethod("get"+StringUtils.captureName(keys));
                backmap.put((K)keys, (E)method.invoke(t));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return backmap;
    }


    /**
     * 功能描述:对象转map(指定过滤的字段)---将对象转出map
     * @param map
     * @param clazz Class 类
     * @param ingoreList 指定的字段
     * @return: java.util.Map<K,E>
     * @author: swc
     * @date: 2019/8/27 22:25
     */
    public static <T> T mapResolveEntity(Map<String,Object> map, Class<T> clazz, String ...ingoreList) {
        try {
            ArrayList<String> ingores =new ArrayList<String>();
            Object obj = null;
            obj = clazz.newInstance();
            if(EmptyUtils.isNullOrEmpty(map)){
                return null;
            }
            if(!EmptyUtils.isNullOrEmpty(ingoreList)){
                ingores =new ArrayList<String>(Arrays.asList(ingoreList));
            }
            for(String key :map.keySet()){
                if(!ingores.contains(key)){
                    ReflectUtils.setFieldValue(obj,key,map.get(key));
                }
            }
            return (T)obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void main(String[] args) {
//        List<A> list =new ArrayList<A>();
//        A a1= new A("小红",10,new Date());
//        A a2= new A("金婷",16,new Date());
//        A a3= new A("光亮",22,new Date());
//        list.add(a1);
//        list.add(a2);
//        list.add(a3);
        //list转map(指定key和value)
//        Map<String, String> listResolveMap = MapObjUtils.getListResolveMap(list, "name", "age", new HashMap<String, String>());
////
////        //对象转map(指定key和value)
////        Map<String, String> list2 = MapObjUtils.getObjResolveMap(a2, "name", "age", new HashMap<String, String>());
////        System.out.println(listResolveMap);
////
////        //list转map(指定key和value,复合组成的key)
////        Map<String, String> map1= MapObjUtils.getListMulResolveMap(list,new HashMap<String, String>(),"birthday",",","name","age");
////
////        //对象转map(指定key和value,复合组成的key)
////        Map<String, String> map2 = MapObjUtils.getObjMulResolveMap(a2, new HashMap<String, String>(), "birthday", null, "name", "age");
////        System.out.println(111);
////
////        //对象转map(指定转换的字段)
////        Map<String, Object> objMap1 = MapObjUtils.getObjToMap(a2, new HashMap<String, Object>(), "age","birthday");
////
////        //对象转map(指定转换的字段)
////        Map<String, Object> objMap2 = MapObjUtils.getObjFilterToMap(a2, new HashMap<String, Object>(), "age","birthday");
////
////        System.out.println(list);

//        HashMap<String,Object> map =new HashMap<String,Object>();
//        map.put("age",10);
//        map.put("birthday",new Date());
//        A age = MapObjUtils.mapResolveEntity(map, A.class, "age");
    }

}
