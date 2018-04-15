<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>机房管理</title>
</head>
<body style="padding:3px;border:0px">
	<table id="dg" title="【机房管理】" style="width:100%;height:480px">
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container" style="height: 80px;">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">机房名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="room_name" type="text" class="condition-text" />
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
						<td width="10%">巡检人：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition" type="text" name="check_staff" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="10%">地址：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition" type="text" name="address" />
							</div>
						</td>
						<td width="10%">创建人：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition" type="text" name="create_by" />
							</div>
						</td>
						<td width="10%">创建时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition" type="text" name="create_date" onClick="WdatePicker();"/>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="addRoom()">新增</div>
			<div class="btn-operation" onClick="editRoom()">编辑</div>
			 <div class="btn-operation" onClick="delRoom()">删除</div>
			<div style="float:right;" class="btn-operation" onClick="clearCondition()">重置</div>
			<div style="float:right;" class="btn-operation" onClick="searchData()">查询</div>
		</div>
	</div>
	<div id="win_staff"></div>

	<script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
		
		function searchData() {
			var room_name = $("input[name='room_name']").val();
			var room_type_id = $("select[name='room_type_id']").val();
			var check_staff = $("input[name='check_staff']").val();
			var address = $("input[name='address']").val();
			var create_by = $("input[name='create_by']").val();
			var create_date = $("input[name='create_date']").val();
			
			var obj = {
					room_name : room_name,
					room_type_id : room_type_id,
					check_staff : check_staff,
					address : address,
					create_by : create_by,
					create_date : create_date
				}
			
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "jfxjRoom/query.do",
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
					field : 'ROOM_ID',
					title : '机房ID',
					checkbox : true
				}, {
					field : 'ROOM_NAME',
					title : '机房名称',
					width : "20%",
					align : 'center'
				}, {
					field : 'ROOM_TYPE_NAME',
					title : '机房类型',
					width : "15%",
					align : 'center'
				}, {
					field : 'CHECK_STAFF',
					title : '巡检人',
					width : "10%",
					align : 'center'
				}, {
					field : 'ADDRESS',
					title : '地址',
					width : "20%",
					align : 'center'
				}, {
					field : 'CREATE_DATE',
					title : '创建时间',
					width : "15%",
					align : 'center'
				}, {
					field : 'CREATE_BY',
					title : '创建人',
					width : "10%",
					align : 'center'
				},{
					field : 'DETAIL_ID',
					title : '操作',
					width : "10%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
                     return "<a onclick='viewDetail("+value+")' style='cursor: pointer;'>详情</a>";  
                    } 
				}] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
		}
		/**选择行触发**/
		function onClickRow(index, row) {
			alert("onClickRow");
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
			$("input[name='room_name']").val("");
			$("input[name='room_type']").val("");
			$("input[name='check_staff']").val("");
			$("input[name='address']").val("");
			$("input[name='longitude']").val("");
			$("input[name='latitude']").val("");
			$("input[name='create_by']").val("");
			$("input[name='create_date']").val("");
		}

		function addRoom() {
				location.href = "toAdd.do";
		}
		
		function editRoom() {
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
				var room_id = selected[0].ROOM_ID;
				location.href = webPath + "jfxjRoom/toUpdate.do?room_id=" + room_id;
			}

		}

		function delRoom() {
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
				$.messager.confirm('系统提示', '您确定要删除机房吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].ROOM_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$('#form').form('submit', {
							url : webPath + "jfxjRoom/delete.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '成功删除机房!',
									showType : 'show'
								});
							}
						});
					}
				});
			}
		}
		
	function viewDetail(value)
		{
			$('#win_staff').window({
				title : "【机房详情】",
				href : webPath + "jfxjRoom/detail.do?room_id=" + value,
				width : 800,
				height : 500,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
	</script>
</body>
</html>