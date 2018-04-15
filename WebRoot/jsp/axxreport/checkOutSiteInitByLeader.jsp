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
								<input name="start_time" id="start_time" onFocus="WdatePicker()" type="text" class="condition-text" />
							</div></td>
						
						<td width="10%">结束日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="end_time" id="end_time" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'start_time\')}'})" type="text" class="condition-text" />
							</div></td>
							
						<td width="10%">区域：</td>
						<td width="20%">
							<div>
								<select name="area_id" id="area_id" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${area}" var="areaInfo">
										<option value='${areaInfo.AREA_ID}'>${areaInfo.NAME}</option>
									</c:forEach>
								</select>
							</div>
						</td>	
					
						<td width="10%">组织名称：</td>
							<td width="20%">
							<div>
								<select name="org_id" id="org_id" class="condition-select">
									<option value=''>--请选择--</option>
<%-- 									<c:forEach items="${scopeList}" var="org"> --%>
<%-- 										<option value='${org.ORG_ID}'>${org.ORG_NAME}</option> --%>
<%-- 									</c:forEach> --%>
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
		
	
		//selectSelected();
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
				url : webPath + "xxdReportController/checkOutSiteByLeader.do",
				queryParams : obj,
				method : 'post',
				//pagination : true,
				//pageNumber : 1,
				//pageSize : 10,
				//pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : false,
				//
				columns : [ [ {
					field : 'NAME',
					title : '单位',
					width : "10%",
					align : 'center'
				},{
					field : 'STAFF_NAME',
					title : '姓名',
					width : "10%",
					align : 'center'
				}, {
					field : 'ORG_NAME',
					title : '组织关系',
					width : "10%",
					align : 'center'
				}, {
					field : 'COUNT_OUT_SITE_ID',
					title : '检查日期/检查点数',
					width : "10%",
					align : 'center'
				},{
					field : 'COUNT_DAY',
					title : '检查天数',
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

		
		function inspectArDownload(){
			if($("#start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			
			window.open(webPath + "xxdReportController/checkOutSiteByLeaderDownload.do"+getParamsForDownloadLocal('form'));
		}
	
		
		$("#area_id").change(
				function changeScope(){
				  	$("#org_id option:gt(0)").remove();
					var jv = {area_id:this.value};
					if(!this.value) return;
					$.ajax({
					  async:false,
					  type:"post",
					  url :webPath + "xxdReportController/getScopeList.do",
					  data:jv,
					  dataType:"json",
					  success:function(data){
					  	$(data).each(function(){
							$("#org_id").append("<option value='"+this.ORG_ID+"'>"+this.ORG_NAME+"</option>");  
					  	});
					  }
						
					});
				}		
		);
	</script>

</body>
</html>
