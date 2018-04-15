<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>工单详情</title>
</head>
<body>
<div style="padding:20px 0 10px 50px">
		<table>
			<tr>
				<c:forEach items="${fileList}" var="v">
					<td height="20" align="left">
						<a href="${v.FILE_PATH}">文件下载</a>
					</td>
				</c:forEach>
			</tr>
			<tr>
				<td colspan="1">
					整改前照片：
				</td>

				<td colspan="3">

					<div style="height: 145px; width: 100%; overflow-y: scroll; border: 1px solid #aaa;">
						<% int i = 0; %>
						<c:forEach items="${photoList}" var="v">
							<% 
								if(i%6==0) out.print("<div>");
							%>

							<div style="margin: 0px 5px 5px 0; float: left; border: 1px solid #bbb; padding: 2px;">
									
								<img onclick="openImg('${v.FILE_PATH}' )" src="${v.MICRO_PHOTO_PATH}"
									style="cursor: pointer;" height="120" border="0" />
								<div style="font-size: 12px; font-family: '宋体'">
									${v.PHOTO_NAME}
								</div>
							</div>
							<%
								i++;
								if(i%6==0){
									out.print("<div style=\"clear: both\"></div></div>");
								}
							%>
						</c:forEach>
					</div>
					
				</td>
			</tr>
			<tr>
				<td colspan="1">
					整改后照片：
				</td>

				<td colspan="3">

					<div style="height: 145px; width: 100%; overflow-y: scroll; border: 1px solid #aaa;">
						<% int j = 0; %>
						<c:forEach items="${photoListNew}" var="v">
							<% 
								if(j%6==0) out.print("<div>");
							%>

							<div style="margin: 0px 5px 5px 0; float: left; border: 1px solid #bbb; padding: 2px;">
									
								<img onclick="openImg('${v.FILE_PATH}' )" src="${v.MICRO_PHOTO_PATH}"
									style="cursor: pointer;" height="120" border="0" />
								<div style="font-size: 12px; font-family: '宋体'">
									${v.PHOTO_NAME}
								</div>
							</div>
							<%
								j++;
								if(j%6==0){
									out.print("<div style=\"clear: both\"></div></div>");
								}
							%>
						</c:forEach>
					</div>
					
				</td>
			</tr>
		</table>
	<div style="text-align:left;padding:10px 0 10px 90px">
		<div class="btn-operation" onClick="cancelEdit()">关闭</div>
	</div>
</div>

<script type="text/javascript">
	function openImg(url){
		window.open (url, "newwindow", "height=340, width=500, toolbar=no, top=200, left=400, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
	}
	

	function cancelEdit(){
		$('#win_trouble').window('close');
	}
</script>
</body>
</html>
