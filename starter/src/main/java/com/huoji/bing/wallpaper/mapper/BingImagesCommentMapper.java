package com.huoji.bing.wallpaper.mapper;

import com.huoji.bing.wallpaper.entity.BingImagesComment;

public interface BingImagesCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BingImagesComment record);

    int insertSelective(BingImagesComment record);

    BingImagesComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BingImagesComment record);

    int updateByPrimaryKey(BingImagesComment record);
}