<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>确认检查实施人</title>
</head>
<body style="padding:3px;border:0px" >
	<form id="form_task" action="" method="post">
		<input name="selected_staffId" type="hidden" />
		<input name="selected_planId" type="hidden"/>
		<input name="inspector_type" type="hidden" />
	</form>
	<div id="tb_staff" style="padding:5px;height:auto">
	<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
					<td width="10%">帐号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="STAFF_NO" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">姓名：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="STAFF_NAME" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="10%">地市：</td>
						<td width="20%">
							<select id="staff_AREA_ID" name="staff_AREA_ID" class="condition-select"  onchange="getSonAreaList_select_staff()">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td width="10%">区县：</td>
						<td width="20%">
							<select id="staff_SON_AREA_ID" name="staff_SON_AREA_ID" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container" >
			<div class="btn-operation" onClick="queryStaffby()" >查询</div>
			<div class="btn-operation" onClick="assignRolePermissions()">角色赋权</div>  <!-- sureCheckStaff -->
		</div>
	</div>
	<table id="dg_plan" title="【设置审核人和接单人】" style="width:100%;height:480px"></table>
    <div id="win_staff_role" ></div>
	<script type="text/javascript">
		$(document).ready(function() {
			/* queryStaff(); */
		});
		function queryStaffby(){
			queryStaff();
		}
		function queryStaff() {
			var STAFF_NAME = $("input[name='STAFF_NAME']").val().trim();
			var STAFF_NO = $("input[name='STAFF_NO']").val().trim();
			var AREA_ID = $("select[name='staff_AREA_ID']").val().trim();
			var SON_AREA_ID = $("select[name='staff_SON_AREA_ID']").val().trim();
			var obj = {
					/* STAFF_NAME : STAFF_NAME,
					STAFF_NO : STAFF_NO,
					SON_AREA_ID : SON_AREA_ID,
					AREA_ID : AREA_ID */
					staff_name : STAFF_NAME,
					staff_no : STAFF_NO,
					son_area : SON_AREA_ID,
					area : AREA_ID
			};
			//return;
			$('#dg_plan').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 600,
				height : 600,
				toolbar : '#tb_staff',
				/* url : webPath + "teamManager/query_staff.do", */
				url : webPath + "Staff/query.do",
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
					field : 'STAFF_ID',
					title : '员工ID',
					checkbox : true
				}, {
					field : 'STAFF_NO',
					title : '员工编码',
					width : "10%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '员工名称',
					width : "10%",
					align : 'center'
				}, {
					field : 'TEL',
					title : '手机号码',
					width : "10%",
					align : 'center'
				}
				] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {}
			});
		}
		
		
		
		/////////////////////////////////////更新审核岗/接单人//////////////////////////////////////////
		function sureCheckStaff() {
		    var selected = $('#dg_plan').datagrid('getChecked');
		    var count = selected.length;
		    if (count == 0) {
		        $.messager.show({
		            title: '提  示',
		            msg: '请选取检查实施人!',
		            showType: 'show'
		        });
		        return;
		    } else {
		    	var arr = new Array();
		        for (var i = 0; i < selected.length; i++) {
		            var value = selected[i].STAFF_ID;
		            arr[arr.length] = value;
		        }
		        
		        ///更新审核人和接单人字段
		        updateStaff(arr.join(","),returnSelect());
		        
		        $('#win_staff').window("close");
		      /*   searchData(); */
		    }
		}
		
		
		function updateStaff(staff_ids,team_ids){
			$.ajax({
				type : 'POST',
				url : webPath + "teamManager/updateShenhe.do",
				data : {
					staff_ids : staff_ids,
					team_ids : team_ids
				},
				dataType : 'json',
				success : function(json) {
					$.messager.alert("提示",json.desc,"info");
					closeForm();
					/* searchData(); */
				}
			});
		}
		
		
		////////////////////////////////////更新兜底岗//////////////////////////////////
		function sureCheckStaff_doudi() {
		    var selected = $('#dg_plan').datagrid('getChecked');
		    var count = selected.length;
		    if (count == 0) {
		        $.messager.show({
		            title: '提  示',
		            msg: '请选取检查实施人!',
		            showType: 'show'
		        });
		        return;
		    } else {
		    	var arr = new Array();
		        for (var i = 0; i < selected.length; i++) {
		            var value = selected[i].STAFF_ID;
		            arr[arr.length] = value;
		        }
		        
		        ///更新审核人和接单人字段
		        updateStaff_doudi(arr.join(","),returnSelect());
		        
		        $('#win_staff').window("close");
		        /* searchData(); */
		    }
		}
		
		
		function updateStaff_doudi(staff_ids,team_ids){
			$.ajax({
				type : 'POST',
				url : webPath + "teamManager/updateDoudi.do",
				data : {
					staff_ids : staff_ids,
					team_ids : team_ids
				},
				dataType : 'json',
				success : function(json) {
					$.messager.alert("提示",json.desc,"info");
					closeForm();
					searchData();
				}
			});
		}
		
		
		
		
		//////////////////////////////////////////////////////////////////////
		
		function assignRolePermissions() {
		    var selected = $('#dg_plan').datagrid('getChecked');
		    var count = selected.length;
		    if (count == 0) {
		        $.messager.show({
		            title: '提  示',
		            msg: '请选取一条数据!',
		            showType: 'show'
		        });
		        return;
		    }
		    /* else if (count > 1) {
		        $.messager.show({
		            title: '提  示',
		            msg: '仅能选取一条数据!',
		            showType: 'show'
		        });
		        return;
		    } */
		    else {
		        var staff_id = selected[0].STAFF_ID;
		        
		        var arr = new Array();
		        for (var i = 0; i < selected.length; i++) {
		            var value = selected[i].STAFF_ID;
		            arr[arr.length] = value;
		        }
		        
		        $("#win_staff_role").window({
		            title: "【角色赋权】",
		            href : webPath + "teamManager/assignRolePermissions.do?staff_id="+staff_id,
		            	   /* +"&staff_ids="+arr.join(",")
						   +"team_ids="+returnSelect(), */
					width : 500,
					height : 500,
		            zIndex: 2,
		            region: "center",
		            collapsible: false,
		            cache: false,
		            modal: true
		        });
		    }
		}

	</script>
</body>
</html>