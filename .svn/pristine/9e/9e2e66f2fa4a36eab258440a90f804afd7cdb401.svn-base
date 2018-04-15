<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/js/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="<%=path%>/js/themes/demo.css" />
<title>关联班组</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="relation" method="post">
			<table>
				<tr>
					<td>代维公司名称：</td>
					<td>
						<input class="condition-text easyui-validatebox condition" 
							value="${comName}"
							type="text" name="edit_company_name" disabled="disabled"/>
					</td>
					<td><input hidden="true" name="hide_com_id" value="${comId}"/></td>
				</tr>
				<tr>
					<td>地市：</td>
					<td>
						<select name="varea" class="condition-select condition" onchange="getSonAreaList('add')" data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
							<c:forEach items="${areaList}" var="al" varStatus="vs">
								<option value="${al.AREA_ID}">${al.NAME}</option>
							</c:forEach>
						</select> 
						<script>
							$(function() {
								getSonAreaList('add');
							})
						</script>
					</td>
				</tr>
				<tr>
					<td>区县：</td>
					<td><select name="vson_area" 
						class="condition-select condition" onchange="getBanzuInfo()"
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
					</select>
					</td>
				</tr>
				<tr>
					<td>班组：</td>
					<td>
						<div class="select_checkBox condition-text-container" >
							<div class="chartQuota">
								<input type="text" id="toWarning" value="请选择" style="width: 150px;font-size: 12px;margin: 0 0 0 -3px;height: 20px;">
								<input type="hidden" id="value" name="team_id_value" value="">	
								<input type="hidden" id="content" value="" style="width:97%">		
							</div>
							<div class="chartOptionsFlowTrend">
								<ul name="ul_name"></ul>
								<!-- <select name="own_company" id="own_company"
									class="condition-select condition"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
									<option value=''>--请选择--</option>
								</select> -->
								<p>
									<a href="#" title="确定" hidefocus="true" class="a_0" onclick="makeSure()"> 确定 </a>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" title="重置" hidefocus="true" class="a_1" onclick="toReset()">重置</a>
								</p>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveRelation()">确定</div>
			<div class="btn-operation" onClick="closeForm()">取消</div>
		</div>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select_checkBox").hover(function(){
				$(".chartOptionsFlowTrend").css("display","inline-block");
			},function(){
				$(".chartOptionsFlowTrend").css("display","none");
			});
		}); 
	</script>
	
</body>
</html>