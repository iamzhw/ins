<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../util/head.jsp"%>
<script type="text/javascript">
 function save(){
	//获取所有看护员  
	var khyId="";
	$("[name=khy]").each(function(){
		if(this.checked){
			khyId=khyId+this.value+",";
		}
	});
	
	if(khyId==''){
		alert("请至少选择一个看护员！");
		return;
	}
	
	//获取所有告警对象
	
	var target="";
	var modelId="";
	var flag=true;//each内return后 整个函数不能结束  
	
	$("[name=set]").each(function(){
		if(this.checked){
			target=target+this.value+",";
			var curModel=$(this).parent().next().children("#mms_type").val();
			if(curModel==''){
				alert($(this).next().text()+"的模板不能为空！");
				flag=false;
				
				return false;
			}else{
				modelId=modelId+$(this).parent().next().children("#mms_type").val()+",";
			}
			
		}
	});
	
	if(!flag){
		return;
	}
	if(target==''){
		alert("至少选择一个告警对象！");
		return;
	}
	$.ajax({          
		  async:false,
		  type:"post",
		  url :webPath + "mmsModelController/saveShortMessageAlarm.do",
		  data:{khyId:khyId,target:target,modelId:modelId},
		  dataType:"json",
		  success:function(data){
			  if(data.status){
				  $.messager.alert('提示','操作成功','info');
			  }else{
				  $.messager.alert('提示','操作失败','error');
			  }
		   }
	  });
	 
 }


 function getSettings(staffId){
	 $.ajax({          
		  async:false,
		  type:"post",
		  url :webPath + "mmsModelController/getSettings.do",
		  data:{staffId:staffId},
		  dataType:"json",
		  success:function(data){
			  var res=data.settingList;
			  if(data.status){
				  
				//制空
				   $("[name=set]").each(function(){
					  this.checked='';
					  $(this).parent().next().children("#mms_type").val("");
				  });
				
				  if(res.length==0){
					  alert("该看护员还没设置告警对象！");
					  return;
				  }
				  
				  
				  $("[name=set]").each(function(){
					  for(var i in res){
						  if(res[i].SEND_TYPE==this.value){
							  this.checked='checked';
							  $(this).parent().next().children("#mms_type").val(res[i].MMS_ID);
							  continue;
						  }
					  }
				  });
			  }
		   }
	  });
 }
 
 function selectAll(){
	 $("[name=set]").each(function(){
			this.checked='checked';
	  });
 }
</script>
<title></title>
</head>
<body style="padding: 3px; border: 0px">


	<fieldset style="width:250px;height:500px;float:left;margin-left: 50px">
		<legend>本地看护人员</legend>
		<table id="khy">


			<c:forEach items="${khyList}" var="res">

				<tr>
					<td><input type="checkbox" name="khy" value="${res.STAFF_ID}"/>${res.STAFF_ID}
						${res.STAFF_NAME}</td>
                    <td><input value="查看设置详情" type="button" onclick="getSettings(${res.STAFF_ID})"/></td>
				</tr>

			</c:forEach>


		</table>
	</fieldset>

	<fieldset style="width:500px;height:500px;float:left">
		<legend>短信告警对象设置</legend>
		<div style=" padding:10px;">
			<form id="ff">

				<table id="set">

					<tr>
						<td><input type="checkbox" value="0" name="set"/><span>发送给自己</span></td>
						<td>模板： <select name="mms_type" id="mms_type"
							class="condition-select">
								<option value=''>--请选择--</option>
								<c:forEach items="${mmsModelList}" var="res">
									<option value='${res.MMS_ID}'>${res.MSS_NAME}</option>
								</c:forEach>

						</select></td>
					</tr>
					<tr>
						<td><input type="checkbox" value="1" name="set"/><span>发给上级</span></td>
						<td>模板： <select name="mms_type" id="mms_type"
							class="condition-select">
								<option value=''>--请选择--</option>
								<c:forEach items="${mmsModelList}" var="res">
									<option value='${res.MMS_ID}'>${res.MSS_NAME}</option>
								</c:forEach>

						</select></td>
					</tr>
					<tr>
						<td><input type="checkbox" value="2" name="set"/><span>发给相关人员</span></td>
						<td>模板： <select name="mms_type" id="mms_type"
							class="condition-select">
								<option value=''>--请选择--</option>
								<c:forEach items="${mmsModelList}" var="res">
									<option value='${res.MMS_ID}'>${res.MSS_NAME}</option>
								</c:forEach>

						</select></td>
					</tr>
					<tr>
						<td><input type="checkbox" value="3" name="set"/><span>发给管理员</span></td>
						<td>模板： <select name="mms_type" id="mms_type"
							class="condition-select">
								<option value=''>--请选择--</option>
								<c:forEach items="${mmsModelList}" var="res">
									<option value='${res.MMS_ID}'>${res.MSS_NAME}</option>
								</c:forEach>

						</select></td>
					</tr>
					<tr>
						<td><input type="checkbox" value="4" name="set"/><span>发给外力点负责人</span></td>
						<td>模板： <select name="mms_type" id="mms_type"
							class="condition-select">
								<option value=''>--请选择--</option>
								<c:forEach items="${mmsModelList}" var="res">
									<option value='${res.MMS_ID}'>${res.MSS_NAME}</option>
								</c:forEach>

						</select></td>
					</tr>

				</table>
			</form>
		</div>

		<div style="height: 35px;">
			<div style="margin-left: 10px;" class="btn-operation"
				onClick="selectAll();">全选</div>
			<div style="margin-left: 10px;" class="btn-operation"
				onClick="save();">保存</div>

		</div>
	</fieldset>







</body>
</html>
