<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>角色赋权</title>
</head>
<body style="padding:3px;border:0px">
	<form id="form_role" action="" method="post">
		<input id="grid_id" name="grid_id" hidden="hidden" value="${grid_id}" /> <input
			name="selected_aduits" type="hidden" />
	</form>
	</div>
	<table id="dg_role" title="【选择管理员】" style="width:100%;height:480px">
	</table>
	<div id="tb_role" style="padding:5px;height:auto">
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="saveAduits()">提交</div>

		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			queryAduits();
		});

		function queryAduits() {
			var obj = {
             grid_id:$("#grid_id").val()
			};
			//return;
			$('#dg_role').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				width : 450,
				height : 480,
				toolbar : '#tb_role',
				url : webPath + "Grid/queryAduits.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 5, 10, 20, 50 ],
				loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'STAFF_ID',
					title : '用户ID',
					checkbox : true
				}, {
					field : 'STAFF_NAME',
					title : '用户名称',
					width : "21.5%",
					align : 'center'
				}, {
					field : 'STAFF_NO',
					title : '用户编码',
					width : "20%",
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
					var rows = $("#dg_role").datagrid("getRows"); 
					var aduits = ${aduits};
					for(var i=0;i<rows.length;i++){
					    var STAFF_ID = rows[i].STAFF_ID;
					    var flag = 0;
						for(var j=0;j<aduits.length;j++){
							if(STAFF_ID==aduits[j]){
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
		
	function saveAduits() {
    var selected = $('#dg_role').datagrid('getChecked');
    var count = selected.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取网格管理员!',
            showType: 'show'
        });
        return;
    } else {
        $.messager.confirm('系统提示', '您确定要选择管理员吗?', function (r) {
            if (r) {
                var arr = new Array();
                for (var i = 0; i < selected.length; i++) {
                    var value = selected[i].STAFF_ID;
                    arr[arr.length] = value;
                }
                $("input[name='selected_aduits']").val(arr);
                $('#form_role').form('submit', {
                    url: webPath + "Grid/saveAduits.do",
                    onSubmit: function () {
                        $.messager.progress();
                    },
                    success: function () {
                        $.messager.progress('close'); // 如果提交成功则隐藏进度条
                        $.messager.show({
                            title: '提  示',
                            msg: '选择管理员成功!',
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