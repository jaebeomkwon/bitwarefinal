/*WBS 화면 구성*/

/*최초 ProjectWbs 화면 그리기 */
var baseData = {} // ProjectInfoDto
var subData = {} // List<ProjectWbsDto>

$(function(){
	
	// 기본데이터 저장 객체
	baseData = {
			start : $('#prjStart').val().split("-"),
			end : $('#prjEnd').val().split("-"),
			schedule : function() { //term 구하기. 날짜와 날짜로 일수 차이를 구하는 공식. [1]에서 -1 해주는 이유는 date객체의 월은 실제 월보다 1이 작기 때문
				var start = new Date(this.start[0], this.start[1]-1, this.start[2]);
				var end = new Date(this.end[0], this.end[1]-1, this.end[2]);
				return ((end.getTime() - start.getTime()) / (1000*60*60*24)) + 1;
			}
	}
	
	var prjCodes = $('#prjCode').val();

	$.ajax({
		url: '/user/selectProjectWbsListAjax',
		type: 'post',
		async: false,
		data: {
			prjCode : prjCodes
		},
		success: function(lists) {
			if(lists.length != 0){
				baseData.chk = true;
				subData.cnt = lists.length;
				subData.prjWorkName = new Array();
				subData.prjGroup = new Array();
				subData.prjStep = new Array();
				subData.prjDepth = new Array();
				subData.prjManager = new Array();
				subData.prjOutput = new Array();
				subData.prjPlanStart = new Array();
				subData.prjPlanEnd = new Array();
				subData.prjRealStart = new Array();
				subData.prjRealEnd = new Array();
				subData.prjWorkCompletion = new Array();
				subData.prjTotalDays = new Array();
				$.each(lists, function(i, projectWbsDto){
					subData.prjWorkName[i] = projectWbsDto.prjWorkName;
					subData.prjGroup[i] = projectWbsDto.prjGroup;
					subData.prjStep[i] = projectWbsDto.prjStep;
					subData.prjDepth[i] = projectWbsDto.prjDepth;
					subData.prjManager[i] = projectWbsDto.prjManager;
					subData.prjOutput[i] = projectWbsDto.prjOutput;
					subData.prjPlanStart[i] = projectWbsDto.prjPlanStart;
					subData.prjPlanEnd[i] = projectWbsDto.prjPlanEnd;
					subData.prjRealStart[i] = projectWbsDto.prjRealStart;
					subData.prjRealEnd[i] = projectWbsDto.prjRealEnd;
					subData.prjWorkCompletion[i] = projectWbsDto.prjWorkCompletion;
					subData.prjTotalDays[i] = projectWbsDto.prjTotalDays;
					});
				}else{
					baseData.chk = false;
 				}
			}
	});
	
	if(baseData.chk) {
		if($('#sessionRanks').val() == "부장" && $('#sessionDeptName').val() == "개발부"){
			for(var i=0; i<subData.cnt; i++){
				$('#tbody').append(screenWriteTbodyEdit(i, subData.prjGroup[i], subData.prjStep[i], subData.prjDepth[i],
							subData.prjWorkName[i], subData.prjManager[i], subData.prjOutput[i], subData.prjPlanStart[i], 
							subData.prjPlanEnd[i], subData.prjRealStart[i], subData.prjRealEnd[i],
							subData.prjWorkCompletion[i], subData.prjTotalDays[i]));
			}
		}else if($('#sessionRanks').val() == "대표" || $('#sessionRanks').val() == "이사"){
			for(var i=0; i<subData.cnt; i++){
				$('#tbody').append(screenWriteTbodyEdit(i, subData.prjGroup[i], subData.prjStep[i], subData.prjDepth[i],
						subData.prjWorkName[i], subData.prjManager[i], subData.prjOutput[i], subData.prjPlanStart[i], 
						subData.prjPlanEnd[i], subData.prjRealStart[i], subData.prjRealEnd[i],
						subData.prjWorkCompletion[i], subData.prjTotalDays[i]));
			}
		}else{
			for(var i=0; i<subData.cnt; i++){
				$('#tbody').append(screenWriteTbodyText(i, subData.prjGroup[i], subData.prjStep[i], subData.prjDepth[i],
							subData.prjWorkName[i], subData.prjManager[i], subData.prjOutput[i], subData.prjPlanStart[i], 
							subData.prjPlanEnd[i], subData.prjRealStart[i], subData.prjRealEnd[i], 
							subData.prjWorkCompletion[i], subData.prjTotalDays[i]));
			}
		}
		//WBS 진행률 통계
		WBSProgress();
	}
});

/*테이블 tbody 화면에 그리는 함수 (개발부 부장 수정 가능 projectWbs 출력) */
function screenWriteTbodyEdit(num, group, step, depth, workName, manager, output, planStart, planEnd, realStart, realEnd, workCompletion, totalDays) {
	var tag = new StringBuffer();
	
	// 텍스트 "null"이나 값의 null인 경우 대체해서 넣어 줄 텍스트
	var substitute = " ";
	
	// 담당자와 산출물   텍스트 "null" 이나 값의 null인지 체크 후 대체해주는 과정
	if(manager == "null" || manager == null){
		manager = substitute;
	}
	if(output == "null" || output == null){
		output = substitute;
	}
	tag.append("<tr id='"+num+"'>");
	tag.append("<td><input type='checkbox' name='chkVal' value='"+num+"'/></td>")
	tag.append("<td>");
	tag.append('<div class="dropdown">');
	tag.append('<button class="btn btn_point" id="dLabel" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">');
	tag.append(numFormat(Number(num)+1));
	tag.append('</button>');
	tag.append('<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">');
	tag.append('<li><a onclick="createList('+num+')">목록추가</a></li>');
	tag.append('<li><a onclick="createChildList('+num+')">하위추가</a></li>');
	tag.append('<li><a onclick="createParentList('+num+')">상위추가</a></li>');
	tag.append('</ul></div>');
	tag.append("<input type='hidden' name='inPrjGroup' value='"+group+"'>");
	tag.append("<input type='hidden' name='inPrjStep' value='"+step+"'>");
	tag.append("<input type='hidden' name='inPrjDepth' value='"+depth+"'>");
	tag.append("<input type='hidden' name='workCompletion' value='"+workCompletion+"'>");
	tag.append("</td>");
	tag.append("<td style='overflow: hidden;'><input style='margin-left: "+(25*depth)+"px;' type='text' name='inPrjWorkName' value='"+workName+"' autocomplete='off'></td>");
	tag.append("<td><input type='date' name='planStart' value='"+planStart+"'></td>");
	tag.append("<td><input type='date' name='planEnd' value='"+planEnd+"'></td>");
	tag.append("<td><input type='date' name='realStart' value='"+realStart+"'></td>");
	tag.append("<td><input type='date' name='realEnd' value='"+realEnd+"'></td>");
	tag.append("<td><input type='text' name='inPrjManager' value='"+manager+"'></td>");
	tag.append("<td><input type='text' name='inPrjOutput' value='"+output+"'></td>")
	tag.append("<td class='planProgressList'>0%</td>");
	tag.append("<td class='realProgressList'>0%</td>");
	tag.append(totalDaysAnalysis(totalDays, baseData.schedule()));
	tag.append("</tr>");
	
	return tag.toString();
}

/*테이블 tbody 화면에 그리는 함수 (읽기 전용 projectWbs 출력) */
function screenWriteTbodyText(num, group, step, depth, workName, manager, output, planStart, planEnd, realStart, realEnd, workCompletion, totalDays) {
	var tag = new StringBuffer();
	
	// 텍스트 "null"이나 값의 null인 경우 대체해서 넣어 줄 텍스트
	var substitute = " ";
	
	// 담당자와 산출물   텍스트 "null" 이나 값의 null인지 체크 후 대체해주는 과정
	if(manager == "null" || manager == null){
		manager = substitute;
	}
	if(output == "null" || output == null){
		output = substitute;
	}
	tag.append("<tr id='"+num+"'>");
	tag.append("<td><input type='checkbox' name='chkVal' value='"+num+"'/></td>")
	tag.append("<td>");
	tag.append('<div class="dropdown">');
	tag.append('<button class="btn btn_point" id="dLabel" type="button" aria-haspopup="true" aria-expanded="false">');
	tag.append(numFormat(Number(num)+1));
	tag.append('</button>');
	tag.append("<input type='hidden' name='inPrjGroup' value='"+group+"'>");
	tag.append("<input type='hidden' name='inPrjStep' value='"+step+"'>");
	tag.append("<input type='hidden' name='inPrjDepth' value='"+depth+"'>");
	tag.append("<input type='hidden' name='workCompletion' value='"+workCompletion+"'>");
	tag.append("</td>");
	tag.append("<td style='overflow: hidden;'><input style='margin-left: "+(25*depth)+"px;' type='text' name='inPrjWorkName' value='"+workName+"' readonly='readonly'></td>");
	tag.append("<td><input type='date' name='planStart' value='"+planStart+"' readonly='readonly'></td>");
	tag.append("<td><input type='date' name='planEnd' value='"+planEnd+"' readonly='readonly'></td>");
	tag.append("<td><input type='date' name='realStart' value='"+realStart+"' readonly='readonly'></td>");
	tag.append("<td><input type='date' name='realEnd' value='"+realEnd+"' readonly='readonly'></td>");
	tag.append("<td><input type='text' name='inPrjManager' value='"+manager+"' readonly='readonly'></td>");
	tag.append("<td><input type='text' name='inPrjOutput' value='"+output+"' readonly='readonly'></td>")
	tag.append("<td class='planProgressList'>0%</td>");
	tag.append("<td class='realProgressList'>0%</td>");
	tag.append(totalDaysAnalysis(totalDays, baseData.schedule()));
	tag.append("</tr>");
	
	return tag.toString();
}

// 맨 처음 목록 생성
function createTopList(){
	if(baseData.chk){
		$('#tbody tr').attr('id', function(){
			return Number($(this).attr('id'))+1;
		}).find('input[name=chkVal]').attr('value', function(){
			return Number($(this).val()) + 1;
		}).end().find('input[name=inPrjGroup]').attr('value', function(){
			return Number($(this).val()) + 1;
		}).end().find('button').text(function(){
			return numFormat(Number($(this).text()) + 1);
		}).end().find('a').attr('onclick', function(){
			if($(this).attr('onclick').indexOf('createChildList') != -1){
				return 'createChildList('+$(this).parents("tr").attr("id")+')';
			}else if($(this).attr('onclick').indexOf('createParentList') != -1){
				return 'createParentList('+$(this).parents("tr").attr("id")+')';
			}else {
				return 'createList('+$(this).parents("tr").attr("id")+')';
			}
		});
	}
	$('#tbody').prepend(screenWriteTbodyEdit(0, 0, 0, 0, '', '', '', '', '', '', '', 0, ''));
	subData.cnt = $('#tbody tr').length;
	baseData.chk = true;
}

// 상위 추가
function createParentList(num){
	var myPrjGroup = $('#tbody tr:eq('+num+')').find('input[name=inPrjGroup]').val();
	var myPrjStep = $('#tbody tr:eq('+num+')').find('input[name=inPrjStep]').val();
	var myPrjDepth = $('#tbody tr:eq('+num+')').find('input[name=inPrjDepth]').val();
	
	var sumStep = 1;
	$('#tbody tr:gt('+num+')').attr('id', function(){
		return Number($(this).attr('id')) + 1;
	}).find('input[name=chkVal]').attr('value', function(){
		return Number($(this).val()) + 1;
	}).end().find('input[name=inPrjGroup]').attr('value', function(){
		if(myPrjDepth == '0'){
			return Number($(this).val()) + 1;
		}else if(myPrjDepth == '1'){
			if(myPrjGroup == $(this).val()){
				$(this).parent('td').find('input[name=inPrjStep]').attr('value', function(){
					return sumStep++;
				});
			}
			return Number($(this).val()) + 1;
		}else{
			if(myPrjGroup == $(this).val){
				$(this).parent('td').find('input[name=inPrjStep]').attr('value', function(){
					return Number($(this).parent('td').find('input[name=inPrjStep]').val()) + 1;
				});
			}
			return $(this).val();
		}
	}).end().find('button').text(function(){
		return numFormat(Number($(this).text()) + 1);
	}).end().find('a').attr('onclick', function(){
		if($(this).attr('onclick').indexOf('createChildList') != -1){
			return 'createChildList('+$(this).parents("tr").attr("id")+')';
		}else if($(this).attr('onclick').indexOf('createParentList') != -1){
			return 'createParentList('+$(this).parents("tr").attr("id")+')';
		}else {
			return 'createList('+$(this).parents("tr").attr("id")+')';
		}
	});
	
	if(myPrjDepth == '0'){
		$('#tbody tr:eq('+num+')').after(screenWriteTbodyEdit(num+1, Number(myPrjGroup)+1, 0, 0, '', '', '', '', '', '', '', 0, ''));
	}else if(myPrjDepth == '1'){
		$('#tbody tr:eq('+num+')').after(screenWriteTbodyEdit(num+1, Number(myPrjGroup)+1, 0, 0, '', '', '', '', '', '', '', 0, ''));
	}else{
		$('#tbody tr:eq('+num+')').after(screenWriteTbodyEdit(num+1, myPrjGroup, Number(myPrjStep)+1, Number(myPrjDepth)-1, '', '', '', '', '', '', '', 0, ''));
	}
	subData.cnt = $('#tbody tr').length;
}

// 목록 추가
function createList(num) {
	var myPrjGroup = $('#tbody tr:eq('+num+')').find('input[name=inPrjGroup]').val();
	var myPrjStep = $('#tbody tr:eq('+num+')').find('input[name=inPrjStep]').val();
	var myPrjDepth = $('#tbody tr:eq('+num+')').find('input[name=inPrjDepth]').val();
	$('#tbody tr:gt('+num+')').attr('id',function(){
		return Number($(this).attr('id')) + 1;
	}).find('input[name=chkVal]').attr('value', function(){
		return Number($(this).val()) + 1;
	}).end().find('input[name=inPrjGroup]').attr('value', function(){
		if($(this).val() == myPrjGroup) {
			if(myPrjStep == '0') {
				return Number($(this).val()) + 1;
			}else {
				$(this).parent('td').find('input[name=inPrjStep]').attr('value', function(){
					return Number($(this).parent('td').find('input[name=inPrjStep]').val()) + 1;
				});
				return $(this).val();
			}
		}else if(myPrjStep != '0') {
			return $(this).val();
		}else {
			return Number($(this).val()) + 1;
		}
	}).end().find('button').text(function(){
		return numFormat(Number($(this).text()) + 1);
	}).end().find('a').attr('onclick', function(){
		if($(this).attr('onclick').indexOf('createChildList') != -1) {
			return 'createChildList('+$(this).parents("tr").attr("id")+')';
		}else if($(this).attr('onclick').indexOf('createParentList') != -1) {
			return 'createParentList('+$(this).parents("tr").attr("id")+')';
		}else {
			return 'createList('+$(this).parents("tr").attr("id")+')';
		}
	});
	if(myPrjStep == '0') {
		$('#tbody tr:eq('+num+')').after(screenWriteTbodyEdit(num+1, Number(myPrjGroup)+1, 0, 0, '', '', '', '', '', '', '', 0, ''));
	}else {
		$('#tbody tr:eq('+num+')').after(screenWriteTbodyEdit(num+1, myPrjGroup, Number(myPrjStep)+1, myPrjDepth, '', '', '', '', '', '', '', 0, ''));
	}
	subData.cnt = $('#tbody tr').length;
}

//하위추가
function createChildList(num) {
	var myPrjGroup = $('#tbody tr:eq('+num+')').find('input[name=inPrjGroup]').val();
	var myPrjStep = $('#tbody tr:eq('+num+')').find('input[name=inPrjStep]').val();
	var myPrjDepth = $('#tbody tr:eq('+num+')').find('input[name=inPrjDepth]').val();
	$('#tbody tr:gt('+num+')').attr('id',function(){
		return Number($(this).attr('id'))+1;
	}).find('input[name=chkVal]').attr('value', function(){
		return Number($(this).val()) + 1;
	}).end().find('input[name=inPrjStep]').attr('value', function(){
		if($(this).parent('td').find('input[name=inPrjGroup]').val() == myPrjGroup) {
			return Number($(this).val()) + 1;
		}else {
			return $(this).val();
		}
	}).end().find('button').text(function(){
		return numFormat(Number($(this).text()) + 1);
	}).end().find('a').attr('onclick', function(){
		if($(this).attr('onclick').indexOf('createChildList') != -1) {
			return 'createChildList('+$(this).parents("tr").attr("id")+')';
		}else if($(this).attr('onclick').indexOf('createParentList') != -1) {
			return 'createParentList('+$(this).parents("tr").attr("id")+')';
		}else {
			return 'createList('+$(this).parents("tr").attr("id")+')';
		}
	});
	$('#tbody tr:eq('+num+')').after(screenWriteTbodyEdit(num+1, myPrjGroup, Number(myPrjStep)+1, Number(myPrjDepth)+1, '', '', '', '', '', '', '', 0, ''));

	subData.cnt = $('#tbody tr').length;
}


/*프로젝트 WBS CRUD*/

// 삭제처리
function checkListRemove() {
	var isc = 0;
	while(true) {
		var chkVal = document.getElementsByName("chkVal");
		for (var i=0,cnt=0; i<chkVal.length; i++) {
			if(chkVal[i].checked) {
				cnt++;
			}
		}
		if(cnt==0) {
			break;
		}
		isc++;
		for (var i=0; i<chkVal.length; i++) {
			if(chkVal[i].checked) {
				var myPrjGroup = $('#tbody tr:eq('+chkVal[i].value+')').find('input[name=inPrjGroup]').val();
				var myPrjStep = $('#tbody tr:eq('+chkVal[i].value+')').find('input[name=inPrjStep]').val();
				$('#tbody tr:gt('+chkVal[i].value+')').attr('id',function(){
					return Number($(this).attr('id')) - 1;
				}).find('input[name=chkVal]').attr('value', function(){
					return Number($(this).val()) - 1;
				}).end().find('input[name=inPrjGroup]').attr('value',function(){
					if(myPrjGroup == $(this).val() && myPrjStep != '0') {
						$(this).parent('td').find('input[name=inPrjStep]').attr('value', function(){
							return Number($(this).parent('td').find('input[name=inPrjStep]').val()) - 1;
						});
						return $(this).val();
					}else if(myPrjStep != '0') {
						return $(this).val();
					}else {
						return Number($(this).val()) - 1;
					}
				}).end().find('button').text(function(){
					return numFormat(Number($(this).text()) - 1);
				}).end().find('a').attr('onclick', function(){
					if($(this).attr('onclick').indexOf('createChildList') != -1) {
						return 'createChildList('+$(this).parents("tr").attr("id")+')';
					}else if($(this).attr('onclick').indexOf('createParentList') != -1) {
						return 'createParentList('+$(this).parents("tr").attr("id")+')';
					}else {
						return 'createList('+$(this).parents("tr").attr("id")+')';
					}
				});
				$('#tbody tr:eq('+chkVal[i].value+')').remove();
				break;
			}
		}
	}
	if(isc==0) {
		alert('삭제할 작업을 선택해주세요');
	}
	if($('#tbody tr').length == 0) {
		baseData.chk = false;
	}
	subData.cnt = $('#tbody tr').length;
}

// 프로젝트 WBS 저장 전 검사
function insertProjectWbsList(){
	//작업명 배열 담기
	var prjWorkNames = byNameArray(document.getElementsByName("inPrjWorkName"));
	//계획 작업 시작일 배열 담기
	var prjPlanStarts = byNameArray(document.getElementsByName("planStart"));
	//계획 작업 종료일 배열 담기
	var prjPlanEnds = byNameArray(document.getElementsByName("planEnd"));
	
	//실제 작업 시작일 배열 담기
	var prjRealStarts = byNameArray(document.getElementsByName("realStart"));
	//실제 작업 종료일 배열 담기
	var prjRealEnds = byNameArray(document.getElementsByName("realEnd"));
	
	//프로젝트 시작일, 종료일 담기
	var prjStart = parseDate($('#prjStart').val());
	var prjEnd = parseDate($('#prjEnd').val());
	var chk = true;
	// split 하지 않고 하이픈을 제거하는 방법
	//var testc = prjStart.replace(/-/gi , "");
	
	
	//업무구분 입력 여부 확인
	for(var i=0; i<prjWorkNames.length; i++){
			if(prjWorkNames[i].trim() == '' || prjWorkNames[i].trim().length == 0){
				alert('업무구분 작업명을 모두 입력해주세요. 참고 : '+(i+1)+"번째 작업명");
				chk = false;
				break;
			}
		}
	//계획 작업 시작일 입력 여부 확인
	for(var i=0; i<prjPlanStarts.length; i++){
			if(prjPlanStarts[i].trim() == '' || prjPlanStarts[i].trim().length == 0){
				alert('계획 작업 시작일을 모두 입력해주세요. 참고 : '+(i+1)+"번째 계획 작업 시작일");
				chk = false;
				break;
			}
		}
	//계획 작업 시작일 프로젝트 시작일 범위 초과 확인
	for(var i= 0; i<prjPlanStarts.length; i++){
		if(prjStart.getTime() > parseDate(prjPlanStarts[i]).getTime()){
			alert('계획 작업 시작일은 프로젝트 시작일과 같거나 이후로 입력해주세요. 참고 : '+(i+1)+"번째 계획 작업 시작일");
			chk = false;
			break;
		}
	}
	//계획 작업 종료일 입력 여부 확인
	for(var i=0; i<prjPlanEnds.length; i++){
			if(prjPlanEnds[i].trim() == '' || prjPlanEnds[i].trim().length == 0){
				alert('계획 작업 종료일을 모두 입력해주세요. 참고 : '+(i+1)+"번째 계획 작업 종료일");
				chk = false;
				break;
			}
		}
	//계획 작업 종료일 프로젝트 시작일 범위 초과 확인
	for(var i= 0; i<prjPlanEnds.length; i++){
		if(parseDate(prjPlanEnds[i]).getTime() > prjEnd.getTime()){
			alert('계획 작업 종료일은 프로젝트 종료일과 같거나 이전으로 입력해주세요. 참고 : '+(i+1)+"번째 계획 작업 종료일");
			chk = false;
			break;
		}
	}
	//계획 작업 시작일, 계획 작업 종료일 비교
	for(var i= 0; i<prjPlanEnds.length; i++){
		if(parseDate(prjPlanStarts[i]).getTime() > parseDate(prjPlanEnds[i]).getTime()){
			alert('계획 작업 종료일은 계획 작업 시작일과 같거나 이후로 입력해주세요. 참고 : '+(i+1)+"번째 계획 작업 종료일");
			chk = false;
			break;
		}
	}
	//실제 작업 시작일 프로젝트 시작일 범위 초과 확인
	for(var i= 0; i<prjRealStarts.length; i++){
		if(nvl(prjRealStarts[i], "")){
			if(prjStart.getTime() > parseDate(prjRealStarts[i]).getTime()){
				alert('실제 작업 시작일은 프로젝트 시작일과 같거나 이후로 입력해주세요. 참고 : '+(i+1)+"번째 실제 작업 시작일");
				chk = false;
				break;
			}
		}
	}
	//실제 작업 종료일 프로젝트 시작일 범위 초과 확인
	for(var i= 0; i<prjRealEnds.length; i++){
		if(nvl(prjRealEnds[i], "")){
			if(parseDate(prjRealEnds[i]).getTime() > prjEnd.getTime()){
				alert('실제 작업 종료일은 프로젝트 종료일과 같거나 이전으로 입력해주세요. 참고 : '+(i+1)+"번째 실제 작업 종료일");
				chk = false;
				break;
			}
		}
	}
	//실제 작업 시작일, 실제 작업 종료일 비교
	for(var i= 0; i<prjRealEnds.length; i++){
		if(nvl(prjRealStarts[i], "")&&nvl(prjRealEnds[i], "")){
			if(parseDate(prjRealStarts[i]).getTime() > parseDate(prjRealEnds[i]).getTime()){
				alert('실제 작업 종료일은 실제 작업 시작일과 같거나 이후로 입력해주세요. 참고 : '+(i+1)+"번째 실제 작업 종료일");
				chk = false;
				break;
			}
		}
	}
	//전부 입력되어 chk true일경우 insertProjectWbsListAjax() 실행
	if(chk){
		insertProjectWbsListAjax();
	}else{
		
	}
}

// 프로젝트 WBS 저장
var insertProjectWbsListAjax = function(){
	
	$.ajax({
		url: '/user/insertProjectWbsListAjax',
		type: 'post',
		async: false,
		data: $('#insertProjectWbsList').serialize(),
		success: function(msg){
			if(msg){
				alert('저장에 성공했습니다.');
			}else{
				alert('저장에 실패했습니다.');
			}
		},
		error : function(msg){
			alert(msg);
		}
	});
	
}



/*통계 */

//진행률 통계 구하기
function WBSProgress(){
	
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1
	var day = date.getDate();
	
	if(month < 10){
		month = "0"+month;
	}
	if(day < 10){
		day = "0"+day;
	}
	var today = year+"-"+month+"-"+day;
	
	//wbs항목들의 percent 값을 담을 배열 생성
	var planPercentArray = new Array();
	var realPercentArray = new Array();
	//분모 구하기 (WBS종료일 - WBS시작일)
	var planDenominator = new Array();
	var realDenominator = new Array();
	//분자 구하기 (TODAY - WBS시작일)
	var planMolecule = new Array();
	var realMolecule = new Array();
	//임시 배열 변수
	var planTempNum = new Array();
	var realTempNum = new Array();
	//총 진행률 값 담을 임시 배열 변수
	var planTempNumTotal = new Array();
	var realTempNumTotal = new Array();
	//총 진행률 계산 값
	var planTotalProgress = 0;
	var realTotalProgress = 0;
	//계산 완료된 총 진행률을 담을 값
	var planTotalProgressPercent = 0;
	var realTotalProgressPercent = 0;
	
	//분모 구하기 (WBS종료일 - WBS시작일) 
	for(var i=0; i<subData.cnt; i++){
		planDenominator[i] = dayCalculation(subData.prjPlanStart[i], subData.prjPlanEnd[i]);
	}
	for(var i=0; i<subData.cnt; i++){
		if(nvl(subData.prjRealStart[i], "") != "" && nvl(subData.prjRealEnd[i], "") != ""){
			realDenominator[i] = dayCalculation(subData.prjRealStart[i], subData.prjRealEnd[i]);	
		}
	}
	//분자 구하기 (TODAY - WBS시작일)
	for(var i=0; i<subData.cnt; i++){
		planMolecule[i] = dayCalculation(subData.prjPlanStart[i], today);
	}
	for(var i=0; i<subData.cnt; i++){
		if(nvl(subData.prjRealStart[i], "") != ""){
			realMolecule[i] = dayCalculation(subData.prjRealStart[i], today);
		}
	}
	
	//개별 진행률 구하기
	for(var i=0; i<subData.cnt; i++){
		planTempNum[i] = makeBaseRound((planMolecule[i] / planDenominator[i]) * 100);
		planTempNumTotal[i] = ((planMolecule[i] / planDenominator[i]) * 100); 
		if(0 < planTempNum[i] && planTempNum[i] <= 100){
			planPercentArray[i] = planTempNum[i];
		}else if(planTempNum[i] > 100){
			planPercentArray[i] = 100;
		}else{
			planPercentArray[i] = 0;
		}
	}
	for(var i=0; i<subData.cnt; i++){
		realTempNum[i] = makeBaseRound((realMolecule[i] / realDenominator[i]) * 100);
		realTempNumTotal[i] = ((realMolecule[i] / realDenominator[i]) * 100); 
		if(0 < realTempNum[i] && realTempNum[i] <= 100){
			realPercentArray[i] = realTempNum[i];
		}else if(realTempNum[i] > 100){
			realPercentArray[i] = 100;
		}else{
			realPercentArray[i] = 0;
		}
	}
	//개별 진행률을 총 진행률에 담기
	for(var i=0; i<subData.cnt; i++){
		if(0 < planTempNumTotal[i] && planTempNumTotal[i] <= 100){
			planTotalProgress += planTempNumTotal[i];
		}else if(planTempNumTotal[i] > 100){
			planTotalProgress += 100;
		}
	}
	for(var i=0; i<subData.cnt; i++){
		if(0 < realTempNumTotal[i] && realTempNumTotal[i] <= 100){
			realTotalProgress += realTempNumTotal[i];
		}else if(realTempNumTotal[i] > 100){
			realTotalProgress += 100;
		}
	}
	// 총 진행률 합 / 작업 리스트 개수
	if(planTotalProgress.length != 0){
		planTotalProgressPercent = makeBaseRound(planTotalProgress / subData.cnt);
	}
	if(realTotalProgress.length != 0){
		realTotalProgressPercent = makeBaseRound(realTotalProgress / subData.cnt);
	}
	//개별 계획 진행률 입력
	for(var i=0; i<subData.cnt; i++){
			$('.planProgressList').each(function(i){
				$(this).text(planPercentArray[i]+'%');
			});
	}
	for(var i=0; i<subData.cnt; i++){
		$('.realProgressList').each(function(i){
			$(this).text(realPercentArray[i]+'%');
		});
}
	// 계획 총 진행률 입력
	if(0 < planTotalProgressPercent && planTotalProgressPercent <= 100){
		$('.planTotalProgress').text(planTotalProgressPercent+'%');
	}else if(planTotalProgressPercent > 100){
		$('.planTotalProgress').text('100%');
	}else{
		$('.planTotalProgress').text('0%');
	}
	if(0 < realTotalProgressPercent && realTotalProgressPercent <= 100){
		$('.realTotalProgress').text(realTotalProgressPercent+'%');
	}else if(realTotalProgressPercent > 100){
		$('.realTotalProgress').text('100%');
	}else{
		$('.realTotalProgress').text('0%');
	}
}

//날짜 차이 일수 구하기
function dayCalculation(start, end){
	var tempStart = start.split("-");
	var tempEnd = end.split("-");
	
	var starts = new Date(tempStart[0], tempStart[1]-1, tempStart[2]);
	var ends = new Date(tempEnd[0], tempEnd[1]-1, tempEnd[2]);
	
	return ((ends.getTime() - starts.getTime()) / (1000*60*60*24)) + 1;
}




/*부가 기능*/

//숫자 5 -> 05 변경
function numFormat(num) {
	var str = ''+num;
	if(num<10 && str.indexOf('0') == -1) {
		str = '0'+num;
	}
	return str;
}

//배열로 반환한다.
function byNameArray(data) {
	var dataArray = new Array();
	for(var i=0; i<data.length; i++) {
		dataArray[i] = data[i].value;
	}
	return dataArray;
}

//parse a date in yyyy-mm-dd format
function parseDate(input) {
  var parts = input.match(/(\d+)/g);
  // new Date(year, month [, date [, hours[, minutes[, seconds[, ms]]]]])
  return new Date(parts[0], parts[1]-1, parts[2]); // months are 0-based
}

//totalDays 문자열을 쪼개서 날짜만큼 inPrjTotalDays를 만들어 둠.
function totalDaysAnalysis(data, cnt) {
	var tag = new StringBuffer();
	for(var i=0; i<cnt; i++) {
			tag.append("<td style='display: none;'><input type='hidden' name='inPrjTotalDays' value='0'></td>");
		}
	return tag.toString();
}

//undefined 처리
function nvl(str, defaultStr){
    
    if(typeof str == "undefined" || str == null || str == "")
        str = defaultStr ;
    return str ;
}

//전체체크
function checkAll(bool) {
	var chkVal = document.getElementsByName("chkVal");
	for (var i = 0; i < chkVal.length; i++) {
		chkVal[i].checked = bool;
	}
}

/*부서에 따라 wbs와 달력 show or hide*/
$(function(){
	if($('#sessionDeptName').val() =="개발부"){
		$('#tbody').show();
		$('#thead').show();
		$('#tfooter').show();
		$('#viewCalendarBtn').show();
	}else if($('#sessionRanks').val() =="대표"||$('#sessionRanks').val() == "이사"){
		$('#tbody').show();
		$('#thead').show();
		$('#tfooter').show();
		$('#viewCalendarBtn').show();
	}else{
		$('#tbody').hide();
		$('#thead').hide();
		$('#tfooter').hide();
		$('#viewCalendarBtn').hide();
	}
})

/*직급과 프로젝트 완료 여부에 따라 wbs저장, 삭제 Btn */
$(function(){
	if($('#sessionDeptName').val() =="개발부" && $('#sessionRanks').val() == "부장"){
		if($('#prjCompletion').val() > 0){
			$('#insertProjectWbsListBtn').hide();
			$('#checkListRemoveBtn').hide();
		}else{
			$('#insertProjectWbsListBtn').show();
			$('#checkListRemoveBtn').show();
		}
	}else if($('#sessionRanks').val() =="대표"||$('#sessionRanks').val() == "이사"){
		if($('#prjCompletion').val() > 0){
			$('#insertProjectWbsListBtn').hide();
			$('#checkListRemoveBtn').hide();
		}else{
			$('#insertProjectWbsListBtn').show();
			$('#checkListRemoveBtn').show();
		}
	}else{
		$('#insertProjectWbsListBtn').hide();
		$('#checkListRemoveBtn').hide();
	}
})

	


/*달력 기능 */

//프로젝트 작업 목록 달력 모달
function viewCalendarModal(){
		$("#viewCalendarModal").modal();
	}

//프로젝트 작업 목록 달력에 값 붙이기
$(function(){
	var calendarEl = document.getElementById('calendar');
	  
	var calendar = new FullCalendar.Calendar(calendarEl, {
		plugins: [ 'interaction', 'dayGrid', 'timeGrid', 'list' ],
	  	header: {
    		left: 'prev,next today',
    		center: 'title',
    		right: 'dayGridMonth, listMonth'
    	},
    	defaultView: 'dayGridMonth',
    	defaultDate: new Date(),
    	locale: 'ko',
    	navLinks: true, // can click day/week names to navigate views
    	businessHours: true, // display business hours
    	editable: false,
  });
	
	var prjCode = $('#prjCode').val();
	
	$.ajax({
			type: "post",
			url: "/user/selectProjectWbsOnCalendarAjax",
			data: {prjCode : prjCode},
			dataType: "json",
			success:function(projectWbsList){
				for(var i in projectWbsList){
					if(projectWbsList[i].prjDepth==0) var color = "#ff0000";
					if(projectWbsList[i].prjDepth==1) var color = "#ff6000";
					if(projectWbsList[i].prjDepth==2) var color = "#ff9600";
					if(projectWbsList[i].prjDepth==3) var color = "#ffd200";
					if(projectWbsList[i].prjDepth==4) var color = "#ffe400";
					if(projectWbsList[i].prjDepth==5) var color = "#c1ee0c";
					if(projectWbsList[i].prjDepth==6) var color = "#66ee0c";
					if(projectWbsList[i].prjDepth==7) var color = "#0cee76";
					if(projectWbsList[i].prjDepth==8) var color = "#0c91ee";
					if(projectWbsList[i].prjDepth==9) var color = "#0c6cee";
					calendar.addEvent({
		            	title: projectWbsList[i].prjWorkName,
		                start: projectWbsList[i].prjPlanStart,
		                end: projectWbsList[i].prjPlanEnd,
		                color: color
		            });
				}
			}
		});
	  calendar.render();
})