package com.huoji.bing.wallpaper.task;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.huoji.bing.wallpaper.service.BingImagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 每日同步必应壁纸
 */
@Slf4j
@Component
public class SyncBingWallpaper {

    @Resource
    private BingImagesService imagesService;

    /**
     * 每天0点过1分钟执行一次同步
     */
    @Scheduled(cron = "0 01 0 * * *")
    public void sync(){
        log.info("======== 执行壁纸同步，现在时间是：{}", DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        int count = imagesService.syncTodayImage();
        log.info("======== 执行成功，同步1条数据：{}", count);
    }
}
