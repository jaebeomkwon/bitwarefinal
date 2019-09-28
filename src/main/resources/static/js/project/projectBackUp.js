//일정체크 이벤트
function scheduleEvent() {
	$('input[name=inPrjTotalDays]').parent('td').off().click(function(){
		if($(this).find('input').val() == 0) {
			$(this).css('background-color','yellow').find('input').attr('value', 1);
		}else {
			$(this).css('background-color', '').find('input').attr('value', 0);
		}
	});
}


//화면에 통계를 그려준다
function progress() {
	var cntListArray = new Array();
	for(var i=0; i<subData.cnt; i++) { // wbs 데이터(행) 개수만큼 cntListArray에 0을 대입
		cntListArray[i] = 0;
	}
	var cntDayArray = new Array();
	var cntDaySumArray = new Array();
	for(var i=0; i<baseData.schedule(); i++) { // 프로젝트 일수만큼 cntDatArray, cntDaySumArray에 0을 대입
		cntDayArray[i] = 0;
		cntDaySumArray[i] = 0;
	}
	var cnt = 0; // cntListArray에 쓸 cnt 선언
	$('input[name=inPrjTotalDays]').each(function(i){ // inPrjTotalDays의 개수만큼(i라고 설정) each 돌림
		if(i%baseData.schedule() == baseData.schedule()-1) { // 작업리스트 첫째줄은 0 둘째줄부터 cnt++; 만약 subData
			cnt++;
		}
		if($(this).val() == '1') { // inPrjTotalDays[i]의 value가 1인 경우
			cntListArray[cnt]++; // if cnt = 1 / cntListArray[1]++; cntListArray[1] value = 1+ 
			cntDayArray[i%baseData.schedule()]++;
		}
	});
	for(var i=0; i<baseData.schedule(); i++) {
		if(i==0) {
			cntDaySumArray[i] = cntDayArray[i];
		}else {
			cntDaySumArray[i] = cntDaySumArray[i-1] + cntDayArray[i];
		}
	}
	var sumTerm = 0;
	for(var i=0; i<subData.cnt; i++) { // wbs데이터 개수만큼 for문 진행
		sumTerm += cntListArray[i]; 
	}
	var percent = makeRound(sumTerm); // sumTerm 의 소수점 정리 후 percent에 대입
	var oldPercent = 100 / sumTerm; 
	$('input[name=inPrjTotalDays]').each(function(){
		if($(this).val() == '1') {
			$(this).parent('td').html(percent+"%<input type='hidden' name='inPrjsTotalDays' value='1'>");
		}else {
			$(this).parent('td').html("<input type='hidden' name='inPrjTotalDays' value='0'>");
		}
	});
	if(isFinite(percent)) { // isFinite(a) a가 유한한 수면 true 아닌 경우(문자 포함) false를 반환
		$('.progressList').each(function(i){ 
			$(this).text(makeBaseRound(cntListArray[i]*oldPercent)+'%');
		}).parents('table').find('.progressDay').each(function(i){
			$(this).text(makeBaseRound(cntDayArray[i]*oldPercent)+'%');
		}).parents('thead').find('.progressDaySum').each(function(i){
			$(this).text(makeBaseRound(cntDaySumArray[i]*oldPercent)+'%');
		});
		$('#thead tr:eq(5) td:eq(9)').text(makeBaseRound(cntDaySumArray[cntDaySumArray.length-1]*oldPercent)+'%');
	}else {
		$('.progressList').each(function(i){
			$(this).text('0%');
		}).parents('table').find('.progressDay').each(function(i){
			$(this).text('0%');
		}).parents('thead').find('.progressDaySum').each(function(i){
			$(this).text('0%');
		});
		$('#thead tr:eq(5) td:eq(9)').text('0%');		
	}
}

//totalDays 문자열을 쪼개서 값을 검사한다. (1인 곳은 bgcolor yellow, value = 1) 
function totalDaysAnalysis(data, cnt) {
	var tag = new StringBuffer();
	for(var i=0; i<cnt; i++) {
		if(data.charAt(i)=='1') {
			tag.append("<td style='background-color: yellow;'><input type='hidden' name='inPrjTotalDays' value='1'></td>");
		}else {
			tag.append("<td><input type='hidden' name='inPrjTotalDays' value='0'></td>");
		}
	}
	return tag.toString();
}





//배열을 받아 태그로 만들어준다.
function tagMonthOne(dates) {
	var tag = new StringBuffer();
	for(var i=0; i<dates.length; i++) {
		tag.append(tdTagFormatDay(dates[i]));
	}
	return tag.toString();
}

//매개변수로 받은 월부터 마지막 12까지
function LastMonths(year, month) {
	var tag = new StringBuffer();
	for(var i=Number(month); i<=12; i++) {
		tag.append(tagMonthOne(calendar.makeOne(year, i)));
	}
	return tag.toString();
}
//매개변수로 받은 월이하 1이상
function startMonths(year, month) {
	var tag = new StringBuffer();
	for(var i=1; i<=Number(month); i++){
		tag.append(tagMonthOne(calendar.makeOne(year, i)));
	}
	return tag.toString();
}

//달력의 해당 날짜의 요일을 구하기위해 현재위치 반환
function monthDayIndex(month, day) {
	for(var i=0; i<month.length; i++) {
		if(month[i]==day) {
			return i;
		}
	}
}
//시작월부터 종료월의 colspan값을 구해서 td 반환
function yearMonth(year, startMonth, lastMonth) {
	var tag = new StringBuffer();
	if(calendar.iscLeafCheck(year)) {
		for(var i=Number(startMonth)-1; i<Number(lastMonth); i++) {
			tag.append(tdTagFormatMonth(calendar.LEAF[i], year, i+1));
		}
	}else {
		for(var i=Number(startMonth)-1; i<Number(lastMonth); i++) {
			tag.append(tdTagFormatMonth(calendar.PLAIN[i], year, i+1));
		}
	}
	return tag.toString();
}

//td 월 한칸 포맷
function tdTagFormatMonth(colspan, year, month) {
	return "<td class='removeThead' colspan='"+colspan+"'>"+year+"-"+numFormat(month)+"</td>";
}
//td 주차 한칸 포맷
function tdTagFormatWeek(colspan, week) {
	return "<td class='removeThead' colspan='"+colspan+"'>"+week+"주차</td>";
}
//td 날짜 한칸 포맷
function tdTagFormatDay(day) {
	return "<td class='removeThead'><div style='width: 60px;'>"+numFormat(day)+"</div></td>";
}

//테이블 thead 화면에 그리는 함수
function screenWriteThead() {
	// 월처리
	var tag = new StringBuffer();
	if(baseData.start[0] != baseData.end[0]) {
		
		if(calendar.iscLeafCheck(baseData.start[0])) { //윤년 판단 윤년이면 true 반환
			var startMonthCnt = calendar.LEAF[baseData.start[1]-1] - baseData.start[2] + 1;
			tag.append(tdTagFormatMonth(startMonthCnt, baseData.start[0], baseData.start[1])); // td 월 한 칸 포맷
		}else { // 평년인 경우
			var startMonthCnt = calendar.PLAIN[baseData.start[1]-1] - baseData.start[2] + 1;
			tag.append(tdTagFormatMonth(startMonthCnt, baseData.start[0], baseData.start[1]));
		}
		tag.append(yearMonth(baseData.start[0], Number(baseData.start[1])+1, 12)); //시작월부터 종료월의 colspan값을 구해서 td반환
		if(Number(baseData.end[0])-Number(baseData.start[0]) > 1) {
			for(var i=Number(baseData.start[0])+1; i<Number(baseData.end[0]); i++) {
				tag.append(yearMonth(i, 1, 12)); //시작월부터 종료월의 colspan값을 구해서 td 반환 (year, startMonth, endMonth)
			}
		}
		tag.append(yearMonth(baseData.end[0], 1, Number(baseData.end[1])-1));
		if(calendar.iscLeafCheck(baseData.end[0])) {
			tag.append(tdTagFormatMonth(baseData.end[2], baseData.end[0], baseData.end[1]));
		}else {
			tag.append(tdTagFormatMonth(baseData.end[2], baseData.end[0], baseData.end[1]));
		}
	}else if(baseData.start[1] == baseData.end[1]) {
		tag.append(tdTagFormatMonth(baseData.schedule(), baseData.start[0], baseData.start[1]));
	}else {
		if(calendar.iscLeafCheck(baseData.start[0])) {
			var startMonthCnt = calendar.LEAF[baseData.start[1]-1] - baseData.start[2] + 1;
			tag.append(tdTagFormatMonth(startMonthCnt, baseData.start[0], baseData.start[1]));
			tag.append(yearMonth(baseData.start[0], Number(baseData.start[1])+1, Number(baseData.end[1])-1));
			tag.append(tdTagFormatMonth(baseData.end[2], baseData.end[0], baseData.end[1]));
		}else {
			var startMonthCnt = calendar.PLAIN[baseData.start[1]-1] - baseData.start[2] + 1;
			tag.append(tdTagFormatMonth(startMonthCnt, baseData.start[0], baseData.start[1]));
			tag.append(yearMonth(baseData.start[0], Number(baseData.start[1])+1, Number(baseData.end[1])-1));
			tag.append(tdTagFormatMonth(baseData.end[2], baseData.end[0], baseData.end[1]));
		}
	}
	$("#thead tr:eq(0)").append(tag.toString());
	// 주차처리
	tag = new StringBuffer();
	var startDayIndex = monthDayIndex(calendar.make(baseData.start[0], baseData.start[1]), baseData.start[2]);
	var lastDayIndex = monthDayIndex(calendar.make(baseData.end[0], baseData.end[1]), baseData.end[2]);
	var startDayOfWeek = 7 - startDayIndex % 7;
	var lastDayOfWeek = lastDayIndex % 7;
	var remain = (baseData.schedule() - startDayOfWeek) / 7;
	tag.append(tdTagFormatWeek(startDayOfWeek, 1));
	for(var i=2; i<remain+2; i++) {
		tag.append(tdTagFormatWeek(7, i));
	}
	$("#thead tr:eq(1)").append(tag.toString());
	// 날짜처리
	var tag = new StringBuffer();
	if(baseData.start[0] != baseData.end[0]) {
		for(var i=Number(baseData.start[2]); i<=calendar.lastDay(baseData.start[0], baseData.start[1]); i++) {
			tag.append(tdTagFormatDay(i));
		}
		if(Number(baseData.end[0])-Number(baseData.start[0]) > 1) {
			tag.append(LastMonths(baseData.start[0], Number(baseData.start[1])+1));
			for(var i=Number(baseData.start[0])+1; i<Number(baseData.end[0]); i++) {
				tag.append(startMonths(i, 12));
			}
			tag.append(startMonths(baseData.end[0], Number(baseData.end[1])-1));
		}else {
			tag.append(LastMonths(baseData.start[0], Number(baseData.start[1])+1));
			tag.append(startMonths(baseData.end[0], Number(baseData.end[1])-1));
		}
		for(var i=1; i<=Number(baseData.end[2]); i++) {
			tag.append(tdTagFormatDay(i));
		}
	}else if(baseData.start[1] == baseData.end[1]) {
		for(var i=Number(baseData.start[2]); i<=Number(baseData.end[2]); i++){
			tag.append(tdTagFormatDay(i));
		}
	}else {
		var lastDay = calendar.lastDay(baseData.start[0], baseData.start[1]);
		for(var i=Number(baseData.start[2]); i<=lastDay; i++) {
			tag.append(tdTagFormatDay(i));
		}
		for(var i=Number(baseData.start[1])+1; i<Number(baseData.end[1]); i++) {
			tag.append(tagMonthOne(calendar.makeOne(baseData.start[0], i)));
		}
		for(var i=1; i<=Number(baseData.end[2]); i++){
			tag.append(tdTagFormatDay(i));
		}
	}
	$("#thead tr:eq(2)").append(tag.toString());
	
	// 총, 일간 진척률 처리
	tag = new StringBuffer();
	for(var i=0; i<baseData.schedule(); i++) {
		tag.append("<td class='removeThead progressDaySum'></td>"); // 총 진척률
	}
	$("#thead tr:eq(3)").append(tag.toString());
	
	tag = new StringBuffer();
	for(var i=0; i<baseData.schedule(); i++) {
		tag.append("<td class='removeThead progressDay'></td>"); // 일간 진척률
	}
	$("#thead tr:eq(4)").append(tag.toString());
	
	tag = new StringBuffer();
	for(var i=0; i<baseData.schedule(); i++) {
		tag.append("<td class='removeThead'></td>"); // 일간 진척률 아랫칸
	}
	$("#thead tr:eq(5)").append(tag.toString());
	
}