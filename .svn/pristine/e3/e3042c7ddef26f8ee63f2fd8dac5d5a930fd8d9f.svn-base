<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../util/head.jsp"%>
		<title>选择员工</title>
	</head>
	<body style="padding: 3px; border: 0px">
			<div class="condition-container">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<input name="selected_staffId" type="hidden" />
					<table class="condition">
						<tr>
							<td width="10%">
								账号：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input name="staff_no" type="text" class="condition-text" />
								</div>
							</td>
							<td width="10%">
								姓名：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input name="staff_name" type="text" class="condition-text" />
									<input name="dept_id" type="hidden" class="condition-text" value="${dept_id}"/>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div class="btn-operation" onClick="queryStaff()">
					查询
				</div>
				<div class="btn-operation" onClick="submitStaff()">
					确定
				</div>
			</div>
		<table id="dg_plan" title="【选择员工】" style="width: 100%; height: 580px">
		</table>
		<div id="tb_plan" style="padding: 5px; height: auto"></div>
		<script type="text/javascript">
		$(document).ready(function() {
			queryStaff();
		});
		function queryStaffby(){
			queryStaff();
		}
		function queryStaff() {
			var staff_name = $("input[name='staff_name']").val().trim();
			var staff_no = $("input[name='staff_no']").val().trim();
			var dept_id = $("input[name='dept_id']").val().trim();
			var obj = {
				staff_name : staff_name,
				staff_no : staff_no,
				dept_id :dept_id
			};
			//return;
			$('#dg_plan').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 450,
				height : 380,
				toolbar : '#tb_plan',
				url : webPath + "Dept/query_staff.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				//pageNumber : 1,
				//pageSize : 10,
				pageList : [20,50,200,500],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				columns : [ [ {
					field : 'STAFF_ID',
					title : '员工ID',
					checkbox : true
				}, {
					field : 'STAFF_NO',
					title : '员工帐号',
					width : "8%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '员工名称',
					width : "12%",
					align : 'center'
				},
				{
					field : 'DEPT_NAME',
					title : '所属班组',
					width : "12%",
					align : 'center'
				}] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
					var rows = $("#dg_plan").datagrid("getRows"); 
					for(var i=0;i<rows.length;i++){
					    var type = rows[i].TYPE;
						if(type == 1){
							$('#dg_plan').datagrid('selectRow',i);
						}
					}
					
				}
			});
			
		}
		
	function submitStaff() {
	    var selected = $('#dg_plan').datagrid('getChecked');
	    var count = selected.length;
	    var title ="您确定要分配班组吗?";
	    var staffArr = new Array();
	    if (count == 0) {
	       title = "是否把此班组的人员全部删除?"
	    } 
	    for (var i = 0; i < selected.length; i++) {
            var value = selected[i].STAFF_ID;
            staffArr[staffArr.length] = value;
	     }
	     $("input[name='selected_staffId']").val(staffArr);
        $.messager.confirm('系统提示', title, function (r) {
            if (r) {   
                /* var arr = new Array();
                var inspector_type;
                for (var i = 0; i < selected.length; i++) {
                    var value = selected[i].STAFF_ID;
                    arr[arr.length] = value;
                }
                $("input[name='selected_staffId']").val(arr);
                $('#form_task').form('submit', {
                    url: webPath + "Dept/distributeStaff.do",
                    onSubmit: function () {
                        $.messager.progress();
                    },
                    success: function () {
                        $.messager.progress('close'); // 如果提交成功则隐藏进度条
                        $.messager.show({
                            title: '提  示',
                            msg: '任务分配成功!',
                            showType: 'show'
                        });
                        $('#win_staff').window('close');
                        searchData();
                    }
                }); */
               
                $.ajax({
						type : 'POST',
						url : webPath +"Dept/distributeStaff.do",
						data : {
							staffArr : $("input[name='selected_staffId']").val().trim(),
							dept_id : $("input[name='dept_id']").val().trim()							
						},
						dataType : 'json',
						success : function(json) {
							if (json.ifsuccess) {
								$.messager.show({
									title : '提  示',
									msg : '保存成功!',
									showType : 'show'
								});
							}
							//$("#tabs" ).tabs({selected:2});
							//parent.$("#tabs").tabs("select", "缆线管理");
							//closeTab();

							//setTimeout(closeTab(),5000);
							location.href = 'index.do';
						}
					});
            }
        });
	}
	</script>
	</body>
</html>