<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/themes/demo.css"/>
<title>新增关键点计划</title>
<style type="text/css">
body,html,#allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

.condition {
    width: 100%;
    border: 0px solid;
}
*{margin:0;padding:0;list-style-type:none;}
a,img{border:0;}
body{font:12px/180% Arial, Helvetica, sans-serif, "新宋体";}

.selectbox .select-bar{padding:0 20px;}
.selectbox .select-bar select{width:150px;height:200px;border:4px #A0A0A4 outset;padding:4px;}
.selectbox .btn{width:50px; height:30px; margin-top:10px; cursor:pointer;}
</style>
</head>
<body style="padding: 3px; border: 0px">

<div class="easyui-tabs" id="pointTabs" style="width:280px;height:540px;float: left;">
	<div title="填写计划信息" style="padding:10px" data-options="selected:true">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition" style="width: 100%;">
					<tr>
						<td style="width:30%;">计划名称：</td>
						<td style="width:70%;">
							<div class="condition-text-container">
								<input id="plan_name" name="plan_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
					<tr>
						<td >计划类型：</td>
						<td >
								<select id="plan_type"  name="plan_type" class="condition-select">
									<option value="">
										请选择
									</option>
									<option value="0">周期性</option>
									<option value="1">临时性</option>
							</select>
						</td>
					</tr>
					<tr>
						<td >计划周期：</td>
						<td >
							<select name="plan_circle" class="condition-select">
								<option value="">
									请选择
								</option>
								<option value="1">日</option>
								<option value="2">周</option>
								<option value="5">半月</option>
								<option value="3">月</option>
								<option value="4">年</option>
							</select>
						</td>
					</tr>
					<tr>
						<td >次数：</td>
						<td>
								<!-- <input name="plan_frequency" type="text" class="condition-text" /> -->
								<input name="plan_frequency" class="easyui-numberspinner" value="1" data-options="increment:1" style="width:155px;"></input>
						</td>
					</tr>
					<tr>
						 <td >开始时间：</td>
							<td>
								<div class="condition-text-container">
								<input class="condition-text condition"
									type="text" name=plan_start_time id="plan_start_time" 
									onClick="WdatePicker();" />
								</div>
							</td>
					</tr>
					<tr>
							<td >结束时间：</td>
							<td>
								<div class="condition-text-container">
								<input class="condition-text condition"
									type="text" name=plan_end_time id="plan_end_time"
									onClick="WdatePicker({minDate:'#F{$dp.$D(\'plan_start_time\')}'});" />
								</div>
							</td>
					</tr>
					<tr>
							<td >区域：</td>
						<td > 
							<select name="son_area" class="condition-select" onchange="sonAreaChange()">
									<option value="">
										请选择
									</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="10%">计划所属网格：</td>
							<td > 
								<select name="plan_dept_name" class="condition-select" >
							</select>
							</td>
					</tr>
					<tr>
						<td colspan="2" > 
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




<div class="easyui-tabs" id="pointTabs1" style="width:900px;height:480px;float: left;margin-left: 10px; overflow:auto;   " >
		<div title="选择关键点" style="padding:10px;  overflow-x: hidden;" data-options="fit:true">
				<div id="tb" style="padding: 5px; height: auto">
				<div class="condition-container">
					<form id="form" action="" method="post">
						<input type="hidden" name="selected" value="" />
						<table class="condition">
							<tr>
								<td width="10%">关键点编码：</td>
								<td width="20%">
									<div class="condition-text-container">
										<input name="point_no" type="text" class="condition-text" />
									</div>
								</td>
								<td width="10%">关键点名称：</td>
								<td width="20%">
									<div class="condition-text-container">
										<input name="point_name" type="text" class="condition-text" />
									</div>
								</td>
								
								<td width="10%">点类型：</td>
								<td width="20%"><select name="point_type"
									class="condition-select">
										<option value="">请选择</option>
										<c:forEach items="${pointTypeList }" var="type">
											<option value="${type.POINT_TYPE_ID }">${type.POINT_TYPE_NAME
												}</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
							<td width="10%">设备类型：</td>
								<td width="20%">
										<select name="eqp_type" class="condition-select">
											<option value="">请选择</option>
									</select>
									</td>
								<td width="10%">维护等级：</td>
								<td width="20%">
									<select class="easyui-combobox" multiple="true" id="point_level" name="point_level" style="width:100%;">
											<option value="">请选择</option>
											<option value="1" style="width:100%;">A1</option>
											<option value="2" style="width:100%;">A2</option>
											<option value="3" style="width:100%;">A3</option>
											<option value="4" style="width:100%;">B1</option>
											<option value="5" style="width:100%;">B2</option>
											<option value="6" style="width:100%;">B3</option>
											<option value="7" style="width:100%;">C1</option>
											<option value="8" style="width:100%;">C2</option>
											<option value="9" style="width:100%;">C3</option>
									</select>
								</td>
								<td width="10%">是否已派发：</td>
								<td width="20%">
										<select name="is_distribute" class="condition-select">
											<option value="">请选择</option>
											<option value="1">是</option>
											<option value="0">否</option>
									</select>
								</td>
								</tr>
								<tr>
								<td width="10%">点所属网格：</td>
							<td > 
								<select name="dept_name" class="condition-select" >
								<option value="">
										请选择
									</option>
							</select>
							</td>
							<td/>
								<td/>
								<td/>
								<td/>
								</tr>
						</table>
					</form>
				</div>
				<div class="btn-operation-container">
					<div style="float: right;" class="btn-operation"
						onClick="clearCondition()">重置</div>
					<div style="float: right;" class="btn-operation"
						onClick="searchData()">查询</div>
				
					
				</div>
			</div>
			<table id="dg" title="【关键点管理】" style="width: 880px; height: 500px">
			</table>
		</div>
</div> 

	<script type="text/javascript">
		$(document).ready(function() {
			getSonAreaList();
			searchData();
			//getMntList();
			getEqpTypeList();
			deptTips();
			//searchPointData();
			//$('#tabs').tabs('enableTab', '选择关键点');
			//$('#tabs').tabs('enableTab', '选择人员或班组');
			//$('#pointTabs').tabs('disableTab',1);
			//$('#pointTabs').tabs('enableTab', 1);
		});
		function getSonAreaList() {
		var areaId='${areaId}';
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
				$("select[name='son_area'] option").remove();
				var GLY ='${GLY}';
				var LXXJ_ADMIN ='${LXXJ_ADMIN}';
				var LXXJ_ADMIN_AREA ='${LXXJ_ADMIN_AREA}';
				if (GLY == 'true' || LXXJ_ADMIN =='true' || LXXJ_ADMIN_AREA=='true')
				{
					$("select[name='son_area']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
					
						$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
					}
					
				}
				else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${sonAreaId}){
							$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
						}
					}
				}
			}
		});
	}
	
	
		function searchPointData() {
			var obj = {
			};
			//return;
			$('#dg1').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : false,
				toolbar : '#addtb',
				url : webPath +  "/pointPlan/queryPoint.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 50,
				pageList : [ 20, 50 ,500,5000],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				onDblClickRow:function(rowIndex,rowData){},
				columns : [ [ {
					field : 'POINT_ID',
					title : '点ID',
					checkbox : true
				}, {
					field : 'POINT_NO',
					title : '点编码',
					width : "25%",
					align : 'center'
				}, {
					field : 'POINT_NAME',
					title : '点名称',
					width : "25%",
					align : 'center'
				}, {
					field : 'EQUIPMENT_TYPE_NAME',
					title : '设备类型',
					width : "12%",
					align : 'center'
				}] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
				}
			});
		}
		var lastIndex;
function searchData() {
			lastIndex=0;
			var pressCommandFlag = 0;//1表示按了回车键
			var point_no = $("input[name='point_no']").val();
			var point_name = $("input[name='point_name']").val();
			var address = $("input[name='address']").val();
			var point_type = $("select[name='point_type']").val();
			var son_area_id = $("select[name='son_area']").val();
			var point_level=$("#point_level").combobox('getValues');
			var dept_name = $("select[name='dept_name']").val();
			var is_distribute = $("select[name='is_distribute']").val();
			var eqp_type = $("select[name='eqp_type']").val();
			
			var obj = {
				point_no : point_no,
				point_name : point_name,
				address : address,
				point_type : point_type,
				son_area_id:son_area_id,
				point_level:point_level,
				dept_name:dept_name,
				is_distribute:is_distribute,
				eqp_type:eqp_type
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : false,
				toolbar : '#tb',
				url : webPath + "/CablePlan/queryPoint.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 50,
				pageList : [ 20, 50,500,5000 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				onDblClickRow:function(rowIndex,rowData){
				$("#dg").datagrid('endEdit',lastIndex);
	           	lastIndex=rowIndex;
	           	//$("#dg").datagrid('endEdit',rowIndex);
	           	$("#dg").datagrid('beginEdit',rowIndex);
	           	var POINT_NAME = rowData.POINT_NAME;
	           	$("input.datagrid-editable-input").val(POINT_NAME)
	           	.bind("blur",function(evt)
	           	{
	           		//alert(pressCommandFlag);
	           		if(rowData.POINT_NAME != $(this).val() && pressCommandFlag==0)
	           		{
	           			$(this).val(rowData.POINT_NAME);
	           			 $("#dg").datagrid('endEdit',lastIndex);
	           		}
	           	}).
	           	bind("focus",function(evt)
	           	{
	           		//alert("focus");
	           		 pressCommandFlag = 0;
	           	}).bind("keypress",function(evt){
	               if(evt.keyCode==13){
	               	
	                  updatePoint($(this),rowData);
	                  pressCommandFlag = 1;
	               }
	           }).focus();
	        },
				columns : [ [ {
					field : 'POINT_ID',
					title : '点ID',
					checkbox : true
				},
				{
					field : 'AREA',
					title : '地市',
					width : "6%",
					align : 'center'
				}, {
					field : 'SON_AREA',
					title : '区域',
					width : "7%",
					align : 'center'
				} , {
					field : 'DEPT_NAME',
					title : '所属网格',
					width : "9%",
					align : 'center'
				}, 
				 {
					field : 'POINT_NO',
					title : '点编码',
					width : "15%",
					align : 'center'
				}, {
					field : 'POINT_NAME',
					title : '点名称',
					width : "15%",
					align : 'center'
				}, {
					field : 'LEVEL_NAME',
					title : '维护等级',
					width : "5%",
					align : 'center'
				},{
					field : 'POINT_TYPE_NAME',
					title : '点类型',
					width : "7%",
					align : 'center'
				}, {
					field : 'EQUIPMENT_TYPE_NAME',
					title : '设备类型',
					width : "8%",
					align : 'center'
				}] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
				}
			});
		}
		
		function save(){
			var areaId='${areaId}';
			var plan_name = $.trim($("input[name='plan_name']").val());
			var plan_type = $.trim($("select[name='plan_type']").val());
			var plan_circle = $.trim($("select[name='plan_circle']").val());
			var plan_frequency = $.trim($("input[name='plan_frequency']").val());
			var plan_start_time = $.trim($("input[name='plan_start_time']").val());
			var plan_end_time = $.trim($("input[name='plan_end_time']").val());
			var son_area_id = $.trim($("select[name='son_area']").val());
			var dept_name = $.trim($("select[name='plan_dept_name']").val());
			
			var rows = $("#dg").datagrid('getSelections');
			var points="";
			if(null==plan_name || ""==plan_name){
				$.messager.show({
					title : '提  示',
					msg : '请填写计划名称!',
					showType : 'show'
				});
				return;
			}
			if(null==plan_type || ""==plan_type){
				$.messager.show({
					title : '提  示',
					msg : '请填选择计划类型!',
					showType : 'show'
				});
				return;
			}
			if(null==plan_circle || ""==plan_circle){
				$.messager.show({
					title : '提  示',
					msg : '请填选择计划周期!',
					showType : 'show'
				});
				return;
			}
			if(null==plan_frequency || ""==plan_frequency){
				$.messager.show({
					title : '提  示',
					msg : '请填写次数!',
					showType : 'show'
				});
				return;
			}
			if(!isPInt(plan_frequency)){
				$.messager.show({
					title : '提  示',
					msg : '次数应为正整数!',
					showType : 'show'
				});
				return;
			}
			if(null==plan_start_time || ""==plan_start_time){
				$.messager.show({
					title : '提  示',
					msg : '开始时间不能为空!',
					showType : 'show'
				});
				return;
			}
			if(null==plan_end_time || ""==plan_end_time){
				$.messager.show({
					title : '提  示',
					msg : '结束时间不能为空!',
					showType : 'show'
				});
				return;
			}
			if(null==son_area_id || ""==son_area_id){
				$.messager.show({
					title : '提  示',
					msg : '请选择区域!',
					showType : 'show'
				});
				return;
			}
			if(rows.length<=0){
				$.messager.show({
					title : '提  示',
					msg : '请选择关键点!',
					showType : 'show'
				});
				return;
			}else{
				for (var i = 0; i < rows.length; i++) {
					if(i==0){
						points += rows[i].POINT_ID;
					}else{
						points += (","+rows[i].POINT_ID);
					}
					
				}
			}
			$.ajax({
				type : 'POST',
				url : webPath + "/CablePlan/savePointPlan.do",
				data : {
					areaId : areaId,
					plan_name:plan_name,
					plan_type:plan_type,
					plan_circle:plan_circle,
					plan_frequency:plan_frequency,
					plan_start_time:plan_start_time,
					plan_end_time:plan_end_time,
					son_area_id:son_area_id,
					points:points,
					dept_name:dept_name
				},
				dataType : 'json',
				success : function(data) 
				{
					var resultCode=data.resultCode;
			          if(resultCode=="000"){
			        	 /*  $.messager.show({
							title : '提  示',
							msg : data.resultDesc,
							showType : 'show'
						}); */
						 alert(data.resultDesc);  
						location.href = 'index.do';
						
			          }else{
			           	$.messager.show({
							title : '提  示',
							msg : data.resultDesc,
							showType : 'show'
						});
			          	return;
			          }
				}
			});
		}
		
		//正整数
		function isPInt(str) {
		    var g = /^[1-9]*[1-9][0-9]*$/;
		    return g.test(str);
		}
		//整数
		function isInt(str)
		{
		    var g=/^-?\d+$/;
		    return g.test(str);
		}
		//获得维护等级
	function getMntList() {
		$.ajax({
			type : 'POST',
			url : webPath + "Lxxj/point/manage/getMntList.do",
			data : {
			},
			dataType : 'json',
			success : function(json) 
			{
				var result = json.mntList;
				$("select[name='point_level'] option").remove();
				$("select[name='point_level']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
				
					$("select[name='point_level']").append("<option value=" + result[i].LEVEL_ID + ">"
									+ result[i].LEVEL_NAME + "</option>");
				}
			}
		});
	}
			//获得设备的类型
	function getEqpTypeList() {
		$.ajax({
			type : 'POST',
			url : webPath + "Lxxj/point/manage/getEqpTypeList.do",
			data : {
			},
			dataType : 'json',
			success : function(json) 
			{
				var result = json.eqpTypeList;
				$("select[name='eqp_type'] option").remove();
				$("select[name='eqp_type']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
				
					$("select[name='eqp_type']").append("<option value=" + result[i].EQUIPMENT_TYPE_ID + ">"
									+ result[i].EQUIPMENT_TYPE_NAME + "</option>");
				}
			}
		});
	}
	function deptTips(){
	/* 	    $("#dept_name").combobox({
		    	valueField: 'DEPT_ID', //TPrice
                textField: 'DEPT_NAME',
                 //注册事件
                 onChange: function (newValue, oldValue) {
                      if (newValue != null) {
                         var thisKey = $("#dept_name").combobox('getText'); //搜索词
                         var urlStr = webPath+"Dept/deptSelectTip.do?dept_name="+thisKey;
                         var v = $("#dept_name").combobox("reload", urlStr);
                     } 
                 },
                 onSelect: function (record) {
                     $("#dept_name").combobox('setValue', record.DEPT_NAME);   
                 }
		    });  
		    //不需要模糊匹配
		     var urlStr = webPath+"Dept/deptSelectTip.do?dept_name=";
                         var v = $("#dept_name").combobox("reload", urlStr); */
                         
            $.ajax({
			type : 'POST',
			url : webPath +"Dept/getDeptByAreaId.do?dept_name=",
			data : {
			},
			dataType : 'json',
			success : function(json) 
			{
				var result = json;
				$("select[name='dept_name'] option").remove();
				$("select[name='dept_name']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
				
					$("select[name='dept_name']").append("<option value=" + result[i].DEPT_NO + ">"
									+ result[i].DEPT_NAME + "</option>");
					$("select[name='plan_dept_name']").append("<option value=" + result[i].DEPT_NO + ">"
									+ result[i].DEPT_NAME + "</option>");
				}
			}
		});
		}
		function sonAreaChange(){
			searchData();
		}
		function back(){
			location.href = 'index.do';
		}
	</script>
</body>
</html>