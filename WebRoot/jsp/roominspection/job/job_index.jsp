<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>计划管理</title>
</head>

<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%" nowrap="nowrap">计划名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="job_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">计划周期：</td>
						<td width="20%">
							<select name="circle_id" class="condition-select">
								<option value="">
									全部
								</option>
								<c:forEach items="${circles}" var="al">
									<option value="${al.CIRCLE_ID}">
										${al.CIRCLE_NAME}
									</option>
								</c:forEach>
							</select>
						</td>
						<td width="10%" nowrap="nowrap">机房类型：</td>
						<td width="20%">
							<select name="room_type_id" class="condition-select">
								<option value="">
									全部
								</option>
								<c:forEach items="${roomTypes}" var="al">
									<option value="${al.ROOM_TYPE_ID}">
										${al.ROOM_TYPE_NAME}
									</option>
								</c:forEach>
							</select>
						</td>
						<td width="10%" nowrap="nowrap">区域：</td>
						<td width="20%">
							<select name="area_id" class="condition-select">
								<option value="">
									全部
								</option>
								<c:forEach items="${areaList}" var="al">
									<option value="${al.AREA_ID}">
										${al.NAME}
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<!-- <div class="btn-operation" onClick="editStaff()">编辑</div>
			<div class="btn-operation" onClick="delPlan()">删除</div>
			 -->
			<div class="btn-operation" onClick="addPlan()">增加</div>
			<div class="btn-operation" onClick="stopJob()">暂停</div>
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div style="float:right;" class="btn-operation" onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【计划管理】" style="width:100%;height:480px"></table>
	<div id="win_staff"></div>

	<script type="text/javascript">
	$(document).ready(function() {
			searchData();
		});
		
		function searchData() {
			var job_name = $("input[name='job_name']").val();
			var circle_id = $("select[name='circle_id']").val();
			var room_type_id = $("select[name='room_type_id']").val();
			var area_id = $("select[name='area_id']").val();
			var obj = {
				job_name : job_name,
				circle_id : circle_id,
				room_type_id : room_type_id,
				area_id : area_id
				
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "JfxjJob/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'JOB_ID',
					title : '计划ID',
					checkbox : true
				},{
					field : 'JOB_NAME',
					title : '计划名称',
					width : "30%",
					align : 'center'
				}, {
					field : 'CIRCLE_NAME',
					title : '计划周期',
					width : "10%",
					align : 'center'
				}, {
					field : 'ROOM_TYPE_NAME',
					title : '机房类型',
					width : "15%",
					align : 'center'
				}, {
					field : 'AREA_NAME',
					title : '区域',
					width : "20%",
					align : 'center'
				}, {
					field : 'CREATOR',
					title : '创建者',
					width : "10%",
					align : 'center'
				}, {
					field : 'CREATE_DATE',
					title : '创建时间',
					width : "15%",
					align : 'center'
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
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

		//清空查询条件
		function clearCondition(form_id) {
			$("input[name='job_name']").val("");
			$("select[name='circle_id']").val("");
			$("select[name='room_type_id']").val("");
			$("select[name='area_id']").val("");
		}

		//新增计划
		function addPlan() {
			location.href = "add.do";
		}
		
		
		//修改计划
		function editStaff() {
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
				var job_id = selected[0].JOB_ID;
				location.href = webPath + "JfxjJob/edit.do?job_id=" + job_id;
			}
		}
		
		//暂停计划
		function stopJob() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取要暂停的计划!',
					showType : 'show'
				});
				return;
			} else {
				$.messager.confirm('系统提示', '您确定要暂停计划吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].JOB_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$('#form').form('submit', {
							url : webPath + "JfxjJob/stop.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '成功暂停计划!',
									showType : 'show'
								});
							}
						});
					}
				});
			}
		}
		
		//删除计划
		function delPlan() {
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
							var value = selected[i].JOB_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$('#form').form('submit', {
							url : webPath + "JfxjJob/delete.do",
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