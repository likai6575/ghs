package com.likai.ghs.test;

import com.likai.ghs.mapper.SourceRssMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {

    private final static String dir = "C:\\test";
    @Autowired
    private static SourceRssMapper sourceRssMapper;
    public static void main(String[] args) throws IOException, ParseException {

        Document document = Jsoup.connect("https://rakuen.thec.me/PixivRss/daily_r18-30").get();
        //获取lastBuildDate和缓存中的对比 以检测是否为更新后的信息
        String lastBuildDate = document.getElementsByTag("lastBuildDate").get(0).text();

        //获取item并遍历
        Elements itemElements = document.getElementsByTag("item");
        for (int i = 0; i < itemElements.size(); i++) {

            String title=itemElements.get(i).getElementsByTag("title").get(0).text().replace("<![CDATA[", "").replace("]]>", "").trim();

            Element descriptionElement=Jsoup.parse(itemElements.get(i).getElementsByTag("description").get(0).text().replace("<![CDATA[", "").replace("]]>", ""));
            String pTag = descriptionElement.getElementsByTag("p").get(1).text();
            String author=pTag.substring(pTag.indexOf("画师") + 3, pTag.indexOf(" - 上传于")).trim();
            //文件名fileName  格式 title - author
            String fileName=author+" - "+title;

            //下载地址url
            String url =descriptionElement.getElementsByTag("img").attr("src");

            //发布时间pubDate
            String pubDate=itemElements.get(i).getElementsByTag("pubDate").get(0).text().trim();
            //点击数
            int clickNum=Integer.parseInt(pTag.substring(pTag.indexOf("阅览数") + 4, pTag.indexOf(" - 收藏数")));
            //收藏数
            int favorNum=Integer.parseInt(pTag.substring(pTag.indexOf("收藏数") + 4));




//            Element cdataElement = Jsoup.parse(descriptionElements.get(i).toString().replace("<![CDATA[", "").replace("]]>", ""));
//            String url = cdataElement.getElementsByTag("img").attr("src");
//            //按照rss提供的pixiv图片代理的标签规则来提取文件名和文件信息
//            String pTag = cdataElement.getElementsByTag("p").get(1).toString();
//            String author = pTag.substring(pTag.indexOf("画师") + 3, pTag.indexOf(" - 上传于"));
//            Date d = sourceSDF.parse(pTag.substring(pTag.indexOf("上传于") + 4, pTag.indexOf(" - 阅览数")));
//            String date = targetSDF.format(d);
//            String fileName = url.substring(url.lastIndexOf("/") + 1);
//            System.out.println(date + " " +author + " " + fileName);

//            Connection.Response response=Jsoup.connect(url).ignoreContentType(true).execute();
//            byte[] img=response.bodyAsBytes();
//            savaImage(img,dir,i+".jpg");

        }
    }

    //现成代码 cv
    public static void savaImage(byte[] img,String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        File dir = new File(filePath);
        try {
            //判断文件目录是否存在
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdir();
            }
            file = new File(filePath + "\\" + fileName);

            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}