<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/jsp/util/head.jsp"%>
		<title>端子信息</title>
	</head>
  
		<body>
		<div style="overflow:auto; width: 100%;height: 100%;">
		<div id="win_port"></div>
		<div id ="port">
	  	<c:forEach items="${portList }" var="port">
			<div>
				<table id="${port.EQP_ID}" style="width:100%;">
					<tr>
						<td style="width: 15%; height: 30px;">端子编码：</td>
						<td style="text-align: center; width: 30%;">
							<input name="port_id" type=hidden class="" value="${port.PORT_ID}"/>
							${port.PORT_NO}
						</td>
						<td style="width:15%; height: 30px;">现场规范：</td>
						<td style="text-align: left; width: 30%;">
							${port.REMARK}
						</td>
					</tr>
				</table>
			</div>
			<div style="border-bottom: 1px solid #d2d2d2;">
				<c:forEach items="${port.photos }" var="ph">
					<a href="${ph.PHOTO_PATH }" title="照片预览" class="nyroModal">
						<img src="${ph.PHOTO_PATH }" style="width: 50px;"/></a>  
				</c:forEach>
			</div>
		</c:forEach>
	</div>
     <script type="text/javascript">

  	</script>
  </body>
</html>
