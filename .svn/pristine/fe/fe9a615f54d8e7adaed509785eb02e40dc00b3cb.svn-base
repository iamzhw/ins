<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="../../util/head.jsp"%>
		<title>工单管理</title>
	</head>
	<body class="easyui-layout">
		<div>
			<form id="taskMng" action="" method="post">
				<table class="condition">
					<tr>
						<td width="8%">创建时间：</td>
						<td width="16%">
							<div class="condition-text-container">
								<input class="condition-text condition" type="text" value="" name=START_TIME id="task_start_time" onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
							</div>
						</td>
						<td width="8%">工单进度：</td>
						<td width="16%">
							<select id="stateId" class="condition-select" >
								<option value="">请选择</option>
								<option value="0">待处理</option>
								<option value="1">维修中</option>
								<option value="2">已完成</option>
							</select>
						</td>
						<td width="8%">创建者：</td>
						<td width="16%">
							<div class="condition-text-container">
								<input name="addressDesc" id="addressDesc" class="condition-text" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="8%">维修人员：</td>
						<td width="16%">
							<div class="condition-text-container">
								<input name="addressDesc" id="addressDesc" class="condition-text" />
							</div>
						</td>
						<td width="8%">井盖编号：</td>
						<td width="16%">
							<div class="condition-text-container">
								<input name="addressDesc" id="addressDesc" class="condition-text" />
							</div>
						</td>
						<td width="8%">工单名称：</td>
						<td>
							<div class="condition-text-container">
								<input name="addressDesc" id="addressDesc" class="condition-text" />
							</div>
						</td>
					</tr>
					<tr>
						<td></td><td></td><td></td><td></td>
						<td>
							<div class="btn-operation" onClick="searchData()">查询</div>
						</td>
						<td>
							<div class="btn-operation" onClick="reform()">重置</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div>
			<table class="easyui-datagrid" data-options="fit:false,singleSelect:true,pageNumber:1,pageSize:10,pageList:[20,50],pagination:true" >
				<thead>
					<tr>
						<th data-options="field:'id',checkbox:true">ID</th>
						<th data-options="field:'code',width:180">工单名称</th>
						<th data-options="field:'name',width:90">井盖数</th>
						<th data-options="field:'area',width:100">创建者</th>
						<th data-options="field:'remark',width:180">创建时间</th>
						<th data-options="field:'address',width:120">维修人员</th>
						<th data-options="field:'info',width:180">完成时间</th>
						<th data-options="field:'before_photo',width:90">状态</th>
						<th data-options="field:'before_photo1',width:90">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>281</td>
						<td>测试工单</td>
						<td>1</td>
						<td>wulian</td>
						<td>2017/07/03 10:00</td>
						<td>wulianrepair</td>
						<td>2017/07/03 15:54</td>
						<td>已完成</td>
						<td><a href="javascript:show();">详情</a></td>
					</tr>
					<tr>
						<td>282</td>
						<td>测试工单02</td>
						<td>3</td>
						<td>wulian</td>
						<td>2017/09/19 10:00</td>
						<td>wulianrepair</td>
						<td>2017/09/19 15:54</td>
						<td>已完成</td>
						<td><a href="javascript:show();">详情</a></td>
					</tr>
					<tr>
						<td>281</td>
						<td>测试工单</td>
						<td>1</td>
						<td>wulian</td>
						<td>2017/07/03 10:00</td>
						<td>wulianrepair</td>
						<td>2017/07/03 15:54</td>
						<td>已完成</td>
						<td><a href="javascript:show();">详情</a></td>
					</tr>
					<tr>
						<td>281</td>
						<td>测试工单</td>
						<td>1</td>
						<td>wulian</td>
						<td>2017/07/03 10:00</td>
						<td>wulianrepair</td>
						<td>2017/07/03 15:54</td>
						<td>已完成</td>
						<td><a href="javascript:show();">详情</a></td>
					</tr>
					<tr>
						<td>281</td>
						<td>测试工单</td>
						<td>1</td>
						<td>wulian</td>
						<td>2017/07/03 10:00</td>
						<td>wulianrepair</td>
						<td>2017/07/03 15:54</td>
						<td>已完成</td>
						<td><a href="javascript:show();">详情</a></td>
					</tr>
					<tr>
						<td>281</td>
						<td>测试工单</td>
						<td>1</td>
						<td>wulian</td>
						<td>2017/07/03 10:00</td>
						<td>wulianrepair</td>
						<td>2017/07/03 15:54</td>
						<td>已完成</td>
						<td><a href="javascript:show();">详情</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</body>
</html>