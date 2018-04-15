
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>巡线段管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<c:if test="${isAdmin==0}">
						  <td width="10%">地区：</td>
							<td width="20%">
								<div>
									<select name="p_area_id" id="p_area_id" class="condition-select" onchange="getCableAndInsStaff(this.value)" >
										<option value=''>--请选择--</option>
										<c:forEach items="${areaList}" var="res">
											<option value='${res.AREA_ID}'>${res.NAME}</option>
										</c:forEach>
									</select>
								</div>
							</td>
						</c:if>
						<td width="10%">光缆段：</td>
						<td width="20%">
							<div>
								<select name="p_cable_id" id="p_cable_id" class="condition-select" onchange="getRelay(this.value)">
									<option value=''>--请选择--</option>
									<c:forEach items="${cableList}" var="res">
										<option value='${res.CABLE_ID}'>${res.CABLE_NAME}</option>
									</c:forEach>
								</select>
							</div>
						</td>
						<td width="10%">中继段：</td>
						<td width="20%">
							<div>
								<select name="p_relay_id" id="p_relay_id" class="condition-select">
								</select>
							</div>
						</td>
						
					</tr>
					<tr>
					<td width="10%">巡线段名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_line_name" id="p_line_name" type="text"
									class="condition-text" />
							</div>
						</td>
					  <td width="10%">巡线人：</td>
						<td width="20%">
							<div class="condition-text-container">
								<%--<select name="p_inspect_id" id="p_inspect_id" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${localInspectStaff}" var="res">
										<option value='${res.STAFF_ID}'>${res.STAFF_NAME}</option>
									</c:forEach>
								</select>
							--%>
							<input name="p_inspect_name" id="p_inspect_name" type="text"
									class="condition-text" />
							
							</div>
						</td>
					</tr>
					


				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<c:if test="${isAdmin==1}">
				<div class="btn-operation" onClick="lineInfoAddUI()">增加</div>
				<div class="btn-operation" onClick="lineInfoEditUI()">编辑</div>
				<div class="btn-operation" onClick="lineInfoDeleteUI()">删除</div>
			</c:if>
			<div style="float: right;" class="btn-operation" onClick="showMap()">地图展示</div>
			<div style="float: right;" class="btn-operation" onClick="insertSite()">巡线点插入</div>
			<div style="float: right;" class="btn-operation" onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【巡线段管理】" style="width: 100%; height: 480px">
	</table>
	<div id="win"></div>
	<div id="win_distribute"></div>

	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {

			//searchData();
		});

		function searchData() {
			
			if('${isAdmin}'=='0'){
				if($("#p_area_id").val()==''){
					alert("地区不能为空");
					return false;
				}
			}

			var obj = makeParamJson('form');

			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "lineInfoController/query.do",
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
					field : 'LINE_ID',
					title : '巡线段ID',
					checkbox : true
				}, {
					field : 'LINE_NAME',
					title : '巡线段名称',
					width : "10%",
					align : 'center'
				}, {
					field : 'CABLE_NAME',
					title : '光缆段',
					width : "10%",
					align : 'center'
				}, {
					field : 'RELAY_NAME',
					title : '中继段',
					width : "10%",
					align : 'center'
				},{
					field : 'DISTANCE',
					title : '巡线段长度',
					width : "10%",
					align : 'center',
					formatter : function(value, rec,
							index) {
						return "<span>"+value+"公里</span>";
					}
				}, {
					field : 'INSPECT_NAME',
					title : '巡检人',
					width : "10%",
					align : 'center'
				}, {
					field : 'AREA_NAME',
					title : '地区',
					width : "10%",
					align : 'center'
				},  {
					field : 'CREATE_PERSON',
					title : '创建人',
					width : "10%",
					align : 'center'
				}, {
					field : 'CREATE_TIME',
					title : '创建时间',
					width : "10%",
					align : 'center'
				},{
					field : 'OPR',
					title : '操作',
					width : "10%",
					align : 'center',
					formatter : function(value, rec,
							index) {
						return "&nbsp;&nbsp;<a href='javascript:showTheLineOnMap("+rec.LINE_ID+")'>地图</a>&nbsp;&nbsp;";
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

		function lineInfoAddUI() {
			
			
			location.href="lineInfoAddUI.do";
		}

		function saveForm() {
			var pass = $("#formAdd").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要新增巡线段吗?', function(r) {
					if (r) {
						var data = makeParamJson('formAdd');
						$.ajax({
							type : 'POST',
							url : webPath
									+ "lineInfoController/lineInfoSave.do",
							data : data,
							dataType : 'json',
							success : function(json) {
								if (json.status) {

									$.messager.show({
										title : '提  示',
										msg : '新增巡线段成功！',
										showType : 'show',
										timeout : '1000'//ms

									});
								} else {
									$.messager.alert("提示", "新增巡线段失败！", "info");
									return;
								}
								$('#win').window('close');
								searchData();

							}
						})
					}
				});
			}
		}

		function lineInfoEditUI() {
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
				var line_id = selected[0].LINE_ID;
				location.href="lineInfoEditUI.do?line_id="+line_id;
				


			}

		}

		function lineInfoDeleteUI() {
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
				$.messager.confirm('系统提示', '您确定要删除巡线段吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].LINE_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$.messager.progress();
						$.ajax({
							async : false,
							type : "post",
							url : webPath
									+ "lineInfoController/lineInfoDelete.do",
							data : {
								ids : $("input[name='selected']").val()
							},
							dataType : "json",
							success : function(data) {
								$.messager.progress('close');
								if (data.status) {

									searchData();
									$.messager.show({
										title : '提  示',
										msg : '成功删除巡线段!',
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

		function updateForm() {
			var pass = $("#formEdit").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要更新巡线段吗?', function(r) {
					if (r) {
						var obj = makeParamJson('formEdit');
						$.ajax({
							type : 'POST',
							url : webPath
									+ "lineInfoController/lineInfoUpdate.do",
							data : obj,
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '更新巡线段成功!',
										showType : 'show',
										timeout : '1000'//ms
									});
								}
								$('#win').window('close');
								searchData();
							}
						})
					}
				});
			}
		}
		
		
		function showTheLineOnMap(line_id){
		    
			addTab("巡线段地图展示",webPath+"lineInfoController/showTheLineOnMap.do?line_id="+line_id);
			
		}
		// TODO
		function showMap(){
			var obj = makeParamJson('form');
			addTab("地图展示",webPath+"lineInfoController/mapShow.do?p_area_id="+obj.p_area_id);
			/*  
			$.ajax({
				type:"post",
				data:obj,
				url:webPath + "lineInfoController/queryForMap.do",
				success:function(data){
					if(data != null && data != ""){
						var rows = data.rows;
						var lineIds = "";
						for(var i=0;i<rows.length;i++){
							lineIds += rows[i].LINE_ID + ",";
						}
						lineIds = lineIds.substring(0, lineIds.length-1);
						addTab("地图展示",webPath+"lineInfoController/mapShow.do?lineIds="+lineIds);
					}
				}
			}); */
		}
		
		function getRelay(cable_id){
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "lineInfoController/getRelay.do",
				  data:{cable_id:cable_id},
				  dataType:"json",
				  success:function(data){
					  $("#p_relay_id").empty();
					  $("#p_relay_id").append("<option value=''>--请选择--</option>");		
					  $.each(data.relayList,function(i,item){
						  $("#p_relay_id").append("<option value='"+item.RELAY_ID+"'>"+item.RELAY_NAME+"</option>");		
					  });
				  }
			  });
		}
		
		function getCableAndInsStaff(area_id){
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "lineInfoController/getCableAndInsStaff.do",
				  data:{area_id:area_id},
				  dataType:"json",
				  success:function(data){
					  $("#p_cable_id").empty();
					  $("#p_cable_id").append("<option value=''>--请选择--</option>");		
					  $.each(data.cableList,function(i,item){
						  $("#p_cable_id").append("<option value='"+item.CABLE_ID+"'>"+item.CABLE_NAME+"</option>");		
					  });
					  
					  $("#p_inspect_id").empty();
					  $("#p_inspect_id").append("<option value=''>--请选择--</option>");		
					  $.each(data.insStaffList,function(i,item){
						  $("#p_inspect_id").append("<option value='"+item.STAFF_ID+"'>"+item.STAFF_NAME+"</option>");		
					  });
				  }
			  });
		}
		
		//巡线点点插入
		function insertSite(){
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
			}
			var line_id = selected[0].LINE_ID;
			var relay_id = selected[0].RELAY_ID;
			$('#win_distribute').window({
				title : "【巡线点列表】",
				href : webPath + "lineInfoController/addSiteInLine.do?lineId=" + line_id+"&relayId="+relay_id,
				width : 820,
				height : 500,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		
		
	</script>

</body>
</html>
