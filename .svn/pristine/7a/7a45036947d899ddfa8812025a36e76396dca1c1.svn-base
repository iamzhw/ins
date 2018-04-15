<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/jsp/util/head.jsp"%>
<title>角色赋权</title>
</head>
<body style="padding:3px;border:0px" >
	<form id="form_role" action="" method="post">
	<input name="asp_staff_id" type="hidden" value="${asp_staff_id}" />
	<input name="selected_roles" type="hidden" />
	</form>
	<table id="dg_role" title="【角色赋权】" style="width:100%;height:480px">
	</table>
	<div id="tb_role" style="padding:5px;height:auto">
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="saveRolePermissions()">赋权</div>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			queryRolePermissions();

		});
		function queryRolePermissions() {
			var obj = {
				
			};
			//return;
			$('#dg_role').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 450,
				height : 480,
				toolbar : '#tb_role',
				url : webPath + "unifiedPage/queryRolePermissions.do",
				queryParams : obj,
				method : 'post',
				//pagination : true,
				//pageNumber : 1,
				//pageSize : 10,
				//pageList : [ 5, 10, 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'ROLE_ID',
					title : '角色ID',
					checkbox : true
				}, {
					field : 'ROLE_NAME',
					title : '角色名称',
					align : 'center'
				}, {
					field : 'ROLE_NO',
					title : '角色编码',
					align : 'center'
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					var rows = $("#dg_role").datagrid("getRows"); 
					var roles = ${roles};
					for(var i=0;i<rows.length;i++){
					    var role_id = rows[i].ROLE_ID;
					    var flag = 0;
						for(var j=0;j<roles.length;j++){
							if(role_id==roles[j]){
								flag = 1;
							}
						}
						if(flag == 1){
							$('#dg_role').datagrid('selectRow',i);
						}
					}
					
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
function saveRolePermissions() {
    var selected = $('#dg_role').datagrid('getChecked');
    var count = selected.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取角色权限!',
            showType: 'show'
        });
        return;
    } else {
        $.messager.confirm('系统提示', '您确定要角色赋权吗?', function (r) {
            if (r) {
                var arr = new Array();
                for (var i = 0; i < selected.length; i++) {
                    var value = selected[i].ROLE_ID;
                    arr[arr.length] = value;
                }
                $("input[name='selected_roles']").val(arr);
                $('#form_role').form('submit', {
                    url: webPath + "unifiedPage/saveRolePermissions.do",
                    onSubmit: function () {
                        $.messager.progress();
                    },
                    success: function () {
                        $.messager.progress('close'); // 如果提交成功则隐藏进度条
                        $.messager.show({
                            title: '提  示',
                            msg: '角色赋权成功!',
                            showType: 'show'
                        });
                        $('#win_staff').window('close');
                        
                    }
                });
            }
        });
    }
}
		
	</script>
</body>
</html>