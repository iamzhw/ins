<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<title></title>
</head>
<body>
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">开始日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="start_time" id="start_time" onFocus="WdatePicker()" type="text" class="condition-text" />
							</div></td>
						
						<td width="10%">结束日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="end_time" id="end_time" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'start_time\')}'})" type="text" class="condition-text" />
							</div></td>
							
							<td width="10%">组织名称：</td>
							<td width="20%">
							<div>
								<select name="org_id" id="org_id" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${orgList}" var="org">
										<option value='${org.ORG_ID}'>${org.ORG_NAME}</option>
									</c:forEach>
								</select>
							</div>
						   </td>
							
							<td width="10%">巡检人：</td>
						<td width="20%">
							<div>
								<select name="inspect_id" id="inspect_id" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${localInspactStaff}" var="res">
										<option value='${res.STAFF_ID}'>${res.STAFF_NAME}</option>
									</c:forEach>
								</select>
							</div>
							</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			
			<div style="float: left;" class="btn-operation"
				onClick="inspectArDownload()">导出</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
    <div id="dg" style="width:90%;height:90%;"></div>
	
</body>
<script type="text/javascript">
		$(document).ready(function() {
		
			//searchData();
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
			
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				fitColumns:true,
				url : webPath + "mainOutSiteController/queryOutSiteRate.do",
				queryParams : obj,
				method : 'post',
				//pagination : true,
				//pageNumber : 1,
				//pageSize : 10,
				//pageList : [ 5, 10, 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'STAFF_NAME',
					title : '巡线员',
					width : "15%",
					align : 'center'
				}, {
					field : 'ORG_NAME',
					title : '区域',
					width : "15%",
					align : 'center'
				},{
					field : 'START_DATE',
					title : '时间',
					width : "20%",
					align : 'center'
				}, {
					field : 'OUT_SITE_ID',
					title : '计划外力点数',
					width : "15%",
					align : 'center'
				}, {
					field : 'ACTUAL_COUNT',
					title : '实际外力点数',
					width : "15%",
					align : 'center'
				}, 
				{
					field : 'SITE_RATE',
					title : '检查到位率',
					width : "15%",
					align : 'center'
				},
				{
					field : 'AVG_SITE_RATE',
					title : '全市外力点平均到位率',
					width : "20%",
					align : 'center'
				}
				] ],
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

		
		function inspectArDownload(){
			if($("#start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			
			window.open(webPath + "mainOutSiteController/outSiteRateDownLoad.do"+getParamsForDownloadLocal('form'));
		}
	</script>

</html>
