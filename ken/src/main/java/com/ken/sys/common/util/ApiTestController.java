///******************************************************************************
//package api;
//import com.jiangqiao.core.controller.BaseController;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//
///**
// * <ul>
// * <li>Title: 匠桥基础开发框架-LoginController.java</li>
// * <li>Description: 登录和个人信息部分Controller  </li>
// * <li>Copyright: Copyright (c) 2018</li>
// * <li>Company: http://www.ahjianqiao.com/</li>
// * </ul>
// *
// * @author swc
// * @version 匠桥基础开发框架1.0
// * @date 2019年1月3日 下午11:18:18
// */
//@RequestMapping("/api")
//@RestController
//public class ApiTestController extends BaseController {
//
//    private Logger logger = LoggerFactory.getLogger(ApiTestController.class);
//
//	//上传接口
//	@RequestMapping("/receiveapi")
//	@ResponseBody
//	public String FilePicture(@RequestParam(value="file",required=false)
//									  MultipartFile file, HttpServletRequest request) {
//		if (file!=null) {
//			String fileName=file.getOriginalFilename();
//			try {
//				BufferedInputStream bis=new BufferedInputStream(file.getInputStream()) ;//创建输入的管道
//				byte[] buf = new byte[1024*20];//创建一个小数组
//				int lenght = 0;
//				BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(  //创建输出管道
//						"D:\\" + new String(file.getOriginalFilename().getBytes("GBK"),"ISO8859_1"))); //图片会拷贝到这里
//				while ((lenght=bis.read(buf)) != -1) {
//					bos.write(buf, 0, lenght);
//				}
//				bos.close();
//				bis.close();
//			} catch (Exception e) {
//                logger.error(e.getMessage());
//				e.printStackTrace();
//			}
//			System.out.println("success");
//		}
//		String result="{\"message\":{\"header\":{\"auth\":{\"callerid\":null,\"license\":null,\"token\":null,\"ipadd\n" +
//				"r\":null,\"macaddr\":null},\"response\":{\"code\":1,\"text\":null,\"detail\":null}},\"body\":\n" +
//				"{\"bussresponse\":{\"code\":\"1\",\"text\":\"璇锋眰鎴愬姛\"},\"queryresponse\":null,\"dataset\n" +
//				"\":[{\"name\":\"uploadFile\",\"row\":[{\"id\":89658,\"filePath\":\"M00/00/F7/wKgBaV2_9giAI8i\n" +
//				"cAAAAFgifNvs5900958\",\"fileMd5\":\"AF1EFD8017E4D6A3EEA11E70B9DA764C\",\"fileTime\":\"20\n" +
//				"19-11-04T09:57:53.000+0000\",\"fileSize\":\"22B\",\"fileLength\":22,\"formatName\":\"txt\",\n" +
//				"\"key\":\"20191104175747666116516299200000\",\"fileGroup\":\"group2\",\"fileName\":\"123\"}]\n" +
//				"}]}}}";
//		return result;
//	}
//
//
//
//	/**
//	 * 功能描述:下载接口
//	 * @param request
//	 * @param response
//	 * @return: void
//	 * @author: swc
//	 * @date: 2019/11/4 0004 下午 15:39
//	*/
//    @RequestMapping("/downloadapi")
////    @ResponseBody
//    public void downloadapi(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            File file = new File("F:\\FileZilla_v3.40.0.zip");
//            if(!file.exists() ||  !file.isFile()){
//                System.out.println("文件不存在。。F:\\FileZilla_v3.40.0.zip" );
////                return "error";
//            }
//            //response.setCharacterEncoding("utf-8");
//            //response.setContentType("multipart/form-data");
//            //response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
//            InputStream inputStream = new FileInputStream(file);
//            OutputStream os = response.getOutputStream();
//            byte[] b = new byte[1024];
//            int length;
//            while ((length = inputStream.read(b)) > 0) {
//                os.write(b, 0, length);
//            }
//            inputStream.close();
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//            ex.printStackTrace();
//        }
//    }
//
//
//
//}
