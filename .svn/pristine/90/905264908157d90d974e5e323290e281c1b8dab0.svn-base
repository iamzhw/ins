package com.inspecthelper.service.impl;

import org.springframework.stereotype.Service;

import com.inspecthelper.model.CallEsb;

import icom.util.ExceptionCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class OssAbitiyServiceImpl {

	/**
	 * 调用ESB查询资源的XY轴信息（设备与光电缆）
	 * 
	 * @param areaCode
	 * @param resId
	 * @param resSpecId
	 * @return
	 */
	public String getResPointsFromESB(String areaCode, String resId,
			String resSpecId) {
		// 查询xy轴坐标
		JSONObject jsonPoint = new JSONObject();
		jsonPoint.put("areaCode", areaCode);
		JSONObject jsonb = new JSONObject();
		jsonb.put("resId", resId);
		jsonb.put("resTypeId", resSpecId);
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonb);
		jsonPoint.put("resIds", jsonArray);
		String resultStr = "";
		String pointStr = "";
		try {
			String point = this.getResPoints(jsonPoint.toString());
			JSONObject jsonResult = JSONObject.fromObject(point);
			JSONArray jArray = jsonResult.getJSONArray("resultInf");
			pointStr = jArray.getJSONObject(0).getString("points");
		} catch (Exception e) {
			return resultStr;
		}
		if (resSpecId.equals("701") || resSpecId.equals("-701")
				|| resSpecId.equals("-801") || resSpecId.equals("801")) {

			if (pointStr == null) {
				resultStr = "";
			} else {
				// pointStr="null,1 2,3 4,null,5 6,null";
				// 坐标点里面可能会存在null 所以要修正一下
				pointStr = pointStr.replace("null,", "");
				pointStr = pointStr.replace(",null", "");
				resultStr = pointStr;
			}
		} else {
			if (!pointStr.equals("")) {
				String[] points = pointStr.split(",");
				resultStr = points[0] + " " + points[1];
			} else {
				resultStr = "";
			}
		}
		return resultStr;
	}
	
	public String getResPoints(String jsonStr) {
		JSONObject inJSON = JSONObject.fromObject(jsonStr);
		String areaNo = inJSON.getString("areaCode").split("\\.")[0];
		;
		// if("sz".equals(inJSON.getString("areaCode").split("\\.")[1].trim())){
		//			
		// }else{
		// areaNo = inJSON.getString("areaCode").split("\\.")[1];
		// }

		// String areaNo = inJSON.getString("areaCode");
		JSONArray jsonArray = inJSON.getJSONArray("resIds");
		int size = jsonArray.size();
		JSONArray js = new JSONArray();
		for (int i = 0; i < size; i++) {
			JSONObject jo = new JSONObject();
			String resId = jsonArray.getJSONObject(i).getString("resId");
			String resTypeId = jsonArray.getJSONObject(i)
					.getString("resTypeId");
			String length = "";
			if (jsonArray.getJSONObject(i).has("length")) {
				length = jsonArray.getJSONObject(i).getString("length");
			}
			jo.put("resId", resId);
			jo.put("resTypeId", resTypeId);
			jo.put("length", length);
			js.add(jo);
		}
		CallEsb c = new CallEsb();
		String method = "getResPoints";// OSS提供方的接口名称
		Object[] obj = new Object[] { js.toString(), areaNo };
		String sendMsg = "";
		try {
			sendMsg = c.getCallEsbForGis(obj, method);
		} catch (Exception e) {
			return returnResultJson(ExceptionCode.ESB_ERROR, e.getMessage());
		}
		JSONObject jsons = new JSONObject();
		if (sendMsg != null) {
			jsons.put("result", "000");
			jsons.put("resultInf", sendMsg);
		} else {
			return returnResultJson(ExceptionCode.OSS_ERROR, "无数据");
		}
		return jsons.toString();
	}
	/**
	 * 错误返回json数据
	 * 
	 * @author Fanjiwei
	 * @param state
	 * @return
	 */
	private String returnResultJson(String errorCode, String bodyString) {
		JSONObject js = new JSONObject();
		js.put("result", errorCode);
		js.put("resultInf", bodyString);
		return js.toString();
	}


}
