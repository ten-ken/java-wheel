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
import org.springframework.beans.PropertyMatches;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private static final char SEPARATOR_ONE = '_';
    private static final char SEPARATOR_TWO = '-';
    private static final String CHARSET_NAME = "UTF-8";
    private static final String WORDS = "abcdefghijklmnopqrstuvwxyz0123456789!@";


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
            if (c == SEPARATOR_ONE || c==SEPARATOR_TWO) {
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

    /**
     * 功能描述: 判断字符串非空 去除前后空格
     * @param source
     * @return: boolean
     * @author: swc
     * @date: 2019/9/4 0004 上午 9:16
     */
    public static boolean isNotBlank(String  source) {
        return source != null  && source.trim().length() > 0;
    }

    /**
     * 功能描述: 判断字符串非空 不去除前后空格
     * @param source
     * @return: boolean
     * @author: swc
     * @date: 2019/9/4 0004 上午 9:16
    */
    public static boolean isNotEmpty(String  source) {
        return source != null  && source.length() > 0;
    }

    /**
     * 功能描述:获取两个字符串的相似度
     * @param str
     * @param target
     * @return: float
     * @date: 2017/9/4 0004 上午 10:02
    */
    public static float getSimilarityRatio(String str, String target) {
        int max = Math.max(str.length(), target.length());
        return 1 - (float) StringInnerUtils.compare(str, target) / max;
    }

    //这部分构建内部类是把算法核心隔离
    private static class StringInnerUtils  {
        /**
         * 比较两个字符串的相识度
         * 核心算法：用一个二维数组记录每个字符串是否相同，如果相同记为0，不相同记为1，每行每列相同个数累加
         * 则数组最后一个数为不相同的总数，从而判断这两个字符的相识度
         * @param str
         * @param target
         * @return
         */
        private static int compare(String str, String target) {
            int d[][];              // 矩阵
            int n = str.length();
            int m = target.length();
            int i;                  // 遍历str的
            int j;                  // 遍历target的
            char ch1;               // str的
            char ch2;               // target的
            int temp;               // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
            if (n == 0) {
                return m;
            }
            if (m == 0) {
                return n;
            }
            d = new int[n + 1][m + 1];
            // 初始化第一列
            for (i = 0; i <= n; i++) {
                d[i][0] = i;
            }
            // 初始化第一行
            for (j = 0; j <= m; j++) {
                d[0][j] = j;
            }
            for (i = 1; i <= n; i++) {
                // 遍历str
                ch1 = str.charAt(i - 1);
                // 去匹配target
                for (j = 1; j <= m; j++) {
                    ch2 = target.charAt(j - 1);
                    if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                        temp = 0;
                    } else {
                        temp = 1;
                    }
                    // 左边+1,上边+1, 左上角+temp取最小
                    d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
                }
            }
            return d[n][m];
        }

        /**
         * 功能描述: 获取最小的值
         * @param one
         * @param two
         * @param three
         * @return: int 
        */
        private static int min(int one, int two, int three) {
            return (one = one < two ? one : two) < three ? one : three;
        }
    }

    /**
     * 功能描述:是否高的匹配程度
     * @param str
     * @param target
     * @return: float
     * @date: 2017/9/4 0004 上午 10:02
     */
    public static boolean isHightRedio(String str, String target) {
        try {
            float similarityRatio = getSimilarityRatio(str, target);
            double multiple = NumberCalUtils.divMultTwo(3,str.length(),target.length());
            if((multiple>=5 || multiple<=0.2) && similarityRatio>=0.15){
                return true;
            }
            if((multiple>=3 || multiple<=0.33) && similarityRatio>=0.25){
                return true;
            }
            if(similarityRatio>=0.3333){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



//    public static void main(String[] args) {
//        String s1 ="6的倍数为基数";
//        String s2 ="他们的数制源自古巴比伦人，以数字6的倍数为基数";
//
//        float similarity =StringUtils.getSimilarityRatio(s2,s1);
//        System.out.println(similarity);
//        System.out.println(isHightRedio(s1,s2));
//    }
}
