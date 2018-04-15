<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../../util/head.jsp"%>

<script type="text/javascript">

</script>

<title>步巡点信息修改</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="ff" method="post">
<!-- 			<input type="hidden" name="vstaff_id" value="${staff.STAFF_ID}" /> -->
			<table>
				<tr>
					<td>中继段：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								type="text" id="relay_name" name="relay_name" value="${equip.RELAY_NAME}" readonly="true"/>
						</div>
					</td>
					<td>设施类型：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								type="text" id="equip_type" name="equip_type" value="${equip.EQUIP_TYPE_NAME}" readonly="true"/>
						</div>
					</td>	
				</tr>
				<tr>
					<td>路由：</td>
					<td>
						<div class="condition-text-container">
						<c:if test="${equip.IS_EQUIP==1}">
							<input class="condition-text easyui-textbox condition"
								value="路由設施" name="is_equip" id="is_equip" readonly="true"/>
						</c:if>
						<c:if test="${equip.IS_EQUIP==0}">
							<input class="condition-text easyui-textbox condition"
								value="非路由設施" name="is_equip" id="is_equip" readonly="true"/>
						</c:if>		
						</div>
					</td>
					<td>设施ID：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								 id="equip_id" name="equip_id" value="${equip.EQUIP_ID}" readonly="true"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>设施编号：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								value="${equip.EQUIP_CODE}" name="equip_code" id="equip_code" />
						</div>
					</td>
					<td>高度/深度：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								value="${equip.DEPTH}" name="depth" id="depth" />
						</div>
					</td>
				</tr>
				<tr>
					<td>X坐标：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								value="${equip.LONGITUDE}" name="longitude" id="longitude" readonly="true"/>
						</div>
					</td>
					<td>Y坐标：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								value="${equip.LATITUDE}" name="latitude" id="latitude" readonly="true"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>位置描述：</td>
					<td colspan="3">
						<div class="condition-text-container" style="width:100%;">
							<input class="condition-text easyui-textbox condition"
								value="${equip.EQUIP_ADDRESS}" name="equip_address" id="equip_address" style="width:99%;" multiline="true" />
						</div>
					</td>
				</tr>
				<tr>
					<td>设施描述：</td>
					<td colspan="3">
						<div class="condition-text-container" style="width:100%;">
							<input class="condition-text easyui-textbox condition"
								value="${equip.DESCRIPTION}" name="description" id="description" style="width:99%;" multiline="true" readonly="true"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>户主姓名：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								value="${equip.OWNER_NAME}" name="owner_name" id="owner_name" />
						</div>
					</td>
					<td>联系电话：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								value="${equip.OWNER_TEL}" name="owner_tel" id="owner_tel" />
						</div>
					</td>
				</tr>
				<tr>
					<td>义务护线员姓名：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								value="${equip.PROTECTER}" name="protecter" id="protecter" />
						</div>
					</td>
					<td>护线员联系电话：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								value="${equip.PROTECT_TEL}" name="protect_tel" id="protect_tel" />
						</div>
					</td>
				</tr>
				<tr>
					<td>采集时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
								id="create_date" name="create_date" value="${equip.CREATE_DATE}" readonly="true"/>
						</div>
					</td>
					<td>编辑时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
							   id="update_date" name="update_date" value="${equip.UPDATE_DATE}" readonly="true"/>
						</div>
					</td>	
				</tr>
				<tr>
					<td>点评状态：</td>
					<td>
						<div class="condition-text-container">
							<c:if test="${is_check==1}">
							<input class="condition-text easyui-textbox condition"
								value="已点评"  name="is_check" id="is_check" readonly="true"/>
							</c:if>
							<c:if test="${is_check==0}">
							<input class="condition-text easyui-textbox condition"
								value="未点评"  name="is_check" id="is_check" readonly="true"/>
							</c:if>
						</div>
					</td>
					<td>评价状态：</td>
					<td>
						<div class="condition-text-container">
							<c:if test="${check.STATUS==1}">
							<input class="condition-text easyui-textbox condition"
								value="有问题"  name="check_status" id="check_status" readonly="true"/>
							</c:if>
							<c:if test="${check.STATUS==0}">
							<input class="condition-text easyui-textbox condition"
								value="没问题"  name="check_status" id="check_status" readonly="true"/>
							</c:if>
						</div>
					</td>	
				</tr>
				<tr>
					<td>点评时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
							  id="check_time" name="check_time" value="${check.CHECK_TIME}" readonly="true"/>
						</div>
					</td>
					<td>点评人：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-textbox condition"
							 id="check_staff_name" name="check_staff_name" value="${check.STAFF_NAME}" readonly="true"/>
						</div>
					</td>	
				</tr>
				<tr>
					<td>点评结果：</td>
					<td colspan="3">
						<div class="condition-text-container" style="width:100%;">
							<input class="condition-text easyui-textbox condition"
								value="${keydescription}" name="keydescription" id="keydescription" style="width:99%;" multiline="true" readonly="true" />
						</div>
					</td>
				</tr>
			</table>	
		</form>
		
		
		<div>
			   <p><span>填报时现场图片</span></p>   
			<c:if test="${photosize>0}">
				<div id="photo"  data-options="closable:false" style="overflow:auto;">
					<img src="${photo.MICRO_PHOTO}" style="width: 100px;"/>
					<a href="javascript:openwindow();">查看更多图片</a>
				</div>
			</c:if>  
			<div id="more_picture"    style="overflow:auto;display:none;">
				<c:forEach items="${photoList}" var="p">
					<img src="${p.MICRO_PHOTO}" style="width: 100px;"/>
				</c:forEach>
			</div>
		</div>		 	
		
		<div style="text-align:left;padding:10px 0 10px 90px">
			<div class="btn-operation" onClick="updateForm()">更新</div>
		</div>
	</div>  
	<div id="photo_show"></div>
	<script type="text/javascript">
	openwindow = function (){
	document.getElementById("more_picture").style.display="block" ; 
	document.getElementById("photo").style.display="none" ; 
	}
	</script> 
</body>
</html>
