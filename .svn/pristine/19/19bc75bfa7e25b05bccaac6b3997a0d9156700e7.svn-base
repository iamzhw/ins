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
					<td width="10%">代维公司：</td>
					<td width="20%">
						<select id="show_company" name="show_company" class="condition-select">
								<option value="">
									请选择
								</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveRelation()">保存</div>
			<div class="btn-operation" onClick="closeForm()">取消</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	getMainTainCompany();
	
	function getMainTainCompany(){
		alert();
		$.ajax({
			type : 'POST',
			url : webPath + "General/getMainTainCompany.do",
			dataType : 'json',
			success : function(json) 
			{		
				var result = json.mainTainCompany;
				$("select[name='show_company'] option").remove();
				$("select[name='show_company']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
					$("select[name='show_company']").append("<option value=" + result[i].COMPANY_ID + ">"+ result[i].COMPANY_NAME + "</option>");					
				}
			}
		});
	}
</script>
</html>