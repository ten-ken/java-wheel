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

import com.ken.sys.common.ifs.DataCompare;
import org.springframework.beans.BeanUtils;
import javax.ws.rs.DefaultValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>Description: 数据对比工具 </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author ken
 * @date 2019/11/13 0013 下午 14:23
 */
public class DataCompareUtil {

    /**
     * 功能描述: 对比数据库信息  获取 新增(add) 修改(update)  删除(delete) 和旧（old）数据===》 减少基础业务的代码 代码重复
     * @param dataBaseList  查询数据库得到数据
     * @param dataList     后台接收的数据
     * @param fieldNames  默认为主键 id  具有唯一性的字段名称(可变形参)
     * @return: java.util.Map<java.lang.String,java.util.List<T>> 
     * @author: swc
     * @date: 2019/11/13 0013 下午 16:08
    */ 
    public static <T> Map<String,List<T>> getSaveUpdateDeLeteData(List<T> dataBaseList,List<T> dataList,@DefaultValue("id") String... fieldNames)throws Exception{
          return realCompare(dataBaseList,dataList,null,fieldNames);
    }


    /**
     * 功能描述: 对比数据库信息  返回对应的map（key + value--实体） 和 数据源  接口中进行实际处理
     * @param dataBaseList  查询数据库得到数据
     * @param dataList     后台接收的数据
     * @param dataCompare  实际外部处理的接口
     * @param fieldNames  默认为主键 id  具有唯一性的字段名称(可变形参)
     * @return: java.util.Map<java.lang.String,java.util.List<T>>
     * @author: swc
     * @date: 2019/11/13 0013 下午 16:08
     */
    public static <T> void getSaveUpdateDeLeteData(List<T> dataBaseList, List<T> dataList, DataCompare dataCompare, @DefaultValue("id") String... fieldNames)throws Exception{
         realCompare(dataBaseList,dataList,dataCompare,fieldNames);
    }


    /**
     * 功能描述: 对比数据库信息  获取 新增(add) 修改(update)  删除(delete) 和旧（old）数据===》 减少基础业务的代码 代码重复
     * @param dataBaseList  查询数据库得到数据
     * @param dataList     后台接收的数据
     * @param fieldNames  默认为主键 id  具有唯一性的字段名称
     * @return: java.util.Map<java.lang.String,java.util.List<T>>
     * @author: swc
     * @date: 2019/11/13 0013 下午 16:08
     */
    private static <T> Map<String,List<T>> realCompare(List<T> dataBaseList,List<T> dataList,DataCompare dataCompare,@DefaultValue("id") String... fieldNames)throws Exception{
        Map<String,List<T>>  map =new HashMap<String,List<T>>();
        if(EmptyUtils.isNullOrEmpty(fieldNames)){
            fieldNames[0]  ="id";
        }
        List<T> oldList =new ArrayList<T>();
        T tem =null;

        //需要修改的数据
        List<T> updateList =new ArrayList<T>();

        Map<Object,T>  existMap =new HashMap<Object,T>();

        StringBuffer keys =null;
        for (T exist : dataBaseList) {
            keys=new StringBuffer();
            for(String fileName:fieldNames){
                keys.append(ReflectUtils.getFieldValue(exist,fileName));
            }
            existMap.put(keys.toString(),exist);
        }

        //定义了外部接口
        if(!EmptyUtils.isNullOrEmpty(dataCompare)){
            dataCompare.handData(existMap,dataList);
            return map;
        }

        for (T data : dataList) {
            tem = (T)data.getClass().newInstance();
            BeanUtils.copyProperties(data,tem);
            oldList.add(tem);

            keys=new StringBuffer();
            for(String fileName:fieldNames){
                keys.append(ReflectUtils.getFieldValue(data,fileName));
            }
            if(existMap.containsKey(keys.toString())){
                updateList.add(data);//存在的部分
                //移除修改的部分 ---未匹配的就是需要删除的
                existMap.remove(keys.toString());
            }
        }

        //这才是需要真正新增的数据
        dataList.removeAll(updateList);

        //需要被删除的数据 (数据库存在 但修改时不存在的数据)
        List<T> deleteList = new ArrayList<>(existMap.values());

        map.put("add",dataList);
        map.put("update",updateList);
        map.put("delete",deleteList);
        map.put("old",oldList);
        return map;
    }

}
