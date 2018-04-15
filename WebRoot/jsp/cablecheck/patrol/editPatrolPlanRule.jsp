<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/themes/demo.css"/>
<title>编辑关键点计划</title>
<style type="text/css">
body,html,#allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

.condition {
    width: 100%;
    border: 0px solid;
}
*{margin:0;padding:0;list-style-type:none;}
a,img{border:0;}
body{font:12px/180% Arial, Helvetica, sans-serif, "新宋体";}

.selectbox .select-bar{padding:0 20px;}
.selectbox .select-bar select{width:150px;height:200px;border:4px #A0A0A4 outset;padding:4px;}
.selectbox .btn{width:50px; height:30px; margin-top:10px; cursor:pointer;}
</style>
</head>
<body style="padding: 3px; border: 0px">

<div class="easyui-tabs" id="pointTabs" style="margin:auto;width:360px;height:auto;float: left;">
	<div title="填写计划信息" style="padding:10px" data-options="selected:true">
			<form id="form" action="" method="post">
				<input type="hidden" name="plan_id"  />
				<table class="condition" style="width: 100%;">
					
					<tr>
						<td>计划名称：</td>
						<td>
							<div class="condition-text-container">
								<input id="plan_name" name="plan_name" type="text" class="condition-text"  />
							</div>
						</td>
					</tr>
					<tr>
						<td >计划类型：</td>
						<td >
								<input id="plan_type_name" name="plan_type_name" type="text" disabled="disabled" class="condition-text"  />
								<input id="plan_type" name="plan_type" type="hidden" class="condition-text"  />
						</td>
					</tr>
					<tr>
						<td >计划周期：</td>
						<td >
							<select name="plan_circle" class="condition-select">
								<option value="">
									请选择
								</option>
								<option value="1">日</option>
								<option value="2">周</option>
								<option value="5">半月</option>
								<option value="3">月</option>
								<option value="4">年</option>
							</select>
						</td>
					</tr>
					<tr>
						<td >次数：</td>
						<td>
								<!-- <input name="plan_frequency" type="text" class="condition-text" /> -->
								<input name="plan_frequency" class="easyui-numberspinner" value="${PLAN_FREQUENCY}" data-options="increment:1" style="width:155px;"></input>
						</td>
					</tr>
					<tr>
						 <td >开始时间：</td>
							<td>
								<div class="condition-text-container">
								<input class="condition-text condition"
									type="text" name=plan_start_time id="plan_start_time" 
									onClick="WdatePicker();" />
								</div>
							</td>
					</tr>
					<tr>
							<td >结束时间：</td>
							<td>
								<div class="condition-text-container">
								<input class="condition-text condition"
									type="text" name=plan_end_time id="plan_end_time"
									onClick="WdatePicker({minDate:'#F{$dp.$D(\'plan_start_time\')}'});" />
								</div>
							</td>
					</tr>
					<%-- <tr>
							<td >区域：</td>
						<td > 
							<select name="son_area" class="condition-select" onchange="sonAreaChange()">
									<option value="">
										请选择
									</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td>承包人：</td>
						<td>
							<select id="contractor" name="contractor" class="condition-select">
								<option value="">请选择</option>
						    </select>
						    <input id="distribution_amount" value="${distribution_amount}"  name="distribution_amount" type="hidden"
									class="condition-text" />
						</td>
					</tr> --%>
					<input id="distribution_amount_hidden" value="${distribution_amount}"  name="distribution_amount" type="hidden" class="condition-text" />
					<tr id="distribution_amount_tr">
						<td>派发量：</td>
						<td>
							<div class="condition-text-container">
								<input id="distribution_amount_third" value="${distribution_amount}"  name="distribution_amount" type="text" class="condition-text"  style="width:130px;"/>  %
							</div>
						</td>
					</tr>
					<c:if test="${'1'==PLAN_TYPE}">
<%-- 				    	<input id="distribution_amount_third" value="${distribution_amount}"  name="distribution_amount" type="text" class="condition-text"  style="width:130px;"/> --%>
				    	<tr>
							<td>ＡＢ类机房检查周期（月）：</td>
							<td>
								<div class="condition-text-container">
									<input id="FIRST_AB_CYCLE" name="FIRST_AB_CYCLE" type="text" class="condition-text"  />
								</div>
							</td>
						</tr>
						<tr>
							<td>CD类机房检查周期（月）：</td>
							<td>
								<div class="condition-text-container">
									<input id="FIRST_CD_CYCLE" name="FIRST_CD_CYCLE" type="text" class="condition-text"  />
								</div>
							</td>
						</tr>
				    	<tr>
							<td>分纤箱和综合箱检查周期（月）：</td>
							<td>
								<div class="condition-text-container">
									<input id="FIRST_OTHER_CYCLE" name="FIRST_OTHER_CYCLE" type="text" class="condition-text"  />
								</div>
							</td>
						</tr>
				    </c:if>
				    <c:if test="${'2'==PLAN_TYPE}">
				    
				    </c:if>
				    <c:if test="${'3'==PLAN_TYPE}">
				    
				    </c:if>
				    <c:if test="${'4'==PLAN_TYPE}">
				    
				    </c:if>
					<tr>
						<td colspan="2" > 
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

	<script type="text/javascript">
		$(document).ready(function() {
			$("input[name='plan_id']").val('${PLAN_ID}');
			$("input[name='plan_name']").val('${PLAN_NAME}');
			
			$("select[name='plan_circle'] option[value="+'${PLAN_CIRCLE}'+"]").attr("selected",true);
			//$("input[name='plan_frequency']").val('${PLAN_FREQUENCY}');
			$("input[name='plan_start_time']").val('${PLAN_START_TIME}');
			$("input[name='plan_end_time']").val('${PLAN_END_TIME}');
			$("input[name='plan_type']").val('${PLAN_TYPE}');
			var planType = '${PLAN_TYPE}';
			var planTypeName;
			if("1"==planType){
				planTypeName = "承包人检查";
			}else if("2"==planType){
				planTypeName = "网格质量检查员检查";
			}else if("3"==planType){
				planTypeName = "市县公司专业中心";
			}else if("4"==planType){
				planTypeName = "市公司资源中心";
			}
			$("#plan_type_name").val(planTypeName);
			/* val planType = $("input[name='plan_type']").val();
			if("1"==planType){
				$("#plan_type_name").val("承包人检查");
			}else if("2"==planType){
				$("#plan_type_name").val("网格质量检查员检查");
			}else if("3"==planType){
				$("#plan_type_name").val("市县公司专业中心");
			} */
			
			if("3"==planType){
				$("#distribution_amount_tr").show();
			}else{
				$("#distribution_amount_tr").hide();
			}
			$("input[name='distribution_amount']").val('${DISTRIBUTION_AMOUNT}');
		});
		
		function save(){
			var areaId = '${areaId}';
			var plan_id = $.trim($("input[name='plan_id']").val());
			var plan_name = $.trim($("input[name='plan_name']").val());
			var plan_type = $.trim($("input[name='plan_type']").val());
			var plan_circle = $.trim($("select[name='plan_circle']").val());
			var plan_frequency = $.trim($("input[name='plan_frequency']").val());
			var plan_start_time = $.trim($("input[name='plan_start_time']").val());
			var plan_end_time = $.trim($("input[name='plan_end_time']").val());
			//var son_area_id = $.trim($("select[name='son_area']").val());
			
			//var contractor = $.trim($("#contractor").val());
			//var distribution_amount = $.trim($("#distribution_amount").val());
			
			var distribution_amount = "3"==plan_type ? $.trim($("#distribution_amount_third").val()):$.trim($("#distribution_amount_hidden").val());

			var FIRST_AB_CYCLE = $.trim($("input[name='FIRST_AB_CYCLE']").val());
			var FIRST_CD_CYCLE = $.trim($("input[name='FIRST_CD_CYCLE']").val());
			var FIRST_OTHER_CYCLE = $.trim($("input[name='FIRST_OTHER_CYCLE']").val());
			
			if(null==plan_name || ""==plan_name){
				$.messager.show({
					title : '提  示',
					msg : '请填写计划名称!',
					showType : 'show'
				});
				return;
			}
			if(null==plan_type || ""==plan_type){
				$.messager.show({
					title : '提  示',
					msg : '请填选择计划类型!',
					showType : 'show'
				});
				return;
			}
			if(null==plan_circle || ""==plan_circle){
				$.messager.show({
					title : '提  示',
					msg : '请填选择计划周期!',
					showType : 'show'
				});
				return;
			}
			if(null==plan_frequency || ""==plan_frequency){
				$.messager.show({
					title : '提  示',
					msg : '请填写次数!',
					showType : 'show'
				});
				return;
			}
			if(!isPInt(plan_frequency)){
				$.messager.show({
					title : '提  示',
					msg : '次数应为正整数!',
					showType : 'show'
				});
				return;
			}
			if(null==plan_start_time || ""==plan_start_time){
				$.messager.show({
					title : '提  示',
					msg : '开始时间不能为空!',
					showType : 'show'
				});
				return;
			}
			if(null==plan_end_time || ""==plan_end_time){
				$.messager.show({
					title : '提  示',
					msg : '结束时间不能为空!',
					showType : 'show'
				});
				return;
			}
			/* if(null==son_area_id || ""==son_area_id){
				$.messager.show({
					title : '提  示',
					msg : '请选择区域!',
					showType : 'show'
				});
				return;
			} */
			
			/* if (null == contractor || "" == contractor) {
				$.messager.show({
					title : '提  示',
					msg : '请选择承包人!',
					showType : 'show'
				});
				return;
			} */
			
			if(!isPInt(FIRST_AB_CYCLE)){
				$.messager.show({
					title : '提  示',
					msg : 'ＡＢ类机房检查周期是正整数!',
					showType : 'show'
				});
				return;
			}
			
			if(!isPInt(FIRST_CD_CYCLE)){
				$.messager.show({
					title : '提  示',
					msg : 'CD类机房检查周期是正整数!',
					showType : 'show'
				});
				return;
			}
			
			if(!isPInt(FIRST_OTHER_CYCLE)){
				$.messager.show({
					title : '提  示',
					msg : '分纤箱和综合箱检查周期是正整数!',
					showType : 'show'
				});
				return;
			}
			
			$.ajax({
				type : 'POST',
				url : webPath + "/patrolPlan/saveEditPatrolPlanRule.do",
				data : {
					areaId:areaId,
					plan_id:plan_id,
					plan_name:plan_name,
					plan_type:plan_type,
					plan_circle:plan_circle,
					plan_frequency:plan_frequency,
					plan_start_time:plan_start_time,
					plan_end_time:plan_end_time,
					/* son_area_id:son_area_id,
					contractor:contractor, */
					distribution_amount:distribution_amount,
					FIRST_AB_CYCLE : FIRST_AB_CYCLE,
					FIRST_CD_CYCLE : FIRST_CD_CYCLE,
					FIRST_OTHER_CYCLE : FIRST_OTHER_CYCLE
				},
				dataType : 'json',
				success : function(data)
				{
					var resultCode=data.resultCode;
			          if(resultCode=="001"){
			        	  $.messager.show({
							title : '提  示',
							msg : data.resultDesc,
							showType : 'show'
						});
						return;
			          }else{
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
		function isInt(str)
		{
		    var g=/^-?\d+$/;
		    return g.test(str);
		}
		
		function back(){
			location.href = 'index.do';
		}
	</script>
</body>
</html>