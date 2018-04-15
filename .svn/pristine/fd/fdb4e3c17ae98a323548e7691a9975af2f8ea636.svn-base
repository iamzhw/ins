package icom.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ProcessJson {
	public static String getResultJsonObject(Map map, String[] cols) {
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", ExceptionCode.SUCCESS);
		for (int j = 0; j < cols.length; j++) {
			Object value = map.get(cols[j]);
			if (value == null) {
				value = "";
			}
			if (value instanceof Date) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String dateStr = sdf.format(value);
				resultJson.put(cols[j].toLowerCase(), dateStr);
			} else {
				resultJson.put(cols[j].toLowerCase(), value);
			}
		}
		return resultJson.toString();
	}

	public static JSONArray getResultJsonArray(List<Map> list, String[] cols) {
		JSONArray ja = new JSONArray();
		for (Map map : list) {
			JSONObject resultJson = new JSONObject();

			for (int j = 0; j < cols.length; j++) {

				Object value = map.get(cols[j]);
				if (value == null) {
					value = "";
				}
				if (value instanceof Date) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					String dateStr = sdf.format(value);
					resultJson.put(cols[j].toLowerCase(), dateStr);
				} else {
					resultJson.put(cols[j].toLowerCase(), value);
				}

			}
			ja.add(resultJson);
		}

		return ja;
	}
}
