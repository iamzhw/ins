
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
								<input name="start_time" id="start_time" onFocus="WdatePicker()" type="text" class="condition-text"/>
							</div></td>
						
						<td width="10%">结束日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="end_time" id="end_time"  type="text" class="condition-text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'start_time\')}'})"/>
							</div></td>
						
						
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			
			<div style="float: left;" class="btn-operation"
				onClick="inspectTimeDailyDownload()">导出</div>
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
		/////
		
		function searchData() {
			
			if($("#start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			
			
			var obj=makeParamJson('form');
			
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "xxdReportController/getInspectTimeDaily.do",
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
					field : 'AREA_NAME',
					title : '分公司',
					width : "10%",
					align : 'center'
				}, {
					field : 'INS_TIME_VALID_ALLDAY',
					title : '平均每天巡线时长',
					width : "20%",
					align : 'center'
				}, {
					field : 'INS_TIME_VALID_MORNING',
					title : '平均上午巡线时长',
					width : "20%",
					align : 'center'
				}, {
					field : 'INS_TIME_VALID_AFTERNOON',
					title : '平均下午巡线时长',
					width : "20%",
					align : 'center'
				}, {
					field : 'REMARK',
					title : '备注',
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

		
		function inspectTimeDailyDownload(){
			if($("#start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			window.open(webPath + "xxdReportController/inspectTimeDailyDownload.do"+getParamsForDownloadLocal('form'));
		}
		
		
	
		////////////////////////////////////////////////////////
		// 日期转化为yyyy-mm-dd格式
		////////////////////////////////////////////////////////
		function dateToString(DateIn) {
			var Year = 0;
			var Month = 0;
			var Day = 0;

			var CurrentDate = "";
			// 初始化时间
			Year = DateIn.getYear();
			Month = DateIn.getMonth() + 1;
			Day = DateIn.getDate();

			CurrentDate = Year + "-";
			if (Month >= 10) {
				CurrentDate = CurrentDate + Month + "-";
			} else {
				CurrentDate = CurrentDate + "0" + Month + "-";
			}

			if (Day >= 10) {
				CurrentDate = CurrentDate + Day;
			} else {
				CurrentDate = CurrentDate + "0" + Day;
			}
			return CurrentDate;
		}
		
		
		function getEndtime(startDate){
			var sd=new Date(Date.parse(startDate.replace(/-/g,'/')));
			
			sd.setDate(sd.getDate()+6);
			$("#end_time").val(dateToString(sd));
		}
	</script>

</body>
</html>
