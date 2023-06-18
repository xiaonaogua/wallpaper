package com.huoji.bing.wallpaper.service;

import com.github.pagehelper.PageInfo;
import com.huoji.bing.wallpaper.domain.vo.BingParamsVO;
import com.huoji.bing.wallpaper.entity.BingImages;

public interface BingImagesService {

    int deleteByPrimaryKey(Integer id);

    int insert(BingImages record);

    int insertSelective(BingImages record);

    BingImages selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BingImages record);

    int updateByPrimaryKey(BingImages record);

    PageInfo<BingImages> selectPageList(BingParamsVO params);

    BingImages selectTodayImage();

    int syncTodayImage();

    /**
     * 通过方向向前向后分页加载一些数据
     *
     * @param direction prev / next
     * @param id 当前的ID
     * @return response
     */
    PageInfo<BingImages> selectPageListByCurrentId(String direction, Integer id);


}
