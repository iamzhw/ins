<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/jsp/util/head.jsp"%>
		<title>工单审核</title>
	</head>
  
	<body>
		<div id="win_port"></div>
		<div id ="details">
		<input type="hidden" value="${TASK_ID}" name="TASK_ID" id="TASK_ID" />
	  	<c:forEach items="${taskList }" var="task">
			<div>
				<table style="width:100%;">
					<tr>
						<td style="width: 8%; height: 30px; font-weight: bold;">任务编码：</td>
						<td style="text-align: left; width: 30%; color: gray;">${task.TASK_NO}</td>
						<td style="width: 8%; height: 30px; font-weight: bold;">任务名称：</td>
						<td style="text-align: left; width: 30%; color: gray;">${task.TASK_NAME }</td>
					</tr>
					
					<tr>
						<td style="width: 8%; height: 30px; font-weight: bold;">任务类型：</td>
						<td style="text-align: left; width: 30%; color: gray;">${task.TASK_TYPE}</td>
						<td style="width: 8%; height: 30px; font-weight: bold;">任务状态：</td>
						<td style="text-align: left; width: 30%; color: gray;">${task.STATUS}</td>
					</tr>
					<tr>
						<td style="width: 8%; height: 30px; font-weight: bold;">创建时间：</td>
						<td style="text-align: left; width: 30%; color: gray;">${task.CREATE_TIME}</td>
						<td style="width: 8%; height: 30px; font-weight: bold;">开始时间：</td>
						<td style="text-align: left; width: 30%; color: gray;">${task.START_TIME}</td>
					</tr>
					<tr>
						<td style="width: 8%; height: 30px; font-weight: bold;">最后一次更新时间：</td>
						<td style="text-align: left; width: 30%; color: gray;">${task.LAST_UPDATE_TIME}</td>
						<td style="width: 8%; height: 30px; font-weight: bold;">结束时间：</td>
						<td style="text-align: left; width: 30%; color: gray;">${task.COMPLETE_TIME }</td>
					</tr>
					<tr>
						<td style="width: 8%; height: 30px; font-weight: bold;">地市：</td>
						<td style="text-align: left; width: 30%; color: gray;">${task.AREA_NAME}</td>
						<td style="width: 8%; height: 30px; font-weight: bold;">区域：</td>
						<td style="text-align: left; width: 30%; color: gray;">${task.SON_AREA_NAME }</td>
					</tr>
					<tr>
						<td style="width: 8%; height: 30px; font-weight: bold;">检查员：</td>
						<td style="text-align: left; width: 30%; color: gray;">${task.STAFF_NAME}</td>
					</tr>
				</table>
			</div>
			
		 	<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#CCCCCC">
				<tr height="80px" bgcolor="#FFFFFF">
					<td align="right" class="form_01b" style="text-align: center; font-size: 16px;"><strong>审核意见</strong>&nbsp;&nbsp;</td>
					<td colspan="4" align="left" class="list_pd1">
						<textarea rows="10" cols="130" id="shyj">
							
						</textarea>
					</td>
				</tr>
			</table>
			<div class="btn-operation-container">
				<div style="float: right;" class="btn-operation" onClick="doTask('8')">
					通过
				</div>
				<div style="float: right;" class="btn-operation" onClick="doTask('6')">
					不通过
				</div>
			</div>
		</c:forEach>
	</div>
	<div id ="details">
		<h3>设备信息</h3>
	  	<c:forEach items="${taskEqpPhotoList }" var="eqp">
			<div>
				<table id="${eqp.task_id}" style="width:100%;">
					<tr>
						<td style="width: 5%; height: 30px;">设备编码：</td>
						<td style="text-align: left; width: 30%;">${eqp.eqp_no }</td>
						<td style="width: 5%; height: 30px;">设备名称：</td>
						<td style="text-align: left; width: 30%;">${eqp.eqp_name }</td>
					</tr>
					
					<tr>
						<td style="width: 5%; height: 30px;">设备地址：</td>
						<td style="text-align: left; width: 30%;">${eqp.eqpaddress }</td>
						<td style="width: 5%; height: 30px;">所属区域：</td>
						<td style="text-align: left; width: 30%;">${eqp.son_area_name }</td>
					</tr>
					<tr>
						<td style="width: 5%; height: 30px;">回单备注：</td>
						<td style="text-align: left; width: 30%;">${eqp.info }</td>
					</tr>
				</table>
			</div>
			
		 	<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#CCCCCC">
				<tr height="150px" bgcolor="#FFFFFF">
					<td align="right" class="form_01b" style="text-align: center;"><strong>整改前</strong>&nbsp;&nbsp;</td>
					<td colspan="4" align="left" class="list_pd1">
						<c:forEach items="${eqp.photoList }" var="photo">
						  <c:if test="${photo.record_type==1 or photo.record_type==3 or photo.record_type==0}">
							 <a href="${photo.photo_path }" title="照片预览" class="nyroModal">
								<img src="${photo.micro_photo_path }" style="width: 100px;"/>
							 </a>
						  </c:if>  
						</c:forEach>
					</td>
				</tr>
				<tr height="150px" bgcolor="#FFFFFF">
					<td align="right" class="form_01b" style="text-align: center;"><strong>整改后</strong>&nbsp;&nbsp;</td>
					<td colspan="4" align="left" class="list_pd1">
						<c:forEach items="${eqp.photoList }" var="photo2">
							 <c:if test="${photo2.record_type==2}">
								<a href="${photo2.photo_path }" title="照片预览" class="nyroModal">
									<img src="${photo2.micro_photo_path }" style="width: 100px;"/>
								</a> 
							</c:if> 
						</c:forEach>
					</td>
				</tr>
			</table>
		</c:forEach>
		<div id ="port">
		<h3>端子信息</h3>
			<c:if test="${TASK_TYPE=='0'}">
				<div>
					<table style="width:100%; text-align: center;" border="0" bgcolor="#CCCCCC" cellspacing="1" cellpadding="3">
						<tr bgcolor="#FFFFFF">
							<td style="width:3%; height: 30px;"><strong>序号</strong></td>
							<td style="width:8%; height: 30px;"><strong>端子编码</strong></td>
							<td style="width:8%; height: 30px;"><strong>光路编码</strong></td>
							<td style="width:8%; height: 30px;"><strong>光路名称</strong></td>
							
						</tr>
						<c:if test="${not empty portList}">
							<c:forEach items="${portList }" var="port" varStatus="porStatus">
								<tr bgcolor="#FFFFFF">
									<td style="width: 3%;">
										${porStatus.index + 1}
									</td>
									<td style="width: 10%;">
										<input name="port_id" type=hidden class="" value="${port.DZID}"/>
										${port.DZBM}
									</td>
									<td style="width: 15%;">
										${port.GLBH}
									</td>
									<td style="width: 30%;">
										${port.GLMC}
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty portList}">
							<tr bgcolor="#FFFFFF">
								<td colspan="4">
									无端子信息！
								</td>
							</tr>
						</c:if>
					</table>
				</div>
			</c:if>
			<c:if test="${TASK_TYPE != '0'}">
				<table class="easyui-datagrid" data-options="remoteSort: false" >
			<thead>
				<tr>
					<th data-options="field:'rownum'"><strong>序号</strong></th>
					<th data-options="field:'port_no',sortable:true"><strong>端子编码</strong></th>
					<th data-options="field:'recordtype'" ><strong>记录类型</strong></th>
					<th data-options="field:'if_check'" ><strong>是否检查</strong></th>
					<th data-options="field:'ischeckok'" ><strong>是否合格</strong></th>
					<th data-options="field:'descript'" ><strong>检查描述</strong></th>
					<th data-options="field:'port_info'" ><strong>整改描述</strong></th>
					<th data-options="field:'photo'"><strong>检查照片</strong></th>
					<th data-options="field:'photozg'"><strong>整改照片</strong></th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty portList}">
					<c:forEach items="${portList }" var="port" varStatus="porStatus">
						<tr>
							<td>${porStatus.index + 1}</td>
							<td><input name="port_id" type=hidden class="" value="${port.DZID}"/>${port.PORT_NO}</td>
							<td>${port.RECORDTYPE}</td>
							<c:choose>
								<c:when test="${empty port.portCheckedList }">
									<td>否</td>
								</c:when>
								<c:otherwise>
									<td>是</td>
								</c:otherwise>
							</c:choose>
							<td>${port.ISCHECKOK}</td>
							<td>${port.DESCRIPT}</td>
						    <td>
							<c:forEach items="${port.portCheckedList }" var="portChecked">
							${portChecked.PORT_INFO}
							</c:forEach>
							</td>
							<td>
							<%-- <c:forEach items="${port.portCheckedList }" var="portChecked">
							        
									<c:if test="${portChecked.PHOTO_PATH != null }">
										<a href="${portChecked.PHOTO_PATH }" title="照片预览" class="nyroModal">
											<img src="${portChecked.MICRO_PHOTO_PATH }" style="width: 50px;"/>
						 				</a>
									</c:if>
									<c:if test="${portChecked.PHOTO_PATH == null }">
										暂无照片
									</c:if>
								
							</c:forEach> --%>
								<c:forEach items="${port.portCheckedList }" var="portChecked">
									<!-- 检查照片 -->
										<c:if test="${portChecked.PHOTO_PATH != null }">
											<c:if test="${portChecked.RECORD_TYPE != 2 }">
												<a href="${portChecked.PHOTO_PATH }" title="照片预览" class="nyroModal">
													<img src="${portChecked.MICRO_PHOTO_PATH }" style="width: 50px;"/>
								 				</a>
								 			</c:if>
										</c:if>									
								</c:forEach>
							</td>
							<td>
								<c:forEach items="${port.portCheckedList }" var="portChecked">
								   <!--        整改照片 -->     
										<c:if test="${portChecked.PHOTO_PATH != null }">
											<c:if test="${portChecked.RECORD_TYPE == 2 }">
												<a href="${portChecked.PHOTO_PATH }" title="照片预览" class="nyroModal">
													<img src="${portChecked.MICRO_PHOTO_PATH }" style="width: 50px;"/>
								 				</a>
								 			</c:if>
										</c:if>								
								</c:forEach>
							</td>
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
			</c:if>
	</div>
	<div>
		<h3>流程详情</h3>
			<div>
				<table style="width:100%; text-align: center;" border="0" bgcolor="#CCCCCC" cellspacing="1" cellpadding="3">
					<tr bgcolor="#FFFFFF">
						<td style="width:3%; height: 30px;"><strong>序号</strong></td>
						<td style="width:8%; height: 30px;"><strong>操作时间</strong></td>
						<td style="width:8%; height: 30px;"><strong>操作人</strong></td>
						<td style="width:8%; height: 30px;"><strong>操作</strong></td>
						
					</tr>
					<c:if test="${not empty processList}">
						<c:forEach items="${processList }" var="process" varStatus="proStatus">
							<tr bgcolor="#FFFFFF">
								<td style="width: 3%;">
									${proStatus.index + 1}
								</td>
								<td style="width: 10%;">
									<input name="port_id" type=hidden class=""/>
									${process.OPER_TIME}
								</td>
								<td style="width: 15%;">
									${process.STAFF_NAME}
								</td>
								<td style="width: 30%;">
									${process.REMARK}
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty processList}">
						<tr bgcolor="#FFFFFF">
							<td colspan="4">
								无工单流程信息！
							</td>
						</tr>
					</c:if>
				</table>
			</div>
	</div>
	</div>
	<script type="text/javascript">
	
		$(function(){
			$('.nyroModal').nyroModal();
		});
		function doTask(status_id){
			var shyj = $("#shyj").val();
			var taskId = $("#TASK_ID").val();
			$.ajax({
				type : 'POST',
				url : webPath + "CableCheck/taskNewAudit.do?status_id=" + status_id + "&taskId=" + taskId+ "&shyj=" + shyj,
				dataType : 'json',
				success : function(json) {
					if(json.status){
					    $.messager.alert('页面消息','操作成功！','info',function(){
					       closeddd('工单审核');
					    });

					    
						/*$.messager.show({
							title : '提  示',
							msg : '操作成功!',
							showType : 'show'
						});*/
					  
					} else {
						$.messager.show({
							title : '提  示',
							msg : json.message,
							showType : 'show'
						});
					}
				}
			});
				//closeddd('工单审核');
		}
		function closeddd(subtitle){
			parent.$('#tabs').tabs('close',subtitle);
		}
	</script>
	</body>
</html>
