<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../util/head.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<title></title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">开始日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="start_time" id="start_time" onFocus="WdatePicker( { maxDate: '%y-%M-%{%d-1}' }  )" type="text" class="condition-text" />
							</div></td>
						
						<td width="10%">结束日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="end_time" id="end_time" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'start_time\')}',maxDate: '%y-%M-%{%d-1}' })" type="text" class="condition-text" />
							</div></td>
						
							<td width="10%">区域：</td>
						<td width="20%">
							
							
							<div>
								<select name="org_id" id="org_id" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${scopeList }" var="scope">
										<option value='${scope.ORG_ID}'>${scope.ORG_NAME}</option>
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
	</div>
    <table id="dg" title="【】" style="width:100%" class="common-table">
		<thead id="tsh">
		   
		  </thead>
		<tbody id="tsb">
		</tbody>
	</table>
	
	
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
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
				toolbar : '#tb',
				url : webPath + "xxdReportController/qualityReportByscope.do",
				queryParams : obj,
				method : 'post',
				//pagination : true,
				//pageNumber : 1,
				//pageSize : 10,
				//pageList : [ 5, 10, 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : false,
				//
				columns : [ [ {
					field : 'ORG_NAME',
					title : '区域名称',
					width : "10%",
					align : 'center'
				},{
					field : 'COUNT_STAFF_ID',
					title : '包线人数',
					width : "10%",
					align : 'center'
				}, {
					field : 'AVG_XUNJIAN_RATE',
					title : '巡检平均到位率',
					width : "10%",
					align : 'center'
				},{
					field : 'AVG_SITE_RATE',
					title : '外力点平均巡检到位率',
					width : "10%",
					align : 'center'
				}, {
					field : 'AVG_ARRIVAL_RATE',
					title : '关键点平均巡检到位率',
					width : "10%",
					align : 'center'
				},{
					field : 'AVG_RATEOFKANHU',
					title : '外力点平均看护到位率',
					width : "10%",
					align : 'center'
				}, {
					field : 'AVG_TIME_VALID_ALLDAY', 
					title : '巡线平均时长',
					width : "10%",
					align : 'center'
				},{
					field : 'AVG_TIME_VALID_MORNING', 
					title : '巡线上午平均时长',
					width : "10%",
					align : 'center'
				},{
					field : 'AVG_TIME_VALID_AFTERNOON', 
					title : '巡线下午平均时长',
					width : "10%",
					align : 'center'
				},{
					field : 'DATE_TIME', 
					title : '巡线日期',
					width : "10%",
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
		

		function clearCondition(form_id) {
			
			$("#"+form_id+"").form('reset', 'none');
			
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
			
			window.open(webPath + "xxdReportController/qualityReportByScopeDownload.do"+getParamsForDownloadLocal('form'));
		}
	</script>

</body>
</html>
