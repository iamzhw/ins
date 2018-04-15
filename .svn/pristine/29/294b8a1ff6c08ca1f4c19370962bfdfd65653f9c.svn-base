<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>巡检计划</title>

</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<input type="hidden" name="selected_planId" value="" />
				
				<table class="condition">
					<tr>
						<td width="10%">地市：</td>
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
						<td width="10%">区县：</td>
						<td width="20%">
							<select name="SON_AREA_ID" id="son_area_id" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td>计划类型：</td>
						<td>
							<select id="plan_type" name="plan_type"
								class="condition-select">
									<option value="">请选择</option>
									<option value="1">承包人检查</option>
									<option value="2">网格质量检查员检查</option>
									<option value="3">市县公司专业中心</option>
									<option value="4">市公司资源中心</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
				<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
				<div style="float:right;" class="btn-operation" onClick="searchData()">查询</div>
				<div style="float:right;" class="btn-operation" onClick="addRule()">更新</div>
		</div>
	</div>
	<table id="dg" title="【计划管理】" style="width:98%;height:480px">
	</table>
	<div id="win_staff"></div>
	<div id="win_Plan"></div>
	<script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
		
		function searchData() {
			var area_id = $("#area_id").val();
			var son_area_id = $("#sobn_area_id").val();
			var plan_type = $("#plan_type").val();
			
			var obj = {
				area_id : area_id,
				son_area_id : son_area_id,
				plan_type : plan_type
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "patrolPlan/queryRule.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				fit:true,
				singleSelect : false,
				columns : [ [ {
					field : 'PLAN_NAME',
					title : '计划类型',
					width : "12%",
					align : 'center'
				}, {
					field : 'AREA_NAME',
					title : '地市',
					width : "8%",
					align : 'center'
				}, {
					field : 'SON_AREA_NAME',
					title : '区县',
					width : "8%",
					align : 'center'
				}, {
					field : 'KEY_NO',
					title : '规则编码',
					width : "8%",
					align : 'center'
				}, {
					field : 'KEY_NAME',
					title : '规则名称',
					width : "35%",
					align : 'center'
				} , {
					field : 'KEY_VALUE',
					title : '规则值',
					width : "8%",
					align : 'center'
				},{
					field : 'STAFF_NAME',
					title : '操作人',
					width : "10%",
					align : 'center'
				},{
					field : 'CREATE_TIME',
					title : '更新时间',
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
		/**点击checkbox触发**/
		function onCheck(index, row) {
			alert("onCheck");
		}

		function onSelect(index, row) {
			alert("onSelect");
		}

		function onSelectAll(rows) {
			alert(rows);
			alert("onSelectAll");
		}

		function clearCondition(form_id) {
			$("#area_id").val("");
			$("#son_area_id").val("");
			$("#plan_type").val("");
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
		}

		function addRule() {
			location.href = "addRule.do";
		}
		function editRule() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show'
				});
				return;
			} else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show'
				});
				return;
			} else {
				var plan_id = selected[0].PLAN_ID;
				location.href = "patrolPlanEdit.do?plan_id=" + plan_id;
			}
		}
		

		function delRule() {
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
				$.messager.confirm('系统提示', '您确定要删除计划吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].PLAN_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$('#form').form('submit', {
							url : webPath + "patrolPlan/plan_delete.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '成功删除计划!',
									showType : 'show'
								});
							}
						});
					}
				});
			}
		}
		
	</script>
</body>
</html>