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
public class BingImagesLike {
    private Long id;

    /**
     * 图片ID
     */
    private Integer imageId;

    private String ip;

    private String phone;

    /**
     * 是否喜欢：Y/N
     */
    private String likeFlag;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdatedTime;
}