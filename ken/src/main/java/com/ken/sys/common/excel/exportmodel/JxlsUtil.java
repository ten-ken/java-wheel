package com.ken.sys.common.excel.exportmodel;

import com.ken.sys.common.util.DateUtil;
import com.ken.sys.common.util.FileOperateUtil;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <ul>
 * <li>Title: JxlsUtil.java</li>
 * <li>Description: Yplt Mall System</li>
 * <li>Copyright: Copyright (c) 2017</li>
 * </ul>
 *
 * @author sgs
 * @date 2017年12月27日 下午8:22:44
 */
public class JxlsUtil {
	static {
		//添加自定义指令（可覆盖jxls原指令）
		XlsCommentAreaBuilder.addCommandMapping("image", ImageCommand.class);
		// XlsCommentAreaBuilder.addCommandMapping("each", EachCommand.class);
		XlsCommentAreaBuilder.addCommandMapping("merge", MergeCommand.class);
		XlsCommentAreaBuilder.addCommandMapping("link", LinkCommand.class);
	}

	/**
	 * jxls模版文件目录
	 */
	private final static String TEMPLATE_PATH = "jxlsTemplate";

	/**
	 * 导出excel
	 *
	 * @param is    - excel文件流
	 * @param os    - 生成模版输出流
	 * @param beans - 模版中填充的数据
	 * @throws IOException
	 */
	public static void exportExcel(InputStream is, OutputStream os, Map<String, Object> beans) throws IOException {
		Context context = new Context();
		if (beans != null) {
			for (String key : beans.keySet()) {
				context.putVar(key, beans.get(key));
			}
		}
		JxlsHelper jxlsHelper = JxlsHelper.getInstance();
		Transformer transformer = jxlsHelper.createTransformer(is, os);
		JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
		Map<String, Object> funcs = new HashMap<String, Object>();
		funcs.put("jx", new JxlsUtil());    //添加自定义功能
		evaluator.getJexlEngine().setFunctions(funcs);
		jxlsHelper.processTemplate(context, transformer);
	}

	public static void export(HttpServletRequest request, HttpServletResponse response, String templeteName, String fileName, Map<String, Object> beans) throws IOException {
		fileName = fileName + DateUtil.dateToStr(DateUtil.getSysDate(), DateUtil.FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
		//设置响应
		response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"");
		response.setContentType("application/vnd.ms-excel");
		InputStream in = null;
		OutputStream out = null;
		try {
			Context context = new Context();
			if (beans != null) {
				for (String key : beans.keySet()) {
					context.putVar(key, beans.get(key));
				}
			}
			in = new BufferedInputStream(new FileInputStream(templeteName));
			out = response.getOutputStream();
			JxlsHelper jxlsHelper = JxlsHelper.getInstance();
			Transformer transformer = jxlsHelper.createTransformer(in, out);
			JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
			Map<String, Object> funcs = new HashMap<String, Object>();
			funcs.put("jx", new JxlsUtil());    //添加自定义功能
			evaluator.getJexlEngine().setFunctions(funcs);
			jxlsHelper.processTemplate(context, transformer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 功能描述: 导出多个sheet的表格(非模板)
	 * @param
	 * @author zhuzj
	 * @date 2018/8/24 15:35
	 */
	public static String exportMultiSheet(HttpServletRequest request, HttpServletResponse response, String templeteName, String outPath, String fileName, Map<String, Object> beans) throws IOException {
		fileName = fileName + DateUtil.dateToStr(DateUtil.getSysDate(), DateUtil.FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
		//设置生成的路径
		outPath = outPath + fileName;

		InputStream in = null;
		OutputStream out = null;
		try {
			Context context = PoiTransformer.createInitialContext();
			if (beans != null) {
				for (String key : beans.keySet()) {
					context.putVar(key, beans.get(key));
				}
			}
			in = new BufferedInputStream(new FileInputStream(templeteName));
			out = new FileOutputStream(outPath);
			JxlsHelper jxlsHelper = JxlsHelper.getInstance();
			Transformer transformer = jxlsHelper.createTransformer(in, out);
			JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
			Map<String, Object> funcs = new HashMap<String, Object>();
			funcs.put("jx", new JxlsUtil());    //添加自定义功能
			evaluator.getJexlEngine().setFunctions(funcs);
			jxlsHelper.processTemplate(context, transformer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return fileName;
	}

	/**
	 * 功能描述: 导出多个sheet的表格(模板)
	 * @param
	 * @author zhuzj
	 * @date 2018/8/24 15:35
	 */
	public static String exportMultiTemplateSheet(HttpServletRequest request, HttpServletResponse response, String templeteName, String outPath,String folderPath,String financeReportPath,String fileName, Map<String, Object> beans) throws IOException {
		fileName = fileName + DateUtil.dateToStr(DateUtil.getSysDate(), DateUtil.FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
		//设置生成的路径
		outPath = outPath + fileName;

		InputStream in = null;
		OutputStream out = null;
		try {
			Context context = PoiTransformer.createInitialContext();
			if (beans != null) {
				for (String key : beans.keySet()) {
					context.putVar(key, beans.get(key));
				}
			}
			in = new BufferedInputStream(new FileInputStream(templeteName));
			out = new FileOutputStream(outPath);
			JxlsHelper jxlsHelper = JxlsHelper.getInstance();
			Transformer transformer = jxlsHelper.createTransformer(in, out);
			JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
			Map<String, Object> funcs = new HashMap<String, Object>();
			funcs.put("jx", new JxlsUtil());    //添加自定义功能
			evaluator.getJexlEngine().setFunctions(funcs);
			jxlsHelper.processTemplate(context, transformer);
			//生成成功后删除模板
			FileOperateUtil.deleteSheet(folderPath+financeReportPath+fileName,"Template");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return fileName;
	}


	/**
	 * 功能描述: 导出多个sheet的表格(模板)，直接导出 不需要存储在服务器
	 * @param
	 * @author zhuzj
	 * @date 2018/8/24 15:35
	 */
	public static void exportMultiTemplateSheetNoSave(HttpServletRequest request, HttpServletResponse response, String templeteName,String fileName, Map<String, Object> beans) throws IOException {
		fileName = fileName + DateUtil.dateToStr(DateUtil.getSysDate(), DateUtil.FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
		//设置生成的路径
		//outPath = outPath + fileName;
		response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"");
		response.setContentType("application/vnd.ms-excel");
		InputStream in = null;
		OutputStream out = null;
		try {
			Context context = PoiTransformer.createInitialContext();
			if (beans != null) {
				for (String key : beans.keySet()) {
					context.putVar(key, beans.get(key));
				}
			}
			in = new BufferedInputStream(new FileInputStream(templeteName));
			out = response.getOutputStream();
			JxlsHelper jxlsHelper = JxlsHelper.getInstance();
			Transformer transformer = jxlsHelper.createTransformer(in, out);
			JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
			Map<String, Object> funcs = new HashMap<String, Object>();
			funcs.put("jx", new JxlsUtil());    //添加自定义功能
			evaluator.getJexlEngine().setFunctions(funcs);
            //生成成功后删除模板setDeleteTemplateSheet(true)  模板sheet命名必须为（Template）
			jxlsHelper.setDeleteTemplateSheet(true).processTemplate(context, transformer);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

	}


	/**
	 * 导出excel
	 *
	 * @param xlsPath excel文件
	 * @param outPath 输出文件
	 * @param beans   模版中填充的数据
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void exportExcel(String xlsPath, String outPath, Map<String, Object> beans) throws FileNotFoundException, IOException {
		exportExcel(new FileInputStream(xlsPath), new FileOutputStream(outPath), beans);
	}

	/**
	 * 导出excel
	 *
	 * @param xls   excel文件
	 * @param out   输出文件
	 * @param beans 模版中填充的数据
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void exportExcel(File xls, File out, Map<String, Object> beans) throws FileNotFoundException, IOException {
		exportExcel(new FileInputStream(xls), new FileOutputStream(out), beans);
	}

	/**
	 * 获取jxls模版文件
	 */
	public static File getTemplate(String path) {
		//String templatePath = JxlsUtil.class.getClassLoader().getResource(TEMPLATE_PATH).getPath();
		File template = new File(path);
		if (template.exists()) {
			return template;
		}
		return null;
	}

	// 日期格式化
	public String dateFmt(Date date, String fmt) {
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat dateFmt = new SimpleDateFormat(fmt);
			return dateFmt.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 返回第一个不为空的对象
	public Object defaultIfNull(Object... objs) {
		for (Object o : objs) {
			if (o != null)
				return o;
		}
		return null;
	}

	// if判断
	public Object ifelse(boolean b, Object o1, Object o2) {
		return b ? o1 : o2;
	}
}
