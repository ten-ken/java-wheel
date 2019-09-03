package com.ken.sys.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**excel导入自定义注解
 * @author: ken
 * @date: 2019/8/28 0028 下午 20:07
*/ 
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumnAnnotation {
    int index() default -1;// -1表示该字段段不用从excel里面读取，0，1,2,3表示该字段属于excel哪个列

    String dataType() default DataType.STRING;// 数据类型

    String fieldName() default ""; //字段名称

    int length() default 32;// 长度

    boolean nullable() default false;// 是否为空

    boolean isRepeat() default true;//是否允许重复

    String pattern() default ""; // 验证规则---时间格式可以写YYYYMMDD

    String columnName() default ""; //excel列名称
}
