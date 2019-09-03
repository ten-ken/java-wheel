/******************************************************************************
 *
 * 作者（author）：ken
 * 微信（weChat）：mlchao1992
 * 个人博客（website）：
 *
 ******************************************************************************
 * 注意：尊重原创
 *****************************************************************************/

package com.ken.sys.common.saxmodel;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.ken.sys.common.anno.DataType;
import com.ken.sys.common.anno.ExcelColumnAnnotation;
import com.ken.sys.common.ifs.IExcelRowReader;
import com.ken.sys.common.util.*;
import org.apache.commons.lang3.time.DateUtils;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>Description: 此实体实现 hotool工具类的RowHandler（没引进可删此接口）
 * 和RowHandler （项目本身接口） </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 * @author ken
 * @date 2019/8/29 0029 下午 16:40
 */
public class RowExcelHandler<T> implements IExcelRowReader,RowHandler,Serializable {
    //存储实体自定义接口的信息
    private Map<Integer,ExcelColumnAnnotation> map = new HashMap<Integer,ExcelColumnAnnotation>();

    private T t;

    //存储错误信息
    private List<String> errorData =new ArrayList<String>();

    //存储实体对象集合
    private List<T> dataList =new ArrayList<T>();

    //开始行 默认是1
    private int startRow = 1;

    //记录所有的载体
    private Map<String,Object> dataMap =new HashMap<String,Object>();

    //需要匹配的实际字段的选择项 map
    private  Map[] checkMap = null;

    //总行数
    private int totalRow = 0;

    //失败行数
    private int failRow = 0;

    public RowExcelHandler(T t, int startRow, Map ...checkMaps) {
        this.t = t;
        this.startRow = startRow;
        this.checkMap = checkMaps;
        Field[] accessibleField = ReflectUtils.getAccessibleField(t);
        for(Field field:accessibleField){
            ExcelColumnAnnotation annotation = field.getAnnotation(ExcelColumnAnnotation.class);
            if(!EmptyUtils.isNullOrEmpty(annotation)){
                map.put(annotation.index(),annotation);
            }
        }
    }


    /**
     * 功能描述: 处理行数据 主方法
     * @param i
     * @param i1
     * @param list
     * @return: void
     * @author: swc
     * @date: 2019/8/30 0030 上午 11:18
     */
    @Override
    public void handle(int i, int i1, List<Object> list) {
        boolean check =false;
        boolean rowFail =false;
        T t1 = null;
        Constructor<?> constructor =null;
        ExcelColumnAnnotation curAnno = null;
        try {
            t1 = (T)t.getClass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if(i1>=startRow){
            for(int k =0;k<list.size();k++){
                if(!EmptyUtils.isNullOrEmpty(map.get(k))){
                    curAnno = map.get(k);
                    try {
                        //不能为空 值为空
                        if(!curAnno.nullable() && list.get(k)==null){
                            errorData.add("第"+(i1+1)+"行，"+curAnno.columnName()+" 不能为空</br>");
                            rowFail =true;
                            continue;
                        }

                        if(curAnno.dataType().equalsIgnoreCase(DataType.STRING) && StringUtils.moreThanMaxLen((String)list.get(k),curAnno.length())){
                            errorData.add("第"+(i1+1)+"行，"+curAnno.columnName()+" 长度超过"+curAnno.length()+"个字符</br>");
                            rowFail =true;
                            continue;
                        }

                        //时间类型
                        if(curAnno.dataType().equalsIgnoreCase(DataType.DATE)){
                            if(!EmptyUtils.isNullOrEmpty((String)list.get(k))){
                                ReflectUtils.setFieldValue(t1,curAnno.fieldName(),DateUtils.parseDate((String)list.get(k),curAnno.pattern()));
                            }
                            continue;
                        }else{
                            if(!EmptyUtils.isNullOrEmpty(curAnno.pattern()) && !RegExUtil.checkStr(curAnno.pattern(),(String)list.get(k))){
                                errorData.add("第"+(i1+1)+"行，"+curAnno.columnName()+"格式不对</br>");
                                rowFail =true;
                                continue;
                            }
                            //是否匹配
                            for(Map singleMap:checkMap){
                                if(!EmptyUtils.isNullOrEmpty(singleMap) && singleMap.containsKey("excel-index") && CompareNumberUtil.equal(k, singleMap.get("excel-index"))){
                                    //(Integer)k== (Integer) singleMap.get("excel-index")
                                    if(singleMap.containsKey(list.get(k).toString())){
                                        ReflectUtils.setFieldValue(t1,(String)singleMap.get("specified_field"),singleMap.get(list.get(k)));
                                        break;
                                    }
                                    check =true;
                                    rowFail =true;
                                    break;
                                }
                            }
                            // 与数据库字段不匹配
                            if(check){
                                check =false;
                                errorData.add("第"+(i1+1)+"行，"+curAnno.columnName()+"与系统数据匹配错误</br>");
                                continue;
                            }
                            constructor = Class.forName(curAnno.dataType()).getConstructor(String.class);
                            if(!EmptyUtils.isNullOrEmpty(list.get(k))){
                                ReflectUtils.setFieldValue(t1,curAnno.fieldName(),constructor.newInstance((String)list.get(k)));
                            }
                            continue;
                        }
                    }catch (Exception ex){
                        errorData.add("第"+(i1+1)+"行，"+curAnno.columnName()+"数据类型或格式不匹配</br>");
                        rowFail =true;
                    }
                }
            }

            dataMap.put("errorData",errorData);
            totalRow = i1-startRow+1;
            if(rowFail){
                failRow++;
                dataMap.put("dataList",dataList);
                return;
            }
            dataList.add(t1);
            dataMap.put("dataList",dataList);
        }
    }

    /**
     * 功能描述: 获取处理后的数据
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @author: swc
     * @date: 2019/8/30 0030 上午 11:17
     */
    public Map<String,Object> getHandData(){
        Map<String, Object> clone = ObjectUtil.clone(dataMap);
        clone.put("failNums",failRow);//失败总条数
        clone.put("successNums",totalRow-failRow);//成功总条数
        dataMap.clear();
        checkMap = null;
        totalRow = 0;
        failRow=0;
        return   clone;
    }


}
