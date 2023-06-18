var bingHost = "https://www.bing.com"
selectTodayImage = function () {
    $.ajax({
        url: '/selectTodayImage',
        dataType: 'json',
        type: 'get',
        success: function (result) {
            var img = bingHost + result.data.urlbase + "_1920x1080.jpg"
            $("body").css("background-image", "url(" + img + ")")
        }
    })
}

// 查询每日一言
selectTodayWord = function () {
    var wold = "有志者事竟成，破釜沉舟，百二秦关终属楚；苦心人天不负，卧薪尝胆，三千越甲可吞吴。";
    var wParam = {"str": wold};
    this.printText($("#todayWold"), wParam);

    var author = "--脏三疯";
    var aParam = {"str": author};
    this.printText($("#todayWoldAuthor"), aParam);
}

function printText(Object, parameter) {
    var arr
    var obj
    var typewriter = {
        str: parameter.str || "衣带渐宽终不悔，为伊消得人憔悴.",
        effect: parameter.effect || "upBig",
        speed: parameter.speed || 200,
    }
    arr = [];
    for (var i = 0; i < typewriter.str.length; i++) {
        arr[i] = typewriter.str[i];
    }
    console.log(arr);
    var num = 0;

    obj = setInterval(function () {
        var randomN = Math.floor(Math.random() * 4) + 1;
        var eff = null;
        if (randomN === 1) {
            eff = "fadeInRightBig";
        }
        if (randomN === 2) {
            eff = "fadeInLeftBig";
        }
        if (randomN === 3) {
            eff = "fadeInUpBig";
        }
        if (randomN === 4) {
            eff = "fadeInDownBig";
        }
        if (typewriter.effect === "normal") {
            Object.append('<span style="display: inline-block;" class="tip">' + arr[num] + '</span>');
        } else if (typewriter.effect === "rightBig") {
            Object.append('<span style="display: inline-block;" class="tip animated fadeInRightBig">' + arr[num] + '</span>');
        } else if (typewriter.effect === "right") {
            Object.append('<span style="display: inline-block;" class="tip animated fadeInRight">' + arr[num] + '</span>');
        } else if (typewriter.effect === "leftBig") {
            Object.append('<span style="display: inline-block;" class="tip animated fadeInLeftBig">' + arr[num] + '</span>');
        } else if (typewriter.effect === "left") {
            Object.append('<span style="display: inline-block;" class="tip animated fadeInLeft">' + arr[num] + '</span>');
        } else if (typewriter.effect === "downBig") {
            Object.append('<span style="display: inline-block;" class="tip animated fadeInDownBig">' + arr[num] + '</span>');
        } else if (typewriter.effect === "down") {
            Object.append('<span style="display: inline-block;" class="tip animated fadeInDown">' + arr[num] + '</span>');
        } else if (typewriter.effect === "upBig") {
            Object.append('<span style="display: inline-block;" class="tip animated fadeInUpBig">' + arr[num] + '</span>');
        } else if (typewriter.effect === "up") {
            Object.append('<span style="display: inline-block;" class="tip animated fadeInUp">' + arr[num] + '</span>');
        } else if (typewriter.effect === "random") {
            Object.append('<span style="display: inline-block;" class="tip animated ' + eff + '">' + arr[num] + '</span>');
        }
        num++
        if (num === arr.length) {
            clearInterval(obj);
        }
    }, typewriter.speed);
}

// 查询当日图片
selectTodayImage();
// 查询今日一言
selectTodayWord();

