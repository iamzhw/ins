
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>看护时间段模板管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<table id="dg_kanhu_shijian" title="【看护时间段模板管理】" style="width: 100%; height: 480px">
	</table>
	<div id="tb_kanhu_shijian" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form_kanhu_shijian" action="" method="post">
				<input type="hidden" name="selected_kanhu_shijian" value="" />
				<table class="condition">
					<tr>
						<td width="10%">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_start_time" id="p_start_time" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})"
									class="condition-text" />
							</div>
						</td>
						<td width="10%">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_end_time" id="p_end_time" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})"
									class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="sure__kanhu_shijian()">确定</div>

			<div style="float:center;" class="btn-operation"
				onClick="clearCondition('form_kanhu_shijian')">重置</div>
			<div style="float:center;" class="btn-operation"
				onClick="searchData_kanhu_shijian()">查询</div>
		</div>
	</div>
	<div id="win_distribute_kanhu_shijian"></div>

	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {

			searchData_kanhu_shijian();
		});

		function searchData_kanhu_shijian() {

			var obj = makeParamJson('form_kanhu_shijian');
			$('#dg_kanhu_shijian').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				width : 600,
				height : 380,
				toolbar : '#tb_kanhu_shijian',
				url : webPath + "outsitePlanManage/getPartTimeList.do",
				queryParams : obj,
				method : 'post',
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'PART_TIME_ID',
					title : 'ID',
					checkbox : true
				}, {
					field : 'START_TIME',
					title : '开始时间',
					width : "12%",
					align : 'center'
				}, {
					field : 'END_TIME',
					title : '结束时间',
					width : "12%",
					align : 'center'
				},  {
					field : 'CITY_NAME',
					title : '城市',
					width : "12%",
					align : 'center'
				}, {
					field : 'IS_PRITERMISSION',
					title : '是否默认',
					width : "12%",
					align : 'center',
					formatter:function(value, rec,index) {
					if(value=='0'){
						return "否";
					}else{
						return "是";
					}
				}
				}
				] ],
				nowrap : false,
				striped : true,
				onCheck:onCheck,
				onLoadSuccess : function(data) {
				}
			});
		}

		function clearCondition(form_id) {
			$("#" + form_id + "").form('reset', 'none');
		}

		function sure__kanhu_shijian() {
			var selected = $('#dg_kanhu_shijian').datagrid('getChecked');
			var count = selected.length;
			
			var arr = new Array();
			var arr_id = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var part_time_id = selected[i].PART_TIME_ID;
					var value_start_time = selected[i].START_TIME;
					var value_end_time = selected[i].END_TIME;
					arr[arr.length] = value_start_time+'--'+value_end_time+'\n';
					arr_id[arr_id.length] = part_time_id;
				}
			
			$('#kanhu_shijian_id').val(arr_id.toString());
			$('#kanhu_shijian').val(arr.toString());
			
			$('#win_select_kanhu_shijian').window('close');

		}
		/**点击checkbox触发**/
		function onCheck(index, row) {
			var selected = $('#dg_kanhu_shijian').datagrid('getChecked');
			var count = selected.length;
			
			if(count==0){
				
			}else{
				for ( var i = 0; i < selected.length; i++) {
					 
					if(selected[i].END_TIME > selected[i+1].START_TIME
						&&selected[i].START_TIME < selected[i+1].END_TIME){
						 $.messager.show({
							title : '提  示',
							msg : '所选时间重叠，请重新选择!',
							showType : 'show',
							timeout:'1200'//ms
						});
						$('#dg_kanhu_shijian').datagrid('clearChecked');
					 } 
					
				}
			}
			
		}

	</script>
</body>
</html>
