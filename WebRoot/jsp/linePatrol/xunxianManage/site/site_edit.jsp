<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../../util/head.jsp"%>

<title>巡线点编辑</title>

<script type="text/javascript">
 
</script>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formEdit" method="post">
            <input type="hidden" id="site_id" name="site_id" value="${SITE_ID}">
            <input type="hidden" id="defaultDis" name="defaultDis" value="${defaultDis}">
           
			<table>
				 <tr>
					<td>巡线点名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="site_name" id="site_name" value="${SITE_NAME}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				 <tr>
					<td>地址：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" name="address" id="address" value="${ADDRESS}"
								/>
						</div>
					</td>
				</tr>
				 <tr>
					<td>类型：</td>
					<td>
						<div>

							<select name="site_type" id="site_type" class="condition-select easyui-validatebox condition"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
								
								<option value='1' <c:if test="${SITE_TYPE==1}">selected</c:if>>关键点</option>
								<option value='2' <c:if test="${SITE_TYPE==2}">selected</c:if>>非关键点</option>
								
							</select>
						</div>
					</td>
				</tr>
				</tr>
				 <tr>
					<td>匹配距离：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" name="site_match" id="site_match" value="${SITE_MATCH}"/><%--<span style="color:red">默认距离：${defaultDis}米</span>
						--%></div>
					</td>
				</tr>
				<%--<tr>
					<td>维护级别：</td>
					<td>
						<div>

							<select name="maintain_rank" id="maintain_rank" class="condition-select easyui-validatebox condition"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
								
								<option value='1' <c:if test="${MAINTAIN_RANK==1}">selected</c:if>>一级</option>
								<option value='2' <c:if test="${MAINTAIN_RANK==2}">selected</c:if>>二级</option>
								<option value='3' <c:if test="${MAINTAIN_RANK==3}">selected</c:if>>三级</option>
								
							</select>
						</div>
					</td>
				</tr>

			--%></table>
		</form>
		
		<div>
			   <p><span>巡线点照片</span></p>   
			<c:if test="${photosize>0}">
				<div id="photo"  data-options="closable:false" style="overflow:auto;">
					<a href="${photo.PHOTO_PATH}" title="照片预览" class="nyroModal">
					<img src="${photo.PHOTO_PATH}" style="width: 100px;"/>
					<a href="javascript:openwindow();">查看更多图片</a>
				</div>
			</c:if>  
			<div id="more_picture"    style="overflow:auto;display:none;">
				<c:forEach items="${photoList}" var="p">
				  <a href="${p.PHOTO_PATH}" title="照片预览" class="nyroModal">
					<img src="${p.PHOTO_PATH}" style="width: 100px;"/>
				</c:forEach>
			</div>
		</div>
		
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="updateForm()">保存</div>
			
		</div>
	</div>
	
	<div id="photo_show"></div>
	<script type="text/javascript">
		$(function() {
			$('.nyroModal').nyroModal();
		});

		openwindow = function() {
			document.getElementById("more_picture").style.display = "block";
			document.getElementById("photo").style.display = "none";
		}
	</script> 
	
</body>
</html>
