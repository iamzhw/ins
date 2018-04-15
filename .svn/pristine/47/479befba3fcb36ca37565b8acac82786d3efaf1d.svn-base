<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../../util/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>

<div class="pageContent">
	<form id="jobId">
		<input type="hidden" name="job_id" value="${job.JOB_ID}" />
		<div class="pageFormContent" >
			<table >
			<tr>
			<td><label>计划名称：</label> </td>
			<td><input name="job_name" type="text" class="required"
					size="30" value="${job.JOB_NAME}" /></td>	
			</tr><tr>				
			<td>
			<label>计划周期：</label>
			</td>
			<td>
				<select class="combox required" name="circle_id">
				<c:forEach items="${circles}" var="v">
				<option <c:if test="${v.CIRCLE_ID==job.CIRCLE_ID}">selected</c:if> value="${v.CIRCLE_ID}" >${v.CIRCLE_NAME}</option>
				</c:forEach>
				</select>
			</td>
			</tr>
			<tr>
			<td>
			<label>机房类型：</label>
			</td>
			<td>
			<select class="combox required" name="room_type_id">
				<c:forEach items="${roomTypes}" var="v">
					<option <c:if test="${v.ROOM_TYPE_ID==job.ROOM_TYPE_ID}">selected</c:if> value="${v.ROOM_TYPE_ID}" >${v.ROOM_TYPE_NAME}</option>
				</c:forEach>
			</select>
			</td>
			</tr>
			<tr>
			<td>
			<label>区域：</label>
			</td>
			<td>
			<select class="combox required" name="area_id">
				<c:forEach items="${areaList}" var="v">
					<option <c:if test="${v.AREA_ID==job.AREA_ID}">selected</c:if> value="${v.AREA_ID}" >${v.NAME}</option>
				</c:forEach>
			</select>
			</td>
			</tr>
			<tr>
			<td>
			<label>检查内容：</label>				
			</td>
			<td>
			<textarea name="content" cols="80" rows="4" class="required" >${job.CONTENT}</textarea>
			</td>
			</tr>
			<tr>
			<td>
			<label>创建人：</label> 
			</td>
			<td>
			<input name="creator" type="text" readonly="readonly" class="required" size="30" value="${job.CREATOR}" />
			</td>
			</tr><tr>
			<td>
			<label>创建日期：</label>
			</td>
			<td>
			 <input name="create_date" type="text"
					readonly="readonly" class="required" size="30" value="${job.CREATE_DATE}"/>
			</td>
			</tr>
			</table>
		</div>
		<div class="formBar">
			<div style="height: 7%; border-bottom: 2px solid green;">
				<div style="margin-left: 10px;" class="btn-operation"
					onclick="savePlan();">
					保存
				</div>
				<div style="margin-left: 10px;" class="btn-operation"
					onclick="history.back();">
					返回
				</div>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">

	function savePlan(){
	
		if($("#jobId").form('validate')){
			$.messager.confirm('系统提示', '您确定要保存该计划吗?', function(r) {
				if(r){
					var job_id = $("input[name='job_id']").val();
					var job_name = $("input[name='job_name']").val();
					var circle_id = $("select[name='circle_id']").val();
					var room_type_id = $("select[name='room_type_id']").val();
					var area_id = $("select[name='area_id']").val();
					var content = $("textarea[name='content']").val();
					var creator = $("input[name='creator']").val();
					var create_date = $("input[name='create_date']").val();
					$.ajax({
						type : 'POST',
						url : webPath + "JfxjJob/update.do",
						data : {
							job_id : job_id,
							job_name : job_name,
							circle_id : circle_id,
							room_type_id : room_type_id,
							area_id :area_id,
							content : content,
							creator : creator,
							create_date : create_date
						},
						dataType : 'json',
						success : function(json) {
							if (json.status) {
								$.messager.show({
									title : '提  示',
									msg : '保存计划成功!',
									showType : 'show'
								});
							}
							location.href = 'index.do';
						}
					});
				}
			});
		}
	}
</script>
