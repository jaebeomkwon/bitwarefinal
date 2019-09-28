/**
 * 2019-08-27
 * 황준우 
 */
//tab 
$(function(){   
    $(".tab > ul li").click(function(){
        var now_tab = $(this).index();
        $(this).parent().find("li").removeClass("on");
        $(this).parent().parent().parent().find(".info").addClass("hidden");
        $(this).parent().find("li").eq(now_tab).addClass("on");
        $(this).parent().parent().parent().find(".info").eq(now_tab).removeClass("hidden");
    });
});