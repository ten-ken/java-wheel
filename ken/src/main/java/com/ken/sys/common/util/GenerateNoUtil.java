/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/
package com.ken.sys.common.util;

import java.util.Date;
import java.util.Random;

/**
 * <ul>
 * <li>Title: 匠桥ERP系统-GenerateNoUtil</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 *
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2020/3/11 0011 下午 15:05
 */
public class GenerateNoUtil {

    //最大长度
    private static Integer maxLength =20;

    public static void main(String[] args) {
        getCodeNo(null,990);
    }

    /**
     * 功能描述: 同时同秒  配上用户的id 加0-3位随机数
     * @param pref
     * @param userId
     * @return: java.lang.String
     * @author: swc
     * @date: 2020/3/11 0011 下午 16:17
    */
    public  static String getCodeNo(String pref,long userId){
        StringBuffer sbf =new StringBuffer();
        if(!EmptyUtils.isNullOrEmpty(pref)){
            sbf.append(pref);
        }
        sbf.append(DateUtil.dateToStr(new Date(),"yyyyMMddHHmm"));
        sbf.append(String.format("%05d", userId));//将用户id拼接上  补足五位补位

        Random random =new Random();
        if(sbf.length()==maxLength-3){
            sbf.append(random.nextInt(9));
        }
        if(sbf.length()==maxLength-2){
            sbf.append(random.nextInt(9));
        }
        if(sbf.length()==maxLength-1){
            sbf.append(random.nextInt(9));
        }
        System.out.println(sbf.toString());
        return sbf.toString();
    }

}
