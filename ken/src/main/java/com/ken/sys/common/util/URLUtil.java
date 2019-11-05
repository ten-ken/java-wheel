package util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * <ul>
 * <li>Description: URLUtil 请求外部接口 </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author swc
 * @date 2019/11/5 0005 上午 9:14
 */
public class URLUtil {

    public  static Map<String,String> defaultconfig;
    public  static String BOUNDARY = "----WebKitFormBoundaryxCv8G97t2l0mzR3F";

    static {
        defaultconfig =new HashMap<String,String>();
        defaultconfig.put("connection", "Keep-Alive");
        defaultconfig.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
        defaultconfig.put("Charsert", "UTF-8");
        defaultconfig.put("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        defaultconfig.put("Accept-Language", "zh-CN,zh;q=0.9");
    }

    /**
     * 功能描述: post 请求   将文件写入流 并传入
     * @param url   访问url
     * @param path  文件路径
     * @param out   空参
     * @param in    空参
     * @return: java.lang.String
     * @author: swc
     * @date: 2019/11/5 0005 上午 9:22
     */
    public static String connectByStream(String url,String path,DataOutputStream out,BufferedReader in)throws Exception {
         return connectAndWriteFile(url,path,out,in,null);
    }

    /**
     * 功能描述: post 请求   将文件写入流 并传入
     * @param url   访问url
     * @param path  文件路径
     * @param out   空参 前面已定义
     * @param in    空参 前面已定义
     * @param headConfig    请求头配置（使用了就不会用默认请求头）
     * @return: java.lang.String
     * @author: swc
     * @date: 2019/11/5 0005 上午 9:22
     */
    public static String connectByStream(String url,String path,DataOutputStream out,BufferedReader in,Map<String,String> headConfig)throws Exception {
        return connectAndWriteFile(url,path,out,in,headConfig);
    }

    /**
     * 功能描述: post 请求   将文件写入流 作为form对象传入
     * @param url   访问url
     * @param path  文件路径
     * @param out   空参 前面已定义
     * @param in    空参 前面已定义
     * @param headConfig    请求头配置
     * @return: java.lang.String
     * @author: swc
     * @date: 2019/11/5 0005 上午 9:22
     */
    private static String connectAndWriteFile(String url,String path,DataOutputStream out,BufferedReader in,Map<String,String> headConfig)throws Exception {
        if(headConfig==null || headConfig.isEmpty()){
            headConfig =defaultconfig;
        }

        String result = "";
        URL realUrl = new URL(url);
        //打开和URL之间的连接
        HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
        //发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        for(String key:headConfig.keySet()){
            conn.setRequestProperty(key, headConfig.get(key));
        }
        conn.connect();
        out = new DataOutputStream(conn.getOutputStream());
        byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
        //添加参数
        StringBuffer sb1 = new StringBuffer();
        sb1.append("--");
        sb1.append(BOUNDARY);
        sb1.append("\r\n");
//        sb1.append("Content-Disposition: form-data;name=\"\"");
//        sb1.append("\r\n");
//        sb1.append("\r\n");
//        sb1.append("file");
//        sb1.append("\r\n");
        out.write(sb1.toString().getBytes());

        //添加参数file
        File file = new File(path);
        StringBuffer sb = new StringBuffer();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"");
        sb.append("\r\n");
        sb.append("Content-Type: application/octet-stream");
        sb.append("\r\n");
        sb.append("\r\n");

        out.write(sb.toString().getBytes());

        DataInputStream in1 = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in1.read(bufferOut)) != -1) {
            out.write(bufferOut,0,bytes);
        }
        out.write("\r\n".getBytes());
        in1.close();
        out.write(end_data);

        //flush输出流的缓冲
        out.flush();
        //定义BufferedReader输入流来读取URL的响应
//        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        String line;
//        while ((line = in.readLine()) != null) {
//            result += line;
//        }
        return responseBodyText(conn,in);
    }

    /**
     * 功能描述: 一般普通的接口请求  返回HttpURLConnection（连接对象）
     * @param url 请求url
     * @param sendType 请求类型 get  post。。。
     * @param query  请求参数  如 "name=lisa&age=19"
     * @param headConfig  请求头设置  木有则不配置
     * @return: void
     * @author: swc
     * @date: 2019/11/5 0005 上午 10:00
    */ 
    public static HttpURLConnection commonConnect(String url,String sendType,String query,Map<String,String> headConfig) throws Exception{
        URL restURL = new URL(url);
        /*
         * 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类 的子类HttpURLConnection
         */
        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        //请求方式
        conn.setRequestMethod(sendType);
        //设置是否从httpUrlConnection读入，默认情况下是true; httpUrlConnection.setDoInput(true);
        conn.setDoOutput(true);
        //allowUserInteraction 如果为 true，则在允许用户交互（例如弹出一个验证对话框）的上下文中对此 URL 进行检查。
        conn.setAllowUserInteraction(false);
        if(headConfig!=null){
            for(String key:headConfig.keySet()){
                conn.setRequestProperty(key, headConfig.get(key));
            }
        }
        PrintStream ps = new PrintStream(conn.getOutputStream());
        ps.print(query);
        ps.close();
        return conn;
    }

    /**
     * 功能描述: 获取响应的数据  针对一般的请求  写入流的单独去写
     * @param conn HttpURLConnection(连接)
     * @param in  空参 前面已定义
     * @return: java.lang.String
     * @author: swc
     * @date: 2019/11/5 0005 上午 10:14
    */
    public static String responseBodyText(HttpURLConnection conn,BufferedReader in)throws IOException{
        String result ="";
        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        return result;
    }

}
