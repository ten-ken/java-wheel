/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/
package com.ken.sys.common.message.impl;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * <ul>
 * <li>Title: 匠桥ERP系统-BoolMessage</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2019/9/2 0002 下午 13:06
 */
public class BoolMessage<T> extends AbstractMessage {
    private boolean success;
    private String message;
    private T record;
    private List<T> records;

    public List<T> getRecords() {
        return this.records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public T getRecord() {
        return this.record;
    }

    public void setRecord(T t) {
        this.record = record;
    }

    public BoolMessage() {
    }

    public BoolMessage(JSONObject json) {
        this.json = json;
    }

    public BoolMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public BoolMessage(boolean success, String message, T record) {
        this.success = success;
        this.message = message;
        this.record = record;
    }


    public BoolMessage(boolean success, String message, T record, List<T> records) {
        this.success = success;
        this.message = message;
        this.record = record;
        this.records = records;
    }

    public JSONObject buildJSON() {
        this.json = this.currentJson();

        try {
            this.json.put("message", this.message);
            this.json.put("success", this.success);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return this.currentJson();
    }

    public void putInJSON(JSONObject record) {
        try {
            record.put("message", this.message);
            record.put("success", this.success);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void putMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            BoolMessage that = (BoolMessage)o;
            return this.success == that.success;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.success ? 1 : 0;
    }
}

