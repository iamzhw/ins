
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title></title>
<style type="text/css">
	table{
		
	}
</style>
</head>
<body style="padding: 3px; border: 0px">
		<div class="condition-container">
			<form id="updForm"  method="post">
			<input name="relayinfoId" value="${rows[0].RELAYID }" type="hidden"/>
			<input name="i_index" value="${rows[0].I_INDEX }" type="hidden"/>
			<table class="condition">
				<tr>
					<td  colspan="3">接头点1</td>
					<td  colspan="3">接头点2</td>
				</tr>
				<tr>
					<td>A向km<input type="text" name="directiona1"
						value="${rows[0].DIRECTIONA1 }" />
					</td>
					<td>B向<input type="text" name="directionb1"
						value="${rows[0].DIRECTIONB1 }" />

					</td>
					<td>平均<input type="text" name="avg1" value="${rows[0].AVG1 }" />

					</td>
					<td>A向km<input type="text" name="directiona2"
						value="${rows[0].DIRECTIONA2 }" />

					</td>
					<td>B向<input type="text" name="directionb2"
						value="${rows[0].DIRECTIONB2 }" />

					</td>
					<td>平均<input type="text" name="avg2" value="${rows[0].AVG2 }" />

					</td>
				</tr>
				
				
				
				<tr>
					<td  colspan="3">接头点3</td>
					<td  colspan="3">接头点4</td>
				</tr>
				<tr>
					<td>A向km<input type="text" name="directiona3"
						value="${rows[0].DIRECTIONA3 }" />

					</td>
					<td>B向<input type="text" name="directionb3"
						value="${rows[0].DIRECTIONB3 }" />

					</td>
					<td>平均<input type="text" name="avg3" value="${rows[0].AVG3 }" />

					</td>
					<td>A向km<input type="text" name="directiona4"
						value="${rows[0].DIRECTIONA4 }" />

					</td>
					<td>B向<input type="text" name="directionb4"
						value="${rows[0].DIRECTIONB4 }" />

					</td>
					<td>平均<input type="text" name="avg4" value="${rows[0].AVG4 }" />

					</td>
				</tr>

				<tr>
					<td  colspan="3">接头点5</td>
					<td  colspan="3">接头点6</td>
				</tr>
				<tr>
					<td>A向km<input type="text" name="directiona5"
						value="${rows[0].DIRECTIONA5 }" />

					</td>
					<td>B向<input type="text" name="directionb5"
						value="${rows[0].DIRECTIONB5 }" />

					</td>
					<td>平均<input type="text" name="avg5" value="${rows[0].AVG5 }" />

					</td>
					<td>A向km<input type="text" name="directiona6"
						value="${rows[0].DIRECTIONA6 }" />

					</td>
					<td>B向<input type="text" name="directionb6"
						value="${rows[0].DIRECTIONB6 }" />

					</td>
					<td>平均<input type="text" name="avg6" value="${rows[0].AVG6 }" />

					</td>
				</tr>

				<tr>
					<td >割接前总耗损dB</td>
					<td >割接后总耗损dB</td>
					<td >衰减系数dB/km</td>
				</tr>
				<tr>
					<td ><input type="text" name="beforedb" 
						value="${rows[0].BEFOREDB }" /></td>
					<td ><input type="text" name="db" value="${rows[0].DB }" /></td>
					<td ><input type="text" name="dbkm" value="${rows[0].DBKM }" />

					</td>
				</tr>
			</table>
		</form>
		</div>
	<div class="btn-operation-container" style="width:150px;margin: 0px auto;">
		<div class="btn-operation" onClick="updTestDetailInfo()">提交</div>
	</div>
</body>
</html>
