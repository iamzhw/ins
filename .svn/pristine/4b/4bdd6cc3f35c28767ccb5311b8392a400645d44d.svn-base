
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>外力点迁移日志</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<table id="dg" title="【看护计划管理】" style="width: 100%; height: 480px">
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						
						<td >原经度：</td>
						<td >
							<div class="condition-text-container">
								<input name="old_longitude" id="old_longitude" class="condition-text"  />
							</div>
						</td>
						<td >原纬度：</td>
						<td >
							<div class="condition-text-container">
								<input name="old_latitude" id="old_latitude" class="condition-text"  />
							</div>
						</td>
					
					<td >外力点名称：</td>
						<td >
							<div class="condition-text-container">
								<input name="site_name" id="site_name"  class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="edit_movelog()">编辑</div>
			<div  class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			<div  class="btn-operation"
				onClick="searchData()">查询</div>
				<!--  <div class="btn-operation" onClick="look_baobiao()">巡线时长报表</div>-->
		</div>
	</div>
	<div id="win_job"></div>
	<div id="win_look_baobiao"></div>
	</div>
	
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
		
		function searchData() {
			var old_longitude =$("#old_longitude").val();
			var old_latitude =$("#old_latitude").val(); 
			var site_name=$("#site_name").val();
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "outsitePlanManage/query_movelog.do",
				queryParams : {old_longitude:old_longitude,old_latitude:old_latitude,site_name:site_name},
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'MOVE_ID',
					title : 'ID',
					width : '12%',
					align : 'center',
					checkbox : true
				},{
					field : 'SITE_NAME',
					title : '外力点',
					width : '18%',
					align : 'center'
				}, {
					field : 'OLD_LONGITUDE',
					title : '原经度',
					width : "12%",
					align : 'center'
				}, {
					field : 'OLD_LATITUDE',
					title : '原纬度',
					width : "12%",
					align : 'center'
				},{
					field : 'NEW_LONGITUDE',
					title : '新经度',
					width : "12%",
					align : 'center'
				},{
					field : 'NEW_LATITUDE',
					title : '新纬度',
					width : "12%",
					align : 'center'
				},{
					field : 'STAFF_NAME',
					title : '迁移人',
					width : "10%",
					align : 'center'
				},{
					field : 'MOVE_TIME',
					title : '迁移时间',
					width : "15%",
					align : 'center'
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
				
			});
		}

		function clearCondition(form_id) {
			$("#"+form_id+"").form('reset', 'none');
		}
//页面跳转
		function edit_movelog() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show',
					 timeout:'1000'//ms
				});
				return;
			} else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show',
					timeout:'1000'//ms
				});
				return;
			} else {
				
				var MOVE_ID = selected[0].MOVE_ID;
				$('#win_job').window({
					title : "【编辑迁移日志】",
					href : webPath + "outsitePlanManage/toUpdate_movelog.do?move_id=" + MOVE_ID,
					width : 400,
					height : 300,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				}); 
			}

		}
	//更新数据
		function saveForm_update_movelog() {
			var pass = $("#form_update").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要更新日志吗?', function(r) {
					if (r) {
						var obj=makeParamJson('form_update');
						$.ajax({
							type : 'POST',
							url : webPath + "outsitePlanManage/update_outsite_movelog.do",
							data : obj,
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '更新日志成功!',
										showType : 'show',
										timeout:'1000'//ms
									});
								}
								$('#win_job').window('close');
								searchData();
							}
						})
					}
				});
			}
		}
	function test() {
						$.ajax({async:false,
							  type:"post",
							  url : webPath + "outsitePlanManage/test.do",
							  //data:{ids:$("input[name='selected']").val()},
							  dataType:"json",
							  success:function(data){
							  }
						  });
		}
function look_baobiao() {
			$('#win_look_baobiao').window(
							{
								title : "【巡线时长】",
								href : webPath
										+ "outsitePlanManage/get_baobiao.do",
								width : 680,
								height : 420,
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
