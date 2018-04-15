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
			
			<div id="dialog">
			</div>
    
	<script type="text/javascript">
		
		$(document).ready(function(){
			
			getDetailData();
		})
	
		function getDetailData(){
			
			
		  			$('#dg_detail').datagrid({
						//此选项打开，表格会自动适配宽度和高度。
						//width : '1600',
						height: '400',
						width : '2800',
						//autoSize : true,
						url : webPath + "mainOutSiteController/getCheckDetail.do",
						queryParams: {out_site_id:${out_site_id}},
						method : 'post',
						//pagination : true,
						//pageNumber : 1,
						//pageSize : 100,
						//pageList : [ 100 ],
						loadMsg:'数据加载中.....',
						rownumbers : true,
						singleSelect : true,
						fitColumns:true,
						
						nowrap : false,
						striped : true,
						
						columns : [ [ 
						
								{
									field : 'OUT_RECORD_ID',
									title : 'ID',
									checkbox : true
								}, {
									field : 'CHECK_TIME',
									title : '检查时间',
									width : "10%",
									align : 'center'
								},{
									field : 'CREATE_PERSON',
									title : '检查人',
									width : "10%",
									align : 'center'
								},{
									field : 'OUT_SITE_NAME',
									title : '看护现场名称',
									width : "10%",
									align : 'center'
								},{
									field : 'IS_ON_OUT',
									title : '看护人员在场情况',
									width : "10%",
									align : 'center',
									formatter : function(value, rec,
										index) {
									if (value=='1') {
										return "是";
									} else {
										return "否";
									}
										
								}
								},{
									field : 'IS_CONSTRUCTION',
									title : '现场是否施工',
									width : "10%",
									align : 'center',
									formatter : function(value, rec,
										index) {
									if (value=='1') {
										return "是";
									} else {
										return "否";
									}
										
								}
								},{
									field : 'IS_COMPLETE_EQUIP',
									title : '看护人员防护用品是否完整',
									width : "10%",
									align : 'center',
									formatter : function(value, rec,
										index) {
									if (value=='1') {
										return "是";
									} else {
										return "否";
									}
										
								}
								},{
									field : 'MISS_ITEM_NAME',
									title : '缺项名称',
									width : "10%",
									align : 'center'
								},{
									field : 'IS_COMPLETE_LOOK',   
									title : '看护记录是否完整',
									width : "10%",
									align : 'center',
									formatter : function(value, rec,
										index) {
									if (value=='1') {
										return "是";
									} else {
										return "否";
									}
										
								}
								},{
									field : 'MISS_ITEM_RECORD',
									title : '看护记录缺项名',
									width : "10%",
									align : 'center'
								},{
									field : 'IS_KNOW_CON',
									title : '现场施工计划知否',
									width : "10%",
									align : 'center',
									formatter : function(value, rec,
										index) {
									if (value=='1') {
										return "是";
									} else {
										return "否";
									}
										
								}
								},{
									field : 'IS_THAN_TEN_MAC',
									title : '现场施工机械是否在10M范围内',
									width : "10%",
									align : 'center',
									formatter : function(value, rec,
										index) {
									if (value=='1') {
										return "是";
									} else {
										return "否";
									}
										
								}
								},{
									field : 'MISS_ITEM_MAC',
									title : '施工机械缺项名称',
									width : "10%",
									align : 'center'
								},{
									field : 'IS_KNOW',
									title : '线路位置和埋深熟悉情况',
									width : "10%",
									align : 'center'
								},{
									field : 'LEADER_TYPE',
									title : '领导人类型',
									width : "10%",
									align : 'center'
								},{
									field : 'LEADER_NAME',
									title : '领导人名称',
									width : "10%",
									align : 'center'
								},{
									field : 'PROBLEM_MES',
									title : '发现问题',
									width : "10%",
									align : 'center'
								},{
									field : 'SIDE_PROGRESS',
									title : '施工进度',
									width : "10%",
									align : 'center'
								},{
									field : 'RECTIFICATION',
									title : '整改情况',
									width : "10%",
									align : 'center'
								},{
									field : 'ASSESS',
									title : '考核情况',
									width : "5%",
									align : 'center'
								},{
									field : 'SUB_COMPARY_NO',
									title : '分公司编号',
									width : "5%",
									align : 'center'
								},{
									field : 'SUB_COMPARY_NAME',
									title : '分公司名称',
									width : "10%",
									align : 'center'
								},{
									field : 'AREA_NAME',
									title : '所属地市',
									width : "10%",
									align : 'center'
								},{
									field : 'CREATION_TIME',
									title : '创建时间',
									width : "10%",
									align : 'center'
								},{
									field : 'UPDATE_TIME',
									title : '修改时间',
									width : "10%",
									align : 'center'
								},{
									field : 'UPDATED_PERSON',
									title : '修改人',
									width : "10%",
									align : 'center'
								},{
									field : 'OPR',
									title : '详情',
									width : "10%",
									align : 'center',
									formatter : function(value, rec,
											index) {
										return "&nbsp;&nbsp;<a  style='color:blue' onclick='PhotoDetail(\""
										+ rec.OUT_RECORD_ID+"\",\""+rec.CREATED_BY
										+ "\")'>查看</a>";
									}
								}
						] ]
						
						
						
					});
		  		
	  	}
	  	
		function PhotoDetail(out_site_id,staff_id){
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "mainOutSiteController/getOutsitePhoto.do?out_site_id="+out_site_id+"&staff_id="+staff_id,
				  dataType:"json",
				  success:function(data){
					  $("#dialog").empty();
					   $.each(data,function(i,item){
<%--						 console.log(item.PHOTO_PATH);--%>
						 $("#dialog").append("<img src=\""+item.MICRO_PHOTO+"\"/>");
					   });
				  }
			  });
			$("#dialog").dialog({
				title: '处理结果',    
			    width: 500,    
			    height: 500,    
			    closed: false,    
			    cache: false,    
			    modal: true,
				resizable:true
            });
			
		}
		
		
	</script>
    
  </body>
  
  
</html>
