<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>版本管理</title>
</head>
<body style="padding:3px;border:0px">
	<table id="dg" title="【版本管理】" style="width:100%;height:480px">
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post" enctype="multipart/form-data">
			<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">版本号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="version_no" id="version_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">应用名称：</td>
						<td width="20%"><select name="software_id" id="software_id" class="condition-select">
								<option value="">请选择</option>
								<c:forEach items="${softwareList}" var="sl">
									<option value="${sl.SOFTWARE_ID}">${sl.SOFTWARE_NAME}</option>
								</c:forEach>
						</select></td>
						<td width="40%">
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="addVersion()">增加</div>
			<div class="btn-operation" onClick="delVersion()">删除</div>
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div class="btn-operation" onClick="searchData()" style="float:right;">查询</div>
		</div>
	</div>
	<div id="win_version"></div>

	<script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
		function searchData() {		   
			var version_no = $("#version_no").attr("value");
			var software_id = $("#software_id").attr("value");		
			var obj = {
				version_no : version_no,
				software_id : software_id			
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Version/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [{
					field : 'SOFTWARE_VERSION_ID',
					title : '版本ID',
					width : "3%",
					align : 'center',
					checkbox : true
				},{
					field : 'VERSION_NUM',
					title : '版本号',
					width : "12%",
					align : 'center'
				},{
					field : 'VERSION_INFO',
					title : '版本名称',
					width : "12%",
					align : 'center'
				},{
					field : 'DRIVATE_TYPE',
					title : '驱动类型',
					width : "12%",
					align : 'center' 
				},
				 {
					field : 'SOFTWARE_NAME',
					title : '应用名称',
					width : "12%",
					align : 'center'
				}, {
					field : 'PACKAGE_NAME',
					title : '包名',
					width : "12%",
					align : 'center'
				},{
					field : 'COMMENTS',
					title : '版本说明',
					width : "12%",
					align : 'center'
				},{
					field : 'CREATE_STAFF',
					title : '创建人',
					width : "12%",
					align : 'center'
				}, {
					field : 'CREATE_DATE',
					title : '创建时间',
					width : "12%",
					align : 'center'
				}, {
					field : 'FILE_PATH',
					title : '操作',
					width : "10%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){

                            //function里面的三个参数代表当前字段值，当前行数据对象，行号（行号从0开始）

                            //alert(rowData.username);  
// 							alert(value);
							var url=window.location.href+"";
							var h=url.indexOf("132.228.237.107");
							if(h!=-1){
							//value="http://61.160.128.47:8080/files/zhxjAPK/2014-06-25/vpn.apk";
							value=value.replace('61.160.128.47','132.228.237.107');
							//alert(value);
							}
                            return "<a onclick='downLoadAPK("+rowData.SOFTWARE_VERSION_ID+")' >下载</a>";  
                       } 
				}
				<%--, {
					field : 'MODIFY_STAFF',
					title : '修改人',
					width : "10%",
					align : 'center'
				}, {
					field : 'MODIFY_DATE',
					title : '修改时间',
					width : "10%",
					align : 'center'
				}--%>
				] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onClickCell:onClickCell, 
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
				}
			});
		}
		
		function saveForm() {
			var pass = $("#sv").form('validate');			
			if (pass) {
				$.messager.confirm('系统提示', '您确定要新增版本吗?', function(r) {
					if (r) {
// 						var software_id = $("#software_id_add").val();
// 						var version_no = $("#version_no_add").attr("value");
// 						var version_info = $("#version_info_add").attr("value");
// 						var drivate_type = $("#drivate_type_add").val();
// 						var file = $("#file_add").attr("value");
// 						var if_force_update = $("#if_force_update_add").attr("value");	
// 						var state = $("#state_add").attr("value");		
// 						$.ajax({
// 							type : 'POST',
// 							url : webPath + "Version/save.do",
// 							data : {
// 								software_id : software_id,
// 								version_no : version_no,
// 								version_info : version_info,
// 								drivate_type : drivate_type,
// 								file : file,
// 								if_force_update : if_force_update,
// 								state:state
// 							},
// 							enctype:'multipart/form-data',
// 							dataType : 'json',
// 							success : function(json) {
// 								if (json.status) {
// 									$.messager.show({
// 										title : '提  示',
// 										msg : '新增版本成功!',
// 										showType : 'show'
// 									});
// 								}
// 								$('#win_version').window('close');
// 								searchData();
// 							}
// 						});
                $('#sv').form('submit', {
                    url: webPath + "Version/save.do",
                    onSubmit: function () {
                        $.messager.progress();
                    },
                    success: function (data) {
                    	var dt = JSON.parse(data);
                        $.messager.progress('close'); // 如果提交成功则隐藏进度条
                        if(dt.status){
	                        $.messager.show({
	                            title: '提  示',
	                            msg: '版本新增成功!',
	                            showType: 'show'
	                        });
                        }else{
                        	$.messager.show({
	                            title: '提  示',
	                            msg: '版本新增失败!',
	                            showType: 'show'
	                        });
                        }
                        $('#win_version').window('close');
                        
                    }
                });

					}
				});
			}
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
		function onClickCell(rowIndex, field, value){
			alert(rowIndex);
			alert(field);
			alert(value);
		}
		function onSelectAll(rows) {
			alert(rows);
			alert("onSelectAll");
		}

		$(function() {
			//查询
			$("select[name='area']").change(function() {
				//根据area_id，获取区县
				getSonAreaList('query');
			});
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
						$("select[name='vson_area'] option").remove();
						//$("select[name='vson_area']").append(
						//		"<option value=''>请选择</option>");
						for ( var i = 0; i < result.length; i++) {
							$("select[name='vson_area']").append(
									"<option value=" + result[i].AREA_ID + ">"
											+ result[i].NAME + "</option>");
						}
					}

				}
			})
		}

		function clearCondition(form_id) {
			//$(form_id).form('reset','none');
			$("input[name='version_no']").val('');
			$("input[name='software_no']").val('');
		}

		function addVersion() {
			$('#win_version').window({
				title : "【新增版本】",
				href : webPath + "Version/add.do",
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
					title : "【编辑版本】",
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

		function delVersion() {
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
				$.messager.confirm('系统提示', '您确定要删除版本吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].SOFTWARE_VERSION_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$('#form').form('submit', {
							url : webPath + "Version/delete.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '成功删除版本!',
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
			$("input[name='version_no']").val('');
			$("input[name='software_no']").val('');
		}
		
		function updateForm() {
			var pass = $("#ff").form('validate');
			if (pass) {
				$.messager.confirm('系统提示', '您确定要更新版本吗?', function(r) {
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
								status : status
							},
							dataType : 'json',
							success : function(json) {
								if (json.status) {
									$.messager.show({
										title : '提  示',
										msg : '更新版本成功!',
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
		
		function downLoadAPK(softId){ 
			window.open(webPath + "Version/downLoadAPK.do?softId="+softId);
		}
	</script>
</body>
</html>