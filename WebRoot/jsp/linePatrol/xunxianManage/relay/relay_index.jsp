
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>中继段管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<table id="dg" title="【中继段管理】" style="width: 100%; height: 480px">
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">中继段名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="relay_name" id="relay_name" type="text"
									class="condition-text" />
							</div></td>
						<td width="10%">所属光缆：</td>
						<td width="20%">
							<div>
								<select name="cable_id" id="cable_id" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${cableList}" var="res">

										<option value='${res.CABLE_ID}'>${res.CABLE_NAME}</option>
									</c:forEach>
								</select>


							</div></td>

					</tr>




				</table>
			</form>
		</div>
		<div class="btn-operation-container">
		<c:if test="${isAdmin==0}">
		   <div class="btn-operation" onClick="relayAddUI()">增加</div>
			<div class="btn-operation" onClick="relayEditUI()">编辑</div>
			<div class="btn-operation" onClick="relayDeleteUI()">删除</div>
		</c:if>
			


			<%--<div style="float:right;" class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			--%><div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
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
				url : webPath + "relayController/query.do",
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
					field : 'RELAY_ID',
					title : '中继段ID',
					checkbox : true
				}, {
					field : 'RELAY_NAME',
					title : '中继段名称',
					width : "25%",
					align : 'center'
				}, {
					field : 'CABLE_NAME',
					title : '所属光缆',
					width : "25%",
					align : 'center'
				},{
					field : 'PROTECT_GRADE',
					title : '维护等级',
					width : "25%",
					align : 'center'
				},{
					field : 'AREA_NAME',
					title : '所属地区',
					width : "25%",
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
			
			$("#"+form_id+"").form('reset', 'none');
			
		}

		function relayAddUI() {
		   /*
			var url=webPath + "relayController/relayAddUI.do";
			 var res=window.showModalDialog(url, null,
							"dialogWidth=400px;dialogHeight=300px;status:no;resizable:yes;");
			 if(res==1){
				 searchData();
			 }  */
			 
			 $('#win').window({
					title : "【新增中继段】",
					href : webPath + "relayController/relayAddUI.do",
					width : 400,
					height : 450,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
		}
		
		function saveForm() {
			var pass = $("#formAdd").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要新增中继段吗?', function(r) {
					if (r) {
						var data=makeParamJson('formAdd');
						
						var area_id="";
						$("[name=area_id]").each(function(){
							if(this.checked){
								area_id=area_id+this.value+",";
							}
							
						});
						data.area_id=area_id;
						
						$.ajax({
							type : 'POST',
							url : webPath + "relayController/relaySave.do",
							data : data,
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									
									$.messager.show({
										title : '提  示',
										msg : '新增中继段成功!',
										showType : 'show',
										timeout:'1000'//ms
									});
								}
								else{
									$.messager.alert("提示","新增中继段失败！","info");
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
		
		
		function relayEditUI() {
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
				var relay_id = selected[0].RELAY_ID;
			
				$('#win').window({
					title : "【中继段编辑编辑】",
					href : webPath + "relayController/relayEditUI.do?id=" + relay_id,
					width : 400,
					height : 450,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
				/*
				var url=webPath + "relayController/relayEditUI.do?id="+relay_id;
				 var res=window.showModalDialog(url, null,
								"dialogWidth=400px;dialogHeight=300px;status:no;resizable:yes;");
				 if(res==1){
					 searchData();
				 }
				 */
				
			}

		}

		function relayDeleteUI() {
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
				$.messager.confirm('系统提示', '您确定要删除中继段吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].RELAY_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$.messager.progress();
						$.ajax({          async:false,
							  type:"post",
							  url : webPath + "relayController/relayDelete.do",
							  data:{ids:$("input[name='selected']").val()},
							  dataType:"json",
							  success:function(data){
								  $.messager.progress('close');
								  if(data.status){
										
										searchData();
										$.messager.show({
											title : '提  示',
											msg : '成功删除中继段!',
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
			var pass = $("#formUpdate").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要更新中继段吗?', function(r) {
					if (r) {
						var obj=makeParamJson('formUpdate');
						
						var area_id="";
						$("[name=area_id]").each(function(){
							if(this.checked){
								area_id=area_id+this.value+",";
							}
							
						});
						obj.area_id=area_id;
						
						$.ajax({
							type : 'POST',
							url : webPath + "relayController/relayUpdate.do",
							data : obj,
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '更新中继段成功!',
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
		
		function getOwnArea(cable_id){
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "relayController/getOwnArea.do",
				  data:{cable_id:cable_id},
				  dataType:"json",
				  success:function(data){
					  $("#area_ids").empty();
					  //$("#cable_id").append("<option value=''>--请选择--</option>");	
					 
					  $.each(data.ownArea,function(i,item){
						  $("#area_ids").append(" <input type='checkbox' name='area_id' value='"+item.AREA_ID+"'/>"+item.AREA_NAME+"<br/>");		
					  });
				  }
			  });
		}

</script>

</body>
</html>
