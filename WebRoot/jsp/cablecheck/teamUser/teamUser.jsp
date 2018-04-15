<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<style type="text/css">
a{text-decoration:none;}
</style>
<style type="text/css">
body, html, #allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

.condition {
	width: 100%;
	border: 0px solid;
}

* {
	margin: 0;
	padding: 0;
	list-style-type: none;
}

a, img {
	border: 0;
}

body {
	font: 12px/180% Arial, Helvetica, sans-serif, "新宋体";
}

.selectbox .select-bar {
	padding: 0 20px;
}

.selectbox .select-bar select {
	width: 150px;
	height: 200px;
	border: 4px #A0A0A4 outset;
	padding: 4px;
}

.selectbox .btn {
	width: 50px;
	height: 30px;
	margin-top: 10px;
	cursor: pointer;
}

.chartOptionsFlowTrend{
    z-index:300;
    background-color:white;
	border:1px solid gray;
	display:none;
	position: absolute;
	left:0px;
	top:23px;
	width:150px;
}
.chartOptionsFlowTrend ul{
	float:left;
	padding: 0px;
	margin: 5px;
}
.chartOptionsFlowTrend li{
	display:block;
	position: relative;
	left:0px;
	margin: 0px;
	clear:both;
}
.chartOptionsFlowTrend li *{
	float:left;
}
.chartOptionsFlowTrend p {
	height: 23px;
	line-height: 23px;
	overflow: hidden;
	position: relative;
	z-index: 2;
	background: #fefbf7;
	padding-top: 0px;
	display: inline-block;
}
.chartOptionsFlowTrend p a {
	border: 1px solid #fff;
	margin-left: 15px;
	color: #2e91da;
}
.select_checkBox{
	/* border:0px solid red; */
	position: relative;
	display:inline-block;
}	
</style>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<title>班组人员管理</title>
	</head>
	<body style="padding: 3px; border: 0px">
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<table class="condition">
						<tr>
							<td width="8%">人员账户/姓名：</td>
							<td width="13">
								<div class="condition-text-container">
									<input name="staff_name" type="text" class="condition-text" />
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div class="btn-operation" onClick="searchData()">查询</div>
				<div class="btn-operation" onClick="deleteUser()">删除</div>
			</div>
		</div>
		<table id="dg" title="班组人员" style="width: 100%; height: 480px">
		</table>
		<div id="win_company"></div>
		<div id="win_staff"></div>
		<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
		
		
		function searchData(){
			var staff_name = $("input[name='staff_name']").val();
			var team_id = ${team_id};
			var obj = {
				staff_name : staff_name,
				team_id : team_id
			};
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "teamUser/listAll.do?",
				method : 'post',
				queryParams : obj,
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [20,50,100],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				fit : true,
				singleSelect : false,
				remoteSort: false,
				columns : [[
			            {
			            	field : 'TEAM_ID',
							title : '',
							rowspan : '2', 
							checkbox : true	
			            },
			            {
							field : 'TEAM_NAME',
							title : '班组名称',
							width : "30%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						},{
							field : 'STAFF_ID',
							title : '人员ID',
							hidden:true
						},{
							field : 'STAFF_NO',
							title : '人员账户',
							width : "15%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						},{
							field : 'STAFF_NAME',
							title : '人员姓名',
							width : "15%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						},{
							field : 'MOBLEPHONE',
							title : '手机号',
							width : "15%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						}
						]],
						nowrap : false,
						striped : true,
						onLoadSuccess : function(data) {
							$("body").resize();
						}
			});	
		}
		
		function closeForm(){
			$('#win_company').window('close');
		}
		
		function returnSelect(){
			var selected = $('#dg').datagrid('getChecked');
			var arr = new Array();
			for ( var i = 0; i < selected.length; i++) {
				var value = selected[i].STAFF_ID;
				arr[arr.length] = value;
			}
			return arr.join(",");
		}
		
		function deleteUser(){
			$.messager.confirm('系统提示', '您确定要删除选中的人员吗?', function(r) {
				if (r) {
					var team_id = ${team_id};
					var selectUserId = returnSelect();
					$.ajax({
						type : 'POST',
						url : webPath + "MainTainCompany/updateTreeStatus.do",
						data : {
							node_id:selectUserId,
							parent_id:team_id,
							isParent:false
						},
						dataType : 'json',
						success : function(json) {
							$.messager.alert("提示",json.desc,"info");
							searchData();
						}
					});
				}
			});
		}
		
	</script>
</html>