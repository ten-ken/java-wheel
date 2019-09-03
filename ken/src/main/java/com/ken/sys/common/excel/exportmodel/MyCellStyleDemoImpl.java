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
import com.ken.sys.common.ifs.AbstractCellStyle;
import com.ken.sys.common.ifs.MyCellStyle;
import com.ken.sys.common.util.EmptyUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * <ul>
 * <li>Title: MyCellStyleDemoImpl</li>
 * <li>Description: 设置单元格的实现类-demo </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author ken
 * @date 2019/9/2 0002 下午 16:37
 */
public class MyCellStyleDemoImpl extends AbstractCellStyle {
    public MyCellStyleDemoImpl() {
    }

    public MyCellStyleDemoImpl(int maxLen) {
        MAX_Len = maxLen;
    }


    /***
     * 功能描述: 单元格样式设置
     * @param sheet sheet  可以合并单元格等等操作
     * @param style 单元格样式
     * @param font 单元格字体
     * @param row
     * @return: void
     * @author: ken
     * @date: 2019/9/2 0002 下午 17:51
    */
    @Override
    public void setCellStyle(Sheet sheet, CellStyle style, Font font, Row row) {
        //coding demo
        if(style!=null){
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);      //居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
            style.setTopBorderColor(HSSFColor.BLACK.index);     //上边框颜色
            style.setBottomBorderColor(HSSFColor.BLACK.index);
            style.setLeftBorderColor(HSSFColor.BLACK.index);
            style.setRightBorderColor(HSSFColor.BLACK.index);
        }
        if(font!=null){
            font.setFontName("宋体");//给样式指定字体
            font.setFontHeightInPoints((short) 14);              // 字体大小
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);        // 加粗
            style.setFont(font);
        }
        row.getCell(0).setCellStyle(style);        //给标题设置样式
        row.setHeight((short)1000);
    }


    /**
     * 功能描述:
     * @param style 单元格样式
     * @param font 单元格字体
     * @return: org.apache.poi.ss.usermodel.CellStyle 
     * @author: ken
     * @date: 2019/9/2 21:07
    */ 
    @Override
    public CellStyle getHeadStyle(CellStyle style, Font font) {
        //coding  demo
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);      //居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        style.setTopBorderColor(HSSFColor.BLACK.index);     //上边框颜色
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);

        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);              // 字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);        // 加粗
        style.setFont(font);                                 //给样式指定字体
        return style;
    }

    /**
     * 功能描述: 设置单元格样式
     * @param rowInd 当前行
     * @param colInd 当前列
     * @param sheet sheet
     * @param filedName 实体字段
     * @param cellStyle 单元格样式
     * @param font 字体
     * @param row  行
     * @return: void
     * @author: ken
     * @date: 2019/9/2 21:06
    */

    @Override
    public void setCellStyle(int rowInd, int colInd,Sheet sheet, String filedName, CellStyle cellStyle, Font font, Row row) {
        System.out.println(rowInd+" - "+colInd);
        return;
    }

}
