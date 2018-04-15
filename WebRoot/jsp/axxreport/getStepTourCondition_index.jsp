
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../util/head.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<title>步巡完成情况</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">起始日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="start_time" id="start_time" onFocus="WdatePicker()" type="text" class="condition-text" />
							</div></td>
						<td width="10%">结束日期：</td>
						<td width="20%">
						   <div class="condition-text-container">
								<input name="end_time" id="end_time" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'start_time\')}'})" type="text" class="condition-text" />
							</div>
							</td>
							<td width="10%">分公司：</td>
							<td width="20%">
							<div>
								<select name="org_id" id="org_id" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${map.orgList}" var="org">
										<option value='${org.ORG_ID}'>${org.ORG_NAME}</option>
									</c:forEach>
								</select>
							</div>
							
							</td>
						</tr>
						<tr>	
							
									<td width="10%">周期：</td>
						<td width="20%">	
							<div>
								<select name="cycle" id="cycle" class="condition-select">
									<option value=''>--请选择--</option>
									<option value='2'>一干</option>
									<option value='3'>二干</option>
									<option value='6'>地标</option>
									
								</select>
							</div>
							
							</td>
							<td width="10%">巡检人：</td>
						<td width="20%">	
							<div>
								<select name="user_id" id="user_id" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${map.localInspactStaff}" var="res">
										<option value='${res.STAFF_ID}'>${res.STAFF_NAME}</option>
									</c:forEach>
								</select>
							</div>
							
							</td>
							
						
							
					</tr>
					<tr>
						当前周期提示   一干周期时间:${map.startDate0}至 ${map.endDate0} ,二干周期时间:${map.startDate1}至 ${map.endDate1},地标周期时间 :${map.startDate2}至 ${map.endDate2}
					</tr>
					
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			
			<div style="float: left;" class="btn-operation"
				onClick="xxdDownload()">导出</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
    <table id="dg" title="【】" style="width: 100%; height: 480px">
	</table>
	<div id="win" ></div>
	
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
		
			
		});
		
		
		function searchData() {
			if($("#start_time").val()==''){
					alert("开始日期不能为空！");
					return false;
				}
				if($("#end_time").val()==''){
					alert("结束日期不能为空！");
					return false;
				}
			
			var obj=makeParamJson('form');
			
			if( $("#cycle").val()==''  ){
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "xxdReportController/getStepTourCondition.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : false,
				//
				columns : [
				[
				{field:'STAFF_NAME',title:'步巡人员',rowspan:2,align:'center',width:"10%",sortable:true},

				{field:'ORG_NAME',title:'区域',rowspan:2,align:'center',width:"10%",sortable:true},
				
				{title:'计划步巡点数 ',width:"20%",align:'center',colspan:3},
				
				{title:'实际步巡点数 ',width:"20%",align:'center',colspan:3},
				
				{title:'完成比率  ',width:"20%",align:'center',colspan:3},
				
				{field:'SEARCH_DATE',title:'查询时间',rowspan:2,align:'center',width:"10%",sortable:true},
				
				{field : 'PLANID',title : '操作',rowspan:2,width : "10%",align : 'center',formatter:function(value,rowData,rowIndex){
	              	return "<a  style='color:blue' onclick='flowDetailUI(\""
						+ rowData.INSPECT_ID
						+ "\")'>详情</a>";
	                } 
	               }
				
				],[
				
				{field:'FIBER_1',title:'一干',width:80,align:'center',sortable:true},
				
				{field:'FIBER_2',title:'二干',width:80,align:'center',sortable:true},
				
				{field:'LANDMARK',title:'地标',width:80,align:'center',sortable:true},
				
				{field:'ACT_FIBER_1',title:'一干',width:80,align:'center',sortable:true},
				
				{field:'ACT_FIBER_2',title:'二干',width:80,align:'center',sortable:true},
				
				{field:'ACT_LANDMARK',title:'地标',width:80,align:'center',sortable:true},
				
				{field:'FIBER_1_RATE',title:'一干',width:80,align:'center',sortable:true},
				
				{field:'FIBER_2_RATE',title:'二干',width:80,align:'center',sortable:true},
				
				{field:'LANDMARK_RATE',title:'地标',width:80,align:'center',sortable:true}
				]
				],
				//width : 'auto',
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
       			// $(this).datagrid("fixRownumber");
       			 
				
				}
				
				
			});
			}
			else{
			
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "xxdReportController/getStepTypeTourCondition.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : false,
				//
				columns : [
				[
				{field:'STAFF_NAME',title:'步巡人员',rowspan:2,align:'center',width:"10%",sortable:true},

				{field:'ORG_NAME',title:'区域',rowspan:2,align:'center',width:"10%",sortable:true},
				
				{field:'TOL_NUM',title:'计划步巡点数 ',width:"20%",align:'center'},
				
				{field:'ACT_NUM',title:'实际步巡点数 ',width:"20%",align:'center'},
				
				{field:'RATE',title:'完成比率  ',width:"20%",align:'center'},
				
				{field:'SEARCH_DATE',title:'查询时间',rowspan:2,align:'center',width:"10%",sortable:true},
				
				{field : 'PLANID',title : '操作',rowspan:2,width : "10%",align : 'center',formatter:function(value,rowData,rowIndex){
	              	return "<a  style='color:blue' onclick='flowDetailUI(\""
						+ rowData.INSPECT_ID
						+ "\")'>详情</a>";
	                } 
	               }
				]
				],
				
				//width : 'auto',
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
       			// $(this).datagrid("fixRownumber");
       			 
				
				}
				
				
			});
			}
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
			
			$("#"+form_id+"").form('reset', 'none');
			
		}
		
		
		function flowDetailUI(id){
			addTab("点评内容统计",webPath+ "xxdReportController/getStepDetailUI_index.do?staff_id="+ id);
		}

		function getParamsForDownloadLocal(idOrDom) {
			if (!idOrDom) {
				return;
			}
			var o;
			if (typeof idOrDom == "string") {
				o = $("#" + idOrDom);
			} else {
				o = $(idOrDom);
			}
			var res = "?randomPara=1";
			o.find("input,select").each(function() {
				var o = $(this);
				var tag = this.tagName.toLowerCase();
				var name = o.attr("name");
				if (name) {
					if (tag == "select") {
						if(o.find("option:selected").val()=='all' || o.find("option:selected").val()=='' ){
							res=res+"&"+name+"=";
						}else{
							res=res+"&"+name+"="+o.find("option:selected").val();
						}
						
					} else if (tag == "input") {
						res=res+"&"+name+"="+ o.val();
					}
				}
			});
			return res;
		}



	
		
		function xxdDownload(){
			if($("#start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			
			if( $("#cycle").val()==''){
			window.open(webPath + "xxdReportController/getStepTourConditionDown.do"+getParamsForDownloadLocal('form'));
			}else{
			window.open(webPath + "xxdReportController/getStepTypeTourConditionDown.do"+getParamsForDownloadLocal('form'));
			}
		}
	</script>

</body>
</html>