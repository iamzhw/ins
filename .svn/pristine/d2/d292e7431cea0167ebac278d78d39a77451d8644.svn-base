<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/jsp/util/head.jsp"%>
		<title>任务详情</title>
	</head>
	<body>
		<h3>设备信息</h3>
		<table class="easyui-datagrid" style="width:1300px;height:200px" data-options="remoteSort: false">
			<thead>
				<tr>
					<th data-options="field:'code',sortable:true">设备编码</th>
					<th data-options="field:'name',sortable:true">设备名称</th>
					<th data-options="field:'address'">设备地址</th>
					<th data-options="field:'area'">所属区域</th>
					<th data-options="field:'remark'">现场规范</th>
					<th data-options="field:'info'">回单备注</th>
					<th data-options="field:'before_photo'">整改前照片</th>
		            <th data-options="field:'after_photo'">整改后照片</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${taskEqpPhotoList }" var="eqp">
					<tr>
						<td>${eqp.eqp_no }</td>
						<td>${eqp.eqp_name }</td>
						<td>${eqp.eqpaddress }</td>
						<td>${eqp.son_area_name }</td>
						<td>${eqp.REMARK }</td>
						<td>${eqp.info }</td>
						<td>
	            			<c:forEach items="${eqp.photoList }" var="photo">
								<c:if test="${photo.record_type==1 or  photo.record_type==3 or  photo.record_type==0}">
									 <a href="${photo.photo_path }" title="照片预览" class="nyroModal">
										<img src="${photo.micro_photo_path }" style="width: 50px;"/>
									 </a>
								</c:if>  
							</c:forEach>
	            		</td>
	            		<td>
	            			<c:forEach items="${eqp.photoList }" var="photo2">
								<c:if test="${photo2.record_type==2}">
									<a href="${photo2.photo_path }" title="照片预览" class="nyroModal">
										<img src="${photo2.micro_photo_path }" style="width: 50px;"/>
									</a> 
								</c:if> 
							</c:forEach>
	            		</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<h3>端子信息</h3>
		<table class="easyui-datagrid" data-options="remoteSort: false">
			<thead>
				<tr>
					<th data-options="field:'rownum'"><strong>序号</strong></th>
					<th data-options="field:'port_no',sortable:true"><strong>端子编码</strong></th>
					<th data-options="field:'eqp_no',sortable:true"><strong>设备编码</strong></th>
					<th data-options="field:'eqp_name',sortable:true"><strong>设备名称</strong></th>
					<th data-options="field:'gl_no',sortable:true" ><strong>光路编码</strong></th>
					<th data-options="field:'gl_name'" ><strong>光路名称</strong></th>
					<th data-options="field:'ish'" ><strong>是否H端子</strong></th>
					<th data-options="field:'gdbh'" ><strong>工单编号</strong></th>
					<th data-options="field:'xz'" ><strong>工单性质</strong></th>
					<th data-options="field:'sggh'" ><strong>施工工号</strong></th>
					<th data-options="field:'gdjgsj'" ><strong>工单竣工时间</strong></th>
					<th data-options="field:'bdsj'" ><strong>端子变动时间</strong></th>
					<th data-options="field:'recordtype'" ><strong>检查/整改记录</strong></th>
					<th data-options="field:'if_check'" ><strong>是否检查</strong></th>
					<th data-options="field:'create_staff'" ><strong>检查人/整改人</strong></th>
					<th data-options="field:'create_time',nowarp:false,autoSize:true" width="79px"><strong>检查时间</strong></th>
					<th data-options="field:'ischeckok'" ><strong>检查/整改反馈</strong></th>
					<th data-options="field:'descript'" ><strong>检查描述</strong></th>
					<th data-options="field:'port_info'" ><strong>整改描述</strong></th>
					<th data-options="field:'photo'"><strong>检查照片</strong></th>
					<th data-options="field:'photozg'"><strong>整改照片</strong></th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty portList}">
					<c:forEach items="${portList }" var="port" varStatus="porStatus">
						<tr>
							<td>${porStatus.index + 1}</td>
							<td><input name="port_id" type=hidden class="" value="${port.DZID}"/>${port.PORT_NO}</td>
							<td>${port.EQP_NO}</td>
							<td>${port.EQP_NAME}</td>
							<td>${port.GLBH}</td>
							<td>${port.GLMC}</td>							
                            <td>${port.ISH}</td>
							<td>${port.GDBH}</td>
                            <td>${port.XZ}</td>
							<td>${port.SGGH}</td>
							<td>${port.GDJGSJ}</td>
							<td>${port.BDSJ}</td>
							<td>${port.RECORDTYPE}</td>
							<c:choose>
								<c:when test="${empty port.portCheckedList }">
									<td>否</td>
								</c:when>
								<c:otherwise>
									<td>是</td>
								</c:otherwise>
							</c:choose>
							<td>${port.CREATE_STAFF}</td>
							<td data-options="field:'create_time',nowarp:false,autoSize:true" width="79px">${port.CREATE_TIME}</td>	
							<td>${port.ISCHECKOK}</td>
							<td>${port.DESCRIPT}</td>
							 <td>
							<c:forEach items="${port.portCheckedList }" var="portChecked">
							${portChecked.PORT_INFO}
							</c:forEach>
							</td>
							<td>
							<c:forEach items="${port.portCheckedList }" var="portChecked">
								<!-- 检查照片 -->
									<c:if test="${portChecked.PHOTO_PATH != null }">
										<c:if test="${portChecked.RECORD_TYPE != 2 }">
											<a href="${portChecked.PHOTO_PATH }" title="照片预览" class="nyroModal">
												<img src="${portChecked.MICRO_PHOTO_PATH }" style="width: 50px;"/>
							 				</a>
							 			</c:if>
									</c:if>
									<%-- <c:if test="${portChecked.PHOTO_PATH == null }">
										暂无照片
									</c:if> --%>
								
							</c:forEach>
							</td>
							<td>
							<c:forEach items="${port.portCheckedList }" var="portChecked">
							   <!--        整改照片 -->     
									<c:if test="${portChecked.PHOTO_PATH != null }">
										<c:if test="${portChecked.RECORD_TYPE == 2 }">
											<a href="${portChecked.PHOTO_PATH }" title="照片预览" class="nyroModal">
												<img src="${portChecked.MICRO_PHOTO_PATH }" style="width: 50px;"/>
							 				</a>
							 			</c:if>
									</c:if>								
									<%-- <c:if test="${portChecked.PHOTO_PATH == null }">
										暂无照片
									</c:if> --%>
								
							</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty portList}">
					<tr>
						<td colspan="6">无端子信息！</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<h3>流程详情</h3>
		<table class="easyui-datagrid">
			<thead>
				<tr>
					<th data-options="field:'rownum'">序号</th>
					<th data-options="field:'oper_time'">操作时间</th>
					<th data-options="field:'oper_staff'">操作人</th>
					<th data-options="field:'oper_remark'">流程环节</th>
					<th data-options="field:'oper_content'">操作详情</th>
					<th data-options="field:'oper_receiver'">接单人</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty processList}">
					<c:forEach items="${processList }" var="process" varStatus="proStatus">
						<tr>
							<td style="width: 3%;">${proStatus.index + 1}</td>
							<td style="width: 10%;">
								<input name="port_id" type=hidden class=""/>${process.OPER_TIME}
							</td>
							<td style="width: 15%;">${process.STAFF_NAME}</td>
							<td style="width: 30%;">${process.REMARK}</td>
							<td style="width: 30%;">${process.CONTENT}</td>
							<td style="width: 30%;">${process.RECEIVER}</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty processList}">
					<tr>
						<td colspan="4">
							无工单流程信息！
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
<div id="intoAudit"  style="width:100%;height:200px;"></div>
		<script type="text/javascript">
			$(function(){
				$('.nyroModal').nyroModal();
			});
		</script>
	</body>
	<script>
 	$(document).ready(function() {
	   var taskId = '${taskEqpPhotoList[0].task_id}';
	 
	   var url = "<%=path%>/CableCheck/getTaskByTaskId.do";
	   $.post(url,{"taskId":taskId},function(data){
	       
  	        if(data.STATUS_ID==7){
  	    var table='';
  	      table+=' <table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#CCCCCC">';
				table+='<tr height="80px" bgcolor="#FFFFFF">';
					table+='<td align="right" class="form_01b" style="text-align: center; font-size: 16px;"><strong>审核意见</strong>&nbsp;&nbsp;</td>';
					table+='<td colspan="4" align="left" class="list_pd1">';
						table+='<textarea rows="10" cols="130" id="shyj">';
							
						table+='</textarea>';
					table+='</td>';
				table+='</tr>';
			table+='</table>';
  	       
	         
	         table+='<div class="btn-operation-container">';
				table+='<div style="float: right;" class="btn-operation" onClick="doTask(\'8\')">';
					table+='通过';
				table+='</div>';
				table+='<div style="float: right;" class="btn-operation" onClick="doTask(\'6\')">';
				table+='	不通过';
				table+='</div>';
			table+='</div>';
	            $("#intoAudit").html(table);
	            
	      }else{
	      
	      }
	   },"json");
	});
	function doTask(status_id){
			var shyj = $("#shyj").val();
			var taskId = '${taskEqpPhotoList[0].task_id}';
			$.ajax({
				type : 'POST',
				url : webPath + "CableCheck/taskNewAudit.do?status_id=" + status_id + "&taskId=" + taskId+ "&shyj=" + shyj,
				dataType : 'json',
				success : function(json) {
					if(json.status){
						$.messager.alert('页面消息','操作成功！','info',function(){
					       closeddd('任务详情');
					    });
						
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
		function closeddd(subtitle){
			parent.$('#tabs').tabs('close',subtitle);
		}
 
	</script>
</html>