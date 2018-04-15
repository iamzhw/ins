<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<style>
a{text-decoration:none;}
</style>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<%-- <script type="text/javascript" src="<%=path%>/js/donetask/donetask-index.js"></script> --%>
		<title>专项检查报告</title>
	</head>
	<style type="text/css">
	.div_content{
        display:none;
        position: absolute;
        /* top: 400px; */
        width: 190px;
        margin-left:0px;
        height: 150px;
        margin-top:3px;
        padding: 0;
        background-color: #F5F5F5; 
        z-index:1011;
        overflow: hidden;
        padding-top: 6px;
	}
	.wljb{
		margin-top: 10px;
	}
	.button {
		display: inline-block;
		position: relative;
		margin: 8px;
		margin-left: 14px;
		padding: 0 6px;
		text-align: center;
		text-decoration: none;		
		text-shadow: 1px 1px 1px rgba(255,255,255, .22);
		-webkit-border-radius: 8px;
		-moz-border-radius: 8px;
		border-radius: 8px;
		-webkit-box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);
		-moz-box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);
		box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);

		-webkit-transition: all 0.15s ease;
		-moz-transition: all 0.15s ease;
		-o-transition: all 0.15s ease;
		-ms-transition: all 0.15s ease;
		transition: all 0.15s ease;		
	}
	</style>
	<body style="padding: 3px; border: 0px">
		<input type="hidden" id="ifGLY" />
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 30px;">
				<form id="form" action="" method="post">
					<table class="condition">
						<tr>	
							<td width="10%">地市：</td>
							<td width="20%">
								<div>
									<select name="area_id" id="area_id" class="condition-select" onchange="getSonAreaList()">
										<option value=''>--请选择--</option>																		
										<%-- <c:forEach items="${area}" var="areaInfo"> 
												<option value='${areaInfo.AREA_ID}'>${areaInfo.NAME}</option>										
										</c:forEach> --%>
									</select>
								</div>
							</td>
							<td width="10%">
								区域：
							</td>
							<td width="20%">
								<select name="SON_AREA_ID" id="son_area" class="condition-select" >
									<option value="">
										请选择
									</option>
								</select>
							</td>
							<td width="10%" nowrap="nowrap">检查开始时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" value="" name=START_TIME id="task_start_time" onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">检查完成时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" name=COMPLETE_TIME id="task_end_time" onClick="WdatePicker({minDate:'#F{$dp.$D(\'task_start_time\')}'});" />
								</div>
							</td>
							</td>
						
							<tr>
							
                            <td width="10%" nowrap="nowrap">任务总量开始时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" value="" name=PSTART_TIME id="p_start_time" onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">任务总量结束时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" name=PCOMPLETE_TIME id="p_end_time" onClick="WdatePicker({minDate:'#F{$dp.$D(\'p_start_time\')}'});" />
								</div>
							</td>
							</tr>
								<!--
							<tr>
						
							<td width="10%"  >网络级别：</td>
							<td width="20%"> 
								<input type="hidden" id="wljb_id"  />
								<div class="div1 condition-text-container" style="width:55%" >
									<input type="text" id="wljb_content" class="condition-text" onclick="showDiv()"style="width:97%" />
								</div>	
								<div id="exchange" class="div_content" style="width:155px"  >	
									<input type="checkbox" id="wljb1" class="wljb" name="checkbox" value="80000302">接入层<br/>
									<input type="checkbox" id="wljb2" class="wljb" name="checkbox" value="81538172">网络层<br/>
									<input type="button" id="confirm" class="wljb button " value="确定"onclick=" getChoice()">  <input type="button" id="reset" class="wljb button "value="重置"onclick="removeChoice()"> 		
								</div>	
							</td>
							 -->
							
							</tr>
						</tr>
						
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div style="float: right;" class="btn-operation" onClick="reform()">重置</div>
				<div style="float: right;" class="btn-operation" onClick="exportExcel()">导出</div>
				<div style="float: right;" class="btn-operation" onClick="searchData()">查询</div>
				<input name="area_id" type="hidden" value="" />
			</div>
			<div class="btn-operation-container" id = "initbutton" >
				<div style="float: right;" class="btn-operation" onClick="exportExcelByCity()">汇总导出</div>
				<div style="float: right;" class="btn-operation" onClick="searchDataByCity()">各地市汇总</div>
			</div>
		</div>
		<table id="dg" title="【专项检查报告】" style="width: 100%; height: 480px"></table>
	
<div id="keyPointsArrivaled"></div>
		
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
		/*initbutton();*/
		initParameters();
		getAreaList();
		/* searchData(); */
		});
		function initbutton(){
		var isAdmin1 ='${CABLE_ADMIN}';
		if(true ==isAdmin1 || isAdmin1 =='true'){
		
		$("#initbutton").show();
					
				}
		
		};

		function showDiv(){
			$('#exchange').toggle();
		}
		function getChoice(){
			var arr=document.getElementsByName("checkbox");
			var values="";
			var contents="";
			for(i=0;i<arr.length;i++){
				if(arr[i].checked){
				var value = arr[i].value;
				var content= arr[i].nextSibling.nodeValue;
				values=values+value+",";
				contents=contents+content+",";			
				}
			}
			values=values.substring(0,values.length-1);
			contents=contents.substring(0,contents.length-1);
			$('#wljb_id').val(values);
			$('#wljb_content').val(contents);
			$('#exchange').css('display', 'none');
		    
		}
		function removeChoice(){
			$("[name='checkbox']").removeAttr('checked');
			$('#wljb').val('');
			$('#wljb_content').val('');
		}
		function searchData() {
		    
			if($("#task_start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			var AREA_ID = $("select[name='area_id']").val();
		
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			
			var static_month = $("input[name='static_month']").val();
		
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var son_area = $("#son_area").val();
			
			if(AREA_ID==null || ""==AREA_ID){
				var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
				var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
				var isAdmin1 ='${CABLE_ADMIN}';
				if(true ==isAdmin1 || isAdmin1 =='true'){
					
				}
				if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
				 	 AREA_ID=${areaId};
				}
				if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
				 	 AREA_ID=${areaId};
					 son_area =${sonAreaId};
				}			
			}	


			var WLJB_ID = ''; 
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
				
			var obj ={
					START_TIME : START_TIME,
					COMPLETE_TIME:COMPLETE_TIME,
					AREA_ID:AREA_ID,
					static_month:static_month,
					PSTART_TIME:PSTART_TIME,
					son_area:son_area,
					PCOMPLETE_TIME:PCOMPLETE_TIME,
					WLJB_ID:WLJB_ID
			};
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "SpecialCheckReport/specialCheckReport.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'CITY',
					title : '地市',
					width : "7%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'AREAID',
					title : '区域',
					width : "14%",
					rowspan : '2', 
				    align : 'center',
				    hidden:true
				},{
					field : 'TOWN',
					title : '区域',
					width : "7%",
					rowspan : '2', 
				    align : 'center'
				}, {
					field : '资源整治单已检查',
					title : '资源整治单（已检查）',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '资源整治单发现问题',
					title : '资源整治单（发现问题）',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show1("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '资源整改单总量',
					title : '资源整治单（总量）',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show2("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : 'CHECK_PORT_ZY',
					title : '资源整治单检查端子数',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show40("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : 'WRONG_PORT_ZY',
					title : '资源整治单错误端子数',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show41("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : 'RATE_ZY',
					title : '资源整治单端子准确率',
					width:"6%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : 'FTTH拆机已检查',
					title : 'FTTH拆机（已检查）',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show3("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : 'FTTH拆机发现问题',
					title : 'FTTH拆机（发现问题）',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show4("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'CHECK_PORT_FTTH',
					title : 'FTTH检查端子数',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show42("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'WRONG_PORT_FTTH',
					title : 'FTTH错误端子数',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show43("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'RATE_FTTH',
					title : 'FTTH端子准确率',
					width : "6%",
					rowspan : '2', 
					align : 'center'
				},{
					field : 'IOM拆机已检查',
					title : 'IOM拆机（已检查）',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show5("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'IOM拆机发现问题',
					title : 'IOM拆机（发现问题）',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show6("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'CHECK_PORT_IOM',
					title : 'IOM拆机检查端子数',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show44("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'WRONG_PORT_IOM',
					title : 'IOM拆机错误端子数',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show45("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'RATE_IOM',
					title : 'IOM端子准确率',
					width : "6%",
					rowspan : '2', 
					align : 'center'
				},
		        {
					field : 'CHECK_PORT',
					title : '全部检查端子数',
					width:120,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
				if (row.AREAID != undefined){
	        	return "<a href=\"javascript:show46("+ row.AREAID + ");\">"+value+"</a>";
	        	}else{
	        	return value;
	        	}
	        	
	        } 
				}, {
					field : 'RIGHT_PORT',
					title : '全部检查端子准确数',
					width:160,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show47("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : 'WRONG_PORT',
					title : '全部检查端子错误数',
					width:160,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show48("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : 'RATE',
					title : '全部端子检查准确率',
					width:160,
					rowspan : '2', 
					align : 'center'
				}, {
					field : '客响订单已检查',
					title : '客响订单（已检查）',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show7("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单发现问题',
					title : '客响订单（发现问题）',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show8("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单问题1',
					title : '客响订单问题1',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show9("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单问题2',
					title : '客响订单问题2',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show10("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}
				, {
					field : '客响订单问题2',
					title : '客响订单问题2',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show11("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}
				, {
					field : '客响订单问题3',
					title : '客响订单问题3',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show12("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单问题4',
					title : '客响订单问题4',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show13("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单问题5',
					title : '客响订单问题5',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show14("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单问题6',
					title : '客响订单问题6',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show15("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单问题7',
					title : '客响订单问题7',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show16("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}
				
			]],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				console.log('obj:'+obj);
					$(this).datagrid("fixRownumber");
					 var rows = $('#dg').datagrid('getRows')//获取当前的数据行
            var zycheck=0,zywrong=0,zyall=0,port_zy=0,wrong_zy=0,
                 ftthcheck=0,ftthwrong=0,ftthport=0,wrong_ftth=0,
                 iomcheck=0,iomwrong=0,iomport=0,wrong_iom=0,
                 dz=0,dzw=0,dzr=0,
                 kxcheck=0,kxwrong=0,kx0=0,kx1=0,kx2=0,kx3=0,kx4=0,kx5=0,kx6=0
                 
                 ;//统计总和
            
            for (var i = 0; i < rows.length; i++) {
                
            	zycheck += rows[i]['资源整治单已检查'];
            	zywrong += rows[i]['资源整治单发现问题'];
            	zyall += rows[i]['资源整改单总量'];
            	port_zy += rows[i]['CHECK_PORT_ZY'];
            	wrong_zy += rows[i]['WRONG_PORT_ZY'];
            	ftthcheck += rows[i]['FTTH拆机已检查'];
            	ftthwrong += rows[i]['FTTH拆机发现问题'];
            	ftthport += rows[i]['CHECK_PORT_FTTH'];
            	wrong_ftth += rows[i]['WRONG_PORT_FTTH'];
            	iomcheck += rows[i]['IOM拆机已检查'];
            	iomwrong += rows[i]['IOM拆机发现问题'];
            	iomport += rows[i]['CHECK_PORT_IOM'];
            	wrong_iom += rows[i]['WRONG_PORT_IOM'];
            	
                dz += rows[i]['CHECK_PORT'];
                dzw += rows[i]['WRONG_PORT'];
                dzr += rows[i]['RIGHT_PORT'];
                kxcheck += rows[i]['客响订单已检查'];
                kxwrong += rows[i]['客响订单发现问题'];
                kx0 += rows[i]['客响订单问题1'];
                kx1 += rows[i]['客响订单问题2'];
                kx2 += rows[i]['客响订单问题3'];
                kx3 += rows[i]['客响订单问题4'];
                kx4 += rows[i]['客响订单问题5'];
                kx5 += rows[i]['客响订单问题6'];
                kx6 += rows[i]['客响订单问题7'];
              
                
            }
            //新增一行显示统计信息
            var dzwc=0 ;  
        	if (dz!=0){
        dzwc=dzr/dz;
        	}
        	 var dzwc_zy=0 ;  
         	if (port_zy!=0){
         		dzwc_zy=(port_zy-wrong_zy)/port_zy;
         	}
         	 var dzwc_ftth=0 ;  
         	if (ftthport!=0){
         		dzwc_ftth=(ftthport-wrong_ftth)/ftthport;
         	}
         	 var dzwc_iom=0 ;  
          	if (iomport!=0){
          		dzwc_iom=(iomport-wrong_iom)/iomport;
          	}
  
           $('#dg').datagrid('appendRow', { itemid: '<b>统计：</b>', CITY:'合计', 资源整治单已检查: zycheck, 资源整治单发现问题: zywrong, 资源整改单总量: zyall, CHECK_PORT_ZY: port_zy, WRONG_PORT_ZY: wrong_zy, FTTH拆机已检查: ftthcheck,FTTH拆机发现问题:ftthwrong,CHECK_PORT_FTTH:ftthport,
        	   WRONG_PORT_FTTH:wrong_ftth,IOM拆机已检查:iomcheck , IOM拆机发现问题:iomwrong,CHECK_PORT_IOM:iomport ,WRONG_PORT_IOM:wrong_iom , 
        	   CHECK_PORT:dz,WRONG_PORT:dzw,RIGHT_PORT:dzr,客响订单已检查:kxcheck ,客响订单发现问题:kxwrong ,
        	   客响订单问题1:kx0,  客响订单问题2:kx1,  客响订单问题3:kx2,  客响订单问题4:kx3,  客响订单问题5:kx4,  客响订单问题6:kx5,  客响订单问题7:kx6,
        	   RATE: Math.floor(dzwc * 10000) / 100+'%',
           RATE_ZY: Math.floor(dzwc_zy * 10000) / 100+'%',
			RATE_FTTH: Math.floor(dzwc_ftth * 10000) / 100+'%',
			RATE_IOM: Math.floor(dzwc_iom * 10000) / 100+'%'});
				}
			});
		};
		
		  
		function initParameters(){//初始化查询参数
		var now = new Date();
		//默认设备变动月份
		var changYear = now.getFullYear();
		var changeMonth = now.getMonth()-1;
		if(changeMonth==0){changeMonth=12;changYear=changYear-1;}
		changeMonth = changeMonth>9?changeMonth:"0"+changeMonth;
		var changeTime = changYear+"-"+changeMonth;
		$("#static_month").val(changeTime);
		//默认派发结束时间
		var pendYear = now.getFullYear();
		var pendMonth = now.getMonth();
		if(pendMonth==0){pendMonth=12;pendYear=pendYear-1;}
		pendMonth = pendMonth>9?pendMonth:"0"+pendMonth;
		 var myDate = new Date(pendYear, pendMonth, 0);
        var pendDay =  myDate.getDate();
		pendDay = pendDay>9?pendDay:"0"+pendDay;
		var pendTime = pendYear+"-"+pendMonth+"-"+pendDay;
	
		$("#p_end_time").val(pendTime);
		//默认派发任务开始时
		var start = new Date();
		var pstartYear = start.getFullYear();
		var pmonth = now.getMonth();
		var pstartMonth = start.getMonth();
		if(pstartMonth==0){pstartMonth=12;pstartYear=pstartYear-1;}
		pstartMonth = pstartMonth>9?pstartMonth:"0"+pstartMonth;
		var pstartDay = '01';
		var pstartTime = pstartYear+"-"+pstartMonth+"-"+pstartDay;
		$("#p_start_time").val(pstartTime);
		//默认任务结束时间
		var endYear = now.getFullYear();
		var endMonth = now.getMonth();
		if(endMonth==0){endMonth=12;endYear=endYear-1;}
		endMonth = endMonth>9?endMonth:"0"+endMonth;
		 var myDate = new Date(endYear, endMonth, 0);
        var endDay =  myDate.getDate();
		endDay = endDay>9?endDay:"0"+endDay;
		var endTime = endYear+"-"+endMonth+"-"+endDay;
		$("#task_end_time").val(endTime);
		//默认任务开始时
		var start = new Date();
		var startYear = start.getFullYear();
		var month = now.getMonth();
		var startMonth = start.getMonth();
		if(startMonth==0){startMonth=12;startYear=startYear-1;}
		startMonth = startMonth>9?startMonth:"0"+startMonth;
		var startDay = '01';
		var startTime = startYear+"-"+startMonth+"-"+startDay;
		$("#task_start_time").val(startTime);
	}
		//获取地市
	function getAreaList() {
		 var areaId=2; 
		 $.ajax({
			type : 'POST',
			url : webPath + "General/getSonAreaList.do",
			data : {
				areaId : areaId
			},
			dataType : 'json',
			success : function(json) 
			{
				var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
				var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
				var isAdmin1 ='${CABLE_ADMIN}';
				var result = json.sonAreaList;
				$("select[name='area_id'] option").remove();
				$("select[name='area_id']").append("<option value=''>请选择</option>");
				if( true ==isAdmin1 || isAdmin1 =='true'){
					for ( var i = 0; i < result.length; i++) {
							$("select[name='area_id']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
						}					
				}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true') {
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${areaId}){
							$("select[name='area_id']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
						}					
					}
				}else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${areaId}){
							$("select[name='area_id']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
						}						
					}
				}	
			}
		});
	}
		
		
		
		     	//获取区域
		function getSonAreaList() {
			var areaId = $("#area_id").val();
			$.ajax({
				type : 'POST',
				url : webPath + "General/getSonAreaList.do",
				data : {
					areaId : areaId
				},
				dataType : 'json',
				success : function(json) 
				{
					/* var result = json.sonAreaList;
					$("select[name='SON_AREA_ID'] option").remove();
					$("select[name='SON_AREA_ID']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
						$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
					} */
					var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
					var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
					var isAdmin1 ='${CABLE_ADMIN}';
					var result = json.sonAreaList;
					$("select[name='SON_AREA_ID'] option").remove();
					$("select[name='SON_AREA_ID']").append("<option value=''>请选择</option>");
					if(true ==isAdmin1 || isAdmin1 =='true'){	
						for ( var i = 0; i < result.length; i++) {					
							$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");												
						}
					}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true'){
						for ( var i = 0; i < result.length; i++) {
							$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");					
						}
					}else{
						for ( var i = 0; i < result.length; i++) {
							if(result[i].AREA_ID==${sonAreaId}){
							$("select[name='SON_AREA_ID'] option").remove();
								$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}						
						}
					}
				}
			});
		}
		
		function reform(){			
			$("input[name='START_TIME']").val('');
			$("select[name='SON_AREA_ID']").val('');
			$("input[name='COMPLETE_TIME']").val('');
			$("select[name='area_id']").val('');
		};
		
		function show(AREAID){
		   var type = 0;
		   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
		function show1(AREAID){
		   var type = 1;
		   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
		function show2(AREAID){
		   var type = 2;
		   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME);
	};
		function show3(AREAID){
		   var type = 3;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show4(AREAID){
		   var type = 4;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show5(AREAID){
		   var type = 5;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show6(AREAID){
		   var type = 6;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show7(AREAID){
		   var type = 7;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show8(AREAID){
		   var type = 8;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show9(AREAID){
		   var type = 9;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show10(AREAID){
		   var type = 10;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show11(AREAID){
		   var type = 11;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show12(AREAID){
		   var type = 12;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show13(AREAID){
		   var type = 13;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show14(AREAID){
		   var type = 14;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show15(AREAID){
		   var type = 15;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show16(AREAID){
		   var type = 16;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
		
	function show40(AREAID){
		   var type = 40;
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?AREAID="+AREAID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show41(AREAID){
		   var type = 41;  
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?AREAID="+AREAID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	
	function show42(AREAID){
		   var type = 42;  
		    var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?AREAID="+AREAID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month+"&WLJB_ID="+WLJB_ID);
	};
	function show43(AREAID){
		   var type = 43;  
		    var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?AREAID="+AREAID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month+"&WLJB_ID="+WLJB_ID);
	};
	function show44(AREAID){
		   var type = 44;  
		    var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?AREAID="+AREAID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month+"&WLJB_ID="+WLJB_ID);
	};
	function show45(AREAID){
		   var type = 45;  
		    var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?AREAID="+AREAID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month+"&WLJB_ID="+WLJB_ID);
	};
	function show46(AREAID){
		   var type = 46;  
		    var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?AREAID="+AREAID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month+"&WLJB_ID="+WLJB_ID);
	};
	function show47(AREAID){
		   var type = 47;  
		    var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?AREAID="+AREAID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month+"&WLJB_ID="+WLJB_ID);
	};
	function show48(AREAID){
		   var type = 48;  
		    var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?AREAID="+AREAID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month+"&WLJB_ID="+WLJB_ID);
	};
	
		
		function exportExcel() {
		
			if($("#task_start_time").val()==''){
					alert("开始日期不能为空！");
					return false;
			}
			if($("#task_end_time").val()==''){
					alert("结束日期不能为空！");
					return false;
			}
			var AREA_ID = $("select[name='area_id']").val();
			var son_area = $("#son_area").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var static_month = $("input[name='static_month']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			
			if(AREA_ID==null || ""==AREA_ID){
				var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
				var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
				var isAdmin1 ='${CABLE_ADMIN}';
				if(true ==isAdmin1 || isAdmin1 =='true'){
					
				}
				if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
				 	 AREA_ID=${areaId};
				}
				if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
				 	 AREA_ID=${areaId};
					 son_area =${sonAreaId};
				}		
			}	
			 window.open(webPath
					+ "SpecialCheckReport/exportExcel.do?AREA_ID="+AREA_ID+"&son_area="+son_area+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month
					+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME+"&WLJB_ID="+WLJB_ID
			); 
		}
		    /* window.open(webPath
					+ "SpecialCheckReport/exportExcel.do"
					+ getParamsForDownloadLocal('form'));
			}  */		
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
				o
						.find("input,select")
						.each(
								function() {
									var o = $(this);
									var tag = this.tagName.toLowerCase();
									var name = o.attr("name");
									if (name) {
										if (tag == "select") {
											if (o.find("option:selected").val() == 'all'
													|| o.find("option:selected")
															.val() == '') {
												res = res + "&" + name + "=";
											} else {
												res = res
														+ "&"
														+ name
														+ "="
														+ o.find("option:selected")
																.val();
											}
	
										} else if (tag == "input") {
											res = res + "&" + name + "=" + o.val();
										}
									}
								});					
				return res;
			}
			
			
			
			
			function searchDataByCity() {
		    
			if($("#task_start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			var AREA_ID = $("select[name='area_id']").val();
		
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			
			var static_month = $("input[name='static_month']").val();
		
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var son_area = $("#son_area").val();
			
			if(AREA_ID==null || ""==AREA_ID){
				var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
				var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
				var isAdmin1 ='${CABLE_ADMIN}';
				if(true ==isAdmin1 || isAdmin1 =='true'){
					
				}
				if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
				 	 AREA_ID=${areaId};
				}
				if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
				 	 AREA_ID=${areaId};
					 son_area =${sonAreaId};
				}			
			}		
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			var obj ={
					START_TIME : START_TIME,
					COMPLETE_TIME:COMPLETE_TIME,
					AREA_ID:AREA_ID,
					static_month:static_month,
					PSTART_TIME:PSTART_TIME,
					son_area:son_area,
					WLJB_ID:WLJB_ID,
					PCOMPLETE_TIME:PCOMPLETE_TIME
			};
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "SpecialCheckReport/specialCheckReportByCity.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'CITY',
					title : '地市',
					width : "7%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'PARENT_AREA_ID',
					title : '区域',
					width : "14%",
					rowspan : '2', 
				    align : 'center',
				    hidden:true
				}, {
					field : '资源整治单已检查',
					title : '资源整治单（已检查）',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show20("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '资源整治单发现问题',
					title : '资源整治单（发现问题）',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show21("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '资源整改单总量',
					title : '资源整治单（总量）',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show22("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : 'CHECK_PORT_ZY',
					title : '资源整治单检查端子数',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show50("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : 'WRONG_PORT_ZY',
					title : '资源整治单错误端子数',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show51("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : 'RATE_ZY',
					title : '资源整治单端子准确率',
					width:"6%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : 'FTTH拆机已检查',
					title : 'FTTH拆机（已检查）',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show23("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : 'FTTH拆机发现问题',
					title : 'FTTH拆机（发现问题）',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show24("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'CHECK_PORT_FTTH',
					title : 'FTTH检查端子数',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show52("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'WRONG_PORT_FTTH',
					title : 'FTTH错误端子数',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show53("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'RATE_FTTH',
					title : 'FTTH端子准确率',
					width : "6%",
					rowspan : '2', 
					align : 'center'
				},{
					field : 'IOM拆机已检查',
					title : 'IOM拆机（已检查）',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show25("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'IOM拆机发现问题',
					title : 'IOM拆机（发现问题）',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show26("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
					
		        } 
				},{
					field : 'IOM拆机总量',
					title : 'IOM（拆机总量）',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show27("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
					
		        } 
				},{
					field : 'CHECK_PORT_IOM',
					title : 'IOM拆机检查端子数',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show54("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'WRONG_PORT_IOM',
					title : 'IOM拆机错误端子数',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show55("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'RATE_IOM',
					title : 'IOM端子准确率',
					width : "6%",
					rowspan : '2', 
					align : 'center'
				},
		        {
					field : 'CHECK_PORT',
					title : '全部检查端子数',
					width:120,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
				if (row.PARENT_AREA_ID != undefined){
	        	return "<a href=\"javascript:show56("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
	        	}else{
	        	return value;
	        	}
	        	
	        } 
				}, {
					field : 'RIGHT_PORT',
					title : '全部检查端子准确数',
					width:160,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show57("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : 'WRONG_PORT',
					title : '全部检查端子错误数',
					width:160,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show58("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : 'RATE',
					title : '全部端子检查准确率',
					width:160,
					rowspan : '2', 
					align : 'center'
				}, {
					field : '客响订单已检查',
					title : '客响订单（已检查）',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show28("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单发现问题',
					title : '客响订单（发现问题）',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show29("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单问题1',
					title : '客响订单问题1',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show30("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单问题2',
					title : '客响订单问题2',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show31("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}
				
				, {
					field : '客响订单问题3',
					title : '客响订单问题3',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show33("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单问题4',
					title : '客响订单问题4',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show34("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单问题5',
					title : '客响订单问题5',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show35("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单问题6',
					title : '客响订单问题6',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show36("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单问题7',
					title : '客响订单问题7',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show37("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '客响订单总量',
					title : '客响订单（总量）',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show38("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}]],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				console.log('obj:'+obj);
					$(this).datagrid("fixRownumber");
					 var rows = $('#dg').datagrid('getRows')//获取当前的数据行
					  var zycheck=0,zywrong=0,zyall=0,port_zy=0,wrong_zy=0,
		                 ftthcheck=0,ftthwrong=0,ftthport=0,wrong_ftth=0,
		                 iomcheck=0,iomwrong=0,iomport=0,wrong_iom=0,
		                 dz=0,dzw=0,dzr=0,
		                 kxcheck=0,kxwrong=0,kx0=0,kx1=0,kx2=0,kx3=0,kx4=0,kx5=0,kx6=0,
		                 iom_all=0,
		                 kx_all=0
		                 ;//统计总和
		            
		            for (var i = 0; i < rows.length; i++) {
		                
		            	zycheck += rows[i]['资源整治单已检查'];
		            	zywrong += rows[i]['资源整治单发现问题'];
		            	zyall += rows[i]['资源整改单总量'];
		            	port_zy += rows[i]['CHECK_PORT_ZY'];
		            	wrong_zy += rows[i]['WRONG_PORT_ZY'];
		            	ftthcheck += rows[i]['FTTH拆机已检查'];
		            	ftthwrong += rows[i]['FTTH拆机发现问题'];
		            	ftthport += rows[i]['CHECK_PORT_FTTH'];
		            	wrong_ftth += rows[i]['WRONG_PORT_FTTH'];
		            	iomcheck += rows[i]['IOM拆机已检查'];
		            	iomwrong += rows[i]['IOM拆机发现问题'];
		            	iomport += rows[i]['CHECK_PORT_IOM'];
		            	wrong_iom += rows[i]['WRONG_PORT_IOM'];
		            	iom_all+=rows[i]['IOM拆机总量'];
		                dz += rows[i]['CHECK_PORT'];
		                dzw += rows[i]['WRONG_PORT'];
		                dzr += rows[i]['RIGHT_PORT'];
		                kxcheck += rows[i]['客响订单已检查'];
		                kxwrong += rows[i]['客响订单发现问题'];
		                kx_all += rows[i]['客响订单总量'];     
		                kx0 += rows[i]['客响订单问题1'];
		                kx1 += rows[i]['客响订单问题2'];
		                kx2 += rows[i]['客响订单问题3'];
		                kx3 += rows[i]['客响订单问题4'];
		                kx4 += rows[i]['客响订单问题5'];
		                kx5 += rows[i]['客响订单问题6'];
		                kx6 += rows[i]['客响订单问题7'];
		              
		                
		            }
		            //新增一行显示统计信息
		            var dzwc=0 ;  
		        	if (dz!=0){
		        dzwc=dzr/dz;
		        	}
		        	 var dzwc_zy=0 ;  
		         	if (port_zy!=0){
		         		dzwc_zy=(port_zy-wrong_zy)/port_zy;
		         	}
		         	 var dzwc_ftth=0 ;  
		         	if (ftthport!=0){
		         		dzwc_ftth=(ftthport-wrong_ftth)/ftthport;
		         	}
		         	 var dzwc_iom=0 ;  
		          	if (iomport!=0){
		          		dzwc_iom=(iomport-wrong_iom)/iomport;
		          	}
		  
		           $('#dg').datagrid('appendRow', { itemid: '<b>统计：</b>', CITY:'合计', 资源整治单已检查: zycheck, 资源整治单发现问题: zywrong, 资源整改单总量: zyall, CHECK_PORT_ZY: port_zy, WRONG_PORT_ZY: wrong_zy, FTTH拆机已检查: ftthcheck,FTTH拆机发现问题:ftthwrong,CHECK_PORT_FTTH:ftthport,
		        	   WRONG_PORT_FTTH:wrong_ftth,IOM拆机已检查:iomcheck ,IOM拆机总量:iom_all, IOM拆机发现问题:iomwrong,CHECK_PORT_IOM:iomport ,WRONG_PORT_IOM:wrong_iom , 
		        	   CHECK_PORT:dz,WRONG_PORT:dzw,RIGHT_PORT:dzr,客响订单已检查:kxcheck ,客响订单发现问题:kxwrong ,客响订单总量:kx_all,
		        	   客响订单问题1:kx0,  客响订单问题2:kx1,  客响订单问题3:kx2,  客响订单问题4:kx3,  客响订单问题5:kx4,  客响订单问题6:kx5,  客响订单问题7:kx6,
		        	   RATE: Math.floor(dzwc * 10000) / 100+'%',
		           RATE_ZY: Math.floor(dzwc_zy * 10000) / 100+'%',
					RATE_FTTH: Math.floor(dzwc_ftth * 10000) / 100+'%',
					RATE_IOM: Math.floor(dzwc_iom * 10000) / 100+'%'});
						}
					});
				};
		
		
		function show20(PARENT_AREA_ID){
		   var type = 20;
		   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
		function show21(PARENT_AREA_ID){
		   var type = 21;
		   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
		function show22(PARENT_AREA_ID){
		   var type = 22;
		   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME);
	};
		function show23(PARENT_AREA_ID){
		   var type = 23;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show24(PARENT_AREA_ID){
		   var type = 24;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show25(PARENT_AREA_ID){
		   var type = 25;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show26(PARENT_AREA_ID){
		   var type = 26;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show27(PARENT_AREA_ID){
		   var type = 27;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME);
	};
	function show28(PARENT_AREA_ID){
		   var type = 28;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show29(PARENT_AREA_ID){
		   var type = 29;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show30(PARENT_AREA_ID){
		   var type = 30;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show30(PARENT_AREA_ID){
		   var type = 30;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show31(PARENT_AREA_ID){
		   var type = 31;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show32(PARENT_AREA_ID){
		   var type = 32;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show33(PARENT_AREA_ID){
		   var type = 33;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show34(PARENT_AREA_ID){
		   var type = 34;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show35(PARENT_AREA_ID){
		   var type = 35;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show36(PARENT_AREA_ID){
		   var type = 36;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show37(PARENT_AREA_ID){
		   var type = 37;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show38(PARENT_AREA_ID){
		   var type = 38;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/SpecialCheckReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME);
	};
		
	function show50(PARENT_AREA_ID){
		   var type = 58;
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show51(PARENT_AREA_ID){
		   var type = 51;  
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show52(PARENT_AREA_ID){
		   var type = 52;  
		    var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryDtsj.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month+"&WLJB_ID="+WLJB_ID);
	};
	function show53(PARENT_AREA_ID){
		   var type = 53;
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show54(PARENT_AREA_ID){
		   var type = 54;
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show55(PARENT_AREA_ID){
		   var type = 55;
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show56(PARENT_AREA_ID){
		   var type = 56;
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show57(PARENT_AREA_ID){
		   var type = 57;
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};

	function show58(PARENT_AREA_ID){
		   var type = 58;
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/SpecialCheckReport/queryPort.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
		function exportExcelByCity() {
		
			if($("#task_start_time").val()==''){
					alert("开始日期不能为空！");
					return false;
			}
			if($("#task_end_time").val()==''){
					alert("结束日期不能为空！");
					return false;
			}
			var AREA_ID = $("select[name='area_id']").val();
			var son_area = $("#son_area").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var static_month = $("input[name='static_month']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();

			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			if(AREA_ID==null || ""==AREA_ID){
				var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
				var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
				var isAdmin1 ='${CABLE_ADMIN}';
				if(true ==isAdmin1 || isAdmin1 =='true'){
					
				}
				if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
				 	 AREA_ID=${areaId};
				}
				if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
				 	 AREA_ID=${areaId};
					 son_area =${sonAreaId};
				}		
			}	
			
			 window.open(webPath
					+ "SpecialCheckReport/exportExcelByCity.do?AREA_ID="+AREA_ID+"&son_area="+son_area+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month
					+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME+"&WLJB_ID="+WLJB_ID
			); 
		}
		
		
	</script>
</html>