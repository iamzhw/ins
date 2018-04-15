<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/js/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="<%=path%>/js/themes/demo.css" />
<title>派单关系新增</title>
<style type="text/css">
body, html, #allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

.condition {
	width: 100%;
	border: 0px solid;
}

* {
	margin: 0;
	padding: 0;
	list-style-type: none;
}

a, img {
	border: 0;
}

body {
	font: 12px/180% Arial, Helvetica, sans-serif, "新宋体";
}

.selectbox .select-bar {
	padding: 0 20px;
}

.selectbox .select-bar select {
	width: 150px;
	height: 200px;
	border: 4px #A0A0A4 outset;
	padding: 4px;
}

.selectbox .btn {
	width: 50px;
	height: 30px;
	margin-top: 10px;
	cursor: pointer;
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
	display:block;
	position: relative;
	left:0px;
	margin: 0px;
	clear:both;
}
.chartOptionsFlowTrend li *{
	float:left;
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
.select_checkBox{
	/* border:0px solid red; */
	position: relative;
	display:inline-block;
}	
</style>
</head>
<body style="padding: 30px; border: 0px">

	<div class="easyui-tabs" id="pointTabs"
		style="padding-top:20px;padding-botton:10px;width: 320px; height: auto; margin:auto;">
		<div title="填写派单关系" style="padding: 10px" data-options="selected:true">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition" style="width: 100%;">
					<tr>
						<td>检查实施人类型：</td>
						<td>
							<select id="CHECK_TYPE" name="CHECK_TYPE"
								class="condition-select">
									<option value="">请选择</option>
									<option value="2">网格质量检查员</option>
									<option value="3">市县公司专业中心</option>
									<option value="4">市公司资源中心</option>
							</select>
						</td>
					</tr>
					<tr id="AREA_TR">
						<td>地市：</td>
						<td>
							<select id="show_area" class="condition-select">
									<option value="">
										请选择
									</option>
									<c:forEach items="${areaList}" var="area">
										<option value="${area.AREA_ID}">${area.NAME}</option>
									</c:forEach>
							</select>
							<input type="hidden" id="AREA_ID" name="AREA_ID"/>
						</td>
					</tr>
					<tr id="AREA_QX">
						<td>区县：</td>
						<td>
							<select id="show_area_qx" name="show_area_qx" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
							<input type="hidden" id="AREA_QX" name="AREA_QX"/>
						</td>
					</tr>
					<tr id="Grid_TR">
						<td>网格：</td>
						<td>
							<select id="GRID_ID" name="GRID_ID" class="condition-select">
									<option value="">
										请选择
									</option>
									<%-- <c:forEach items="${areaList}" var="area">
										<option value="${area.AREA_ID}">${area.NAME}</option>
									</c:forEach> --%>
							</select>
						</td>
					</tr>
					
					<tr>
						<td>是否是接单负责人：</td>
						<td>
							<select id="IS_ORDER" name="IS_ORDER"
								class="condition-select">
									<option value="1">是</option>
									<option value="0">否</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>检查实施人：</td>
						<td>
							<input type="text" id="STAFF_NAME"/>
							<input type="hidden" id="STAFF_ID" name="STAFF_ID"/> 
						</td>
					</tr>
					<tr id="MAINTAIN_COMPANY">
						<td>代维公司：</td>
						<td>
							<select id="show_company" name="show_company" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
							<input type="hidden" id="MAINTAIN_COMPANY_ID" name="MAINTAIN_COMPANY"/>
						</td>
					</tr>
					<tr id="BANZU">
						<td>班组权限：</td>
						<td>
							<div class="select_checkBox condition-text-container" >
								<div class="chartQuota">
									<input type="text" id="toWarning" value="请选择" style="width: 150px;font-size: 12px;margin: 0 0 0 -3px;height: 20px;">
									<input type="hidden" id="value" value="">	
									<input type="hidden" id="content" value="" style="width:97%">		
								</div>
								<div class="chartOptionsFlowTrend"">
										<ul name="ul_name">
											<!-- <li class=""><input name="checkboxxz" type="checkbox" value="60"><span>存量光路维护类型</span>
											</li> -->
										</ul>
										<p>
											<a href="#" title="确定" hidefocus="true" class="a_0" onclick="makeSure()"> 确定 </a>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" title="重置" hidefocus="true" class="a_1" onclick="toReset()">重置</a>
										</p>
								</div>
							</div>
						</td>
					
					
						<!-- <td>班组权限：</td>
						<td>
							<select id="show_banzu" name="show_banzu" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
							<input type="hidden" id="BANZU_ID" name="BANZU"/>
						</td> -->
					</tr>
					
					<tr>
						<td colspan="2">
							<div class="btn-operation-container">
								<div style="margin-left: 10%;" class="btn-operation"
									onClick="save()">保存</div>
								<div style="margin-left: 5%;" class="btn-operation"
									onClick="back()">取消</div>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="win_staff"></div>
	<script type="text/javascript">
		$(document).ready(function() {
			// 判断是否是省级管理员
			if("${CABLE_ADMIN}"){
				$("#AREA_ID").val();
				$("#AREA_TR").show();
			}else{
				$("#AREA_ID").val("${areaId}");
				("#AREA_TR").hide();
			}
			
			$(".select_checkBox").hover(function(){
				$(".chartOptionsFlowTrend").css("display","inline-block");
			},function(){
				$(".chartOptionsFlowTrend").css("display","none");
			});
			
			//获取代维公司
			getMainTainCompany();
			
			//根据代维公司id获取班组信息
			$("#show_company").change(function(){
				getBanzuInfo();
			});
			
			$("#show_area").change(function(){
				if(""==$("#CHECK_TYPE").val()){
					$.messager.show({
						title : '提  示',
						msg : '请先选择检查实施人类型!',
						showType : 'show'
					});
					$(this).val("");
					return;
				}else{
					$("#AREA_ID").val($(this).val());
					/* getGridByAREA($(this).val()); */
					getSonAreaList();
				}
			});
			
			$("#show_area_qx").change(function(){
				getGridList();
			});
			
			
			if(""!=$("#CHECK_TYPE").val()&&"2"==$("#CHECK_TYPE").val()){
				$("#Grid_TR").show();
				$("#AREA_QX").show();
			}else{
				$("#Grid_TR").hide();
				$("#AREA_QX").hide();
			}
			$("#CHECK_TYPE").change(function(){
				if(""!=$(this).val()&&"2"==$(this).val()){
					$("#Grid_TR").show();
					$("#AREA_QX").show();
				}else{
					$("#Grid_TR").hide();
					$("#AREA_QX").hide();
				}
			});
			
			$("#STAFF_NAME").click(function(){
				if(""!=$("#AREA_ID").val()){
					$('#win_staff').window({
						title : "【选择检查实施人】",
						href : webPath + "dispatchRelation/check_staff.do?AREA_ID="+$("#AREA_ID").val()+'&SON_AREA_ID='+$("#show_area_qx").val(),
						width : 600,
						height : 600,
						zIndex : 2,
						region : "center",
						collapsible : false,
						cache : false,
						modal : true
					});
				}else{
					$.messager.show({
						title : '提  示',
						msg : '请先选择地市!',
						showType : 'show'
					});
					return;
				}
			});
		});
		
		/* function getGridByAREA(AREA_ID){
			$.ajax({
				type : 'POST',
				url : webPath + "dispatchRelation/getGridListByAreaId.do",
				data : {
					AREA_ID : AREA_ID
				},
				dataType : 'json',
				success : function(json) {
					var result = json.gridList;
					$("select[name='GRID_ID'] option").remove();
					
					$("select[name='GRID_ID']").append("<option value=''>-- 请选择 --</option>");
					
					for (var i = 0; i < result.length; i++) {
						$("select[name='GRID_ID']").append(
								"<option value=" + result[i].GRID_ID + ">"
										+ result[i].GRID_NAME
										+ "</option>");
					}
					
				}
			});
		} */
		
		function save() {
			
			var CHECK_TYPE = $("#CHECK_TYPE").val(),
				AREA_ID = $("#AREA_ID").val(),
				GRID_ID = $("#GRID_ID").val(),
				STAFF_ID = $("#STAFF_ID").val(),
				IS_ORDER = $("#IS_ORDER").val(),
				company_id = $("#show_company").val(), //代维公司id
				team_id = $("#value").val();      //班组id
				
			if (null == CHECK_TYPE || "" == CHECK_TYPE) {
				$.messager.show({
					title : '提  示',
					msg : '请选择检查实施人类型!',
					showType : 'show'
				});
				return;
			}else{
				if("2"==CHECK_TYPE){
					if(""==GRID_ID){
						$.messager.show({
							title : '提  示',
							msg : '请选择网格!',
							showType : 'show'
						});
						return;
					}
				}
			}
			
			if (null == AREA_ID || "" == AREA_ID) {
				$.messager.show({
					title : '提  示',
					msg : '请填选择地市!',
					showType : 'show'
				});
				return;
			}
			if (null == STAFF_ID || "" == STAFF_ID) {
				$.messager.show({
					title : '提  示',
					msg : '请填选择检查实施人!',
					showType : 'show'
				});
				return;
			}
			
			
			$.ajax({
				type : 'POST',
				url : webPath + "/dispatchRelation/saveDispatchRelation.do",
				data : {
					 CHECK_TYPE : CHECK_TYPE,
					
					 AREA_ID : AREA_ID,
					
					 GRID_ID : GRID_ID,
					
					 STAFF_ID : STAFF_ID,
					
					 IS_ORDER : IS_ORDER,
					 
					 company_id :company_id,
					 team_id:team_id
				},
				dataType : 'json',
				success : function(data) 
				{
					var resultCode = data.resultCode;
					if (resultCode == "001") {
						$.messager.show({
							title : '提  示',
							msg : data.resultDesc,
							showType : 'show'
						});
						return;
					} else {
						$.messager.show({
							title : '提  示',
							msg : data.resultDesc,
							showType : 'show'
						});
						location.href = 'index.do';
					}
				}
			});
		}

		//1~100正整数
		function isPInt(str) {
			var g = /^([1-9]\d?|100)$/ ;
				//^[1-9]*[1-9][0-9]*$/;
			return g.test(str);
		}
		//整数
		function isInt(str) {
			var g = /^-?\d+$/;
			return g.test(str);
		}
		
		
		/* function sonAreaChange() {
			// 			searchData();
			getContractorList();
		} */
		function back() {
			location.href = 'index.do';
		}
		
		
		
		//获取区域
		function getSonAreaList() {
			var areaId = $("#show_area").val(); 	
			$.ajax({
				type : 'POST',
				url : webPath + "General/getSonAreaList.do",
				data : {
					areaId : areaId
				},
				dataType : 'json',
				success : function(json) 
				{		
					var result = json.sonAreaList;
					$("select[name='show_area_qx'] option").remove();
					$("select[name='show_area_qx']").append("<option value=''>请选择</option>");
					$("select[name='GRID_ID'] option").remove();
					$("select[name='GRID_ID']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
						$("select[name='show_area_qx']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");					
					}
				}
			});
		}
		//获取综合化维护网格
		function getGridList() {
			var areaId = $("#show_area").val();
			var sonAreaId = $("#show_area_qx").val();
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
					$("select[name='GRID_ID'] option").remove();
					$("select[name='GRID_ID']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
						$("select[name='GRID_ID']").append("<option value=" + result[i].GRID_ID + ">"+ result[i].GRID_NAME + "</option>");
					}
				}
			});
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
					$("ul[name='ul_name'] li").remove();
					for ( var i = 0; i < result.length; i++) {	
						$("ul[name='ul_name']").append(
						"<li class=''><input style='height:22px;' name='checkboxxz' type='checkbox' value="+result[i].TEAM_ID+"><span>"+result[i].TEAM_NAME+"</span></li>");
					}
				}
			});
		}
		
		

		//获取光路性质的下拉框
		function makeSure(){
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
			$('#value').val(values);
			$('#content').val(contents);
		}

		function toReset(){
			$("[name='checkboxxz']").removeAttr('checked');
			$('#value').val('');
			$('#content').val('');
		}
		
	</script>
</body>
</html>