/******************************************************************************
 *
 * 作者（author）：ken
 * 微信（weChat）：mlchao1992
 * 个人博客（website）：
 *
 ******************************************************************************
 * 注意：尊重原创
 *****************************************************************************/
package com.ken.sys.common.util;

import java.util.List;

/**
 * <ul>
 * <li>Title: ken-TreeInfoVo</li>
 * <li>Description: 封装的tree信息 </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author ken
 * @version V1.0
 * @date 2019/9/20 20:35
 */
public class TreeInfoVo<T> {
    //每个父节点下的数信息
    private List<T> treeList;
    //主要树信息
    private List<T> trees;

    public List<T> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<T> treeList) {
        this.treeList = treeList;
    }

    public List<T> getTrees() {
        return trees;
    }

    public void setTrees(List<T> trees) {
        this.trees = trees;
    }
}
