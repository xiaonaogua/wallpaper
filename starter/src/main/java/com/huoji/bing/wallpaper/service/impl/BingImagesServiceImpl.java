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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    @Value("${bing.images.size}")
    private String imageSize;

    @Value("${bing.images.diskPath}")
    private String diskPath;

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
                // 下载文件
                this.saveImage2Disk(bi);
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

    public void saveImage2Disk(BingImages image) {
        String sizes[] = imageSize.split(",");
        for(String size : sizes) {
            String imageUrl = BING_HOST + image.getUrlbase() + size;
            String dir = size.replace(".jpg","").substring(1);
            String fileName = image.getFullstartdate() + size;
            String directoryPath = diskPath + dir;
            String filepath = directoryPath + "/" + fileName;
            log.info(filepath);
            try {
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    boolean created = directory.mkdirs();  // 使用 mkdirs() 方法创建目录及其所有父目录
                }

                URL url = new URL(imageUrl);
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                int responseCode = httpConn.getResponseCode();
                // 确认服务器返回的响应码是 OK
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpConn.getInputStream();
                    FileOutputStream outputStream = new FileOutputStream(filepath);
                    // 可以使用缓冲字节数组来读取和写入数据
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.close();
                    inputStream.close();
                    System.out.println("图片已经成功下载到本地！");
                } else {
                    System.out.println("不能下载图片，服务器响应代码为：" + responseCode);
                }
                httpConn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
