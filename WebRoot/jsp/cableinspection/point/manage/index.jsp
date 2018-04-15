<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>关键点管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">关键点编码：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="point_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">关键点名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="point_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">点地址：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition" type="text"
									name="address" />
							</div>
						</td>
						<td width="10%">点类型：</td>
						<td width="20%"><select name="point_type"
							class="condition-select">
								<option value="">请选择</option>
								<c:forEach items="${pointTypeList }" var="type">
									<option value="${type.POINT_TYPE_ID }">${type.POINT_TYPE_NAME
										}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td width="10%">区域：</td>
						<td width="20%">
							<select name="son_area" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td width="10%">维护等级：</td>
						<td width="20%">
							<select name="point_level" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td width="10%">维护网格：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="easyui-combobox" type="text" id="dept_name"
									name="dept_name"/>
							</div>
						</td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="addPoint()">新增</div>
			<c:if test="${!isSonAreaAdmin }">
				<div class="btn-operation" onClick="updateCoordinate()">修改</div>
			</c:if>
			<c:if test="${!isSonAreaAdmin }">
				<div class="btn-operation" onClick="delPoint()">删除</div>
			</c:if>
			<div class="btn-operation" onClick="exportPoint()">导出</div>
			<div class="btn-operation" onClick="importPoint()">导入</div>
			<div style="float: right;" class="btn-operation"
				onClick="clearCondition()">重置</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【关键点管理】" style="width: 100%; height: 480px">
	</table>
	<div id="win_staff"></div>

	<script type="text/javascript">
		$(document).ready(function() {
			//searchData();
			getSonAreaList();
			getMntList();
			deptTips();
		});
		function getSonAreaList() {
		var areaId='${areaId}';
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
				$("select[name='son_area'] option").remove();
				var GLY ='${GLY}';
				var LXXJ_ADMIN ='${LXXJ_ADMIN}';
				var LXXJ_ADMIN_AREA ='${LXXJ_ADMIN_AREA}';
				if (GLY == 'true' || LXXJ_ADMIN =='true' || LXXJ_ADMIN_AREA=='true')
				{
					$("select[name='son_area']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
					
						$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
					}
					
				}
				else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${sonAreaId}){
							$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
						}
					}
				}
			}
		});
	}
	//获得维护等级
	function getMntList() {
		$.ajax({
			type : 'POST',
			url : webPath + "Lxxj/point/manage/getMntList.do",
			data : {
			},
			dataType : 'json',
			success : function(json) 
			{
				var result = json.mntList;
				$("select[name='point_level'] option").remove();
				$("select[name='point_level']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
				
					$("select[name='point_level']").append("<option value=" + result[i].LEVEL_ID + ">"
									+ result[i].LEVEL_NAME + "</option>");
				}
			}
		});
	}
		var lastIndex;
		function searchData() {
			lastIndex=0;
			var pressCommandFlag = 0;//1表示按了回车键
			var point_no = $("input[name='point_no']").val();
			var point_name = $("input[name='point_name']").val();
			var address = $("input[name='address']").val();
			var point_type = $("select[name='point_type']").val();
			var son_area_id = $("select[name='son_area']").val();
			var point_level = $("select[name='point_level']").val();
			var dept_name = $("#dept_name").combobox("getValue");
			var obj = {
				point_no : point_no,
				point_name : point_name,
				address : address,
				point_type : point_type,
				son_area_id:son_area_id,
				point_level:point_level,
				dept_name:dept_name
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Lxxj/point/manage/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				onDblClickRow:function(rowIndex,rowData){
				$("#dg").datagrid('endEdit',lastIndex);
	           	lastIndex=rowIndex;
	           	//$("#dg").datagrid('endEdit',rowIndex);
	           	$("#dg").datagrid('beginEdit',rowIndex);
	           	var POINT_NAME = rowData.POINT_NAME;
	           	$("input.datagrid-editable-input").val(POINT_NAME)
	           	.bind("blur",function(evt)
	           	{
	           		//alert(pressCommandFlag);
	           		if(rowData.POINT_NAME != $(this).val() && pressCommandFlag==0)
	           		{
	           			$(this).val(rowData.POINT_NAME);
	           			 $("#dg").datagrid('endEdit',lastIndex);
	           		}
	           	}).
	           	bind("focus",function(evt)
	           	{
	           		//alert("focus");
	           		 pressCommandFlag = 0;
	           	}).bind("keypress",function(evt){
	               if(evt.keyCode==13){
	               	
	                  updatePoint($(this),rowData);
	                  pressCommandFlag = 1;
	               }
	           }).focus();
	        },
				columns : [ [ {
					field : 'POINT_ID',
					title : '点ID',
					checkbox : true
				}, {
					field : 'POINT_NO',
					title : '点编码',
					width : "15%",
					align : 'center'
				}, {
					field : 'POINT_NAME',
					title : '点名称',
					width : "15%",
					align : 'center',
					editor:{type:'text'}
				}, {
					field : 'LEVEL_NAME',
					title : '维护等级',
					width : "7%",
					align : 'center'
				},{
					field : 'POINT_TYPE_NAME',
					title : '点类型',
					width : "8%",
					align : 'center'
				}, {
					field : 'EQUIPMENT_TYPE_NAME',
					title : '设备类型',
					width : "7%",
					align : 'center'
				}, {
					field : 'LONGITUDE',
					title : '经度',
					width : "7%",
					align : 'center'
				}, {
					field : 'LATITUDE',
					title : '纬度',
					width : "7%",
					align : 'center'
				}, {
					field : 'ADDRESS',
					title : '地址',
					width : "15%",
					align : 'center'
				}, {
					field : 'AREA',
					title : '地区',
					width : "8%",
					align : 'center'
				}, {
					field : 'DEPT_NAME',
					title : '所属网格',
					width : "8%",
					align : 'center'
				}, {
					field : 'AREA_TYPE',
					title : '设备位置',
					width : "8%",
					align : 'center'
				}, {
					field : 'SON_AREA',
					title : '所属区域',
					width : "8%",
					align : 'center'
				} ] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
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

		function clearCondition() {
			$("input[name='point_no']").val("");
			$("input[name='point_name']").val("");
			$("input[name='address']").val("");
			$("input[name='point_type']").val("");
		}

		function addPoint() {
			location.href = webPath + "Lxxj/point/manage/add.do";
		}

		function delPoint() {
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
				$.messager
						.confirm(
								'系统提示',
								'删除关键点后，会删除与此点相关的所有信息（包括：采集坐标的点、所创建的计划和任务）。您确定要删除所选关键点吗?',
								function(r) {
									if (r) {
										var arr = new Array();
										for ( var i = 0; i < selected.length; i++) {
											var value = selected[i].POINT_ID;
											arr[arr.length] = value;
										}
										$("input[name='selected']").val(arr);
										$('#form')
												.form(
														'submit',
														{
															url : webPath
																	+ "Lxxj/point/manage/delete.do",
															onSubmit : function() {
																$.messager
																		.progress();
															},
															success : function(
																	json) {
																var data = eval('('
																		+ json
																		+ ')');
																if (data.status) {
																	$.messager
																			.progress('close'); // 如果提交成功则隐藏进度条
																	searchData();
																	$.messager
																			.show({
																				title : '提  示',
																				msg : '成功删除关键点!',
																				showType : 'show'
																			});
																} else {
																	$.messager
																			.progress('close');
																	$.messager
																			.confirm(
																					'系统提示',
																					'关键点'
																							+ data.message
																							+ '已生成计划，是否同时删除该计划',
																					function(
																							v) {
																						if (v) {
																							$(
																									'#form')
																									.form(
																											'submit',
																											{
																												url : webPath
																														+ "Lxxj/point/manage/delete.do?delFlag=1",
																												onSubmit : function() {
																													$.messager
																															.progress();
																												},
																												success : function(
																														json) {
																													var data = eval('('
																															+ json
																															+ ')');
																													if (data.status) {
																														$.messager
																																.progress('close'); // 如果提交成功则隐藏进度条
																														searchData();
																														$.messager
																																.show({
																																	title : '提  示',
																																	msg : '成功删除关键点!',
																																	showType : 'show'
																																});
																													}
																												}
																											});
																						}
																					});
																}
															}
														});
									}
								});
			}
		}

		function importPoint() {
			$('#win_staff').window({
				title : "【导入关键点】",
				href : webPath + "Lxxj/point/manage/import.do",
				width : 400,
				height : 250,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}

		function importPoints() {
			$.messager.confirm('系统提示', '您确定要导入关键点吗?', function(r) {
				if (r) {
					$('#sv').form('submit', {
						url : webPath + "Lxxj/point/manage/import_save.do",
						onSubmit : function() {
							$.messager.progress();
						},
						success : function(data) {
							$.messager.progress('close'); // 如果提交成功则隐藏进度条
							var json = eval('(' + data + ')');
							if (json.status) {
								$.messager.show({
									title : '提  示',
									msg : '导入关键点成功!',
									showType : 'show'
								});
								$('#win_staff').window('close');
							} else {
								$.messager.show({
									title : '提  示',
									msg : json.message,
									showType : 'show',
									width : 350,
									height : 150
								});
							}
						}
					});
				}
			});
		}

		function downloadExample() {
			$("#hiddenIframe").attr("src", "./downloadExample.do");
		}

		function exportPoint() {
			/*  
				var arr = new Array();
				try {
					var selected = $('#dg').datagrid('getChecked');
					for ( var i = 0; i < selected.length; i++) {
						var value = selected[i].POINT_ID;
						arr[arr.length] = value;
					}
				} catch (e) {
				}
				//$.messager.progress();
				var iframe = $("#hiddenIframe");
				iframe.attr("src", "./export.do?ids=" + arr.toString());
			*/
			lastIndex=0;
			var pressCommandFlag = 0;//1表示按了回车键
			var point_no = $("input[name='point_no']").val();
			var point_name = $("input[name='point_name']").val();
			var address = $("input[name='address']").val();
			var point_type = $("select[name='point_type']").val();
			var son_area_id = $("select[name='son_area']").val();
			var point_level = $("select[name='point_level']").val();
			var dept_name = $("#dept_name").combobox("getValue");
			var obj = {
				point_no : point_no,
				point_name : point_name,
				address : address,
				point_type : point_type,
				son_area_id:son_area_id,
				point_level:point_level,
				dept_name:dept_name
			};
			$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {
            	if (r) {
	                $('#form').form('submit', {
	                    url:  webPath + "Lxxj/point/manage/export.do?son_area_id="+son_area_id,
	                    queryParams : obj,
	                    onSubmit: function () {
	                      //$.messager.progress();
	                	},
                    	success: function () {
	                      	//$.messager.progress('close'); // 如果提交成功则隐藏进度条
	                        $.messager.show({
	                            title: '提  示',
	                            msg: '导出成功!',
	                            showType: 'show'
	                        });
                    	}
	                });
	            }
	        });
		}
		
		function updatePoint(obj,rowData)
		{
		 	var pointName = obj.val();
		 	var pointId = rowData.POINT_ID;
		 	var pointType = rowData.POINT_TYPE;
 		 	var oldPointName = rowData.POINT_NAME;
 		 	if($.trim(pointName) =='')
			{
				$.messager.show({
					title : '错  误',
					msg : '关键点名称不能为空!',
					showType : 'show'
					});
					obj.val(oldPointName);
					obj.focus()
					return 0;
			}
			else if(pointType == 4 || pointType == '4')
			{
				$.messager.show({
					title : '错  误',
					msg : '设备点不能修改点名称!',
					showType : 'show'
					});
					obj.val(oldPointName);
					$("#dg").datagrid('endEdit',lastIndex);
					return 0;
			}
			$.messager.confirm('系统提示',
				'是否确定提交修改关键点名称?',
				function(r) {
					if (r) {
						$.ajax({
							type : 'POST',
							url : webPath+ "Lxxj/point/manage/updatePointName.do",
							data : {
								pointName : pointName,
								pointId  : pointId},
							async: false,	
							success : function(json) 
							{
								var data = eval('('+ json+ ')');
								if (data.status) {
									$.messager.show({
										title : '提  示',
										msg : '修改成功!',
										showType : 'show'
									});
									obj.val(pointName);
									$("#dg").datagrid('endEdit',lastIndex);
								}
								else{
									$.messager.show({
										title : '警  告',
										msg : '修改失败，请重试!',
										showType : 'show'
									});
									obj.val(pointName);
									obj.focus();
								}
								
							},
							error : function(json) 
							{
							
							obj.val(pointName);
							obj.focus();
							}
						});
					}
					//取消提交
					else
					{
						obj.val(pointName);
						obj.focus();
					}
				});
		}
		
		function updateCoordinate(){
			var selected = $('#dg').datagrid("getChecked");
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选择一个内容!',
					showType : 'show'
				});
				return;
			}else if (count > 1){
				$.messager.show({
					title : '提  示',
					msg : '选择数目太多',
					showType : 'show'
				});
				return;
			}
			var point_id = selected[0].POINT_ID;
			{
			addTab("关键点修改", webPath + "Lxxj/point/manage/toUpdatePage.do?POINT_ID=" + point_id);
			}
		}
		
		function deptTips(){
		    $("#dept_name").combobox({
		    	valueField: 'DEPT_ID', //TPrice
                textField: 'DEPT_NAME',
                 //注册事件
                 onChange: function (newValue, oldValue) {
                     if (newValue != null) {
                         var thisKey = $("#dept_name").combobox('getText'); //搜索词
                         var urlStr = webPath+"Dept/deptSelectTip.do?dept_name="+thisKey;
                         var v = $("#dept_name").combobox("reload", urlStr);
                     }
                 },
                 onSelect: function (record) {
                     $("#dept_name").combobox('setValue', record.DEPT_NAME);   
                 },
                 click:function(newValue, oldValue){
                         var thisKey = $("#dept_name").combobox('getText'); //搜索词
                         var urlStr = webPath+"Dept/deptSelectTip.do?dept_name="+thisKey;
                         var v = $("#dept_name").combobox("reload", urlStr);
                 }
		    });  
		}
	</script>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
</body>
</html>