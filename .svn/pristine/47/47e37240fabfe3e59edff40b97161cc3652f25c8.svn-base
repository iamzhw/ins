<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>新增班组</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="ff" method="post">
			<table>
				<tr>
					<td>班组编码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="vdept_no"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>班组名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="vdept_name"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				
				<tr>
					<td width="10%">区域：</td>
					<td width="30%">
						<select name="vson_area" class="condition-select">
								<option value="">
									请选择
								</option>
						</select>
					</td>
				</tr>
				
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>
			<div class="btn-operation" onClick="clearForm()">清空</div>
		</div>
	</div>
		<script type="text/javascript">
		$(document).ready(function() {
			getSonAreaList();
		});
		
		function getSonAreaList() {
		var areaId='${areaId}';
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
					$("select[name='vson_area']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
					
						$("select[name='vson_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
					}
					
				}
				else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${sonAreaId}){
							$("select[name='vson_area']").append("<option value=" + result[i].AREA_ID + ">"
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