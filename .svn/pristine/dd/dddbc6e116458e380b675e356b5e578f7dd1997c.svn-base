<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="condition-container">
	<form id="addForm" method="post">
		<input name="relayinfoId" value="${relayinfoId}"
			type="hidden" /> 
		<table class="condition" >
			<tr>
					<td  colspan="3">接头点1</td>
					<td  colspan="3">接头点2</td>
				</tr>
				<tr>
					<td>A向km<input type="text" name="directiona1"
						 />
					</td>
					<td>B向<input type="text" name="directionb1"
						/>

					</td>
					<td>平均<input type="text" name="avg1"  />

					</td>
					<td>A向km<input type="text" name="directiona2"
						 />

					</td>
					<td>B向<input type="text" name="directionb2"
						 />

					</td>
					<td>平均<input type="text" name="avg2"  />

					</td>
				</tr>
				
				
				
				<tr>
					<td  colspan="3">接头点3</td>
					<td  colspan="3">接头点4</td>
				</tr>
				<tr>
					<td>A向km<input type="text" name="directiona3"
						 />

					</td>
					<td>B向<input type="text" name="directionb3"
						 />

					</td>
					<td>平均<input type="text" name="avg3"  />

					</td>
					<td>A向km<input type="text" name="directiona4"
						 />

					</td>
					<td>B向<input type="text" name="directionb4"
						 />

					</td>
					<td>平均<input type="text" name="avg4"  />

					</td>
				</tr>

				<tr>
					<td  colspan="3">接头点5</td>
					<td  colspan="3">接头点6</td>
				</tr>
				<tr>
					<td>A向km<input type="text" name="directiona5"
						/>

					</td>
					<td>B向<input type="text" name="directionb5"
						 />

					</td>
					<td>平均<input type="text" name="avg5" />

					</td>
					<td>A向km<input type="text" name="directiona6"
						/>

					</td>
					<td>B向<input type="text" name="directionb6"
						 />

					</td>
					<td>平均<input type="text" name="avg6"  />

					</td>
				</tr>

				<tr>
					<td >割接前总耗损dB</td>
					<td >割接后总耗损dB</td>
					<td >衰减系数dB/km</td>
				</tr>
				<tr>
					<td ><input type="text" name="beforedb" 
						 /></td>
					<td ><input type="text" name="db"  /></td>
					<td ><input type="text" name="dbkm"  />

					</td>
				</tr>
		</table>
	</form>
</div>
<div class="btn-operation-container" style="width:150px;margin: 0px auto;">
	<div class="btn-operation"
		onClick="addTestDetailInfo()">提交</div>
</div>
<script type="text/javascript">
	function addTestDetailInfo(){
		$("#addForm").form('submit', {
			url : webPath + "/CutAndConnOfFiberController/addTestDetailInfo.do",
			onSubmit : function() {
				// do some check    
				// return false to prevent submit;    
			},
			success : function(data) {
				var json=$.parseJSON(data);
				if(json.status){
					$('#win').window('close');
					$("#dg").datagrid('reload');
					$.messager.alert("提示","新增成功");
				}else{
					$.messager.alert("提示","新增失败");
				}
			}
		});
	}
</script>
