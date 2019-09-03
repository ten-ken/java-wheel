package com.ken.sys.common.ifs;

/**
 * <ul>
 * <li>Title: AbstractCellStyle</li>
 * <li>Description: 实现单元格样式接口的抽象类 </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2019/9/3 0003 上午 10:30
 */
public abstract class AbstractCellStyle implements MyCellStyle{
    protected int MAX_Len =3000;

    public int getMAX_Len() {
        return MAX_Len;
    }
}
