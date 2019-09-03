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

import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>Title: IExcelRowReader</li>
 * <li>Description: excel行处理的通用接口 </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author ken
 * @version V1.0
 * @date 2019/8/30 23:51
 */
public interface IExcelRowReader {
    /**
     * 功能描述: 
     * @param i 当前列
     * @param i1 当前行
     * @param list 行数据
     * @return: void 
     * @author: swc
     * @date: 2019/8/31 0:42
    */
    void handle(int i, int i1, List<Object> list);

    /**
     * 功能描述: 取出处理后的数据
     * @param
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @author: swc
     * @date: 2019/8/31 0:44
    */
    Map<String,Object> getHandData();
}
