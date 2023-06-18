package com.huoji.bing.wallpaper.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.huoji.bing.wallpaper.mapper.BingImagesLikeMapper;
import com.huoji.bing.wallpaper.entity.BingImagesLike;
import com.huoji.bing.wallpaper.service.BingImagesLikeService;

import java.util.List;

@Service
public class BingImagesLikeServiceImpl implements BingImagesLikeService {

    @Resource
    private BingImagesLikeMapper bingImageLikeMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return bingImageLikeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(BingImagesLike record) {
        return bingImageLikeMapper.insert(record);
    }

    @Override
    public int insertSelective(BingImagesLike record) {
        return bingImageLikeMapper.insertSelective(record);
    }

    @Override
    public BingImagesLike selectByPrimaryKey(Long id) {
        return bingImageLikeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(BingImagesLike record) {
        return bingImageLikeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BingImagesLike record) {
        return bingImageLikeMapper.updateByPrimaryKey(record);
    }

    @Override
    public BingImagesLike selectHasLike(BingImagesLike record) {
        List<BingImagesLike> list = this.selectList(record);
        if(CollectionUtil.isEmpty(list)) {
            return BingImagesLike.builder().likeFlag("N").imageId(record.getImageId()).build();
        }else {
            return list.get(0);
        }
    }

    @Override
    public List<BingImagesLike> selectList(BingImagesLike record) {
        return bingImageLikeMapper.selectList(record);
    }

}
