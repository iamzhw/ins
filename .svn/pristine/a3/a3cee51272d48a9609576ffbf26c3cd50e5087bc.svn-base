<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>员工管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">姓名：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="staff_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">帐号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="staff_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">组织关系：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="org_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="10%">角色：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="role_name" type="text" class="condition-text" />
							</div>
						</td>	
						<td width="10%">地市：</td>
						<td width="20%"><select name="area" class="condition-select">
								<option value="">请选择</option>
								<c:forEach items="${areaList}" var="al">
									<option value="${al.AREA_ID}">${al.NAME}</option>
								</c:forEach>
						</select></td>
						<td width="10%">区县：</td>
						<td width="20%"><select name="son_area"
							class="condition-select">
								<option value="">请选择</option>
						</select>
					</tr>
					
					<tr>
						<td width="10%">证件号码：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="id_number" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%"></td>
						<td width="20%"></td>
						<td width="10%"></td>
						<td width="20%"></td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="addStaff()">增加</div>
			<div class="btn-operation" onClick="editStaff()">编辑</div>
			<div class="btn-operation" onClick="delStaff()">删除</div>
			<div class="btn-operation" onClick="exportStaff()">导出</div>
			<div class="btn-operation" onClick="importStaff()">导入</div>
			<div class="btn-operation" onClick="assignRolePermissions()">
				角色赋权</div>
			<div class="btn-operation" onClick="assignSoftPermissions()">
				应用赋权</div>
				<div class="btn-operation" onClick="assignOutSitePermissions()">
			   外力点赋权</div>
			   <div style="float: right;" class="btn-operation"
				onClick="girdManage()">网格管理</div>
			<div style="float: right;" class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【员工管理】" style="width: 100%; height: 480px">
	</table>
	<div id="win_staff"></div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
		selectSelected();
			//searchData();
		});
		
		function selectSelected(){
			$.ajax({
				type : 'POST',
				url : webPath + "Staff/selectSelected.do",
				dataType : 'json',
				async:false,
				success : function(json) {
					if(json[0].ifGly==1){			
						$("#ifGLY").val("0");
					}else{
						$("select[name='area']").val(${areaId});
						$("select[name='area']").attr("disabled","disabled");
						$("#ifGLY").val("1");
					}
				}
			});
		}
		function searchData() {
			var staff_name = $("input[name='staff_name']").val().trim();
			var staff_no = $("input[name='staff_no']").val().trim();
			var area = $("select[name='area']").val();
			var son_area = $("select[name='son_area']").val();
			var org_name = $("input[name='org_name']").val();
			var role_name = $("input[name='role_name']").val();
			var id_number = $("input[name='id_number']").val();
			var obj = {
				staff_name : staff_name,
				staff_no : staff_no,
				area : area,
				son_area : son_area,
				org_name : org_name,
				role_name : role_name,
				id_number : id_number
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Staff/query.do",
				queryParams : obj,
				fit : true,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : false,
				columns : [ [ {
					field : 'STAFF_ID',
					title : '员工ID',
					checkbox : true
				}, {
					field : 'STAFF_NAME',
					title : '员工姓名',
					width : "9%",
					align : 'center'
				}, {
					field : 'STAFF_NO',
					title : '员工帐号',
					width : "10%",
					align : 'center'
				}, {
					field : 'ROLE_NAME',
					title : '角色',
					width : "15%",
					align : 'center'
				}, {
					field : 'SOFTWARE_NAME',
					title : '应用',
					width : "10%",
					align : 'center'
				}, {
					field : 'TEL',
					title : '联系方式',
					width : "10%",
					align : 'center'
				}, {
					field : 'DEPT_NAME',
					title : '维护网格',
					width : "10%",
					align : 'center'
				}, {
					field : 'ID_NUMBER',
					title : '证件号码',
					width : "15%",
					align : 'center'
				},{
					field : 'AREA_LEVEL',
					title : '外力点权限区域',
					width : "9%",
					align : 'center',
					formatter : function(value, rowData, index){
						if(value == "0"){
							return "市公司";
						}else if(value=='1'){
							return "县公司";
						}else{
							return "无外力点权限";
						}
					}
				}, {
					field : 'MANAGE_LEVEL',
					title : '外力点权限等级',
					width : "9%",
					align : 'center',
					formatter : function(value, rowData, index){
						if(value == "0"){
							return "分管领导";
						}else if(value=='1'){
							return "主要管理和技术人员";
						}else if(value=='2'){
							return "班组长";
						}else{
							return "无外力点权限";
						}
					}
				}, {
					field : 'AREA',
					title : '地市',
					width : "6%",
					align : 'center'
				}, {
					field : 'SON_AREA',
					title : '区县',
					width : "9%",
					align : 'center'
				}, {
					field : 'ORG_NAME',
					title : '组织关系',
					width : "10%",
					align : 'center'
				} ] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
       			 $(this).datagrid("fixRownumber");
				 $("body").resize();
				}
			});
		}
		/**选择行触发**/
		function onClickRow(index, row) {
			alert("onClickRow");
		}
		/**点击checkbox触发**/
		function onCheck(index, row) {
			alert("onCheck");
		}

		function onSelect(index, row) {
			alert("onSelect");
		}

		function onSelectAll(rows) {
			alert(rows);
			alert("onSelectAll");
		}

		$(function() {
			//查询
			if($("#ifGLY").val()=="1"){
				getSonAreaList('query');
			}else{
			$("select[name='area']").change(function() {
				//根据area_id，获取区县
				getSonAreaList('query');
			});
			}
		});

		function getSonAreaList(operator) {
			//	var areaId = $("select[name='area']").val();
			var area_id = 0;
			if ('query' == operator) {
				area_id = $("select[name='area']").find("option:selected")
						.val();
			} else if ('add' == operator) {
				area_id = $("select[name='varea']").find("option:selected")
						.val();
			}
			$.ajax({
				type : 'POST',
				url : webPath + "General/getSonAreaList.do",
				data : {
					areaId : area_id
				},
				dataType : 'json',
				success : function(json) {
					var result = json.sonAreaList;
					if ('query' == operator) {
						$("select[name='son_area'] option").remove();
						$("select[name='son_area']").append(
								"<option value=''>请选择</option>");
						for ( var i = 0; i < result.length; i++) {
							$("select[name='son_area']").append(
									"<option value=" + result[i].AREA_ID + ">"
											+ result[i].NAME + "</option>");
						}
					} else if ('add' == operator) {
						if($("#ifGLY").val()=="1"){
							$("select[name='varea']").attr("disabled","disabled");
						}
						$("select[name='vson_area'] option").remove();
						for ( var i = 0; i < result.length; i++) {
							$("select[name='vson_area']").append(
									"<option value=" + result[i].AREA_ID + ">"
											+ result[i].NAME + "</option>");
						}
					}
				}
			})
			
			$.ajax({          
				  async:false,
				  type:"post",
				  url :webPath + "General/getLocalCompanys.do",
				  data:{areaId:area_id},
				  dataType:"json",
				  success:function(data){
					  $("#own_company").empty();
					  $("#own_company").append("<option value=''>--请选择--</option>");		
					  $.each(data.companyList,function(i,item){
						  $("#own_company").append("<option value='"+item.ORG_ID+"'>"+item.ORG_NAME+"</option>");		
					  });
				  }
			  });
		}

		function clearCondition(form_id) {
			//$(form_id).form('reset','none');
			$("input[name='staff_name']").val('');
			$("input[name='staff_no']").val('');
			$("input[name='org_name']").val('');
			$("input[name='role_name']").val('');
			if($("#ifGLY").val()=="0"){
			$("select[name='area']").val('');
			$("select[name='son_area'] option").remove();
			$("select[name='son_area']").append("<option value=''>请选择</option>");
			}else{
			$("select[name='son_area']").val('');
			}
			
			
			
		}

		function addStaff() {
			$('#win_staff').window({
				title : "【新增员工】",
				href : webPath + "Staff/add.do",
				width : 400,
				height : 500,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		function editStaff() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show'
				});
				return;
			} else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show'
				});
				return;
			} else {
				var staff_id = selected[0].STAFF_ID;
				$('#win_staff').window({
					title : "【编辑员工】",
					href : webPath + "Staff/edit.do?staff_id=" + staff_id,
					width : 400,
					height : 500,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			}

		}

		function delStaff() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取要删除的数据!',
					showType : 'show'
				});
				return;
			} else {
				$.messager.confirm('系统提示', '您确定要删除员工吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].STAFF_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$('#form').form('submit', {
							url : webPath + "Staff/delete.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '成功删除员工!',
									showType : 'show'
								});
							}
						});
					}
				});
			}
		}


		//staff-add.jsp
		function clearForm() {
			$("input[name='vstaff_name']").val('');
			$("input[name='vstaff_no']").val('');
			$("input[name='vpassword_1']").val('');
			$("input[name='vpassword_2']").val('');

			$("input[name='vtel']").val('');
			$("input[name='vid_number']").val('');
			$("input[name='vemail']").val('');
			$("select[name='varea']").val('');
			$("select[name='vson_area']").val('');
			$("select[name='vstatus']").val('1');
		}
		function saveForm() {
			var pass = $("#ff").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要新增员工吗?', function(r) {
					if (r) {
						var staff_name = $("input[name='vstaff_name']").val();
						var staff_no = $("input[name='vstaff_no']").val();
						var password = $("input[name='vpassword_1']").val();
						var tel = $("input[name='vtel']").val();
						var id_number = $("input[name='vid_number']").val();
						var email = $("input[name='vemail']").val();
						var area = $("select[name='varea']").val();
						var son_area = $("select[name='vson_area']").val();
						var status = $("select[name='vstatus']").val();
						var own_company = $("select[name='own_company']").val();
						var maintor_type = $("select[name='maintor_type']").val();
						
						$.ajax({
							type : 'POST',
							url : webPath + "check/checkIdNumber.do",
							data : {
								id_number:id_number,
								user_name:staff_name
							},
							dataType : 'json',
							success : function(json) {
								if(json.result != "00"){
									$.messager.alert('警告','姓名与身份证号码不一致，请核对！');
								}else{
									$.ajax({
										type : 'POST',
										url : webPath + "Staff/save.do",
										data : {
											staff_name : staff_name,
											staff_no : staff_no,
											password : password,
											tel : tel,
											id_number : id_number,
											email : email,
											area : area,
											son_area : son_area,
											status : status,
											own_company : own_company,
											maintor_type : maintor_type
										},
										dataType : 'json',
										success : function(json) {
											if (json.status) {
												$.messager.alert("提示","新增员工成功！","info");
											}
											else{
												$.messager.alert("提示","新增员工失败！","info");
												return;
											}
											$('#win_staff').window('close');
											searchData();
										}
									});
								}
							}
						});
					}
				});
			}
		}
		function updateForm() {
			var pass = $("#ff").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要更新员工吗?', function(r) {
					if (r) {
						var staff_id = $("input[name='vstaff_id']").val();
						var staff_name = $("input[name='vstaff_name']").val();
						var staff_no = $("input[name='vstaff_no']").val();
						var password = $("input[name='vpassword_1']").val();
						var tel = $("input[name='vtel']").val();
						var id_number = $("input[name='vid_number']").val();
						var email = $("input[name='vemail']").val();
						var area = $("select[name='varea']").val();
						var son_area = $("select[name='vson_area']").val();
						var status = $("select[name='vstatus']").val();
						var own_company = $("select[name='own_company']").val();
						var maintor_type = $("select[name='maintor_type']").val();
						
						$.ajax({
							type : 'POST',
							url : webPath + "Staff/update.do",
							data : {
								staff_id : staff_id,
								staff_name : staff_name,
								staff_no : staff_no,
								password : password,
								tel : tel,
								id_number : id_number,
								email : email,
								area : area,
								son_area : son_area,
								status : status,
								own_company : own_company,
								maintor_type : maintor_type
							},
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '更新员工成功!',
										showType : 'show'
									});
								}
								$('#win_staff').window('close');
								searchData();
							}
						})
					}
				});
			}
		}
		
function assignSoftPermissions() {
    var selected = $('#dg').datagrid('getChecked');
    var count = selected.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取一条数据!',
            showType: 'show'
        });
        return;
    } else if (count > 1) {
        $.messager.show({
            title: '提  示',
            msg: '仅能选取一条数据!',
            showType: 'show'
        });
        return;
    } else {
        var staff_id = selected[0].STAFF_ID;
        $("#win_staff").window({
            title: "【应用赋权】",
            href : webPath + "Staff/assignSoftPermissions.do?staff_id="+staff_id,
			width : 500,
			height : 500,
            zIndex: 2,
            region: "center",
            collapsible: false,
            cache: false,
            modal: true
        });
        
    }
}

function assignRolePermissions() {
    var selected = $('#dg').datagrid('getChecked');
    var count = selected.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取一条数据!',
            showType: 'show'
        });
        return;
    } else if (count > 1) {
        $.messager.show({
            title: '提  示',
            msg: '仅能选取一条数据!',
            showType: 'show'
        });
        return;
    } else {
        var staff_id = selected[0].STAFF_ID;
        $("#win_staff").window({
            title: "【角色赋权】",
            href : webPath + "Staff/assignRolePermissions.do?staff_id="+staff_id,
			width : 500,
			height : 500,
            zIndex: 2,
            region: "center",
            collapsible: false,
            cache: false,
            modal: true
        });
        
    }
}



function  assignOutSitePermissions(){

var selected = $('#dg').datagrid('getChecked');
    var count = selected.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取一条数据!',
            showType: 'show'
        });
        return;
    } else if (count > 1) {
        $.messager.show({
            title: '提  示',
            msg: '仅能选取一条数据!',
            showType: 'show'
        });
        return;
    } else {
        var staff_id = selected[0].STAFF_ID;
        $("#win_staff").window({
            title: "【外力点赋权】",
            href : webPath + "Staff/assignOutSitePermissions.do?staff_id="+staff_id,
			width : 500,
			height : 500,
            zIndex: 2,
            region: "center",
            collapsible: false,
            cache: false,
            modal: true
        });
        
    }
}



function exportStaff() {
	$.messager.confirm('系统提示', '您确定要导出员工信息吗?', function (r) {
            if (r) {
                
                $('#form').form('submit', {
                    url: webPath + "Staff/export.do",
                    onSubmit: function () {
                        //$.messager.progress();
                    },
                    success: function () {
                        //$.messager.progress('close'); // 如果提交成功则隐藏进度条
                        $.messager.show({
                            title: '提  示',
                            msg: '导出员工信息成功!',
                            showType: 'show'
                        });
                        
                    }
                });
            }
        });
        
}

function importStaff() {
	$('#win_staff').window({
		title : "【导入用户】",
		href : webPath + "Staff/import.do",
		width : 400,
		height : 250,
		zIndex : 2,
		region : "center",
		collapsible : false,
		cache : false,
		modal : true
	});
}

function downloadExample(){
	$("#hiddenIframe").attr("src", "./downloadExample.do");
}

function importUsers(){
	$.messager.confirm('系统提示', '您确定要导入用户吗?', function(r) {
		if (r) {
			 $('#sv').form('submit', {
                 url: webPath + "Staff/import_save.do",
                 onSubmit: function () {
                     $.messager.progress();
                 },
                 success: function (data) {
                     $.messager.progress('close'); // 如果提交成功则隐藏进度条
                     var json = eval('(' + data + ')');
                     if(json.status){
                    	 $.messager.show({
	                         title: '提  示',
	                         msg: '导入用户成功!',
	                         showType: 'show'
	                     });
                    	 $('#win_staff').window('close');
                     } else {
                    	 $.messager.show({
	                         title: '提  示',
	                         msg: json.message,
	                         showType: 'show',
	                         width : 350,
	                         height : 150
	                     });
                     }
                 }
             });
		}
	});
}

function updateAsssignOutSite(){
var pass = $("#formEdit").form('validate');
			if (pass) {
				$.messager
						.confirm(
								'系统提示',
								'您确定要修改人员外力点权限吗?',
								function(r) {
									if (r) {
										var obj = makeParamJson('formEdit');
										$.ajax({
													type : 'POST',
													url : webPath
															+ "Staff/saveAssignOutSitePermissions.do",
													data : obj,
													dataType : 'json',
													success : function(json) {
														if (json.status) {
															$.messager
																	.show({
																		title : '提  示',
																		msg : '更新人员外力点权限成功!',
																		showType : 'show',
																		timeout : '1000'//ms
																	});
														}
														$('#win_staff').window(
																'close');
														searchData();
													}
												})
									}
								});
			}
		}
function girdManage(){
	var selected = $('#dg').datagrid('getChecked');
    var count = selected.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取一条数据!',
            showType: 'show'
        });
        return;
    } else if (count > 1) {
        $.messager.show({
            title: '提  示',
            msg: '仅能选取一条数据!',
            showType: 'show'
        });
        return;
    } else {
        var staff_id = selected[0].STAFF_ID;
        $("#win_staff").window({
            title: "【角色赋权】",
            href : webPath + "Staff/gridManage.do?staff_id="+staff_id,
			width : 500,
			height : 500,
            zIndex: 2,
            region: "center",
            collapsible: false,
            cache: false,
            modal: true
        });
        
    }	
}
</script>

</body>
</html>