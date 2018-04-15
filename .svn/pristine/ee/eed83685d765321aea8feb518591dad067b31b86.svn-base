<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/js/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="<%=path%>/js/themes/demo.css" />
<title>新增规则</title>
<style type="text/css">
body, html, #allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

.condition {
	width: 100%;
	border: 0px solid;
}

* {
	margin: 0;
	padding: 0;
	list-style-type: none;
}

a, img {
	border: 0;
}

body {
	font: 12px/180% Arial, Helvetica, sans-serif, "新宋体";
}

.selectbox .select-bar {
	padding: 0 20px;
}

.selectbox .select-bar select {
	width: 170px;
	height: 200px;
	border: 4px #A0A0A4 outset;
	padding: 4px;
}

.selectbox .btn {
	width: 50px;
	height: 30px;
	margin-top: 10px;
	cursor: pointer;
}
</style>
</head>
<body style="padding: 3px; border: 0px">

	<div class="easyui-tabs" id="pointTabs"
		style="width: 280px; height: 540px; float: left;">
		<div title="填写规则信息" style="padding: 10px" data-options="selected:true">
			<input type="hidden" id="plan_ids" />
			<form id="form" action="" method="post">
				<table class="condition" style="width: 100%;">
					<tr>
						<td width="10%">地市：<span style="color: red;">*</span></td>
						<td width="20%">
							<select id="area_id" class="condition-select" onchange="getSonAreaList()">
									<option value="">
										请选择
									</option>
									<c:forEach items="${areaList}" var="area">
										<option value="${area.AREA_ID}">${area.NAME}</option>
									</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td width="10%">区县：<span style="color: red;">*</span></td>
						<td width="20%">
							<select name="SON_AREA_ID" id="son_area_id" class="condition-select" onchange="getRule()">
									<option value="">
										请选择
									</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>计划类型：<span style="color: red;">*</span></td>
						<td>
							<select id="plan_type" name="plan_type"
								class="condition-select" onchange="getRule()">
									<option value="">请选择</option>
									<option value="1">承包人检查</option>
									<option value="2">网格质量检查员检查</option>
									<option value="3">市县公司专业中心</option>
									<option value="4">市公司资源中心</option>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div class="btn-operation-container">
								<div style="margin-left: 10%;" class="btn-operation"
									onClick="save()">保存</div>
								<div style="margin-left: 5%;" class="btn-operation"
									onClick="back()">取消</div>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	
	</div>
	<div class="easyui-tabs"
		style="width: 250px; height: 540px; float: left;">
		<div title="派发规则" style="padding: 10px" data-options="selected:true">
				<table class="condition" style="width: 250px;" id="rule_table">
					
				</table>
		</div>
	
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
		});

		function save() {
			var plan_ids = $("#plan_ids").val();
			var planId = plan_ids.split(",");
			var key_values;
			
			for(var i=0;i<planId.length;i++){
				if($("#"+planId[i]+"").val()!="" && $("#"+planId[i]+"").val()!=null){
					if(!isNaN($("#"+planId[i]+"").val())){
						if (!isInt($("#"+planId[i]+"").val())) {
							$.messager.show({
								title : '提  示',
								msg : '部分数据不合要求，应为非负整数',
								showType : 'show'
							});
							return;
						}
					}else{
						$.messager.show({
							title : '提  示',
							msg : '请输入数字',
							showType : 'show'
						});
						return;
					}
				}
				if(i==0){
					key_values=$("#"+planId[i]+"").val()==""?'0':$("#"+planId[i]+"").val();
				}else{
					key_values += $("#"+planId[i]+"").val()==""?','+'0':','+$("#"+planId[i]+"").val();
				}
			}
			$.ajax({
				type : 'POST',
				url : webPath + "/patrolPlan/updateRule.do",
				data : {
					plan_ids : plan_ids,
					key_values : key_values
				},
				dataType : 'json',
				success : function(json) 
				{
					var result = json.result;
					if ('success' == result) {
						$.messager.show({
							title : '提  示',
							msg : '更新规则成功!',
							showType : 'show'
						});
						location.href = 'ruleIndex.do';
					} else if ("fail" == result) {
						$.messager.show({
							title : '提  示',
							msg : '更新规则失败!',
							showType : 'show'
						});
					}
				}
			});
		}

		//非负整数
		function isInt(str) {
			var g = /^[1-9]\d*|0$/;
			return g.test(str);
		}
		function back() {
			location.href = 'ruleIndex.do';
		}
		
		//获取区域
		function getSonAreaList() {
			var areaId = $("#area_id").val(); 	
			$.ajax({
				type : 'POST',
				url : webPath + "General/getSonAreaList.do",
				data : {
					areaId : areaId
				},
				dataType : 'json',
				success : function(json) 
				{		
					var result = json.sonAreaList;
					$("select[name='SON_AREA_ID'] option").remove();
					$("select[name='SON_AREA_ID']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
						$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");					
					}
				}
			});
			getRule();
		}
		
		function getRule(){
			var plan_type = $("#plan_type").val();
			var area_id = $("#area_id").val();
			var son_area_id = $("#son_area_id").val();
			
			if(null == plan_type || "" == plan_type || null == area_id || "" == area_id || null == son_area_id || "" == son_area_id){
				$("#rule_table").html("");
				return ;
			}
			$.ajax({
				type : 'POST',
				url : webPath + "patrolPlan/getRule.do",
				data : {
					plan_type : plan_type,
					areaId : area_id,
					sonAreaId : son_area_id
				},
				dataType : 'json',
				success : function(json) 
				{
					var result = json.ruleList;
					$("#rule_table").html("");
					var plan_ids;
					for ( var i = 0; i < result.length; i++) {
						if(i==0){
							plan_ids = result[i].PLAN_ID;
						}else{
							plan_ids += ','+result[i].PLAN_ID;
						}
						
						$("#rule_table").append("<tr>"
							+"<td width='170px;'>"+result[i].KEY_NAME+":</td>"
							+"<td>"
							+"<div class='condition-text-container' style='width:50px;'>"
							+"<input type='text' id='"+result[i].PLAN_ID+"' class='condition-text' value='"+result[i].KEY_VALUE+"' />"
							+"</div>"
							+"</td>"
							+"</tr>");
						
						if('1002'==result[i].KEY_NO||'1003'==result[i].KEY_NO||'1004'==result[i].KEY_NO
								||'2015'==result[i].KEY_NO||'3009'==result[i].KEY_NO||'4011'==result[i].KEY_NO
									||'2002'==result[i].KEY_NO||'3002'==result[i].KEY_NO||'4002'==result[i].KEY_NO){
							$("#"+result[i].PLAN_ID).attr("readOnly","true");
						}
					}
					 $("#plan_ids").val(plan_ids);
				}
			});
		}
		
	</script>
</body>
</html>