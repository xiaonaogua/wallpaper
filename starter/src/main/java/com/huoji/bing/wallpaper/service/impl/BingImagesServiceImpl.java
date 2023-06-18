package com.huoji.bing.wallpaper.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huoji.bing.wallpaper.domain.vo.BingParamsVO;
import com.huoji.bing.wallpaper.entity.BingImages;
import com.huoji.bing.wallpaper.mapper.BingImagesMapper;
import com.huoji.bing.wallpaper.service.BingImagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class BingImagesServiceImpl implements BingImagesService {
    private static final String BING_HOST = "https://www.bing.com";

    private static final String BING_API_URL = BING_HOST + "/HPImageArchive.aspx?format=js&idx=%d&n=%d";

    @Resource
    private BingImagesMapper bingImagesMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return bingImagesMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(BingImages record) {
        return bingImagesMapper.insert(record);
    }

    @Override
    public int insertSelective(BingImages record) {
        BingParamsVO params = BingParamsVO.builder().urlbase(record.getUrlbase()).build();
        List<BingImages> list = this.selectList(params);
        if (CollectionUtil.isEmpty(list)) {
            return bingImagesMapper.insertSelective(record);
        } else {
            record.setId(list.get(0).getId());
            record.setLastUpdatedTime(new Date());
            return this.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public BingImages selectByPrimaryKey(Integer id) {
        return bingImagesMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(BingImages record) {
        return bingImagesMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BingImages record) {
        return bingImagesMapper.updateByPrimaryKey(record);
    }

    public List<BingImages> selectList(BingParamsVO params) {
        return bingImagesMapper.selectList(params);
    }

    @Override
    public PageInfo<BingImages> selectPageList(BingParamsVO params) {
        // 初始化分页对象
        PageHelper.startPage(params.getPage(), params.getLimit());
        PageInfo<BingImages> pageInfo = new PageInfo(this.selectList(params));
        this.splitCopyRight(pageInfo.getList());
        return pageInfo;
    }

    @Override
    public BingImages selectTodayImage() {
        return bingImagesMapper.selectTodayImage();
    }

    @Override
    public int syncTodayImage() {
        String apiUrl = String.format(BING_API_URL, 0, 1);
        int size = mergeSave(apiUrl);
        return size;
    }

    /**
     * 通过方向向前向后分页加载一些数据
     *
     * @param direction prev / next
     * @param id        当前的ID
     * @return response
     */
    @Override
    public PageInfo<BingImages> selectPageListByCurrentId(String direction, Integer id) {
        // 如果不是上一页，那就是下一页
        if (!StrUtil.equals(direction, "prev")) {
            direction = "next";
        }
        // 初始化分页对象
        PageHelper.startPage(1, 10);
        BingParamsVO params = BingParamsVO.builder().id(id).direction(direction).build();
        PageInfo<BingImages> pageInfo = new PageInfo(this.selectList(params));
        this.splitCopyRight(pageInfo.getList());

        return pageInfo;
    }

    private int mergeSave(String apiUrl) {
        String result = HttpUtil.get(apiUrl);
        JSONObject jsonObject = JSONObject.parseObject(result);
        List<BingImages> images = JSONObject.parseArray(jsonObject.getString("images"), BingImages.class);
        for (BingImages bi : images) {
            if (bi != null) {
                bi.setImagesUrl(String.format("%s%s%s", BING_HOST, bi.getUrlbase(), "_UHD.jpg"));
                this.insertSelective(bi);
            }
        }
        return images.size();
    }

    private void splitCopyRight(List<BingImages> list) {
        list.forEach(i -> {
            i.setCopyrightName(this.copyRightName(i.getCopyright()));
            i.setCopyrightAuthor(this.copyRightAuthor(i.getCopyright()));
        });
    }

    private String copyRightName(String copyRight) {
        if (StrUtil.isNotBlank(copyRight) && copyRight.contains("(")) {
            return copyRight.split("\\(")[0];
        }
        return copyRight;
    }

    private String copyRightAuthor(String copyRight) {
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(copyRight);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return copyRight;
    }

}
