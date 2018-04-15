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
	<form id="chargeback_troubleId" method="post" enctype ="multipart/form-data">
		<input type="hidden" name="chargeback_troubleIds" id="chargeback_troubleIds" value="" />
		<table>
			<tr>
				<td><label>回单说明：</label></td>
				<td>
					<textarea name="chargeback_remarks"  id="chargeback_remarks" class="condition-select condition"></textarea>
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
		if($("#chargeback_troubleId").form('validate')){
			$.messager.confirm('系统提示', '您确定要回单吗?', function(r) {
				if(r){
					$('#chargeback_troubleIds').val($("input[name='selected']").val());
		              $('#chargeback_troubleId').form('submit', {
		                    url: webPath + "insOrderTask/completeChargebackTask.do",
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
