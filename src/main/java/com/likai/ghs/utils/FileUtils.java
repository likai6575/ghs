package com.likai.ghs.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtils {
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    public static void downloadPic(String strUrl,String savePath,String fileName) throws Exception {
        HttpURLConnection conn = null;
        URL url = new URL(strUrl);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-agent", userAgent);
        conn.setUseCaches(false);
        conn.setConnectTimeout(DEF_CONN_TIMEOUT);
        conn.setReadTimeout(DEF_READ_TIMEOUT);
        conn.setInstanceFollowRedirects(false);
        conn.connect();
        InputStream is = conn.getInputStream();//连接得到输入流内容
        File file=new File(savePath);
        if(!file.exists()) {//保存文件夹不存在则建立
            file.mkdirs();
        }
        FileOutputStream fos=new FileOutputStream(new File(savePath,fileName));//获取网址中的图片名
        int intRead = 0;
        byte[] buf=new byte[1024*2];//生成2K 大小的容器用于接收图片字节流
        while ((intRead = is.read(buf)) != -1) {
            fos.write(buf,0,intRead);//文件输出流到指定路径文件
            fos.flush();
        }
        fos.close();//关闭输出流
        if (conn != null) {
            conn.disconnect();//关闭连接
        }
    }
}
