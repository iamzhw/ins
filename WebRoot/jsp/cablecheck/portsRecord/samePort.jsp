<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<title>端子变动记录</title>
	</head>
  
  <body style="padding: 3px; border: 0px">
  		<input name="area" value="${area}" id="area" type="hidden"/>
		<input name="son_area" value="${son_area}" id="son_area" type="hidden"/>
		<input name="whwg" value="${whwg}" id="whwg" type="hidden"/>
		<input name="dz_start_time" value="${dz_start_time}" id="dz_start_time" type="hidden"/>
		<input name="dz_end_time" value="${dz_end_time}" id="dz_end_time" type="hidden"/>
		<input name="static_month" value="${static_month}" id="static_month" type="hidden"/>	
		<input name="PORT_ID" value="${PORT_ID}" id="PORT_ID" type="hidden"/>		
		<input name="check_start_time" value="${check_start_time}" id="check_start_time" type="hidden"/>
		<input name="check_end_time" value="${check_end_time}" id="check_end_time" type="hidden"/>
		<!-- <div id="tb" style="padding: 5px; height: auto">
			<div class="btn-operation-container">
				<div style="float:right;margin-right:30px" class="btn-operation" onClick="searchData()">查询</div>
			</div>
		</div> -->
		<table id="dg" title="【端子记录】" style="width: 100%; height: 480px"></table>
	</body>
	<script type="text/javascript">
		$(document).ready(function(){
			searchData();
		});
		function searchData() {
			var area = $("#area").val();
			var son_area = $("#son_area").val();
			var whwg = $("#whwg").val();
			var dz_start_time = $("#dz_start_time").val();
			var dz_end_time = $("#dz_end_time").val();
			var static_month = $("#static_month").val();//端子变动月份
			var check_start_time = $("#check_start_time").val();//检查开始时间
			var check_end_time = $("#check_end_time").val();//检查结束时间
		    var PORT_ID = $("#PORT_ID").val();
		    
			if(area==null || ""==area){
				var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
				var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
				var isAdmin1 ='${CABLE_ADMIN}';
				if(true ==isAdmin1 || isAdmin1 =='true'){
					
				}
				if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
				 	 area=${areaId};
				}
				if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
				 	 area=${areaId};
					 son_area =${sonAreaId};
			    }		    
			}
			
			var obj = {
				area:area,
				son_area:son_area,
				whwg:whwg,
				dz_start_time:dz_start_time,
				dz_end_time:dz_end_time,
				static_month:static_month,
				check_start_time:check_start_time,
				check_end_time:check_end_time,
				PORT_ID:PORT_ID
			};
			$('#dg').datagrid(
					{
						//此选项打开，表格会自动适配宽度和高度。
						autoSize : true,
						toolbar : '#tb',
						url : webPath + "CheckRecord/querySamePortRecord.do",
						queryParams : obj,
						method : 'post',
						pagination : true,
						pageNumber : 1,
						pageSize : 10,
						pageList : [20,50],
						rownumbers : true,
						fit : true,
						singleSelect : false,
						remoteSort: false,
						columns : [ [
								{
									field : 'EQP_NO',
									title : '设备编码',
									width : "10%",
									rowspan : '2',
									align : 'center'
								},{
									field : 'EQP_NAME',
									title : '设备名称',
									width : "10%",
									rowspan : '2',
									align : 'center'
								},{
									field : 'PORT_ID',
									title : '端子ID',
									width : "10%",
									rowspan : '2',
									align : 'center'
								},{
									field : 'PORT_NO',
									title : '端子编码',
									width : "12%",
									rowspan : '2',
									align : 'center'
								},
								{
									field : 'ISCHECKOK',
									title : '是否合格',
									width : "7%",
									rowspan : '2',
									align : 'center'
								},
								{
									field : 'CREATE_STAFF',
									title : '检查人',
									width : "7%",
									rowspan : '2',
									align : 'center'
								},
								{
									field : 'CREATE_TIME',
									title : '检查时间',
									width : "7%",
									rowspan : '2',
									align : 'center'
								} ] ],
						nowrap : false,
						striped : true,
						onLoadSuccess : function(data) {
							$(this).datagrid("fixRownumber");
							$("body").resize();
						}
			});
		}
	
	</script>
</html>
