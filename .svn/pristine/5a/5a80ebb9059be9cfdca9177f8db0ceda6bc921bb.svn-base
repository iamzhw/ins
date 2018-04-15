<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../../util/head.jsp"%>

<title>新增</title>

<script type="text/javascript">
function saveForm() {
	
	var pass = $("#ff").form('validate');
	
	if (pass) {
		//颜色验证 颜色值差20
		var cable_color=$("#cable_color").val();
		
		var flag=true;
		$.ajax({          
			  async:false,
			  type:"post",
			  url :webPath + "gldManage/validateCblColor.do",
			  data:{cable_color:cable_color},
			  dataType:"json",
			  success:function(data){
				  if(data.status){
					  if(data.res>0){
						  alert("系统中已经有光缆是相似颜色，请重新选择一个颜色！");
						  flag=false;
					  }
				  }else{
					  alert("验证失败，请重新验证");
					  flag=false;
				  }
			  }
		  });
		
		
		if(flag){
			$.messager.confirm('系统提示', '您确定要新增光缆吗?', function(r) {
				if (r) {
					//var data=makeParamJson('#ff');
		
						var cable_name=$("#cable_name").val().trim();
						var fiber_grade=$("#fiber_grade").val();
						var area_id="";
						$("[name=area_id]").each(function(){
							if(this.checked){
								
								area_id=area_id+this.value+",";
							}
							
						});
						
						
						
					$.ajax({
						type : 'POST',
						url : webPath + "gldManage/save.do",
						data : {cable_name:cable_name,fiber_grade:fiber_grade,cable_color:cable_color,area_id:area_id},
						dataType : 'json',
						success : function(json) {
							if (json.status) {
								$.messager.alert("提示","新增光缆成功！","info");
								window.returnValue=1;
								window.close();
							}
							else{
								$.messager.alert("提示","新增光缆失败！","info");
								return;
							}
							
							

						}
					})
				}
			});
		}
		
	}
}

</script>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="ff" method="post">

			<table>
				<tr>
					<td>光缆段名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="cable_name" id="cable_name"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				<tr>
					<td>光缆段等级：</td>
					<td>
						<div>

							<select name="fiber_grade" id="fiber_grade" class="condition-select easyui-validatebox condition"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
								
								<option value='1'>一级</option>
								<option value='2'>二级</option>
								
							</select>
						</div>
					</td>
				</tr>
				<tr>
				  <td>光缆段颜色：</td>
					<td>
						
						
						<div class="condition-text-container">
							<input class="color{required:false,pickerClosable:true} condition-text easyui-validatebox condition"
								type="text" name="cable_color" id="cable_color"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				
				<tr>
					<td>所属地区：</td>
					<td>
						<div>
							 <c:forEach items="${areaList}" var="res">
								    <input type="checkbox" name="area_id" value="${res.AREA_ID}"/>${res.NAME}<br/>
							 </c:forEach>
							
						</div>
					</td>
				</tr>

			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>
			
		</div>
	</div>
</body>
</html>