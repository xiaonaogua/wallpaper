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
public class BingImagesComment {
    private Long id;

    /**
     * 图片ID
     */
    private Integer imageId;

    private String ip;

    private String phone;

    /**
     * 评论
     */
    private String comment;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdatedTime;
}