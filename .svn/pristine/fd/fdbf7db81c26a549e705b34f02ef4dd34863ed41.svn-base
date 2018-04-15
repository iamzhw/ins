<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>新增隐患类型</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
			<table id="tb">
				<tr>
					<td align="center">隐患名称: </td>
					<td align="center">操作</td>
				</tr>
			</table>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="save()">保存</div>
		</div>
	</div>
	
	<script type="text/javascript">
	
		$(document).ready(function() {
			
			var trouble_type_name = $("input[name='trouble_type_name']").val();
			var troubleTypeNames = trouble_type_name.split(",");
			var showTr="";
			
			if(troubleTypeNames.length==0){
				showTr="<tr><td><input type='text'/></td><td><div class='btn-operation' onClick='add();'>增加</div></td></tr>"
			}
			else{
				showTr="<tr><td><input type='text' value='"+troubleTypeNames[0]+"'/></td><td><div class='btn-operation' onClick='add();'>增加</div></td></tr>"
			}
			
			$("#tb").append(showTr);
				
			for(var i=1;i < troubleTypeNames.length;i++){
				showTr="<tr><td><input type='text' value='"+troubleTypeNames[i]+"'/></td><td><div class='btn-operation' onClick='deleteRow(this);'>删除</div></td></tr>"
				$("#tb").append(showTr);
			}
		});

		function add(){
			var showTr="<tr><td><input type='text' /></td><td><div class='btn-operation' onClick='deleteRow(this);'>删除</div></td></tr>"
				$("#tb").append(showTr);
		}
		
		function deleteRow(obj){
		    var table=document.getElementById('tb'); 
       		table.deleteRow(obj.parentNode.parentNode.rowIndex)  
		}
		
		function save(){
			var tab=document.getElementById("tb"); 
			var rows = tab.rows.length;
			var value="";
			for(var i = 1; i < rows; i++){  
		     	var inputs = tab.rows[i].getElementsByTagName("input");
		        for(var m = 0; m < inputs.length; m++){
		        	inputValue=inputs[m].value;
		        	if(inputValue!=""){
		        		value+=","+inputValue;
		        	}
		        }
			}
			if(value != "")
			{
				value = value.substring(1);
				document.getElementById("troubleTypeNameId").value=value;
				$('#win_staff').window('close');
			}
		}
	</script>
</body>
</html>