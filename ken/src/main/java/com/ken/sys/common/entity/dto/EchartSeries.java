/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/
package com.ken.sys.common.entity.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * <ul>
 * <li>Title: Echarts常用的options里面的series（饼图一般是series里面的data部分）</li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 * T 为数据类型 一般建议为Long类型 或者 BigDecimal 类型
 * @author ken
 * @date 2019/12/3 0003 下午 17:37
 */
public class EchartSeries<T> {
    private String name;
    private String type;
    private List<T> data;
    private String stack;

    private BigDecimal value;

    //备用字段  方便一些自定义的参数 前端能正常接收
    private Object backObj;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Object getBackObj() {
        return backObj;
    }

    public void setBackObj(Object backObj) {
        this.backObj = backObj;
    }
}
