<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>新增缆线</title>
</head>
<body style="padding:3px;border:0px" >
	<div class="condition-container">
			<form id="add_new_parent_form" action="" method="post">
				<input type="hidden" name="selectedLineForAdd" value="" />
				<table class="condition">
					<tr>
					<td width="10%">缆线编码：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_cable_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">缆线名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="p_cable_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container" >
			<div class="btn-operation" onClick="confirmParentLine()" >新增</div>
			<div class="btn-operation" onClick="backToParentLine()" >返回</div>
		</div>
	</div>
	<script type="text/javascript">
	function confirmParentLine(){
		$.messager.confirm('系统提示', '您确定要新增吗?', function (r) {
            if (r) {
            	 var line = $('#dg').datagrid('getChecked');
            	 var arr = new Array();
                for (var i = 0; i < line.length; i++) {
                    var value = line[i].CABLE_ID;
                    arr[arr.length] = value;
                }
                $("input[name='selectedLineForAdd']").val(arr);
                $('#add_new_parent_form').form('submit', {
                    url: webPath + "Cable/addNewParentLine.do",
                    onSubmit: function () {
                        $.messager.progress();
                    },
                    success: function () {
                        $.messager.progress('close'); // 如果提交成功则隐藏进度条
                        $.messager.show({
                            title: '提  示',
                            msg: '新增成功!',
                            showType: 'show'
                        });
                        $('#parent_line').window('close');
                        $('#add_parent_line').window('close');
                        searchData();
                    }
                });
            }
        });
	}
	</script>
</body>
</html>