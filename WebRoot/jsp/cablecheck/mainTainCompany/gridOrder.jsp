<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<style type="text/css">
a{text-decoration:none;}
</style>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<title>网格工单</title>
	</head>
	<body style="padding: 3px; border: 0px">
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<table class="condition">
						<tr>
							<td width="10">网格：</td>
							<td width="20">
								<div class="condition-text-container">
									<input name="company_name" type="text" class="condition-text" />
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div class="btn-operation" onClick="searchData()">查询</div>
			</div>
		</div>
		<table id="dg" title="网格工单" style="width: 100%; height: 480px">
		</table>
		<div id="win_company"></div>
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
		function searchData(){
			var company_name = $("input[name='company_name']").val();
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "MainTainCompany/gridOrderAll.do",
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [20,50,100],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				fit : true,
				singleSelect : true,
				remoteSort: false,
				columns : [[
			            {
			            	field : 'COMPANY_ID',
							title : '代维公司ID',
							rowspan : '2', 
							checkbox : true	
			            },{
							field : 'COMPANY_NAME',
							title : '代维公司名称',
							width : "40%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						},{
							field : 'BANZU_NAME',
							title : '班组名称',
							width : "40%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						}]],
						nowrap : false,
						striped : true,
						onLoadSuccess : function(data) {
							$("body").resize();
						}
			});	
		}
		
		function getSonAreaList(operator) {
			//	var areaId = $("select[name='area']").val();
			var area_id = 0;
			if ('query' == operator) {
				area_id = $("select[name='area']").find("option:selected")
						.val();
			} else if ('add' == operator) {
				area_id = $("select[name='varea']").find("option:selected")
						.val();
			}
			$.ajax({
				type : 'POST',
				url : webPath + "General/getSonAreaList.do",
				data : {
					areaId : area_id
				},
				dataType : 'json',
				success : function(json) {
					var result = json.sonAreaList;
					if ('query' == operator) {
						$("select[name='son_area'] option").remove();
						$("select[name='son_area']").append(
								"<option value=''>请选择</option>");
						for ( var i = 0; i < result.length; i++) {
							$("select[name='son_area']").append(
									"<option value=" + result[i].AREA_ID + ">"
											+ result[i].NAME + "</option>");
						}
					} else if ('add' == operator) {
						$("select[name='vson_area'] option").remove();
						for ( var i = 0; i < result.length; i++) {
							if(i==0){
								$("select[name='vson_area']").append(
										"<option value=" + result[i].AREA_ID + " selected>"
										+ result[i].NAME + "</option>");
							}else{
								$("select[name='vson_area']").append(
										"<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
							}
						}
					}
					/* getTeam(); */
					getBanzuInfo();
				}
			});
		}
		
		
		function getTeam(){
			var areaId = $("select[name='vson_area']").find("option:selected").val();
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "General/getBanzuByAreaId.do",
				  data:{areaId:areaId},
				  dataType:"json",
				  success:function(data){
					  $("#own_company").empty();
					  $("#own_company").append("<option value=''>--请选择--</option>");		
					  $.each(data.banzu,function(i,item){
						  $("#own_company").append("<option value='"+item.TEAM_ID+"'>"+item.TEAM_NAME+"</option>");		
					  });
				  }
			  });
		}
		
		function getBanzuInfo(){
			var areaId = $("select[name='vson_area']").find("option:selected").val();
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "General/getBanzuByAreaId.do",
				  data:{areaId:areaId},
				  dataType:"json",
				  success:function(json){
					  console.info(json);
					  var result = json.banzu;
					  $("ul[name='ul_name'] li").remove();
					  for ( var i = 0; i < result.length; i++) {	
						  $("ul[name='ul_name']").append(
						  "<li class=''><input style='height:22px;' name='checkboxxz' type='checkbox' value="
						  +result[i].TEAM_ID+"><span>"+result[i].TEAM_NAME+"</span></li>");
					  }
				  }
			  });
		}
		
		function closeForm(){
			$('#win_company').window('close');
		}
		
	</script>
</html>