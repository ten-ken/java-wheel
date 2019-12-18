/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/
package com.ken.sys.common.entity;

import java.math.BigDecimal;

/**
 * <ul>
 * <li>Title: 匠桥ERP系统-EchartPo</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2019/12/3 0003 下午 17:37
 */
public class EchartPo {
    private String name;
    private String visitType;
    private String stack;
    private Long[] data;
    private BigDecimal value;

    //直接访问次数
    private Long redictCount;

    //邮件营销访问次数
    private Long emailCount;


    //视频广访问次数
    private Long videoCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public Long[] getData() {
        return data;
    }

    public void setData(Long[] data) {
        this.data = data;
    }

    public Long getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(Long emailCount) {
        this.emailCount = emailCount;
    }

    public Long getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Long videoCount) {
        this.videoCount = videoCount;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public Long getRedictCount() {
        return redictCount;
    }

    public void setRedictCount(Long redictCount) {
        this.redictCount = redictCount;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
