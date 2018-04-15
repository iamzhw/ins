<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>工单审核</title>
</head>
<body>
<div style="padding:20px 0 10px 50px">
	<form id="check_troubleId" method="post" enctype ="multipart/form-data">
		<input type="hidden" name="check_troubleIds" id="check_troubleIds" value="" />
		<input type="hidden" name="check_flag" id="check_flag" value="" />
		<table>
			<tr>
				<td><label>责任公司：</label></td>
				<td>
					<div class="condition-text-container">
					<input name="check_companyId" class="condition-text" />
					</div>
				</td>
				<td><label>考核金额：</label></td>
				<td>
					<div class="condition-text-container">
					<input name="check_money" class="condition-text" />
					</div>
				</td>
			</tr>
			<tr>
				<td><label>项目编号：</label></td>
				<td>
					<div class="condition-text-container">
					<input name="check_projNumber" class="condition-text" />
					</div>
				</td>
				<td><label>安装人员：</label></td>
				<td>
					<div class="condition-text-container">
					<input name="check_installName" class="condition-text" />
					</div>
				</td>
			</tr>
			<tr>
				<td><label>安装公司：</label></td>
				<td>
					<div class="condition-text-container">
					<input name="check_installCompany" class="condition-text" />
					</div>
				</td>
				<td><label>安装时间：</label></td>
				<td>
					<div class="condition-text-container">
					<input name="check_installDate" class="condition-text" />
					</div>
				</td>
			</tr>
			<tr>
				<td><label>业务号：</label></td>
				<td>
					<div class="condition-text-container">
					<input name="check_accessNum" class="condition-text" />
					</div>
				</td>
				<td><label>备注：</label></td>
				<td>
					<div class="condition-text-container">
					<input name="check_remarks" class="condition-text" />
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="1">
					查看照片：
				</td>

				<td colspan="3">

					<div style="height: 145px; width: 100%; overflow-y: scroll; border: 1px solid #aaa;">
						<% int i = 0; %>
						<c:forEach items="${photoList}" var="v">
							<% 
								if(i%3==0) out.print("<div>");
							%>

							<div style="margin: 0px 5px 5px 0; float: left; border: 1px solid #bbb; padding: 2px;">
									
								<img onclick="openImg('${v.FILE_PATH}' )" src="${v.MICRO_PHOTO_PATH}"
									style="cursor: pointer;" height="120" border="0" />
								<div style="font-size: 12px; font-family: '宋体'">
									${v.type}
								</div>
							</div>
							<%
								i++;
								if(i%3==0){
									out.print("<div style=\"clear: both\"></div></div>");
								}
							%>
						</c:forEach>
					</div>
					
				</td>
		</tr>
		<tr>
			<c:forEach items="${fileList}" var="v">
				<td height="20" align="left">
					<a href="${v.FILE_PATH}">文件下载</a>
				</td>
			</c:forEach>
		</tr>
		</table>
	</form>
	<div style="text-align:left;padding:10px 0 10px 90px">
		<div class="btn-operation" onClick="saveEdit('0')">通过</div>
		<div class="btn-operation" onClick="saveEdit('1')">不通过</div>
		<div class="btn-operation" onClick="cancelEdit()">取消</div>
	</div>
</div>

<script type="text/javascript">

	$("#check_companyId").change(
		function(){
			var companyId=document.getElementById("check_companyId").value;
			$.ajax({
				type:'POST',
				dataType:'json',
				url:  webPath + "insOrderTask/getStaffByCompany.do",
				data: { companyId:companyId },
				success:function(json)
				{
					var result = json.staffList;
					$("#check_staffId option").remove();
					 $("#check_staffId").append("<option value=''>----------请选择----------</option>");
					if(result.length>0)
					{
						for(var i=0;i<result.length;i++)
						{
							$("#check_staffId").append("<option value=" + result[i].STAFF_ID + ">" + result[i].STAFF_NAME + "</option>");
						}
				}  else {
	                    $("#check_staffId option").remove();
	                    //添加一条选项
	                    $("#check_staffId").append("<option value=''>----------暂无人----------</option>");
  						 }
				}	
			});
		});

	function cancelEdit(){
		$('#win_trouble').window('close');
	}
	
	function getInfo(){
		var companyId = document.getElementById("paras_projNumber").value;
		$.ajax({
				type:'POST',
				dataType:'json',
				url:"<%=request.getContextPath()%>/ins-order!getStaffByFiberCode",
				data: { companyId:companyId },
				success:function(result)
				{if(result.length>0){
					document.getElementById("paras_installName").value=result[0].INSTALL_STAFF;
					document.getElementById("paras_installCompany").value=result[0].INSTALL_COMPANY;
					document.getElementById("paras_installDate").value=result[0].INSTALL_DATE;
					document.getElementById("paras_accessNum").value=result[0].ACCESS_NUM;
					}
				}
		});
	}

	function saveEdit(type){
		if($("#check_troubleId").form('validate')){
			$.messager.confirm('系统提示', '您确定要审核工单吗?', function(r) {
				if(r){
					$('#check_troubleIds').val($("input[name='selected']").val());
					$('#check_flag').val(type);
		              $('#check_troubleId').form('submit', {
		                    url: webPath + "insOrderTask/completeCheckTask.do",
		                    onSubmit: function () {
		                        $.messager.progress();
		                    },
		                    success: function () {
		                        $.messager.progress('close'); // 如果提交成功则隐藏进度条
		                        $.messager.show({
		                            title: '提  示',
		                            msg: '工单审核成功!',
		                            showType: 'show'
		                        });
		                        $('#win_trouble').window('close');
		                        searchData();
		                    }
		                });
				}
			});
		}
	}
</script>
</body>
</html>
