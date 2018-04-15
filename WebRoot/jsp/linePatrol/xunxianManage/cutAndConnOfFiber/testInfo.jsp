
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
			<input type="hidden" name="relayinfoids"/>
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
		</div>
	<div class="btn-operation-container">
		<div style="float: left;" class="btn-operation" onClick="serchData()">查询</div>
		<div style="float: left;" class="btn-operation" onClick="toAddTestInfo()">新增</div>
    	<div style="float: left;" class="btn-operation" onClick="addExcel()">导入Excel</div>
		<div style="float: left;" class="btn-operation" onClick="exportExcel()">导出Excel</div>
	</div>

	<div id="dg" style="width: 98%; height: 80%;"></div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<div id="win"></div>
	<div id="dialog">
	  <div id="fillData"></div>
	</div>
</body>
	<script type="text/javascript">
		function serchData(){
			var obj=makeParamJson('form');
			$('#dg').datagrid({
				fitColumns:true,
				autoSize : true,
				url : webPath + "/CutAndConnOfFiberController/query.do",
				method : 'post',
				queryParams:obj,
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [
					[  
						{field:'RELAYINFOID',title:'',width:30,align:'center',hidden:true},
						{field:'CABLE_NAME',title:'线路名称',width:50,align:'center'},
						{field:'RELAY_NAME',title:'中继段',width:50,align:'center'},
						{field:'RELAYDISTANCE',title:'中继段全长',width:50,align:'center'},
						{field:'TESTDATE',title:'测试日期',width:50,align:'center'},
						{field:'PLACE1',title:'测试地点1',width:50,align:'center'},
						{field:'TESTER1',title:'测试人1',width:50,align:'center'},
						{field:'TESTMETER1',title:'测试仪表1',width:50,align:'center'},
						{field:'PLACE2',title:'测试地点2',width:50,align:'center'},
						{field:'TESTER2',title:'测试人2',width:50,align:'center'},
						{field:'TESTMETER2',title:'测试仪表2',width:50,align:'center'},
						{field:'TESTFORM',title:'测试窗口',width:50,align:'center'},
						{field:'REFRACTION',title:'折射率',width:50,align:'center'},
						{field:'detail',title:'', width:50,align:'center',
							formatter: function(value,row,index){
								return "<a href='javascript:void(0)' onclick='showDetail("+row.RELAYINFOID+")'>详情</a>&nbsp;&nbsp;"+
								"<a href='javascript:void(0)' onclick='delTestInfo("+row.RELAYINFOID+")'>删除</a>&nbsp;&nbsp;"+
								"<a href='javascript:void(0)' onclick='toUpdTestInfo("+row.RELAYINFOID+")'>修改</a>&nbsp;&nbsp;";
							}
						}
					]	
				],
				onLoadSuccess:function(data){

				}
			});
		}
		$(function(){
			if("${localId}"){
				getCable("${localId}");
			}else{
				$("#tr_cityName td").css("display",'');
			}
		});
		
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
		
		//详细信息页面跳转
		function showDetail(relayinfoId){
			
			location.href = webPath + "/CutAndConnOfFiberController/relayDetailInfoInit.do?relayinfoId="+relayinfoId;
			
		}
		//删除测试信息
		function delTestInfo(relayinfoId){
			$.messager.confirm("提示","是否删除",function(i){
				if(i){
					if(relayinfoId){
						$.ajax({
							url : webPath + "/CutAndConnOfFiberController/delTestInfo.do",// 跳转到 action    
							data : {
								relayinfoId : relayinfoId
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
				}
			});
		}
		
		//修改中继段测试记录表信息
		function toUpdTestInfo(relayinfoId){
			$('#win').window({
				title : "中继段测试记录修改",
				href : webPath+ "/CutAndConnOfFiberController/toUpdTestInfo.do?relayinfoId="+relayinfoId,
				width : 820,
				height : 280,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		
		
		function toAddTestInfo(){
			$('#win').window({
				title : "中继段测试记录新增",
				href : webPath+ "/CutAndConnOfFiberController/toAddTestInfo.do",
				width : 900,
				height : 250,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
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
		
		function exportExcel(){
			var rows = $('#dg').datagrid("getRows");
			var str="";
			if(rows.length<=0){
				$.messager.alert("提示","没有数据可供导出");
				return;
			}
			for(var i=0;i<rows.length;i++){
				str+=rows[i].RELAYINFOID+",";
			}
			var index = str.lastIndexOf(",")
			str=str.substring(0,index);
			$("input[name='relayinfoids']").val(str);
			window.open(webPath + "/CutAndConnOfFiberController/exportTestInfoExcel.do"+getParamsForDownloadLocal('form'));
		}
<%--		function addExcel() {--%>
<%--		    var selected = $('#dg').datagrid('getChecked');--%>
<%--		    var RELAYINFOID = selected[0].RELAYINFOID;--%>
<%--		    var count = selected.length;--%>
<%--		    if (count == 0) {--%>
<%--		        $.messager.show({--%>
<%--		            title: '提  示',--%>
<%--		            msg: '请勾选一条数据!',--%>
<%--		            showType: 'show'--%>
<%--		        });--%>
<%--		        return;--%>
<%--		    } else if(count==1){--%>
<%--		    	$('#win').window({--%>
<%--					title : "导入EXCEL",--%>
<%--					href : webPath+ "/CutAndConnOfFiberController/addExcel.do?RELAYINFOID="+RELAYINFOID,--%>
<%--					width : 900,--%>
<%--					height : 250,--%>
<%--					zIndex : 2,--%>
<%--					region : "center",--%>
<%--					collapsible : false,--%>
<%--					cache : false,--%>
<%--					modal : true--%>
<%--				});--%>
<%--		    }--%>
<%--		}--%>
		function addExcel() {
	    	$('#win').window({
				title : "导入EXCEL",
				href : webPath+ "/CutAndConnOfFiberController/addExcel.do",
				width : 400,
				height : 250,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		function downloadDataExample(){
			$("#hiddenIframe").attr("src", "./downloadDataExample.do");
		}
		
		function importUsers(){
			$.messager.confirm('系统提示', '您确定要导入吗?', function(r) {
				if (r) {
					 $('#sv').form('submit', {
		                 url: webPath + "CutAndConnOfFiberController/import_save.do",
		                 onSubmit: function () {
		                     $.messager.progress();
		                 },
		                 success: function (data) {
		                     $.messager.progress('close'); // 如果提交成功则隐藏进度条
		                     var json = eval('(' + data + ')');
		                     if(json.status){
		                    	 $.messager.show({
			                         title: '提  示',
			                         msg: '导入用户成功!',
			                         showType: 'show'
			                     });
		                    	 $('#win').window('close');
		                     } else {
		                    	 $.messager.show({
			                         title: '提  示',
			                         msg: json.message,
			                         showType: 'show',
			                         width : 350,
			                         height : 150
			                     });
		                     }
		                 }
		             });
				}
			});
		}
	</script>
</html>
