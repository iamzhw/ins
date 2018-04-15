<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>修改</title>

</head>
<body style="padding: 3px; border: 0px">
		<div id="r-result">
			<div style="height: 35px; border-bottom: 1px solid #d2d2d2;">
				<div class="btn-operation" onClick="update();">修改</div>
				<div class="btn-operation" onClick="history.back();">返回</div>
			</div>
			<div style="overflow:auto; width: 100%;height: 430px;">
			<div id ="details">
			<form action="" id="formp" method="post">
					<table id="${POINT_ID}">
						<input name="POINT_ID" type="hidden" value="${POINT_ID}"/>
						<tr>
							<td style="width: 25%; height: 30px; font-weight: bold;">设备编码：</td>
							<td style="text-align: left; width: 50%; font-weight: bold;">
							<input name="POINT_NO" type="text" class="" value="${POINT_NO}"/>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">设备名称：</td>
							<td style="text-align: left; width: 70%;">
							<input name="POINT_NAME" type="text" class="" value="${POINT_NAME}"/>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">设备类型：</td>
							<td style="text-align: left; width: 70%;">
							<select name="EQP_TYPE_ID" class="condition-select">
								<option value="">请选择 </option>
								<c:forEach items="${equipTypes}" var="type1">
									<option value="${type1.EQUIPMENT_TYPE_ID }">${type1.EQUIPMENT_TYPE_NAME }</option>
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">点类型：</td>
							<td style="text-align: left; width: 70%;">
							<select name="POINT_TYPE" class="condition-select">
								<option value="">请选择 </option>
								<c:forEach items="${pointTypeList }" var="type">
									<option value="${type.POINT_TYPE_ID }">${type.POINT_TYPE_NAME}</option>
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">维护等级：</td>
							<td style="text-align: left; width: 70%;">
							<select name="POINT_LEVEL" class="condition-select" id="mnt_type">
								<option value="">请选择 </option>
								<c:forEach items="${mntLevel}" var="mnt">
									<option value="${mnt.LEVEL_ID }">${mnt.LEVEL_NAME }</option>
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">设备位置</td>
							<td style="text-align: left; width: 70%;">
							<select name="AREA_TYPE" class="condition-select" id="AREA_TYPE">
								<option value="">请选择 </option>
								<c:forEach items="${areaType}" var="t">
									<option value="${t.id }">${t.name }</option>
								</c:forEach>
							</select>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">网格</td>
							<td style="text-align: left; width: 70%;">
							<select name="DEPT" class="condition-select" id="DEPT">
								<option value="">请选择 </option>
								<c:forEach items="${deptList}" var="dept">
									<option value="${dept.DEPT_NO}">${dept.DEPT_NAME }</option>
								</c:forEach>
							</select>
							</td>
						</tr> 
						<tr>
							<td style="width: 30%; height: 30px;">区域：</td>
							<td style="text-align: left; width: 70%;">
							<select name="SON_AREA_ID" class="condition-select" id="SON_AREA_ID">
								<option value="">请选择 </option>
								<c:forEach items="${area}" var="a">
									<c:if test="${SON_AREA_ID==a.AREA_ID}">
										<option value="${a.AREA_ID }" selected="selected">${a.NAME }</option>
									</c:if>
									<c:if test="${SON_AREA_ID!=a.AREA_ID}">
									<option value="${a.AREA_ID}">${a.NAME }</option>
									</c:if>
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">所属区域：</td>
							<td style="text-align: left; width: 70%;">${SON_AREA}</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">坐标：</td>
							<td style="text-align: left; width: 70%;">${LONGITUDE}</br>${LATITUDE}</td>
						</tr>
					</table>
				</div>
		</div>
		</div>
		<input name="POINT_ID" type=hidden class="" value="${POINT_ID}"/>
		</form>
	<script type="text/javascript">

</script>
</body>
</html>