/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/
package com.ken.sys.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.HttpURLConnection;

/**
 * <ul>
 * <li>Title: 匠桥ERP系统-FileManagerDownUtil</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * <li>Company: http://www.jiangqiaotech.com/</li>
 * </ul>
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2019/10/31 0031 下午 17:40
 */
public class FileTransferUtil {

    private static Logger logger = LoggerFactory.getLogger(FileTransferUtil.class);
    /**
     * 调用流程上传文件接口上传文件(有用)
     * @param url
     * @param path
     * @return
     */
    public static String sendPostUplodFile(String url, String path) {
        DataOutputStream out = null;
        BufferedReader in = null;
        String result = "";
        try {
            result =URLUtil.connectByStream(url,path,out,in);
            logger.debug(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 功能描述: get 请求 进行访问 文件 进行跨域下载
     * @param url
     * @param query
     * @return: java.lang.String
     * @author: swc
     * @date: 2019/11/1 0001 下午 14:24
     */
    public static void  getDownLoad(String url, String query, String sendType,String localPath) throws Exception {
        sendType = EmptyUtils.isNullOrEmpty(sendType)?"POST":sendType.toUpperCase();
        HttpURLConnection conn = URLUtil.commonConnect(url,sendType,query,null);
        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
//            response.setContentType("text/html;charset=UTF-8");
            bis = new BufferedInputStream(conn.getInputStream());
//            bos = new BufferedOutputStream(response.getOutputStream());
            File file =new File(localPath);
            if(!file.getParentFile().exists()){
                file.mkdirs();
            }
            bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }

        }
        bReader.close();
    }

//    /**
//     * 功能描述: 將文件转为字节数组
//     * @param file
//     * @return: byte[]
//     * @author: swc
//     * @date: 2019/11/1 0001 下午 17:42
//     */
//    public static byte[] getFileToByte(File file) {
//        byte[] by = new byte[(int) file.length()];
//        try {
//            InputStream is = new FileInputStream(file);
//            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
//            byte[] bb = new byte[2048];
//            int ch;
//            ch = is.read(bb);
//            while (ch != -1) {
//                bytestream.write(bb, 0, ch);
//                ch = is.read(bb);
//            }
//            by = bytestream.toByteArray();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return by;
//    }


}
