<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!-- <div class="condition-container" style="height:50px;">
		<div class="btn-operation-container">
			<div style="float: left;" class="btn-operation" onClick="history.back()">返回</div>
		</div>
	</div> -->
<form id="updRecordOfFiberForm" method="post">
	<fieldset style="width:750px; margin: 0px auto;">
		<legend>干线光缆割接记录表</legend>
		<input name="id" value="${rows[0].ID }" type="hidden"/>
		<table class="condition" style="width: 100% ;margin: 0px auto;" >
			<tr id="tr_cityName">
				<td width="20%" style="display: none;">地区名：</td>
				<td width="20%" style="display: none;">
					<div>
						<select name="city_name" class="condition-select"
							onchange="getCable(this.value)">
							<option value=''>--请选择--</option>
							<c:forEach var="city" items="${cityModel }">
								<option value="${city.AREA_ID }" <c:if test="${city.AREA_ID eq rows[0].CITYKEY}" >selected</c:if>>${city.NAME }</option>
							</c:forEach>
						</select>
					</div>
				</td>
				<td width="20%" >*干线光缆名称：</td>
				<td width="20%">
					<div>
						<select name="cable_name" class="condition-select easyui-validatebox" data-options="required:true"
							onchange="getRelay(this.value)">
							<option value=''>--请选择--</option>
							<option value='${rows[0].LINEID }' selected="selected">"${rows[0].CABLE_NAME }"</option>
						</select>
					</div>
				</td>
				<td width="20%" >*割接日期：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="cutdate" value="${rows[0].CUTDATE }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" data-options="required:true"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">*中继段：</td>
				<td width="20%">
					<div>
						<select name="relay_name" class="condition-select easyui-validatebox" data-options="required:true">
							<option value=''>--请选择--</option>
							<option value='${rows[0].RELAYID }' selected="selected">"${rows[0].RELAY_NAME }"</option>
						</select>
					</div>
				</td>
				<td width="20%">光缆结构：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="fiberjg" value="${rows[0].FIBERJG }" readonly="readonly"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%" >原光缆厂家：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="oldfibername" value="${rows[0].OLDFIBERNAME }" readonly="readonly"/>
					</div>
				</td>
				<td width="20%" >机务人员开始调度：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="changedate" value="${rows[0].CHANGEDATE }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">原光缆色谱：</td>
				<td width="20%">
					<div class="condition-text-container">
						<%-- <input class="condition-text condition" type="text" name="fiberspectrum" value="${rows[0].FIBERSPECTRUM }" readonly="readonly"/> --%>
					</div>
				</td>
				<td width="20%">原即时长度：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="fiberlengthatnow" value="${rows[0].FIBERLENGTHATNOW }" readonly="readonly"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%" >原纤芯厂家：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="oldxinname" value="${row[0].OLDXINNAME }" readonly="readonly"/>
					</div>
				</td>
				<td width="20%" >原纤芯数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="fibercorenum" value="${row[0].FIBERCORENUM }" readonly="readonly"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">原接头号：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="fiberpiecing" value="${rows[0].FIBERPIECING }" readonly="readonly"/>
					</div>
				</td>
				<td width="20%">光缆割接开始时间(断缆)：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="fibercurdate" value="${rows[0].FIBERCURDATE }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">新光缆厂家：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="newfibername" value="${rows[0].NEWFIBERNAME }" />
					</div>
				</td>
				<td width="20%">接续开始时间：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="curstartdate" value="${rows[0].CURSTARTDATE }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">新纤芯厂家：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="newxinname" value="${rows[0].NEWXINNAME }" />
					</div>
				</td>
				<td width="20%">接续完成时间：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="curenddate" value="${rows[0].CURENDDATE }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">原光缆纤芯色谱：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="oldfiberxinsp" value="${rows[0].OLDFIBERXINSP }" />
					</div>
				</td>
				<td width="20%">光缆割接时长：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="datedistain" value="${rows[0].DATEDISTAIN }" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">新光缆纤芯色谱：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="newfiberxinsp" value="${rows[0].NEWFIBERXINSP }" />
					</div>
				</td>
				<td width="20%">所有系统恢复正常时间：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="regaindate" value="${rows[0].REGAINDATE }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">割接前纤芯长度(上次割接后)：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="oldcutoverlength" value="${rows[0].OLDCUTOVERLENGTH }" readonly="readonly"/>
					</div>
				</td>
				<td width="20%">割接前纤芯长度(测试)：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="oldcutoverlength" value="${rows[0].OLDCUTOVERLENGTH }" data-options="validType:'doubleTest'"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">*割接后纤芯长度(全程)：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="newcutoverlength" value="${rows[0].NEWCUTOVERLENGTH }" data-options="required:true,validType:'doubleTest'"/>
					</div>
				</td>
				<td width="20%">接头最小耗损：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="lineminsunhao" value="${rows[0].LINEMINSUNHAO }" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">接头耗损>0.3dB纤芯数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="sunhao3" value="${rows[0].SUNHAO3 }" data-options="validType:'doubleTest'"/>
					</div>
				</td>
				<td width="20%">接头最大耗损：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="linemaxsunhao" value="${rows[0].LINEMAXSUNHAO }" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">割接后纤芯长度变化：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="changelength" value="${rows[0].CHANGELENGTH }" data-options="validType:'doubleTest'" />
					</div>
				</td>
				<td width="20%">接头耗损>0.5dB纤芯数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="sunhao5" value="${rows[0].SUNHAO5 }" data-options="validType:'doubleTest'"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">测试人员：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="tester" value="${rows[0].TESTER }" />
					</div>
				</td>
				<td width="20%">测试时间：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="testdate" value="${rows[0].TESTDATE }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					</div>
				</td>
			</tr>
		</table>
	</fieldset>
	
	<fieldset style="width:750px; margin: 0px auto;">
		<legend>割接人员安排情况</legend>
		<table class="condition" style="width: 100% ;margin: 0px auto;" >
			<tr>
				<td width="20%">*参加割接总人数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="peoplenumber" value="${rows[0].PEOPLENUMBER }" data-options="required:true,validType:'doubleTest'"/>
					</div>
				</td>
				<td width="20%">*割接现场指挥：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="curleader" value="${rows[0].CURLEADER }" data-options="required:true"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">*机务人员名单：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="jwpeoples" value="${rows[0].JWPEOPLES }" data-options="required:true"/>
					</div>
				</td>
				<td width="20%">*本端机房：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="jifang1" value="${rows[0].JIFANG1 }" data-options="required:true"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">机房测试人员名单：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="jwtestpeoples" value="${rows[0].JWTESTPEOPLES }" />
					</div>
				</td>
				<td width="20%">对端机房1：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="jifang2" value="${rows[0].JIFANG1 }" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">割接现场人员总数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="curoverpeoplenumber" value="${rows[0].CUROVERPEOPLENUMBER }" data-options="validType:'doubleTest'" />
					</div>
				</td>
				<td width="20%">对端机房2：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="jifang3" value="${rows[0].JIFANG3 }" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">割接现场人员名单：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="curoverpeoples" value="${rows[0].CUROVERPEOPLES }" />
					</div>
				</td>
				<td width="20%">正式员工人数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="zhenshipeo" value="${rows[0].ZHENSHIPEO }" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">劳务工人数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="raowupeo" value="${rows[0].RAOWUPEO }" />
					</div>
				</td>
				<td width="20%">其它人员数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="qitapeo" value="${rows[0].QITAPEO }" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">*填表人：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="tianbiaoren" value="${rows[0].TIANBIAOREN }" />
					</div>
				</td>
				<td width="20%">本端机房名称：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="jifangname" value="${rows[0].JIFANGNAME }"/>
					</div>
				</td>
			</tr>
		</table>
	</fieldset>
	<div class="btn-operation-container" style="width:10%;margin: 0px auto;">
		<div style="float: left;" class="btn-operation" onClick="updRecordOfFiber()" >修改</div>
	</div>
</form>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
<script type="text/javascript">
$(function(){
	if(!("${localId}")) 	
	$("#tr_cityName td").css("display",'');
});

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
				$("select[name='cable_name']").append("<option value="+this.CABLE_ID+"<c:if test="${rows[0].LINEID} eq this.CABLE_ID">selected </c:if> >"+this.CABLE_NAME+"</option>");
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

function updRecordOfFiber(){
	$("#updRecordOfFiberForm").form('submit', {
		url : webPath + "/CutAndConnOfFiberController/updRecordOfFiber.do",
		onSubmit : function() {
			var isValid = $(this).form('validate');
			if (!isValid){
				$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
			}
			return isValid;	// 返回false终止表单提交
		},
		success : function(data) {
			var json=$.parseJSON(data);
			if(json.status){
				$('#win').window('close');
				$('#dg').datagrid('reload');
				$.messager.alert("提示","修改成功");
			}else{
				$.messager.alert("提示","修改失败");
			}
		}
	});
}
	
</script>
</html>
