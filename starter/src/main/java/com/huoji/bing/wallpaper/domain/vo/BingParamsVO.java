package com.huoji.bing.wallpaper.domain.vo;

import com.huoji.bing.wallpaper.domain.request.PagingParameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BingParamsVO extends PagingParameter {
    private Integer id;

    private String imagesUrl;

    private String startdate;

    private String fullstartdate;

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

    /**
     * prev / next 上一页或者下一页
     */
    private String direction;

}