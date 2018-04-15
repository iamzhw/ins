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
			<input type="hidden" id="equCode" value="${equCode }" />
			<input type="hidden" id="id" value="${id}" />
			<table width="100%" style="margin-top: 5px">
				<tr><td width="10%"><input type="radio"  name="radio" class="checkBox" value="FTTH" />是FTTH
						<input type="radio"  name="radio" class="checkBox" value="BBU" />是BBU
                        <input type="radio"  name="radio" class="checkBox" value="设备" />设备
						<input type="radio"  name="radio" class="checkBox" value="其它" />其它</td></tr>
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
	</body>
</html>
	<script type="text/javascript">
	function btnSave1(){
					var targetId = "0035";
					var orderId = document.getElementById("orderId").value;
					var equipmentId = document.getElementById("equipmentId").value;
					var changeStatus = document.getElementById("changeStatus").value;
					var equCode = document.getElementById("equCode").value;
					var checkBox = document.getElementsByName("radio");
					var remarks = document.getElementById("remarks").value;
					if(remarks.length==0){
						alert("请填写描述！");
						return false;
					}
					var id = document.getElementById("id").value;
					var ftth = "";
					for(var i=0;i<checkBox.length;i++){
						if(checkBox[i].checked){
							ftth = encodeURI(encodeURI(checkBox[i].value));
					}
					}
					//alert("orderId:"+orderId+"equipmentId:"+equipmentId+"changeStatus:"+changeStatus);
					var type = 0;
					if(equCode.length==0){
						equCode = '为空';
					}
					remarks = "端子:" + changeStatus+";"+"系统" + equCode+";"+"现场"+document.getElementById("remarks").value;
					remarks = encodeURI(encodeURI(remarks));
					var url = webPath+"inspect/saveResTrou_.do?orderId="+orderId+"&equipmentId="+equipmentId+"&targetId="+targetId+"&remarks="+remarks+"&type="+type+"&ftth="+ftth+"&id="+id;
					$('#cduan-trou-form').form("submit",{
						url : url,
						success : function(json) {
							if(json=="true"){
								alert('描述成功!');
								close();
							}else{
								alert('描述失败，请重试！');
							}
						},
					error:function (){
						alert('描述失败，请重试！');
					}
					});
				};
				
	function btnSave2(){
					var targetId = "0035";
					var orderId = document.getElementById("orderId").value;
					var equipmentId = document.getElementById("equipmentId").value;
					var changeStatus = document.getElementById("changeStatus").value;
					var equCode = document.getElementById("equCode").value;
					var checkBox = document.getElementsByName("radio");
					var id = document.getElementById("id").value;
					var ftth = "";
					if(checkBox[0].checked){
							ftth = "1";
					}
					var type = 1;
					if(equCode.length==0){
						equCode = '为空';
					}
					var remarks = "端子:" + changeStatus+";"+"系统" + equCode+";"+"现场"+document.getElementById("remarks").value;
					remarks = encodeURI(encodeURI(remarks));
					var url = webPath+"inspect/saveResTrou_.do??orderId="+orderId+"&equipmentId="+equipmentId+"&targetId="+targetId+"&remarks="+remarks+"&type="+type+"&ftth="+ftth+"&id="+id;
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