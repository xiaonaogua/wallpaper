package com.huoji.bing.wallpaper.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageInfo;
import com.huoji.bing.wallpaper.domain.response.Response;
import com.huoji.bing.wallpaper.domain.vo.BingParamsVO;
import com.huoji.bing.wallpaper.entity.BingImages;
import com.huoji.bing.wallpaper.entity.BingImagesLike;
import com.huoji.bing.wallpaper.service.BingImagesLikeService;
import com.huoji.bing.wallpaper.service.BingImagesService;
import com.huoji.bing.wallpaper.service.BingImagesStatisticsService;
import com.huoji.bing.wallpaper.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class BingController {

    @Resource
    private BingImagesService imagesService;

    @Resource
    private BingImagesStatisticsService statisticsService;

    @Resource
    private BingImagesLikeService likeService;

    /**
     * 跳转到主窗口
     */
    @GetMapping("/main")
    public ModelAndView main(HttpServletRequest request) {
        return new ModelAndView("main");
    }

    /**
     * 跳转到首页
     */
    @GetMapping("/index")
    public ModelAndView login(HttpServletRequest request) {
        return new ModelAndView("index");
    }

    /**
     * 跳转到壁纸
     */
    @GetMapping("/wallpaper")
    public ModelAndView wallpaper(HttpServletRequest request) {
        return new ModelAndView("wallpaper");
    }

    /**
     * 跳转到关闭
     */
    @GetMapping("/about")
    public ModelAndView about(HttpServletRequest request) {
        return new ModelAndView("about");
    }

    /**
     * 跳转到demo
     */
    @GetMapping("/demo")
    public ModelAndView demo(HttpServletRequest request) {
        return new ModelAndView("demo");
    }

    /**
     * 分页查询
     *
     * @param bingParams
     * @return
     */
    @RequestMapping("/selectPageList")
    @ResponseBody
    public Response selectPageList(BingParamsVO bingParams) {
        // 禁止超过20
        if(bingParams.getLimit() > 20){
            bingParams.setLimit(20);
        }
        PageInfo<BingImages> pageInfo = imagesService.selectPageList(bingParams);
        return Response.success(200, "success", pageInfo);
    }

    /**
     * 获取今天的图片
     *
     * @return Response
     */
    @RequestMapping("/selectTodayImage")
    @ResponseBody
    public Response selectTodayImage() {
        BingImages bingImages = imagesService.selectTodayImage();
        return Response.success(200, "success", bingImages);
    }

    /**
     * 获取今天的图片
     *
     * @return
     */
    @GetMapping("/syncTodayImage")
    @ResponseBody
    public Response syncTodayImage() {
        int count = imagesService.syncTodayImage();
        return Response.success(200, "success", count);
    }

    /**
     * 获取7天内的图片
     *
     * @return
     */
    @GetMapping("/load7DaysImage")
    @ResponseBody
    public Response load7DaysImage() {
        BingParamsVO bingParams = BingParamsVO.builder().build();
        bingParams.setPage(1);
        bingParams.setLimit(7);
        PageInfo<BingImages> pageInfo = imagesService.selectPageList(bingParams);
        return Response.success(200, "success", pageInfo);
    }

    /**
     * 通过方向向前向后分页加载一些数据
     *
     * @param f prev / next
     * @param id 当前的ID
     * @return response
     */
    @RequestMapping("/selectPageListByCurrentId")
    @ResponseBody
    public Response selectPageListByCurrentId(HttpServletRequest request, @RequestParam String f, @RequestParam Integer id) {
        PageInfo<BingImages> pageInfo = imagesService.selectPageListByCurrentId(f, id);
        String ip = IPUtil.getIpAddress(request);
        pageInfo.getList().forEach(i -> {
            i.setBingImagesStatistics(statisticsService.selectByImageId(i.getId()));
            BingImagesLike like = BingImagesLike.builder().imageId(i.getId()).ip(ip).build();
            i.setBingImageLike(likeService.selectHasLike(like));
        });
        return Response.success(200, "success", pageInfo);
    }

    /**
     * 通过方向向前向后分页加载一些数据
     *
     * @return response
     */
    @RequestMapping("/saveDownloadInfo")
    @ResponseBody
    public Response saveDownloadInfo(@RequestParam Integer id) {
        return Response.success(200, "success", statisticsService.addDownloadCount(id, 1));
    }

    /**
     * 查询是否点赞
     *
     * @return
     */
    @GetMapping("/like")
    @ResponseBody
    public Response like(HttpServletRequest request, Integer id) {
        int c = 1;
        String message = "点赞成功";
        String ip = IPUtil.getIpAddress(request);
        BingImagesLike param = BingImagesLike.builder().imageId(id).ip(ip).build();
        List<BingImagesLike> list = likeService.selectList(param);
        if(CollectionUtil.isEmpty(list)) {
            param.setCreationTime(new Date());
            param.setLastUpdatedTime(new Date());
            likeService.insertSelective(param);
        } else {
            param = list.get(0);
            param.setLastUpdatedTime(new Date());
            if(param.getLikeFlag().endsWith("Y")) {
                param.setLikeFlag("N");
                message = "取消点赞";
                c = -1; // 取消点赞 -1
            } else {
                param.setLikeFlag("Y");
            }
            likeService.updateByPrimaryKeySelective(param);
        }
        statisticsService.addLikeCount(id, c);
        return Response.success(200, message, param);
    }

}
