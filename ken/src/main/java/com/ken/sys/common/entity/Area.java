/******************************************************************************
 *
 * 作者（author）：ken
 * 微信（weChat）：mlchao1992
 * 个人博客（website）：
 *
 ******************************************************************************
 * 注意：尊重原创
 *****************************************************************************/
package com.ken.sys.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * <ul>
 * <li>Title: ken-Area</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author ken
 * @version V1.0
 * @date 2019/9/21 13:50
 */
public class Area implements Serializable {
    private String pid;

    private String name;

    private String code;

    private String active;

    private List<Area> childList;

    public Area(  String code,String pid,String name) {
        this.pid = pid;
        this.name = name;
        this.code = code;
    }

    public Area() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Area> getChildList() {
        return childList;
    }

    public void setChildList(List<Area> childList) {
        this.childList = childList;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
