<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title></title>
</head>
<body style="padding: 3px; border: 0px">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input name="relayinfoId" value="${model[0].RELAYINFOID }" type="hidden"/>
				填报单位：<label>${model[0].CITY_NAME }</label>
			</form>
			<div class="btn-operation-container">
				<div style="float:left;" class="btn-operation" onClick="history.back()">返回</div>
				<div style="float:left;" class="btn-operation" onClick="delDetailInfo()">删除</div>
				<div style="float:left;" class="btn-operation" onClick="toAddTestDetailInfo()">新增</div>
				<div style="float:left;" class="btn-operation" onClick="addSubExcel()">批量导入</div>
			</div>
			
		</div>
	
	<div id="dg" style="width: 98%; height: 80%;"></div>
	<div id="win"></div>
	<div id="dialog">
	  <div id="fillData"></div>
	</div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
</body>
	<script type="text/javascript">
		$(function(){
			$("input").each(function(){
				this.readOnly=true;
			});
			searchData();
<%--			$('#dg').datagrid({--%>
<%--				fitColumns:true,--%>
<%--				autoSize : true,--%>
<%--				toolbar : '#tb',--%>
<%--				url : webPath + "/CutAndConnOfFiberController/showRelayDetailInfo.do?relayinfoId="+$("input[name='relayinfoId']").val(),--%>
<%--				method : 'post',--%>
<%--				pagination : true,--%>
<%--				pageNumber : 1,--%>
<%--				pageSize : 10,--%>
<%--				pageList : [ 5, 10, 20, 50 ],--%>
<%--				//loadMsg:'数据加载中.....',--%>
<%--				rownumbers : true,--%>
<%--				singleSelect : false,--%>
<%--				columns : [--%>
<%--					[  --%>
<%--						{checkbox:true,field:'I_INDEX',title:'',width:50,align:'center',rowspan:2},--%>
<%--						{field:'',title:'接头点1',width:50,align:'center',colspan:3},--%>
<%--						{field:'',title:'接头点2',width:50,align:'center',colspan:3},--%>
<%--						{field:'',title:'接头点3',width:50,align:'center',colspan:3},--%>
<%--						{field:'',title:'接头点4',width:50,align:'center',colspan:3},--%>
<%--						{field:'',title:'接头点5',width:50,align:'center',colspan:3},--%>
<%--						{field:'',title:'接头点6',width:50,align:'center',colspan:3},--%>
<%--						{field:'BEFOREDB',title:'割接前</br>总损耗dB',width:50,align:'center',rowspan:2},--%>
<%--						{field:'DB',title:'割接后</br>总损耗dB',width:50,align:'center',rowspan:2},--%>
<%--						{field:'DBKM',title:'衰减系数dB/km',width:100,align:'center',rowspan:2},--%>
<%--						{field:'detail',title:'',width:50,align:'center',rowspan:2,--%>
<%--							formatter:function(value,row,index){--%>
<%--								return "<a href='javascript:void(0)' onclick='toUpdateDetailInfo("+row.RELAYINFOID+","+row.I_INDEX+")'>修改</a>";--%>
<%--							}	--%>
<%--						}--%>
<%--					],--%>
<%--					[--%>
<%--						{field:'DIRECTIONA1',title:'A向km',width:50,align:'center'},--%>
<%--						{field:'DIRECTIONB1',title:'B向',width:50,align:'center'},--%>
<%--						{field:'AVG1',title:'平均',width:50,align:'center'},--%>
<%--						--%>
<%--						{field:'DIRECTIONA2',title:'A向km',width:50,align:'center'},--%>
<%--						{field:'DIRECTIONB2',title:'B向',width:50,align:'center'},--%>
<%--						{field:'AVG2',title:'平均',width:50,align:'center'},--%>
<%--						--%>
<%--						{field:'DIRECTIONA3',title:'A向km',width:50,align:'center'},--%>
<%--						{field:'DIRECTIONB3',title:'B向',width:50,align:'center'},--%>
<%--						{field:'AVG3',title:'平均',width:50,align:'center'},--%>
<%--						--%>
<%--						{field:'DIRECTIONA4',title:'A向km',width:50,align:'center'},--%>
<%--						{field:'DIRECTIONB4',title:'B向',width:50,align:'center'},--%>
<%--						{field:'AVG4',title:'平均',width:50,align:'center'},--%>
<%--						--%>
<%--						{field:'DIRECTIONA5',title:'A向km',width:50,align:'center'},--%>
<%--						{field:'DIRECTIONB5',title:'B向',width:50,align:'center'},--%>
<%--						{field:'AVG5',title:'平均',width:50,align:'center'},--%>
<%--						--%>
<%--						{field:'DIRECTIONA6',title:'A向km',width:50,align:'center'},--%>
<%--						{field:'DIRECTIONB6',title:'B向',width:50,align:'center'},--%>
<%--						{field:'AVG6',title:'平均',width:50,align:'center'},--%>
<%--					]--%>
<%--				],--%>
<%--			});--%>
		});

		function searchData(){
			$('#dg').datagrid({
				fitColumns:true,
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "/CutAndConnOfFiberController/showRelayDetailInfo.do?relayinfoId="+$("input[name='relayinfoId']").val(),
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [
					[  
						{checkbox:true,field:'I_INDEX',title:'',width:50,align:'center',rowspan:2},
						{field:'',title:'接头点1',width:50,align:'center',colspan:3},
						{field:'',title:'接头点2',width:50,align:'center',colspan:3},
						{field:'',title:'接头点3',width:50,align:'center',colspan:3},
						{field:'',title:'接头点4',width:50,align:'center',colspan:3},
						{field:'',title:'接头点5',width:50,align:'center',colspan:3},
						{field:'',title:'接头点6',width:50,align:'center',colspan:3},
						{field:'BEFOREDB',title:'割接前</br>总损耗dB',width:50,align:'center',rowspan:2},
						{field:'DB',title:'割接后</br>总损耗dB',width:50,align:'center',rowspan:2},
						{field:'DBKM',title:'衰减系数dB/km',width:100,align:'center',rowspan:2},
						{field:'detail',title:'',width:50,align:'center',rowspan:2,
							formatter:function(value,row,index){
								return "<a href='javascript:void(0)' onclick='toUpdateDetailInfo("+$("input[name='relayinfoId']").val()+","+row.I_INDEX+")'>修改</a>";
							}	
						}
					],
					[
						{field:'DIRECTIONA1',title:'A向km',width:50,align:'center'},
						{field:'DIRECTIONB1',title:'B向',width:50,align:'center'},
						{field:'AVG1',title:'平均',width:50,align:'center'},
						
						{field:'DIRECTIONA2',title:'A向km',width:50,align:'center'},
						{field:'DIRECTIONB2',title:'B向',width:50,align:'center'},
						{field:'AVG2',title:'平均',width:50,align:'center'},
						
						{field:'DIRECTIONA3',title:'A向km',width:50,align:'center'},
						{field:'DIRECTIONB3',title:'B向',width:50,align:'center'},
						{field:'AVG3',title:'平均',width:50,align:'center'},
						
						{field:'DIRECTIONA4',title:'A向km',width:50,align:'center'},
						{field:'DIRECTIONB4',title:'B向',width:50,align:'center'},
						{field:'AVG4',title:'平均',width:50,align:'center'},
						
						{field:'DIRECTIONA5',title:'A向km',width:50,align:'center'},
						{field:'DIRECTIONB5',title:'B向',width:50,align:'center'},
						{field:'AVG5',title:'平均',width:50,align:'center'},
						
						{field:'DIRECTIONA6',title:'A向km',width:50,align:'center'},
						{field:'DIRECTIONB6',title:'B向',width:50,align:'center'},
						{field:'AVG6',title:'平均',width:50,align:'center'},
					]
				],
			});
		}
		
		//var relayinfoId=$("input[name='relayinfoId']").val();
		function toUpdateDetailInfo(relayinfoId,i_index){
			$("#win").window({
				title : "详情修改",
				href : webPath + "/CutAndConnOfFiberController/toUpdTestDetailInfo.do?relayinfoId="+relayinfoId+"&i_index="+i_index,
				width : 1200,
				height : 350,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		function downloadExampleSub(){
			$("#hiddenIframe").attr("src", "./downloadExampleSub.do");
		}
		function addSubExcel() {
	    	$('#win').window({
				title : "导入EXCEL",
				href : webPath+ "/CutAndConnOfFiberController/addSubExcel.do?relayinfoId="+$("input[name='relayinfoId']").val(),
				width : 400,
				height : 250,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		function countSubstr(str,substr,isIgnore){
		    var count;
		    var reg="";
		    if(isIgnore==true){
		        reg="/"+substr+"/gi";
		    }else{
		        reg="/"+substr+"/g";
		    }
		        reg=eval(reg);
		    if(str.match(reg)==null){
		        count=0;
		    }else{
		        count=str.match(reg).length;
		    }
		    return count;
		}
		function delDetailInfo(){
			var rows=$('#dg').datagrid('getChecked');
			var str="";
			if(!rows.length){
				$.messager.alert("提示","请勾选删除的内容");
				return;
			}
			for (var int = 0; int < rows.length; int++) {
				str+=rows[int].I_INDEX+",";
			}
			var relayinfoId=$("input[name='relayinfoId']").val();
			$.messager.confirm("提示","是否删除",function(i){
				if(i){
					$.ajax({
						url : webPath + "/CutAndConnOfFiberController/delDetailInfoOfTest.do",// 跳转到 action    
						data : {
							relayinfoId : relayinfoId,
							str:str
						},
						type : 'post',
						cache : false,
						dataType : 'json',
						success : function(data) {
							if(data.status){
								$('#dg').datagrid('reload');
								$.messager.alert("提示","删除成功");
							}else{
								$.messager.alert("提示","删除失败");
							}
						}
					});
				}
			});
		}
		
		function updTestDetailInfo(){
			$("#updForm").form('submit',{
				url : webPath + "/CutAndConnOfFiberController/updTestDetailInfo.do",
				onSubmit : function() {
					// do some check    
					// return false to prevent submit;    
				},
				success : function(data) {
					var json=$.parseJSON(data);
					if(json.status){
						$("#win").window('close');
						$("#dg").datagrid('reload');
						$.messager.alert("提示","修改成功");
					}else{
						$.messager.alert("提示","修改失败");
					}
				}
				
			});
		}
		
		function toAddTestDetailInfo(){
			$("#win").window({
				title : "新增详情",
				href : webPath + "/CutAndConnOfFiberController/toAddTestDetailInfo.do?relayinfoId="+$("input[name='relayinfoId']").val(),
				width : 1200,
				height : 400,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		function importExcel(){
			$.messager.confirm('系统提示', '您确定要导入吗?', function(r) {
				if (r) {
					$("#sv").form('submit', {
						url : webPath + "CutAndConnOfFiberController/import_save_tip.do?relayinfoId="+$("input[name='relayinfoId']").val(),
						onSubmit : function() {
							$.messager.progress();
						},
						success : function(data) {
							$('#win').window('close');
							$.messager.progress('close');
							var count = countSubstr(data, "<br>", true);
							var height = 100 * (count / 2);
							if (height < 100) {
								height = 100;
							}
							if (height > 400) {
								height = 400;
							}
							$("#dialog").dialog({
								title: '处理结果',    
							    width: 500,    
							    height: height,    
							    closed: false,    
							    cache: false,    
							    modal: true,
								resizable:true
		                    });
		                    $("#fillData").html(data);
		                    searchData();
						}
					});
				}
			});
		}	
	</script>
</html>
