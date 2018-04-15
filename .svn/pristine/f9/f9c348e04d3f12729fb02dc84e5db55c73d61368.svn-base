<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<style type="text/css">
        .table_border td
        {
            border-top:1px #DDD solid;
            border-right:1px #DDD solid;
           border-color: #ccc;
        }
        .table_border
        {
         border-bottom:1px #DDD solid;
         border-left:1px #DDD solid;
         border-color:initial;
         width:100%
        }
    
    .label_custom
    {
    	font-weight:blod;
    	font-size: large;
    	background-color: #ebebeb;
    }
</style>
<title>系统参数</title>
</head>
<body>
	<div style="padding:20px 0 10px 10px">
		<table class="table_border">
			<tr><td colspan="2" class="label_custom"><label>定时任务</label></td></tr>
			<tr>
			<td><label>手动生成巡线任务</label> </td>
			<td>
			<div class="btn-operation" onClick="autoTask()">执行</div>
			</td>
			</tr>
		</table>
	</div>
	<div id="win_param"></div>
	<script type="text/javascript">
		function autoTask() {
			$.messager.confirm('系统提示', '您确定要手动生成任务吗?', function(r) {
				if (r) {
					$.ajax({
						type : 'POST',
						url : webPath + "Param/autoLineTask.do",
						data : {},
						dataType : 'json',
						success : function(json) {
							if (json.status) {
								$.messager.show({
									title : '提  示',
									msg : '任务生成成功！',
									showType : 'show',
									timeout:'1000'//ms
								   
								});
							}
							else{
								$.messager.alert("提示","任务生成失败！","info");
								return;
							}
						}
					});
				}
			});
		}
	</script>
</body>
</html>