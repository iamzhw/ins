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
							
<!--						<td width="10%">区域：</td>-->
<!--						<td width="20%">-->
<!--							<div>-->
<!--								<select name="area_id" id="area_id" class="condition-select">-->
<!--									<option value=''>--请选择--</option>-->
<!--									<c:forEach items="${area}" var="areaInfo">-->
<!--										<option value='${areaInfo.AREA_ID}'>${areaInfo.NAME}</option>-->
<!--									</c:forEach>-->
<!--								</select>-->
<!--							</div>-->
<!--						</td>	-->
					
<!--						<td width="10%">组织名称：</td>-->
<!--							<td width="20%">-->
<!--							<div>-->
<!--								<select name="org_id" id="org_id" class="condition-select">-->
<!--									<option value=''>--请择--</option>-->
<!--<%-- 									<c:forEach items="${scopeList}" var="org"> --%>-->
<!--<%-- 										<option value='${org.ORG_ID}'>${org.ORG_NAME}</option> --%>-->
<!--<%-- 									</c:forEach> --%>-->
<!--								</select>-->
<!--							</div>-->
<!--						</td>	-->
						
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
				toolbar : '#tb',
				url : webPath + "xxdReportController/getProvinceStepTourCondition.do",
				queryParams : obj,
				method : 'post',
				//pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : false,
				//
				columns : [
				[
				{field:'NAME',title:'地区',rowspan:3,align:'center',width:"5%"},

			
				
				{title:'一干 ',width:"20%",colspan:5},
				
				{title:'二干 ',width:"20%",colspan:5},
				
				{title:'地标 ',width:"20%",colspan:5},
				
			
				
				{field:'SEARCH_DATE',title:'查询时间',rowspan:3,align:'center',width:"10%",sortable:true}
				
				],[
				{field:'FIBER_1_NUM',title:'计划数量',width:80,align:'center',sortable:true},
				
				{field:'FIBER_1_ACT',title:'已完成数量',width:80,align:'center',sortable:true},
				
				{field:'DANGER_NUM1',title:'问题',width:80,align:'center',sortable:true},
				
				{field:'FIBER_1_RATE',title:'完成比率',width:80,align:'center',sortable:true},
				
				{field:'DANGER_NUM1_RATE',title:'完好率',width:80,align:'center',sortable:true},
				
				{field:'FIBER_2_NUM',title:'计划数量',width:80,align:'center',sortable:true},
				
				{field:'FIBER_2_ACT',title:'已完成数量',width:80,align:'center',sortable:true},
				
				{field:'DANGER_NUM2',title:'问题',width:80,align:'center',sortable:true},
				
				{field:'FIBER_2_RATE',title:'完成比率',width:80,align:'center',sortable:true},
				
				{field:'DANGER_NUM2_RATE',title:'完好比率',width:80,align:'center',sortable:true},
				
				{field:'LANDMARK_NUM',title:'计划数量',width:80,align:'center',sortable:true},
				
				{field:'LANDMARK_ACT',title:'已完成数量',width:80,align:'center',sortable:true},
				
				{field:'DANGER_LANDMARK',title:'问题',width:80,align:'center',sortable:true},
				
				{field:'LANDMARK_RATE',title:'完成比率',width:80,align:'center',sortable:true},
				
				{field:'DANGER_LANDMARK_RATE',title:'完好比率',width:80,align:'center',sortable:true}
			
				
				
				
			
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

		
		function inspectArDownload(){
			if($("#start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			
			window.open(webPath + "xxdReportController/getProvinceStepTourConditionDown.do"+getParamsForDownloadLocal('form'));
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
