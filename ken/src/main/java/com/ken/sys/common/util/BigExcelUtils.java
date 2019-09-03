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

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.ken.sys.common.entity.TAppUserInfoPO;
import com.ken.sys.common.excel.saxmodel.RowExcelHandler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>Title: ExeclReadUtils</li>
 * <li>Description: 大数据的excel文档解析 </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2019/8/30 0030 上午 11:00
 */
public class BigExcelUtils {
    // excel2003扩展名
    public static final String EXCEL03_EXTENSION = ".xls";
    // excel2007扩展名
    public static final String EXCEL07_EXTENSION = ".xlsx";

    /**
     * 功能描述: 通用的导入 sax解析 ---10-20万以内数据解析 单行数据9列 8-15秒 excel2007 建议最大数据量不要超过12万条
     * @param inputStream 流数据
     * @param fileName 文件名称 包括后缀
     * @param rowHandler 处理的接口工具
//     * @param startRow 开始行
//     * @param checkMaps 检查匹配相关行字段的map  可不写
     * @return: java.util.Map<java.lang.String,java.lang.Object> 
     * @author: swc
     * @date: 2019/8/30 0030 上午 11:10
    */ 
    public static Map<String,Object>  readExcel(InputStream inputStream, String fileName, RowHandler rowHandler, int sheetIndex){
        sheetIndex= sheetIndex<0?0:sheetIndex;
        if(fileName.endsWith(EXCEL07_EXTENSION)){
            ExcelUtil.read07BySax(inputStream, sheetIndex, rowHandler);
        }else if(fileName.endsWith(EXCEL03_EXTENSION)){
            ExcelUtil.read03BySax(inputStream, sheetIndex, rowHandler);
        }else {
            Map<String,Object> map =new HashMap<String,Object>();
            map.put("error_code","导入的文件类型或格式不对");
            return map;
        }
        Map<String, Object> handData = ((RowExcelHandler)rowHandler).getHandData();
        return handData;
    }

    //使用案例1
    public static void main(String[] args) throws Exception{
        long bg = System.currentTimeMillis();
        //此map装载 匹配项 其中key为excel-index是Long类型 是匹配excel的某一行 key为specified_field 指定是实际需要设置的实体字段名称
        //不写就可以不匹配
        HashMap<String,Object> hashMap =new HashMap<String,Object>();
        hashMap.put("excel-index",3L);
        hashMap.put("specified_field","memberLevelId");
        hashMap.put("三星",30L);
        hashMap.put("四星",40L);
        hashMap.put("五星",50L);
        hashMap.put("普通用户",10L);

        //此为接口实现  匹配映射的实体 如果需要修改 可修改接口实现 重新书写方法 构造器第二参数为处理的开始行
        RowExcelHandler<TAppUserInfoPO> tAppUserInfoPORowExcelHandler = new RowExcelHandler<TAppUserInfoPO>(new TAppUserInfoPO(), 1,hashMap);

        Map map = BigExcelUtils.readExcel(new FileInputStream("D:\\20.xlsx"),"20.xlsx", tAppUserInfoPORowExcelHandler,0);
        System.out.println( System.currentTimeMillis()-bg);
        System.out.println(((List)map.get("dataList")).size());
    }

}
