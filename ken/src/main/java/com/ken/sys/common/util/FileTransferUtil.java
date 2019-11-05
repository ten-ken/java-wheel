/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/
package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public static String upLoadUrl = "http://10.138.32.125/file-centralize-manage-service/api/file/uploadFile";
    public static String downLoadUrl ="http://10.138.32.125/file-centralize-manage-service/api/file/downloadFileParamByKey";

    /**
     * 调用流程上传文件接口上传文件(有用)
     * @param url
     * @param path
     * @return
     */
    public static List<ResultPo> sendPostUplodFile(String url, String path) {
        List<ResultPo> row =new ArrayList<ResultPo>();
        DataOutputStream out = null;
        BufferedReader in = null;
        String result = "";
        try {
            result =URLUtil.connectByStream(url,path,out,in);
            System.out.println(result);
            logger.debug(result);
            row = getRowData(result);
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
        return row;
    }

    /**
     * 功能描述: get 请求
     * @param url
     * @param query
     * @return: java.lang.String
     * @author: swc
     * @date: 2019/11/1 0001 下午 14:24
     */
    private static void  getDownLoad(String url, String query, String sendType) throws Exception {
        sendType = EmptyUtil.isNullOrEmpty(sendType)?"POST":sendType.toUpperCase();
        HttpURLConnection conn = URLUtil.commonConnect(url,sendType,query,null);
        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
//            response.setContentType("text/html;charset=UTF-8");
            bis = new BufferedInputStream(conn.getInputStream());
//            bos = new BufferedOutputStream(response.getOutputStream());
            File file =new File("D:\\123.zip");
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

    /**
     * 功能描述: 將文件转为字节数组
     * @param file
     * @return: byte[]
     * @author: swc
     * @date: 2019/11/1 0001 下午 17:42
     */
    public static byte[] getFileToByte(File file) {
        byte[] by = new byte[(int) file.length()];
        try {
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return by;
    }

    public static void main(String[] args) throws Exception {
        String by ="http://192.168.0.101:8080/api";
        String url1 ="http://10.138.32.125/file-centralize-manage-service/api/file/uploadFile";
        String url2 ="http://10.138.32.125/file-centralize-manage-service/api/file/downloadFileParamByKey";

        sendPostUplodFile(by+"/receiveapi","D:\\music\\小黄人打架.mov");
//        System.out.println("上传文件成功"+"D:\\music\\小黄人打架.mov");
//        logger.debug("上传文件成功"+"D:\\123.txt");

        getDownLoad(by+"/downloadapi","query=abc&name=zhangfei","get");
//
//        System.out.println("下载文件成功"+"D:\\soft\\download");
//        logger.debug("下载文件成功"+"D:\\soft\\download");
    }

    /**
     * 功能描述: 针对第三方接口的数据处理
     * @param jsonTxt
     * @return: java.util.List<util.ResultPo>
     * @author: swc
     * @date: 2019/11/4 0004 下午 18:23
     */
    public static List<ResultPo> getRowData(String jsonTxt){
        List<ResultPo> row =new ArrayList<ResultPo>();
        try{
            JSONObject jsonObject =JSON.parseObject(new String(jsonTxt.getBytes(),"UTF-8"));
            if(!EmptyUtil.isNullOrEmpty(jsonObject.get("message"))){
                JSONObject j1 = (JSONObject)jsonObject.get("message");
                if(EmptyUtil.isNullOrEmpty(j1)){
                    return row;
                }
                JSONObject j2 = (JSONObject)j1.get("body");
                if(EmptyUtil.isNullOrEmpty(j2)){
                    return row;
                }
                JSONArray j3 = null;
                if(j2.containsKey("dataset") ){
                    j3 = (JSONArray)j2.get("dataset");
                }
                if(j2.containsKey("dataset\n") ){
                    j3 = (JSONArray)j2.get("dataset\n");
                }
                if(EmptyUtil.isNullOrEmpty(j3)){
                    return row;
                }
                JSONObject j4 = j3.getJSONObject(0);
                if(EmptyUtil.isNullOrEmpty(j4)){
                    return row;
                }
                row = JSONArray.parseArray(JSONArray.toJSONString(j4.get("row")), ResultPo.class);
            }
        }catch (Exception ex){
            logger.debug(ex.getMessage());
        }
        return row;
    }

}
