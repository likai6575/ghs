package com.likai.ghs.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.likai.ghs.PO.FileInfo;
import com.likai.ghs.PO.SourceRss;
import com.likai.ghs.service.GHSService;
import com.likai.ghs.utils.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/ghs")
public class GHSController {

    @Autowired
    GHSService ghsService;


    @PostMapping("/test")
    @ResponseBody
    public R test() throws Exception {

        List<SourceRss> sourceRsses=ghsService.selectAllSourceRss();
        for (SourceRss sourceRss:sourceRsses
             ) {
            Document document=null;
            document = Jsoup.connect(sourceRss.getUrl()).get();
            //获取lastBuildDate和缓存中的对比 以检测是否为更新后的信息
            String lastBuildDate = document.getElementsByTag("lastBuildDate").get(0).text();

            //获取item并遍历
            Elements itemElements = document.getElementsByTag("item");
            for (int i = 0; i < itemElements.size(); i++) {
                //标题title
                String title = itemElements.get(i).getElementsByTag("title").get(0).text().replace("<![CDATA[", "").replace("]]>", "").trim().replace("/","&").replace("\\","&");
                //连接link
                String link = itemElements.get(i).getElementsByTag("link").get(0).text().trim();

                Element descriptionElement = Jsoup.parse(itemElements.get(i).getElementsByTag("description").get(0).text().replace("<![CDATA[", "").replace("]]>", ""));
                String pTag = descriptionElement.getElementsByTag("p").get(1).text();
                //作者author
                String author = pTag.substring(pTag.indexOf("画师") + 3, pTag.indexOf(" - 上传于")).trim().replace("/","&").replace("\\","&");
                //下载地址url
                String url = descriptionElement.getElementsByTag("img").attr("src");
                //文件名fileName  格式 title - author.fileType
                String fileType=url.substring(url.lastIndexOf("."));
                String fileName = author + " - " + title+fileType;
                //发布时间pubDate
                String pubDate = itemElements.get(i).getElementsByTag("pubDate").get(0).text().trim();
                //点击数
                int clickNum = Integer.parseInt(pTag.substring(pTag.indexOf("阅览数") + 4, pTag.indexOf(" - 收藏数")));
                //收藏数
                int favorNum = Integer.parseInt(pTag.substring(pTag.indexOf("收藏数") + 4));
                //下载记录
                FileInfo fileInfo=new FileInfo();
                fileInfo.setTitle(title);
                fileInfo.setAuthor(author);
                fileInfo.setClickNum(clickNum);
                fileInfo.setFavorNum(favorNum);
                fileInfo.setFileName(fileName);
                fileInfo.setFilePath(sourceRss.getSaveDir());
                fileInfo.setPubDate(pubDate);
                fileInfo.setSrc(url);
                fileInfo.setSourceRss(sourceRss.getId());
                fileInfo.setSourceLink(link);


                FileUtils.downloadPic(url, sourceRss.getSaveDir(),fileName);
                ghsService.insertFileInfo(fileInfo);
            }
        }


        return R.ok("下载完成");
    }

}
