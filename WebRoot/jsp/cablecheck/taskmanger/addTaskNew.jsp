
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="../../util/head.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<title>新增任务</title>
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
	.res_type{
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
	
	
	/* 带复选框的下拉框  */
	ul li{
		 list-style: none;
		 padding:0px;
		 margin: 0px;
	}
		
	.select_checkBox{
		border:0px solid red;
		position: relative;
		display:inline-block;
		
		
	}	
	.chartQuota{
		height:23px;
		float:left;
		display:inline-block;
		border:0px solid black;
		position: relative;
	}

	.chartOptionsFlowTrend{
	    z-index:300;
	    background-color:white;
		border:1px solid gray;
		display:none;
		position: absolute;
		left:0px;
		top:23px;
		width:150px;
	}
	.chartOptionsFlowTrend ul{
		float:left;
		padding: 0px;
		margin: 5px;
	}
	.chartOptionsFlowTrend li{
		/* float:left; */
		display:block;
		position: relative;
		left:0px;
		margin: 0px;
		clear:both;
	}
	.chartOptionsFlowTrend li *{
		float:left;
	}
	a:-webkit-any-link {
	color: -webkit-link;
	text-decoration: underline;
	cursor: auto;
	}
	.chartQuota p a {
	float: left;
	height: 21px;
	outline: 0 none;
	border: 1px solid #ccc;
	line-height: 22px;
	padding: 0 5px;
	overflow: hidden;
	background: #eaeaea;
	color: #313131;
	text-decoration: none;
	}	

	.chartQuota p {
	margin:0px;
	folat:left;
	overflow: hidden;
	height: 23px;
	line-height:24px;
	display: inline-block;
	}	
	.chartOptionsFlowTrend p {
	height: 23px;
	line-height: 23px;
	overflow: hidden;
	position: relative;
	z-index: 2;
	background: #fefbf7;
	padding-top: 0px;
	display: inline-block;
	}
	.chartOptionsFlowTrend p a {
	border: 1px solid #fff;
	margin-left: 15px;
	color: #2e91da;
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		getAreaList();
		isSonAreaAdmin();
		getMainTainCompany();
		$("#show_company").change(function(){
			getBanzuInfo();
		});
		/* $('#choices').css('display', 'none'); */
		//获取光路性质的下拉框
		$(".select_checkBox").hover(function(){
			$(".chartOptionsFlowTrend").css("display","inline-block");
		},function(){
			$(".chartOptionsFlowTrend").css("display","none");
		});
	});
	
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
				$("select[name='AREA_ID'] option").remove();
				$("select[name='AREA_ID']").append("<option value=''>请选择</option>");
				if( true ==isAdmin1 || isAdmin1 =='true'){
					for ( var i = 0; i < result.length; i++) {
							$("select[name='AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
						}					
				}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true') {
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${areaId}){
							$("select[name='AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
						}					
					}
				}else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${areaId}){
							$("select[name='AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
						}						
					}
				}	
			}
		});
	}
	
	//获取区域
	function getSonAreaList() {
		 var areaId = $("#area").val(); 	
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
	
	//获取综合化维护网格
	function getGridList() {
		var areaId = $("#area").val();
		var sonAreaId = $("#son_area").val();
		$.ajax({
			type : 'POST',
			url : webPath + "General/getGridList.do",
			data : {
				areaId : areaId,
				sonAreaId : sonAreaId
			},
			dataType : 'json',
			success : function(json) 
			{
				var result = json.gridList;
				$("select[name='WHWG'] option").remove();
				$("select[name='WHWG']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
					$("select[name='WHWG']").append("<option value=" + result[i].GRID_ID + ">"+ result[i].GRID_NAME + "</option>");
				}
			}
		});
	}
	
	function searchData() {
		var dz_start_time = $("#dz_start_time").val();
		var dz_end_time = $("#dz_end_time").val();
		var area = $("#area").val();
		var son_area = $("#son_area").val();
		var whwg = $("#whwg").val();
		var mange_id = $("#mange_id").val();
		var equipmentCode = $("#EQUIPMENT_CODE").val();
		var equipmentName = $("#EQUIPMENT_NAME").val();
		var equipmentAddress = $("#EQUIPMENT_ADDRESS").val();
		var contract_person_name = $("#contract_person_name").val();//承包人
	    var wljb = $("#wljb").val();
	    var show_company = $("#show_company").val();//代维公司
	    var show_banzu = $("#show_banzu").val();//代维班组
	    var order_type = $("#order_type").val();//工单性质
	    var order_from = $("#order_from").val();//工单来源
	    var RES_TYPE_ID = ''; 
		var res_type_id=$("#res_type_id").val();
		if(res_type_id!=""){
			RES_TYPE_ID=RES_TYPE_ID+res_type_id;
		}
		if(area==null || ""==area){
			var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
			var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
			var isAdmin1 ='${CABLE_ADMIN}';
			if(true ==isAdmin1 || isAdmin1 =='true'){
				
			}
			if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
			 	 area=${areaId};
			}
			if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
			 	 area=${areaId};
				 son_area =${sonAreaId};
		    }		    
		}
		var obj = {
			dz_start_time:dz_start_time,
			dz_end_time:dz_end_time,
			area:area,
			son_area:son_area,
			whwg:whwg,
			mange_id:mange_id,
			equipmentCode:equipmentCode,
			equipmentName:equipmentName,
			equipmentAddress:equipmentAddress,
	        contract_person_name:contract_person_name,
		    RES_TYPE_ID:RES_TYPE_ID,
		    wljb :wljb,
		    show_company:show_company,
		    show_banzu:show_banzu,
		    order_type:order_type,
		    order_from:order_from
		};
		
		$('#dg').datagrid(
				{
					//此选项打开，表格会自动适配宽度和高度。
					autoSize : true,
					toolbar : '#tb',
					url : webPath + "CableTaskMangerNew/addTaskQuery.do",
					queryParams : obj,
					method : 'post',
					pagination : true,
					pageNumber : 1,
					pageSize : 10,
					pageList : [200,500,1000,5000,10000, 20000],
					//loadMsg:'数据加载中.....',
					rownumbers : true,
					fit : true,
					singleSelect : false,
					remoteSort: false,
					columns : [ [
							{
								field : 'EQUIPMENT_ID',
								title : '设备ID',
								rowspan : '2',
								checkbox : true
							},{
								field : 'AREA',
								title : '地市',
								width : "5%",
								rowspan : '2',
								sortable : true,
								align : 'center'
							},{
								field : 'SON_AREA',
								title : '区域',
								width : "5%",
								rowspan : '2',
								sortable : true,
								align : 'center'
							},{
								field : 'GRID_NAME',
								title : '综合化维护网格',
								width : "8%",
								rowspan : '2',
								sortable : true,
								align : 'center'
							},{
								field : 'EQUIPMENT_CODE',
								title : '设备编码',
								width : "12%",
								sortable : true,
								rowspan : '2',
								align : 'center'
							},{
								field : 'EQUIPMENT_NAME',
								title : '设备名称',
								width : "10%",
								rowspan : '2',
								sortable : true,
								align : 'center'
							},{
								field : 'RES_TYPE',
								title : '设备类型',
								width : "7%",
								rowspan : '2',
								sortable:true,
								align : 'center'
							},{
								field : 'ADDRESS',
								title : '设备地址',
								width : "12%",
								rowspan : '2',
								sortable : true,
								align : 'center'
							},{
								field : 'WLJB',
								title : '网络级别',
								width : "12%",
								sortable : true,
								rowspan : '2',
								align : 'center'
							},{
								field : 'MANAGE_AREA',
								title : '管理区名称',
								width : "10%",
								rowspan : '2',
								align : 'center',
								sortable : true
							},{
								field : 'CONTRACT_PERSON_NAME',
								title : '承包人',
								width : "7%",
								rowspan : '2',
								align : 'center',
								sortable : true
							},{
								field : 'COMPANY_NAME',
								title : '代维公司',
								width : "7%",
								rowspan : '2',
								align : 'center',
								sortable : true
							},{
								field : 'TEAM_NAME',
								title : '代维班组',
								width : "7%",
								rowspan : '2',
								align : 'center',
								sortable : true
							},{
								field : 'TEAM_ID',
								title : '代维班组ID',
								width : "7%",
								rowspan : '2',
								align : 'center',
								hidden : true
							},{
								field : 'ORDERNUM',
								title : '工单数',
								width : "7%",
								rowspan : '2',
								align : 'center',
								sortable : true
							},{
								field : 'PORTNUM',
								title : '端子数',
								width : "7%",
								rowspan : '2',
								align : 'center',
								sortable : true
							},{
								field : 'TASKID',
								title : '操作',
								width : "10%",
								rowspan : '2',
								align : 'center',
								formatter : function(value, row, Index) {
									return "<a href=\"javascript:viewDevice('"
											+ row.EQUIPMENT_ID+'\',\''+row.TEAM_ID + "');\">详情</a>";
								}
								//'<a href="#" onclick="jsView(\''+row.modelId+'\',\''+row.description+'\')">查看</a>';
							} ] ],
					nowrap : false,
					striped : true,
					onLoadSuccess : function(data) {
						$(this).datagrid("fixRownumber");
						$("body").resize();
					}
				});
		
	}

	function doTask(){
		var selected = $('#dg').datagrid('getChecked');
		var count = selected.length;
		var area = $("#area").val();
		var son_area = $("#son_area").val();
		
		if(son_area == ''){
			alert("请选择区域！！！");
			return;
		}
		
		if (count == 0) {
			$.messager.show({
				title : '提  示',
				msg : '请选择设备!',
				showType : 'show'
			});
			return;
		} else {
			var arr = new Array();
			for ( var i = 0; i < selected.length; i++) {
				//var value = selected[i].EQUIPMENT_ID+'_'+selected[i].TEAM_ID;
				var value = selected[i].EQUIPMENT_ID;
				arr[arr.length] = value;
			}
				$('#win_staff').window({
					title : "【选择人员】",
					href : webPath + "CableTaskMangerNew/selectStaff.do?sbIds=" + arr + "&area="+ area + "&son_area="+son_area,
					width : 860,
					height : 480,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
		}
	}
	//将设备派发给承包人
	function send_contract_name(){
		var dz_start_time = $("#dz_start_time").val();
		var dz_end_time = $("#dz_end_time").val();
		var selected = $('#dg').datagrid('getChecked');
		var count = selected.length;
		if (count == 0) {
			$.messager.show({
				title : '提  示',
				msg : '请选择设备!',
				showType : 'show'
			});
			return;
		} else {
			//判断是否是承包人设备
			var count=0;
			for(var i=0;i<selected.length;i++){
				var value=selected[i].CONTRACT_PERSION_NAME;
				if(value != undefined && value!=""){
					count++;
				}
			}
			if(count<selected.length){
				//所选的设备不全是承包人设备
				$.messager.show({
					title : '提  示',
					msg : '所选设备不全为承包人设备!',
					showType : 'show'
				});
				return;
			}else{
				//所选设备全为承包人设备
				var arr = new Array();			
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].SBIDS;						
					arr[arr.length] = value;
				}
				$.messager.confirm('请确认','是否确认派发？',function(r){
					if(r){
						doShow();
						$.ajax({
								type : 'POST',
								url : webPath + "CableTaskManger/send_contract_name.do?sbIds=" + arr+"&dz_start_time="+ dz_start_time + "&dz_end_time="+dz_end_time,
								dataType : 'json',
								success : function(json) {
									$.messager.progress('close');
									if(json.status){
										$.messager.show({
											title : '提  示',
											msg : '操作成功!',
											showType : 'show'
										});
										searchData();
									} else {
										$.messager.show({
											title : '提  示',
											msg : json.message,
											showType : 'show'
										});
									}
								}
						});						
					}
				});	
			}
									
		}
		
		
		
		
	}
	
	function doShow(){
		$.messager.progress({
			title:'提示',
			msg:'正在派发中,请稍后....',
			interval:500
		});
	}
	
	function myCloseTab() {  
		if(parent.$('#tabs').tabs('exists',"设备信息")){
			parent.$('#tabs').tabs('close',"设备信息");
		}
    } 
	
	function viewDevice(EQUIPMENT_ID,TEAM_ID){
		myCloseTab("设备工单详情");
		var area = $("#area").val();
		var son_area = $("#son_area").val();
		var dz_start_time = $("#dz_start_time").val();
		var dz_end_time = $("#dz_end_time").val();
		var  url="<%=path%>/CableTaskMangerNew/viewDevice.do?dz_start_time="+dz_start_time+"&dz_end_time="+dz_end_time+"&area="+area+"&son_area="+son_area+"&EQUIPMENT_ID="+EQUIPMENT_ID+"&TEAM_ID="+TEAM_ID;
		addTab("设备工单详情", url);
	}
	function myCloseTab(title) {  
		if(parent.$('#tabs').tabs('exists',title)){
			parent.$('#tabs').tabs('close',title);
		}
    } 
	
	function exportExcel(){
		var RES_TYPE_ID='';
		var res_type_id=$("#res_type_id").val();
		if(res_type_id!=""){
			RES_TYPE_ID=RES_TYPE_ID+res_type_id;
		}
	
		var dz_start_time = $("#dz_start_time").val();
		var dz_end_time = $("#dz_end_time").val();
		var area = $("#area").val();
		var son_area = $("#son_area").val();
		var whwg = $("#whwg").val();
		var mange_id = $("#mange_id").val();
		var equipmentCode = $("#EQUIPMENT_CODE").val();
		var equipmentName = $("#EQUIPMENT_NAME").val();
		var equipmentAddress = $("#EQUIPMENT_ADDRESS").val();
		var contract_person_name = $("#contract_person_name").val();//承包人
		var show_company = $("#show_company").val();//代维公司
	    var show_banzu = $("#show_banzu").val();//代维班组
		var wljb = $("#wljb").val();
		if(area==null || ""==area){
			var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
			var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
			var isAdmin1 ='${CABLE_ADMIN}';
			if(true ==isAdmin1 || isAdmin1 =='true'){
				
			}
			if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
			 	 area=${areaId};
			}
			if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
			 	 area=${areaId};
				 son_area =${sonAreaId};
		    }		    
		}
		window.open(webPath
			+ "CableTaskMangerNew/downTaskQuery.do?dz_start_time="+dz_start_time+"&dz_end_time="+dz_end_time+"&area="+area+"&son_area="+son_area+"&whwg="+whwg+"&mange_id="+mange_id
			+"&equipmentCode="+equipmentCode+"&equipmentName="+equipmentName+"&equipmentAddress="+equipmentAddress
			+"&contract_person_name="+contract_person_name+"&RES_TYPE_ID="+RES_TYPE_ID+"&wljb="+wljb+"&show_company="+show_company+"&show_banzu="+show_banzu);					
	}
	
	function exportExcelByPort(){
		var RES_TYPE_ID='';
		var res_type_id=$("#res_type_id").val();
		if(res_type_id!=""){
			RES_TYPE_ID=RES_TYPE_ID+res_type_id;
		}
		var dz_start_time = $("#dz_start_time").val();
		var dz_end_time = $("#dz_end_time").val();
		var area = $("#area").val();
		var son_area = $("#son_area").val();
		var whwg = $("#whwg").val();
		var mange_id = $("#mange_id").val();
		var equipmentCode = $("#EQUIPMENT_CODE").val();
		var equipmentName = $("#EQUIPMENT_NAME").val();
		var equipmentAddress = $("#EQUIPMENT_ADDRESS").val();
		var contract_person_name = $("#contract_person_name").val();//承包人
		var show_company = $("#show_company").val();//代维公司
	    var show_banzu = $("#show_banzu").val();//代维班组
	    var order_type = $("#order_type").val();//工单性质
	    var order_from = $("#order_from").val();//工单来源
		var wljb = $("#wljb").val();
		if(area==null || ""==area){
			var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
			var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
			var isAdmin1 ='${CABLE_ADMIN}';
			if(true ==isAdmin1 || isAdmin1 =='true'){
				
			}
			if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
			 	 area=${areaId};
			}
			if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
			 	 area=${areaId};
				 son_area =${sonAreaId};
		    }		    
		}
	
		window.open(webPath
			+ "CableTaskMangerNew/downPortQuery.do?dz_start_time="+dz_start_time+"&dz_end_time="+dz_end_time+"&area="+area+"&son_area="+son_area+"&whwg="+whwg+"&mange_id="+mange_id
			+"&equipmentCode="+equipmentCode+"&equipmentName="+equipmentName+"&equipmentAddress="+equipmentAddress
			+"&contract_person_name="+contract_person_name+"&RES_TYPE_ID="+RES_TYPE_ID+"&wljb="+wljb+"&show_company="+show_company+"&show_banzu="+show_banzu
			+"&order_type="+order_type+"&order_from="+order_from);					
	}
	
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
		$('#res_type_id').val(values);
		$('#res_type_content').val(contents);
		$('#exchange').css('display', 'none');
	    
	}
	function removeChoice(){
		$("[name='checkbox']").removeAttr('checked');
		$('#res_type_id').val('');
		$('#res_type_content').val('');
	}
	
	function isSonAreaAdmin(){
		//如果是区管理员---保留onfocus事件,则onchange事件删除，这样会导致纯粹的维护员、审核员、巡检员看不到维护网格
		/* if(true =='${CABLE_ADMIN_SONAREA}' || '${CABLE_ADMIN_SONAREA}' =='true'){
			$("#son_area").removeAttr("onchange");
		}else{
			$("#whwg").removeAttr("onfocus");
		} */
		//如果是省管理员或市管理员----保留onchange事件，则onfocus事件删除，这样就可以避免纯粹是维护员、审核员、巡检员看不到维护网格的情况
		if(true == '${CABLE_ADMIN_AREA}'|| '${CABLE_ADMIN_AREA}' =='true'||'${CABLE_ADMIN}'==true||'${CABLE_ADMIN}'=='true'){		
			$("#whwg").removeAttr("onfocus");
		}else{
			$("#son_area").removeAttr("onchange");
		}
	}
	
	//获取光路性质的下拉框
	
	function makeSure(){
		//以为其它选项与别的光路性质不能同时选择  其它选项只能单选
		var qiTa=$('#qita').val();
		$('#toWarning').css('display','none');
		$("#content").prop('type','text'); 
		var arr=document.getElementsByName("checkboxxz");
		var values="";
		var contents="";
		for(var i=0;i<arr.length;i++){
			if(arr[i].checked){
			var value = arr[i].value;			
			var content= arr[i].nextSibling.innerText; 
			values=values+value+",";
			contents=contents+content+",";			
			}
		}
		values=values.substring(0,values.length-1);
		contents=contents.substring(0,contents.length-1);
		
		//选判断选取的值中是否含有 qita ,在判断 值是否等于 qita 如果等于只选取了qita，不等于的话既选取了qita,有选择了别的
		if(values.indexOf(qiTa)>0){
			if(values!=qiTa){
				$("[name='checkboxxz']").removeAttr('checked');
				$('#value').val('');
				$('#content').val('');
				alert('其它选项只能单选!');
				return;
			}			
		}else{
			$('#value').val(values);
			$('#content').val(contents);
		}
	}

	function toReset(){
		$("[name='checkboxxz']").removeAttr('checked');
		$('#value').val('');
		$('#content').val('');

	}
	
	function getMainTainCompany(){
		$.ajax({
			type : 'POST',
			url : webPath + "General/getMainTainCompany.do",
			/* data : {}, */
			dataType : 'json',
			success : function(json) 
			{		
				var result = json.mainTainCompany;
				$("select[name='show_company'] option").remove();
				$("select[name='show_company']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
					$("select[name='show_company']").append("<option value=" + result[i].COMPANY_ID + ">"+ result[i].COMPANY_NAME + "</option>");					
				}
			}
		});
	}
	
	function getBanzuInfo(){
		var companyId = $("#show_company").val();
		$.ajax({
			type : 'POST',
			url : webPath + "General/getBanzuByCompanyId.do",
			data : {
				companyId:companyId
			},
			dataType : 'json',
			success : function(json) 
			{		
				var result = json.banzu;
				$("select[name='show_banzu'] option").remove();
				$("select[name='show_banzu']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
					$("select[name='show_banzu']").append("<option value=" + result[i].TEAM_ID + ">"+ result[i].TEAM_NAME + "</option>");					
				}
			}
		});
	}
	
</script>
	<body style="padding: 3px; border: 0px">
		<input type="hidden" id="ifGLY" />
	    <!-- <div id="1" style="width:1750px; height: 818px;overflow: auto;"> -->
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 138px;">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<input type="hidden" name="selected" value="" />
					<table class="condition" style="width:98%;">
						 <tr>
							<td width="3%"  >
								地市：
							</td>
							<td width="20%"  >
								<select name="AREA_ID" id="area" class="condition-select" onchange="getSonAreaList()" style="width:94%">
									<option value="">
										请选择
									</option>
								</select>
							</td>
							<td width="3%"   >
								区域：
							</td>
							<td width="20%" >
								<select name="SON_AREA_ID" id="son_area" class="condition-select" onchange="getGridList()" style="width:96%">
									<option value="">
										请选择
									</option>
								</select>
							</td>
							<td width="19%" >
								所属维护网格：
							</td>
							<td width="20%" >
								<select name="WHWG" id="whwg" class="condition-select" onfocus="getGridList()" style="width:96%">
									<option value="">
										请选择
									</option>
								</select>
							</td>			
						</tr>						
						<tr>
							<td width="1%" nowrap="nowrap"   >
								端子动态开始时间：
							</td>
							<td width="6%" >
								<div class="condition-text-container" style="width:92%">
									<input class="condition-text condition" type="text"
										name="DZ_START_TIME" id="dz_start_time" onClick="WdatePicker();" style="width:97%" />
								</div>
							</td>
							<td width="3%" nowrap="nowrap">
								端子动态结束时间：
							</td>
							<td width="5%" >
								<div class="condition-text-container" style="width:94%"  >
									<input class="condition-text condition" type="text"
										name="DZ_END_TIME" id="dz_end_time" 
										onClick="WdatePicker({minDate:'#F{$dp.$D(\'dz_start_time\')}'});" />
								</div>
							</td>
							<td align="left" width="8%">
								管理区名称：
							</td>
							<td width="10%" align="left">
								<div class="condition-text-container" style="width:92%">
									<input name="MANAGE_AREA_ID" id="mange_id" class="condition-text" style="width:97%" />
								</div>
							</td>
						</tr>
					
						<tr>
							<td align="left" width="1%" >设备编码：</td>
							<td width="6%" align="left" >
								<div class="condition-text-container" style="width:92%">
									<input name="EQUIPMENT_CODE" id="EQUIPMENT_CODE" class="condition-text"style="width:97%"  />
								</div>
							</td>
							<td align="left" width="3%">设备名称：</td>
							<td width="5%" align="left">
								<div class="condition-text-container" style="width:94%">
									<input name="EQUIPMENT_NAME" id="EQUIPMENT_NAME" class="condition-text" />
								</div>
							</td>
							<td align="left" width="8%">设备地址：</td>
							<td width="10%" align="left">
								<div class="condition-text-container" style="width:92%">
									<input name="EQUIPMENT_ADDRESS" id="EQUIPMENT_ADDRESS" class="condition-text"style="width:97%" />
								</div>
							</td>
						</tr>
						<tr>
							<td align="left" width="1%"  >设备类型：</td>
							<td align="left" width="6%"> 
								<input type="hidden" id="res_type_id"  />
								<div class="div1 condition-text-container" style="width:92%" >
									<input type="text" id="res_type_content" class="condition-text" onclick="showDiv()"style="width:97%" />
								</div>	
								<div id="exchange" class="div_content">	
									<input type="checkbox" id="res_type_id1" class="res_type" name="checkbox" value="411">ODF<br/>
									<input type="checkbox" id="res_type_id2" class="res_type" name="checkbox" value="703">光交接箱<br/>
									<input type="checkbox" id="res_type_id3" class="res_type" name="checkbox" value="704">光分纤箱<br/>
									<input type="checkbox" id="res_type_id4" class="res_type" name="checkbox" value="414">综合配线箱<br/>
									<input type="button" id="confirm" class="res_type button " value="确定"onclick=" getChoice()">  <input type="button" id="reset" class="res_type button "value="重置"onclick="removeChoice()"> 		
								</div>	
							</td>
							<td align="left" width="3%">网络级别：</td>
							<td width="5%" align="left">
								<select name="wljb" id="wljb" class="condition-select" style="width:96%">
									<option value="">请选择</option>
									<option value="80000302">接入层</option>
									<option value="81538172">网络层</option>
								</select>
							</td>
							<td align="left" width="3%">
								承包人：
							</td>
							<td width="5%" align="left">
								<div class="condition-text-container" style="width:94%">
									<input name="contract_person_name" id="contract_person_name" class="condition-text" />
								</div>
							</td>
						</tr>
						 <tr>
							<td width="10%">代维公司：</td>
							<td width="20%">
								<select id="show_company" name="show_company" class="condition-select" style="width:96%">
										<option value="">
											请选择
										</option>
								</select>
							</td>
							<td width="10%">代维班组：</td>
							<td width="20%">
								<select id="show_banzu" name="show_banzu" class="condition-select" style="width:96%">
										<option value="">
											请选择
										</option>
								</select>
							</td>
							<td width="10%">工单性质：</td>
							<td width="20%">
								<select id="order_type" name="order_type" class="condition-select" style="width:96%">
										<option value="">
											请选择
										</option>
										<option value="17">
											建设单（拆）
										</option>
										<option value="18">
											建设单（新装）
										</option>
								</select>
							</td>
						</tr>	
						
						<tr>
							<td width="10%">工单来源：</td>
							<td width="20%">
								<select id="order_from" name="order_from" class="condition-select" style="width:96%">
										<option value="">
											请选择
										</option>
										<option value="1">
											综调
										</option>
										<option value="2">
											IOM
										</option>
								</select>
							</td>
						</tr>	
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div style="float:right;" class="btn-operation" onClick="searchData()">查询</div>
				<div style="float:right;" class="btn-operation" onClick="doTask()">派发</div>
			    <div style="float:right;" class="btn-operation" onClick="exportExcelByPort()">按工单导出</div> 
				<div style="float:right;" class="btn-operation" onClick="exportExcel()">按任务导出</div>
				
			</div>
		</div>
		<table id="dg" title="【任务管理】" style="width: 98%; height: 480px"></table>
		<!-- </div> -->
		<div id="win_staff"></div>
	</body>
</html>
