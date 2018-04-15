<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="../../util/head.jsp"%>
		<title>井盖管理</title>
	</head>
	<body>
		<div>
			<form id="coverMng" action="" method="post">
				<table class="condition">
					<tr>
						<td width="8%">归属单位：</td>
						<td width="16%">
							<select id="stateId" class="condition-select" >
								<option value="">请选择</option>
								<option value="0">南京</option>
								<option value="1">苏州</option>
							</select>
						</td>
						<td width="8%">编号：</td>
						<td width="16%">
							<div class="condition-text-container">
								<input name="addressDesc" id="addressDesc" class="condition-text" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="btn-operation" onClick="searchData()">新增</div>
						</td>
						<td></td><td></td>
						<td>
							<div class="btn-operation" onClick="reform()">查询</div>
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
						<th data-options="field:'code',width:180">编号</th>
						<th data-options="field:'name',width:180">经纬度</th>
						<th data-options="field:'area',width:90">归属单位</th>
						<th data-options="field:'remark',width:400">位置描述</th>
						<th data-options="field:'address',width:180">告警策略</th>
						<th data-options="field:'before_photo1',width:150">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>281</td>
						<td>0865153030001466</td>
						<td>108.888708,34.191241</td>
						<td>南京</td>
						<td>新民金色家园光交金色家园C幢3单元1楼GPON华为2#分光器</td>
						<td>默认告警策略</td>
						<td>
							<a href="javascript:show();">详情</a>
							<a href="javascript:show();">编辑</a>
							<a href="javascript:show();">设置</a>
							<a href="javascript:show();">删除</a>
						</td>
					</tr>
					<tr>
						<td>282</td>
						<td>0865153030004627</td>
						<td>108.855313,34.187602</td>
						<td>南京</td>
						<td>皇塘底田下光交底田下03P003杆GPON华为1#分光器</td>
						<td>默认告警策略</td>
						<td>
							<a href="javascript:show();">详情</a>
							<a href="javascript:show();">编辑</a>
							<a href="javascript:show();">设置</a>
							<a href="javascript:show();">删除</a>
						</td>
					</tr>
					<tr>
						<td>281</td>
						<td>0865153030001466</td>
						<td>108.888708,34.191241</td>
						<td>南京</td>
						<td>新民金色家园光交金色家园C幢3单元1楼GPON华为2#分光器</td>
						<td>默认告警策略</td>
						<td>
							<a href="javascript:show();">详情</a>
							<a href="javascript:show();">编辑</a>
							<a href="javascript:show();">设置</a>
							<a href="javascript:show();">删除</a>
						</td>
					</tr>
					<tr>
						<td>281</td>
						<td>0865153030001466</td>
						<td>108.888708,34.191241</td>
						<td>南京</td>
						<td>新民金色家园光交金色家园C幢3单元1楼GPON华为2#分光器</td>
						<td>默认告警策略</td>
						<td>
							<a href="javascript:show();">详情</a>
							<a href="javascript:show();">编辑</a>
							<a href="javascript:show();">设置</a>
							<a href="javascript:show();">删除</a>
						</td>
					</tr>
					<tr>
						<td>281</td>
						<td>0865153030001466</td>
						<td>108.888708,34.191241</td>
						<td>南京</td>
						<td>新民金色家园光交金色家园C幢3单元1楼GPON华为2#分光器</td>
						<td>默认告警策略</td>
						<td>
							<a href="javascript:show();">详情</a>
							<a href="javascript:show();">编辑</a>
							<a href="javascript:show();">设置</a>
							<a href="javascript:show();">删除</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</body>
</html>