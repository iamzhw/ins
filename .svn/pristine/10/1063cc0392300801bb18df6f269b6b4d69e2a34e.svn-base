<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>编辑代维公司</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="ff" method="post">
			<table>
				<tr>
					<td width="14%"  >
						地市：
					</td>
					<td>
						<select name="edit_AREA_ID" id="edit_area" class="condition-select" style="width:80%" onchange="getSonAreaList_edit()">
							<option value="">
								请选择
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="14%">区县：</td>
					<td>
						<select name="edit_SON_AREA_ID" id="edit_son_area" class="condition-select" style="width:80%">
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
			<div class="btn-operation" onClick="closeForm()">取消</div>
		</div>
	</div>
</body>
</html>