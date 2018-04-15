<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>检查项管理</title>
</head>

<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%" nowrap="nowrap">检查项名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="check_item_name" type="text" class="condition-text" />
							</div>
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
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="addCheckItem()">增加</div>
			<div class="btn-operation" onClick=" updateCheckItem()">编辑</div>
			<div class="btn-operation" onClick=" delCheckItem()">删除</div>
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
			var check_item_name = $("input[name='check_item_name']").val();
			var room_type_id = $("select[name='room_type_id']").val();
			
			var obj = {
				check_item_name : check_item_name,
				room_type_id : room_type_id
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "checkItem/query.do",
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
					field : 'CHECK_ITEM_ID',
					title : '检查项ID',
					checkbox : true
				},{
					field : 'CHECK_ITEM_NAME',
					title : '检查项名称',
					width : "25%",
					align : 'center'
				}, {
					field : 'CHECK_ITEM_CONTENT',
					title : '检查内容',
					width : "30%",
					align : 'center'
				}, {
					field : 'ROOM_TYPE_NAME',
					title : '机房类型',
					width : "15%",
					align : 'center'
				}, {
					field : 'CREATE_BY',
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

		//清空查询条件
		function clearCondition(form_id) {
			$("input[name='check_item_name']").val("");
			$("select[name='room_type_id']").val("");
		}

		//新增计划
		function addCheckItem() {
			location.href = "toAdd.do";
		}
		
		
		//修改计划
		function updateCheckItem() {
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
				var check_item_id = selected[0].CHECK_ITEM_ID;
				location.href = webPath + "checkItem/toUpdate.do?check_item_id=" + check_item_id;
			}
		}
		
		
		//删除检查项
		function delCheckItem() {
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
				$.messager.confirm('系统提示', '您确定要删除检查项吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].CHECK_ITEM_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$('#form').form('submit', {
							url : webPath + "checkItem/delete.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '成功删除检查项!',
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