/* 사이드메뉴 */
$(function(){
	$(".aside_con .dep1 a").click(function(e){

	$('.dep2ul').removeClass('active');
	if($(this).parent().hasClass('have')){
		e.preventDefault();
		$(this).siblings('.dep2ul').removeClass('active');
		$(this).siblings('.dep2ul').addClass('active');
	}
	});
});

$(window).load(function(){
  var url_pathname = window.location.pathname;
    var url_search = window.location.search;
    var url = url_pathname +  url_search;
     $(".aside_con .dep2ul li a").each(function(){
//      $(this).parent().parent().removeClass("active");
      $(this).removeClass('active');
      if ($(this).attr("href") == url ){
      $(this).addClass("active");
      $(this).parent().parent().addClass("active");
      }
  });   
});

/* 전체페이지 autocomplete off */
$(document).ready(function(){
    $( document ).on( 'focus', ':input', function(){
        $( this ).attr( 'autocomplete', 'off' );
    });
});

/* 채팅 아코디언  */
$(function(){
	$(".btn_chat").bind("click", function(){
		if($(this).data("dragging")) return;
		
		$(this).toggleClass("on");
		if($(this).hasClass("on") == true){
			$(this).parent(".chat_inner").css("transform","translateX(-100px)");	
			$(".tab").click(function(){
				$(this).parent().parent(".chat_inner").css("transform","translateX(-300px)");
			});
			
		}else{
			$(this).parent(".chat_inner").css("transform","translateX(0px)");
			$(this).parent().parent(".chat_inner").css("transform","translateX(0px)");
		}
		
		$.ajax({
			url: "/user/chatDepartmentListAjax",
			data: {},
			datatype: "json",
			success: function(lists){
				var str = "";
				for(var i in lists){
					if(lists[i].department.deptName=="지원부"){
						if(lists[i].check){
							str += "<li class='person' value='"+lists[i].department.deptName+"'><a href='#dep"+(Number(i)+1)+"'>"+lists[i].department.deptName+"<span><img src='/images/icon_new.png'></span></a></li>";
						}else{
							str += "<li class='person' value='"+lists[i].department.deptName+"'><a href='#dep"+(Number(i)+1)+"'>"+lists[i].department.deptName+"</a></li>";
						}
					}else if(lists[i].department.deptName=="영업부"){
						if(lists[i].check){
							str += "<li class='person' value='"+lists[i].department.deptName+"'><a href='#dep"+(Number(i)+1)+"'>"+lists[i].department.deptName+"<span><img src='/images/icon_new.png'></span></a></li>";
						}else{
							str += "<li class='person' value='"+lists[i].department.deptName+"'><a href='#dep"+(Number(i)+1)+"'>"+lists[i].department.deptName+"</a></li>";
						}
					}else if(lists[i].department.deptName=="개발부"){
						if(lists[i].check){
							str += "<li class='person' value='"+lists[i].department.deptName+"'><a href='#dep"+(Number(i)+1)+"'>"+lists[i].department.deptName+"<span><img src='/images/icon_new.png'></span></a></li>";
						}else{
							str += "<li class='person' value='"+lists[i].department.deptName+"'><a href='#dep"+(Number(i)+1)+"'>"+lists[i].department.deptName+"</a></li>";
						}
					}
				}
				$("#department li").remove();
				$("#department").append(str);
				
				$(function(){
					$('.person').bind('click',function(){
						var deptName = $(this).attr("value");
						$.ajax({
							url: "/user/chatMemberListByDepartmentAjax",
							data: {deptName:deptName},
							datatype: "json",
							success: function(list){
								var str = "";
								for(var i in list){
									if(list[i].content==null){
										list[i].content="";
									}
									if(list[i].count!=0){
										if(list[i].member.teamName==null){
											str += "<li class='memberByDept' value='"+list[i].member.memId+"'>"+"<p>"+list[i].member.ranks+"</p><p>"+list[i].member.memName+"</p><span class='count'>"+list[i].count+"</span><p>"+list[i].content+"</p></li>";
										}else{
											str += "<li class='memberByDept' value='"+list[i].member.memId+"'>"+"<p>"+list[i].member.teamName+" "+list[i].member.ranks+"</p><p>"+list[i].member.memName+"</p><span class='count'>"+list[i].count+"</span><p>"+list[i].content+"</p></li>";
										}
									}else{
										if(list[i].member.teamName==null){
											str += "<li class='memberByDept' value='"+list[i].member.memId+"'>"+"<p>"+list[i].member.ranks+"</p><p>"+list[i].member.memName+"</p><p>"+list[i].content+"</p></li>";
										}else{
											str += "<li class='memberByDept' value='"+list[i].member.memId+"'>"+"<p>"+list[i].member.teamName+" "+list[i].member.ranks+"</p><p>"+list[i].member.memName+"</p><p>"+list[i].content+"</p></li>";
										}
									}
								}
								$("#memberByDepartment li").remove();
								$("#memberByDepartment").append(str);
							}
						});
					});
				});
			}
		})
	});
	$( ".btn_chat" ).draggable({
		containment:".chat_inner",
		axis:"y",
		start: function(event, ui){
			$(this).data("dragging", true);
		},
		stop: function(event, ui){
			setTimeout(function(){
				$(event.target).data("dragging", false);
			}, 1);
		}
    });
	
	$(document).on('click', '.memberByDept', function(){
		var memId = $(this).attr('value');
		var url = "/user/chat?memId=" + memId;
		var name = "chatPopup";
		
		var popupX=(window.innerWidth/2) - (500/2);
		var popupY=(window.innerHeight/2) - (600/2);
		
		var options = "width=500, height=600, scrollbars, resizable, toolbar=no, left="+popupX+", top="+popupY;
		var win = window.open(url, name, options);
		var interval = window.setInterval(function() {
			if (win == null || win.closed) {
				window.clearInterval(interval);
				$.ajax({
					url:"/user/deleteMap",
					data:{},
					success:function(data){
					}
				});
		    }
		}, 1000);

		$("#btn_chat_enter").click();
	})
});

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


$(window).load(function(){
	  var url_pathname = window.location.pathname;
	    var url_search = window.location.search;
	    var url = url_pathname +  url_search;
	     $(".tab2 > ul li a").each(function(){
	      if ($(this).attr("href") == url ){
	      $(this).parent().addClass("on");
	     }
	  });   
	});




$(function(){ 
	setInterval(function(){
		$.ajax({
			url:"/user/checkChatAlert",
			data:{},
			success:function(msg){
				if(msg=="new messages"){
					/*document.getElementById("smile_color_change").parentNode.style.background="#ff0000";
					document.getElementById("smile_color_change").parentNode.style.border="1px solid #ff0000";*/
					$(".smile").removeClass('fal');
					$(".smile").removeClass('fa-comment-smile');
					$(".smile").removeClass('fa-flip-horizontal');
					
					$(".smile").addClass('fas');
					$(".smile").addClass('fa-exclamation');
					$(".smile").addClass('blink');
				}
				if(msg=="no new messages"){
//					document.getElementById("smile_color_change").parentNode.style.background="#003263";
//					document.getElementById("smile_color_change").parentNode.style.border="1px solid #003263";
					$(".smile").removeClass('fas');
					$(".smile").removeClass('fa-exclamation');
					$(".smile").removeClass('blink');
					
					$(".smile").addClass('fal');
					$(".smile").addClass('fa-comment-smile');
					$(".smile").addClass('fa-flip-horizontal');
					
				}
			}
		});
	}, 1000);
});

