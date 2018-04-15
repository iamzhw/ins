
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>整治单管理</title>
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
							</div>
						</td>
						<td width="10%">结束日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="end_time" id="end_time" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'start_time\')}',maxDate: '%y-%M-%{%d-1}' })" type="text" class="condition-text" />
							</div>
						</td>
						<td width="5%">整治单状态：</td>
						<td width="10%">
							 <div>
								<select name="order_status" id="order_status" class="condition-select">
									<option value=''>--请选择--</option>
									<option value='1'>已派单</option>
									<option value='2'>已处理</option>
									<option value='3'>已审核</option>
								</select>
							</div>
					    </td>
					</tr>
					<tr>
						<td width="5%">整治内容描述：</td>
						<td width="10%">
							<div class="condition-text-container">
								<input name="fixordercode" id="fixordercode" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">处理人：</td>
						<td width="20%">
							<div>
							    <input name="dealpeople" id="dealpeople" type="text" class="condition-text" />
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
						</td>
					 </tr>		
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="auditUI()">审核</div>
			<div style="float:right;" class="btn-operation"	onClick="clearCondition('form')">重置</div>
			<div style="float: right;" class="btn-operation" onClick="searchData()">查询</div>
		</div>
	</div>
	<div id="win"></div>
	<div id="win_auditUI"></div>
	<div id="win_detailUI"></div>
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
				url : webPath + "FixOrder/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 10, 20, 50],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : true,
				
				//
				columns : [ [ {
					field : 'FIXORDER_ID',
					title : '整治单ID',
					checkbox : true
				},{
					field : 'FIXORDER_CODE',
					title : '整治单描述',
					width : "40%",
					align : 'center'
				}, {
					field : 'CREATOR_NAME',
					title : '整治单发起人',
					width : "10%",
					align : 'center'
				},{
					field : 'CREATE_TIME',
					title : '发起时间',
					width : "10%",
					align : 'center'
				},{
					field : 'STATUS_NAME',
					title : '整治单状态',
					width : "10%",
					align : 'center'
				}, {
					field : 'SITE_NAME',
					title : '外力点名称',
					width : "10%",
					align : 'center'
				},{
					field : 'DEALING_PEOPLE_NAME',
					title : '处理人',
					width : "10%",
					align : 'center'
				},{
					field : 'OP',
					title : '操作',
					width : "10%",
					align : 'center',
					formatter : function(value, rec,index) {
						return "&nbsp;&nbsp;<a  style='color:blue' onclick='detailUI(\""
						+ rec.FIXORDER_ID
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
		
		
		function reject(is_pass){
			$.messager.confirm('系统提示','您确定要驳回该整治单吗?',function(r) {
				if (r) {
					var obj=makeParamJson('formAudit');
					obj["is_pass"] = is_pass;
                    $.ajax({          
                    	  async:false,
						  type:"post",
						  url :webPath + "FixOrder/audit.do",
						  data:obj,
						  dataType:"json",
						  success:function(data){
							if(data.status){
								$.messager.show({
									title : '提  示',
									msg : '驳回成功!',
									showType : 'show',
									timeout : '1000'//ms
								});
								$("#win_auditUI").window('close');
								
								searchData();
								
							}else{
								alert("驳回失败！");
							}
						  }
					  });
				}
			});
		}
		
		
		function auditUI() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取要审核的整治单!',
					showType : 'show',
					timeout:'1000'//ms
				});
				return;
			} else {
				var order_status = selected[0].STATUS_NAME;
				if(order_status!='已处理'){
					$.messager.show({
						title : '提  示',
						msg : '该工单已经审核或者还未处理，不能审核!',
						showType : 'show',
						timeout:'2000'//ms
					});
					return false;
				}
				var fixorder_id = selected[0].FIXORDER_ID;
				$('#win_auditUI').window({
							title : "【审核】",
							href : webPath+ "FixOrder/auditUI.do?fixorder_id="+fixorder_id,
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
		
		function audit(is_pass){
				$.messager.confirm('系统提示','您确定要审核该整治单吗?',function(r) {
				if (r) {
					var obj=makeParamJson('formAudit');
					obj["is_pass"] = is_pass;
                    $.ajax({          
                    	  async:false,
						  type:"post",
						  url :webPath + "FixOrder/audit.do",
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
		
		function detailUI(fixorder_id){
			$('#win_detailUI').window({
				title : "【详情】",
				href : webPath+ "FixOrder/detailUI.do?fixorder_id="+fixorder_id,
				width : 800,
				height : 450,
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
