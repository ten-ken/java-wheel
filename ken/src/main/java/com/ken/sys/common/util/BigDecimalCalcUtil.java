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
 * Double 类型数据运算
 */
public class BigDecimalCalcUtil {
    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 2;


    public static BigDecimal removeNull(BigDecimal bigDecimal){
        if(EmptyUtils.isNullOrEmpty(bigDecimal)){
            return BigDecimal.ZERO;
        }
        return bigDecimal;
    }



    public static BigDecimal nullToNumberOne(BigDecimal bigDecimal){
        if(EmptyUtils.isNullOrEmpty(bigDecimal)){
            return BigDecimal.ONE;
        }
        return bigDecimal;
    }

    /**
     *提供精确的加法运算
     * @param v1 被加数
     * @param v2 加数
     * @param precision
     * @return
     */
    public static BigDecimal add(BigDecimal v1,BigDecimal v2,Integer precision){
        BigDecimal val1=removeNull(v1);
        BigDecimal val2=removeNull(v2);
        if(!EmptyUtils.isNullOrEmpty(precision)){
            precision = precision.compareTo(new Integer(0))<=0?0:precision;
            return val1.add(val2).setScale(precision,BigDecimal.ROUND_HALF_UP);
        }

        return val1.add(val2);
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @param precision
     */
    public static BigDecimal sub(BigDecimal v1,BigDecimal v2,Integer precision){
        BigDecimal val1=removeNull(v1);
        BigDecimal val2=removeNull(v2);
        if(!EmptyUtils.isNullOrEmpty(precision)){
            precision = precision.compareTo(new Integer(0))<=0?0:precision;
            return val1.subtract(val2).setScale(precision,BigDecimal.ROUND_HALF_UP);
        }
        return val1.subtract(val2);
    }
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @param precision
     * @return 两个参数的积
     */
    public static BigDecimal mul(BigDecimal v1,BigDecimal v2,Integer precision){
        BigDecimal val1=removeNull(v1);
        BigDecimal val2=removeNull(v2);
        if(!EmptyUtils.isNullOrEmpty(precision)){
            precision = precision.compareTo(new Integer(0))<=0?0:precision;
            return val1.multiply(val2).setScale(precision,BigDecimal.ROUND_HALF_UP);
        }
        return val1.multiply(val2);
    }


    /**
     * @description（相对）精确的除法运算
     * @param v1 被除数
     * @param v2 除数
     * @param precision
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal v1,BigDecimal v2,int precision){
        BigDecimal val1=removeNull(v1);
        BigDecimal val2=removeNull(v2);
        if(val2.compareTo(BigDecimal.ZERO)==0){
            return new BigDecimal("0");
        }
        if(precision<=0){
            precision=DEF_DIV_SCALE;
        }
        return val1.divide(val2,precision,BigDecimal.ROUND_HALF_UP);
    }




    /**
     *多个BigDecimal进行加法计算
     * @param precision  小于等于0为不设置精度
     * @param v1 被加数
     * @param mul 加数（可变长度形参）
     * @return
     */
    public static BigDecimal addMult(Integer precision,BigDecimal v1,BigDecimal... mul){
        BigDecimal val1=removeNull(v1);

        for (int i = 0; i < mul.length; i++) {
            val1 = val1.add(removeNull(mul[i]));
        }

        if(!EmptyUtils.isNullOrEmpty(precision) && precision>0){
            val1 = val1.setScale(precision,BigDecimal.ROUND_HALF_UP);
        }
        return val1;
    }


    /**
     *多个BigDecimal进行减法计算
     * @param precision 小于等于0为不设置精度
     * @param v1 被减数
     * @param mul 减数（可变长度形参）
     * @return
     */
    public static BigDecimal subMult(Integer precision,BigDecimal v1,BigDecimal... mul){
        BigDecimal val1=removeNull(v1);
        for (int i = 0; i < mul.length; i++) {
            val1 = val1.subtract(removeNull(mul[i]));
        }
        if(!EmptyUtils.isNullOrEmpty(precision) && precision>0){
            val1 = val1.setScale(precision,BigDecimal.ROUND_HALF_UP);
        }
        return val1;
    }



    /**
     *多个BigDecimal进行乘法计算
     * @param precision 小于等于0为不设置精度
     * @param v1 被乘数
     * @param mul 乘数（可变长度形参）
     * @return
     */
    public static BigDecimal mulMult(Integer precision,BigDecimal v1,BigDecimal... mul){
        BigDecimal val1=removeNull(v1);
        for (int i = 0; i < mul.length; i++) {
            val1 = val1.multiply(removeNull(mul[i]));
        }

        if(!EmptyUtils.isNullOrEmpty(precision)){
            precision = precision.compareTo(new Integer(0))<=0?0:precision;
            val1 = val1.setScale(precision,BigDecimal.ROUND_HALF_UP);
        }
        return val1;
    }




    /**
     *多个BigDecimal进行乘法计算
     * @param precision 小于等于0为不设置精度
     * @param v1 被乘数
     * @param mul 乘数（可变长度形参）
     * @return
     */
    public static BigDecimal divMult(Integer precision,BigDecimal v1,BigDecimal... mul){
        BigDecimal val1 = removeNull(v1);
        BigDecimal val2 = new BigDecimal("1");
        for (int i = 0; i < mul.length; i++) {
            val2 = val2.multiply(removeNull(mul[i]));
        }
        if(val2.compareTo(BigDecimal.ZERO)==0){
            return new BigDecimal("0");
        }
        if(!EmptyUtils.isNullOrEmpty(precision)){
            precision = precision.compareTo(new Integer(0))<=0?BigDecimalCalcUtil.DEF_DIV_SCALE:precision;
        }
        return val1.divide(val2,precision,BigDecimal.ROUND_HALF_UP);
    }

    /**
     *
     * @param v1 底数
     * @param v2 指数
     * @return
     */
    public static Double power(Object v1,Object v2){
        BigDecimal val=null;
        if(v1==null || v2==null){
            return  new Double("0");
        }
        double one = Double.parseDouble(v1.toString());
        double two = Double.parseDouble(v2.toString());
        return Math.pow(one,two);
    }


//    public static void main(String[] args) {
//        BigDecimal a=new BigDecimal("50000");
//        BigDecimal b=null;
//        BigDecimal c=new BigDecimal("10000");
//        System.out.println(BigDecimalCalcUtil.divMult(2,a,b,c));
//        System.out.println(BigDecimalCalcUtil.div(a,b,2));
//    }
}
