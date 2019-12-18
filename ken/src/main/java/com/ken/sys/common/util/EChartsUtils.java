package com.ken.sys.common.util;
import com.ken.sys.common.entity.dto.EchartSeries;

import java.math.BigDecimal;
import java.util.*;

public class EChartsUtils {

    public static final String TYPEFOLDLINE ="line";

    public static final String TYPEBAR ="bar";

    /**
     * 功能描述: 获取echarts图==》最简单的柱状图  配置  y轴 [巴西,中国,韩国]  x轴[25,32,56]
     * @param list
     * @param nameField
     * @param valueField
     * @return: java.util.Map<java.lang.String,java.util.List<java.lang.Object>> 
     * @author: ken
     * @date: 2019/12/3 0003 下午 17:26
     */
    public static <T> Map<String,List<Object>> getBarChart(List<T> list, String nameField, String valueField)throws Exception {
        List<Object> namesList =new ArrayList<Object>();
        List<Object> valuesList =new ArrayList<Object>();

        Map<String,List<Object>> map =new HashMap<String,List<Object>>();

        for(T t:list){
            namesList.add(ReflectUtils.getFieldValue(t,nameField));
            valuesList.add(ReflectUtils.getFieldValue(t,valueField));
        }
        map.put("values",valuesList);
        map.put("names",namesList);
        return map;
    }

    /**
     * 功能描述: 生成折线图或者柱状图的属性
     * @param type "bar"==>柱状图  or "line" 折线图
     * @param list
     * @param nameField x轴需要对应的值 （实体的属性名)
     * @param legends
     * @param fields y轴需要对应的值 （单个serie对应的data数据  实体对应的属性名)
     * @param saveList 每个list和fields的值是一一对应的  
     * @return: java.util.Map<java.lang.String,java.util.List<java.lang.Object>> 
     * @author: ken
     * @date: 2019/12/4 0004 下午 13:47
    */ 
    public static <T>Map<String,List<Object>> getECharOptionsToFoldLine(String type,List<T> list, String nameField, Object[] legends, Object[] fields, List ...saveList) {
        List<Object> namesList =new ArrayList<Object>();
        Map<String,List<Object>> map =new HashMap<String,List<Object>>();
        Object obj =null;
        for(T t:list){
            namesList.add(ReflectUtils.getFieldValue(t,nameField));
            for(int ind=0;ind<fields.length;ind++){
               obj = ReflectUtils.getFieldValue(t,(String)fields[ind]);
               obj = EmptyUtils.isNullOrEmpty(obj)?0:obj;
               saveList[ind].add(obj);
            }
        }

        List<Object> backList =new ArrayList<Object>();
        EchartSeries<Long> echartSeries =null;
        for(int ind=0;ind<fields.length;ind++){
            echartSeries =new EchartSeries<Long>();
            echartSeries.setName((String)legends[ind]);
            echartSeries.setType(type);
//             echartSeries.setStack("总量");
            echartSeries.setData(saveList[ind]);
            backList.add(echartSeries);
        }
        map.put("names",namesList);
        map.put("values",backList);
        map.put("legends",Arrays.asList(legends));
        return map;
    }


    /**
     * 功能描述: 生成饼状图的属性
     * @param list
     * @param nameField
     * @param circleVField list里面对象的属性值  代表该饼状图区域的数值
     * @return: java.util.Map<java.lang.String,java.util.List<java.lang.Object>> 
     * @author: ken
     * @date: 2019/12/4 0004 下午 13:51
    */ 
    public static <T>Map<String,List<Object>> getECharOptionsToCircle(List<T> list, String nameField, String circleVField) {
        List<Object> namesList =new ArrayList<Object>();
        Map<String,List<Object>> map =new HashMap<String,List<Object>>();

        List<Object> backList =new ArrayList<Object>();
        EchartSeries<Long> echartSeries =null;

        Object obj=null;
        List<Object> legends =new ArrayList<Object>();

        for(int ind=0;ind<list.size();ind++){
            namesList.add(ReflectUtils.getFieldValue(list.get(ind),nameField));
            echartSeries =new EchartSeries<Long>();
            echartSeries.setName((String)ReflectUtils.getFieldValue(list.get(ind),nameField));
            obj = ReflectUtils.getFieldValue(list.get(ind),circleVField);
            obj = EmptyUtils.isNullOrEmpty(obj)?0:obj;
            echartSeries.setValue(new BigDecimal(obj.toString()));
            backList.add(echartSeries);
            legends.add((String)ReflectUtils.getFieldValue(list.get(ind),nameField));
        }

        map.put("names",namesList);
        map.put("values",backList);
        map.put("legends",legends);
        return map;
    }
}
