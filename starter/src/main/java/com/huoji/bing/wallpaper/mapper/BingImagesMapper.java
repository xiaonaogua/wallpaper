package com.huoji.bing.wallpaper.mapper;

import com.huoji.bing.wallpaper.domain.vo.BingParamsVO;
import com.huoji.bing.wallpaper.entity.BingImages;

import java.util.List;

public interface BingImagesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BingImages record);

    int insertSelective(BingImages record);

    BingImages selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BingImages record);

    int updateByPrimaryKey(BingImages record);

    List<BingImages> selectList(BingParamsVO params);

    BingImages selectTodayImage();
}