<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>编辑班组</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="ff" method="post">
			<input type="hidden" name="edept_id" value="${dept.DEPT_ID}" />
			<table>
				<tr>
					<td>班组编码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="edept_no" value="${dept.DEPT_NO}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>班组名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="edept_name" value="${dept.DEPT_NAME}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td width="10%">区域：</td>
					<td width="30%">
						<select name="eson_area" class="condition-select">
								<option value="">
									请选择
								</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 90px">
			<div class="btn-operation" onClick="updateForm()">更新</div>
		</div>
	</div>
	<script type="text/javascript">
	$(document).ready(function() {
			getSonAreaList3();
		});
		var areaId='${areaId}';
		function getSonAreaList3() {
		$.ajax({
			type : 'POST',
			url : webPath + "General/getSonAreaList.do",
			data : {
				areaId : areaId
			},
			dataType : 'json',
			success : function(json) 
			{
				var result = json.sonAreaList;
				$("select[name='vson_area'] option").remove();
				var GLY ='${GLY}';
				var LXXJ_ADMIN ='${LXXJ_ADMIN}';
				var LXXJ_ADMIN_AREA ='${LXXJ_ADMIN_AREA}';
				if (GLY == 'true' || LXXJ_ADMIN =='true' || LXXJ_ADMIN_AREA=='true')
				{
					$("select[name='eson_area']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${dept.SON_AREA_ID}){
							$("select[name='eson_area']").append("<option value=" + result[i].AREA_ID + " selected>"
									+ result[i].NAME + "</option>");
						}
						else
						{
							$("select[name='eson_area']").append("<option value=" + result[i].AREA_ID + ">"
									+ result[i].NAME + "</option>");
						}
					}
					
				}
				else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${sonAreaId}){
							$("select[name='eson_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
						}
					}
				}
			}
		});
	}
</script>
</body>
</html>
