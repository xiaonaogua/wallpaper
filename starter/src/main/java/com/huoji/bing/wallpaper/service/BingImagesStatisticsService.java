package com.huoji.bing.wallpaper.service;

import com.huoji.bing.wallpaper.entity.BingImagesStatistics;

public interface BingImagesStatisticsService {


    int deleteByPrimaryKey(Integer id);

    int insert(BingImagesStatistics record);

    int insertSelective(BingImagesStatistics record);

    BingImagesStatistics selectByPrimaryKey(Integer id);

    BingImagesStatistics selectByImageId(Integer imageId);

    int updateByPrimaryKeySelective(BingImagesStatistics record);

    int updateByPrimaryKey(BingImagesStatistics record);

    /**
     * 添加查看数
     * @param imageId 图片ID
     * @param count +1 -1
     * @return int
     */
    int addViewCount(Integer imageId, int count);

    /**
     * 添加点赞
     * @param imageId 图片ID
     * @param count +1 -1
     * @return int
     */
    int addLikeCount(Integer imageId, int count);

    /**
     * 添加下载数
     * @param imageId 图片ID
     * @param count +1 -1
     * @return int
     */
    int addDownloadCount(Integer imageId, int count);

    /**
     * 添加评论
     * @param imageId 图片ID
     * @param count +1 -1
     * @return int
     */
    int addCommentCount(Integer imageId, int count);

    /**
     * 添加收藏数量
     * @param imageId 图片ID
     * @param count +1 -1
     * @return int
     */
    int addCollectCount(Integer imageId, int count);

}
