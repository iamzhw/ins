<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>光缆段管理</title>
</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">区域：</td>
						<td width="18%">
							<select name="son_area" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
					<td width="10%">光缆段编号：</td>
						<td width="18%">
							<div class="condition-text-container">
								<input name="cable_no" type="text" class="condition-text" />
							</div></td>
						<td width="10%">光缆段名称：</td>
						<td width="18%">
							<div class="condition-text-container">
								<input name="cable_name" type="text" class="condition-text" />
							</div></td>
						<td width="10%">所属光缆编码：</td>
						<td width="18%">
							<div class="condition-text-container">
								<input name="parent_cable_no" type="text" class="condition-text" />
							</div></td>
					</tr>
					<tr>
						<td width="10%">所属光缆名称：</td>
						<td width="18%">
							<div class="condition-text-container">
								<input name="parent_cable_name" type="text" class="condition-text" />
							</div>
						</td>
						
						<td width="10%">缆线类型：</td>
						<td width="18%">
							<select name="line_type" class="condition-select">
									<option value="">
										请选择
									</option>
									<option value="0">
										光缆段
									</option>
									<option value="1">
										区域型
									</option>
									<option value="4">
										光缆
									</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="addCable()">增加线型缆线</div>
			<div class="btn-operation" onClick="addCablePolygon()">增加区域型缆线</div>
			<c:if test="${admin||!isSonAreaAdmin }">
				<div class="btn-operation" onClick="delCable()">删除</div>
			</c:if>
			<div class="btn-operation" onClick="exportLinePoint()">导出</div>
			<div class="btn-operation" onClick="editLinePointPage()">导入修改</div>
			<div class="btn-operation" onClick="toEditParentLinePage()">修改所属光缆</div>
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
				<input name="area_id" type="hidden" value=""/>
				<input name="son_area_id" type="hidden" value=""/>
		</div>
	</div>
	<table id="dg" title="【光缆段管理】" style="width:100%;height:480px">
	</table>
	<div id="win_Cable"></div>
	<div id="Line_Cable"></div>
	<div id="parent_line"></div>
	<div id="add_parent_line"></div>
</body>
</html>
<script type="text/javascript">
	$(document).ready(function() {
		$("select[name='son_area']").change(function() {
				//根据area_id，获取区县
				$("input[name='son_area_id']").val($("select[name='son_area']").val());
			});
		selectSelected();
		getSonAreaList();
		//searchData();
	});
	function selectSelected(){
		$.ajax({
				type : 'POST',
				url : webPath + "Staff/selectSelected.do",
				dataType : 'json',
				async:false,
				success : function(json) {
				$("input[name='area_id']").val("${areaId}");
				if(${isSonAreaAdmin})
				{
					$("input[name='son_area_id']").val("${sonAreaId}");
				}
				if(json[0].ifGly!=1){
				//$("input[name='son_area_id']").val(${sonAreaId});
				}
			}
			});
		}
		function getSonAreaList() {
		var areaId=$("input[name='area_id']").val();
		if(null==areaId||""==areaId)
		{
			$("select[name='son_area'] option").remove();
			return;
		}
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
				var isSonAreaAdmin ='${isSonAreaAdmin}';
				if (undefined !=isSonAreaAdmin && isSonAreaAdmin!='true'){
				$("select[name='son_area']").append(
							"<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
					
						$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
					}
					
				}
				else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID=="${sonAreaId}"){
							$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
						}
					}
				}
			}
		});
	}
	function searchData() {
		var cable_name = $("input[name='cable_name']").val().trim();
		var cable_no = $("input[name='cable_no']").val().trim();
		var area_id=$("input[name='area_id']").val().trim();
		var son_area_id=$("input[name='son_area_id']").val().trim();
		var parent_cable_no = $("input[name='parent_cable_no']").val().trim();
		var parent_cable_name =$("input[name='parent_cable_name']").val().trim();
		
		var LINE_TYPE = $("select[name='line_type']").val().trim();
		var obj = {
			cable_name : cable_name,
			cable_no : cable_no,
			area_id:area_id,
			son_area_id:son_area_id,
			parent_cable_name:parent_cable_name,
			parent_cable_no:parent_cable_no,
			LINE_TYPE:LINE_TYPE
		};
		//return;
		$('#dg').datagrid({
			//此选项打开，表格会自动适配宽度和高度。
			autoSize : true,
			toolbar : '#tb',
			url : webPath + "Cable/query.do",
			queryParams : obj,
			method : 'post',
			pagination : true,
			pageNumber : 1,
			pageSize : 10,
			pageList : [ 20, 50 ],
			//loadMsg:'数据加载中.....',
			rownumbers : true,
			fit:true,
			singleSelect : true,
			columns : [ [ {
				field : 'CABLE_ID',
				title : '光缆ID',
				width : "5%",
				checkbox : true
			}, {
				field : 'CABLE_NO',
				title : '光缆段编码',
				width : "13%",
				align : 'center'
			} ,{
				field : 'CABLE_NAME',
				title : '光缆段名称',
				width : "13%",
				align : 'center'
			},{
				field : 'LINE_TYPE',
				title : '缆线类型',
				width : "6%",
				align : 'center',
				formatter:function(value,row,Index){
					    // alert(jQuery.type(value)+"  "+row.LINE_TYPE);
						if (null!=row.LINE_TYPE&&""!=row.LINE_TYPE.toString()){
							if("0"==row.LINE_TYPE||0==row.LINE_TYPE){
								return "光缆段";
							}else if("1"==row.LINE_TYPE||1==row.LINE_TYPE){
								return "区域型";
							}else if("4"==row.LINE_TYPE||4==row.LINE_TYPE){
								return "光缆";
							}else{
								return null;
							}
						}else{
							return row.LINE_TYPE;
						}
		         }
			},{
				field : 'LINE_LEVEL',
				title : '光缆等级',
				width : "5%",
				align : 'center'
			},{
				field : 'PARENT_CABLE_NO',
				title : '所属光缆编码',
				width : "14%",
				align : 'center'
			},{
				field : 'PARENT_CABLE_NAME',
				title : '所属光缆名称',
				width : "14%",
				align : 'center'
			},{
				field : 'CREATE_STAFF',
				title : '创建人',
				width : "5%",
				align : 'center'
			},{
				field : 'KEY_POINT',
				title : '关键点数',
				width : "6%",
				align : 'center'
			},{
				field : 'NOR_POINT',
				title : '非关键点数',
				width : "7%",
				align : 'center'
			},{
				field : 'DIS',
				title : '光缆皮长(公里)',
				width : "7%",
				align : 'center'
			},{
				field : 'CREATE_TIME',
				title : '创建时间',
				width : "8%",
				align : 'center'
			},{
				field : 'AREA_NAME',
				title : '地市',
				width : "6%",
				align : 'center'
			},{
				field : 'SON_AREA_NAME',
				title : '地区',
				width : "7%",
				align : 'center'
			}, {
				field : 'CABLEID',
				title : '操作',
				width : "10%",
				align : 'center',
				formatter:function(value,rowData,rowIndex){
                	return "<a href=\"javascript:onClickRow(" + rowData.CABLEID + ");\">地图</a>&nbsp;&nbsp;<a href=\"javascript:edit(" + value + ");\">编辑</a>";
                } 
			}] ],
			//width : 'auto',
			nowrap : false,
			striped : true,
			//onCheck:onCheck,
			//onSelect:onSelect,
			//onSelectAll:onSelectAll,
			onLoadSuccess : function(data) {
			$(this).datagrid("fixRownumber");
			$("body").resize();
			}
		});
	}
	function clearCondition(form_id) {
		$("input[name='cable_name']").val('');
		$("input[name='cable_no']").val('');
	}
	
	function addCable() {
	//addTab("缆线新增",webPath+"Cable/add.do");
		location.href = "add.do";
	}
	
	function addCablePolygon() {
		
		location.href= "add.do?shape=polygon";
	}
	
	/**选择行触发**/
	function onClickRow(cable_id) {
	addTab("查看缆线",webPath+"Cable/show.do?cableId="+cable_id);
		//location.href = "show.do?cableId="+row.CABLE_ID;
	}
	function edit(cable_id) {
	addTab("查看缆线",webPath+"Cable/edit.do?cableId="+cable_id);
		//location.href = "show.do?cableId="+row.CABLE_ID;
	}
	
	function delCable() {
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
		$.messager.confirm('系统提示', '您确定要删除该缆线吗?', function(r) {
			if (r) {
			var arr = new Array();
			for ( var i = 0; i < selected.length; i++) {
				var value = selected[i].CABLE_ID;
				arr[arr.length] = value;
			}
			$("input[name='selected']").val(arr);
			$('#form').form('submit', {
				url : webPath + "Cable/deleteCable.do",
				onSubmit : function() {
					$.messager.progress();
				},
				success : function(json) {
					var data=JSON.parse(json);
					$.messager.progress('close'); // 如果提交成功则隐藏进度条
					$.messager.show({
						title : '提  示',
						msg : data.desc,
						showType : 'show'
					});
					searchData();
				}
			});
			}
			});
<%-- 	
		$.messager.progress();	// 显示进度条
		$("#form").attr("action",webPath+"Staff/delete.do");
	    $("#form").submit();
		--%>
	}

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
	
	function editStaff() {
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
			var staff_id = selected[0].STAFF_ID;
			$('#win_staff').window({
				title : "【编辑员工】",
				href : webPath + "Staff/edit.do?staff_id=" + staff_id,
				width : 400,
				height : 450,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}

	}

	function exportLinePoint(){
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
		}else{
			var line_id = selected[0].CABLE_ID;
			$.messager.confirm('系统提示', '您确定要导出该报表吗?', function(r) {
			if (r) {
				$('#form').form('submit', {
					url : webPath + "Cable/exportLinePoint.do?line_id="+line_id,
					onSubmit : function() {
						//  $.messager.progress();
					},
					success : function() {
						//  $.messager.progress('close'); // 如果提交成功则隐藏进度条
						$.messager.show({
							title : '提  示',
							msg : '导出成功!',
							showType : 'show'
						});

					}
				});
			}
		});
		}
	}
	
	function editLinePointPage(){
		$('#Line_Cable').window({
    			title : "【导入】",
    			href : webPath + "Cable/editLinePointPage.do",
    			width : 400,
    			height : 250,
    			zIndex : 2,
    			region : "center",
    			collapsible : false,
    			cache : false,
    			modal : true
    	});
	}
	
	function editLinePoint(){
		$.messager.confirm('系统提示', '您确定要导入吗?', function(r) {
    			if (r) {
    				 $('#sv').form('submit', {
    	                 url: webPath + "Cable/editLinePoint.do",
    	                 onSubmit: function () {
    	                     $.messager.progress();
    	                 },
    	                 success: function (data) {
    	                     $.messager.progress('close'); // 如果提交成功则隐藏进度条
    	                     var json = eval('(' + data + ')');
    	                     if(json.status){
    	                         $('#Line_Cable').window('close');
    	                     	 searchData();
    	                    	 $.messager.show({
    		                         title: '提  示',
    		                         msg: '导入用户成功!',
    		                         showType: 'show'
    		                     });
    	                    	  	                   	                    	 
    	                     } else {
    	                     	 $('#Line_Cable').window('close');
    	                    	 $.messager.show({
    		                         title: '提  示',
    		                         msg: json.message,
    		                         showType: 'show',
    		                         width : 350,
    		                         height : 150
    		                     });  	                    
    	                     }
    	                 }
    	             });
    			}
    		});
	}
	
	function toEditParentLinePage(){
		var selected = $('#dg').datagrid('getChecked');
		var count = selected.length;
		if (count == 0) {
			$.messager.show({
				title : '提  示',
				msg : '请选取一条数据!',
				showType : 'show'
			});
			return;
		}
		$('#parent_line').window({
					title : "【选择缆线】",
					href : webPath + "Cable/parentLineSelectPage.do",
					width : 500,
					height : 460,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
	}
</script>
