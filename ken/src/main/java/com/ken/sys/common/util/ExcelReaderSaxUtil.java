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
import com.ken.sys.common.entity.TAppUserInfoPO;
import com.ken.sys.common.ifs.IExcelRowReader;
import com.ken.sys.common.excel.saxmodel.Excel2003SaxReader;
import com.ken.sys.common.excel.saxmodel.Excel2007SaxReader;
import com.ken.sys.common.excel.saxmodel.RowExcelHandler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * <ul>
 * <li>Title: ExcelReaderSaxUtil</li>
 * <li>Description: 原生poi的excel的sax解析 </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author ken
 * @version V1.0
 * @date 2019/8/25 10:51
 */
public class ExcelReaderSaxUtil {
    // excel2003扩展名
    public static final String EXCEL03_EXTENSION = ".xls";
    // excel2007扩展名
    public static final String EXCEL07_EXTENSION = ".xlsx";

    /**
     * 读取Excel文件，可能是03也可能是07版本
     *
     * @param fileName
     * @throws Exception
     */
    public static Map readExcel(String fileName , InputStream inputStream, IExcelRowReader rowHandler) throws Exception {
        // 处理excel2003文件
        if (fileName.endsWith(EXCEL03_EXTENSION)) {
            Excel2003SaxReader excel2003SaxReader =new Excel2003SaxReader(rowHandler);
            excel2003SaxReader.process(inputStream);
            Map<String, Object> handData = rowHandler.getHandData();
            return handData;
        } else if (fileName.endsWith(EXCEL07_EXTENSION)) {
            // 处理excel2007文件
            Excel2007SaxReader excel2007SaxReader =new Excel2007SaxReader(rowHandler);
            excel2007SaxReader.processOneSheet(inputStream,1);
            Map<String, Object> handData = rowHandler.getHandData();
            return handData;
        } else {
            throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
        }
    }

    public static void main(String[] args) throws Exception{
        long bg = System.currentTimeMillis();
        HashMap<String,Object> hashMap =new HashMap<String,Object>();
        hashMap.put("excel-index",3);
        hashMap.put("specified_field","memberLevelId");
        hashMap.put("三星",30L);
        hashMap.put("二星",20L);
        hashMap.put("一星",10L);
        hashMap.put("四星",40L);
        hashMap.put("五星",50L);
        hashMap.put("普通用户",10L);
        RowExcelHandler<TAppUserInfoPO> tAppUserInfoPORowExcelHandler = new RowExcelHandler<TAppUserInfoPO>(new TAppUserInfoPO(), 1,hashMap);
        Map map = ExcelReaderSaxUtil.readExcel("15.xlsx", new FileInputStream("D:\\15.xlsx"), tAppUserInfoPORowExcelHandler);
        System.out.println(System.currentTimeMillis()- bg);
    }

}
