
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
		var sblx = $("#sblx").val();
		var equipmentCode = $("#EQUIPMENT_CODE").val();
		var equipmentName = $("#EQUIPMENT_NAME").val();
		var equipmentAddress = $("#EQUIPMENT_ADDRESS").val();
		var ish = $("#ISH").val();
		var isxz = $("#ISXZ").val();
		var contract_person_name = $("#contract_person_name").val();//承包人
		var reformedEquipment='';
		var selectType1 = '';
		var selectType2 = '';
		var selectType3 = '';
	    var wljb = $("#wljb").val();
	    var RES_TYPE_ID = ''; 
		
	   /*  var RES_TYPE_ID1 = '';
		var RES_TYPE_ID2 = '';
		var RES_TYPE_ID3 = '';
		var RES_TYPE_ID4 = '';  */ 
		
		var obj1 = document.getElementsByName('selectType1');
		var obj2 = document.getElementsByName('selectType2');
		var obj3 = document.getElementsByName('selectType3');
		
		var xz=$("#value").val();
		var res_type_id=$("#res_type_id").val();
		if(res_type_id!=""){
			RES_TYPE_ID=RES_TYPE_ID+res_type_id;
		}
	/* 	if(area == ''){
			alert("请选择地市！！！");
			return;
		} */		
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
	   /*  var type1 = document.getElementsByName('RES_TYPE_ID1');
		var type2 = document.getElementsByName('RES_TYPE_ID2');
		var type3 = document.getElementsByName('RES_TYPE_ID3');
		var type4 = document.getElementsByName('RES_TYPE_ID4');  */
						
     	/* for(var i=0;i<type1.length;i++){
			if(type1[i].checked){
				RES_TYPE_ID1+=type1[i].value;
			}
		}
		for(var i=0;i<type2.length;i++){
			if(type2[i].checked){
				RES_TYPE_ID2+=type2[i].value;
			}
		}
		for(var i=0;i<type3.length;i++){
			if(type3[i].checked){
				RES_TYPE_ID3+=type3[i].value;
			}
		}
		for(var i=0;i<type4.length;i++){
			if(type4[i].checked){
				RES_TYPE_ID4+=type4[i].value;
			}
		} */  
		
		
		for(var i=0;i<obj1.length;i++){
			if(obj1[i].checked){
				selectType1+=obj1[i].value;
			}
		}
		for(var i=0;i<obj2.length;i++){
			if(obj2[i].checked){
				selectType2+=obj2[i].value;
			}
		}
		for(var i=0;i<obj3.length;i++){
			if(obj3[i].checked){
				selectType3+=obj3[i].value;
			}
		}

		var obj = {
			dz_start_time:dz_start_time,
			dz_end_time:dz_end_time,
			area:area,
			son_area:son_area,
			whwg:whwg,
			mange_id:mange_id,
			sblx:sblx,
			equipmentCode:equipmentCode,
			equipmentName:equipmentName,
			equipmentAddress:equipmentAddress,
			selectType1:selectType1,
			selectType2:selectType2,
			selectType3:selectType3,
	        ish : ish,
	        isxz:isxz,
	        xz:xz,
	       reformedEquipment:reformedEquipment,
	       contract_person_name:contract_person_name,
		   /*  RES_TYPE_ID1:RES_TYPE_ID1,
			RES_TYPE_ID2:RES_TYPE_ID2,
			RES_TYPE_ID3:RES_TYPE_ID3,
			RES_TYPE_ID4:RES_TYPE_ID4 */
		    RES_TYPE_ID:RES_TYPE_ID,
		    wljb :wljb
		};
		
		if(selectType1 == ''&&selectType2 == ''&&selectType3 == ''){ //无分组
			$('#dg').datagrid(
					{
						//此选项打开，表格会自动适配宽度和高度。
						autoSize : true,
						toolbar : '#tb',
						url : webPath + "CableTaskManger/addTaskQuery.do",
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
									field : 'SBIDS',
									title : '设备ID',
									rowspan : '2',
									checkbox : true
								},
								{
									field : 'AREA',
									title : '地市',
									width : "5%",
									rowspan : '2',
									sortable : true,
									align : 'center'
								},
								{
									field : 'SON_AREA',
									title : '区域',
									width : "5%",
									rowspan : '2',
									sortable : true,
									align : 'center'
								},{
									field : 'EQUIPMENT_NAME',
									title : '设备名称',
									width : "10%",
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
									field : 'WLJB',
									title : '网络级别',
									width : "12%",
									sortable : true,
									rowspan : '2',
									align : 'center'
								},{
									field : 'EQUIPMENT_ADDRESS',
									title : '设备地址',
									width : "12%",
									rowspan : '2',
									sortable : true,
									align : 'center'
								},{
									field : 'ZHHWHWG',
									title : '综合化维护网格',
									width : "8%",
									rowspan : '2',
									sortable : true,
									align : 'center'
								},
								{
									field : 'MANAGE_AREA_ID',
									title : '管理区名称',
									width : "10%",
									rowspan : '2',
									align : 'center',
									sortable : true
								},
								{
									field : 'RES_TYPE',
									title : '设备类型',
									width : "7%",
									rowspan : '2',
									sortable:true,
									align : 'center'
								},{
								field : 'STAFF_NAME',
								title : '检查员',
								width : "7%",
								rowspan : '2',
								sortable:true,
								align : 'center'
							},{
								field : 'RECORD_TYPE',
								title : '是否整改',
								width : "7%",
								rowspan : '2',
								sortable:true,
								align : 'center'
							},
							{
								field : 'CREATE_TIME',
								title : '上次检查时间',
								width : "10%",
								rowspan : '2',
								sortable:true,
								align : 'center'
							},{
									field : 'DZNUM',
									title : '端子变化数',
									width : "7%",
									rowspan : '2',
									align : 'center',
									sortable : true
								},{
									field : 'CONTRACT_PERSION_NAME',
									title : '承包人',
									width : "7%",
									rowspan : '2',
									align : 'center',
									sortable : true
								},{
									field : 'CHAIJINUM',
									title : '拆机数',
									width : "7%",
									rowspan : '2',
									align : 'center',
									sortable : true
								},{
									field : 'ADDNUM',
									title : '新增光路数',
									width : "7%",
									rowspan : '2',
									align : 'center',
									sortable : true
								},{
									field : 'HNUM',
									title : 'H光路数量',
									width : "8%",
									rowspan : '2',
									align : 'center',
									sortable : true
								},{
									field : 'GLNUM',
									title : '涉及光路数',
									width : "8%",
									rowspan : '2',
									align : 'center',
									sortable : true
								},{
									field : 'DATEFROM',
									title : '数据来源',
									width : "8%",
									rowspan : '2',
									align : 'center',
									sortable : true
								},
								{
									field : 'TASKID',
									title : '操作',
									width : "10%",
									rowspan : '2',
									align : 'center',
									formatter : function(value, row, Index) {
										return "<a href=\"javascript:viewDevice('"
												+ row.SBIDS + "');\">查看设备</a>";
									}
								} ] ],
						nowrap : false,
						striped : true,
						onLoadSuccess : function(data) {
							$(this).datagrid("fixRownumber");
							$("body").resize();
						}
					});
		}else{  //分组
			var products = [
			                {productid:'FI-SW-01',name:'Koi'},
			                {productid:'K9-DL-01',name:'Dalmation'},
			                {productid:'RP-SN-01',name:'Rattlesnake'},
			                {productid:'RP-LI-02',name:'Iguana'},
			                {productid:'FL-DSH-01',name:'Manx'},
			                {productid:'FL-DLH-02',name:'Persian'},
			                {productid:'AV-CB-01',name:'Amazon Parrot'}
			            ];
			$('#dg').datagrid(
					{
						//此选项打开，表格会自动适配宽度和高度。
						autoSize : true,
						toolbar : '#tb',
						url : webPath + "CableTaskManger/addTaskQuery.do",
						queryParams : obj,
						method : 'post',
						pagination : true,
						pageNumber : 1,
						pageSize : 10,
						pageList : [ 100000, 500 ],
						//loadMsg:'数据加载中.....',
						rownumbers : true,
						singleSelect : false,
						remoteSort: false,
						fit : true,
						columns : [ [
								{
									field : 'SBIDS',
									title : '设备ID',
									rowspan : '2',
									checkbox : true
								},
								{
									field : 'AREA',
									title : '地市',
									width : "8%",
									rowspan : '2',
									align : 'center'
								},
								{
									field : 'SON_AREA',
									title : '区域',
									width : "8%",
									rowspan : '2',
									align : 'center'
								},
								{
									field : 'ZHHWHWG',
									title : '综合化维护网格',
									width : "12%",
									rowspan : '2',
									align : 'center'
								},
								{
									field : 'MANAGE_AREA_ID',
									title : '管理区编码',
									width : "10%",
									rowspan : '2',
									align : 'center',
									sortable : true
								},
								{
									field : 'RES_TYPE',
									title : '设备类型',
									width : "8%",
									rowspan : '2',
									align : 'center'
								},
								{
									field : 'NUM',
									title : '设备数量',
									width : "8%",
									rowspan : '2',
									align : 'center',
									sortable : true
								},{
									field : 'DZNUM',
									title : '端子变化数',
									width : "8%",
									rowspan : '2',
									align : 'center',
									sortable : true
								},{
									field : 'HNUM',
									title : 'H光路数量',
									width : "8%",
									rowspan : '2',
									align : 'center',
									sortable : true
								},{
									field : 'GLNUM',
									title : '涉及光路数',
									width : "8%",
									rowspan : '2',
									align : 'center',
									sortable : true
								},
								{
									field : 'PFGZ',
									title : '派发规则',
									width : "16%",
									rowspan : '2',
									align : 'center'/* ,
									formatter : function(value, row, Index) {
										return "<select name=\"PFGZ\" id=\"pfgz\" class=\"condition-select\"><option value=\"\">请选择</option><option value=\"0\">派发10%</option></select>";
									} */
								},
								{
									field : 'TASKID',
									title : '操作',
									width : "10%",
									rowspan : '2',
									align : 'center',
									formatter : function(value, row, Index) {
										return "<a href=\"javascript:viewDevice('"
												+ row.SBIDS + "');\">查看设备</a>";
									}
								} ] ],
						nowrap : false,
						striped : true,
						onLoadSuccess : function(data) {
							$(this).datagrid("fixRownumber");
							$("body").resize();
						}
					});
		}
		
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
			/* var pf = $("#pfgz").val(); */
			for ( var i = 0; i < selected.length; i++) {
				var value = selected[i].SBIDS;
				arr[arr.length] = value;
			}
				$('#win_staff').window({
					title : "【选择人员】",
					href : webPath + "CableTaskManger/selectStaff.do?sbIds=" + arr + "&area="+ area + "&son_area="+son_area,
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
	
	function viewDevice(SBIDS){
		myCloseTab();
		var area = $("#area").val();
		var dz_start_time = $("#dz_start_time").val();
		var dz_end_time = $("#dz_end_time").val();
		var  url="<%=path%>/CableTaskManger/viewDevice.do?dz_start_time="+dz_start_time+"&dz_end_time="+dz_end_time+"&area="+area+"&EQUIPMENT_ID="+SBIDS;
		addTab("设备信息", url);
	}
	
	function exportExcel(){
		/* var RES_TYPE_ID1 = '';
		var RES_TYPE_ID2 = '';
		var RES_TYPE_ID3 = '';
		var RES_TYPE_ID4 = '';
		var type1 = document.getElementsByName('RES_TYPE_ID1');
		var type2 = document.getElementsByName('RES_TYPE_ID2');
		var type3 = document.getElementsByName('RES_TYPE_ID3');
		var type4 = document.getElementsByName('RES_TYPE_ID4');  */
		var RES_TYPE_ID='';
		var res_type_id=$("#res_type_id").val();
		if(res_type_id!=""){
			RES_TYPE_ID=RES_TYPE_ID+res_type_id;
		}
	
	    var selectType1 = '';
		var selectType2 = '';
		var selectType3 = '';

		var obj1 = document.getElementsByName('selectType1');
		var obj2 = document.getElementsByName('selectType2');
		var obj3 = document.getElementsByName('selectType3');

	   /*  for(var i=0;i<type1.length;i++){
			if(type1[i].checked){
				RES_TYPE_ID1+=type1[i].value;
			}
		}
		for(var i=0;i<type2.length;i++){
			if(type2[i].checked){
				RES_TYPE_ID2+=type2[i].value;
			}
		}
		for(var i=0;i<type3.length;i++){
			if(type3[i].checked){
				RES_TYPE_ID3+=type3[i].value;
			}
		}
		for(var i=0;i<type4.length;i++){
			if(type4[i].checked){
				RES_TYPE_ID4+=type4[i].value;
			}
		}  */
		for(var i=0;i<obj1.length;i++){
			if(obj1[i].checked){
				selectType1+=obj1[i].value;
			}
		}
		for(var i=0;i<obj2.length;i++){
			if(obj2[i].checked){
				selectType2+=obj2[i].value;
			}
		}
		for(var i=0;i<obj3.length;i++){
			if(obj3[i].checked){
				selectType3+=obj3[i].value;
			}
		}
	
		var ish = $("#ISH").val();
		var isxz = $("#ISXZ").val();
		var dz_start_time = $("#dz_start_time").val();
		var dz_end_time = $("#dz_end_time").val();
		var area = $("#area").val();
		var son_area = $("#son_area").val();
		var whwg = $("#whwg").val();
		var mange_id = $("#mange_id").val();
		var sblx = $("#sblx").val();
		var equipmentCode = $("#EQUIPMENT_CODE").val();
		var xz=$("#value").val();//
		var wljb = $("#wljb").val();
		
	/* if(area == ''){
			alert("请选择地市！！！");
			return;
		} */
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
			+ "CableTaskManger/downTaskQuery.do?selectType1="+selectType1+"&selectType2="+selectType2+"&selectType3="+selectType3+"&ish="+ish+"&isxz="+isxz
			+"&dz_start_time="+dz_start_time+"&dz_end_time="+dz_end_time+"&area="+area+"&son_area="+son_area+"&whwg="+whwg+"&mange_id="+mange_id+"&sblx="+sblx
			+"&equipmentCode="+equipmentCode+"&RES_TYPE_ID="+RES_TYPE_ID+"&xz="+xz+"&wljb="+wljb);					
	}
	
	function exportExcelByPort(){
		
		var dz_start_time = $("#dz_start_time").val();
		var dz_end_time = $("#dz_end_time").val();
		var area = $("#area").val();
		var son_area = $("#son_area").val();
		var whwg = $("#whwg").val();
		var mange_id = $("#mange_id").val();
		var equipmentCode = $("#EQUIPMENT_CODE").val();
		var equipmentName = $("#EQUIPMENT_NAME").val();
		var equipmentAddress = $("#EQUIPMENT_ADDRESS").val();
		var ish = $("#ISH").val();
		var isxz = $("#ISXZ").val();
		var xz=$("#value").val();
		var selectType1 = '';
		var selectType2 = '';
		var selectType3 = '';	
	
		var obj1 = document.getElementsByName('selectType1');
		var obj2 = document.getElementsByName('selectType2');
		var obj3 = document.getElementsByName('selectType3');
		
		var RES_TYPE_ID=$("#res_type_id").val();
		var wljb = $("#wljb").val();
	
		for(var i=0;i<obj1.length;i++){
			if(obj1[i].checked){
				selectType1+=obj1[i].value;
			}
		}
		for(var i=0;i<obj2.length;i++){
			if(obj2[i].checked){
				selectType2+=obj2[i].value;
			}
		}
		for(var i=0;i<obj3.length;i++){
			if(obj3[i].checked){
				selectType3+=obj3[i].value;
			}
		}
		window.open(webPath
			+ "CableTaskManger/downPortQuery.do?selectType1="+selectType1+"&selectType2="+selectType2+"&selectType3="+selectType3+"&ish="+ish+"&isxz="+isxz
			+"&dz_start_time="+dz_start_time+"&dz_end_time="+dz_end_time+"&area="+area+"&son_area="+son_area+"&whwg="+whwg+"&mange_id="+mange_id
			+"&equipmentCode="+equipmentCode+"&equipmentName="+equipmentName+"&equipmentAddress="+equipmentAddress+"&RES_TYPE_ID="+RES_TYPE_ID+"&xz="+xz+"&wljb="+wljb);					
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
							<!-- <td width="25%" align="left">
								<select name="RES_TYPE_ID" id="res_type_id" class="condition-select" multiple="multiple"size="1">
									<option value=""selected="selected">所有</option>						
									<option value="411">ODF</option>
									<option value="703">光交接箱</option>
									<option value="704">光分纤箱</option>
									<option value="414">综合配线箱</option>
								</select>
							</td> -->
						
							<!-- <td width="18%" align="left">
								<div>
									<input type="checkbox" value="411" name="RES_TYPE_ID1"  >ODF
									<input type="checkbox" value="703" name="RES_TYPE_ID2" style="margin-left: 10px;  ">光交接箱
									<input type="checkbox" value="704" name="RES_TYPE_ID3" style="margin-left: 10px;">光分纤箱
								    <input type="checkbox" value="414" name="RES_TYPE_ID4" style="margin-left: 10px;">综合配线箱														
								</div>
							</td> -->
							<td align="left" width="3%">设备只包含H光路：</td>
							<td width="5%" align="left"  >
								<select name="ISH" id="ISH" class="condition-select" style="width:96%" >
									<option value="">请选择</option>
									<option value="0">是</option>
									<option value="1">否</option>
								</select>
							</td>
							<td align="left" width="10%">存量维护类型的设备：</td>
							<td width="10%" align="left">
								<select name="ISXZ" id="ISXZ" class="condition-select" style="width:96%">
									<option value="1">包含</option>
									<option value="0">不包含</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="left" width="1%" >光路性质：</td>
							<td width="6%" align="left" >
								<div class="select_checkBox condition-text-container" style="width:95%">
									<div class="chartQuota" style="width:99%">
										<input type="text" id="toWarning" value="请选择" style="width:97%">
										<input type="hidden" id="value" value="">	
										<input type="hidden" id="content" value="" style="width:97%">		
									</div>
									<div class="chartOptionsFlowTrend"">
											<ul>
												<li class=""><input name="checkboxxz" type="checkbox" value="60"><span>存量光路维护类型</span>
												</li>
												<li class=""><input name="checkboxxz" type="checkbox" value="13"><span>设备入网</span>
												</li>
												<li class=""><input name="checkboxxz"  type="checkbox" value="17"><span>建设单（拆）</span>
												</li>
												<li class=""><input name="checkboxxz" type="checkbox" value="18"><span>建设单（新装）</span>
												</li>
												<li class=""><input name="checkboxxz" type="checkbox" value="10000"><span>人工</span>
												</li>
												<li class=""><input name="checkboxxz" id="qita" type="checkbox" value="qita"><span>其它</span>
												</li>
											</ul>
											<p>
												<a href="#" title="确定" hidefocus="true" class="a_0" onclick="makeSure()"> 确定 </a>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" title="重置" hidefocus="true" class="a_1" onclick="toReset()">重置</a>
											</p>
									</div>
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
					</table>
				</form>
			</div>
			
			<div class="btn-operation-container">
				<input type="checkbox" value="0" name="selectType1" >只看综合维护网格
				<input type="checkbox" value="1" name="selectType2" style="margin-left: 40px;">只看管理区
				<input type="checkbox" value="2" name="selectType3" style="margin-left: 40px;">只看设备类型			
				
				<div style="float:right;margin-right:30px" class="btn-operation" onClick="searchData()">查询</div>
				<div style="float:right;" class="btn-operation" onClick="doTask()">派发</div>
				<div style="float:right;" class="btn-operation" onClick="send_contract_name()">承包人派发</div>
			    <div style="float:right;" class="btn-operation" onClick="exportExcelByPort()">按端子导出</div> 
				<div style="float:right;" class="btn-operation" onClick="exportExcel()">按任务导出</div>
				
			</div>
		</div>
		<table id="dg" title="【任务管理】" style="width: 100%; height: 480px"></table>
		<!-- </div> -->
		<div id="win_staff"></div>
	</body>
</html>
