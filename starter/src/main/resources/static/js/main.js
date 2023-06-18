
$("#index_tab").bind({
    click: function () {
        $("#main-iframe").attr("src","/index");
        $(".active_tabs_bar").css("transform","translateX(12px)");
    },
})
$("#wallpaper_tab").bind({
    click: function () {
        $("#main-iframe").attr("src","/wallpaper");
        $(".active_tabs_bar").css("transform","translateX(75px)");
    },
})
$("#about_tab").bind({
    click: function () {
        $("#main-iframe").attr("src","/about");
        $(".active_tabs_bar").css("transform","translateX(136px)");
    },
})
function hideTabs() {
    $(".page-tabs").slideUp().hide();
}
function showTabs() {
    $(".page-tabs").slideDown().show();
}