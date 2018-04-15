<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>隐患点管理</title>
</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">隐患点编码：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="point_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">隐患点名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="point_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">隐患点类型：</td>
						<td width="20%">
							<select name="point_type" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td width="10%">上报时间时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=create_time onClick="WdatePicker();" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="checkTrouble()">审核</div>
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【隐患点管理】" style="width:100%;height:480px">
	</table>
	<div id="win_staff"></div>

	<script type="text/javascript">
		$(document).ready(function() {
			//searchData();
		});
		function searchData() {
			var point_no = $("input[name='point_no']").val();
			var point_name = $("input[name='point_name']").val();
			var point_type = $("input[name='point_type']").val();
			var create_time = $("input[name='create_time']").val();
			var obj = {
					point_no : point_no,
					point_name : point_name,
					point_type : point_type,
					create_time : create_time
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Trouble/query.do",
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
					field : 'POINT_ID',
					title : '隐患点ID',
					checkbox : true
				}, {
					field : 'POINT_NO',
					title : '隐患点编码',
					width : "18%",
					align : 'center'
				}, {
					field : 'POINT_NAME',
					title : '隐患点名称',
					width : "18%",
					align : 'center'
				}, {
					field : 'POINT_TYPE_NAME',
					title : '隐患点类型',
					width : "9%",
					align : 'center'
				}, {
					field : 'CREATE_TIME',
					title : '上报时间',
					width : "10%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '上报人员',
					width : "10%",
					align : 'center'
				}, {
					field : 'LONGITUDE',
					title : '经度',
					width : "7%",
					align : 'center'
				}, {
					field : 'LATITUDE',
					title : '纬度',
					width : "7%",
					align : 'center'
				}, {
					field : 'ADDRESS',
					title : '地址',
					width : "10%",
					align : 'center'
				}, {
					field : 'NAME',
					title : '所属地区',
					width : "6%",
					align : 'center'
				} ] ],
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
			$("input[name='point_no']").val("");
			$("input[name='point_name']").val("");
			$("input[name='point_type']").val("");
			$("input[name='create_time']").val("");
		}
		
		function checkTrouble(){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选择需要审核的隐患点!',
					showType : 'show'
				});
				return;
			} else {
				$.messager.confirm('系统提示', '您确定要审核这些隐患点吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].POINT_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$.ajax({
							type : 'GET',
							url : webPath + "Trouble/check.do?ids=" + arr,
							dataType : 'json',
							success : function(json) {
								if(json.status){
									searchData();
									$.messager.show({
										title : '提  示',
										msg : '审核成功!',
										showType : 'show'
									});
								}
							}
						});
					}
				});
				
			}
		}
	</script>
</body>
</html>