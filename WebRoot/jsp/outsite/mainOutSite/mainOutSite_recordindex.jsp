
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
									<c:forEach items="${orgList}" var="org">
										<option value='${org.ORG_ID}'>${org.ORG_NAME}</option>
									</c:forEach>
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
						<td width="10%">检查人：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_check_name" id="p_check_name" type="text"
									class="condition-text" />
							</div>
						</td>
						<td width="10%">外力点名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_site_name" id="p_site_name" type="text"
									class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="mainOutSiteByDown()">导出</div>

<%--			<div class="btn-operation" onClick="mainOutSiteEditUI()">编辑</div>--%>
<%--			<div class="btn-operation" onClick="mainOutSiteDeleteUI()">删除</div>--%>
<%--			<div class="btn-operation" onClick="showConfirmTbl()">方案确认表格</div>--%>

			<div style="float:right;" class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<div id="win"></div>
	<div id="win_checkRecord"></div>
	
	

	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {

<%--			if("${localId}"){--%>
<%--				getAllGroup("${localId}");	--%>
<%--			}--%>
<%--			else{--%>
<%--				$("#tr_cityName td").css("display",'');--%>
<%--			}--%>
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
				url : webPath + "mainOutSiteController/query_recordindex.do",
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
					field : 'OUT_RECORD_ID',
					title : '外力点ID',
					checkbox : true
				}, {
					field : 'OUT_SITE_NAME',
					title : '外力点名称',
					width : "10%",
					align : 'center'
				},{
					field : 'STAFF_NAME',
					title : '检查人',
					width : "10%",
					align : 'center'
				}, {
					field : 'ORG_NAME',
					title : '组织名称',
					width : "10%",
					align : 'center'
				}, {
					field : 'CHECK_TIME',
					title : '检查时间',
					width : "8%",
					align : 'center'
				},{
					field : 'PROBLEM_MES',
					title : '发现问题',
					width : "7%",
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
					+ "mainOutSiteController/record_indexDown.do"
					+ getParamsForDownloadLocal('form'));
		}
		
	</script>

</body>
</html>
