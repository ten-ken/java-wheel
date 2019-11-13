/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/
package com.kcppc.core.utils;

import com.kcppc.util.EmptyUtil;
import org.springframework.beans.BeanUtils;
import javax.ws.rs.DefaultValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>Title: 匠桥ERP系统-DataCompareUtil</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2019/11/13 0013 下午 14:23
 */
public class DataCompareUtil {

    /**
     * 功能描述: 对比数据库信息  获取 新增(add) 修改(update)  删除(delete) 和旧（old）数据===》 减少基础业务的代码 代码重复
     * @param dataBaseList  查询数据库得到数据
     * @param dataList     后台接收的数据
     * @param fieldName  默认为主键 id  具有唯一性的字段名称
     * @return: java.util.Map<java.lang.String,java.util.List<T>> 
     * @author: swc
     * @date: 2019/11/13 0013 下午 16:08
    */ 
    public static <T> Map<String,List<T>> getSaveUpdateDeLeteData(List<T> dataBaseList,List<T> dataList,@DefaultValue("id") String fieldName)throws Exception{
        Map<String,List<T>>  map =new HashMap<String,List<T>>();
        if(EmptyUtil.isNullOrEmpty(fieldName)){
            fieldName  ="id";
        }

        List<T> oldList =new ArrayList<T>();
        T tem =null;

        //需要修改的数据
        List<T> updateList =new ArrayList<T>();

        Map<Object,T>  existMap =new HashMap<Object,T>();
        for (T exist : dataBaseList) {
            existMap.put(ReflectUtils.getFieldValue(exist,fieldName),exist);
        }

        for (T data : dataList) {
            tem = (T)data.getClass().newInstance();
            BeanUtils.copyProperties(data,tem);
            oldList.add(tem);

            if(existMap.containsKey(ReflectUtils.getFieldValue(data,fieldName))){
                updateList.add(data);//存在的部分
                //移除修改的部分 ---未匹配的就是需要删除的
                existMap.remove(ReflectUtils.getFieldValue(data,fieldName));
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
