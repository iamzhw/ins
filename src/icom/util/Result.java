package icom.util;

import net.sf.json.JSONObject;

public class Result {

	public static String returnCode(String code){
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", code);
		return resultJson.toString();
	}
	
	public static String returnCode(String code,String desc){
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", code);
		resultJson.put("desc", desc);
		return resultJson.toString();
	}
	
	public static JSONObject returnError(String msg){
		JSONObject result = new JSONObject();
		result.put("status", false);
		result.put("message", msg);
		return result;
	}
	
	public static JSONObject returnSuccess(String msg){
		JSONObject result = new JSONObject();
		result.put("status", true);
		result.put("message", msg);
		return result;
	}
}
