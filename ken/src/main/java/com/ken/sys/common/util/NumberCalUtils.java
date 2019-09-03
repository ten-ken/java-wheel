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
import java.math.BigDecimal;

/**
 * <ul>
 * <li>Title: NumberCalUtils</li>
 * <li>Description: 除了BigDecimal的Integer Long Double等类型的加减乘除 </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2019/8/28 0028 下午 14:21
 */
public class NumberCalUtils {
    private static final String defaultAddAndSub ="0";
    private static final String defaultMulAndDiv ="1";

    /**
     * 功能描述: 去除null数据 默认值为0
     * @param v
     * @return: java.lang.String 
     * @author: swc
     * @date: 2019/8/28 0028 下午 16:41
    */ 
    public static <T extends Number> String removeNull(T v,String type){
        if(EmptyUtils.isNullOrEmpty(v)){
            if("2".equals(type)){
                return NumberCalUtils.defaultMulAndDiv;
            }
            return NumberCalUtils.defaultAddAndSub;
        }
        return v.toString();
    }

    /**
     * 功能描述: 类型判断进行转换
     * @param val1
     * @param v1
     * @return: java.lang.Object 
     * @author: swc
     * @date: 2019/8/28 0028 下午 16:40
    */ 
    public static <T extends Number> Object  backResult(Double val1, T v1,Integer precision) {
        BigDecimal bigDecimal =null;
        int len = val1.toString().indexOf(".")>0 && precision!=null ?val1.toString().indexOf("."):0;
        precision =(precision!=null && precision>0)?precision:val1.toString().length()-len;

        if(v1 instanceof Short){
            return Short.parseShort(val1.toString().replaceAll("[\\.][\\d]{0,15}",""));
        }else if(v1 instanceof Integer){
            return Integer.parseInt(val1.toString().replaceAll("[\\.][\\d]{0,15}",""));
        }else if(v1 instanceof Long){
            return Long.parseLong(val1.toString().replaceAll("[\\.][\\d]{0,15}",""));
        }else if(v1 instanceof Float){
            bigDecimal = BigDecimal.valueOf(Double.parseDouble(val1.toString())).setScale(precision,BigDecimal.ROUND_HALF_UP);
            return bigDecimal.floatValue();
        }else if(v1 instanceof Double){
            bigDecimal = BigDecimal.valueOf(Double.parseDouble(val1.toString())).setScale(precision,BigDecimal.ROUND_HALF_UP);
            return bigDecimal.doubleValue();

        }
        return val1;
    }

    /**
     *多个Number进行加法计算 （相加对象类型需一致 返回同一类型）
     * @param v1 被加数
     * @param mul 加数（可变长度形参）
     * @return
     */
    public static <T extends Number> T addMult(Integer precision,T v1, T... mul) throws Exception{
        Double val1 =Double.valueOf(removeNull(v1,null));
        if(!EmptyUtils.isNullOrEmpty(mul)){
            for (int i = 0; i < mul.length; i++) {
                val1+=Double.valueOf(removeNull(mul[i],null));
            }
        }
        return (T)backResult(val1,v1,precision);
    }

    /**
     *多个Number进行加法计算 （对象类型可以不一致 返回Double类型）
     * @param v1 被加数
     * @param mul 加数（可变长度形参）
     * @return
     */
    public static <T extends Number> Double addMultTwo(Integer precision,T v1, T... mul) throws Exception{
        Double val1 =Double.valueOf(removeNull(v1,null));
        if(!EmptyUtils.isNullOrEmpty(mul)){
            for (int i = 0; i < mul.length; i++) {
                val1+=Double.valueOf(removeNull(mul[i],null));
            }
        }
        int len = val1.toString().indexOf(".")>0 && precision!=null ?val1.toString().indexOf("."):0;
        precision =(precision!=null && precision>0)?precision:val1.toString().length()-len;

        return new BigDecimal(val1.toString()).setScale(precision,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     *多多个Number进行减法计算 （对象类型需一致 返回同一类型）
     * @param v1 被加数
     * @param mul 加数（可变长度形参）
     * @return
     */
    public static <T extends Number> T subMult(Integer precision,T v1, T... mul) throws Exception{
        Double val1 =Double.valueOf(removeNull(v1,null));
        if(!EmptyUtils.isNullOrEmpty(mul)){
            for (int i = 0; i < mul.length; i++) {
                val1-=Double.valueOf(removeNull(mul[i],null));
            }
        }
        return (T)backResult(val1,v1,precision);
    }

    /**
     *多个Number进行减法计算 （对象类型可以不一致 返回Double类型）
     * @param v1 被加数
     * @param mul 加数（可变长度形参）
     * @return
     */
    public static <T extends Number> Double subMultTwo(Integer precision,T v1, T... mul) throws Exception{
        Double val1 =Double.valueOf(removeNull(v1,null));
        if(!EmptyUtils.isNullOrEmpty(mul)){
            for (int i = 0; i < mul.length; i++) {
                val1-=Double.valueOf(removeNull(mul[i],null));
            }
        }
        int len = val1.toString().indexOf(".")>0 && precision!=null ?val1.toString().indexOf("."):0;
        precision =(precision!=null && precision>0)?precision:val1.toString().length()-len;

        return new BigDecimal(val1.toString()).setScale(precision,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     *多个Number进行乘法计算 （对象类型需一致 返回同一类型）
     * @param v1 被加数
     * @param mul 加数（可变长度形参）
     * @return
     */
    public static <T extends Number> T mulMult(Integer precision,T v1, T... mul) throws Exception{
        Double val1 =Double.valueOf(removeNull(v1,"2"));
        if(!EmptyUtils.isNullOrEmpty(mul)){
            for (int i = 0; i < mul.length; i++) {
                val1*= Double.valueOf(removeNull(mul[i],"2"));
            }
        }
        return (T)backResult(val1,v1,precision);
    }

    /**
     *多个Number进行乘法计算 （对象类型可以不一致 返回Double类型）
     * @param v1 被加数
     * @param mul 加数（可变长度形参）
     * @return
     */
    public static <T extends Number> Double mulMultTwo(Integer precision,T v1, T... mul) throws Exception{
        Double val1 =Double.valueOf(removeNull(v1,"2"));
        if(!EmptyUtils.isNullOrEmpty(mul)){
            for (int i = 0; i < mul.length; i++) {
                val1*=Double.valueOf(removeNull(mul[i],"2"));
            }
        }
        int len = val1.toString().indexOf(".")>0 && precision!=null ?val1.toString().indexOf("."):0;
        precision =(precision!=null && precision>0)?precision:val1.toString().length()-len;

        return new BigDecimal(val1.toString()).setScale(precision,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     *多个Number进行除法计算 （对象类型需要一致 返回同一类型）
     * @param v1 被加数
     * @param mul 加数（可变长度形参）
     * @return
     */
    public static <T extends Number> T divMult(Integer precision,T v1, T... mul) throws Exception{
        Double val1 =Double.valueOf(removeNull(v1,"2"));
        if(!EmptyUtils.isNullOrEmpty(mul)){
            for (int i = 0; i < mul.length; i++) {
                val1/=Double.valueOf(removeNull(mul[i],"2"));
            }
        }
        return (T)backResult(val1,v1,precision);
    }

    /**
     *多个Number进行除法计算 （对象类型可以不一致 返回Double类型）
     * @param v1 被加数
     * @param mul 加数（可变长度形参）
     * @return
     */
    public static <T extends Number> Double divMultTwo(Integer precision,T v1, T... mul) throws Exception{
        Double val1 =Double.valueOf(removeNull(v1,"2"));
        if(!EmptyUtils.isNullOrEmpty(mul)){
            for (int i = 0; i < mul.length; i++) {
                val1/=Double.valueOf(removeNull(mul[i],"2"));
            }
        }
        int len = val1.toString().indexOf(".")>0 && precision!=null ?val1.toString().indexOf("."):0;
        precision =(precision!=null && precision>0)?precision:val1.toString().length()-len;
        return new BigDecimal(val1.toString()).setScale(precision,BigDecimal.ROUND_HALF_UP).doubleValue();
    }




//    public static void main(String[] args) throws Exception {
//        Double number = NumberCalUtils.mulMultTwo(2,new Double("202.25"), new Double("200.25"));
//        System.out.println(number);
//    }



}
