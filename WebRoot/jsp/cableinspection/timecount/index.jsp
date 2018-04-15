<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>任务时长统计</title>
</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">网格：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="dept_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">巡线员：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="staff_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=start_time id="start_time" 
								onClick="WdatePicker();" />
							</div>
						</td>
						<td width="10%">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=complete_time id="complete_time"
								onClick="WdatePicker();" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="10%">地市：</td>
						<td width="30%">
							<select name="area_id" class="condition-select" onchange="getSonAreaById()">
									<option value="">
										请选择
									</option>
									<c:forEach items="${areaList}" var="area">
										<option value="${area.AREA_ID}">${area.NAME}</option>
									</c:forEach>
							</select>
						</td>
						<td width="10%">区县：</td>
						<td width="30%">
							<select name="son_area_id" class="condition-select">
								<option value="">
										请选择
									</option>
									<c:forEach items="${sonAreaList}" var="area">
										<option value="${area.AREA_ID}">${area.NAME}</option>
									</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="exportExcel()">导出</div>

			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【任务时长统计】" style="width:100%;height:480px">
	</table>
	<div id="win_staff"></div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
			getSonAreaById();
		});
		function searchData() {
			var dept_name = $("input[name='dept_name']").val();
			var staff_name = $("input[name='staff_name']").val();
			var start_time = $("input[name='start_time']").val();
			var complete_time = $("input[name='complete_time']").val();
			var area_id=$("select[name='area_id']").val();
			var son_area_id = $("select[name='son_area_id']").val();
			var obj = {
				dept_name : dept_name,
				staff_name : staff_name,
				start_time : start_time,
				complete_time : complete_time,
				son_area_id:son_area_id,
				area_id:area_id
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "TimeCount/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [  {
					field : 'NAME',
					title : '市',
					width : "15%",
					align : 'center'
				}, {
					field : 'SON_NAME',
					title : '区县',
					width : "15%",
					align : 'center'
				}, {
					field : 'DEPT_NAME',
					title : '网格',
					width : "15%",
					align : 'center'
				}, {
					field : 'ZONE',
					title : '片区(预留)',
					width : "15%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '巡线员',
					width : "15%",
					align : 'center'
				},{
					field : 'TIMECOUNT',
					title : '巡线时长',
					width : "15%",
					align : 'center'
				}, {
					field : 'DISTANCE',
					title : '巡线里程',
					width : "15%",
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
		function exportExcel() {
			var dept_name = $("input[name='dept_name']").val();
			var staff_name = $("input[name='staff_name']").val();
			var start_time = $("input[name='start_time']").val();
			var complete_time = $("input[name='complete_time']").val();
			var son_area_id = $("select[name='son_area_id']").val();
			var area_id = $("select[name='area_id']").val();
			
			var obj = {
			dept_name : dept_name,
			staff_name : staff_name,
			complete_time : complete_time,
			son_area_id : son_area_id,
			area_id:area_id
		};
		$.messager.confirm('系统提示', '您确定要导出该报表吗?', function(r) {
			if (r) {

				$('#form').form('submit', {
					url : webPath + "TimeCount/export.do",
					queryParams : obj,
					onSubmit : function() {
						//  $.messager.progress();
					},
					success : function() {
						//  $.messager.progress('close'); // 如果提交成功则隐藏进度条
						$.messager.show({
							title : '提  示',
							msg : '导出成功!',
							showType : 'show'
						});

					}
				});
			}
		});
			
			
			/*$("#hiddenIframe").attr("src", "/ins/TimeCount/export.do?dept_name="+dept_name+
										   "&staff_name="+staff_name+
										   "&start_time="+start_time+
										   "&complete_time="+complete_time+
										   "&son_area_id="+son_area_id);*/
		}
		function getSonAreaById(){
		var area_id=$("select[name='area_id']").val().trim();
		if(area_id!=null&&area_id!=''&&area_id!=undefined){
			$.ajax({
			 url:webPath + "ArrivalRate/getSonAreaById.do?area_id="+area_id,
			 contentType:"application/json",
			 type:"post",
			 dataType:"json",
			 data:{
			 	area_id:area_id
			 },
			 success:function(json){
			  var list = json.sonAreaList;
				 $("select[name='son_area_id'] option").remove();
				 $("select[name='son_area_id']").append("<option value=''>请选择</option>");
				 $.each(list,function(i,e){
				 	$("select[name='son_area_id']").append("<option value='"+e.AREA_ID+"'>"+e.NAME+"</option>");
				 });
			 }
			})
		}
	}
	</script>
</body>
</html>