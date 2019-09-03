/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/
package com.ken.sys.common.util;

import com.ken.sys.common.message.Message;
import com.ken.sys.common.message.MessageUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * <ul>
 * <li>Title: 匠桥基础开发框架-FileOperateUtil.java</li>
 * <li>Description: 文件上传下载工具 </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.ahjianqiao.com/</li>
 * </ul>
 *
 * @author shenggs
 * @version 匠桥基础开发框架1.0
 * @date 2018年3月26日 上午9:05:31
 */
public class FileOperateUtil {

	private static final String REALNAME = "realName";
	private static final String STORENAME = "storeName";
	private static final String SIZE = "size";
	private static final String SUFFIX = "suffix";
	private static final String CONTENTTYPE = "contentType";
	private static final String CREATETIME = "createTime";
	private static final String UPLOADDIR = "uploadDir/";
	
	public final static int UPLOAD_FILE_MAX_SIZE = 10;//MB

	public static final String CONTENT_TYPE_OCTET = "application/octet-stream; charset=UTF-8";

	/**
	 * 将上传的文件进行重命名
	 * 
	 * @author geloin
	 * @date 2012-3-29 下午3:39:53
	 * @param name
	 * @return
	 */
	public static String rename(String name) {

		Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date()));
		
		//modify by jjxu 2015-09-23 begin
		//String fileName = name.substring(0, name.lastIndexOf(".")) + "_" + now;
		String fileName = "" + now;
		//modify by jjxu 2015-09-23 end

		if (name.indexOf(".") != -1) {
			fileName += name.substring(name.lastIndexOf("."));
		}

		return fileName;
	}
	
	/**
     * 将上传的文件进行重命名
     * 
     * @author geloin
     * @date 2012-3-29 下午3:39:53
     * @param name
     * @return
     */
    public static String getFileName(String type) {

        Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(new Date()));
        
        //modify by jjxu 2015-09-23 begin
        //String fileName = name.substring(0, name.lastIndexOf(".")) + "_" + now;
        String fileName = "" + now + "." + type;
        //modify by jjxu 2015-09-23 end

        return fileName;
    }
    
	/**
	 * 将上传的文件进行重命名
	 * 
	 * @author geloin
	 * @date 2012-3-29 下午3:39:53
	 * @param name
	 * @return
	 */
	public static String renameIcon(String name, String icon) {

		Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date()));
		
		//modify by jjxu 2015-09-23 begin
		//String fileName = name.substring(0, name.lastIndexOf(".")) + "_" + now;
		String fileName = icon + now;
		//modify by jjxu 2015-09-23 end

		if (name.indexOf(".") != -1) {
			fileName += name.substring(name.lastIndexOf("."));
		}

		return fileName;
	}

	/**
	 * 压缩后的文件名
	 * 
	 * @author geloin
	 * @date 2012-3-29 下午6:21:32
	 * @param name
	 * @return
	 */
	private static String zipName(String name) {
		String prefix = "";
		if (name.indexOf(".") != -1) {
			prefix = name.substring(0, name.lastIndexOf("."));
		} else {
			prefix = name;
		}
		return prefix + ".zip";
	}

	/**
	 * 上传文件
	 * 
	 * @author geloin
	 * @date 2012-5-5 下午12:25:47
	 * @param request
	 * @param params
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> upload(HttpServletRequest request,
			String[] params, Map<String, Object[]> values) throws Exception {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = mRequest.getFileMap();

		String uploadDir = request.getSession().getServletContext()
				.getRealPath("/")
				+ FileOperateUtil.UPLOADDIR;
		File file = new File(uploadDir);

		if (!file.exists()) {
			file.mkdir();
		}

		String fileName = null;
		int i = 0;
		for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet()
				.iterator(); it.hasNext(); i++) {

			Map.Entry<String, MultipartFile> entry = it.next();
			MultipartFile mFile = entry.getValue();

			fileName = mFile.getOriginalFilename();

			String storeName = rename(fileName);

			String noZipName = uploadDir + storeName;
			String zipName = zipName(noZipName);

			// 上传成为压缩文件
			ZipOutputStream outputStream = new ZipOutputStream(
					new BufferedOutputStream(new FileOutputStream(zipName)));
			outputStream.putNextEntry(new ZipEntry(fileName));
			// outputStream.setEncoding("GBK");

			FileCopyUtils.copy(mFile.getInputStream(), outputStream);

			Map<String, Object> map = new HashMap<String, Object>();
			// 固定参数值对
			map.put(FileOperateUtil.REALNAME, zipName(fileName));
			map.put(FileOperateUtil.STORENAME, zipName(storeName));
			map.put(FileOperateUtil.SIZE, new File(zipName).length());
			map.put(FileOperateUtil.SUFFIX, "zip");
			map.put(FileOperateUtil.CONTENTTYPE, "application/octet-stream");
			map.put(FileOperateUtil.CREATETIME, new Date());

			// 自定义参数值对
			for (String param : params) {
				map.put(param, values.get(param)[i]);
			}

			result.add(map);
		}
		return result;
	}
	/**
	 * 删除文件
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static Boolean deleteFile(String path, String fileName) {
		boolean flag = false;
		File file = new File(path + fileName);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}
	
	/**
	 * 上传文件
	 * 
	 * @author geloin
	 * @date 2012-5-5 下午12:25:47
	 * @param request
	 * @param uploadFolderPath
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String upload(HttpServletRequest request,
			String uploadFolderPath, MultipartFile file) throws Exception {
		// 获取根路
		String filePath = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String fileName = "";
		try {
			inputStream = file.getInputStream();

			File newFile = new File(uploadFolderPath);
			// 如果文件路径不存在就新建一个
			if (!newFile.exists()) {
				newFile.mkdirs();
			}
			// 获取原始文件名
			String originalFilename = file.getOriginalFilename();

			// 文件更名
			fileName = rename(originalFilename);

			filePath = uploadFolderPath + fileName;

			outputStream = new FileOutputStream(filePath);
			int readBytes = 0;
			byte[] buffer = new byte[10000];
			while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
				outputStream.write(buffer, 0, readBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}

		}

		return fileName;
	}

	public static String uploadByIcon(HttpServletRequest request,
			String uploadFolderPath, MultipartFile file, String icon) throws Exception {
		// 获取根路
		String filePath = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String fileName = "";
		try {
			inputStream = file.getInputStream();

			File newFile = new File(uploadFolderPath);
			// 如果文件路径不存在就新建一个
			if (!newFile.exists()) {
				newFile.mkdirs();
			}
			// 获取原始文件名
			String originalFilename = file.getOriginalFilename();

			// 文件更名
			fileName = renameIcon(originalFilename,icon);

			filePath = uploadFolderPath + fileName;

			outputStream = new FileOutputStream(filePath);
			int readBytes = 0;
			byte[] buffer = new byte[10000];
			while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
				outputStream.write(buffer, 0, readBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}

		}

		return fileName;
	}
	
	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param storeName
	 * @param contentType
	 * @param realName
	 * @throws Exception
	 */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String storeName, String contentType,
			String downLoadPath) throws Exception {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			request.setCharacterEncoding("UTF-8");

			long fileLength = new File(downLoadPath).length();

			response.setContentType(contentType);
			
			 if (request.getHeader("user-agent").toLowerCase().contains("msie") ||
			         request.getHeader("user-agent").toLowerCase().contains("trident") ||
			         request.getHeader("user-agent").toLowerCase().contains("edge") || 
	                    request.getHeader("user-agent").toLowerCase().endsWith("like gecko")) {
			     // IE
			     storeName = URLEncoder.encode(storeName, "UTF-8");
	         } else {
	             // 非IE
	             storeName = new String(storeName.getBytes("utf-8"), "ISO8859-1");
	         }

			response.setHeader("Content-disposition", "attachment; filename=\""
					+ storeName + "\"");
			response.setHeader("Content-Length", String.valueOf(fileLength));

			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}

		}
	}
	
	public static void show(HttpServletRequest request,HttpServletResponse response, String downLoadPath,String storeName) throws IOException {
		FileInputStream inputStream = null;
		try {
			File file = new File(downLoadPath, storeName);
			if (file.exists()) {
				long fileLength = file.length();
				inputStream = new FileInputStream(file);
				response.setHeader("Content-Length", String.valueOf(fileLength));
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[2048];
				int count = 0;
				while((count = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0 ,count);
					outputStream.flush();
				}
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
	
	/**
	 * 检查文件大小 MB
	 * @param fileSize
	 * @return true 是 false 否
	 */
	public static boolean isGreaterMaxFileSize(long fileSize){
		if(fileSize/1024/1024 > FileOperateUtil.UPLOAD_FILE_MAX_SIZE){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 检查文件大小 KB
	 * @param fileSize
	 * @return true 是 false 否
	 */
	public static boolean checkUploadFileSizeByType(long fileSize,String strFileSize){
		double maxFileSize = 0;
		if(strFileSize != null && !strFileSize.equals("") )
		{ 
			maxFileSize = Double.parseDouble(strFileSize);
		}else{
			return false;
		}
		
		if(fileSize/1024 > maxFileSize){
			return true;
		}
		
		return false;
	}
	
	public static boolean checkUploadFileSizeByType(long fileSize,Integer maxFileSize){
		if(maxFileSize == null){
			return false;
		}
		
		if(fileSize/1024 > maxFileSize){
			return true;
		}
		
		return false;
	}
	
	public static Message checkFileIfExist(String downLoadPath, String fileName){
		File file = new File(downLoadPath, fileName);
		if (file.exists()) {
			return MessageUtil.createMessage(true, "文件存在。");
		}else{
			return MessageUtil.createMessage(false, "文件不存在。");
		}		
	}
	
	public static boolean checkFileIfExist2(String downLoadPath,String fileName){
		File file = new File(downLoadPath, fileName);
		if (file.exists()) {
			return true;
		}else{
			return false;
		}		
	}
	
	public static void writeJsonToFile(String filePath,String fileName, String jsonStr) throws IOException {
		File newFile = new File(filePath);
		// 如果文件路径不存在就新建一个
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		
		String encoding = "UTF-8";//设置文件的编码！！
		OutputStreamWriter outstream = new OutputStreamWriter(new FileOutputStream(filePath + fileName), encoding);
		   
		PrintWriter writer = new PrintWriter(outstream);  
		//writer.format(encoding,null);
		writer.write(jsonStr);  
		writer.close();  
	}
	
	public static void copyFile(String fromFilePath,String fileName,String toFilePath) throws IOException{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		File newFile = new File(toFilePath);
		// 如果文件路径不存在就新建一个
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		
		try {
			bis = new BufferedInputStream(new FileInputStream(fromFilePath + fileName));
			bos = new BufferedOutputStream(new FileOutputStream(toFilePath + fileName));
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}

		}
	}
	
	
	/**
	 * 删除目录
	 * @param dirPath
	 */
    public static void deleteDirectory(String dirPath) {  
        File fileDir = new File(dirPath);  
        
        deleteAllFilesOfDir(fileDir);  
    } 
    
    private static void deleteAllFilesOfDir(File path) {  
        if (!path.exists())  
            return;  
        if (path.isFile()) {  
            path.delete();  
            return;  
        }  
        File[] files = path.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            deleteAllFilesOfDir(files[i]);  
        }  
        path.delete();  
    }  
    
    /**
     * 文件大小 单位 MB
     * @param filePath
     * @return
     * @throws IOException 
     */
    public static double getFileSize(String filePath) throws IOException{
    	FileInputStream fis= null;  

    	File f= new File(filePath);  
        fis= new FileInputStream(f);  
        int fileSize = fis.available();
        
        fis.close(); 
        
        return (double)fileSize/1024/1024;
    }
	/**
	 * 删除指定的Sheet
	 * @param targetFile  目标文件
	 * @param sheetName   Sheet名称
	 */
	public static void deleteSheet(String targetFile,String sheetName) {
		try {
			FileInputStream fis = new FileInputStream(targetFile);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			fileWrite(targetFile, wb);
			//删除Sheet
			wb.removeSheetAt(wb.getSheetIndex(sheetName));
			fileWrite(targetFile, wb);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 写隐藏/删除后的Excel文件
	 * @param targetFile  目标文件
	 * @param wb          Excel对象
	 * @throws Exception
	 */
	public static void fileWrite(String targetFile,XSSFWorkbook wb) throws Exception{
		FileOutputStream fileOut = new FileOutputStream(targetFile);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
}
