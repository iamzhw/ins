<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<!-- <div class="condition-container" style="height:50px;">
		<div class="btn-operation-container">
			<div style="float: left;" class="btn-operation" onClick="history.back()">返回</div>
		</div>
	</div> -->
<form id="addRecordOfFiberForm" method="post">
	<fieldset style="width:750px; margin: 0px auto;">
		<legend>干线光缆割接记录表</legend>
		<table class="condition" style="width: 100% ;margin: 0px auto;" >
			<tr id="tr_cityName">
			<td width="20%" style="display: none;">地区名：</td>
			<td width="20%" style="display: none;">
				<div>
					<select name="city_name" class="condition-select"
						onchange="getCable(this.value)">
						<option value=''>--请选择--</option>
						<c:forEach var="city" items="${cityModel }">
							<option value="${city.AREA_ID }">${city.NAME }</option>
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
					</select>
				</div>
			</td>
			<td width="20%" >*割接日期：</td>
				<td width="20%">
					<div class="condition-text-container ">
						<input class="condition-text condition easyui-validatebox" type="text" name="cutdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" data-options="required:true" />
					</div>
				</td>
			</tr>
			<tr>
			<td width="20%">*中继段：</td>
			<td width="20%">
				<div>
					<select name="relay_name" class="condition-select easyui-validatebox" data-options="required:true">
						<option value=''>--请选择--</option>
					</select>
				</div>
			</td>
			<td width="20%">光缆结构：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="fiberjg"  />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%" >原光缆厂家：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="oldfibername"  />
					</div>
				</td>
				<td width="20%" >机务人员开始调度：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="changedate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">原光缆色谱：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="fiberspectrum" />
					</div>
				</td>
				<td width="20%">原即时长度：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="fiberlengthatnow"  />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%" >原纤芯厂家：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="oldxinname" />
					</div>
				</td>
				<td width="20%" >原纤芯数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="fibercorenum" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">原接头号：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="fiberpiecing" />
					</div>
				</td>
				<td width="20%">光缆割接开始时间(断缆)：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="fibercurdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">新光缆厂家：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="newfibername"  />
					</div>
				</td>
				<td width="20%">接续开始时间：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="curstartdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">新纤芯厂家：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="newxinname" />
					</div>
				</td>
				<td width="20%">接续完成时间：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="curenddate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">原光缆纤芯色谱：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="oldfiberxinsp"  />
					</div>
				</td>
				<td width="20%">光缆割接时长：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="datedistain"  />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">新光缆纤芯色谱：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="newfiberxinsp"  />
					</div>
				</td>
				<td width="20%">所有系统恢复正常时间：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="regaindate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">割接前纤芯长度(上次割接后)：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="oldcutoverlength" />
					</div>
				</td>
				<td width="20%">割接前纤芯长度(测试)：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="oldcutoverlength"  data-options="validType:'doubleTest'"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">*割接后纤芯长度(全程)：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="newcutoverlength" data-options="required:true,validType:'doubleTest'" />
					</div>
				</td>
				<td width="20%">接头最小耗损：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="lineminsunhao"  />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">接头耗损>0.3dB纤芯数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="sunhao3" data-options="validType:'doubleTest'"/>
					</div>
				</td>
				<td width="20%">接头最大耗损：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="linemaxsunhao"  />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">割接后纤芯长度变化：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="changelength" data-options="validType:'doubleTest'" />
					</div>
				</td>
				<td width="20%">接头耗损>0.5dB纤芯数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="sunhao5" data-options="validType:'doubleTest'" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">测试人员：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="tester"  />
					</div>
				</td>
				<td width="20%">测试时间：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="testdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
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
						<input class="condition-text condition easyui-validatebox" type="text" name="peoplenumber" data-options="required:true,validType:'doubleTest'"/>
					</div>
				</td>
				<td width="20%">*割接现场指挥：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="curleader" data-options="required:true"/>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">*机务人员名单：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="jwpeoples" data-options="required:true" />
					</div>
				</td>
				<td width="20%">*本端机房：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="jifang1" data-options="required:true" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">机房测试人员名单：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="jwtestpeoples" />
					</div>
				</td>
				<td width="20%">对端机房1：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="jifang2"  />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">割接现场人员总数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="curoverpeoplenumber" data-options="validType:'doubleTest'" />
					</div>
				</td>
				<td width="20%">对端机房2：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="jifang3" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">割接现场人员名单：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="curoverpeoples"  />
					</div>
				</td>
				<td width="20%">正式员工人数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="zhenshipeo"  />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">劳务工人数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="raowupeo"  />
					</div>
				</td>
				<td width="20%">其它人员数：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="qitapeo"  />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">*填表人：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition easyui-validatebox" type="text" name="tianbiaoren"  data-options="required:true"/>
					</div>
				</td>
				<td width="20%">本端机房名称：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="jifangname" />
					</div>
				</td>
			</tr>
		</table>
	</fieldset>
	<div class="btn-operation-container" style="width:10%;margin: 0px auto;">
		<div style="float: left;" class="btn-operation" onClick="addRecordOfFiber()" >新增</div>
	</div>
</form>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
<script type="text/javascript">
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
	
	function addRecordOfFiber(){
		$("#addRecordOfFiberForm").form('submit', {
			url : webPath + "/CutAndConnOfFiberController/addRecordOfFiber.do",
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
					$.messager.alert("提示","新增成功");
				}else{
					$.messager.alert("提示","新增失败");
				}
			}
		});
	}
	
</script>
