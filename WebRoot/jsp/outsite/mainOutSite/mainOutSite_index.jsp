
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>外力点管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<table id="dg" title="【外力点管理】" style="width: 100%;">
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
				<tr>
					<td width="10%">组织名称：</td>
						<td width="20%">
							<div class="">
								<select name="org_id" id="org_id"
									class="condition-select">
									<option value=''>--请选择--</option>
								</select>
							</div>
						</td>
						<td width="10%">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name="p_start_time" id="p_start_time" 
								onClick="WdatePicker();" />
							</div>
						</td>
						<td width="10%">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name="p_end_time" id="p_end_time"
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'p_start_time\')}'});" />
							</div>
						</td>
					</tr>
					<tr id="tr_cityName">
						<td width="10%">外力点名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_site_name" id="p_site_name" type="text"
									class="condition-text" />
							</div>
						</td>
						<td width="10%">施工状态：</td>
						<td width="20%">
							<div class="">
								<select name="p_con_status" id="p_con_status"
									class="condition-select">
									<option value=''>--请选择--</option>
									
									<option value='1'>外力施工中</option>
									<option value='2'>外力施工结束</option>

								</select>
							</div>
						</td>
						<td width="10%" style="display:none;">地市名称：</td>
						<td width="20%" style="display:none;">
							<div class="">
								<select name="area_id" id="area_id" onclick="getAllGroup(this.value)"
									class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach var="city" items="${cityModel }">
										<option value="${city.AREA_ID }" <c:if test="${city.AREA_ID eq localId }">selected</c:if>>${city.NAME }</option>
									</c:forEach>
								</select>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">

			<div class="btn-operation" onClick="mainOutSiteEditUI()">编辑</div>
			<div class="btn-operation" onClick="mainOutSiteDeleteUI()">删除</div>
			<div class="btn-operation" onClick="showConfirmTbl()">方案确认表格</div>
			<div class="btn-operation" onClick="mainOutSiteByDown()">导出</div>

			<div style="float:right;" class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<div id="win"></div>
	<div id="win_checkRecord"></div>
	
	

	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {

			if("${localId}"){
				getAllGroup("${localId}");	
			}
			else{
				$("#tr_cityName td").css("display",'');
			}
		});

		function getAllGroup(areaId){
			$("select[name='org_id']").find("option:not(:first)").remove();
			if(!areaId) return;
			$.ajax({
				url : webPath + "/mainOutSiteController/getAllGroup.do",  
				data : {
					areaId : areaId
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(data) {
					$(data).each(function(){
						$("select[name='org_id']").append("<option value="+this.ORG_ID+">"+this.ORG_NAME+"</option>");
					});
				}
			});
			
		}
		
		function searchData() {

			var obj = makeParamJson('form');

			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				fitColumns:true,
				toolbar : '#tb',
				url : webPath + "mainOutSiteController/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,

				singleSelect : false,
				columns : [ [ {
					field : 'OUT_SITE_ID',
					title : '外力点ID',
					checkbox : true
				}, {
					field : 'SITE_NAME',
					title : '外力点名称',
					width : "10%",
					align : 'center'
				},{
					field : 'CON_STATUS',
					title : '外力点状态',
					width : "10%",
					align : 'center',
					formatter : function(value, rec,
							index) {
						if(value==1){
							return "外力施工中";
						}else{
							return "外力施工结束";
						}
						
					}
				}, {
					field : 'IS_ADD_ELEBAR',
					title : '是否添加电子围栏',
					width : "10%",
					align : 'center',
					formatter : function(value, rec,
							index) {
						if(value==1){
							return "是";
						}else{
							return "否";
						}
					}	
				},{
					field : 'AFFECTED_FIBER',
					title : '受影响光缆',
					width : "10%",
					align : 'center'
				}, {
					field : 'SITE_DANGER_LEVEL',
					title : '外力影响等级',
					width : "8%",
					align : 'center',
					formatter : function(value, rec,
							index) {
						if(value==1){
							return "Ⅰ";
						}else if(value==2){
							return "Ⅱ";
						}else if(value==3){
							return "Ⅲ";
						}else{
							return "隐患";
						}
						
					}
				},{
					field : 'FIBER_LEVEL',
					title : '干线等级',
					width : "7%",
					align : 'center',
					formatter : function(value, rec,
							index) {
						if(value==1){
							return "一级";
						}else{
							return "二级";
						}
						
					}
				},{
					field : 'CREATION_TIME',
					title : '创建时间',
					width : "10%",
					align : 'center'
				},{
					field : 'CON_STARTDATE',
					title : '开始时间',
					width : "10%",
					align : 'center'
				}, {
					field : 'PRE_ENDDATE',
					title : '预计结束时间',
					width : "10%",
					align : 'center'
				}, {
					field : 'USER_NAME',
					title : '上报人员',
					width : "8%",
					align : 'center'
				}, {
					field : 'FLOW_STATUS',
					title : '流程状态',
					width : "10%",
					align : 'center',
					formatter : function(value, rec,index) {
						if(value==1){
							return "待班组长确认";
						}else if(value==2){
							return "待县分管领导确认";
						}else if(value==3){
							return "待市分管领导确认";
						}else if(value==4){
							return "待县主要管理和技术人员确认";
						}else if(value==5){
							return "待市主要管理和技术人员确认";
						}else{
							return "确认完成";
						}
						
					}
				},{
					field : 'opr',
					title : '操作',
					width : "20%",
					align : 'center',
					formatter : function(value, rec,
							index) {
						return "&nbsp;&nbsp;<a  style='color:blue' onclick='getOuteSiteLocation (\""
						+ rec.OUT_SITE_ID
						+ "\")'>地图位置</a>&nbsp;&nbsp;<a  style='color:blue' onclick='getOuteSiteDetailUI(\""
						+ rec.OUT_SITE_ID
						+ "\")'>详情</a>&nbsp;&nbsp;<a  style='color:blue' onclick='checkDetailUI(\""
						+ rec.OUT_SITE_ID
						+ "\")'>检查详情</a>&nbsp;&nbsp;<a  style='color:blue' onclick='flowDetailUI(\""
						+ rec.OUT_SITE_ID
						+ "\")'>流程详情</a>";
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

			$("#" + form_id + "").form('reset', 'none');

		}

		function mainOutSiteEditUI() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show',
					timeout : '1000'//ms
				});
				return;
			} else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show',
					timeout : '1000'//ms
				});
				return;
			} else {
				var out_site_id = selected[0].OUT_SITE_ID;

				addTab("外力点编辑",webPath+ "mainOutSiteController/mainOutSiteEditUI.do?id="+ out_site_id);
			
			}

		}

		function mainOutSiteDeleteUI() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取要删除的数据!',
					showType : 'show',
					timeout : '1000'//ms
				});
				return;
			} else {
				$.messager
						.confirm(
								'系统提示',
								'您确定要删除外力点吗?',
								function(r) {
									if (r) {
										var arr = new Array();
										for ( var i = 0; i < selected.length; i++) {
											var value = selected[i].OUT_SITE_ID;
											arr[arr.length] = value;
										}
										$("input[name='selected']").val(arr);
										$.messager.progress();
										$
												.ajax({
													async : false,
													type : "post",
													url : webPath
															+ "mainOutSiteController/mainOutSiteDelete.do",
													data : {
														ids : $(
																"input[name='selected']")
																.val()
													},
													dataType : "json",
													success : function(data) {
														$.messager
																.progress('close');
														if (data.status) {

															searchData();
															$.messager
																	.show({
																		title : '提  示',
																		msg : '成功删除外力点!',
																		showType : 'show',
																		timeout : '1000'//ms

																	});
														} else {
															alert("删除失败");
														}
													}
												});

									}
								});
			}
		}

		
		
		function getOuteSiteDetailUI(id){
			
			addTab("外力点详情", webPath + "mainOutSiteController/getOuteSiteDetailUI.do?out_site_id="+id);
			
		}
		
		function checkDetailUI(id){
			$('#win_checkRecord').window({
				title : "【检查详情】",
				href : webPath + "mainOutSiteController/checkDetailUI.do?out_site_id=" + id,
				width : 1060,
				height : 400,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		
		function flowDetailUI(id){
			$('#win_checkRecord').window({
				title : "【流程详情】",
				href : webPath + "mainOutSiteController/flowDetailUI.do?out_site_id=" + id,
				width : 900,
				height : 400,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		function getOuteSiteLocation(id){
		
			addTab("地图位置" ,webPath + "mainOutSiteController/getOuteSiteLocation.do?out_site_id="+id);
		
		}
		
		
		
		
		function showConfirmTbl() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show',
					timeout : '1000'//ms
				});
				return;
			} else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show',
					timeout : '1000'//ms
				});
				return;
			} else {
				var out_site_id = selected[0].OUT_SITE_ID;
				addTab("外力点方案确认表格",webPath+ "mainOutSiteController/showConfirmTbl.do?id="+ out_site_id);
			}

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
			o
					.find("input,select")
					.each(
							function() {
								var o = $(this);
								var tag = this.tagName.toLowerCase();
								var name = o.attr("name");
								if (name) {
									if (tag == "select") {
										if (o.find("option:selected").val() == 'all'
												|| o.find("option:selected")
														.val() == '') {
											res = res + "&" + name + "=";
										} else {
											res = res
													+ "&"
													+ name
													+ "="
													+ o.find("option:selected")
															.val();
										}

									} else if (tag == "input") {
										res = res + "&" + name + "=" + o.val();
									}
								}
							});
			return res;
		}

		function mainOutSiteByDown() {
			window.open(webPath
					+ "mainOutSiteController/queryByDown.do"
					+ getParamsForDownloadLocal('form'));
		}
	</script>

</body>
</html>
