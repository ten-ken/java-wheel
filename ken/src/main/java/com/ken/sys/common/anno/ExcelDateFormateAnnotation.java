/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/
package com.ken.sys.common.anno;

import java.lang.annotation.*;

/**
 * <ul>
 * <li>Title: 匠桥ERP系统-ExportExcelAnnotation</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2019/9/3 0003 下午 15:44
 */
@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelDateFormateAnnotation {
    String fieldName() default ""; //字段名称
    String dateFormate() default ""; // 验证规则---时间格式可以写YYYYMMDD
}