<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<div class="condition-container">
			<form id="form"  method="post">
				<table class="condition">
					<tr>
						<td>设施编号:</td>
						<td >
							<div class="condition-text-container">
								<input class="condition-text condition"  type="text" value="${model[0].EQUIP_CODE }" />
							</div>
						</td>
						<td>设施类型:</td>
						<td >
							<div class="condition-text-container">
								<c:if test="${model[0].EQUIP_TYPE eq 1 }">
									<input class="condition-text condition"  type="text" value="标石" />
								</c:if>
								<c:if test="${model[0].EQUIP_TYPE eq 2 }">
									<input class="condition-text condition"  type="text" value="人井" />
								</c:if>
								<c:if test="${model[0].EQUIP_TYPE eq 3 }">
									<input class="condition-text condition"  type="text" value="地标" />
								</c:if>
								<c:if test="${model[0].EQUIP_TYPE eq 4 }">
									<input class="condition-text condition"  type="text" value="宣传牌" />
								</c:if>
								<c:if test="${model[0].EQUIP_TYPE eq 5 }">
									<input class="condition-text condition"  type="text" value="埋深点" />
								</c:if>
								<c:if test="${model[0].EQUIP_TYPE eq 6 }">
									<input class="condition-text condition"  type="text" value="电杆" />
								</c:if>
								<c:if test="${model[0].EQUIP_TYPE eq 7 }">
									<input class="condition-text condition"  type="text" value="警示牌" />
								</c:if>
								<c:if test="${model[0].EQUIP_TYPE eq 8 }">
									<input class="condition-text condition"  type="text" value="护坡" />
								</c:if>
								<c:if test="${model[0].EQUIP_TYPE eq 9 }">
									<input class="condition-text condition"  type="text" value="接头盒" />
								</c:if>
								<c:if test="${model[0].EQUIP_TYPE eq 10 }">
									<input class="condition-text condition"  type="text" value="非路由标志" />
								</c:if>
							</div>
						</td>
						<td>位置描述:</td>
						<td >
							<div class="condition-text-container">
								<input class="condition-text condition"  type="text" value="${model[0].EQUIP_ADDRESS }" />
							</div>
						</td>
					</tr>
					<tr id="isDisplay">
						<td>户主(单位)姓名:</td>
						<td >
							<div class="condition-text-container">
								<input class="condition-text condition"  type="text" value="${model[0].OWNER_NAME }" />
							</div>
						</td>
						<td>户主(单位)电话:</td>
						<td >
							<div class="condition-text-container">
								<input class="condition-text condition"  type="text" value="${model[0].OWNER_TEL }" />
							</div>
						</td>
					</tr>
					<tr id="description">
						<td>义务护线员姓名:</td>
						<td >
							<div class="condition-text-container">
								<input class="condition-text condition"  type="text" value="${model[0].PROTECTER }" />
							</div>
						</td>
						<td>义务护线员电话:</td>
						<td >
							<div class="condition-text-container">
								<input class="condition-text condition"  type="text" value="${model[0].PROTECT_TEL }" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	
<script type="text/javascript">
	$(function(){
		var array={};
		array="${model[0].DESCRIPTION}".split(",");
		var equip_type = "${model[0].EQUIP_TYPE}";
		if(equip_type==1||equip_type==2||equip_type==3){
			$("#isDisplay").append('<td>光缆深度:</td><td><div class="condition-text-container"><input class="condition-text condition"  type="text" value='+array[0]+'  ></div></td>');
			array.shift()
			$("#description").append("<td>描述:</td><td><div class='condition-text-container'><input class='condition-text condition'  type='text' value="+array+" ></div></td>");
		}else if(equip_type==6){
			$("#isDisplay").append("<td>光缆高度:</td><td><div class='condition-text-container'><input class='condition-text condition'  type='text' value="+array[0]+" ></div></td>");
			array.shift()
			$("#description").append("<td>描述:</td><td><div class='condition-text-container'><input class='condition-text condition'  type='text' value="+array+" ></div></td>");
		}else {
			$("#description").append("<td>描述:</td><td><div class='condition-text-container'><input class='condition-text condition'  type='text' value="+array+" ></div></td>");
		}
		$("input[type='text']").each(function(){
			this.readOnly=true;
		});
	});
</script>
	