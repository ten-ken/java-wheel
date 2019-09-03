/******************************************************************************
 *
 * 作者（author）：ken
 * 微信（weChat）：mlchao1992
 * 个人博客（website）：
 *
 ******************************************************************************
 * 注意：尊重原创
 *****************************************************************************/

package com.ken.sys.common.ifs;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * <ul>
 * <li>Title: MyCellStyle</li>
 * <li>Description: 设置单元格样式的接口 </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author ken
 * @version V1.0
 * @date 2019/9/2 0002 下午 16:12
 */
public interface MyCellStyle {

    //获取头部样式
    CellStyle getHeadStyle(CellStyle cellStyle, Font font);

    //设置样式
    void setCellStyle(Sheet sheet,CellStyle style, Font font, Row row);

    //设置某行某列单元格样式
    void setCellStyle(int rowInd, int colInd,Sheet sheet, String filedName, CellStyle cellStyle, Font font, Row row);

    //获取做的数据量
    int getMAX_Len();
}
