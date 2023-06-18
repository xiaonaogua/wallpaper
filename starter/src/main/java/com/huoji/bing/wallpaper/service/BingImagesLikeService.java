package com.huoji.bing.wallpaper.service;

import com.huoji.bing.wallpaper.entity.BingImagesLike;

import java.util.List;

public interface BingImagesLikeService {


    int deleteByPrimaryKey(Long id);

    int insert(BingImagesLike record);

    int insertSelective(BingImagesLike record);

    BingImagesLike selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BingImagesLike record);

    int updateByPrimaryKey(BingImagesLike record);

    BingImagesLike selectHasLike(BingImagesLike record);

    List<BingImagesLike> selectList(BingImagesLike record);

}
