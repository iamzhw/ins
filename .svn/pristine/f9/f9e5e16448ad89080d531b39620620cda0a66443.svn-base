<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>新增点评</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="ff" method="post">
			<table>
				<tr>
					<!--  <td><img src="" id="rphoto"></td>-->
				</tr>
				<tr>
					<td>是否合格：</td>
					<td>
						<input id="ifcorret" name="ifcorret" type="radio" value="0" onclick="hideremark()">合格
						<input id="ifcorret" name="ifcorret" type="radio" value="1" onclick="getremarks()">不合格
					</td>
				</tr>
				<tr>
					<td>点评：</td>
					<td id="defaultremark">
					<input id="default" type="radio" name="default" value="照片质量差" onclick="hidetext()">照片质量差
					<input id="default" type="radio" name="default" value="设备本身问题" onclick="hidetext()">设备本身问题
					<input id="default" type="radio" name="default" value="其他" onclick="showtext()">其他
						<textarea id="remark" style="width:300px;height:120px;"></textarea>
					</td>
				</tr>
				
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>
		</div>
	</div>
		<script type="text/javascript">
		$(document).ready(function() {
		$("#remark").hide();
		$("#defaultremark").hide();
			//$("#rphoto").attr("src",$("input[name='vurl']").val());
		});
		
		function saveForm() {
			var pass = $("#ff").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要新增点评吗?', function(r) {
					if (r) {
						var photo_id = $("input[name='vphoto']").val();
						var remark = $("#default:checked").val();
						var status = $("#ifcorret:checked").val();
						var dremark = $("#remark").val();
						if(remark=="其他"&&dremark!=null){
							remark=dremark;
						}
						$.ajax({
							type : 'POST',
							url : webPath + "Remark/save.do",
							data : {
								photo_id : photo_id,
								remark : remark,
								status : status
							},
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '新增成功!',
										showType : 'show'
									});
								}
								$('#win_add').window('close');
							}
						})
					}
				});
			}
		}
		
		function getremarks(){
		$("#defaultremark").show();
		}
		function showtext(){
			$("#remark").show();
		}
		function hidetext(){
		$("#remark").hide();
		}
		function hideremark(){
		$("#defaultremark").hide();
		}
</script>
</body>


</html>