package com.huoji.bing.wallpaper.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.huoji.bing.wallpaper.entity.BingImagesStatistics;
import com.huoji.bing.wallpaper.mapper.BingImagesStatisticsMapper;
import com.huoji.bing.wallpaper.service.BingImagesStatisticsService;
@Service
public class BingImagesStatisticsServiceImpl implements BingImagesStatisticsService{

    @Resource
    private BingImagesStatisticsMapper bingImagesStatisticsMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return bingImagesStatisticsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(BingImagesStatistics record) {
        return bingImagesStatisticsMapper.insert(record);
    }

    @Override
    public int insertSelective(BingImagesStatistics record) {
        return bingImagesStatisticsMapper.insertSelective(record);
    }

    @Override
    public BingImagesStatistics selectByPrimaryKey(Integer id) {
        return bingImagesStatisticsMapper.selectByPrimaryKey(id);
    }

    @Override
    public BingImagesStatistics selectByImageId(Integer imageId) {
        BingImagesStatistics statistics = bingImagesStatisticsMapper.selectByImageId(imageId);
        if(statistics == null) {
            statistics = BingImagesStatistics.builder()
                    .imageId(imageId)
                    .downloadCount(0l)
                    .likeCount(0l)
                    .commentCount(0l)
                    .collectCount(0l)
                    .viewCount(0l)
                    .build();
            this.insertSelective(statistics);
        }
        return statistics;
    }


    @Override
    public int updateByPrimaryKeySelective(BingImagesStatistics record) {
        return bingImagesStatisticsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BingImagesStatistics record) {
        return bingImagesStatisticsMapper.updateByPrimaryKey(record);
    }

    /**
     * 添加查看数
     * @param imageId 图片ID
     * @param count +1 -1
     * @return int
     */
    @Override
    public int addViewCount(Integer imageId, int count) {
        int c = 0;
        BingImagesStatistics statistics = this.selectByImageId(imageId);
        if(statistics == null) {
            statistics = BingImagesStatistics.builder().imageId(imageId).viewCount(1L).build();
            c = this.insertSelective(statistics);
        } else {
            statistics.setViewCount(statistics.getViewCount() + count);
            c = this.updateByPrimaryKeySelective(statistics);
        }
        return c;
    }

    /**
     * 添加点赞
     * @param imageId 图片ID
     * @param count +1 -1
     * @return int
     */
    @Override
    public int addLikeCount(Integer imageId, int count) {
        int c = 0;
        BingImagesStatistics statistics = this.selectByImageId(imageId);
        if(statistics == null) {
            statistics = BingImagesStatistics.builder().imageId(imageId).likeCount(1L).build();
            c = this.insertSelective(statistics);
        } else {
            statistics.setLikeCount(statistics.getLikeCount() + count);
            c = this.updateByPrimaryKeySelective(statistics);
        }
        return c;
    }

    /**
     * 添加下载数
     * @param imageId 图片ID
     * @param count +1 -1
     * @return int
     */
    @Override
    public int addDownloadCount(Integer imageId, int count){
        int c = 0;
        BingImagesStatistics statistics = this.selectByImageId(imageId);
        if(statistics == null) {
            statistics = BingImagesStatistics.builder().imageId(imageId).downloadCount(1L).build();
            c = this.insertSelective(statistics);
        } else {
            statistics.setDownloadCount(statistics.getDownloadCount() + count);
            c = this.updateByPrimaryKeySelective(statistics);
        }
        return c;
    }

    /**
     * 添加评论
     * @param imageId 图片ID
     * @param count +1 -1
     * @return int
     */
    @Override
    public int addCommentCount(Integer imageId, int count){
        int c = 0;
        BingImagesStatistics statistics = this.selectByImageId(imageId);
        if(statistics == null) {
            statistics = BingImagesStatistics.builder().imageId(imageId).commentCount(1L).build();
            c = this.insertSelective(statistics);
        } else {
            statistics.setCommentCount(statistics.getCommentCount() + count);
            c = this.updateByPrimaryKeySelective(statistics);
        }
        return c;
    }

    /**
     * 添加收藏数量
     * @param imageId 图片ID
     * @param count +1 -1
     * @return int
     */
    @Override
    public int addCollectCount(Integer imageId, int count) {
        int c = 0;
        BingImagesStatistics statistics = this.selectByImageId(imageId);
        if(statistics == null) {
            statistics = BingImagesStatistics.builder().imageId(imageId).collectCount(1L).build();
            c = this.insertSelective(statistics);
        } else {
            statistics.setCollectCount(statistics.getCollectCount() + count);
            c = this.updateByPrimaryKeySelective(statistics);
        }
        return c;
    }

}
