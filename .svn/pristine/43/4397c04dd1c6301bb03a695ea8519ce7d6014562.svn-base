
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
			<form id="form" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">查询开始日期：</td>
						<td width="10%">
							<div class="condition-text-container">
								<input name="start_query_time" id="start_query_time" onFocus="WdatePicker( { maxDate: '%y-%M-%{%d-1}' }  )" type="text" class="condition-text" />
							</div></td>
						
						<td width="10%">查询结束日期：</td>
						<td width="10%">
							<div class="condition-text-container">
								<input name="end_query_time" id="end_query_time"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'start_query_time\')}',maxDate: '%y-%M-%{%d-1}' })"  type="text" class="condition-text" />
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
					<tr>	
							<td width="10%">看护人：</td>
						<td width="10%">
							<div>
								<select name="khy_id" id="khy_id" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${localKhyStaff}" var="res">
										<option value='${res.STAFF_ID}'>${res.STAFF_NAME}</option>
									</c:forEach>
								</select>
							</div>
							</td>
						<td width="10%">外力点名称：</td>
						<td width="10%">
						<div class="condition-text-container">
							<input name="site_name" id="site_name" type="text" class="condition-text" />
						</div></td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			
			<div style="float: left;" class="btn-operation"
				onClick="osNurseDailyDownload()">导出</div>
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
	<div id="win" ></div>
	
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
		
			//searchData();
		});
		
		
		function searchData() {
			
			if($("#start_query_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#end_query_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			var obj=makeParamJson('form');
			
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				fitColumns:true,
				url : webPath + "xxdReportController/getOsNurseDaily.do",
				queryParams : obj,
				method : 'post',
// 				pagination : false,
// 				pageNumber : 1,
// 				pageSize : 10,
// 				pageList : [ 5, 10, 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : false,
				//
				columns : [ [ {
					field : 'site_name',
					title : '外力点名称',
					width : "15%",
					align : 'center'
				}, {
					field : 'org_name',
					title : '区域名称 ',
					width : "10%",
					align : 'center'
				},{
					field : 'staff_name',
					title : '看护员',
					width : "10%",
					align : 'center'
				},{
					field : 'planTime',
					title : '计划看护时间(小时)',
					width : "8%",
					align : 'center'
				}
// 				,{
// 					field : 'time',
// 					title : '计划看护时间段',
// 					width : "18%",
// 					align : 'center'
// 				}
// 				, {
// 					field : 'timePart',
// 					title : '离开时间段',
// 					width : "40%",
// 					align : 'center'
// 				}
// 				,{
// 					field : 'timePartByValid',
// 					title : '超过10分钟',
// 					width : "40%",
// 					align : 'center'
// 				}
				,{
					field : 'totalTimes',
					title : '累计离开时间(小时)',
					width : "8%",
					align : 'center'
				}, {
					field : 'leaveTimes',
					title : '离开次数',
					width : "8%",
					align : 'center'
				},{
					field : 'workTime',
					title : '累计在岗时长',
					width : "8%",
					align : 'center'
				},{
					field : 'rateOfKanHu',
					title : '看护到位率',
					width : "8%",
					align : 'center'
				},{
					field : 'planId',
					title : '详情',
					width : "8%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
                     return  "<a onclick='showDetail("+value+")' style='cursor: pointer;color:blue;'>详细信息</a>";  
                    } 
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

		
		function osNurseDailyDownload(){
			if($("#start_query_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#end_query_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			window.open(webPath + "xxdReportController/osNurseDailyDownload.do"+getParamsForDownloadLocal('form'));
		}
		
		function showDetail(value){
			var start_query_time = $("#start_query_time").val();
			var end_query_time = $("#end_query_time").val();
			if($("#start_query_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#end_query_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			$('#win').window({
				title : "【无效时间详情】",
				href : webPath + "xxdReportController/showDetailInfo.do?plan_id=" + value+"&start_query_time="+start_query_time+"&end_query_time="+end_query_time,
				width:1000,
				height:400,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true,
				resizable:false
			});
		}
	</script>

</body>
</html>
