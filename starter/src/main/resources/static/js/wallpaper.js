var bingHost = "https://www.bing.com"
var currentPage = 1;
var hasMoreData = true;
var loadingMoreData = false;
var topScreen = true;
var currentImgSize = "_480x800.jpg";
var oldImgSize = "_480x800.jpg";
var viewImgSize = "_1920x1080.jpg"; // _768/1280.jpg
var downLoadSize = "_UHD.jpg"; // _768/1280.jpg

var pcFlag = "PC"; // 默认PC
var columnCount = 2;
var imgWidth = 400;
var imgHeight = 240;

var bannerHeight = 200;
// 方向，PC左右，移动端上下
var direction = 'vertical';

// 判断是否是清理屏幕模块
var cleanScreen = false;
// 判断是否是全屏幕模块
var fullScreen = false;

// 分页查询
selectPageList = function () {
    var page = {
        "page": currentPage,
        "limit": 12
    };
    $.ajax({
        url: '/selectPageList',
        dataType: 'json',
        type: 'post',
        data: page,
        success: function (result) {
            if(result.data.list.length > 0) {
                var _html = "";
                for (var i in result.data.list) {
                    var line = result.data.list[i]
                    var imageUrl = bingHost + line.urlbase + currentImgSize;
                    // _html += "<div id=\"images_" + line.id + "\" class=\"images\" style='background: url(" + imageUrl + ") round'></div>";
                    _html += "<div class='img_div'>";
                    _html += "<img data-toggle=\"lightbox\" id=\"images_" + line.id + "\" class=\"images\" data-src='" + imageUrl + "' onclick='clickImage(" + line.id + ")' onmouseover='overImage(" + line.id + ")' onmouseout='outImage(" + line.id + ")'>";
                    // _html += "<span class='img_title'> " + line.title + " </span>";
                    _html += "      <div class='img_title view-swiper-item-img-div'>";
                    _html += "          <div class='span-title'>" + line.title + "<i class='iconfont icon-right'></i></div>";
                    _html += "          <div class='copyright-name'>" + line.copyrightName + "</div>";
                    _html += "          <div class='copyright-author'>" + line.copyrightAuthor + "</div>";
                    _html += "      </div>";
                    _html += "</div>";
                }
                $(".bg").append(_html)
                currentPage++;
                loadingMoreData = false

                // 懒加载图片
                lazyload();
            } else {
                hasMoreData = false;
            }
        }
    })
}

selectTodayImage = function () {
    $.ajax({
        url: '/selectTodayImage',
        dataType: 'json',
        type: 'get',
        success: function (result) {
            var img = bingHost + result.data.urlbase + "_1920x1080.jpg"
            $(".bg").append('<style>.bg:before{background: url(' + img + ') no-repeat center; background-size: cover}</style>')
        }
    })
}

// 同步进入图片
syncTodayImage = function () {
    $.ajax({
        url: '/syncTodayImage',
        dataType: 'json',
        type: 'get',
        success: function (result) {
            console.log(result)
            $("#syncCount").html(result.data)
        }
    })
}

// 同步进入图片
syncHistoryImage = function () {
    $.ajax({
        url: '/syncHistoryImage',
        dataType: 'json',
        type: 'get',
        success: function (result) {
            console.log(result)
        }
    })
}


// 加载7天内的图片
load7DaysImage = function () {
    $.ajax({
        url: '/load7DaysImage',
        dataType: 'json',
        type: 'get',
        success: function (result) {
            var _html = "";
            for (var i in result.data.list) {
                var line = result.data.list[i]
                var imageUrl = bingHost + line.urlbase + "_1920x1080.jpg";

                _html += "<div class=\"swiper-slide\">";
                _html += "<div style=\"position: relative\">";
                _html += "<div class=\"swiper-item-img\" style=\"background-image: url('" + imageUrl + "')\"></div>";
                _html += "<span data-swiper-parallax=\"-4500\" class=\"swiper-item-img-span copyright-name\">" + line.copyrightName + "</span>";
                _html += "<span data-swiper-parallax=\"-6500\" class=\"swiper-item-img-span copyright-author\">" + line.copyrightAuthor + "</span>";
                _html += "</div>";
                _html += "</div>";
            }
            $("#bannerSlide").append(_html);

            resetBannerImageSize();

            var mySwiper = new Swiper('.swiper', {
                direction: 'horizontal', // 垂直切换选项
                loop: true, // 循环模式选项
                parallax: true,
                speed: 2000,
                autoplay: true,

                // 如果需要分页器
                pagination: {
                    el: '.swiper-pagination',
                },

                // 如果需要前进后退按钮
                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev',
                },

                // 如果需要滚动条
                scrollbar: {
                    el: '.swiper-scrollbar',
                },
            })
        }
    })
}

// 获取屏幕窗口大小
getScreenSize = function () {
    var screenWidth = window.innerWidth;
    var screenHeight = window.innerHeight;
    $("#screenWidth").html(screenWidth);
    $("#screenHeight").html(screenHeight);
    // 通过屏幕宽度判断屏幕是PC还是移动端，判断是3列显示还是2列显示，设置宽度和高度
    resetProperties(screenWidth);

    // 重新设置图片高度
    resetImageSize();
}

resetProperties = function (screenWidth) {
    if (screenWidth <= 768) {
        pcFlag = "MOBILE";
    } else {
        pcFlag = "PC";
    }

    // 如果是移动端，并宽度大于等于 > 576px,则3列
    if ((pcFlag == 'PC' && screenWidth >= 1200) || (pcFlag == 'MOBILE' && screenWidth >= 576)) {
        columnCount = 3;
    } else {
        columnCount = 2;
    }

    imgWidth = screenWidth / columnCount - 26;
    if (columnCount == 2) {
        imgWidth = imgWidth - 13
    }
    if (columnCount == 3) {
        imgWidth = imgWidth - 9
    }
    if (pcFlag == 'PC') {
        imgHeight = imgWidth * 0.6;
        currentImgSize = "_800x480.jpg";
        oldImgSize = "_480x800.jpg";
        viewImgSize = "_1920x1080.jpg";
        downLoadSize = "_UHD.jpg";
        direction = "horizontal"; // 移动端左右
    }
    if (pcFlag == 'MOBILE') {
        imgHeight = imgWidth * 1.66666666;
        currentImgSize = "_480x800.jpg";
        oldImgSize = "_800x480.jpg";
        viewImgSize = "_768x1280.jpg";
        downLoadSize = "_1080x1920.jpg";
        direction = "vertical"; // 移动端上下
    }

    bannerHeight = 0.5625 * screenWidth;
    if (bannerHeight > 400) {
        bannerHeight = 400;
    }
}

// 屏幕窗口大小变化
$(window).resize(function (e) {
    getScreenSize();
})

window.addEventListener("scroll", (e) => {
    // 设置图片模糊
    lazyload();
    // 判断触底加载
    loadMore();
    // 判断是否加载top按钮
    loadToTop();
});

lazyload = (e) => {
    $(".images").each(function () {
        if (isInViewport($(this))) {
            var imgUrl = $(this).attr('data-src');
            $(this).attr("src", imgUrl)
            $(this).css("filter", "blur(0px)")
        } else {
            $(this).css("filter", "blur(16px)")
        }
    })
}
loadMore = function () {
    var docHeight = $(document).height();
    var winHeight = $(window).height();
    var scrollTop = $(window).scrollTop();
    if(scrollTop + winHeight + 50 > docHeight && hasMoreData && !loadingMoreData) {
        loadingMoreData = true;
        selectPageList();
    }
}

loadToTop = function () {
    var scrollTop = $(window).scrollTop();
    if(scrollTop > 400 && topScreen) {
        $(".toTop").fadeIn();
    } else {
        $(".toTop").fadeOut();
    }
}

$(".toTop").click(function (event) {
    $("html,body").animate({
        scrollTop:"0px"
    }, 300);
})

// 判断元素是否在视窗
function isInViewport(elem) {
    var elemTop = elem.offset().top;
    var elemBottom = elemTop + elem.height();
    var viewportTop = $(window).scrollTop();
    var viewportBottom = viewportTop + $(window).height();
    return elemTop < viewportBottom && elemBottom > viewportTop;
}

resetImageSize = function () {
    $(".bg").append('<style>.img_div,.images {width: ' + imgWidth + 'px !important;height:' + imgHeight + 'px !important;}</style>')
    images = document.getElementsByClassName("images");
    for (let i = 0; i < images.length; i++) {
        var imgSrc = images[i].getAttribute('data-src');
        imgSrc = imgSrc.replace(oldImgSize, currentImgSize);
        images[i].setAttribute("data-src", imgSrc);
        images[i].setAttribute("src", imgSrc);
    }
    resetBannerImageSize();
}

resetBannerImageSize = function () {
    $(".swiper-item-img").css("height", bannerHeight);
}

overImage = function (img) {
    if(pcFlag == "PC") {
        $("#images_" + img).next(".img_title").css("display", "block");
    }
}

outImage = function (img) {
    if(pcFlag == "PC") {
        $("#images_" + img).next(".img_title").css("display", "none");
    }
}

var viewSwiper = new Swiper("#viewSwiper", {
    // parallax: true, // 视差模式
    direction: direction
})
clickImage = function (img) {
    $("#viewSwiper").fadeIn();
    // 隐藏菜单
    window.parent.hideTabs();

    var f = 'next';
    $.ajax({
        url: '/selectPageListByCurrentId?f=' + f + '&id=' + img,
        dataType: 'json',
        type: 'get',
        success: function (result) {
            var list = result.data.list;
            for (let i in list) {
                var line = result.data.list[i]
                appendSlide(line);
            }
        }
    })
}
appendSlide = function (line) {
    var id = line.id;
    var imgUrl = bingHost + line.urlbase + viewImgSize;
    var downloadUrl = bingHost + line.urlbase + downLoadSize;
    var statistics = line.bingImagesStatistics;
    var likeFlag = line.bingImageLike.likeFlag
    var _html = "<div class='swiper-slide'>";
        _html += "  <div class='viewImgBox'>";
        _html += "      <div class='view-swiper-item-img' style='background-image: url(\"" + imgUrl + "\")'></div>";
        _html += "      <div class='view-swiper-item-img-div'>";
        _html += "          <div class='span-title' data-swiper-parallax='4500'>" + line.title + "<i class='iconfont icon-right'></i></div>";
        _html += "          <div class='copyright-name'>" + line.copyrightName + "</div>";
        _html += "          <div class='copyright-author'>" + line.copyrightAuthor + "</div>";
        _html += "      </div>";
        _html += "      <div class='view-swiper-item-left-div'>";
        _html += "          <div>";
        _html += "              <div onclick='like(" + id + ")'>";
        if(likeFlag == 'Y') {
            _html += "              <i id = 'like" + id + "' class='iconfont icon-hanhan-01-01 yidianzan'></i>";
        } else {
            _html += "              <i id = 'like" + id + "' class='iconfont icon-hanhan-01-01'></i>";
        }
        _html += "              </div>";
        _html += "              <div id='likeCount" + id + "'>" + statistics.likeCount + "</div>";
        _html += "          </div>";
        _html += "          <div>";
        _html += "              <div onclick='download(" + id + ", \"" + downloadUrl + "\")'><i class='iconfont icon-xiazai'></i></div><div id='downloadCount" + id + "'>" + statistics.downloadCount + "</div>";
        _html += "          </div>";
        _html += "          <div>";
        _html += "              <div onclick='comment(" + id + ")'><i class='iconfont icon-pinglun-mian'></i></div><div id='commentCount" + id + "'>" + statistics.commentCount + "</div>";
        _html += "          </div>";
        _html += "      </div>";
        _html += "  </div>";
        _html += "</div>";
    viewSwiper.appendSlide(_html);
}
// 点赞
like = function (id) {
    $.ajax({
        url: '/like?id=' + id,
        dataType: 'json',
        type: 'get',
        success: function (result) {
            debugger
            if(result && result.code == "200") {
                layer.msg(result.msg);
                let likeCount = parseInt($("#likeCount" + id).html());
                if($("#like" + id).hasClass("yidianzan")) {
                    $("#like" + id).removeClass("yidianzan");
                    $("#likeCount" + id).html(likeCount - 1);
                } else {
                    $("#like" + id).addClass("yidianzan")
                    $("#likeCount" + id).html(likeCount + 1);
                }
            }
        }
    })
}
// 下载
download = function (id, downloadUrl) {
    let filename = id + '_' + new Date().getTime() + ".jpg";
    saveDownloadInfo(id);
    downloadImage(downloadUrl, filename);
}
// 加载评论
comment = function (id) {

}

// 设置图片下载次数
function saveDownloadInfo(id) {
    $.ajax({
        url: '/saveDownloadInfo',
        dataType: 'json',
        data: {"id": id},
        type: 'post',
        success: function (result) {
            if(result && result.code == "200") {
                let downloadCount = parseInt($("#downloadCount" + id).html());
                $("#downloadCount" + id).html(downloadCount + 1);
            }
        }
    })
}

function downloadImage(url, filename) {
    debugger
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.responseType = 'blob';
    xhr.onload = function (e) {
        if (this.status == 200) {
            var blob = new Blob([this.response], {type: 'image/jpeg'});
            if (window.navigator.msSaveOrOpenBlob) {
                window.navigator.msSaveBlob(blob, filename);  // For IE11+
            } else {
                var a = document.createElement('a');
                var url = window.URL.createObjectURL(blob);
                a.href = url;
                a.download = filename;
                document.body.appendChild(a);
                a.click();
                setTimeout(function () {
                    document.body.removeChild(a);
                    window.URL.revokeObjectURL(url);
                }, 0);
            }
        }
    };
    xhr.send();
}

/*$(document).on('click', '#fullscreen', function(){
    var docElm = document.documentElement;
    if (docElm.webkitRequestFullScreen) {
        docElm.webkitRequestFullScreen();
    } else if (docElm.mozRequestFullScreen) {
        docElm.mozRequestFullScreen();
    } else {
        alert('Fullscreen API is not supported.');
    }
});*/

/*
function fullScreen() {
    if (document.fullscreenElement)
        document.exitFullscreen()
    } else {
        document.documentElement.requestFullscreen()
    }
}
*/

// $("#fullscreen").click();
getScreenSize();
selectTodayImage();
load7DaysImage();
selectPageList();


$(document).ready(function () {
    $(".close-view").bind({
        click: function () {
            $("#viewSwiper").fadeOut();
            // 显示菜单
            window.parent.showTabs();
            viewSwiper.removeAllSlides();
        },
    })
})