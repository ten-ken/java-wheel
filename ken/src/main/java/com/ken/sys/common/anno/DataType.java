/******************************************************************************
 *
 * 作者（author）：ken
 * 微信（weChat）：mlchao1992
 * 个人博客（website）：
 *
 ******************************************************************************
 * 注意：尊重原创
 *****************************************************************************/

package com.ken.sys.common.anno;

/**
 *
 * @author ken
 * @version 数据类型
 * @date 2019-02-01
 */
public  class DataType {
	public final static String STRING ="java.lang.String";
	public final static String INTEGER ="java.lang.Integer";
	public final static String LONG ="java.lang.Long";
	public final static String DOUBLE ="java.lang.Double";
	public final static String FLOAT ="java.lang.Float";
	public final static String DATE ="java.util.Date";
	public final static String ENUM ="enum";
	public final static String BIGDECIMAL ="java.math.BigDecimal";

	public static String getSTRING() {
		return STRING;
	}

	public static String getINTEGER() {
		return INTEGER;
	}

	public static String getLONG() {
		return LONG;
	}

	public static String getDOUBLE() {
		return DOUBLE;
	}

	public static String getFLOAT() {
		return FLOAT;
	}

	public static String getDATE() {
		return DATE;
	}

	public static String getENUM() {
		return ENUM;
	}

	public static String getBIGDECIMAL() {
		return BIGDECIMAL;
	}
}
