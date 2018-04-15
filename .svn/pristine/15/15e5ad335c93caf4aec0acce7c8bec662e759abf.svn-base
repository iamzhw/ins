<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <%@include file="../../util/head.jsp"%>
    <title></title>
  </head>
  
  <body>
			<div style="margin-top: 5px">
			   <table id="dg_detail"></table>
			</div>
	<script type="text/javascript">
		
		$(document).ready(function(){
			
			getDetailData();
		});
	
		function getDetailData(){
  			$('#dg_detail').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				height: '360',
				width : '860',
				url : webPath + "mainOutSiteController/getFlowDetail.do",
				queryParams: {out_site_id:${out_site_id}},
				method : 'post',
				loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				nowrap : false,
				striped : true,
				
				columns : [ [ 
					 {
						field : 'FLOW_STATUS',
						title : '流程节点',
						width : "18%",
						align : 'center',
						formatter : function(value, rec,index) {
							if(value==0){
								return "巡线员上报";
							}else if(value==1){
								return "班组长审核";
							}else if(value==2){
								return "县分管领导确认";
							}else if(value==3){
								return "市分管领导确认";
							}else if(value==4){
								return "县主要管理和技术人员确认";
							}else if(value==5){
								return "市主要管理和技术人员确认";
							}else{
								return "";
							}
						}
					},{
						field : 'REVIEW_STAFF',
						title : '处理人',
						width : "10%",
						align : 'center'
					},{
						field : 'REVIEW_STATUS',
						title : '审批状态',
						width : "10%",
						align : 'center',		
						formatter : function(value, rec,index) {
							if(value==1){
								return "通过";
							}else if(value==0){
								return "驳回";
							}else{
								return "";
							}
						}
					},{
						field : 'OPINON',
						title : '审批意见',
						width : "12%",
						align : 'center'
					},{
						field : 'REVIEW_TIME',
						title : '处理时间',
						width : "15%",
						align : 'center'
					},{
						field : 'IS_SCENCE',
						title : '是否现场确认',
						width : "10%",
						align : 'center',
						formatter : function(value, rec,index) {
							if(value==1){
								return "是";
							}else if(value==0){
								return "否";
							}else{
								return "";
							}
						}
					}] ]
			});
		  		
	  	}
		
	</script>
  </body>
</html>
