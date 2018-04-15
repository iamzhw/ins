<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/css/zTreeStyle3/zTreeStyle.css" />
<script type="text/javascript"
	src="<%=path%>/js/ZTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path%>/js/ZTree/ZTree.js"></script>
<title>功能管理</title>
</head>
<body style="padding:3px;border:0px">
	<div class="easyui-layout" style="width:1100px;height:480px;">
		<div data-options="region:'east'"
			style="width:700px; border-left:0px;padding:8px 0px 0px 50px">
			<div
				style="text-align:left;margin:0px 0px 20px 0px;width:90%;float:top;height:40px;">
				<div id="icon-add" class="btn-operation" onclick="addPoint()">增加</div>
				<div id="icon-edit" class="btn-operation" onclick="editPoint()">编辑</div>
				<div id="icon-remove" class="btn-operation" onclick="removePoint()">删除</div>
				<div id="icon-ok" class="btn-operation" onclick="submitPoint()"
					style="display:none">提交</div>
				<div id="icon-cancel" class="btn-operation" onclick="cancelPoint()"
					style="display:none">取消</div>
			</div>
			<div
				style="text-align:left;margin:0px 0px 20px 0px;width:90%;float:top;">
				<form id="ff" method="post">
					<input type="hidden" name="vid" />
					<table>
						<tr>
							<td>所选功能-名称：</td>
							<td>
								<div class="condition-text-container">
									<input class="condition-text easyui-validatebox condition"
										type="text" name="vname" disabled="disabled"
										data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
								</div>
							</td>
						</tr>
						<tr>
							<td>所选功能-URL：</td>
							<td><div class="condition-text-container">
									<input class="condition-text easyui-validatebox condition"
										type="text" name="vactionName" disabled="disabled"
										data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
								</div>
							</td>
						</tr>
						<tr>
							<td>所选功能-是否是父节点：</td>
							<td><select
								class="condition-select condition"
								name="visParent" disabled="disabled">
									<option value="1">是</option>
									<option value="0">否</option>
							</select></td>
						</tr>
					</table>
				</form>
				<form id="ffadd" method="post">
					<table id="newPoint" style="display: none">
						<tr>
							<td>新增功能-名称：</td>
							<td><div class="condition-text-container">
									<input class="condition-text easyui-validatebox condition"
										type="text" name="nname"
										data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
								</div></td>
						</tr>
						<tr>
							<td>新增功能-URL：</td>
							<td><div class="condition-text-container">
									<input class="condition-text easyui-validatebox condition"
										type="text" name="nactionName"
										data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
								</div>
							</td>
						</tr>
						<tr>
							<td>新增功能-是否是父节点：</td>
							<td><select
								class="condition-select condition"
								name="nisParent">
									<option value="1">是</option>
									<option value="0">否</option>
							</select></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div data-options="region:'center' "
			style="width:400px;padding:3px 7px;">
			<div id="resourceTree" class="ztree"></div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	var setting;
	//参数设置
	setting = {
		async : {
			enable : true,
			url : webPath + "/Resource/getGns.do",
			autoParam : [ "id=id", "name" ]
		//otherParam: {},
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentId",
				rootPId : 0
			}
		},
		callback : {
			onClick : onClick
		}
	};
	$(document).ready(function() {
		$.fn.zTree.init($("#resourceTree"), setting);
	});
	function onClick(event, treeId, treeNode) {
		$("input[name='vid']").val(treeNode.id);
		$("input[name='vname']").val(treeNode.name);
		$("input[name='vactionName']").val(treeNode.actionName);
		if (treeNode.isParent) {
			$("select[name='visParent']").val("1");
		} else {
			$("select[name='visParent']").val("0");
		}
		disableForm();
		showButton();
	}
	var flag;
	function validateSelect() {
		var vid = $("input[name='vid']").val();
		if ("" == vid || vid == null) {
			$.messager.show({
				title : '提  示',
				msg : '请选择功能节点!',
				showType : 'show'
			});
			return false;
		} else {
			return true;
		}
	}
	function hideButton() {
		$("#icon-ok").show();
		$("#icon-cancel").show();
		$("#icon-add").hide();
		$("#icon-edit").hide();
		$("#icon-remove").hide();
	}
	function showButton() {
		$("#icon-ok").hide();
		$("#icon-cancel").hide();
		$("#icon-add").show();
		$("#icon-edit").show();
		$("#icon-remove").show();
	}
	function disableForm() {
		$("input[name='vname']").attr("disabled", true);
		$("input[name='vactionName']").attr("disabled", true);
		document.getElementsByName("visParent")[0].disabled = true;
	}
	function clearForm() {
		$("input[name='vname']").val("");
		$("input[name='vactionName']").val("");
		$("select[name='visParent']").val("1");
		$("input[name='vid']").val("");
	}
	function editPoint() {

		if (validateSelect()) {
			flag = "edit";
			$("input[name='vname']").attr("disabled", false);
			$("input[name='vactionName']").attr("disabled", false);
			document.getElementsByName("visParent")[0].disabled = false;
			hideButton();
		}
	}
	function addPoint() {
		if (validateSelect()) {
			flag = "add";
			hideButton();
			//要显示新增区
			$("#newPoint").show();
		}
	}

	function removePoint() {
		if (validateSelect()) {
			var id = $("input[name='vid']").val();
			$.messager.confirm('系统提示', '您确定要删除节点及其子节点吗?', function(r) {
				if (r) {
					$.ajax({
						type : 'POST',
						url : webPath + "/Resource/removeGns.do",
						data : {
							id : id
						},
						dataType : 'json',
						success : function(json) {
							$.fn.zTree.init($("#resourceTree"), setting);
							clearForm();
							if (json.status) {
								$.messager.show({
									title : '提  示',
									msg : '删除节点及其子节点成功!',
									showType : 'show'
								});
							}

						}
					})
				}
			});
		}
	}

	function submitPoint() {
		if ("edit" == flag) {
			//验证非空
			var pass = $("#ff").form('validate');
			if (!pass) {
				//清空所有
				clearForm();
				$.messager.show({
					title : '提  示',
					msg : '验证不通过!',
					showType : 'show'
				});
				return;
			}

			//对修改过的节点进行更新操作
			var name = $("input[name='vname']").val();
			var actionName = $("input[name='vactionName']").val();
			var isParent = $("select[name='visParent']").val();
			var id = $("input[name='vid']").val();
			$.messager.confirm('系统提示', '您确定要更新节点吗?', function(r) {
				if (r) {
					$.ajax({
						type : 'POST',
						url : webPath + "/Resource/updateGns.do",
						data : {
							name : name,
							actionName : actionName,
							isParent : isParent,
							id : id
						},
						dataType : 'json',
						success : function(json) {
							$.fn.zTree.init($("#resourceTree"), setting);
							if (json.status) {
								$.messager.show({
									title : '提  示',
									msg : '更新节点成功!',
									showType : 'show'
								});
							}
						}
					})
				}
			});
		}

		if ("add" == flag) {
			//验证非空
			var pass = $("#ffadd").form('validate');
			if (!pass) {
				//清空新增FORM所有
				//clearNewForm();
				//showButton();
				$.messager.show({
					title : '提  示',
					msg : '验证不通过!',
					showType : 'show'
				});
				return;
			}
			$.messager.confirm('系统提示', '您确定要新增节点吗?', function(r) {
				var nname = $("input[name='nname']").val();
				var nactionName = $("input[name='nactionName']").val();
				var nisParent = $("select[name='nisParent']").val();
				var parentId = $("input[name='vid']").val();
				if (r) {
					$.ajax({
						type : 'POST',
						url : webPath + "/Resource/addGns.do",
						data : {
							name : nname,
							actionName : nactionName,
							isParent : nisParent,
							parentId : parentId
						},
						dataType : 'json',
						success : function(json) {
							$.fn.zTree.init($("#resourceTree"), setting);
							if (json.status) {
								$.messager.show({
									title : '提  示',
									msg : '新增节点成功!',
									showType : 'show'
								});
							}
							clearForm();
							clearNewForm();
						}
					})
				}
			});
		}
		disableForm();
		showButton();
	}

	function cancelPoint() {
		clearNewForm();
		disableForm();
		showButton();
	}
	function clearNewForm() {
		$("input[name='nname']").val("");
		$("input[name='nactionName']").val("");
		$("select[name='nisParent']").val("1");
		$("#newPoint").hide();
	}
</script>

