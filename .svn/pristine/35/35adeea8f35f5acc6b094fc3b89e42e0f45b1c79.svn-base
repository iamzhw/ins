<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>问题填报</title>
</head>
<body>
		<form id="res-trou-form"  method="post" enctype="multipart/form-data" >
			<input type="hidden" id="m_orderId" value="${orderId}" />
			<input type="hidden" id="m_equipmentId" value="${equipmentId }" />
			<input type="hidden" id="m_targetId" value="${targetId }" />
			<input type="hidden" id="m_state" name="m_state" value="${state }" />
			<table width="50%" style="margin-top: 5px">
				<tr id="description">
					<td align="left" width="200px;">
						添加描述:
					</td>
					<td></td>
				</tr>
				
				<tr id="description1">
					<td align="left" colspan="2">
						<textarea  type="text" id="remarks" cols="50" rows="4"
							name="remarks" class="main-input">${remarks}</textarea>
					</td>
				</tr>
				<tr id="radio">
					<td align="left" >
						<input type="radio" checked name="upload_radio"  value="1" />上传Excel
					</td>
					<td>
						<input type="radio"  name="upload_radio"  value="0" />上传Word
					</td>
				</tr>
				<tr id="upload_file1">
					<td align="left" width="50%">
						上传Excel:
					</td>
					<td width="50%">
						<input name = "excel" type="file" id="excel"/>
					</td>
				</tr>
				<tr id="upload_file2">
					<td align="left" width="50%">
						上传Word:
					</td>
					<td width="50%">
						<input name = "word" type="file" id="word"/>
					</td>
				</tr>
				<tr>
				<td  align="left" width="50%">
				<a href="<%=request.getContextPath()%>/excelFiles/ODF_Example_new20130408.xls">ODF模板下载</a>
				</td><td align="left">
				<a href="<%=request.getContextPath()%>/excelFiles/GJ_Example_new20130408.xls">光交模板下载</a>
				</td>				
				</tr>
				<tr>
				<td colspan="2" align="center">
										<input type="button"  onclick="btnSave1();" value="问题上报" />
										<input type="button" onclick="btnSave2();"  value="已处理" />
										<input type="button" onclick="closeWindow();" value="关闭"/>
				</td>
				</tr>
			
			</table>
		</form>
		</body>
	<script type="text/javascript">
	 $(function(){
	 	var targetId=$.trim($("#m_targetId").val());
	 			 if(targetId!='0015' && targetId!='0017' && targetId!='0018' && targetId!='0029'  && targetId!='0033' && targetId!='0034' && targetId!='0035' && targetId!="1005" && targetId!="1007" && targetId!="1016" ){
					$("#upload_file1").hide();
					$("#radio").hide();
					//$("#file2").hide();
			      }
			    if(targetId=='0015' || targetId=='0017' || targetId=='0029'  || targetId=='0033' || targetId==='0034' || targetId=='0035' || targetId=="1005" || targetId=="1007" || targetId=="1016"){
				$("#description").hide();
				$("#description1").hide();
				$("#upload_file2").hide();
			    }
			   $("input[name='upload_radio']").click(function(){
			   	var checkBox = $("input[name='upload_radio']");
					for(var i = 0;i<checkBox.length;i++){
						var cb = checkBox[i];
						if(cb.checked){
							if(cb.value == 1){
								$("#upload_file2").hide();
								$("#upload_file1").show();
							}else if(cb.value == 0){
								$("#upload_file1").hide();
								$("#upload_file2").show();
							}
							return;
						}
					}
			   });
			   
	 
	 })
	function btnSave1(){
					var targetId = document.getElementById("m_targetId").value;
					var orderId = document.getElementById("m_orderId").value;
					var equipmentId = document.getElementById("m_equipmentId").value;
					var state=document.getElementById("m_state").value;
					var w = "";
					var e = "";
					if(targetId=="0001" ||targetId=="0015" || targetId=="0017" || targetId=="0018" || targetId=="0029"|| targetId=="0034" || targetId=="1005" || targetId=="1007" || targetId=="1016" ){
						w =$.trim($("#word").val());
						e = $.trim($("#excel").val());
						//word = encodeURI(encodeURI(word));
						//excel = encodeURI(encodeURI(excel));
					}
					var type = 0;
					var remarks = document.getElementById("remarks").value;
					remarks = encodeURI(encodeURI(remarks));
					if(remarks.length == 0 && w.length == 0 && e.length == 0){
						alert("请输入描述内容！");
						return;
					}
					var url = webPath+"inspect/saveResTrou.do?orderId="+orderId+"&equipmentId="+equipmentId+"&targetId="+targetId+"&remarks="+remarks+"&type="+type+"&state="+state+"&w="+w+"&e="+e;
					$('#res-trou-form').form("submit",{
						url : url,
						success : function(json) {
							if(json){
								alert('描述成功!');
								close();
							}else{
								alert('描述失败！');
							}
						},
					error:function (){
						alert('保存描述错误！');
					}
					});
				};
				
				
				function btnSave2(){
					var targetId = document.getElementById("m_targetId").value;
					var orderId = document.getElementById("m_orderId").value;
					var equipmentId = document.getElementById("m_equipmentId").value;
					var state=document.getElementById("m_state").value;
					var type = 1;
					var w = "";
					var e = "";
					if(targetId=="0015" || targetId=="0017" || targetId=="0018" || targetId=="0029" || targetId=="0034" || targetId=="1005" || targetId=="1007" || targetId=="1016"){
						w =$.trim($("#word").val());
						e = $.trim($("#excel").val());
						//word = encodeURI(encodeURI(word));
						//excel = encodeURI(encodeURI(excel));
					}
					var remarks = document.getElementById("remarks").value;
					remarks = encodeURI(encodeURI(remarks));
					if(remarks.length == 0 && w.length == 0 && e.length == 0){
						alert("请输入描述内容！");
						return;
					}
					
					var url = webPath+"inspect/saveResTrou.do?orderId="+orderId+"&equipmentId="+equipmentId+"&targetId="+targetId+"&remarks="+remarks+"&type="+type+"&state="+state+"&w="+w+"&e="+e;
					$('#res-trou-form').form("submit",{
						url : url,
						success : function(json) {
							if(json){
								alert('描述成功!');
								close();
							}else{
								alert('描述失败！');
							}
						},
					error:function (){
						alert('保存描述错误！');
					}
					});
	}	
	
	  function closeWindow(){
					close();
				};
	
	</script>
	</html>