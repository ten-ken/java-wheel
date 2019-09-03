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
import java.util.Random;

/**
 * <ul>
 * <li>Title: StringUtils</li>
 * <li>Description: 处理字符串的工具类 </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author swc
 * @version V1.0
 * @date 2019/8/10 21:22
 */
public class StringUtils {

    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";
    private static final String WORDS = "abcdefghijklmnopqrstuvwxyz0123456789";


    /**
     * 功能描述: 首字母大写
     * @param str
     * @return: java.lang.String
     * @author: swc
     * @date: 2019/8/10 21:23
    */
    public static String captureName(String str) {
        if(EmptyUtils.isNullOrEmpty(str)){
            return str;
        }
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs=str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }

    /**
     * 功能描述: 切割字符串成数组
     * @param propertyName
     * @param separator
     * @return: java.lang.String[]
     * @author: swc
     * @date: 2019/8/28 0028 上午 10:35
    */
    public static String[] split(String propertyName, String separator) {
        try{
            if(propertyName==null){
                return null;
            }else if(propertyName.length()==0){
                return StringUtils.EMPTY_STRING_ARRAY;
            }else{
                return propertyName.split(separator);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return StringUtils.EMPTY_STRING_ARRAY;
        }
    }

    /**
     * 驼峰命名法工具
     * @return
     * 		toCamelCase("hello_world") == "helloWorld"
     * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * 		toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }


    /**
     * 功能描述: 生成随机数
     * @param length
     * @return: java.lang.String
     * @author: swc
     * @date: 2019/3/2 15:16
     */
    public static String getRandomStringByLength(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(WORDS.length());
            sb.append(WORDS.charAt(number));
        }
        return sb.toString();
    }

    public static boolean moreThanMaxLen(String  compare1, int maxLen) {
        if(compare1==null){
            return false;
        }
        return compare1.toString().length()>maxLen;
    }

    public static boolean isNotBlank(String  source) {
        return source != null  && source.trim().length() > 0;
    }

    public static boolean isNotEmpty(String  source) {
        return source != null  && source.length() > 0;
    }

}
