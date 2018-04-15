<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>问题填报</title>
</head>
<body>
		<form id="cduan-trou-form"  method="post">
			<input type="hidden" id="orderId" value="${orderId }" />
			<input type="hidden" id="equipmentId" value="${equipmentId }" />
			<input type="hidden" id="changeStatus" value="${changeStatus }" />
			<table width="100%" style="margin-top: 5px">
				<tr id="description">
					<td align="left" width="10%">
						添加描述:
					</td>
				</tr>
				<tr id="description1">
					<td align="left">
						<textarea type="text" id="remarks" cols="60" rows="4"
							name="remarks" class="main-input"></textarea>
					</td>
				</tr>
				
				
				<tr>
				<td align="center">
										<input type="button"  onclick="btnSave1();" value="问题上报" />
										<input type="button" onclick="btnSave2();"  value="已处理" />
										<input type="button" onclick="closeWindow();" value="关闭"/>
				</td></tr>
			
			</table>
		</form>
		<div id="message"></div>
		<div id="querycontentdiv"></div>
	</body>
</html>
	<script type="text/javascript">
	function btnSave1(){
					var targetId = "0033";
					var orderId = document.getElementById("orderId").value;
					var equipmentId = document.getElementById("equipmentId").value;
					var changeStatus = document.getElementById("changeStatus").value;
					//alert("orderId:"+orderId+"equipmentId:"+equipmentId+"changeStatus:"+changeStatus);
					var type = 0;
					var remarks ="原变动情况:" + changeStatus+";"+document.getElementById("remarks").value;
					remarks = encodeURI(encodeURI(remarks));
					var url = webPath+"inspect/saveResTrou_.do?orderId="+orderId+"&equipmentId="+equipmentId+"&targetId="+targetId+"&remarks="+remarks+"&type="+type;
					$('#cduan-trou-form').form("submit",{
						url : url,
						success : function(json) {
							if(json=="true"){
								alert('检查成功!');
								close();
							}else{
								alert('检查失败，请重试！');
							}
						},
					error:function (){
						alert('检查失败，请重试！');
					}
					});
				};
				
	function btnSave2(){
					var targetId = "0033";
					var orderId = document.getElementById("orderId").value;
					var equipmentId = document.getElementById("equipmentId").value;
					var changeStatus = document.getElementById("changeStatus").value;
					var type = 1;
					var remarks = changeStatus + document.getElementById("remarks").value;
					remarks = encodeURI(encodeURI(remarks));
					var url = webPath+"inspect/saveResTrou_.do?orderId="+orderId+"&equipmentId="+equipmentId+"&targetId="+targetId+"&remarks="+remarks+"&type="+type;
					$('#cduan-trou-form').form("submit",{
						url : url,
						success : function(json) {
							if(json=="true"){
								alert('检查成功!');
								close();
							}else{
								alert('检查失败，请重试！');
							}
						},
					error:function (){
						alert('检查失败，请重试！');
					}
					},'text');
				};			
		 function closeWindow(){
					close();
				};
	</script>