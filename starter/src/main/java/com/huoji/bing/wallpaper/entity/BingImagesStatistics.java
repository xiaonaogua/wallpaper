package com.huoji.bing.wallpaper.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BingImagesStatistics {
    private Integer id;

    /**
     * 图片ID
     */
    private Integer imageId;

    /**
     * 查看数
     */
    private Long viewCount;

    /**
     * 下载数
     */
    private Long downloadCount;

    /**
     * 评论数
     */
    private Long commentCount;

    /**
     * 收藏数
     */
    private Long collectCount;

    /**
     * 点赞数
     */
    private Long likeCount;
}