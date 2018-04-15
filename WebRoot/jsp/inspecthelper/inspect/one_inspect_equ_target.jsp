<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>问题填报</title>
</head>

<body style="padding:3px;border:0px;">
	<div  style="padding:5px;">
		<div >
			<form id="target_detail_form" action="" method="post">
				<input type="hidden" id="orderId" value="${orderId }" name="paras_orderId"/>
				<input type="hidden" id="equipmentId" value="${equipmentId }" name="paras_equipmentId"/>
				<input type="hidden" id="staffNo" value="${staffNo }" name="paras_staffNo"/>
			</form>
		</div>
	</div>
	<table id="dg_up" title="【问题填报】" ></table>
	
	<div style="padding-left: 300px;padding-top: 20px;" >
		<div class="btn-operation" style="float: none;" onClick="okBtn()">确定</div>
	</div>
	<div id="win_inspect"></div>
	<script type="text/javascript">
		$(document).ready(function() {
		searchInspect();
		});
		function searchInspect() {
			var orderId = $("#orderId").val().trim();
			var equipmentId=$("#equipmentId").val().trim();
			var staffNo=$("#staffNo").val().trim();
			var obj = {
					//taskName : taskName,
					orderId : orderId,
					equipmentId:equipmentId,
					staffNo : staffNo
			};
			//return;
			$('#dg_up').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				//toolbar : '#tb',
				url : webPath + "inspect/queryOneEquTarget.do",
				queryParams : obj,
				method : 'post',
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'TARGET_ID',
					title : 'ID',
					hidden:true
				},{
					field : 'TARGET_NAME',
					title : '检查项',
					width : "20%",
					align : 'center'
				}, {
					field : 'button',
					title : '操作',
					width : "20%",
					align : 'center',
					formatter: function(value,row,index){
								return "<input type='button' class='button1 btnques' value='上次问题' onclick='btnques(\""+row.TARGET_ID+"\")'/>"+
										"<input type='button' class='btndesc' value='描述' onclick='btndesc(\""+row.TARGET_ID+"\")'/>";
					}
				}, {
					field : 'update',
					title : '上传照片',
					width : "15%",
					align : 'center',
					formatter: function(value,row,index){
								return "<input type='button' class='btnupload' value='上传照片' onclick='btnupload(\""+row.TARGET_ID+"\")'/>";
					}
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
		}
	
	//上次问题
	function btnques(targetId){
			var orderId = $("#orderId").val().trim();
			var equipmentId = $("#equipmentId").val().trim();
			$("#win_inspect").window({
			title: "【上次问题】",
            href : webPath + "inspect/getLastTrou.do?orderId="+orderId+"&targetId="+targetId+"&equipmentId="+equipmentId,
			width : 600,
			height : 350,
            zIndex: 3,
            region: "center",
            collapsible: false,
            cache: false,
            modal: true
        });
	}
	
	//描述
	function btndesc(targetId){
	var orderId = $("#orderId").val().trim();
	var equipmentId = $("#equipmentId").val().trim();
	var obj =new Object();
	var url=webPath + "inspect/saveResTrouIndex.do?orderId="+orderId+"&targetId="+targetId+"&equipmentId="+equipmentId;
	if(targetId == "0033"){
		url = webPath+"inspect/cDuanIndex.do?orderId="+orderId+"&equipmentId="+equipmentId+"&targetId="+targetId;
		window.showModalDialog(url ,obj , "status=no;center=yes;dialogWidth=1000px;dialogHeight=500px" );
	}else if(targetId == "0035"){
		url = webPath+"inspect/dinamicChangeIndex.do?orderId="+orderId+"&equipmentId="+equipmentId+"&targetId="+targetId;
		window.showModalDialog(url ,obj , "status=no;center=yes;dialogWidth=800px;dialogHeight=500px" );
	}else{
		window.showModalDialog(url ,obj , "status=no;center=yes;dialogWidth=450px;dialogHeight=350px" );
	}
	//更新按键文字
		var param = "";
		var tUrl = webPath+"inspect/updateButtonV.do?orderId="+orderId+"&equipmentId="+equipmentId+"&targetId="+targetId; 
		$.post(tUrl,param,function(data){
			if(data=="1"){
				$(".btndesc").val("已描述");
			}
		},'text');
	//var url=webPath + "inspect/saveResTrouIndex.do?orderId="+orderId+"&targetId="+targetId+"&equipmentId="+equipmentId;
	//var obj =new Object();
	//window.showModalDialog(url ,obj , "status=no;center=yes;dialogWidth=600px;dialogHeight=400px" );
	<%--	
		$("#win_inspect").window({
            title: "【描述】",
            href : webPath + "inspect/saveResTrouIndex.do?orderId="+orderId+"&targetId="+targetId+"&equipmentId="+equipmentId,
			width : 450,
			height : 350,
            zIndex: 2,
            region: "center",
            collapsible: false,
            cache: false,
            modal: true
        });--%>
       // 
	}
	
	//上传照片
	function btnupload(targetId){
	var orderId = $("#orderId").val().trim();
	var equipmentId = $("#equipmentId").val().trim();
		 $("#win_inspect").window({
            title: "【照片上传】",
            href : webPath + "inspect/uploadPhoto.do?orderId="+orderId+"&targetId="+targetId+"&equipmentId="+equipmentId,
			width : 380,
			height : 250,
            zIndex: 3,
            region: "center",
            collapsible: false,
            cache: false,
            modal: true
        });
	
	}
	function savePhoto(){
		$.messager.progress(); 
		var orderId=$.trim($("#p_orderId").val());
		var equipmentId=$.trim($("#p_equipmentId").val());
		var targetId=$.trim($("#p_targetId").val());
		var p1=$.trim($("#photo1").val());
		var p2=$.trim($("#photo2").val());
		var p3=$.trim($("#photo3").val());
		var p4=$.trim($("#photo4").val());
		var url = webPath+"inspect/uploadPhoto1.do?orderId="+orderId+"&equipmentId="+equipmentId+"&targetId="+targetId+"&p1="+p1+"&p2="+p2+"&p3="+p3+"&p4="+p4;
		$('#upload_photo').form("submit",{
		url : url,
		success : function(json) {
			if(json){
				$.messager.alert('上传成功','上传成功！','info');
				var tUrl = webPath+"inspect/updatePhotoButtonV.do?orderId="+orderId+"&equipmentId="+equipmentId+"&targetId="+targetId; 
					$.post(tUrl,function(data){
						if(parseInt(data) > 0){
							$(".btnupload").val("照片("+data+")");
						}
						closeWindow();
						},'text');
			}else{
				$.messager.alert('上传失败','上传失败！','info');
			}
				},
		error:function (){
			$.messager.alert('错误','上传错误！','info');
		}
		});
		$.messager.progress('close');
		}
	
	function closeWindow(){
	$('#win_inspect').panel('close');
	}
	
	//确定	
	function okBtn(){
					var orderId = document.getElementById("orderId").value;
					var equipmentId = document.getElementById("equipmentId").value;
					var staffNo= document.getElementById("staffNo").value;
					var url = webPath+"inspect/checkOneEqup.do?orderId="+orderId+"&equipmentId="+equipmentId+"&staffNo="+staffNo;
					$('#target_detail_form').form("submit",{
						url : url,
						success : function(json) {
								$.messager.alert('提示',json+"!",'info');
								$("#win_role").window('close');
						},
					error:function (){
						$.messager.alert('错误','错误！','info');
					}
					});
		
	}
	</script>
</body>
</html>