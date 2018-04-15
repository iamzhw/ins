
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>隐患单管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<table id="dg" title="【隐患单管理】" style="width: 100%; height: 480px">
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">开始日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="start_time" id="start_time" onFocus="WdatePicker( { maxDate: '%y-%M-%{%d-1}' }  )" type="text" class="condition-text" />
							</div></td>
						
						<td width="10%">结束日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="end_time" id="end_time" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'start_time\')}',maxDate: '%y-%M-%{%d-1}' })" type="text" class="condition-text" />
							</div></td>
							
						<td width="5%">隐患单状态：</td>
						<td width="10%">
							 <div>
								<select name="p_order_status" id="p_order_status" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${dangerOrderState}" var="res">
										<option value='${res.STATE_ID}'>${res.STATE_NAME}</option>
									</c:forEach>
								</select>

							</tr>
							<tr>
							</div></td>
						<td width="5%">隐患描述：</td>
						<td width="10%">
							<div class="condition-text-container">
								<input name="p_danger_question" id="p_danger_question" type="text" class="condition-text" />
							</div></td>
	
							<td width="10%">发现人：</td>
						<td width="20%">
							
							
							<div>
								<select name="user_id" id="user_id" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${localInspactStaff}" var="res">
										<option value='${res.STAFF_ID}'>${res.STAFF_NAME}</option>
									</c:forEach>
								</select>
							</div>
							
							</td>
						<td width="10%">区域：</td>
						<td width="20%">
							<div>
								<select name="org_id" id="org_id" class="condition-select">
									<option value=''>--请选择--</option>
									<c:forEach items="${scopeList }" var="scope">
										<option value='${scope.ORG_ID}'>${scope.ORG_NAME}</option>
									</c:forEach>
								</select>
							</div>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<%--<div class="btn-operation" onClick="dangerOrderAddUI()">增加</div>
			--%><div class="btn-operation" onClick="distributeUI()">派单</div>
			<div class="btn-operation" onClick="deleteUI()">删除</div>
			<%--<div class="btn-operation" onClick="receiptUI()">回执</div>
			--%><div class="btn-operation" onClick="auditUI()">审核</div>
			<div class="btn-operation" onClick="dataDownload()">导出</div>
			
			<%--<div style="float:right;" class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			--%><div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<div id="win"></div>
	<div id="win_distribute"></div>
	<div id="win_auditUI"></div>
	<div id="win_detailUI"></div>
	<div id="win_showPhoto"></div>
	
	
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
		
			searchData();
		});
		
		
		function searchData() {
			var obj=makeParamJson('form');
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "dangerOrderController/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [20,50,100],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'ORDER_ID',
					title : '隐患单ID',
					checkbox : true
				},{
					field : 'DANGER_QUESTION',
					title : '隐患描述',
					width : "50%",
					align : 'center'
				},{
					field : 'ORG_NAME',
					title : '区域',
					width : "10%",
					align : 'center'
				},{
					field : 'FOUNDER_NAME',
					title : '发现人',
					width : "10%",
					align : 'center'
				},{
					field : 'FOUND_TIME',
					title : '发现时间',
					width : "10%",
					align : 'center'
				},{
					field : 'ORDER_STATUS',
					title : '隐患单状态',
					width : "10%",
					align : 'center'
				},{
					field : 'OP',
					title : '操作',
					width : "10%",
					align : 'center',
					formatter : function(value, rec,
							index) {
						return "&nbsp;&nbsp;<a  style='color:blue' onclick='detailUI(\""
						+ rec.ORDER_ID
						+ "\")'>详情</a>&nbsp;&nbsp;";
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
			
			$("#"+form_id+"").form('reset', 'none');
			
		}

		function dangerOrderAddUI() {
		   /*
			var url=webPath + "dangerOrderController/dangerOrderAddUI.do";
			 var res=window.showModalDialog(url, null,
							"dialogWidth=400px;dialogHeight=300px;status:no;resizable:yes;");
			 if(res==1){
				 searchData();
			 } */
			 $('#win').window({
					title : "【新增隐患单】",
					href : webPath + "dangerOrderController/dangerOrderAddUI.do",
					width : 400,
					height : 400,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
		}
		
		function saveForm() {
			var pass = true;
		
			if($("#danger_name").val().trim()==''){
				alert("隐患名称不能为空！");
				pass=false;
				return ;
			}
			if($("#danger_question").val().trim()==''){
				alert("隐患内容不能为空！");
				pass=false;
				return ;
			}
			
			
			if (pass) {
				$.messager.confirm('系统提示', '您确定要新增隐患单吗?', function(r) {
					if (r) {
						var data=makeParamJson('formAdd');
						$.ajax({
							type : 'POST',
							url : webPath + "dangerOrderController/dangerOrderSave.do",
							data : data,
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									
									$.messager.show({
										title : '提  示',
										msg : '新增隐患单成功！',
										showType : 'show',
										timeout:'1000'//ms
									   
									});
								}
								else{
									$.messager.alert("提示","新增隐患单失败！","info");
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
		
		
		function distributeUI() {
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
				var order_status = selected[0].ORDER_STATUS;
				if(order_status!='未派单'){
					$.messager.show({
						title : '提  示',
						msg : '该工单已经派过单!',
						showType : 'show',
						timeout:'1000'//ms
					   
					});
					return false;
				}
				
				
				var order_id = selected[0].ORDER_ID;
				var founder_uid = selected[0].FOUNDER_UID;
				var danger_question = selected[0].DANGER_QUESTION;
				
				
				$('#win').window({
					title : "【派单】",
					href : webPath + "dangerOrderController/distributeUI.do?order_id=" + order_id+"&founder_uid="+founder_uid+"&danger_question="+danger_question,
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

		function receiptUI() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取要回执的隐患单!',
					showType : 'show',
					timeout:'1000'//ms
				});
				return;
			} else {
				$.messager.confirm('系统提示', '您确定隐患已经处理并要回执隐患单吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].ORDER_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$.messager.progress();
						$.ajax({          async:false,
							  type:"post",
							  url : webPath + "dangerOrderController/receipt.do",
							  data:{ids:$("input[name='selected']").val()},
							  dataType:"json",
							  success:function(data){
								  $.messager.progress('close');
								  if(data.status){
										
										searchData();
										$.messager.show({
											title : '提  示',
											msg : '操作成功!',
											showType : 'show',
											timeout:'1000'//ms
										   
										});
									}else{
										alert("操作失败！");
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
				$.messager.confirm('系统提示', '您确定要更新隐患单吗?', function(r) {
					if (r) {
						var obj=makeParamJson('formEdit');
						$.ajax({
							type : 'POST',
							url : webPath + "dangerOrderController/dangerOrderUpdate.do",
							data : obj,
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '更新隐患单成功!',
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
		

		
		function dangerOrderDistribute(ids){
			var selected = $('#dg_distribute').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取处理人!',
					showType : 'show',
					timeout : '1000'//ms
				});
				return;
			} else {
				$.messager.confirm('系统提示','您确定要派发隐患单吗?',function(r) {
				if (r) {
					
					var handlePersonId=selected[0].STAFF_ID;
					
                    $.ajax({          async:false,
						  type:"post",
						  url :webPath + "dangerOrderController/dangerOrderDistribute.do",
						  data:{ids:ids,handlePersonId:handlePersonId},
						  dataType:"json",
						  success:function(data){
							if(data.status){
								$.messager.show({
									title : '提  示',
									msg : '派发成功!',
									showType : 'show',
									timeout : '1000'//ms
								});
								$("#win_distribute").window('close');
								
								searchData();
								
							}else{
								alert("分配失败！");
							}
						  }
					  });
					

				}
			});
			}
		}
		
		
		function auditUI() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取要审核的隐患单!',
					showType : 'show',
					timeout:'1000'//ms
				});
				return;
			} else {
				
				
				
				//var arr = new Array();
				//for ( var i = 0; i < selected.length; i++) {
					//var value = selected[i].ORDER_ID;
					//arr[arr.length] = value;
				//}
				//$("input[name='selected']").val(arr);
				var order_status = selected[0].ORDER_STATUS;
				if(order_status!='已处理'){
					$.messager.show({
						title : '提  示',
						msg : '该工单已经审核或者还未处理，不能审核!',
						showType : 'show',
						timeout:'2000'//ms
					   
					});
					return false;
				}
				var order_id = selected[0].ORDER_ID;
				
				$('#win_auditUI').window({
							title : "【审核】",
							href : webPath+ "dangerOrderController/auditUI.do?order_id="+order_id,
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
		
		
		function audit(){
			
				$.messager.confirm('系统提示','您确定要审核隐患单吗?',function(r) {
				if (r) {
					
					var obj=makeParamJson('formAudit');
					
                    $.ajax({          async:false,
						  type:"post",
						  url :webPath + "dangerOrderController/audit.do",
						  data:obj,
						  dataType:"json",
						  success:function(data){
							if(data.status){
								$.messager.show({
									title : '提  示',
									msg : '审核成功!',
									showType : 'show',
									timeout : '1000'//ms
								});
								$("#win_auditUI").window('close');
								
								searchData();
								
							}else{
								alert("审核失败！");
							}
						  }
					  });
					

				}
			});
			
		}
		
		
		function detailUI(order_id){
			$('#win_detailUI').window({
				title : "【详情】",
				href : webPath+ "dangerOrderController/detailUI.do?order_id="+order_id,
				width : 800,
				height : 450,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}

		
		function getPhotoUI(site_id,type){
			addTab("隐患图片",webPath+ "dangerOrderController/getPhoto.do?site_id="+site_id+"&photo_type="+type);
		}
		
		function paidan(){
			$.messager.confirm('系统提示','您确定要派单吗?',function(r) {
				if (r) {
					
					var handle_person=$("#p_handle_person").val();
					var distribute_remark=$("#distribute_remark").val();
					var limit_time=$("#limit_time").val();
					var order_id=$("#order_id").val();
					
                    $.ajax({          async:false,
						  type:"post",
						  url :webPath + "dangerOrderController/paidan.do",
						  data:{handle_person:handle_person,distribute_remark:distribute_remark,limit_time:limit_time,order_id:order_id},
						  dataType:"json",
						  success:function(data){
							if(data.status){
								$.messager.show({
									title : '提  示',
									msg : '派单成功!',
									showType : 'show',
									timeout : '1000'//ms
								});
								$("#win").window('close');
								
								searchData();
								
							}else{
								alert("派单失败！");
							}
						  }
					  });
					

				}
			});
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
				$.messager.confirm('系统提示', '您确定要删除隐患单吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].ORDER_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$.messager.progress();
						$.ajax({          async:false,
							  type:"post",
							  url : webPath + "dangerOrderController/deleteDanger.do",
							  data:{ids:$("input[name='selected']").val()},
							  dataType:"json",
							  success:function(data){
								  $.messager.progress('close');
								  if(data.status){
										
										searchData();
										$.messager.show({
											title : '提  示',
											msg : '成功删除隐患单!',
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
			o.find("input,select").each(function() {
				var o = $(this);
				var tag = this.tagName.toLowerCase();
				var name = o.attr("name");
				if (name) {
					if (tag == "select") {
						if(o.find("option:selected").val()=='all' || o.find("option:selected").val()=='' ){
							res=res+"&"+name+"=";
						}else{
							res=res+"&"+name+"="+o.find("option:selected").val();
						}
						
					} else if (tag == "input") {
						res=res+"&"+name+"="+ o.val();
					}
				}
			});
			return res;
		}
		
		
		
		function dataDownload(){
			
			window.open(encodeURI(webPath + "dangerOrderController/dataDownload.do"+getParamsForDownloadLocal('form')));
		}
	</script>

</body>
</html>
