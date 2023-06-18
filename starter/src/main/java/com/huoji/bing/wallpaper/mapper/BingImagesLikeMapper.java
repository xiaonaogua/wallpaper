package com.huoji.bing.wallpaper.mapper;

import com.huoji.bing.wallpaper.entity.BingImagesLike;

import java.util.List;

public interface BingImagesLikeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BingImagesLike record);

    int insertSelective(BingImagesLike record);

    BingImagesLike selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BingImagesLike record);

    int updateByPrimaryKey(BingImagesLike record);

    List<BingImagesLike> selectList(BingImagesLike record);
}