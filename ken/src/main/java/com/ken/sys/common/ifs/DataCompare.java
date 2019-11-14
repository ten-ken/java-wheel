/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/
package com.ken.sys.common.ifs;

import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>Title: 匠桥ERP系统-DataCompare</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2019/11/14 0014 上午 11:22
 */
public interface DataCompare {
    /**
     * 功能描述: 用于 数据库值和数据源对比 返回错误信息
     * @param existMap
     * @param dataList
     * @return: void
     * @author: swc
     * @date: 2019/11/14 0014 上午 11:37
    */
    <T> void handData(Map<Object,T> existMap, List<T> dataList);
}
