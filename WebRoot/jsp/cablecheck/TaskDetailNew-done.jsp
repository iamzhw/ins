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
		            <th data-options="field:'name'">设备名称</th>   
		            <th data-options="field:'address'">设备地址</th>
		            <th data-options="field:'area'">所属区域</th>
		            <th data-options="field:'remark'">现场规范</th>
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
	            		<td>
	            			<c:forEach items="${eqp.photoList }" var="photo">
		            			<c:if test="${photo.record_type==0 or photo.record_type==4 }">
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
		            <th data-options="field:'rownum'" ><strong>序号</strong></th>
					<th data-options="field:'port_no',sortable:true" ><strong>端子编码</strong></th>
					<th data-options="field:'eqp_no',sortable:true" ><strong>设备编码</strong></th>
					<th data-options="field:'eqp_name',sortable:true" ><strong>设备名称</strong></th>
					<th data-options="field:'glbm',sortable:true" ><strong>光路编码</strong></th>
					<th data-options="field:'orderno'" ><strong>工单号</strong></th>
					<th data-options="field:'action_type'" ><strong>工单性质</strong></th>
					<th data-options="field:'archive_time'" ><strong>工单竣工时间</strong></th>
					<th data-options="field:'staff_name'" ><strong>检查人</strong></th>
					<th data-options="field:'create_time',nowarp:false,autoSize:true" width="79px"><strong>检查时间</strong></th>
					<th data-options="field:'ischeckok'" ><strong>检查反馈</strong></th>
					<th data-options="field:'descript'" ><strong>检查描述</strong></th>
					<th data-options="field:'micro_photo_path'"><strong>检查照片</strong></th>
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
                            <td>${port.ACTION_TYPE}</td>
                            <td>${port.ARCHIVE_TIME}</td>
                            <td>${port.CHECK_STAFF_NAME}</td>
							<td>${port.CHECK_TIME}</td>
							<td>${port.CHECK_ISCHECKOK}</td>
							<td>${port.CHECK_DESCRIPT}</td>
							<td>
								<c:if test="${not empty port.taskOrderCheckedList }">
										<c:forEach items="${port.taskOrderCheckedList}" var="checkedOrderPhoto">
												<a href="${checkedOrderPhoto.PHOTO_PATH }" title="照片预览" class="nyroModal">
													<img src="${checkedOrderPhoto.MICRO_PHOTO_PATH }" style="width: 50px;"/>
												</a> 
										</c:forEach>
								</c:if>
								<c:if test="${ empty port.taskOrderCheckedList}">
										无检查照片
								</c:if>
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
		<h3>流程详情</h3>
		<table class="easyui-datagrid" >
			<thead>
				<tr>
					<th data-options="field:'rownum'" >序号</th>
					<th data-options="field:'oper_time'" >操作时间</th>
					<th data-options="field:'oper_staff'" >操作人</th>
					<th data-options="field:'oper_remark'" >流程环节</th>
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
						<td>无工单流程信息！</td>
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
</html>
