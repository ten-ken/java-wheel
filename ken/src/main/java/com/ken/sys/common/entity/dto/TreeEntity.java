package com.ken.sys.common.entity.dto;
import java.io.Serializable;

/**
 * <ul>
 * <li>Title: TreeEntity</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author ken
 * @date 2019/9/24 0024 上午 10:43
 */
public class TreeEntity<T> implements Serializable {
    private static final long serialVersionUID = 8350572296216436848L;
    private String id;
    private String pId;
    private String name;
    private boolean isParent;
    //是否展开
    private boolean open;

    private boolean checked;
    //是否禁用
    private boolean chkDisabled = false;

    //备用对象 存放一些信息
    private T t;

    public TreeEntity() {
    }

    public boolean isChkDisabled() {
        return this.chkDisabled;
    }

    public void setChkDisabled(boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return this.pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsParent() {
        return this.isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
