package com.huoji.bing.wallpaper.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BingImages {
    private Integer id;

    private String imagesUrl;

    private String fullstartdate;

    private String startdate;

    private String enddate;

    private String url;

    private String urlbase;

    private String copyright;

    private String copyrightlink;

    private String title;

    private String quiz;

    private String wp;

    private String hsh;

    private Integer drk;

    private Integer top;

    private Integer bot;

    /**
     * 创建人
     */
    private String creationBy;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 最后更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdatedTime;

    private String copyrightName;

    private String copyrightAuthor;

    /**
     * 图片统计
     */
    private BingImagesStatistics bingImagesStatistics;

    /**
     * 图片点赞
     */
    private BingImagesLike bingImageLike;

}