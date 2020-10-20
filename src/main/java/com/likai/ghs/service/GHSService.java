package com.likai.ghs.service;

import com.likai.ghs.PO.FileInfo;
import com.likai.ghs.PO.SourceRss;

import java.util.List;

public interface GHSService {

    List<SourceRss> selectAllSourceRss();
    int insertFileInfo(FileInfo fileInfo);
}
