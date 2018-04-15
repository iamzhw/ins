<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../../util/head.jsp"%>

<title>巡线段列表</title>

</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<table id="tableLineId" style="align-text:left;">
		<c:forEach items="${lineList}" var="al" varStatus="status">
		<tr>
		<td><input type="checkbox" name="lineId" value="${al.LINE_ID}"/></td>
		<td><label>${al.LINE_NAME}</label></td>
		</tr>
		</c:forEach>
		</table>
		<div style="text-align:left;padding:10px 0 10px 0px">
			<div class="btn-operation" onClick="selectLine()">保存</div>
			<div class="btn-operation" onClick="closeTab();">取消</div>
		</div>
	</div>
	<div id="win_task"></div>
	<script type="text/javascript">
	$(document).ready(function(){
		checked();
	});
	
	function checked(){
		var lineIds = $("input[name='lineIds']").val();
		var idArr = lineIds.split(",");
		var selectIds = document.getElementsByName("lineId");
		for(var j=0;j<selectIds.length;j++){
			for(var i=0;i<idArr.length;i++){
				if(idArr[i]==selectIds[j].value){
					selectIds[j].checked=true;
					break;
				}
			}
		}
	}
	 function selectLine(){
		    var lineTableObj = document.getElementById('tableLineId');
		    
		    var idArr = new Array();
		    var nameArr = new Array();
	        $("table :checkbox").each(function(key,value){
	            if($(value).prop('checked')){
	            	idArr[idArr.length] = lineTableObj.rows[key].cells[0].childNodes[0].value;
	            	nameArr[nameArr.length] = lineTableObj.rows[key].cells[1].childNodes[0].innerHTML;
	            }
	        });
	        $("input[name='lineIds']").val(idArr);
	        $("textarea[name='lineNames']").val(nameArr);
	        $('#win_task').window('close');
	 }
	 
	 function closeTab(){
			$('#win_task').window('close');
	 }
	
	</script>
</body>
</html>