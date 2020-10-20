package com.likai.ghs.PO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ghs_sourceRss")
public class SourceRss {

    private Integer id;
    private String title;
    private String url;
    private String addDate;
    private String saveDir;

}
