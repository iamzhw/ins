
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../util/head.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<title>无效时长</title>
</head>
<body style="padding: 3px; border: 0px">
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
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
							</div>
						</td>
						<td width="10%">巡检人：</td>
						<td width="20%">
							<div>
								<select name="user_id" id="user_id" class="condition-select">
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
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
    <table id="dg" title="【】" style="width: 100%; height: 480px">
	</table>
	
	
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		function searchData() {
			
			if($("#start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			if($("#user_id").val()==''){
				alert("请选择巡检人！");
				return false;
			}
			
			var obj=makeParamJson('form');
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "xxdReportController/getInvalidTime.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 20,
				pageList : [ 20, 50,200 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : false,
				//
				columns : [ [ {
					field : 'STAFF_NAME',
					title : '巡线员',
					width : "20%",
					align : 'center'
				}, {
					field : 'LINE_DATE',
					title : '巡线日期',
					width : "20%",
					align : 'center'
				},{
					field : 'START_TIME',
					title : '开始时间',
					width : "20%",
					align : 'center'
				}, {
					field : 'END_TIME',
					title : '结束时间',
					width : "20%",
					align : 'center'
				}, {
					field : 'INVALID_TYPE',
					title : '无效类型',
					width : "20%",
					align : 'center',
					formatter : function(value, rowData, index){
						if(value == "0"){
							return "GPS未打开";
						}else if(value=="1") {
							return "GPS连续丢失信号";
						}else if(value=="2"){
							return "未匹配到巡线点";
						}else if(value=='3'){
							return "停留时间超过40分钟";
						}else{
							return "无任何数据上传";
						}
					}
				}] ],
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
			
			window.open(webPath + "xxdReportController/xxdDownload.do"+getParamsForDownloadLocal('form'));
		}
	</script>

</body>
</html>
