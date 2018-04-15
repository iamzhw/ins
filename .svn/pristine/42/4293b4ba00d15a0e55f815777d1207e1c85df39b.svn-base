<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<style>
a{text-decoration:none;}
</style>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<%-- <script type="text/javascript" src="<%=path%>/js/donetask/donetask-index.js"></script> --%>
		<title>检查质量报告</title>
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
							<td width="10%" nowrap="nowrap">其他任务开始时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" value="" name=START_TIME id="task_start_time" onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">其他任务完成时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" name=COMPLETE_TIME id="task_end_time" onClick="WdatePicker({minDate:'#F{$dp.$D(\'task_start_time\')}'});" />
								</div>
							</td>
							</td>
							<tr>
							<td width="10%">设备变动月份：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input name="static_month" id="static_month"
										onFocus="WdatePicker({dateFmt:'yyyy-MM'})" type="text"
										class="condition-text" />
								</div>
							</td>
                            <td width="10%" nowrap="nowrap">派发设备开始时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" value="" name=PSTART_TIME id="p_start_time" onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">派发设备结束时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" name=PCOMPLETE_TIME id="p_end_time" onClick="WdatePicker({minDate:'#F{$dp.$D(\'p_start_time\')}'});" />
								</div>
							</td>
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
							</tr>
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
		<table id="dg" title="【检查质量报告】" style="width: 100%; height: 480px"></table>
		<div>
<ul>
<li>省公司每月通报数据查询条件如下：比如，5月通报4月的数据，设备变动时间：3月，派发设备时间：3.1-4.30，其他任务时间：4.1-4.30；</li>
<li>设备变动月份控制变动设备下三列，派发设备开始和结束时间控制派发设备下三列，其他任务开始和完成时间控制其他任务设备</li>
<li>端子检查准确率=检查端子正确数/检查端子数</li>
<li>设备检查完成率=(周期性任务检查设备数+二次检查设备数+一键反馈检查设备数+不可预告检查设备数)/变动设备数</li>
<li>周期性任务设备只控制派发时间，统计在派发时间内完成的上月变动设备数，不控制完成时间</li>
<li>注：如果认为周期型设备偏少，先确定设备变动月份和派发时间是否准确，其次确定该时间内派发和完成检查的全是上月变动设备</li>
</ul>
</div>
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
				url : webPath + "CheckQualityReport/checkQualityReport.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'CITYNAME',
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
					field : 'NAME',
					title : '区域',
					width : "7%",
					rowspan : '2', 
				    align : 'center'
				},{title:'变动设备',width:"32%",align:'center',colspan:3},
				{title:'派发设备',width:"48%",align:'center',colspan:3},
				{title:'其他任务设备',width:"60%",align:'center',colspan:3}, {
					field : '检查端子数',
					title : '检查端子数',
					width:"6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show5("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '检查端子错误数',
					title : '检查端子错误数',
					width:"6%",
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
					field : '端子检查准确率',
					title : '端子检查准确率',
					width : "6%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : '设备检查完成率',
					title : '设备检查完成率',
					width : "6%",
					rowspan : '2', 
					align : 'center'
				}
				] ,[{
					field : '变动端子数',
					title : '变动端子数',
					width:80,
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show20("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
					
		        	
		        } 
					
				},{
					field : 'CHANGEEQPNUM',
					title : '变动设备量',
					width:80,
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
					
		        	
		        } 
					
				},{
					field : 'EQPISHNUM',
					title : '只包含H光路的设备数',
					width:160,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show7("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'BILLEQPNUM',
					title : '派发设备数',
					width:80,
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
					field : 'BILLEQPISHNUM',
					title : '派发设备中只包含H光路的设备数',
					width:160,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show8("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : 'CHECKEQPNUM',
					title : '周期性任务检查设备数',
					width:160,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show2("+ row.AREAID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        }	
		        },{
					field : '二次检查设备数',
					title : '二次检查设备数',
					width:120,
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
					field : '一键反馈检查设备数',
					title : '一键反馈检查设备数',
					width:160,
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
					field : '不预告抽查设备数',
					title : '不预告抽查设备数',
					width:160,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.AREAID != undefined){
		        	return "<a href=\"javascript:show4("+ row.AREAID + ");\">"+value+"</a>";
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
            var CHANGEEQPNUM = 0//计算listprice的总和
            ,EQPISHNUM=0,BILLEQPNUM=0,BILLEQPISHNUM=0,CHECKEQPNUM=0,sd=0,yj=0,bk=0,dz=0,dzr=0,dzw=0,dtsj=0;//统计总和
            for (var i = 0; i < rows.length; i++) {
                CHANGEEQPNUM += rows[i]['CHANGEEQPNUM'];
                EQPISHNUM += rows[i]['EQPISHNUM'];
                BILLEQPNUM += rows[i]['BILLEQPNUM'];
                BILLEQPISHNUM += rows[i]['BILLEQPISHNUM'];
                CHECKEQPNUM += rows[i]['CHECKEQPNUM'];
                sd += rows[i]['二次检查设备数'];
                yj += rows[i]['一键反馈检查设备数'];
                bk += rows[i]['不预告抽查设备数'];
                dz += rows[i]['检查端子数'];
                dzw += rows[i]['检查端子错误数'];
                dtsj += rows[i]['变动端子数'];
            }
            //新增一行显示统计信息
            
            var dzwc=(dz-dzw)/dz;
            var sbwc = (CHECKEQPNUM+sd+yj+bk)/CHANGEEQPNUM;
           $('#dg').datagrid('appendRow', { itemid: '<b>统计：</b>', CITYNAME:'合计',变动端子数:  dtsj,CHANGEEQPNUM: CHANGEEQPNUM, EQPISHNUM: EQPISHNUM,  BILLEQPNUM: BILLEQPNUM, BILLEQPISHNUM: BILLEQPISHNUM, CHECKEQPNUM: CHECKEQPNUM, 二次检查设备数: sd, 一键反馈检查设备数: yj, 不预告抽查设备数: bk, 检查端子数: dz, 检查端子错误数: dzw, 端子检查准确率: Math.floor(dzwc * 10000) / 100+'%', 设备检查完成率:Math.floor(sbwc * 10000) / 100+'%'});
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
		var pstartMonth = start.getMonth()-1;
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
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
		};
		function show1(AREAID){
		   var type = 1;
			   var static_month = $("input[name='static_month']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
		function show2(AREAID){
		   var type = 2;
		  var static_month = $("input[name='static_month']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
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
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
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
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show7(AREAID){
		   var type = 5;
			    var static_month = $("input[name='static_month']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show8(AREAID){
		   var type = 6;
			    var static_month = $("input[name='static_month']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show9(AREAID){
		   var type = 7;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?AREAID="+AREAID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show5(AREAID){
		   var type = 0;
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/CheckQualityReport/queryPort.do?AREAID="+AREAID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show6(AREAID){
		   var type = 1;  
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/CheckQualityReport/queryPort.do?AREAID="+AREAID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	
	function show20(AREAID){
		   var type = 2;  
		    var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/CheckQualityReport/queryDtsj.do?AREAID="+AREAID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month+"&WLJB_ID="+WLJB_ID);
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
					+ "CheckQualityReport/exportExcel.do?AREA_ID="+AREA_ID+"&son_area="+son_area+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month
					+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME+"&WLJB_ID="+WLJB_ID
			); 
		}
		    /* window.open(webPath
					+ "CheckQualityReport/exportExcel.do"
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
				url : webPath + "CheckQualityReport/checkQualityReportByCity.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'PARENT_AREA_ID',
					title : '地市',
					width : "14%",
					rowspan : '2', 
				    align : 'center',
				    hidden:true
				},{
					field : 'NAME',
					title : '地市',
					width : "7%",
					rowspan : '2', 
				    align : 'center'
				},{title:'变动设备',width:"32%",align:'center',colspan:3},
				{title:'派发设备',width:"60%",align:'center',colspan:3},
				{title:'其他任务设备',width:"60%",align:'center',colspan:3},{
					field : '检查端子数',
					title : '检查端子数',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show15("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '检查端子错误数',
					title : '检查端子错误数',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show16("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        	
		        } 
				}, {
					field : '端子检查准确率',
					title : '端子检查准确率',
					width : "6%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : '设备检查完成率',
					title : '设备检查完成率',
					width : "6%",
					rowspan : '2', 
					align : 'center'
				}
				] 
				
				
				,[{
					field : '变动端子数',
					title : '变动端子数',
					width:80,
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show21("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
					
		        	
		        } 
					
				},{
					field : 'CHANGEEQPNUM',
					title : '变动设备量',
					width : 80,
					rowspan : '2',
					align : 'center',
					formatter:function(value,row,Index){
						if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show10("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	}
				},{
					field : 'EQPISHNUM',
					title : '只包含H光路的设备数',
					width : 160,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show17("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
					
		        	
		        } 
				}, {
					field : 'BILLEQPNUM',
					title : '派发设备数',
					width : 80,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show11("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
					
		        } 
				}, {
					field : 'BILLEQPISHNUM',
					title : '派发设备中只包含H光路的设备数',
					width : 160,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show18("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
					
		        } 
				},{
					field : 'CHECKEQPNUM',
					title : '周期性任务检查设备数',
					width : 120,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show12("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				},{
					field : '二次检查设备数',
					title : '二次检查设备数',
					width : 100,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show19("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '一键反馈检查设备数',
					title : '一键反馈检查设备数',
					width : 160,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show13("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, {
					field : '不预告抽查设备数',
					title : '不预告抽查设备数',
					width : 160,
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
					
					if (row.PARENT_AREA_ID != undefined){
		        	return "<a href=\"javascript:show14("+ row.PARENT_AREA_ID + ");\">"+value+"</a>";
		        	}else{
		        	return value;
		        	}
		        	
		        } 
				}, ]],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				console.log('obj:'+obj);
					$(this).datagrid("fixRownumber");
					 var rows = $('#dg').datagrid('getRows')//获取当前的数据行
            var CHANGEEQPNUM = 0//计算listprice的总和
            ,EQPISHNUM=0,BILLEQPNUM=0,BILLEQPISHNUM=0,CHECKEQPNUM=0,sd=0,yj=0,bk=0,dz=0,dzr=0,dzw=0,dtsj=0;//统计总和
            for (var i = 0; i < rows.length; i++) {
                CHANGEEQPNUM += rows[i]['CHANGEEQPNUM'];
                EQPISHNUM += rows[i]['EQPISHNUM'];
                BILLEQPNUM += rows[i]['BILLEQPNUM'];
                BILLEQPISHNUM += rows[i]['BILLEQPISHNUM'];
                CHECKEQPNUM += rows[i]['CHECKEQPNUM'];
                sd += rows[i]['二次检查设备数'];
                yj += rows[i]['一键反馈检查设备数'];
                bk += rows[i]['不预告抽查设备数'];
                dz += rows[i]['检查端子数'];
                dzw += rows[i]['检查端子错误数'];
                dtsj += rows[i]['变动端子数'];
            }
            //新增一行显示统计信息
                       
            var dzwc=(dz-dzw)/dz;
            var sbwc = (CHECKEQPNUM+sd+yj+bk)/CHANGEEQPNUM;
           $('#dg').datagrid('appendRow', { itemid: '<b>统计：</b>', NAME:'合计', 变动端子数 : dtsj,CHANGEEQPNUM: CHANGEEQPNUM, EQPISHNUM: EQPISHNUM,  BILLEQPNUM: BILLEQPNUM, BILLEQPISHNUM: BILLEQPISHNUM, CHECKEQPNUM: CHECKEQPNUM, 二次检查设备数: sd, 一键反馈检查设备数: yj, 不预告抽查设备数: bk, 检查端子数: dz, 检查端子错误数: dzw, 端子检查准确率: Math.floor(dzwc * 10000) / 100+'%', 设备检查完成率:Math.floor(sbwc * 10000) / 100+'%'});
					
					
				}
			});
		};
		
		
		function show10(PARENT_AREA_ID){
		   var type = 0;
		   var static_month = $("input[name='static_month']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
		};
		function show11(PARENT_AREA_ID){
		   var type = 1;
			   var static_month = $("input[name='static_month']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
		function show12(PARENT_AREA_ID){
		   var type = 2;
		 var static_month = $("input[name='static_month']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
		function show13(PARENT_AREA_ID){
		   var type = 3;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
		function show14(PARENT_AREA_ID){
		   var type = 4;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show17(PARENT_AREA_ID){
		   var type = 5;
			 var static_month = $("input[name='static_month']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show18(PARENT_AREA_ID){
		   var type = 6;
			   var static_month = $("input[name='static_month']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show19(PARENT_AREA_ID){
		   var type = 7;
			   var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("设备清单", "<%=path%>/CheckQualityReport/queryEqp.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&static_month="+static_month+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show15(PARENT_AREA_ID){
		   var type = 0;
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/CheckQualityReport/queryPort.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show16(PARENT_AREA_ID){
		   var type = 1;  
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/CheckQualityReport/queryPort.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&WLJB_ID="+WLJB_ID);
	};
	function show21(PARENT_AREA_ID){
		   var type = 2;  
		    var static_month = $("input[name='static_month']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var WLJB_ID='';
			var wljb_id=$("#wljb_id").val();
			if(wljb_id!=""){
				WLJB_ID=WLJB_ID+wljb_id;
			}
			addTab("端子清单", "<%=path%>/CheckQualityReport/queryDtsj.do?parent_area_id="+PARENT_AREA_ID+"&type="+type+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month+"&WLJB_ID="+WLJB_ID);
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
					+ "CheckQualityReport/exportExcelByCity.do?AREA_ID="+AREA_ID+"&son_area="+son_area+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month
					+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME+"&WLJB_ID="+WLJB_ID
			); 
		}
		
		
	</script>
</html>