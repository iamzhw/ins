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
			<form id="form"  method="post">
				<table class="condition">
					<tr id="tr_cityName">
						<td width="10%">填报单位：</td>
						<td width="20%">
							<div>
								<select name="city_name"  class="condition-select" onchange="getCable(this.value)">
									<c:forEach var="city" items="${cityModel }">
										<option value="${city.AREA_ID }" <c:if test="${city.AREA_ID eq localId }">selected</c:if>>${city.NAME }</option>
									</c:forEach>
								</select>
							</div>
						</td>
						<td width="10%">线路段名：</td>
						<td width="20%">
							<div>
								<select name="cable_name"  class="condition-select" onchange="getRelay(this.value)">
									<option value=''>--请选择--</option>
								</select>
							</div>
						</td>
						<td width="10%">中继段名：</td>
						<td width="20%">
							<div>
								<select name="relay_name"  class="condition-select">
									<option value=''>--请选择--</option>
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td width="10%">报表期间:</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" name="yearPart" type="text" onClick="WdatePicker({skin:'default',dateFmt:'yyyy'});" />
					</div>
				</td>
				<td width="20%">
					<div>
						<select name="upOrDown" class="condition-select">
							<option value='01'>--上半年--</option>
							<option value='02'>--下半年--</option>
						</select>
					</div>
				</td>
					</tr>
				</table>
			</form>
		</div>
	<div class="btn-operation-container">
		<div style="float: left;" class="btn-operation" onClick="serchData()">查询</div>
		<div style="float: left;" class="btn-operation" onClick="delRowsData()">删除</div>
		<div style="float: left;" class="btn-operation" onClick="toUpdReport203()">修改</div>
		<div style="float: left;" class="btn-operation" onClick="toAddReport203()">增加</div>
		<div style="fioat: left;" class="btn-operation" onClick="addSubExcel()">批量倒入</div>
	</div>	
	<div id="dg" style="width: 98%; height: 80%;"></div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<div id="win"></div>
	<div id="dialog">
	  <div id="fillData"></div>
	</div>
</body>
	<script type="text/javascript">
	$(function(){
		if($("select[name='city_name']").val()){
			getCable($("select[name='city_name']").val());
		}
		
		var obj=makeParamJson('form');
		$('#dg').datagrid({
			fitColumns:true,
			autoSize : true,
			queryParams:obj,
			url : webPath + "/CableStatementsController/report203Query.do",
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
					{checkbox:true,field:'ROWINDEX',title:'行号',width:50,align:'center'},
					{field:'CITYKEY',title:'',width:50,align:'center',hidden:true},
					{field:'YEARPART',title:'',width:50,align:'center',hidden:true},
					{field:'NAME',title:'局名',width:30,align:'center'},
					{field:'CABLE_NAME',title:'光缆名',width:50,align:'center'},
					{field:'RELAY_NAME',title:'中继段名',width:50,align:'center'},
					{field:'XINNUMBER',title:'纤芯号',width:50,align:'center'},
					{field:'ONENUMBER',title:'每公里衰耗值dB/Km',width:50,align:'center'},
					{field:'JUNNUMBER',title:'竣工衰耗基准值dB/Km',width:50,align:'center'},
					{field:'ISHEGE',title:'是否合格',width:50,align:'center'}
				]	
			]
//				onLoadSuccess:function(data){
			
//				}
		});
	});
	
		function serchData(){
			var obj=makeParamJson('form');
			$('#dg').datagrid('load',obj);
		}
			
		
		//根据地区获得地区光缆
		function getCable(areaId){
			$("select[name='cable_name']").find("option:not(:first)").remove();
			$("select[name='relay_name']").find("option:not(:first)").remove();
			if(!areaId) return;
			$.ajax({
				url : webPath + "/CutAndConnOfFiberController/getCable.do",// 跳转到 action    
				data : {
					areaId : areaId
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(data) {
					$(data).each(function(){
						$("select[name='cable_name']").append("<option value="+this.CABLE_ID+">"+this.CABLE_NAME+"</option>");
					});
				}
			});
		}
		
		//根据光缆获得中继段
		function getRelay(cableId){
			$("select[name='relay_name']").find("option:not(:first)").remove();
			if(!cableId) return;
			$.ajax({
				url : webPath + "/CutAndConnOfFiberController/getRelay.do",// 跳转到 action    
				data : {
					cableId : cableId
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(data) {
					$(data).each(function(){
						$("select[name='relay_name']").append("<option value="+this.RELAY_ID+">"+this.RELAY_NAME+"</option>");
					});
				}
			});
		}
		
		function delRowsData(){
			var rows=$('#dg').datagrid('getChecked');
			if(!rows.length){
				$.messager.alert("提示","请勾选删除的内容");
				return;
			}
			var arr=new Array();
			for (var i = 0; i < rows.length; i++) {
				var ob=new Object();
				ob.rowIndex=rows[i].ROWINDEX;
				ob.cityKey=rows[i].CITYKEY;
				ob.yearPart=rows[i].YEARPART;
				arr.push(ob);
			}
 			var str=JSON.stringify(arr);
			$.messager.confirm("提示","是否删除",function(i){
				if(i){
					$.ajax({
						url : webPath + "/CableStatementsController/delReport203.do",// 跳转到 action    
						data : {
							"str" : str
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
		
		
		function toUpdReport203(){
			var rows=$('#dg').datagrid('getChecked');
			if(!rows.length){
				$.messager.alert("提示","请勾选修改的内容");
				return;
			}else if(rows.length>1){
				$.messager.alert("提示","只能勾选一条");
				return;
			}
			var arr=new Array();
			for (var i = 0; i < rows.length; i++) {
				var ob=new Object();
				ob.rowIndex=rows[i].ROWINDEX;
				ob.city_name=rows[i].CITYKEY;
				ob.yearPart=rows[i].YEARPART;
				arr.push(ob);
			}
 			var str=JSON.stringify(arr);
 			$('#win').window({
				title : "测试记录修改",
				href : webPath+ "/CableStatementsController/toUpdReport203.do?str="+str,
				width : 900,
				height : 250,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		
		
		function toAddReport203(){
			$('#win').window({
				title : "测试记录新增",
				href : webPath+ "/CableStatementsController/toAddReport203.do?localId="+$("select[name='city_name']").val(),
				width : 900,
				height : 250,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		function addSubExcel() {
	    	$('#win').window({
				title : "导入EXCEL",
				href : webPath+ "/CableStatementsController/addSubExcelFiber.do",
				width : 400,
				height : 250,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		
		function downloadExampleSub(){
			$("#hiddenIframe").attr("src", "./downloadExampleSubFiber.do");
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
		function importExcel(){
			$.messager.confirm('系统提示', '您确定要导入吗?', function(r) {
				if (r) {
					$("#sv").form('submit', {
						url : webPath + "CableStatementsController/import_Date.do",
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
		                    serchData();
						}
					});
				}
			});
		}	
	</script>
</html>
