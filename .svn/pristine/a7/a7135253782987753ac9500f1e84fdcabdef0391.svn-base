package com.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Gps2BaiDu {

	/**
	 * 该方法转换不是很准，已经废弃
	 * 
	 * @param gpsMap
//	 
	 * @throws IOException */
//	public static void convert2(Map<String,Object> gpsMap){
//			double x = Double.parseDouble(gpsMap.get("LONGITUDE").toString());
//			double y = Double.parseDouble(gpsMap.get("LATITUDE").toString());
//			String path = "http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x="+x+"+&y="+y+"&callback=BMap.Convertor.cbk_7594";
//			
//			HttpURLConnection conn = null;
//			try
//			{
//				//使用http请求获取转换结果
//				URL url = new URL(path);
//				conn = (HttpURLConnection) url.openConnection();
//				conn.setRequestMethod("GET");
//				conn.setConnectTimeout(30000);
//				InputStream inStream = conn.getInputStream();
//				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//				byte[] buffer = new byte[1024];
//				int len = 0;
//				while ((len = inStream.read(buffer)) != -1){
//					outStream.write(buffer, 0, len);
//				}
//				
//				//得到返回的结果
//				String res = outStream.toString();
//				//处理结果
//				if (res.indexOf("(") > 0 && res.indexOf(")") > 0){
//					String str = res.substring(res.indexOf("(") + 1, res.indexOf(")"));
//					String err = res.substring(res.indexOf("error") + 7, res.indexOf("error") + 8);
//					if ("0".equals(err)){
//						JSONObject js = JSONObject.fromObject(str);
//						//编码转换
//						String x1 = new String(Base64Util.decode(js.getString("x")));
//						String y1 = new String(Base64Util.decode(js.getString("y")));
//						
//						//更新数据库的值
//						gpsMap.put("LONGITUDE", x1);
//						gpsMap.put("LATITUDE", y1);
//						gpsMap.put("status", "0");
//					}else{
//						gpsMap.put("status", "1");
//					}
//				}else{
//					gpsMap.put("status", "1");
//				}
//			} catch (Exception e){
//				gpsMap.put("status", "1");
//			}finally{
//				if(null != conn){
//					conn.disconnect();
//				}
//			}
//		 
//		}
	
	
	public static void convert(Map<String,Object> gpsMap) throws IOException{
		String x = gpsMap.get("LONGITUDE").toString();
		String y = gpsMap.get("LATITUDE").toString();
		String path ="http://api.map.baidu.com/geoconv/v1/?coords="+x+","+y+"&from=1&to=5&ak=4E1fb44d64c4f75d50a0ec54abff48ff";
		
		HttpURLConnection conn = null;
		InputStream inStream = null;
		ByteArrayOutputStream outStream = null;
		try{
			//使用http请求获取转换结果
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			inStream = conn.getInputStream();
			outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1){
				outStream.write(buffer, 0, len);
			}
			//得到返回的结果
			String res = outStream.toString();
			
			JSONObject js = JSONObject.fromObject(res);
			JSONArray resultArray = js.getJSONArray("result");
			JSONObject result = resultArray.getJSONObject(0); 
			//更新数据库的值
			gpsMap.put("LONGITUDE", result.getString("x"));
			gpsMap.put("LATITUDE", result.getString("y"));
			gpsMap.put("status", "0");
		} catch (Exception e){
			gpsMap.put("status", "1");
		}finally{
			if(null != conn){
				conn.disconnect();
			}
			if(inStream != null){
				inStream.close();
			}
			if(outStream != null){
				outStream.close();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		List<String> list = new ArrayList<String>();
		list.add("118.5886121;31.83263732");

		for(String s : list){
			String[] str = s.split(";");
			Map<String,Object> p = new HashMap<String,Object>();
			p.put("LONGITUDE", str[0]);
			p.put("LATITUDE", str[1]);
			convert(p);
			if("0".equals(p.get("status").toString())){
				System.out.println("00X:" +p.get("LONGITUDE").toString() +"             " + p.get("LATITUDE").toString() );
			}else{
				System.out.println("00Y:" +p.get("LONGITUDE").toString() +"             " + p.get("LATITUDE").toString() );
			}
		}
		
		System.out.println("this is over!");
		
	}
}
