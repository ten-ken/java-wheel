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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <ul>
 * <li>Title: RegExUtil</li>
 * <li>Description: Java正则验证 </li>
 * </ul>
 * <url>
 *     <value>https://www.cnblogs.com/lzq198754/p/5780340.html</value>
 * </url>
 *
 * @author swc
 * @date 2019/2/13 0013 下午 15:23
 */
public class RegExUtil {

    /****邮箱匹配方式一**/
    public static final String emailCheckone ="^[A-Za-z0-9]+@([A-Za-z0-9]+\\.){1,2}[A-Za-z0-9]+$";//不允许有下划线
    /****邮箱匹配方式二**/
    public static final String emailChecktwo ="^\\w+@(\\w+\\.){1,2}\\w+$";//允许有下划线
    /****邮箱匹配方式三**/
    public static final String emailCheckThree ="^[0-9a-zA-Z]\\w+@\\w{2,5}\\.\\w{2,5}$";//允许有下划线,但首位不得是下划线(自己写的)

    /****身份证号匹配方式一**/
    public static final String idCardCheck="^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X|x)?$";

    /****身份证号匹配方式二**/
    public static final String idCardCheckTwo="^(\\d{6})(18|19|20)?(\\d{2})(0[1-9]|1[0-2])([012]\\d|3[0-1])(\\d{3})(\\d|X|x)?$";//自己改进的

    /****身份证号匹配方式二**/
    public static final String checkPhone="^1[3-9]{1}[0-9]\\d{8}$";

    /**
     * 功能描述: 正则表达的设置
     * @param reg  正则
     * @param str  匹配的字符串
     * @return: java.util.regex.Matcher
     * @author: swc
     * @date: 2019/2/14 0014 下午 16:14
     */
    public static Matcher setPublicConfig(String reg, String str) {
        Pattern pattern=Pattern.compile(reg);
        Matcher matcher= pattern.matcher(str);
        return  matcher;
    }

    /**
     * 功能描述: 邮编验证
     * @param str
     * @return: boolean
     * @author: swc
     * @date: 2019/2/13 0013 下午 15:49
     */
    public static boolean checkPostcodes(String str){
        String reg="^[1-9]\\d{5}$";//
        Matcher matcher=setPublicConfig(reg,str);
        return  matcher.matches();
    }

    /**
     * 功能描述: 汉字验证(无英文 @/&/#等特殊符号  中文逗号分号可以有)
     * @param str
     * @return: boolean
     * @author: swc
     * @date: 2019/2/13 0013 下午 15:50
     */
    public static boolean checkChineseCharacters(String str){
        String reg="^[\\u0391-\\uFFE5]+$";
        Matcher matcher=setPublicConfig(reg,str);
        return  matcher.matches();
    }

    /**
     * 功能描述: 验证qq号码
     * @param str
     * @return: boolean
     * @author: swc
     * @date: 2019/2/13 0013 下午 16:03
     */
    public static boolean checkQQ(String str){
        String reg="^[1-9]\\d{4,10}$";
        Matcher matcher=setPublicConfig(reg,str);
        return  matcher.matches();
    }

    /**
     * 功能描述: 验证邮箱
     * @param str
     * @param reg
     * @return: boolean
     * @author: swc
     * @date: 2019/2/13 0013 下午 16:27
     */
    public static boolean checkEmail(String str,String reg){
        Matcher matcher=setPublicConfig(reg,str);
        return  matcher.matches();
    }

    /**
     * 功能描述: 这部分只验证手机号码长度和正常的号段
     * @param str
     * @return: boolean
     * @author: swc
     * @date: 2019/2/13 0013 下午 16:35
     */
    public static boolean checkPhone(String str){
        String reg="^1[3-9]{1}[0-9]\\d{8}$";
        Matcher matcher=setPublicConfig(reg,str);
        return  matcher.matches();
    }

    /**
     * 功能描述: 验证身份证号
     * @param str
     * @return: boolean
     * @author: swc
     * @date: 2019/2/13 0013 下午 16:43
     */
    public static boolean idCardCheck(String str){
        Matcher matcher=setPublicConfig(idCardCheckTwo,str);
        return  matcher.matches();
    }

    /***
     * 功能描述: 验证是否是url
     * @param str
     * @return: boolean
     * @author: swc
     * @date: 2019/2/13 0013 下午 17:20
     */
    public static boolean urlCheck(String str){
        //String reg="^((http|https)://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";
        String reg="^(http|https)://?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";
        Matcher matcher=setPublicConfig(reg,str);
        return  matcher.matches();
    }

    /**
     * 功能描述: 验证是否是正整数
     * @param str
     * @return: boolean
     * @author: swc
     * @date: 2019/2/14 0014 下午 16:35
     */
    public static boolean positiveIntegerCheck(String str){
        String reg="^[1-9]{1}\\d+$";
        Matcher matcher =setPublicConfig(reg,str);
        return matcher.matches();
    }

    /**
     * 功能描述: 验证是否是数字
     * @param str
     * @return: boolean
     * @author: swc
     * @date: 2019/2/14 0014 下午 16:35
     */
    public static boolean numberCheck(String str){
        String reg="^[+-]?[1-9\\d|0]+$";
        Matcher matcher =setPublicConfig(reg,str);
        return matcher.matches();
    }


    /**
     * 功能描述: 替换字符串中空格、制表符、换页符的其中任意一个 （去内容的空格和换行）
     * @param str
     * @return: java.lang.String
     * @author: swc
     * @date: 2019/2/14 0014 下午 16:22
     */
    public static String replaceTrim(String str){
        String reg="\\s*";
        return  str.replaceAll(reg, "");
    }

    /**
     * 功能描述: 将字符串里数字替换掉
     * @param str
     * @param replace 替换后的内容
     * @return: java.lang.String
     * @author: swc
     * @date: 2019/2/14 0014 下午 16:29
     */
    public static String replaceNumber(String str,String replace){
        return str.replaceAll("\\d+",replace);
    }


    /***
     * 功能描述: 完全匹配--通用
     * @param pattern
     * @param str
     * @return: boolean 
     * @author: swc
     * @date: 2019/8/30 0030 上午 9:28
    */ 
    public static boolean checkStr(String pattern, String str) {
        Matcher matcher =setPublicConfig(pattern,str);
        return matcher.matches();
    }
}
