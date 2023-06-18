package com.huoji.bing.wallpaper.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.huoji.bing.wallpaper.entity.BingImagesComment;
import com.huoji.bing.wallpaper.mapper.BingImagesCommentMapper;
import com.huoji.bing.wallpaper.service.BingImagesCommentService;
@Service
public class BingImagesCommentServiceImpl implements BingImagesCommentService{

    @Resource
    private BingImagesCommentMapper bingImagesCommentMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return bingImagesCommentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(BingImagesComment record) {
        return bingImagesCommentMapper.insert(record);
    }

    @Override
    public int insertSelective(BingImagesComment record) {
        return bingImagesCommentMapper.insertSelective(record);
    }

    @Override
    public BingImagesComment selectByPrimaryKey(Long id) {
        return bingImagesCommentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(BingImagesComment record) {
        return bingImagesCommentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BingImagesComment record) {
        return bingImagesCommentMapper.updateByPrimaryKey(record);
    }

}
