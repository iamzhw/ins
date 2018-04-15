<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>选择任务执行人</title>
</head>
<body>
<input name="planSelected" value="${planSelected}" type="hidden"/>
	<table id="dg_soft" title="【选择执行人】" style="width: 100%; height: 480px">
	</table>
	<div id="tb_soft" style="height: auto">
	<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">登录名：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="staff_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">姓名：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="staff_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="clearCondition()">重置</div>
			<div class="btn-operation" onClick="searchData1()">查询</div>
			<div class="btn-operation" onClick="saveTask()">生成任务</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			searchData1();
		});
		function searchData1(){
			var staff_name = $("input[name='staff_name']").val();
			var staff_no = $("input[name='staff_no']").val();
			var obj = {
				staff_name:staff_name,
				staff_no : staff_no
			};
			$('#dg_soft').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 440,
				height : 450,
				toolbar : '#tb_soft',
				url : webPath + "Care/query_keep_staff.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 5, 10, 20, 50 ],
				loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'STAFF_ID',
					title : '用户ID',
					checkbox : true
				}, {
					field : 'STAFF_NO',
					title : '登录名',
					width : "20%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '姓名',
					width : "20%",
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
		function clearCondition() {
			$("input[name='staff_name']").val("");
		}
		function saveTask(){
			var selected = $('#dg_soft').datagrid('getChecked');
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
				var inspector = selected[0].STAFF_ID;
				var planSelected = $("input[name='planSelected']").val();
				$.ajax({
					type : 'POST',
					url : webPath + "Care/task_save.do",
					data : {
						inspector : inspector,
						planSelected : planSelected
					},
					dataType : 'json',
					onSubmit: function () {
                        $.messager.progress();
                    },
					success : function(json) {
						$.messager.progress('close'); // 如果提交成功则隐藏进度条
						if (json.status) {
							$.messager.show({
								title : '提  示',
								msg : '生成任务成功!',
								showType : 'show'
							});
						} else {
							$.messager.show({
								title : '提  示',
								msg : '所选计划已经被分配!',
								showType : 'show'
							});
						}
						$('#win_staff').window('close');
						searchData();
					}
				});
			}
		}
	</script>
</body>
</html>