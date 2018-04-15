<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>工单派发</title>
</head>
<body>
<div style="padding:20px 0 10px 50px">
	<form id="allot_troubleId" method="post" enctype ="multipart/form-data">
		<input type="hidden" name="allot_troubleIds" id="allot_troubleIds" value="" />
		<input type="hidden" name="allot_flag" id="allot_flag" value="${flag}" />
		<table>
			<tr>
				<td><label>单位或者部门：</label> </td>
				<td>
					<select name="allot_companyId" id="allot_companyId" class="condition-select">
						<option value="">----------请选择----------</option>
						<c:forEach items="${companyList}" var="v">
						<option value="${v.COMPANY_ID}">${v.COMPANY_NAME}</option>
						</c:forEach>
					</select>
				</td>	
				<td><label>分配维护人员：</label></td>
				<td>
					<select name="allot_staffId" id="allot_staffId" class="condition-select">
						<option value="">----------请选择----------</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><label>项目编号：</label></td>
				<td>
					<div class="condition-text-container">
					<input name="allot_projNumber" class="condition-text" />
					</div>
				</td>
				<td><label>安装人员：</label></td>
				<td>
					<div class="condition-text-container">
					<input name="allot_installName" class="condition-text" />
					</div>
				</td>
			</tr>
			<tr>
				<td><label>安装公司：</label></td>
				<td>
					<div class="condition-text-container">
					<input name="allot_installCompany" class="condition-text" />
					</div>
				</td>
				<td><label>安装时间：</label></td>
				<td>
					<div class="condition-text-container">
					<input name="allot_installDate" class="condition-text" onClick="WdatePicker({minDate:'%y-%M-{%d+1}'});" />
					</div>
				</td>
			</tr>
			<tr>
				<td><label>业务号：</label></td>
				<td>
					<div class="condition-text-container">
					<input name="allot_accessNum" class="condition-text" />
					</div>
				</td>
				<td><label>备注：</label></td>
				<td>
					<div class="condition-text-container">
					<input name="allot_remarks" class="condition-text" />
					</div>
				</td>
			</tr>
			<tr>
				<td><label>上传照片：</label></td>
				<td>
				<div class="condition-text-container">
					<input type="file" name="photo1" id="photo1"/>
				</div>
				</td>
			</tr>
			<tr>
				<td><label>上传照片：</label></td>
				<td colspan="3">
				<div class="condition-text-container">
					<input type="file" name="photo2" id="photo2"/>
				</div>
				</td>
			</tr>
			<tr>
				<td><label>上传Excel：</label></td>
				<td colspan="3">
				<div class="condition-text-container">
					<input type="file" name="excel" id="excel"/>
				</div>
				</td>
			</tr>
			<tr>
				<td><label>上传Word：</label></td>
				<td colspan="3">
				<div class="condition-text-container">
					<input type="file" name="word" id="word"/>
				</div>
				</td>
			</tr>	
		</table>
	</form>
	<div style="text-align:left;padding:10px 0 10px 90px">
		<div class="btn-operation" onClick="saveEdit()">确定</div>
		<div class="btn-operation" onClick="cancelEdit()">取消</div>
	</div>
</div>

<script type="text/javascript">

	$("#allot_companyId").change(
		function(){
			var companyId=document.getElementById("allot_companyId").value;
			$.ajax({
				type:'POST',
				dataType:'json',
				url:  webPath + "insOrderTask/getStaffByCompany.do",
				data: { companyId:companyId,staffType:'repaire'},
				success:function(json)
				{
					var result = json.staffList;
					$("#allot_staffId option").remove();
					 $("#allot_staffId").append("<option value=''>----------请选择----------</option>");
					if(result.length>0)
					{
						for(var i=0;i<result.length;i++)
						{
							$("#allot_staffId").append("<option value=" + result[i].STAFF_ID + ">" + result[i].STAFF_NAME + "</option>");
						}
				}  else {
	                    $("#allot_staffId option").remove();
	                    //添加一条选项
	                    $("#allot_staffId").append("<option value=''>----------暂无人----------</option>");
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
		})
	}

	function saveEdit(){
		if($("#allot_troubleId").form('validate')){
			$.messager.confirm('系统提示', '您确定要派发工单吗?', function(r) {
				if(r){
					$('#allot_troubleIds').val($("input[name='selected']").val());
		              $('#allot_troubleId').form('submit', {
		                    url: webPath + "insOrderTask/completeTask.do",
		                    onSubmit: function () {
		                        $.messager.progress();
		                    },
		                    success: function () {
		                        $.messager.progress('close'); // 如果提交成功则隐藏进度条
		                        $.messager.show({
		                            title: '提  示',
		                            msg: '工单转派成功!',
		                            showType: 'show'
		                        });
		                        $('#win_trouble').window('close');
		                    }
		                });
				}
			});
		}
	}
</script>
</body>
</html>
