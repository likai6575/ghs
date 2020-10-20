package com.likai.ghs.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ghs_fileInfo")
public class FileInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String fileName;
    private String filePath;
    private String author;
    private String pubDate;
    private Integer clickNum;
    private Integer favorNum;
    private String sourceLink;
    private String title;
    private Integer sourceRss;
    private String src;
    private Integer status;
    private String msg;

}
