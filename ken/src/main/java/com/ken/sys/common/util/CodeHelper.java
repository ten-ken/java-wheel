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


import com.ken.sys.common.anno.CodeValueColumn;
import com.ken.sys.common.entity.SysCodeDetail;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CodeHelper {


    private static  ConcurrentHashMap<String, List<SysCodeDetail>> codeDatas = null;

    static {
        //此处查询 缓存
        //(ConcurrentHashMap<String, List<SysCodeDetail>>)WebContextUtil.getServletContext().getAttribute("code");
        codeDatas =new ConcurrentHashMap<String, List<SysCodeDetail>>();
    }

    private CodeHelper(){

    }

    public static CodeHelper getInstance() {
        return new CodeHelper();
    }

    public <T> List<T> setCodeValue(List<T> lst) {
        if (lst == null || lst.size() == 0) {
            return lst;
        }
        try {
            List<Field> annotation = new ArrayList<Field>();
            Field[] field = null;
            for (Class<?> clazz = lst.get(0).getClass(); clazz != Object.class; clazz = clazz
                    .getSuperclass()) {
                try {
                    field = clazz.getDeclaredFields();
                    for (int i = 0; i < field.length; i++) {
                        if (field[i].isAnnotationPresent(CodeValueColumn.class)) {
                            annotation.add(field[i]);
                        }
                    }
                } catch (Exception e) {
                }
            }
            T baseEntity = null;
            List<SysCodeDetail> codeData = null;
            LinkedHashMap<String, Object> enumData = null;
            ConcurrentHashMap<String, List<SysCodeDetail>> codeDatas =new ConcurrentHashMap<String, List<SysCodeDetail>>();
            for (int j = 0; j < lst.size(); j++) {
                baseEntity = lst.get(j);
                setCodeValue(baseEntity, annotation);
            }
            return lst;
        } catch (Exception e) {
            return lst;
        }
    }

    public <T> T setCodeValue(T baseEntity, List<Field> annotation) {
        List<SysCodeDetail> codeData;
        for (Field each : annotation) {
            CodeValueColumn codeValueColumn = each.getAnnotation(CodeValueColumn.class);
            Object object = ReflectUtils.getFieldValue(baseEntity, each.getName());
            codeData = codeDatas.get(codeValueColumn.typeKey());
            if (codeData != null) {
                for (SysCodeDetail sysCodeDetail: codeData) {
                    if (object != null && sysCodeDetail.getCode().equals(object.toString())) {
                        ReflectUtils.setFieldValue(baseEntity, codeValueColumn.typeName(), sysCodeDetail.getValue());
                    }
                }
            }
        }
        return baseEntity;
    }
}