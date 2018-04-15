<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>光缆管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">光缆名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="cable_name" type="text" class="condition-text" />
							</div></td>

					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
		    <c:if test="${isAdmin==0}">
		       <div class="btn-operation" onClick="addUI()">增加</div>
			   <div class="btn-operation" onClick="editUI()">编辑</div>
			   <div class="btn-operation" onClick="deleteUI()">删除</div>
		    </c:if>
			
			

			<%--<div style="float:right;" class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			--%><div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【光缆管理】" style="width: 100%; height: 480px">
	</table>
	<div id="win"></div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
		
			searchData();
		});
		
		
		function searchData() {
			
			var obj=makeParamJson('form');
			if(${isAdmin==1}){
				obj.area_id='${param_areaId}';
			}
			
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "gldManage/query.do",
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
					field : 'CABLE_ID',
					title : '光缆ID',
					checkbox : true
				}, {
					field : 'CABLE_NAME',
					title : '光缆名称',
					width : "15%",
					align : 'center'
				}, {
					field : 'CABLE_COLOR',
					title : '光缆颜色',
					width : "15%",
					align : 'center',
					styler:function(value,data,index){
						return "background-color:#"+value+";";
					}
					
					
				}, {
					field : 'FIBER_GRADE',
					title : '光缆等级',
					width : "20%",
					align : 'center',
					formatter : function(value, rec,
							index) {
						if(value=="1"){
							return "一级";
						}else if(value=="2"){
							return "二级";
						}else{
							return "三级";
						}
						
					}
				},{
					field : 'AREA_NAME',
					title : '所属地区',
					width : "25%",
					align : 'center'
				}] ],
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
			
			$("#"+form_id+"").form('reset', 'none');
			
		}

		function addUI() {
		   
			var url=webPath + "gldManage/addUI.do";
			 var res=window.showModalDialog(url, null,
							"dialogWidth=400px;dialogHeight=450px;status:no;resizable:yes;");
			 if(res==1){
				 searchData();
			 }
// 			 $('#win').window({
// 					title : "【新增】",
// 					href : webPath + "gldManage/addUI.do",
// 					width : 400,
// 					height : 450,
// 					zIndex : 2,
// 					region : "center",
// 					collapsible : false,
// 					cache : false,
// 					modal : false
// 				});
// 			 searchData();
		}
		
		function saveForm2() {
			var pass = $("#ff").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要新增光缆吗?', function(r) {
					if (r) {
						var data=makeParamJson('#ff');
						$.ajax({
							type : 'POST',
							url : webPath + "gldManage/save.do",
							data : data,
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.alert("提示","新增光缆成功！","info");
								}
								else{
									$.messager.alert("提示","新增光缆失败！","info");
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
		
		
		function editUI() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show',
					 timeout:'1000'//ms
				});
				return;
			} else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show',
					timeout:'1000'//ms
				});
				return;
			} else {
				var cable_id = selected[0].CABLE_ID;
				/*
				$('#win').window({
					title : "【编辑】",
					href : webPath + "gldManage/editUI.do?id=" + cable_id,
					width : 400,
					height : 500,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				}); */
// 				 $('#win').window({
// 						title : "【编辑】",
// 						href : webPath + "gldManage/editUI.do?id="+cable_id,
// 						width : 400,
// 						height : 450,
// 						zIndex : 2,
// 						region : "center",
// 						collapsible : false,
// 						cache : false,
// 						modal : true
// 					});
// 				 searchData();
				var url=webPath + "gldManage/editUI.do?id="+cable_id;
				var res=window.showModalDialog(url, null,
								"dialogWidth=400px;dialogHeight=450px;status:no;resizable:yes;");
				 if(res==1){
					 searchData();
				 }
				
			}

		}

		function deleteUI() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取要删除的数据!',
					showType : 'show',
					timeout:'1000'//ms
				});
				return;
			} else {
				$.messager.confirm('系统提示', '您确定要删除光缆吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].CABLE_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$.messager.progress();
						$.ajax({          async:false,
							  type:"post",
							  url : webPath + "gldManage/delete.do",
							  data:{ids:$("input[name='selected']").val()},
							  dataType:"json",
							  success:function(data){
								  $.messager.progress('close');
								  if(data.status){
										
										searchData();
										$.messager.show({
											title : '提  示',
											msg : '成功删除光缆!',
											showType : 'show',
											timeout:'1000'//ms
										   
										});
									}else{
										alert("删除失败");
									}
							  }
						  });
						
						
					}
				});
			}
		}


		
		
		function updateForm() {
			var pass = $("#ff").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要更新员工吗?', function(r) {
					if (r) {
						var obj=makeParamJson('form');
						$.ajax({
							type : 'POST',
							url : webPath + "Staff/update.do",
							data : obj,
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '更新员工成功!',
										showType : 'show',
										timeout:'1000'//ms
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
		

</script>

</body>
</html>