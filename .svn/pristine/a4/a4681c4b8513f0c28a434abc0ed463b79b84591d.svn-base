<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/jsp/util/head.jsp"%>
		<title>任务详情</title>
	</head>
  
	<body>
		<div id="win_port"></div>
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
						<td style="width: 5%; height: 30px;">现场规范：</td>
						<td style="text-align: left; width: 30%;">${eqp.REMARK }</td>
					</tr>
				</table>
			</div>
			
		 	<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#CCCCCC">
				<tr height="150px" bgcolor="#FFFFFF">
					<td align="right" class="form_01b" style="text-align: center;"><strong>整改前</strong>&nbsp;&nbsp;</td>
					<td colspan="4" align="left" class="list_pd1">
						<c:forEach items="${eqp.photoList }" var="photo">
						  <c:if test="${photo.record_type==1 or photo.record_type==4}">
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
							 <c:if test="${photo.record_type==2}">
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
			<div>
				<table style="width:100%; text-align: center;" border="0" bgcolor="#CCCCCC" cellspacing="1" cellpadding="3">
					<tr bgcolor="#FFFFFF">
						<td style="width:3%; height: 30px;"><strong>序号</strong></td>
						<td style="width:8%; height: 30px;"><strong>端子编码</strong></td>
						<td style="width:8%; height: 30px;"><strong>设备编码</strong></td>
						<td style="width:8%; height: 30px;"><strong>设备名称</strong></td>
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
								<td style="width: 10%;">
									${port.SBBM}
								</td>
								<td style="width: 10%;">
									${port.SBMC}
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
	</body>
</html>
