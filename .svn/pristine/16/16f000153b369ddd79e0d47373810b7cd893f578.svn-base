<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/jsp/util/head.jsp"%>
		<title>任务详情</title>
	</head>
	<body>
		<h3>设备信息</h3>
		<table class="easyui-datagrid" style="width:1300px;height:100px" data-options="remoteSort: false">
			<thead>
				<tr>
					<th data-options="field:'code',sortable:true">设备编码</th>
					<th data-options="field:'name',sortable:true">设备名称</th>
					<th data-options="field:'address'">设备地址</th>
					<th data-options="field:'area'">所属区域</th>
					<th data-options="field:'remark'">现场规范</th>
					<th data-options="field:'info'">回单备注</th>
					<th data-options="field:'before_photo'">整改前照片</th>
		            <th data-options="field:'after_photo'">整改后照片</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${taskEqpPhotoList }" var="eqp">
					<tr>
						<td>${eqp.eqp_no }</td>
						<td>${eqp.eqp_name }</td>
						<td>${eqp.eqpaddress }</td>
						<td>${eqp.son_area_name }</td>
						<td>${eqp.REMARK }</td>
						<td>${eqp.info }</td>
						<td>
	            			<c:forEach items="${eqp.photoList }" var="photo">
								<c:if test="${photo.record_type==1 or  photo.record_type==3 or  photo.record_type==0}">
									 <a href="${photo.photo_path }" title="照片预览" class="nyroModal">
										<img src="${photo.micro_photo_path }" style="width: 50px;"/>
									 </a>
								</c:if>  
							</c:forEach>
	            		</td>
	            		<td>
	            			<c:forEach items="${eqp.photoList }" var="photo2">
								<c:if test="${photo2.record_type==2}">
									<a href="${photo2.photo_path }" title="照片预览" class="nyroModal">
										<img src="${photo2.micro_photo_path }" style="width: 50px;"/>
									</a> 
								</c:if> 
							</c:forEach>
	            		</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<h3>端子信息</h3>
		<table class="easyui-datagrid" data-options="remoteSort: false">
			<thead>
				<tr>
					<th data-options="field:'rownum'"><strong>序号</strong></th>
					<th data-options="field:'PORT_NO',sortable:true"><strong>端子编码</strong></th>
					<th data-options="field:'EQP_NO',sortable:true"><strong>设备编码</strong></th>
					<th data-options="field:'EQP_NAME',sortable:true"><strong>设备名称</strong></th>
					<th data-options="field:'GLBM',sortable:true" ><strong>光路编码</strong></th>
					<th data-options="field:'ORDERNO'" ><strong>工单号</strong></th>
					<th data-options="field:'MARK'" ><strong>工单来源</strong></th>
					<th data-options="field:'ACTION_TYPE'" ><strong>工单性质</strong></th>
					<th data-options="field:'ARCHIVE_TIME'" ><strong>工单竣工时间</strong></th>
										
					<th data-options="field:'CHECK_STAFF_NAME'" ><strong>检查人</strong></th>
					<th data-options="field:'CHECK_ISCHECKOK'" ><strong>检查反馈</strong></th>
					<th data-options="field:'CHECK_DESCRIPT'" ><strong>检查描述</strong></th>		
					<th data-options="field:'CHECK_TIME',nowarp:false,autoSize:true" width="79px"><strong>检查时间</strong></th>
					<th data-options="field:'CHECK_MICRO_PHOTO_PATH'"><strong>检查照片</strong></th>
					
					<th data-options="field:'ZG_STAFF_NAME'" ><strong>整改人</strong></th>
					<th data-options="field:'ZG_ISCHECKOK'" ><strong>整改反馈</strong></th>
					<th data-options="field:'ZG_PORT_INFO'" ><strong>整改描述</strong></th>		
					<th data-options="field:'ZG_TIME',nowarp:false,autoSize:true" width="79px"><strong>整改时间</strong></th>
					<th data-options="field:'ZG_MICRO_PHOTO_PATH'"><strong>整改照片</strong></th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty portList}">
					<c:forEach items="${portList }" var="port" varStatus="porStatus">
						<tr>
							<td>${porStatus.index + 1}</td>
							<td>${port.PORT_NO}</td>
							<td>${port.EQP_NO}</td>
							<td>${port.EQP_NAME}</td>
							<td>${port.GLBM}</td>
                            <td>${port.ORDERNO}</td>
							<td>${port.MARK}</td>
                            <td>${port.ACTION_TYPE}</td>
							<td>${port.ARCHIVE_TIME}</td>
							
							<td>${port.CHECK_STAFF_NAME}</td>
							<td>${port.CHECK_ISCHECKOK}</td>
							<td>${port.CHECK_DESCRIPT}</td>
							<td>${port.CHECK_TIME}</td>
							<td>
								<c:if test="${not empty port.orderCheckedList }">
										<c:forEach items="${port.orderCheckedList}" var="checkedPhoto">
											<c:if test="${checkedPhoto.PHOTO_PATH != null }">
												<a href="${checkedPhoto.PHOTO_PATH }" title="照片预览" class="nyroModal">
													<img src="${checkedPhoto.MICRO_PHOTO_PATH }" style="width: 50px;"/>
												</a> 
											</c:if>
										</c:forEach>
								</c:if>
								<c:if test="${ empty port.orderCheckedList}">
										无检查照片
								</c:if>
							</td>
							
							<c:if test="${not empty port.orderZgList }">
								<c:forEach items="${port.orderZgList}" var="zgPhoto">
									<td>${zgPhoto.ZG_STAFF_NAME }</td>
									<td>${zgPhoto.ZG_ISCHECKOK }</td>
									<td>${zgPhoto.ZG_PORT_INFO }</td>
									<td>${zgPhoto.ZG_TIME }</td>
									<td>
										<c:if test="${not empty zgPhoto.orderZgPhotoList }">
											<c:forEach items="${zgPhoto.orderZgPhotoList}" var="orderZgPhoto">
												<c:if test="${orderZgPhoto.PHOTO_PATH != null }">
													<a href="${orderZgPhoto.PHOTO_PATH }" title="照片预览" class="nyroModal">
														<img src="${orderZgPhoto.MICRO_PHOTO_PATH }" style="width: 50px;"/>
													</a> 
												</c:if>
											</c:forEach>
										</c:if>
										<c:if test="${ empty port.orderCheckedList}">
												无整改照片
										</c:if>
									</td>
									 
								</c:forEach>
							</c:if>
							<c:if test="${ empty port.orderZgList}">
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</c:if>
							
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty portList}">
					<tr>
						<td colspan="6">无端子信息！</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<h3>流程详情</h3>
		<table class="easyui-datagrid">
			<thead>
				<tr>
					<th data-options="field:'rownum'">序号</th>
					<th data-options="field:'oper_time'">操作时间</th>
					<th data-options="field:'oper_staff'">操作人</th>
					<th data-options="field:'oper_remark'">流程环节</th>
					<th data-options="field:'oper_content'">操作详情</th>
					<th data-options="field:'oper_receiver'">接单人</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty processList}">
					<c:forEach items="${processList }" var="process" varStatus="proStatus">
						<tr>
							<td style="width: 3%;">${proStatus.index + 1}</td>
							<td style="width: 10%;">
								<input name="port_id" type=hidden class=""/>${process.OPER_TIME}
							</td>
							<td style="width: 15%;">${process.STAFF_NAME}</td>
							<td style="width: 30%;">${process.REMARK}</td>
							<td style="width: 30%;">${process.CONTENT}</td>
							<td style="width: 30%;">${process.RECEIVER}</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty processList}">
					<tr>
						<td colspan="4">
							无工单流程信息！
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
<div id="intoAudit"  style="width:100%;height:200px;"></div>
		<script type="text/javascript">
			$(function(){
				$('.nyroModal').nyroModal();
			});
		</script>
	</body>
	<script>
 	$(document).ready(function() {
	   var taskId = '${taskEqpPhotoList[0].task_id}';
	 
	   var url = "<%=path%>/CableCheck/getTaskByTaskId.do";
	   $.post(url,{"taskId":taskId},function(data){
	       
  	        if(data.STATUS_ID==7){
  	    var table='';
  	      table+=' <table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#CCCCCC">';
				table+='<tr height="80px" bgcolor="#FFFFFF">';
					table+='<td align="right" class="form_01b" style="text-align: center; font-size: 16px;"><strong>审核意见</strong>&nbsp;&nbsp;</td>';
					table+='<td colspan="4" align="left" class="list_pd1">';
						table+='<textarea rows="10" cols="130" id="shyj">';
							
						table+='</textarea>';
					table+='</td>';
				table+='</tr>';
			table+='</table>';
  	       
	         
	         table+='<div class="btn-operation-container">';
				table+='<div style="float: right;" class="btn-operation" onClick="doTask(\'8\')">';
					table+='通过';
				table+='</div>';
				table+='<div style="float: right;" class="btn-operation" onClick="doTask(\'6\')">';
				table+='	不通过';
				table+='</div>';
			table+='</div>';
	            $("#intoAudit").html(table);
	            
	      }else{
	      
	      }
	   },"json");
	});
	function doTask(status_id){
			var shyj = $("#shyj").val();
			var taskId = '${taskEqpPhotoList[0].task_id}';
			$.ajax({
				type : 'POST',
				url : webPath + "CableCheck/taskNewAudit.do?status_id=" + status_id + "&taskId=" + taskId+ "&shyj=" + shyj,
				dataType : 'json',
				success : function(json) {
					if(json.status){
						$.messager.alert('页面消息','操作成功！','info',function(){
					       closeddd('任务详情');
					    });
						
					} else {
						$.messager.show({
							title : '提  示',
							msg : json.message,
							showType : 'show'
						});
					}
				}
			});
		}
		function closeddd(subtitle){
			parent.$('#tabs').tabs('close',subtitle);
		}
 
	</script>
</html>