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

import java.util.Iterator;
import java.util.List;


public class ComboboxHelper {
    private ComboboxHelper() {
    }

    public static ComboboxHelper getInstance() {
        return new ComboboxHelper();
    }

    /**
     * 功能描述: 生成前端下拉框的样式
     * @param lst
     * @param codeName
     * @param valueName
     * @param rels  存储携带字段的数据
     * @param needHead
     * @return: java.lang.String 
     * @author: swc
     * @date: 2019/8/28 0028 下午 20:58
    */ 
    public String toCombobox(List<?> lst, String codeName, String valueName, String[] rels, boolean needHead) {
        StringBuffer stringBuffer = new StringBuffer();
        if (needHead) {
            stringBuffer.append("<option value=''>请选择</option>");
        }

        if (lst != null && lst.size() != 0) {
            Iterator i$ = lst.iterator();

            while(i$.hasNext()) {
                Object baseEntity = i$.next();
                stringBuffer.append("<option value=\"");
                stringBuffer.append(ReflectUtils.getFieldValue(baseEntity, codeName));
                stringBuffer.append("\"");
                if (rels != null && rels.length > 0) {
                    for(int i = 0; i < rels.length; ++i) {
                        stringBuffer.append("data-"+rels[i]);
//                        stringBuffer.append(i + 1);
                        stringBuffer.append("=\"");
                        stringBuffer.append(ReflectUtils.getFieldValue(baseEntity, rels[i]));
                        stringBuffer.append("\"");
                    }
                }

                stringBuffer.append(">");
                stringBuffer.append(ReflectUtils.getFieldValue(baseEntity, valueName));
                stringBuffer.append("</option>");
            }

            return stringBuffer.toString();
        } else {
            return stringBuffer.toString();
        }
    }

    /**
     * 功能描述: 加入一个OPTGROUP标签 比上面
     * @param lst
     * @param groupName
     * @param codeName
     * @param valueName
     * @param rels  存储携带字段的数据
     * @param needHead
     * @return: java.lang.String 
     * @author: swc
     * @date: 2019/8/28 0028 下午 21:02
    */ 
    public String toComboboxByGroup(List<?> lst, String groupName, String codeName, String valueName, String[] rels, boolean needHead) {
        StringBuffer stringBuffer = new StringBuffer();
        if (needHead) {
            stringBuffer.append("<option value=''>请选择</option>");
        }

        if (lst != null && lst.size() != 0) {
            String tempGroup = "";
            Iterator i$ = lst.iterator();

            while(i$.hasNext()) {
                Object baseEntity = i$.next();
                String group = (String)ReflectUtils.getFieldValue(baseEntity, groupName);
                if (!tempGroup.equals(group)) {
                    stringBuffer.append("<OPTGROUP LABEL=\"");
                    stringBuffer.append(group);
                    stringBuffer.append("\">");
                }

                tempGroup = group;
                stringBuffer.append("<option value=\"");
                stringBuffer.append(ReflectUtils.getFieldValue(baseEntity, codeName));
                stringBuffer.append("\"");
                if (rels != null && rels.length > 0) {
                    for(int i = 0; i < rels.length; ++i) {
                        stringBuffer.append("data-"+rels[i]);
                        stringBuffer.append(i + 1);
                        stringBuffer.append("=\"");
                        stringBuffer.append(ReflectUtils.getFieldValue(baseEntity, rels[i]));
                        stringBuffer.append("\"");
                    }
                }

                stringBuffer.append(">");
                stringBuffer.append(ReflectUtils.getFieldValue(baseEntity, valueName));
                stringBuffer.append("</option>");
            }

            return stringBuffer.toString();
        } else {
            return stringBuffer.toString();
        }
    }
}
