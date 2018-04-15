<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>工单回单</title>
</head>
<body>
<div style="padding:20px 0 10px 50px">
	<form id="hui_troubleId" method="post" enctype ="multipart/form-data">
		<input type="hidden" name="hui_troubleIds" id="hui_troubleIds" value="" />
		<table>
			<tr>
				<td><label>回单说明：</label></td>
				<td>
					<textarea name="hui_remarks"  id="hui_remarks" class="condition-select condition"></textarea>
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
	function cancelEdit(){
		$('#win_trouble').window('close');
	}

	function saveEdit(){
		if($("#hui_troubleId").form('validate')){
			$.messager.confirm('系统提示', '您确定要回单吗?', function(r) {
				if(r){
					$('#hui_troubleIds').val($("input[name='selected']").val());
		              $('#hui_troubleId').form('submit', {
		                    url: webPath + "insOrderTask/completeHuidanTask.do",
		                    onSubmit: function () {
		                        $.messager.progress();
		                    },
		                    success: function () {
		                        $.messager.progress('close'); // 如果提交成功则隐藏进度条
		                        $.messager.show({
		                            title: '提  示',
		                            msg: '回单成功!',
		                            showType: 'show'
		                        });
		                        searchData();
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
