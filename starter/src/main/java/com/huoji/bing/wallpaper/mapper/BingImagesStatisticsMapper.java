package com.huoji.bing.wallpaper.mapper;

import com.huoji.bing.wallpaper.entity.BingImagesStatistics;

public interface BingImagesStatisticsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BingImagesStatistics record);

    int insertSelective(BingImagesStatistics record);

    BingImagesStatistics selectByPrimaryKey(Integer id);

    BingImagesStatistics selectByImageId(Integer id);

    int updateByPrimaryKeySelective(BingImagesStatistics record);

    int updateByPrimaryKey(BingImagesStatistics record);
}