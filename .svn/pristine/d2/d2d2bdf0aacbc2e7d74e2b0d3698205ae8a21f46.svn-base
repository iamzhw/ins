	<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>分公司使用情况</title>
<style>
body{
	overflow: auto;
	width: 98%;
}
.panel-fit, .panel-fit body {
    height: 100%;
    margin: 0;
    padding: 0;
    border: 0;
    overflow: auto;
}
</style>
</head>
<body style="padding: 3px; border: 0px">
	
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected_relation_ids" value="" />
				<table class="condition">
					<tr>
						<td width="10%">检查人员类型：</td>
						<td width="20%">
							<select id="check_type" name="check_type" class="condition-select">
									<option value="">请选择</option>
									<option value="2">网格质量检查员</option>
									<option value="3">市县公司专业中心</option>
									<option value="4">市公司资源中心</option>
							</select>
						</td>
						<td width="10%">地市：</td>
						<td width="20%">
							<select id="show_area" class="condition-select" onchange="getSonAreaList()">
									<option value="">
										请选择
									</option>
									<c:forEach items="${areaList}" var="area">
										<option value="${area.AREA_ID}">${area.NAME}</option>
									</c:forEach>
							</select>
							<input type="hidden" id="AREA_ID" name="AREA_ID"/>
						</td>
						<td width="10%">区县：</td>
						<td width="20%">
							<select name="SON_AREA_ID" id="son_area" class="condition-select" onchange="getGridList()">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td width="10%">网格：</td>
						<td width="20%">
							<select id="whwg" name="WHWG" class="condition-select" >
									<option value="">
										请选择
									</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="10%">检查人：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input id="check_name" name="check_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">代维公司：</td>
						<td width="20%">
							<select id="show_company" name="show_company" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td width="10%">检查人班组权限：</td>
						<td width="20%">
							<select id="show_banzu" name="show_banzu" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
		    <div class="btn-operation" onClick="addDispatchRelation()">新增</div>
			<div class="btn-operation" onClick="deleteRelation()">删除</div>
			<div style="float:right;" class="btn-operation" onClick="searchData()">查询</div>
		</div>
	</div>
<table id="dg" title="【分公司缆线巡检使用情况】" style="width:98%;height:480px">
</table>
<div id="win_staff"></div>
<script type="text/javascript">
	$(document).ready(function() {
		
		
		getMainTainCompany();
		
		$("#show_company").change(function(){
			getBanzuInfo();
		});
		
		
		//searchData();
		//alert(); //是否是省级管理员
		//alert("${areaId}");
		if("${CABLE_ADMIN}"){
			$("#AREA_ID").val();
			$("#AREA_TR").show();
		}else{
			$("#AREA_ID").val("${areaId}");
			("#AREA_TR").hide();
		}
		
		$("#show_area").change(function(){
			$("#AREA_ID").val($(this).val());
		});
	});
	
	function deleteRelation() {
		var selected = $('#dg').datagrid('getChecked');
		var count = selected.length;
		if (count == 0) {
			$.messager.show({
				title : '提  示',
				msg : '请选取要删除的数据!',
				showType : 'show'
			});
			return;
		} else {
			$.messager.confirm('系统提示', '您确定要删除选中的派单关系吗?', function(r) {
				if (r) {
					var arr = new Array();
					for ( var i = 0; i < selected.length; i++) {
						var value = selected[i].RELATION_ID;
						arr[arr.length] = value;
					}
					$("input[name='selected_relation_ids']").val(arr);
					$('#form').form('submit', {
						url : webPath + "dispatchRelation/deleteRelation.do",
						onSubmit : function() {
							$.messager.progress();
						},
						success : function() {
							$.messager.progress('close'); // 如果提交成功则隐藏进度条
							searchData();
							$.messager.show({
								title : '提  示',
								msg : '删除成功!',
								showType : 'show'
							});
						}
					});
				}
			});
		}
	}
	
	function addDispatchRelation() {
		//addTab("新增任务",webPath+"CablePlan/plan_add.do");
		location.href = "addDispatchRelation.do";
	}

	function searchData() {
		var AREA_ID = $("#AREA_ID").val();
		if (AREA_ID == "NaN") {
			AREA_ID = "";
		}
		if ("" == AREA_ID || null == AREA_ID) {
			$.messager.show({
				title : '提  示',
				msg : '请选择地市!',
				showType : 'show'
			});
			return;
		}
		
		var companyId = $("#show_company").val();
		var qxAreaId = $("#son_area").val();
		var checkName = $("#check_name").val();
		var whwgId = $("#whwg").val();
		var checkType = $("#check_type").val();
		var teamId = $("#show_banzu").val();
		
		var searchDate = {
			check_type : checkType,       //实施人类型
			AREA_ID : AREA_ID,           //地市
			qx_area_id : qxAreaId,       //区县
			whwg_id : whwgId,             //网格
			check_name : checkName,//检查人
			company_id : companyId,     //代维公司
			team_id : teamId             //检查人班组权限
		};
		$('#dg').datagrid({
			//此选项打开，表格会自动适配宽度和高度。
			autoSize : true,
			toolbar : '#tb',
			url : webPath + "dispatchRelation/queryDispatchRelation.do",
			queryParams : searchDate,
			method : 'post',
			pagination : true,
			pageNumber : 1,
			pageSize : 10,
			pageList : [ 20, 50, 500 ],
			//loadMsg:'数据加载中.....',
			rownumbers : true,
			fit : true,
			singleSelect : true,
			columns : [ [ {
				field : 'RELATION_ID',
				title : 'ID',
				width : "10%",
				checkbox : true
			}, {
				field : 'CHECK_TYPE',
				title : '检查实施人类型',
				width : "10%",
				align : 'center',
				formatter : function(value, row, Index) {
					if (null != value && "" != value) {
						if ("2" == value) {
							return "网格质量检查员";
						} else if ("3" == value) {
							return "市县公司专业中心";
						} else if ("4" == value) {
							return "市公司资源中心";
						}
					} else {
						return null;
					}

				}
			},{
				field : 'PARENT_AREA_NAME',
				title : '地市',
				width : "9%",
				align : 'center'
			}, {
				field : 'SON_AREA_NAME',
				title : '区县',
				width : "10%",
				align : 'center'
			}, {
				field : 'GRID_NAME',
				title : '网格',
				width : "18%",
				align : 'center'
			}, {
				field : 'STAFF_NAME',
				title : '检查人',
				width : "10%",
				align : 'center'
			}, {
				field : 'TEAM_NAME',
				title : '检查人班组权限',
				width : "10%",
				align : 'center'
			}, {
				field : 'COMPANY_NAME',
				title : '代维公司',
				width : "10%",
				align : 'center'
			}, {
				field : 'STAFF_NO',
				title : '编号',
				width : "10%",
				align : 'center'
			}, {
				field : 'TEL',
				title : '电话',
				width : "10%",
				align : 'center'
			}, {
				field : 'ID_NUMBER',
				title : '身份证',
				width : "14%",
				align : 'center'
			},  {
				field : 'IS_ORDER',
				title : '是否是接单负责人',
				width : "10%",
				align : 'center',
				formatter : function(value, row, Index) {
					if (null != value && "" != value) {
						if ("1" == value) {
							return "是";
						} else if ("0" == value) {
							return "否";
						}
					} else {
						return null;
					}

				}
			}
			] ],
			//width : 'auto',
			nowrap : false,
			striped : true,
			//onClickRow:onClickRow,
			//onCheck:onCheck,
			//onSelect:onSelect,
			//onSelectAll:onSelectAll,
			onLoadSuccess : function(data) {
				$("body").resize();
			}
		});
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
	}
	
	
	function getMainTainCompany(){
		$.ajax({
			type : 'POST',
			url : webPath + "General/getMainTainCompany.do",
			/* data : {}, */
			dataType : 'json',
			success : function(json) 
			{		
				var result = json.mainTainCompany;
				$("select[name='show_company'] option").remove();
				$("select[name='show_company']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
					$("select[name='show_company']").append("<option value=" + result[i].COMPANY_ID + ">"+ result[i].COMPANY_NAME + "</option>");					
				}
			}
		});
	}
	
	function getBanzuInfo(){
		var companyId = $("#show_company").val();
		$.ajax({
			type : 'POST',
			url : webPath + "General/getBanzuByCompanyId.do",
			data : {
				companyId:companyId
			},
			dataType : 'json',
			success : function(json) 
			{		
				var result = json.banzu;
				$("select[name='show_banzu'] option").remove();
				$("select[name='show_banzu']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
					$("select[name='show_banzu']").append("<option value=" + result[i].TEAM_ID + ">"+ result[i].TEAM_NAME + "</option>");					
				}
			}
		});
	}
	
</script>
</body>
</html>
