package com.likai.ghs.service.impl;

import com.likai.ghs.PO.FileInfo;
import com.likai.ghs.PO.SourceRss;
import com.likai.ghs.mapper.FileInfoMapper;
import com.likai.ghs.mapper.SourceRssMapper;
import com.likai.ghs.service.GHSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GHSServiceImpl implements GHSService {

    @Autowired
    private SourceRssMapper sourceRssMapper;

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public List<SourceRss> selectAllSourceRss() {
        return sourceRssMapper.selectList(null);
    }

    @Override
    public int insertFileInfo(FileInfo fileInfo){

        return fileInfoMapper.insert(fileInfo);
    }

}


