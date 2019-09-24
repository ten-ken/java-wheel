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

import com.ken.sys.common.entity.SysCode;

import java.io.*;
import java.util.List;

/**
 * <ul>
 * <li>Title: ken-ObjectUtil</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author ken
 * @version V1.0
 * @date 2019/9/20 20:41
 */
public class ObjectUtil {
    /**
     * 深度拷贝
     * @param src
     * @param <>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut =null;
        ObjectOutputStream out =null;
        ByteArrayInputStream byteIn =null;
        ObjectInputStream in = getObjectInputStream(src,byteOut,out,byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        if(in!=null){
            in.close();
        }
        if(byteIn!=null){
            byteIn.close();
        }
        if(out!=null){
            out.close();
        }
        if(byteOut!=null){
            byteOut.close();
        }
        return dest;
    }

    public static ObjectInputStream getObjectInputStream(Object obj, ByteArrayOutputStream byteOut,ObjectOutputStream out,ByteArrayInputStream byteIn) throws IOException {
        byteOut = new ByteArrayOutputStream();
        out = new ObjectOutputStream(byteOut);
        out.writeObject(obj);
        byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        return new ObjectInputStream(byteIn);
    }

    /**
     * 深度拷贝
     * @param src
     * @param <>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> T deepCopy(T src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut =null;
        ObjectOutputStream out =null;
        ByteArrayInputStream byteIn =null;
        ObjectInputStream in = getObjectInputStream(src,byteOut,out,byteIn);
        @SuppressWarnings("unchecked")
        T dest = (T) in.readObject();
        if(in!=null){
            in.close();
        }
        if(byteIn!=null){
            byteIn.close();
        }
        if(out!=null){
            out.close();
        }
        if(byteOut!=null){
            byteOut.close();
        }
        return dest;
    }

    public static void main(String[] args) throws Exception{
        SysCode sysCode =new SysCode();
        sysCode.setType("22");
        SysCode sysCode2 =ObjectUtil.deepCopy(sysCode);
        sysCode2.setType("33");
        System.out.println(sysCode.getType());
        System.out.println(sysCode2.getType());
    }
}
