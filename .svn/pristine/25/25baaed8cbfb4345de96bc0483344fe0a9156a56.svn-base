<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<style type="text/css">
        .table_border td
        {
            border-top:1px #DDD solid;
            border-right:1px #DDD solid;
           border-color: #ccc;
        }
        .table_border
        {
         border-bottom:1px #DDD solid;
         border-left:1px #DDD solid;
         border-color:initial;
         width:100%
        }
    
    .label_custom
    {
    	font-weight:blod;
    	font-size: large;
    	background-color: #ebebeb;
    }
</style>
<title>系统参数</title>
</head>
<body>
	<div style="padding:20px 0 10px 10px">
		<table class="condition" id="queryParamId">
			<tr>
				<td width="10%">选择分公司：</td>
				<td width="20%">
					<input type="hidden" name="isAdmin" value="${isAdmin}"/>
					<select name="param_areaId" id="param_areaId"  class="condition-select">
						<option value="">
								全省
							</option>
							<c:forEach items="${areaList}" var="al">
								<option value="${al.AREA_ID}" <c:if test="${al.AREA_ID==param_areaId}">selected</c:if>>
									${al.NAME}
								</option>
							</c:forEach>
					</select>
				</td>
				<td><div class="btn-operation" onClick="searchData()">查询</div></td>
			</tr>
		</table>
		<form id="paramId" >
			<input type="hidden" name="pAreaId" value="${param_areaId}"/>
			<table style="width:40%">
			<tr><td>
				<table class="table_border">
				<tr><td colspan="2" class="label_custom"><label>设置工作时间</label></td></tr>
				<tr>
					<td><label>上午：</label> </td>
					<td>
						<input id="WorkStart" name="WorkStart" value="${WorkStart}" onClick="WdatePicker({dateFmt:'HH:mm'});"/>
						<label>-</label>
						<input id="WorkEnd" name="WorkEnd" value="${WorkEnd}" onClick="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'WorkStart\')}'});"/>
					</td>
				</tr>
				<tr>
					<td><label>下午：</label> </td>
					<td>
						<input id="WorkStart2" name="WorkStart2" value="${WorkStart2}" onClick="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'WorkEnd\')}'});"/>
						<label>-</label>
						<input id="WorkEnd2" name="WorkEnd2" value="${WorkEnd2}" onClick="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'WorkStart2\')}'});"/>
					</td>
				</tr>
				</table>
			</td></tr>
			<tr><td>
			<table class="table_border">
				<tr><td colspan="2" class="label_custom"><label>设置巡检点匹配参数</label></td></tr>
				<tr>
				<td><label>巡检点匹配半径（米）：</label> </td>
				<td>
				<input id="siterange" name="siterange" value="${siterange}"/>
				</td>
				</tr>
			</table>
			</td></tr>
			<tr><td>
			<table class="table_border">
				<tr><td colspan="2" class="label_custom"><label>设置外力施工点匹配参数</label></td></tr>
				<tr>
				<td><label>外力施工点匹配半径（米）：</label> </td>
				<td>
				<input id="OutSiteRange" name="OutSiteRange" value="${OutSiteRange}"/>
				</td>
				</tr>
				<tr>
				<td><label>非“隐患”外力点停留时间（分钟）：</label> </td>
				<td>
				<input id="OutSiteStay" name="OutSiteStay" value="${OutSiteStay}"/>
				</td>
				</tr>
				<tr>
				<td colspan="2"><input type="checkbox" id="OutSiteFile" name="OutSiteFile"  value="Y" <c:if test="${OutSiteFile=='Y'}">checked</c:if> onclick="check()"/>
				<label>非“隐患”外力点是否需要上传图片</label></td>
				</tr>
				<tr>
				<td><label>“隐患”外力点停留时间（分钟）：</label> </td>
				<td>
				<input id="UnSafeOutSiteStay" name="UnSafeOutSiteStay" value="${UnSafeOutSiteStay}"/>
				</td>
				</tr>
				<tr>
				<td colspan="2"><input type="checkbox" id="UnSafeOutSiteFile" name="UnSafeOutSiteFile" value="Y" <c:if test="${UnSafeOutSiteFile=='Y'}">checked</c:if> />
				<label>“隐患”外力点是否需要上传图片</label></td>
				</tr>
				<tr>
			</table>
			</td></tr>
			<tr><td>
			<table class="table_border">
				<tr><td colspan="2" class="label_custom"><label>设置轨迹匹配半径：</label></td></tr>
				<tr>
				<td><label>地标匹配半径（米）：</label> </td>
				<td>
				<input id="LandMarkRange" name="LandMarkRange" value="${LandMarkRange}"/>
				</td>
				</tr>
			</table>
			</td></tr>
			<tr><td>
			<table class="table_border">
				<tr><td colspan="2" class="label_custom"><label>巡线数据采集间隔：</label></td></tr>
				<tr>
				<td><label>时速20公里以下（秒）：</label> </td>
				<td>
				<input id="secondsforwalk" name="secondsforwalk" value="${secondsforwalk}"/>
				</td>
				</tr>
				<tr>
				<td><label>时速20公里以上（秒）：</label> </td>
				<td>
				<input id="secondsfordriving" name="secondsfordriving" value="${secondsfordriving}"/>
				</td>
				</tr>
			</table>
			<%-- <table class="table_border">
				<tr><td colspan="2" class="label_custom"><label>修改步巡周期(月)：</label></td></tr>
				<tr>
				<td><label>一干：</label> </td>
				<td>
				<input id="asteptour" name="asteptour" value="${asteptour}" onChange="judgeCircle()"/>
				</td>
				</tr>
				<tr>
				<td><label>二干：</label> </td>
				<td>
				<input id="twodrysteptour" name="twodrysteptour" value="${twodrysteptour}" onChange="judgeCircle()"/>
				</td>
				</tr>
				<tr>
				<td><label>地标：</label> </td>
				<td>
				<input id="landmarkstep" name="landmarkstep" value="${landmarkstep}"/>
				</td>
				</tr>				
			</table> --%>
			</td></tr>
			</table>
		</form>
		<div style="text-align:center;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>
		</div>
	</div>
	<div id="win_param"></div>
	<script type="text/javascript">
	var landmarkCir= ${landmarkCir};
		
		$(document).ready(function() {
			var isAdmin = $("input[name='isAdmin']").val();
			var param_areaId = $("select[name='param_areaId']").val();
			if(isAdmin ==0){
// 				document.getElementById("queryParamId").style.display="";
// 				if(param_areaId != ""){
// 					$("#landmarkstep").val(landmarkCir);
// 					步巡频次只能省设置
// 					document.getElementById("asteptour").disabled="disabled";
// 					document.getElementById("twodrysteptour").disabled="disabled";
// 					document.getElementById("landmarkstep").disabled="disabled";
// 				}
			}else{
// 				$("#landmarkstep").val(landmarkCir);
				document.getElementById("queryParamId").style.display="none";
				document.getElementById("WorkStart").disabled="disabled";
				document.getElementById("WorkEnd").disabled="disabled";
				document.getElementById("WorkStart2").disabled="disabled";
				document.getElementById("WorkEnd2").disabled="disabled";
				document.getElementById("secondsforwalk").disabled="disabled";
				document.getElementById("secondsfordriving").disabled="disabled";
// 				步巡频次只能省设置
// 				document.getElementById("asteptour").disabled="disabled";
// 				document.getElementById("twodrysteptour").disabled="disabled";
// 				document.getElementById("landmarkstep").disabled="disabled";
			}
		});
	
		function searchData(){
			var param_areaId = $("select[name='param_areaId']").val();
			location.href = "index.do?param_areaId=" + param_areaId;
		}
		
		function saveForm() {
			if ($("#paramId").form('validate')) {
				$.messager.confirm('系统提示', '您确定保存参数吗?', function(r) {
					if (r) {
						var data=makeParamJson('paramId');
						data.OutSiteFile=$("#OutSiteFile:checked").val();
						data.UnSafeOutSiteFile=$("#UnSafeOutSiteFile:checked").val();
						
						$.ajax({
							type : 'POST',
							url : webPath + "Param/save.do",
							data : data,
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '参数保存成功！',
										showType : 'show',
										timeout:'1000'//ms
									   
									});
								}
								else{
									$.messager.alert("提示","参数保存失败！","info");
									return;
								}
							}
						});
					}
				});
			}
		}
				
	</script>
</body>
</html>