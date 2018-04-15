<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>上传照片</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="upload_photo" method="post" enctype="multipart/form-data">
			<input type="hidden" id="p_orderId" value="${orderId }" />
			<input type="hidden" id="p_equipmentId" value="${equipmentId }" />
			<input type="hidden" id="p_targetId" value="${targetId }" />
			<table>
				<tr>
					<td>上传照片1：</td>
					<td>
						<div >
							<input 
								type="file" id="photo1" name="photo1"
								 />
						</div></td>
				</tr>
				<tr>
					<td>上传照片2：</td>
					<td>
						<div >
							<input 
								type="file"  id="photo2"  name="photo2"/>
						</div></td>
				</tr>
				<tr>
					<td>上传照片3：</td>
					<td>
						<div >
							<input 
								type="file" id="photo3"  name="photo3"/>
						</div></td>
				</tr>
				<tr>
					<td>上传照片4：</td>
					<td>
						<div >
							<input 
								type="file" id="photo4"  name="photo4"/>
						</div></td>
				</tr>
				
			</table>
		</form>
		<div style="text-align:left;padding-left: 20px;padding-top: 10px;">
			<div class="btn-operation"  onClick="savePhoto();">确定</div>
			<div class="btn-operation" onClick="closeWindow();">关闭</div>
		</div>
	</div>
</body>

</html>