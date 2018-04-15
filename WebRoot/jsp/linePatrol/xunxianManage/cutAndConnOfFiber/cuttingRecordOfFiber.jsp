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
				<table class="condition">
					<tr>
						<td width="10%" nowrap="nowrap">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name="min_date" id="min_date" 
								onClick="WdatePicker();" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name="max_date" id="max_date"
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'min_date\')}'});" />
							</div>
						</td>
					</tr>
					<tr id="tr_cityName">
						<td width="10%" style="display: none;">地区名：</td>
						<td width="20%" style="display: none;">
							<div>
								<select name="city_name"  class="condition-select" onchange="getCable(this.value)">
									<option value=''>--请选择--</option>
									<c:forEach var="city" items="${cityModel }">
										<option value="${city.AREA_ID }">${city.NAME }</option>
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
							</div></td>
						<td width="10%">中继段名：</td>
						<td width="20%">
							<div>
								<select name="relay_name"  class="condition-select">
									<option value=''>--请选择--</option>
									
								</select>


							</div></td>
					</tr>
				</table>
			</form>
			<div class="btn-operation-container">
			<div style="float:left;" class="btn-operation" onClick="serchData()">查询</div>
			<div style="float:left;" class="btn-operation" onClick="toAddRecordOfFiber()">添加</div>
			<div style="float:left;" class="btn-operation" onClick="addSubExcel()">导入Excel</div>
			<div style="float:left;" class="btn-operation" onClick="exportSubExcel()">导出Excel</div>
		</div>
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
			if("${localId}"){
				getCable("${localId}");
			}else{
				$("#tr_cityName td").css("display",'');
			}
		});
		
		function serchData(){
			var obj=makeParamJson('form');
			$('#dg').datagrid({
				fitColumns:true,
				autoSize : true,
				url : webPath + "/CutAndConnOfFiberController/cuttingRecordOfFiber.do",
				method : 'post',
				queryParams:obj,
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [
					[  
						{field:'ID',title:'',width:50,align:'center',hidden:true},
						{field:'CABLE_NAME',title:'线路名',width:50,align:'center'},
						{field:'RELAY_NAME',title:'中继段名称',width:50,align:'center'},
						{field:'CUTDATE',title:'割接日期',width:50,align:'center'},
						{field:'FIBERMANUFACTURER',title:'原光缆厂家',width:50,align:'center'},
						{field:'FIBERCOREMANUFACTURER',title:'原纤芯厂家',width:50,align:'center'},
						{field:'OLDFIBERXINSP',title:'原光缆纤芯色谱',width:50,align:'center'},
						{field:'PEOPLENUMBER',title:'参加割接总人数',width:50,align:'center'},
						{field:'CURLEADER',title:'割接现场指挥',width:50,align:'center'},
						{field:'detail',title:'',width:50,align:'center',
							formatter:function(value,row,index){
								return "<a href='javascript:void(0)' onclick='showDetail("+row.ID+")'>详情</a>&nbsp;&nbsp;"+
									   "<a href='javascript:void(0)' onclick='delRecordOfFiber("+row.ID+")'>删除</a>&nbsp;&nbsp;"+
									   "<a href='javascript:void(0)' onclick='toUpdRecordOfFiber("+row.ID+")'>修改</a>";
							}	
						},
					]
				],
				onLoadSuccess:function(data){
					
				}
			});
		}
		
		
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
		
		function showDetail(id){
			$('#win').window({
				title : "详情",
				href : webPath+ "/CutAndConnOfFiberController/detailRecordOfFiber.do?id="+id,
				width : 820,
				height : 400,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
// 			location.href = webPath + "/CutAndConnOfFiberController/detailRecordOfFiber.do?id="+id;
		}
		
		
		function delRecordOfFiber(id){
			$.messager.confirm("提示","是否要删除",function(i){
				if(i){
					$.ajax({
						url : webPath + "/CutAndConnOfFiberController/delRecordOfFiber.do",// 跳转到 action    
						data : {
							id : id
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
		
		function toAddRecordOfFiber(){
			$('#win').window({
				title : "添加",
				href : webPath+ "/CutAndConnOfFiberController/toAddRecordOfFiber.do",
				width : 820,
				height : 400,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		
		function toUpdRecordOfFiber(id){
			$('#win').window({
				title : "修改",
				href : webPath+ "/CutAndConnOfFiberController/toUpdRecordOfFiber.do?id="+id,
				width : 820,
				height : 400,
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
				href : webPath+ "/CutAndConnOfFiberController/addSubExcelFiber.do",
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
						url : webPath + "CutAndConnOfFiberController/import_save_tip_Fiber.do",
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
		
		function getParamsForDownloadLocal(idOrDom) {
			if (!idOrDom) {
				return;
			}
			var o;
			if (typeof idOrDom == "string") {
				o = $("#" + idOrDom);
			} else {
				o = $(idOrDom);
			}
			var res = "?randomPara=1";
			o.find("input,select").each(function() {
				var o = $(this);
				var tag = this.tagName.toLowerCase();
				var name = o.attr("name");
				if (name) {
					if (tag == "select") {
						if(o.find("option:selected").val()=='all' || o.find("option:selected").val()=='' ){
							res=res+"&"+name+"=";
						}else{
							res=res+"&"+name+"="+o.find("option:selected").val();
						}
						
					} else if (tag == "input") {
						res=res+"&"+name+"="+ o.val();
					}
				}
			});
			return res;
		}
		
		function exportSubExcel(){
			var rows = $('#dg').datagrid("getRows");
			//var str="";
			if(rows.length<=0){
				$.messager.alert("提示","没有数据可供导出");
				return;
			}
			//for(var i=0;i<rows.length;i++){
			//	str+=rows[i].RELAYID+",";
			//}
			//var index = str.lastIndexOf(",")
			//str=str.substring(0,index);
			//$("input[name='relayinfoids']").val(str);
			window.open(webPath + "/CutAndConnOfFiberController/exportFiberRecordExcel.do"+getParamsForDownloadLocal('form'));
		}
		
	</script>
</html>
