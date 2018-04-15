<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<style type="text/css">
	.condition-container {
		height: 48px;
		padding-left: 26px;
		padding-top: 15px;
		width: 80%;
	}
	
	.btn-operation-container {
		height: 42px;
		padding-left: 26px;
		padding-right: 36px;
		padding-top: 10px;
		width: 80%;
	}

	.datagrid-pager {
		border-style: solid;
		border-width: 0px;
		margin: 0px 20px;
		width:80%;
	}
</style>
<title>新增机房</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="roomId" >
			<input type="hidden" name="check_item_id" value="${item.CHECK_ITEM_ID}"/>
			<table style="width: 100%;">
				<tr>
					<td width="10%;"><label>检查项名称：</label> </td>
					<td width="20%">
						<div class="condition-text-container">
						<input name="check_item_name" class="condition-text easyui-validatebox condition"
						type="text" data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" value="${item.CHECK_ITEM_NAME}" />
						</div>
					</td>
					<td width="10%;"><label>机房类型：</label></td>
					<td width="20%">
					<select name="room_type_id" class="condition-select condition"
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
						<c:forEach items="${roomTypes}" var="v">
							<option  <c:if test="${v.ROOM_TYPE_ID==item.ROOM_TYPE_ID}">selected</c:if>  value="${v.ROOM_TYPE_ID}">${v.ROOM_TYPE_NAME}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td width="10%;"><label>检查内容：</label></td>
					<td width="20%">
						<div class="condition-text-container">
							<input id="check_item_name" name="check_item_content" type="text" class="condition-text easyui-validatebox condition"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" value="${item.CHECK_ITEM_CONTENT}"/>
						 </div>
					</td>
					<td width="10%;"><label>是否拍照：</label></td>
					<td width="20%">
					 	<select name="check_type" class="condition-select condition"
							data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" >
							<option <c:if test="${0==item.CHECK_TYPE}">selected</c:if> value="0">是</option>
							<option <c:if test="${1==item.CHECK_TYPE}">selected</c:if> value="1">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="10%;"><label>修改者：</label></td>
					<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text easyui-validatebox condition" type="text" name="modify_by"  width="100%;" readonly="readonly" value="${staffNo}"/>
					</div>
					</td>
					<td width="10%;"><label>修改时间：</label></td>
					<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text easyui-validatebox condition" type="text" name="modify_date" readonly="readonly"  value="${currentDate}"/>
					</div>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:center;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>
			<div class="btn-operation" onClick="history.back();">返回</div>
			<div class="btn-operation" onClick="addTroubleType()">增加隐患类型</div>
		</div>
	</div>
		<div  style="padding:20px 0 10px 50px">
		<input type="hidden" name="trouble_type_name" value="" />
		<input type="hidden" name="trouble_type_id" value="" />
		<table id="tb" style="width: 50%;">
			<tr>
				<td width="30%" align="left" style="padding-left:50px">隐患类型</td>
				<td width="20%" align="left" style="padding-left:40px">操作</td>
			</tr>
			<c:forEach items="${item.troubleTypes}" var="v">
				<tr>
					<td>
					<input type="hidden" value="${v.TROUBLE_TYPE_ID}"/>
					<input type='text' value="${v.TROUBLE_TYPE_NAME}" disabled="disabled"/>
					</td>
					<td><div class='btn-operation' onClick='deleteRow(this);'>删除</div></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div id="win_staff"></div>
	<script type="text/javascript">
		function addTroubleType()
		{
			var showTr="<tr><td><input type='text' /></td><td><div class='btn-operation' onClick='deleteRow(this);'>删除</div></td></tr>";
			$("#tb").append(showTr);
		}
		
		function deleteRow(obj){
		    var table=document.getElementById('tb'); 
	   		table.deleteRow(obj.parentNode.parentNode.rowIndex);
		}
		
		function saveForm(){
	
		if($("#roomId").form('validate')){
			$.messager.confirm('系统提示', '您确定要保存该检查项吗?', function(r) {
				if(r){
					var check_item_id = $("input[name='check_item_id']").val();
					var check_item_name = $("input[name='check_item_name']").val();
					var room_type_id = $("select[name='room_type_id']").val();
					var check_item_content = $("input[name='check_item_content']").val();
					var modify_by = $("input[name='modify_by']").val();
					var modify_date = $("input[name='modify_date']").val();
					var check_type = $("select[name='check_type']").val();
					
					//拼装隐患名称和隐患ID
					setTroubleTypes();
					var trouble_type_name =  $("input[name='trouble_type_name']").val();
					var trouble_type_id = $("input[name='trouble_type_id']").val();
					
					$.ajax({
						type : 'POST',
						url : webPath + "/checkItem/update.do",
						data : {
							check_item_id : check_item_id,
							check_item_name : check_item_name,
							room_type_id : room_type_id,
							check_item_content : check_item_content,
							modify_date : modify_date,
							modify_by : modify_by,
							check_type : check_type,
							trouble_type_name : trouble_type_name,
							trouble_type_id : trouble_type_id
						},
						dataType : 'json',
						success : function(json) {
							if (json.status) {
								$.messager.show({
									title : '提  示',
									msg : '保存检查项成功!',
									showType : 'show'
								});
							}
							location.href = 'index.do';
						}
					});
				}
			});
		}
	}
		
	function setTroubleTypes(){
			var tab=document.getElementById("tb"); 
			var rows = tab.rows.length;
			
			var value= new Array();
			var id= new Array();
			for(var i = 1; i < rows; i++){  
		     	var inputs = tab.rows[i].getElementsByTagName("input");
		        for(var m = 0; m < inputs.length; m++){
		        	
		        	if(inputs[m].type=="hidden"){
		        	  	id[id.length]=inputs[m].value;
		        	}else{
			        	if(inputs[m].value!=""){
			        		value[value.length]=inputs[m].value;
			        	}
		        	}
		    
		        }
			}
			
			$("input[name='trouble_type_name']").val(value);
			$("input[name='trouble_type_id']").val(id);
	}
</script>
</body>
</html>