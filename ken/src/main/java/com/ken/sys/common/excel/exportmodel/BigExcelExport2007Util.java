/******************************************************************************
 *
 * 作者（author）：ken
 * 微信（weChat）：mlchao1992
 * 个人博客（website）：
 *
 ******************************************************************************
 * 注意：尊重原创
 *****************************************************************************/
package com.ken.sys.common.excel.exportmodel;
import com.ken.sys.common.anno.ExcelDateFormateAnnotation;
import com.ken.sys.common.entity.TAppUserInfoPO;
import com.ken.sys.common.ifs.MyCellStyle;
import com.ken.sys.common.util.DateUtil;
import com.ken.sys.common.util.EmptyUtils;
import com.ken.sys.common.util.ReflectUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

/**
 * <ul>
 * <li>Title: ExportExcelData2007</li>
 * <li>Description: 导出百万级数据excel 2007  实测100万数据 22秒</li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author ken
 * @version 1.0
 * @date 2019/8/27 15:15
 */
public class BigExcelExport2007Util {
    private static BigExcelExport2007Util bigExcelExport2007Util =null;

    private static Logger logger = Logger.getLogger(BigExcelExport2007Util.class);

    //时间格式化
    private static Map<String,String> patternMap = new HashMap<String,String>();

    private static MyCellStyle myCellStyle;

    private static CellStyle style = null;

    private static  Font font = null;

    //默认是时间格式
    private static  String defaultDateFormate = DateUtil.FORMAT_YYYYMMDD;


    /**
     * 功能描述: 初始化
     * @param cellStyle
     * @param tClass
     * @return: void 
     * @author: swc
     * @date: 2019/9/3 0003 下午 16:11
    */ 
    public static <T> void initObj(MyCellStyle cellStyle,Class<T> tClass){
        if(bigExcelExport2007Util==null){
            bigExcelExport2007Util = new BigExcelExport2007Util(cellStyle);
        }
        if(EmptyUtils.isNullOrEmpty(tClass)){
            return;
        }
        Field[] accessibleField = tClass.getDeclaredFields();

        //设置时间格式
        for(Field field:accessibleField){
            field.setAccessible(true);
            ExcelDateFormateAnnotation annotation = field.getAnnotation(ExcelDateFormateAnnotation.class);
            if(!EmptyUtils.isNullOrEmpty(annotation)){
                patternMap.put(annotation.fieldName(),annotation.dateFormate());
            }
        }
    }

    public static BigExcelExport2007Util getNewInstance(){
        initObj(null,null);
        return bigExcelExport2007Util;
    }

    public static <T>BigExcelExport2007Util getNewInstance(Class<T> tClass){
        initObj(null,tClass);
        return bigExcelExport2007Util;
    }

    public static <T>BigExcelExport2007Util getNewInstance(MyCellStyle myCellStyle,Class<T> tClass){
        initObj(myCellStyle,tClass);
        return bigExcelExport2007Util;
    }


    public static BigExcelExport2007Util getNewInstance(MyCellStyle myCellStyle){
        initObj(myCellStyle,null);
        return bigExcelExport2007Util;
    }


    public BigExcelExport2007Util() {
    }

    public BigExcelExport2007Util(MyCellStyle myCellStyle) {
        this.myCellStyle = myCellStyle;
    }



    /***********************************************分割线***************************************************************/
    /***********************************************分割线***************************************************************/

    /**
     * 功能描述: 主入口---导出xlxs的excel文档
     * @param list
     * @param headsName
     * @param topName
     * @param filesName
     * @param width
     * @param sheetName
     * @return: void
     * @author: swc
     * @date: 2019/9/2 0002 下午 13:44
     */
    public  <T> SXSSFWorkbook bigExcelInit(SXSSFWorkbook workBook ,List<T> list, String[] filesName, String topName, String[] headsName, int[] width, String sheetName){
        if(EmptyUtils.isNullOrEmpty(filesName) || EmptyUtils.isNullOrEmpty(headsName)){
            logger.error("headsName或filesName值不得为空");
            return null;
        }
        if(headsName.length!=headsName.length){
            logger.error("headsName和filesName的长度请保持一致？参考实际情况。");
        }

        Sheet sheet = null;
        if(sheetName == null || sheetName == ""){
            sheet = workBook.createSheet("sheet1");
        }else{
            sheet = workBook.createSheet(sheetName);
        }
        for(int i=0; i < width.length; i++){
            //设置列宽
            sheet.setColumnWidth(i, width [i]);
        }

        if(!EmptyUtils.isNullOrEmpty(topName)){
            //创建标题行
            Row title = sheet.createRow(0);
            //给标题行单元格赋值
            title.createCell(0).setCellValue(topName);
            //合并单元格  构造参数依次为起始行，截至行，起始列， 截至列
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,headsName.length-1));
            //创建并初始化标题样式
            setDefaultTitleStyle(sheet,workBook, title);
        }
        //初始化抬头和样式
        InitExcelHead(workBook, sheet, headsName);
        //excel内容赋值
        setExcelValue(workBook,sheet,list,filesName);
        return workBook;
    }


    /**
     * 创建并初始化标题样式
     * @param workbook
     * @param title
     */
    public  void setDefaultTitleStyle(Sheet sheet, SXSSFWorkbook workbook, Row title) {
        if(!EmptyUtils.isNullOrEmpty(myCellStyle)){
            //设置特殊样式
            myCellStyle.setCellStyle(sheet,workbook.createCellStyle(),workbook.createFont(),title);
            return;
        }
        style = workbook.createCellStyle();              // 创建样式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);            // 字体居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中

        font = workbook.createFont();                         // 创建字体样式
        font.setFontName("宋体");                                   // 字体
        font.setFontHeightInPoints((short) 16);                    // 字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);              // 加粗
        style.setFont(font);                         //给样式指定字体
        title.getCell(0).setCellStyle(style);        //给标题设置样式
        title.setHeight((short) 1000);
    }



    /**
     * 功能描述: 初始化Excel表头
     * @param workBook
     * @param sheet
     * @param head
     * @return: org.apache.poi.ss.usermodel.Row
     * @author: swc
     * @date: 2019/9/2 0002 下午 16:19
     */
    private  Row InitExcelHead(SXSSFWorkbook workBook, Sheet sheet, String[] head) {
        Row row = sheet.createRow(1);
        CellStyle style = getHeaderStyle(workBook);             //获取表头样式
        for(int i=0; i<head.length; i++){
            row.createCell(i).setCellValue(head[i]);
            row.getCell(i).setCellStyle(style);                 //设置标题样式
        }
        return row;
    }



    /**
     * 功能描述: 处理数据部分 设置值
     * @param workBook
     * @param sheet
     * @param list
     * @param fileds
     * @return: void
     * @author: swc
     * @date: 2019/9/2 21:24
    */ 
    private  <T> void setExcelValue(SXSSFWorkbook workBook,Sheet sheet,  List<T> list, String[] fileds){
        StringBuffer buffer = new StringBuffer();
        for(int i=0; i<list.size(); i++){
            Row row = sheet.createRow(i+2);
            for(int j=0; j < fileds.length; j++){
                //限制设置的数据量 防止内存溢出
                if(!EmptyUtils.isNullOrEmpty(myCellStyle) && myCellStyle.getMAX_Len()>=list.size()){
                    myCellStyle.setCellStyle(i,j,sheet,fileds[j],workBook.createCellStyle(),workBook.createFont(),row);//设置字段部分的样式
                }
                setCellValue(row.createCell(j),list.get(i),fileds[j]);//设置单元格的值
                buffer.delete(0, buffer.length());
            }
        }

    }

    /**
     * 功能描述: 设置单元格的值
     * @param cell
     * @param t
     * @param fieldName
     * @return: void
     * @author: ken
     * @date: 2019/9/2 21:23
    */ 
    private  <T> void setCellValue(Cell cell, T t, String fieldName) {
        Object fieldValue = ReflectUtils.getFieldValue(t, fieldName);
        if(!EmptyUtils.isNullOrEmpty(fieldValue)) {
            if(fieldValue instanceof Date) {
                if(patternMap.containsKey(fieldName)){
                    cell.setCellValue(DateUtil.dateToStr((Date)fieldValue,patternMap.get(fieldName)));
                    return;
                }
                cell.setCellValue(DateUtil.dateToStr((Date)fieldValue,defaultDateFormate));
            }else if(fieldValue instanceof Number){
                cell.setCellValue(Double.parseDouble(fieldValue.toString()));
            } else{
                cell.setCellValue(String.valueOf(fieldValue));
            }
        }
    }


    /**
     * 功能描述: 处理导出
     * @param response
     * @param workBook
     * @param topName
     * @return: void
     * @author: swc
     * @date: 2019/9/2 0002 下午 16:13
     */
    public  void excelExport(HttpServletResponse response, SXSSFWorkbook workBook, String topName)throws IOException  {
        excelExport(response,null,workBook,topName);
    }


    /**
     * 功能描述: 处理导出  本地导出
     * @param output
     * @param workBook
     * @param topName
     * @return: void
     * @author: swc
     * @date: 2019/9/2 0002 下午 16:13
     */
    public  void excelExport(OutputStream output, SXSSFWorkbook workBook, String topName)throws IOException  {
        excelExport(null,output,workBook,topName);
    }

    /**
     * 功能描述: 导出的通用方法
     * @param response
     * @param output
     * @param workBook
     * @param topName
     * @return: void
     * @author: swc
     * @date: 2019/9/2 0002 下午 16:33
     */
    private  void excelExport(HttpServletResponse response,OutputStream output, SXSSFWorkbook workBook, String topName)throws IOException  {
        try {
            String srcFile = topName+DateUtil.dateToStr(DateUtil.getSysDate(),DateUtil.FORMAT_YYYYMMDDHHMMSS) +".xlsx";
            if(EmptyUtils.isNullOrEmpty(output)){
                //清空缓存
                response.reset();
                //设置响应
                response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(srcFile, "utf-8") + "\"");
                response.setContentType("application/vnd.ms-excel");
                output = response.getOutputStream();
            }
            workBook.write(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 功能描述: 获取Excel表头样式(第二行)
     * @param workbook
     * @return: org.apache.poi.ss.usermodel.CellStyle
     * @author: swc
     * @date: 2019/9/2 0002 下午 16:34
     */
    public  CellStyle getHeaderStyle(SXSSFWorkbook workbook) {
        if(!EmptyUtils.isNullOrEmpty(myCellStyle)){
            return myCellStyle.getHeadStyle(workbook.createCellStyle(),workbook.createFont());
        }
        style = workbook.createCellStyle();
        workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);      //居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        style.setTopBorderColor(HSSFColor.BLACK.index);     //上边框颜色
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);

        font = workbook.createFont();               // 创建字体样式
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);              // 字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);        // 加粗
        style.setFont(font);                                 //给样式指定字体

        return style;
    }


    public static void main(String[] args) throws Exception{
        long beginTime = System.currentTimeMillis();
        List<TAppUserInfoPO> list = new ArrayList<>();
        TAppUserInfoPO tAppUserInfoPO1 =null;

        for(int i=0;i<100000;i++){
            tAppUserInfoPO1 =new TAppUserInfoPO();
            tAppUserInfoPO1.setBirthday(new Date());
            tAppUserInfoPO1.setName("小明"+(i+1));
            tAppUserInfoPO1.setMemberLevelId((long)(i+1));
            tAppUserInfoPO1.setSetMeal(String.valueOf(i+1));
            tAppUserInfoPO1.setLocation("合肥"+i);
            tAppUserInfoPO1.setPreference("没有");
            tAppUserInfoPO1.setTel("18888888888");
            tAppUserInfoPO1.setNetworkTimes((long)(3*i+1));
            tAppUserInfoPO1.setSexName("男");
            list.add(tAppUserInfoPO1);
        }

        String[] fields = {"name","birthday","memberLevelId","tel","sexName","setMeal","networkTimes","location","preference"};
        String topName = "zzj666";
        String[] head = {"姓名","生日","级别","业务号码","性别","套餐","在网时长","归属地","使用偏好"};
        int[] width = {5000,5000,2000,5000,2000,2000,5000,5000,4000};


        BigExcelExport2007Util excelExport2007Util = BigExcelExport2007Util.getNewInstance(TAppUserInfoPO.class);
        SXSSFWorkbook sXSSFWorkbook = excelExport2007Util.bigExcelInit(new SXSSFWorkbook(),list, fields, "hhhhh777", head, width, null);
        excelExport2007Util.excelExport(new FileOutputStream("D:\\test_6666.xlsx"),sXSSFWorkbook,topName);

        long endTime = System.currentTimeMillis();
        double tt =(endTime-beginTime)/1000;

        System.out.println(tt);
    }


}
