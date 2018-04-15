<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="../../util/head.jsp"%>
		<title>井盖状态</title>
	</head>
	<body class="easyui-layout">
		<div>
			<form id="coverState" action="" method="post">
				<table class="condition">
					<tr>
						<td width="8%">井盖状态：</td>
						<td width="16%">
							<select id="stateId" class="condition-select" >
								<option value="">请选择</option>
								<option value="0">正常</option>
								<option value="1">小角度倾斜</option>
								<option value="2">大角度倾斜</option>
								<option value="3">翻转</option>
								<option value="4">低电压</option>
							</select>
						</td>
						<td width="8%">归属单位：</td>
						<td width="16%">
							<select id="stateId" class="condition-select" >
								<option value="">请选择</option>
								<option value="0">南京</option>
								<option value="1">苏州</option>
							</select>
						</td>
						<td width="8%">派遣情况：</td>
						<td width="16%">
							<select id="stateId" class="condition-select" >
								<option value="">请选择</option>
								<option value="0">未派遣</option>
								<option value="1">已派遣</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="8%">在线情况：</td>
						<td width="16%">
							<select id="stateId" class="condition-select" >
								<option value="">请选择</option>
								<option value="0">在线</option>
								<option value="1">不在线</option>
							</select>
						</td>
						<td width="8%">位置描述：</td>
						<td width="16%">
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
		<table class="easyui-datagrid" data-options="fit:true,singleSelect:true,pageNumber:1,pageSize:10,pageList:[20,50],pagination:true" >
			<thead>
				<tr>
					<th data-options="field:'id',checkbox:true">ID</th>
					<th data-options="field:'code',width:180">编号</th>
					<th data-options="field:'name',width:90">归属单位</th>
					<th data-options="field:'address',width:100">在线情况</th>
					<th data-options="field:'area',width:90">井盖状态</th>
					<th data-options="field:'remark',width:180">告警时间</th>
					<th data-options="field:'info',width:100">维修情况</th>
					<th data-options="field:'before_photo',width:450">位置描述</th>
					<th data-options="field:'before_photo1'">操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1006</td>
					<td>0865153030004627</td>
					<td>苏州</td>
					<td>不在线</td>
					<td>翻转</td>
					<td>2017/07/03 17:37</td>
					<td>未派遣</td>
					<td>访仙局访高路光交华通针织有限公司后墙GPON华为1#分光器</td>
					<td><a href="javascript:show();">详情</a></td>
				</tr>
				<tr>
					<td>1007</td>
					<td>0865153030001466</td>
					<td>苏州</td>
					<td>在线</td>
					<td>翻转</td>
					<td>2017/09/18 17:37</td>
					<td>已派遣</td>
					<td>珥陵局十字路口光交春光市场路对面OLT0002GPON华为1#分光器</td>
					<td><a href="javascript:show();">详情</a></td>
				</tr>
				<tr>
					<td>1006</td>
					<td>0865153030004627</td>
					<td>苏州</td>
					<td>不在线</td>
					<td>翻转</td>
					<td>2017/07/03 17:37</td>
					<td>未派遣</td>
					<td>访仙局访高路光交华通针织有限公司后墙GPON华为1#分光器</td>
					<td><a href="javascript:show();">详情</a></td>
				</tr>
				<tr>
					<td>1006</td>
					<td>0865153030004627</td>
					<td>苏州</td>
					<td>不在线</td>
					<td>翻转</td>
					<td>2017/07/03 17:37</td>
					<td>未派遣</td>
					<td>访仙局访高路光交华通针织有限公司后墙GPON华为1#分光器</td>
					<td><a href="javascript:show();">详情</a></td>
				</tr>
				<tr>
					<td>1006</td>
					<td>0865153030004627</td>
					<td>苏州</td>
					<td>不在线</td>
					<td>翻转</td>
					<td>2017/07/03 17:37</td>
					<td>未派遣</td>
					<td>访仙局访高路光交华通针织有限公司后墙GPON华为1#分光器</td>
					<td><a href="javascript:show();">详情</a></td>
				</tr>
			</tbody>
		</table>
	</body>
</html>