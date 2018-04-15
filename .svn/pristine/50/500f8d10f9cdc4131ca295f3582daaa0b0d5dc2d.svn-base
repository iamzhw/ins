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

.small-menu {
    position: absolute;
	width: 120px;
	z-index: 99999;
	border: solid 1px #CCC;
	background: #EEE;
	padding: 0px;
	margin: 0px;
	display: none;
}

.small-menu li {
   list-style: none;
	padding: 0px;
	margin: 0px;
}
.small-menu li A {
	color: #333;
	text-decoration: none;
	display: block;
	line-height: 20px;
	height: 20px;
	background-position: 6px center;
	background-repeat: no-repeat;
	outline: none;
	padding: 1px 5px;
	padding-left: 28px;
}

.small-menu li a:hover {
	color: #FFF;
	background-color: #3399FF;
}

.small-menu-separator {
    padding-bottom:0;
    border-bottom: 1px solid #DDD;
}

.small-menu LI.edit A { background-image: url(/ins/images/ztreeRightClickimages/page_white_edit.png); }
.small-menu LI.cut A { background-image: url(/ins/images/ztreeRightClickimages/cut.png); }
.small-menu LI.copy A { background-image: url(/ins/images/ztreeRightClickimages/page_white_copy.png); }
.small-menu LI.paste A { background-image: url(/ins/images/ztreeRightClickimages/page_white_paste.png); }
.small-menu LI.delete A { background-image: url(/ins/images/ztreeRightClickimages/page_white_delete.png); }
.small-menu LI.add A { background-image: url(/ins/images/ztreeRightClickimages/page_white_add.png); }

</style>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../../util/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/zTreeStyle3/zTreeStyle.css" />
		<script type="text/javascript" src="<%=path%>/js/ZTree/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=path%>/js/ZTree/ZTree.js"></script>
		<script type="text/javascript" src="<%=path%>/js/ZTree/jquery.popupSmallMenu.js"></script>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<title>代维公司</title>
	</head>
	<body style="padding: 3px; border: 0px">
		<div class="easyui-layout" style="height:100%;width:15%;float:left">
			<div data-options="region:'center' "
				style="width:50px;padding:3px 7px;">
				<div id="resourceTree" class="ztree"></div>
			</div>
		</div>
		<div style="width: 85%;height:100%;float:left">
			<div id="tb" style="padding: 5px; height: auto; width:100%">
				<div class="condition-container">
					<form id="form" action="" method="post">
						<input type="hidden" name="selected" value="" />
						<table class="condition">
							<tr>
								<td width="4%"  style="display:none">
									地市：
								</td>
								<td width="10%" style="display:none">
									<select name="AREA_ID" id="area" class="condition-select" style="width:94%" onchange="getSonAreaList()">
										<option value="">
											请选择
										</option>
									</select>
								</td>
								
								<td width="4%" style="display:none">区县：</td>
								<td width="10%" style="display:none">
									<select name="SON_AREA_ID" id="son_area" class="condition-select">
											<option value="">
												请选择
											</option>
									</select>
								</td>
							
								<td width="10%">代维公司名称：</td>
								<td width="10%">
									<div class="condition-text-container">
										<input name="company_name" type="text" class="condition-text" />
									</div>
								</td>
								
								<td width="8%">班组名称：</td>
								<td width="10%">
									<div class="condition-text-container">
										<input name="team_name" type="text" class="condition-text" />
									</div>
								</td>
								
								<td width="10%">审核人/接单人/兜底岗：</td>
								<td width="13%">
									<div class="condition-text-container">
										<input name="staff_name" type="text" class="condition-text" />
									</div>
								</td>
								
							</tr>
						</table>
					</form>
				</div>
				<div class="btn-operation-container">
					<div id="teamArea" class="btn-operation" onClick="relationTeam(1)">班组所属区域</div>
					<div id="teamCompany" class="btn-operation" onClick="relationTeam(2)">所属代维公司</div>
					<div id="teamGW" class="btn-operation" onClick="relationTeam(3)">岗位设置</div>
					<div id="teamGWReset" class="btn-operation" onClick="resetShy()">岗位删除</div>
					<div id="teamImport" class="btn-operation" onClick="importStaff()">导入</div>
					<div id="teamExport" class="btn-operation" onClick="exportExcel()">导出</div>
					<div class="btn-operation" onClick="searchTeamData()">查询</div>
					<!-- <div class="btn-operation" onClick="calProcedures()">批量执行操作</div> -->
				</div>
			</div>
			<table id="dg" title="代维公司" style="width: 100%; height: 480px">
			</table>
			<div id="win_company"></div>
			<div id="win_staff"></div>
			<div id="win_reset"></div>
			<div id="win_import_record"></div>
			<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
		</div>
	</body>
	<script type="text/javascript">
		
		var clickNode = '0000017';
		var downDeptNum = "100";
	
		var setting;
		//参数设置
		setting = {
			async : {
				enable : true,
				url : webPath + "/MainTainCompany/getDeptTree.do",
				autoParam : [ "id=id", "name","actionName" ]
			//otherParam: {},
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId",
					rootPId : 0
				}
			},
			callback: {
				onClick:onClick,
				onAsyncSuccess:openzTree
			}
		};
		
		var ztree ;
	
		$(document).ready(function() {
			getAreaList();
			searchTeamData();
			
			$.fn.zTree.init($("#resourceTree"), setting);
			ztree = $.fn.zTree.getZTreeObj("resourceTree");
			
		});
		
		
		
		function hideShowBtn(type){
			if(type == true){
				$('#teamArea').get(0).style.display = 'none';
				$('#teamCompany').get(0).style.display = 'none';
				$('#teamGW').get(0).style.display = 'none';
				$('#teamGWReset').get(0).style.display = 'none';
				$('#teamImport').get(0).style.display = 'none';
				$('#teamExport').get(0).style.display = 'none';
			}else{
				$('#teamArea').get(0).style.display = 'block';
				$('#teamCompany').get(0).style.display = 'block';
				$('#teamGW').get(0).style.display = 'block';
				$('#teamGWReset').get(0).style.display = 'block';
				$('#teamImport').get(0).style.display = 'block';
				$('#teamExport').get(0).style.display = 'block';
			}
		}
		
		
		function openzTree(){
			var ztree_exp = $.fn.zTree.getZTreeObj("resourceTree");
			var node = ztree_exp.getNodeByParam("parentId",'0');  
			ztree_exp.selectNode(node,true);//指定选中ID的节点  
			ztree_exp.expandNode(node, true, false);//指定选中ID节点展开
		}
		

		
		function onClick(event,treeId,treeNode,clickFlag){  
			downDeptNum = treeNode.actionName;
			clickNode = treeNode.id;
            searchTeamData();
            if(downDeptNum == '0'){
            	hideShowBtn(true);
            }else{
            	hideShowBtn(false);
            }
        }
		
		
		/*导入*/
		function importStaff() {
			$('#win_staff').window({
				title : "【导入班组审核员信息】",
				href : webPath + "teamManager/import.do",
				width : 400,
				height : 250,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		
		function resetShy(){
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
				$('#win_reset').window({
					title : "【删除审核人/接单人/兜底岗】",
					href : webPath + "teamManager/reset.do",
					width : 700,
					height : 550,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});	
			}
		}
		
		//关闭重置窗口
		function closeDeleteWin(){
			$('#win_reset').window('close');
		}
		
		
		
		/*导出样例*/
		function downloadExample(){
			$("#hiddenIframe").attr("src", "./downloadExample.do");
		}
		
		
		//重置审核员接单人和兜底岗的确定按钮
		function ensureDel(){
			var select_teamId = returnSelect();
			var select_staffNo = returnRoleDel();
			$.messager.confirm('系统提示', '您确定要更新审核人/接单人/兜底岗吗?', function(r) {
				if (r) {
					$.ajax({
						type : 'POST',
						url : webPath + "teamManager/updateTeamStaff.do",
						data : {
							team_ids : select_teamId,
							staff_no : select_staffNo
						},
						dataType : 'json',
						success : function(json) {
							$.messager.alert("提示",json.desc,"info");
							$('#win_reset').window('close');
							searchTeamData();
						}
					});
				}
			});
		}
		
		
		
		function ensure(){
			var select_teamId = returnSelect();
			var shy = '';
			var ddg = '';
			if($('#shy').attr('checked')){
				shy='shy';
			}
			if($('#ddg').attr('checked')){
				ddg = 'ddg';
			}
			if(shy==''&&ddg==''){
				$.messager.alert("提示","请至少选中一个选项","info");
				return;
			}
			$.messager.confirm('系统提示', '您确定要更新审核人/接单人/兜底岗吗?', function(r) {
				if (r) {
					$.ajax({
						type : 'POST',
						url : webPath + "teamManager/resetTeamStaff.do",
						data : {
							team_ids : select_teamId,
							shy:shy,
							ddg:ddg
						},
						dataType : 'json',
						success : function(json) {
							$.messager.alert("提示",json.desc,"info");
							$('#win_reset').window('close');
							searchTeamData();
						}
					});
				}
			});
		}
		
		function cancel(){
			$('#win_reset').window('close');
		}
		
		/*导入的确定*/
		function importUsers(){
			var file = $('#excel').val();
			if(file ==null || file =='' || !file){
				$.messager.show({
                    title: '提  示',
                    msg: '请先选择文件',
                    showType: 'show'
                });
				return;
			}
			$.messager.confirm('系统提示', '您确定要导入班组审核人吗?', function(r) {
				if (r) {
					 $('#sv').form('submit', {
		                 url: webPath + "teamManager/import_save.do",
		                 onSubmit: function () {
		                     $.messager.progress();
		                 },
		                 success: function (data) {
		                	 debugger;
		                     $.messager.progress('close'); // 如果提交成功则隐藏进度条
		                     var json = eval('(' + data + ')');
	                    	 $.messager.show({
		                         title: '提  示',
		                         msg: json.message,
		                         showType: 'show'
		                     });
	                    	 $('#win_staff').window('close');
	                    	 if(json.status == true){
	                    		 openImportRecode();	 
	                    	 }
		                 }
		             });
				}
			});
		}
		
		
		//打开导入记录的窗口
		function openImportRecode(){
			$('#win_import_record').window({
				title : "【导入记录】",
				href : webPath + "teamManager/openImportRecord.do",
				width : 1150,
				height : 600,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});	
		}
		
		
		/*导出*/
		function exportExcel() {
			/* var company_name = $("input[name='company_name']").val();
			var team_name = $("input[name='team_name']").val();
			var staff_name = $("input[name='staff_name']").val();
			var area = $("#area").val();
			var son_area = $("#son_area").val(); */
			
			var select_teamId = returnSelect();
			window.open(webPath
					+ "teamManager/exportExcel.do?randomPara=1 "
					+ "&clickNode="+clickNode+"&select_team_ids="+select_teamId	
					/* + "&company_name="+company_name+"&area="+area+"&son_area="+son_area+"&team_name="+team_name
					+ "&staff_name="+staff_name */
					+ "&page=1&rows=9999999");
		}
		
		
		function searchTeamData(){
			var company_name = $("input[name='company_name']").val();
			var team_name = $("input[name='team_name']").val();
			var staff_name = $("input[name='staff_name']").val();
			var area = $("#area").val();
			var son_area = $("#son_area").val();
			var option = $("#area").val();
			/* var option_son = $("#son_area").val(); */
			if(area==null || ""==area||option=="0"){
				var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
				var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
				var isAdmin1 ='${CABLE_ADMIN}';
				if(true ==isAdmin1 || isAdmin1 =='true'){
					
				}else if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
					area=${areaId};
				}else if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
					area=${areaId};
					son_area =${sonAreaId};
			    }else{
			    	
			    }
			}
			var obj = {
				clickNode:clickNode,
				company_name : company_name,
				area : area,
				son_area : son_area,
				team_name : team_name,
				staff_name : staff_name,
				option:option
				/* option_son:option_son */
			};
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "teamManager/listAll.do?",
				method : 'post',
				queryParams : obj,
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [20,50,100],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				fit : true,
				singleSelect : false,
				remoteSort: false,
				columns : [[
			            {
			            	field : 'COMPANY_ID',
							title : '代维公司ID',
							rowspan : '2', 
							checkbox : true	
			            },
			            {
			            	field : 'TEAM_ID',
							title : '班组ID',
							hidden:true
			            },
			            {
							field : 'AREA_NAME',
							title : '地市名称',
							width : "10%",
							rowspan : '1',
							sortable : true,
							align : 'center'
						},
						{
							field : 'SON_AREA_NAME',
							title : '区县名称',
							width : "10%",
							rowspan : '1',
							sortable : true,
							align : 'center'
						},{
							field : 'COMPANY_NAME',
							title : '代维公司名称',
							width : "10%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						},{
							field : 'TEAM_NAME',
							title : '班组名称',
							width : "20%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						},{
							field : 'SHENHE_NAME',
							title : '审核人/接单人',
							width : "15%",
							rowspan : '2',
							sortable : true,
							align : 'center',
							hidden:downDeptNum=='0'?true:false
						},{
							field : 'DOUDI_NAME',
							title : '兜底岗',
							width : "15%",
							rowspan : '2',
							sortable : true,
							align : 'center',
							hidden:downDeptNum=='0'?true:false
						},
						/* {
							field : 'JIEDAN_NAME',
							title : '接单人',
							width : "15%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						}, */
						{
							field : 'STAFF_NO',
							title : '人员账户',
							width : "15%",
							rowspan : '2',
							sortable : true,
							align : 'center',
							hidden:downDeptNum!='0'?true:false
						},
						{
							field : 'STAFF_NAME',
							title : '人员名称',
							width : "15%",
							rowspan : '2',
							sortable : true,
							align : 'center',
							hidden:downDeptNum!='0'?true:false
						},
						{
							field : 'ROLE_NAME',
							title : '人员角色',
							width : "15%",
							rowspan : '2',
							sortable : true,
							align : 'center',
							hidden:downDeptNum!='0'?true:false
						},
						{
							field : 'STAFF_NUM',
							title : '综调人数',
							width : "10%",
							rowspan : '2',
							sortable : true,
							align : 'center',
							hidden:downDeptNum=='0'?true:false,
							formatter:function(value,row,Index){
								return "<a href=\"javascript:show('"+ row.TEAM_ID + "');\">"+value+"</a>";  
		                	}
						}]],
						nowrap : false,
						striped : true,
						onLoadSuccess : function(data) {
							$("body").resize();
						}
			});	
		}
		
		function myCloseTab() {  
			if(parent.$('#tabs').tabs('exists',"班组人员管理")){
				parent.$('#tabs').tabs('close',"班组人员管理");
			}
        }
		
		function show(TEAM_ID){
		 	myCloseTab();
			addTab("班组人员管理", "<%=path%>/teamUser/list.do?TEAM_ID='"+TEAM_ID+"'");
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
								searchTeamData();
								$.messager.show({
									title : '提  示',
									msg : '成功删除该代维公司!',
									showType : 'show'
								});
							}
						});
						searchTeamData();
					}
				});
			}
		}
		
		function relationTeam(type){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show'
				});
				return;
			/* } else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show'
				});
				return; */
			} else {
				if(type==1){
					openWin(type);
					setTimeout(getAreaList_edit(),1000);
				}else if (type==2){
					openWin(type);
					setTimeout(getMainTainCompany(),1000);
				}else if (type==3){
					setRole();
					setTimeout(getAreaList_select_staff(),1000);
				}
			}
		}
		
		
		function openWin(type){
			$('#win_company').window({
				title : "班组代维",
				href : webPath + "teamManager/relationWin.do?type="+type,
				width : 400,
				height : 400,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});	
		}
		
		//保存代维公司和班组之间的关系
		function saveRelation(){
			var pass = $("#relation").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要编辑代维公司吗?', function(r) {
					if (r) {
						var company_id = $("#show_company").val();
						var select_teamId = '';
						select_teamId = returnSelect();
						$.ajax({
							type : 'POST',
							url : webPath + "teamManager/updateTeamCompany.do",
							data : {
								team_id : select_teamId,
								company_id : company_id
							},
							dataType : 'json',
							success : function(json) {
								$.messager.alert("提示",json.desc,"info");
								closeForm();
								searchTeamData();
							}
						});
					}
				});
			}
		}
		
		
		function setRole(){
			$('#win_staff').window({
				title : "【设置审核人和接单人】",
				href : webPath + "teamManager/check_staff.do",
				width : 600,
				height : 600,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		
		function returnSelect(){
			var selected = $('#dg').datagrid('getChecked');
			var arr = new Array();
			for ( var i = 0; i < selected.length; i++) {
				var value = selected[i].TEAM_ID;
				arr[arr.length] = value;
			}
			return arr.join(",");
		}
		
		
		function returnRoleDel(){
			var selected = $('#dg_role_del').datagrid('getChecked');
			var arr_p = new Array();
			for ( var i = 0; i < selected.length; i++) {
				var value = selected[i].UNIQUEID;
				arr_p[arr_p.length] = value;
			}
			return arr_p.join(",");
		}
		
		
		//新增和修改页面的保存按钮
		function saveForm() {
			var pass = $("#relation").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要编辑改班组吗?', function(r) {
					if (r) {
						var edit_son_area = $("#edit_son_area").val();
						var edit_area = $("#edit_area").val();
						
						var select_teamId = '';
						select_teamId = returnSelect();
						$.ajax({
							type : 'POST',
							url : webPath + "teamManager/updateTeamArea.do",
							data : {
								area : edit_area,
								son_area : edit_son_area,
								select_teamId : select_teamId
							},
							dataType : 'json',
							success : function(json) {
								$.messager.alert("提示",json.desc,"info");
								closeForm();
								searchTeamData();
							}
						});
					}
				});
			}
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
					$("select[name='AREA_ID']").append("<option value='0'>请选择</option>");
					
					$("select[name='AREA_ID']").append("<option value='-1'>空 </option>");
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
		
		
		//获取区域
		function getSonAreaList() {
			var areaId = $("#area").val(); 	
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
					$("select[name='SON_AREA_ID'] option").remove();
					$("select[name='SON_AREA_ID']").append("<option value=''>请选择</option>");
					$("select[name='SON_AREA_ID']").append("<option value='-1'>空 </option>");
					
					if(true ==isAdmin1 || isAdmin1 =='true'){	
						for ( var i = 0; i < result.length; i++) {					
							$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");												
						}
					}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true'){
						for ( var i = 0; i < result.length; i++) {
							$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");					
						}
					}else{
						for ( var i = 0; i < result.length; i++) {
							if(result[i].AREA_ID==${sonAreaId}){
								/* $("select[name='SON_AREA_ID'] option").remove(); */
								$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}						
						}
					}
				}
			});
		}
		
		
		/////////////////////////////////////区域设置/////////////////////////////////////
		//获取地市
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
					$("select[name='edit_AREA_ID'] option").remove();
					$("select[name='edit_AREA_ID']").append("<option value=''>请选择</option>");
					if( true ==isAdmin1 || isAdmin1 =='true'){
						for ( var i = 0; i < result.length; i++) {
								$("select[name='edit_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}					
					}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true') {
						for ( var i = 0; i < result.length; i++) {
							if(result[i].AREA_ID==${areaId}){
								$("select[name='edit_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}					
						}
					}else{
						for ( var i = 0; i < result.length; i++) {
							if(result[i].AREA_ID==${areaId}){
								$("select[name='edit_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}						
						}
					}	
				}
			});
		}
		
		
		//获取区域
		function getSonAreaList_edit() {
			var areaId = $("#edit_area").val(); 	
			$.ajax({
				type : 'POST',
				url : webPath + "General/getSonAreaList.do",
				data : {
					areaId : areaId
				},
				dataType : 'json',
				success : function(json) 
				{		
					var result = json.sonAreaList;
					$("select[name='edit_SON_AREA_ID'] option").remove();
					$("select[name='edit_SON_AREA_ID']").append("<option value=''>请选择</option>");
					$("select[name='WHWG'] option").remove();
					$("select[name='WHWG']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
						$("select[name='edit_SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");					
					}
				}
			});
		}
		/////////////////////////////////////区域设置/////////////////////////////////////
		
		
		
		///////////////////////////////给”岗位设置“中，选择地市和区县所使用/////////////////////////////////////////////
		//获取地市
		function getAreaList_select_staff() {
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
					$("select[name='staff_AREA_ID'] option").remove();
					$("select[name='staff_AREA_ID']").append("<option value=''>请选择</option>");
					if( true ==isAdmin1 || isAdmin1 =='true'){
						for ( var i = 0; i < result.length; i++) {
								$("select[name='staff_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}					
					}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true') {
						for ( var i = 0; i < result.length; i++) {
							if(result[i].AREA_ID==${areaId}){
								$("select[name='staff_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}					
						}
					}else{
						for ( var i = 0; i < result.length; i++) {
							if(result[i].AREA_ID==${areaId}){
								$("select[name='staff_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}						
						}
					}	
				}
			});
		}
		
		
		//获取区域
		function getSonAreaList_select_staff() {
			var areaId = $("#staff_AREA_ID").val(); 	
			$.ajax({
				type : 'POST',
				url : webPath + "General/getSonAreaList.do",
				data : {
					areaId : areaId
				},
				dataType : 'json',
				success : function(json) 
				{		
					var result = json.sonAreaList;
					$("select[name='staff_SON_AREA_ID'] option").remove();
					$("select[name='staff_SON_AREA_ID']").append("<option value=''>请选择</option>");
					$("select[name='WHWG'] option").remove();
					$("select[name='WHWG']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
						$("select[name='staff_SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");					
					}
				}
			});
		}
		////////////////////////////////////////////////////////////////////////////
		
		function getMainTainCompany(){
			$.ajax({
				type : 'POST',
				url : webPath + "General/getMainTainCompany.do",
				/* data : {}, */
				dataType : 'json',
				success : function(json) 
				{		
					var result = json.mainTainCompany;
					$("select[name='show_company'] option").remove();
					$("select[name='show_company']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
						$("select[name='show_company']").append("<option value=" + result[i].COMPANY_ID + ">"+ result[i].COMPANY_NAME + "</option>");					
					}
				}
			});
		}
		
		
		
		//批量执行操作
		function calProcedures(){
			
			//var select_teamId = returnSelect();
			
			$.ajax({
				type : 'POST',
				url : webPath + "teamManager/calProcedures.do",
				data : {
					team_id_in : clickNode
				},
				dataType : 'json',
				success : function(json) {
				}
			});
		}
		
	</script>
</html>