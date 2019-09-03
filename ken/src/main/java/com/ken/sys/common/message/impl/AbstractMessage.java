/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/
package com.ken.sys.common.message.impl;
import com.alibaba.fastjson.JSONObject;
import com.ken.sys.common.message.Message;

/**
 * <ul>
 * <li>Title: 匠桥ERP系统-AbstractMessage</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2019/9/2 0002 下午 13:07
 */
public abstract class AbstractMessage implements Message {
    protected JSONObject json;

    public AbstractMessage() {
    }

    protected JSONObject currentJson() {
        return this.json == null ? new JSONObject() : this.json;
    }

    public String toString() {
        return this.buildJSON().toString();
    }
}
