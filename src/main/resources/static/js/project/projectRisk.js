
/*프로젝트 변경 시 위험관리대장 재출력 Ajax*/
$(function(){
	$("#projectCategory").change(function(){
		var prjCode = $("#projectCategory").val();
		$.ajax({
			url: "",
			type: "post",
			data: {
				prjCode : prjCode
			},
			success:function(){
				location.href="/user/selectProjectRiskListByPrjName?prjCode="+prjCode;
			}
		})
	})
})

/*위험관리대장 삭제 컨펌*/
function deleteProjectRisk(){
	var comfirmChk = confirm("위험관리대장을 삭제하시겠습니까?");
	var rskCode = $("#rskCode").val();
	if(comfirmChk){
		location.href="/user/deleteProjectRisk?rskCode="+rskCode;
	}
}

/*위험관리대장 글 작성 취소 컨펌*/
function cancelInsertProjectRisk(){
	var confirmChk = confirm("위험관리대장 작성을 취소하시겠습니까?");
	
	if(confirmChk){
		location.href="/user/selectProjectRiskList";
	}
}

/*수정 정규식 체크 후 submit*/
function updateProjectRisk(){
	var rskTitle = $("#rskTitle").val();
	var rskContent = $("#rskContent").val();
	var rskSolution = $("#rskSolution").val();
	var rskResult = $("#rskResult").val();
	
	if(rskTitle == ''){
		alert("위험관리 제목을 입력해주세요");
		return false;
	}else if(rskContent == ''){
		alert("위험관리 내용을 입력해주세요");
		return false;
	}else if(rskSolution == ''){
		alert("위험관리 해결방안을 입력해주세요");
		return false;
	}else if(rskResult == ''){
		alert("위험관리 처리결과를 입력해주세요");
		return false;
	}
	
	$("#updateProjectRiskUpdateForm").submit();
}

/*작성 정규식 체크 후 submit*/
function insertProjectRisk(){
	var rskTitle = $("#rskTitle").val();
	var rskContent = $("#rskContent").val();
	var rskSolution = $("#rskSolution").val();
	var rskResult = $("#rskResult").val();
	
	if(rskTitle == ''){
		alert("위험관리 제목을 입력해주세요");
		return false;
	}else if(rskContent == ''){
		alert("위험관리 내용을 입력해주세요");
		return false;
	}else if(rskSolution == ''){
		alert("위험관리 해결방안을 입력해주세요");
		return false;
	}else if(rskResult == ''){
		alert("위험관리 처리결과를 입력해주세요");
		return false;
	}
	
	$("#insertProjectRiskUpdateForm").submit();
}

/*위험관리대장 전체체크*/
function riskCheckAll(bool) {
	var chkVal = document.getElementsByName("riskAllCheckBox");
	for (var i = 0; i < chkVal.length; i++) {
		chkVal[i].checked = bool;
	}
}

/*위험관리 대장 개별 체크 박스 체크시 전체 체크 해제*/
$(function(){
	$(".riskCheckBox").click(function(){
		$("#riskAllCheckBox").prop("checked",false);
	})
})

/*위험관리대장 체크 삭제 */
function deleteProjectRiskAjax(){
	var confirmChk = confirm("위험관리대장을 삭제하시겠습니까?");
	if(confirmChk){
		var rskCodeArr = new Array();
		$("input[class='riskCheckBox']:checked").each(function(){
			rskCodeArr.push($(this).val());
		})
		$.ajax({
			url: "/user/deleteProjectRiskAjax",
			type: "post",
			data: {
				rskCodeArr : rskCodeArr
			},
			success:function(){
				location.href="/user/selectProjectRiskList";
				alert("위험관리대장이 삭제 되었습니다.");
			}
		})
	}
}

/*위험관리대장 체크박스 show or hide*/
$(function(){
	if($('#sessionDeptName').val() == '개발부'){
		$('#insertProjectRiskBtn').show();
		if($('#sessionRanks').val() != '부장'){
			$('#deleteProjectRiskBtn').hide();
			$('#riskAllCheckBox').hide();
			$('.riskCheckBox').hide();
			$('#deleteProjectRiskDetailBtn').hide();
		}
	}else if($('#sessionRanks').val() =="대표" || $('#sessionRanks').val() =="이사"){
		$('#riskAllCheckBox').show();
		$('.riskCheckBox').show();
		$('#deleteProjectRiskBtn').show();
		$('#insertProjectRiskBtn').show();
		$('#deleteProjectRiskDetailBtn').show();
		$('#updateProjectRiskBtn').show();
	}else{
		$('#riskAllCheckBox').hide();
		$('.riskCheckBox').hide();
		$('#deleteProjectRiskBtn').hide();
		$('#insertProjectRiskBtn').hide();
		$('#deleteProjectRiskDetailBtn').hide();
	}
	
	if($('#sessionId').val() == $('#rskWriter').val()){
		$('#updateProjectRiskBtn').show();
	}else if($('#sessionDeptName').val() == '개발부' && $('#sessionRanks').val() == '부장'){
		$('#updateProjectRiskBtn').show();
	}else if($('#sessionRanks').val() =="대표" || $('#sessionRanks').val() =="이사"){
		$('#updateProjectRiskBtn').show();
	}else{
		$('#updateProjectRiskBtn').hide();
	}
})


