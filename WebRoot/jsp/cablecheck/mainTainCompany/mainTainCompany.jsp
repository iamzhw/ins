<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<style type="text/css">
a{text-decoration:none;}
</style>
<style type="text/css">
body, html, #allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

.condition {
	width: 100%;
	border: 0px solid;
}

* {
	margin: 0;
	padding: 0;
	list-style-type: none;
}

a, img {
	border: 0;
}

body {
	font: 12px/180% Arial, Helvetica, sans-serif, "新宋体";
}

.selectbox .select-bar {
	padding: 0 20px;
}

.selectbox .select-bar select {
	width: 150px;
	height: 200px;
	border: 4px #A0A0A4 outset;
	padding: 4px;
}

.selectbox .btn {
	width: 50px;
	height: 30px;
	margin-top: 10px;
	cursor: pointer;
}

.chartOptionsFlowTrend{
    z-index:300;
    background-color:white;
	border:1px solid gray;
	display:none;
	position: absolute;
	left:0px;
	top:23px;
	width:150px;
}
.chartOptionsFlowTrend ul{
	float:left;
	padding: 0px;
	margin: 5px;
}
.chartOptionsFlowTrend li{
	display:block;
	position: relative;
	left:0px;
	margin: 0px;
	clear:both;
}
.chartOptionsFlowTrend li *{
	float:left;
}
.chartOptionsFlowTrend p {
	height: 23px;
	line-height: 23px;
	overflow: hidden;
	position: relative;
	z-index: 2;
	background: #fefbf7;
	padding-top: 0px;
	display: inline-block;
}
.chartOptionsFlowTrend p a {
	border: 1px solid #fff;
	margin-left: 15px;
	color: #2e91da;
}
.select_checkBox{
	/* border:0px solid red; */
	position: relative;
	display:inline-block;
}	
</style>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<title>代维公司</title>
	</head>
	<body style="padding: 3px; border: 0px">
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<table class="condition">
						<tr>
							<td width="4%">地市：</td>
							<td width="10%">
								<select name="AREA_ID" id="area" class="condition-select">
									<option value="">
										请选择
									</option>
								</select>
							</td>
							<td width="10">代维公司名称：</td>
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
				<div class="btn-operation" onClick="addCom()">增加</div>
				<div class="btn-operation" onClick="editCom()">编辑</div>
				<div class="btn-operation" onClick="delCom()">删除</div>
				<div class="btn-operation" onClick="relationTeam()">关联班组</div>
				<div class="btn-operation" onClick="exportExcel()">导出</div>
				<div class="btn-operation" onClick="searchData()">查询</div>
			</div>
		</div>
		<table id="dg" title="代维公司" style="width: 100%; height: 480px">
		</table>
		<div id="win_company"></div>
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
			searchData();
			getAreaList();
		});
		
		/*导出*/
		function exportExcel() {
			var company_name = $("input[name='company_name']").val();
			var area_id = $("select[name='AREA_ID']").val();
			window.open(webPath
					+ "MainTainCompany/exportExcel.do?randomPara=1 "
					+ "&company_name="+company_name+"&area_id="+area_id
					+ "&page=1&rows=9999999");
		}
		
		
		function returnParam(){
			var company_name = $("input[name='company_name']").val();
			var area_id = $("select[name='AREA_ID']").val();
			var obj = {
					company_name : company_name,
					area_id : area_id,
				};
			return obj;
		}
		
		function searchData(){
			var obj = returnParam();
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "MainTainCompany/listAll.do",
				method : 'post',
				queryParams : obj,
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
			            },
			            {
							field : 'AREA_NAME',
							title : '地市',
							width : "10%",
							rowspan : '2',
							sortable : true,
							align : 'center'
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
							align : 'center',
							hidden:true
						}]],
						nowrap : false,
						striped : true,
						onLoadSuccess : function(data) {
							$("body").resize();
						}
			});	
		}
		
		function addCom() {
			$('#win_company').window({
				title : "【新增代维公司】",
				href : webPath + "MainTainCompany/editView.do",
				width : 400,
				height : 300,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
			setTimeout(getAreaList_edit(),1000);
		}
		function editCom() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show'
				});
				return;
			} else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show'
				});
				return;
			} else {
				var COMPANY_ID = selected[0].COMPANY_ID;
				var COMPANY_NAME = selected[0].COMPANY_NAME;
				$('#win_company').window({
					title : "【编辑代维公司】",
					href : webPath + "MainTainCompany/editView.do?COMPANY_ID="
							+COMPANY_ID+"&COMPANY_NAME="+COMPANY_NAME,
					width : 400,
					height : 300,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
				setTimeout(getAreaList_edit(),1000);
			}

		}
		
		function delCom() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取要删除的数据!',
					showType : 'show'
				});
				return;
			} else {
				$.messager.confirm('系统提示', '您确定要删除该代维公司吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].COMPANY_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$('#form').form('submit', {
							url : webPath + "MainTainCompany/deleteCompany.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '成功删除该代维公司!',
									showType : 'show'
								});
							}
						});
						searchData();
					}
				});
			}
		}
		
		function relationTeam(){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show'
				});
				return;
			} else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show'
				});
				return;
			} else {
				var COMPANY_ID = selected[0].COMPANY_ID;
				var COMPANY_NAME = selected[0].COMPANY_NAME;
				$('#win_company').window({
					title : "关联班组",
					href : webPath + "MainTainCompany/relationCompany.do?COMPANY_ID="
							+COMPANY_ID+"&COMPANY_NAME="+COMPANY_NAME,
					width : 400,
					height : 400,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			}
		}
		
		//保存代维公司和班组之间的关系
		function saveRelation(){
			var pass = $("#relation").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要编辑代维公司吗?', function(r) {
					if (r) {
						var company_id = $("input[name='hide_com_id']").val();
						var team_id = $("input[name='team_id_value']").val();
						$.ajax({
							type : 'POST',
							url : webPath + "MainTainCompany/saveRelation.do",
							data : {
								team_id : team_id,
								company_id : company_id
							},
							dataType : 'json',
							success : function(json) {
								$.messager.alert("提示",json.desc,"info");
								closeForm();
								searchData();
							}
						});
					}
				});
			}
		}
		
		//新增和修改页面的保存按钮
		function saveForm() {
			var pass = $("#relation").form('validate');
			var area_id = $("select[name='AREA_ID_EDIT']").val();
			if(area_id==null||area_id==''||!area_id){
				$.messager.alert("提示","地市区域不可为空","info");
				return;
			}
			var company_name = $("input[name='edit_company_name']").val();
			if(company_name==null||company_name==''||!company_name){
				$.messager.alert("提示","公司名称不可为空","info");
				return;
			}
			if (pass) {
				$.messager.confirm('系统提示', '您确定要编辑代维公司吗?', function(r) {
					if (r) {
						var company_name = $("input[name='edit_company_name']").val();
						var company_id = $("input[name='com_id']").val();
						var area_id = $("select[name='AREA_ID_EDIT']").val();
						var area_name = $("#AREA_ID_EDIT option:selected").text();
						$.ajax({
							type : 'POST',
							url : webPath + "MainTainCompany/editCompany.do",
							data : {
								COMPANY_NAME : company_name,
								COMPANY_ID : company_id,
								area_id:area_id,
								area_name:area_name
							},
							dataType : 'json',
							success : function(json) {
								$.messager.alert("提示",json.desc,"info");
								closeForm();
								searchData();
							}
						});
					}
				});
			}
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
		
		//获取光路性质的下拉框
		function makeSure(){
			$('#toWarning').css('display','none');
			$("#content").prop('type','text'); 
			var arr=document.getElementsByName("checkboxxz");
			var values="";
			var contents="";
			for(var i=0;i<arr.length;i++){
				if(arr[i].checked){
					var value = arr[i].value;			
					var content= arr[i].nextSibling.innerText; 
					values=values+value+",";
					contents=contents+content+",";			
				}
			}
			values=values.substring(0,values.length-1);
			contents=contents.substring(0,contents.length-1);
			$('#value').val(values);
			$('#content').val(contents);
			
			//点击确定以后，将选择框隐藏
			$(".chartOptionsFlowTrend").css("display","none");
			
		}

		function toReset(){
			$("[name='checkboxxz']").removeAttr('checked');
			$('#value').val('');
			$('#content').val('');
			
			//点击重置以后，将选择框隐藏
			$(".chartOptionsFlowTrend").css("display","none");
			
		}
		
		function closeForm(){
			$('#win_company').window('close');
		}
		
		
		
		//获取地市
		function getAreaList() {
			 var areaId=2; 
			$.ajax({
				type : 'POST',
				url : webPath + "General/getSonAreaList.do",
				data : {
					areaId : areaId
				},
				dataType : 'json',
				success : function(json) 
				{
					var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
					var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
					var isAdmin1 ='${CABLE_ADMIN}';
				
					var result = json.sonAreaList;
					$("select[name='AREA_ID'] option").remove();
					$("select[name='AREA_ID']").append("<option value=''>请选择</option>");
					
					if( true ==isAdmin1 || isAdmin1 =='true'){
						for ( var i = 0; i < result.length; i++) {
								$("select[name='AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}					
					}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true') {
						for ( var i = 0; i < result.length; i++) {
							if(result[i].AREA_ID==${areaId}){
								$("select[name='AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}					
						}
					}else{
						for ( var i = 0; i < result.length; i++) {
							if(result[i].AREA_ID==${areaId}){
								$("select[name='AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}						
						}
					}
				}
			});
		}
		
		
		
		/////////////////////////////
		
		//获取地市，新增和编辑的时候
		function getAreaList_edit() {
			 var areaId=2; 
			$.ajax({
				type : 'POST',
				url : webPath + "General/getSonAreaList.do",
				data : {
					areaId : areaId
				},
				dataType : 'json',
				success : function(json) 
				{
					var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
					var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
					var isAdmin1 ='${CABLE_ADMIN}';
				
					var result = json.sonAreaList;
					$("select[name='AREA_ID_EDIT'] option").remove();
					$("select[name='AREA_ID_EDIT']").append("<option value=''>请选择</option>");
					
					if( true ==isAdmin1 || isAdmin1 =='true'){
						for ( var i = 0; i < result.length; i++) {
								$("select[name='AREA_ID_EDIT']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}					
					}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true') {
						for ( var i = 0; i < result.length; i++) {
							if(result[i].AREA_ID==${areaId}){
								$("select[name='AREA_ID_EDIT']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}					
						}
					}else{
						for ( var i = 0; i < result.length; i++) {
							if(result[i].AREA_ID==${areaId}){
								$("select[name='AREA_ID_EDIT']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}						
						}
					}
				}
			});
		}
		
	</script>
</html>