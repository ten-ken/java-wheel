/******************************************************************************
 * 
 * 版权所有：安徽驭鹏轮胎有限公司
 * 
 ******************************************************************************
 * 注意：本内容仅限于安徽驭鹏轮胎有限公司内部使用，禁止转发
 *****************************************************************************/
package com.ken.sys.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <ul>
 * <li>Title: 驭鹏轮胎商城系统-DateUtil.java</li>
 * <li>Description: Yplt Mall System</li>
 * <li>Copyright: Copyright (c) 2017</li>
 * <li>Company: http://www.fyyplt.com/</li>
 * </ul>
 *
 * @author shenggs
 * @version 驭鹏轮胎商城系统1.0
 * @date 2017年12月27日 下午8:22:09
 */
public class DateUtil {

    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    
    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
    
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    
    
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 功能说明：取系统当前时间
     * @author shenggs
     * @date 2017年12月13日 下午4:08:45
     * @return
     */
    public final static Date getSysDate() {
        return Calendar.getInstance().getTime();
    }
    
    /**
     * 功能说明：时间类型转换成字符串类型
     * @author shenggs
     * @date 2017年12月14日 下午2:35:16
     * @param date
     * @param format
     * @return
     */
    public final static String dateToStr(Date date,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(date); 
    }
    
    /**
     * 功能说明：时间类型转换成字符串类型
     * @author shenggs
     * @date 2017年12月14日 下午2:35:16
     * @param date
     * @param format
     * @return
     */
    public final static Date strToDate(String date,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getNextDate(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        Date date = calendar.getTime();
        return date;
    }
    
}
