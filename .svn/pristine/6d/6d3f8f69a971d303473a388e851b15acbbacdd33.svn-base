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
<title>新增关键点计划</title>
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
		<div title="填写计划信息" style="padding: 10px" data-options="selected:true">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition" style="width: 100%;">
					<tr>
						<td width="10%">地市：<span style="color: red;">*</span></td>
						<td width="20%">
							<select id="show_area" class="condition-select" onchange="getSonAreaList()">
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
							<select name="SON_AREA_ID" id="son_area" class="condition-select" onchange="getGridList()">
									<option value="">
										请选择
									</option>
							</select>
						</td>
					</tr>
					<!-- <tr>
						<td width="10%">网格：</td>
						<td width="20%">
							<select id="whwg" name="WHWG" class="condition-select" onchange="getStaffList()">
									<option value="">
										请选择
									</option>
							</select>
						</td>
					</tr> -->
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
					<!-- <tr id="check_staff_tr">
						<td width="10%">检查人：</td>
						<td width="20%">
							<select id="staff_name" name="STAFF_NAME" class="condition-select" >
									<option value="">
										请选择
									</option>
							</select>
						</td>
					</tr> -->
					
					<tr>
						<td>计划周期：</td>
						<td><select name="plan_circle" class="condition-select">
								<option value="">请选择</option>
								<option value="1">日</option>
								<option value="2">周</option>
								<option value="5">半月</option>
								<option value="3" selected ="selected">月</option>
								<option value="4">年</option>
						</select></td>
					</tr>
					<tr>
						<td>次数：</td>
						<td>
							<!-- <input name="plan_frequency" type="text" class="condition-text" /> -->
							<input name="plan_frequency" class="easyui-numberspinner"
							value="1" data-options="increment:1" style="width: 155px;"></input>
						</td>
					</tr>
					
					<tr>
						<td>开始时间：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text condition" type="text"
									name=plan_start_time id="plan_start_time"
									onClick="WdatePicker();" />
							</div>
						</td>
					</tr>
					<tr>
						<td>结束时间：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text condition" type="text"
									name=plan_end_time id="plan_end_time"
									onClick="WdatePicker({minDate:'#F{$dp.$D(\'plan_start_time\')}'});" />
							</div>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">计划名称：</td>
						<td style="width: 70%;">
							<div class="condition-text-container">
								<input id="plan_name" name="plan_name" type="text"
									class="condition-text" />
							</div>
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
					<tr>
						<td colspan="2">
							<span>备注：标红<span style="color: red;">*</span>部分，确定派发规则！</span>						
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
	<div class="easyui-tabs"
		style="width: 850px; height: 540px; float: left;">
		<div title="设备展示" style="padding: 10px" data-options="selected:true">
			<div class="btn-operation-container" id="toolbar" hidden="true;">
					<div style="float:right;" class="btn-operation" onClick="addMakeSure()">确认</div>
					<div style="float:right;" class="btn-operation" onClick="addEqForPreview()">新增</div>
					<div style="float:right;" class="btn-operation" onClick="delteEqForPreview()">删除</div>
					<div id="btn_grid" style="float:right;" class="btn-operation" onClick="getGridNum()">网格比例</div>
					<div id="btn_eqp_column" style="float:right;display:none" class="btn-operation" onClick="showEqp()">设备列表</div>
			</div>
		    <table id="dg" title="【设备管理】" style="width:98%;height:480px;display:none">				
			</table> 
			<div id="win_eq"></div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
		});

		function save() {
			var plan_name = $.trim($("#plan_name").val());
			var areaId = $("#show_area").val();
			var sonAreaId = $("#son_area").val();
			var whwg = $("#whwg").val();
			var staffId = $("#staff_name").val();
			var plan_type = $.trim($("select[name='plan_type']").val());
			var plan_circle = $.trim($("select[name='plan_circle']").val());
			var plan_frequency = $.trim($("input[name='plan_frequency']").val());
			var plan_start_time = $.trim($("input[name='plan_start_time']").val());
			var plan_end_time = $.trim($("input[name='plan_end_time']").val());
			
			if (null == plan_name || "" == plan_name) {
				$.messager.show({
					title : '提  示',
					msg : '请填写计划名称!',
					showType : 'show'
				});
				return;
			}
			if (null == areaId || "" == areaId) {
				$.messager.show({
					title : '提  示',
					msg : '请选择地市!',
					showType : 'show'
				});
				return;
			}
			if (null == sonAreaId || "" == sonAreaId) {
				$.messager.show({
					title : '提  示',
					msg : '请选择区县!',
					showType : 'show'
				});
				return;
			}
			/* if (null == whwg || "" == whwg) {
				$.messager.show({
					title : '提  示',
					msg : '请选择维护网格!',
					showType : 'show'
				});
				return;
			} */
			/* if(plan_type != 1){
				if (null == staffId || "" == staffId) {
					$.messager.show({
						title : '提  示',
						msg : '请选择检查人!',
						showType : 'show'
					});
					return;
				}
			} */
			
			if (null == plan_type || "" == plan_type) {
				$.messager.show({
					title : '提  示',
					msg : '请填选择计划类型!',
					showType : 'show'
				});
				return;
			}
			if (null == plan_circle || "" == plan_circle) {
				$.messager.show({
					title : '提  示',
					msg : '请填选择计划周期!',
					showType : 'show'
				});
				return;
			}
			if (null == plan_frequency || "" == plan_frequency) {
				$.messager.show({
					title : '提  示',
					msg : '请填写次数!',
					showType : 'show'
				});
				return;
			}
			if (!isPInt(plan_frequency)) {
				$.messager.show({
					title : '提  示',
					msg : '次数应为正整数!',
					showType : 'show'
				});
				return;
			}
			if (null == plan_start_time || "" == plan_start_time) {
				$.messager.show({
					title : '提  示',
					msg : '开始时间不能为空!',
					showType : 'show'
				});
				return;
			}
		
			if (null == plan_end_time || "" == plan_end_time) {
				$.messager.show({
					title : '提  示',
					msg : '结束时间不能为空!',
					showType : 'show'
				});
				return;
			}
			
			$.ajax({
				type : 'POST',
				url : webPath + "/patrolPlan/savePatrolPlan.do",
				data : {
					areaId : areaId,
					sonAreaId : sonAreaId,
					whwg : whwg,
					staffId : staffId,
					plan_name : plan_name,
					plan_type : plan_type,
					plan_circle : plan_circle,
					plan_frequency : plan_frequency,
					plan_start_time : plan_start_time,
					plan_end_time : plan_end_time
				},
				dataType : 'json',
				success : function(data) 
				{
					var resultCode = data.resultCode;
					if (resultCode == "001") {
						$.messager.show({
							title : '提  示',
							msg : data.resultDesc,
							showType : 'show'
						});
						return;
					} else {
						$.messager.show({
							title : '提  示',
							msg : data.resultDesc,
							showType : 'show'
						});
						location.href = 'index.do';
					}
				}
			});
		}

		//1~100正整数
		function isPInt(str) {
			var g = /^([1-9]\d?|100)$/ ;
				//^[1-9]*[1-9][0-9]*$/;
			return g.test(str);
		}
		//整数
		function isInt(str) {
			var g = /^-?\d+$/;
			return g.test(str);
		}
		function back() {
			location.href = 'index.do';
		}
		
		//获取区域
		function getSonAreaList() {
			var areaId = $("#show_area").val(); 	
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
					$("select[name='WHWG'] option").remove();
					$("select[name='WHWG']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
						$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");					
					}
				}
			});
			getRule();
		}
		//获取综合化维护网格
		function getGridList() {
			var areaId = $("#show_area").val();
			var sonAreaId = $("#son_area").val();
			$.ajax({
				type : 'POST',
				url : webPath + "General/getGridList.do",
				data : {
					areaId : areaId,
					sonAreaId : sonAreaId
				},
				dataType : 'json',
				success : function(json) 
				{
					var result = json.gridList;
					$("select[name='WHWG'] option").remove();
					$("select[name='WHWG']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
						$("select[name='WHWG']").append("<option value=" + result[i].GRID_ID + ">"+ result[i].GRID_NAME + "</option>");
					}
				}
			});
			getRule();
		}
		//获取网格下检查人
		function getStaffList(){
			var whwgId = $("#whwg").val();
			$.ajax({
				type : 'POST',
				url : webPath + "patrolPlan/getStaffListByWHWGId.do",
				data : {
					whwgId : whwgId
				},
				dataType : 'json',
				success : function(json) 
				{
					var result = json.staffList;
					$("select[name='STAFF_NAME'] option").remove();
					$("select[name='STAFF_NAME']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
						$("select[name='STAFF_NAME']").append("<option value=" + result[i].STAFF_ID + ">"+ result[i].STAFF_NAME + "</option>");
					}
				}
			});
		}
		//获取规则参数
		function getRule(){
			var areaId = $("#show_area").val();
			var sonAreaId = $("#son_area").val();
			var plan_type = $("#plan_type").val();
			
			if(plan_type == 1){
				$("#check_staff_tr").hide();
			}else{
				$("#check_staff_tr").show();
			}
			
			var myDate = new Date();
			var year = myDate.getFullYear();
			var month = myDate.getMonth()+1;
			var day = myDate.getDate();
			var h = myDate.getHours();
			var min = myDate.getMinutes();
			var s=myDate.getSeconds();
			
			//将计划类型的value值获取到赋给计划名称列
			$('#plan_name').val($("#plan_type option:selected").text()+"_"+year+month+day+h+min+s);
			
			//根据上面3个参数定位到具体的规则
			if(null == areaId || "" == areaId || null == sonAreaId || "" ==sonAreaId || null == plan_type || "" == plan_type){
				return;
			}
			$.ajax({
				type : 'POST',
				url : webPath + "patrolPlan/getRule.do",
				data : {
					areaId : areaId,
					sonAreaId : sonAreaId,
					plan_type : plan_type
				},
				dataType : 'json',
				success : function(json) 
				{
					var result = json.ruleList;
					$("#rule_table").html("");
					for ( var i = 0; i < result.length; i++) {
						$("#rule_table").append("<tr>"
						+"<td width='170px;'>"+result[i].KEY_NAME+":</td>"
						+"<td>"
						+"<div class='condition-text-container' style='width:50px;'>"
						+"<input type='text' class='condition-text' value='"+result[i].KEY_VALUE+"' readonly='readonly'/>"
						+"</div>"
						+"</td>"
						+"</tr>");
						if(i==result.length-1 && result[i].PLAN_TYPE != '1'){
							$("#rule_table").append("<tr><td><div class='btn-operation' value='预览' onClick='showEqByPlanRule()'>预览</div></td></tr>");
						}
					}
				}
			});
		}
		function showEqByPlanRule(){
			var area_id = $("#show_area").val(); 
			var son_area_id = $("#son_area").val();
			var plan_type = $("#plan_type").val();
			var whwg = $("#whwg").val();
			/* if (null == whwg || "" == whwg) {
				$.messager.show({
					title : '提  示',
					msg : '网格不能为空!',
					showType : 'show'
				});
				return;
			} */
			$.messager.progress();
			$.ajax({
				type : 'POST',
				url : webPath + "patrolPlan/saveEqByPlanRule.do",
				data : {
					area_id : area_id,
					son_area_id : son_area_id,
					plan_type : plan_type,
					whwg : whwg
				},
				dataType : 'json',
				success : function(json) 
				{
					$.messager.progress('close');
					var result = json.result;
					if('success' == result){
						$("#toolbar").show();
						searchData();
					}else{
						$.messager.show({
							title : '提  示',
							msg : '预览设备失败!',
							showType : 'show'
						});
					}
				}
			});
			
		}
		
		function searchData(){
				var obj = {
				};
				$('#dg').datagrid({
					//此选项打开，表格会自动适配宽度和高度。
					autoSize : true,
					toolbar : '#tb',
					url : webPath + "patrolPlan/queryEqByPlanRule.do",
					queryParams : obj,
					method : 'post',
					pagination : true,
					pageNumber : 1,
					pageSize : 9,
					pageList : [ 9,20, 50, 500],
					//loadMsg:'数据加载中.....',
					rownumbers : true,
					fit:true,
					singleSelect : false,
					columns : [ [ {
						field : 'ID',
						title : '主键ID',
						checkbox : true
					}, {
						field : 'EQ_ID',
						title : '设备ID',
						width : "8%",
						align : 'center'
					}, {
						field : 'EQ_CODE',
						title : '设备编码',
						width : "10%",
						align : 'center'
					}, {
						field : 'EQ_NAME',
						title : '设备名称',
						width : "10%",
						align : 'center'
					}, {
						field : 'AREA_NAME',
						title : '地市',
						width : "6%",
						align : 'center'
					}, {
						field : 'SON_AREA_NAME',
						title : '区县',
						width : "6%",
						align : 'center'
					},{
						field : 'GRID_NAME',
						title : '网格',
						width : "10%",
						align : 'center'
					},
					{
						field : 'CHECKNAME',
						title : '检查人',
						width : "10%",
						align : 'center'
					}
					] ],
					nowrap : false,
					striped : true,
					onLoadSuccess : function(data) {
						$("body").resize();
					}
				});
		}
		
		
		
		function showEqp(){
			$('#btn_eqp_column').hide();
			$('#btn_grid').show();
			searchData();
		}
		
		//点击网格比例按钮，设备列表页面切换到比例页面
		function getGridNum(){
			$('#btn_grid').hide();
			$('#btn_eqp_column').show();
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "patrolPlan/getGridNum.do",
				queryParams : {},
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 9,
				pageList : [ 9,20, 50, 500],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				fit:true,
				singleSelect : false,
				columns : [ [ {
					field : 'ID',
					title : '主键ID',
					checkbox : true
				},{
					field : 'GRID_NAME',
					title : '网格',
					width : "20%",
					align : 'center'
				},{
					field : 'ALLCOUNT',
					title : '网格总变动量',
					width : "12%",
					align : 'center'
				},{
					field : 'SELECTCOUNT',
					title : '选中设备数量',
					width : "12%",
					align : 'center'
				},{
					field : 'COUNTRATE',
					title : '选中设备数量',
					width : "12%",
					align : 'center'
				}
				] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					$("body").resize();
				}
			});
		}
		
		function addMakeSure(){
			var selected = $('#dg').datagrid('getChecked');
		    var count = selected.length;
		    if (count == 0) {
		        $.messager.show({
		            title: '提  示',
		            msg: '请选择至少一条记录!',
		            showType: 'show'
		        });
		        return;
		    } else {
				var ids;
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].ID;
					if(i==0){
						ids = value;
					}else{
						ids += ","+value;
					}
				}
				$.messager.progress();
				$.ajax({
					type : 'POST',
					url : webPath + "patrolPlan/addSureSelect.do",
					data : {
						ids : ids
					},
					dataType : 'json',
					success : function(json) 
					{
						$.messager.progress('close');
						var result = json.result;
						if('success' == result){
							$.messager.show({
								title : '提  示',
								msg : '确认选择成功!',
								showType : 'show'
							});
							searchData();
						}else if("fail" == result){
							$.messager.show({
								title : '提  示',
								msg : '确认选择失败!',
								showType : 'show'
							});
						}
					}
				});
		    }
		}
		
		function addEqForPreview(){
			var area_id = $("#show_area").val(); 
			var son_area_id = $("#son_area").val();
			var whwg = $("#whwg").val();
			var plan_type = $("plan_type").val();
			
			$('#win_eq').window({
				title : "【选择设备】",
				href : webPath + "patrolPlan/showEqForUpdateForPreview.do?area_id="+area_id+"&son_area_id="+son_area_id+"&whwg="+whwg+"&plan_type="+plan_type,
				width : 620,
				height : 400,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		
		function delteEqForPreview(){
			 var selected = $('#dg').datagrid('getChecked');
			    var count = selected.length;
			    if (count == 0) {
			        $.messager.show({
			            title: '提  示',
			            msg: '请选择至少一条记录!',
			            showType: 'show'
			        });
			        return;
			    } else {
			    	$.messager.confirm('系统提示', '您确定要删除设备吗?', function(r) {
						if(r){
							var ids;
							for ( var i = 0; i < selected.length; i++) {
								var value = selected[i].ID;
								if(i==0){
									ids = value;
								}else{
									ids += ","+value;
								}
							}
							$.messager.progress();
							$.ajax({
								type : 'POST',
								url : webPath + "patrolPlan/deleteEqForPreview.do",
								data : {
									ids : ids
								},
								dataType : 'json',
								success : function(json) 
								{
									$.messager.progress('close');
									var result = json.result;
									if('success' == result){
										$.messager.show({
											title : '提  示',
											msg : '删除设备成功!',
											showType : 'show'
										});
										searchData();
									}else if("fail" == result){
										$.messager.show({
											title : '提  示',
											msg : '删除设备失败!',
											showType : 'show'
										});
									}
								}
							});
						}
						
					})
			       
			    }
		}
	</script>
</body>
</html>