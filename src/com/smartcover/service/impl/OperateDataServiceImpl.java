package com.smartcover.service.impl; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcover.dao.OperateDataDao;
import com.smartcover.service.OperateDataService;
import com.system.constant.Constants;
import com.system.tool.HttpUtil;
import com.system.tool.JsonUtil;

/** 
 * @author wangxiangyu
 * @date：2017年10月11日 上午8:45:10 
 * 类说明：智能井盖数据操作接口实现类
 */
@Service
@SuppressWarnings("all")
public class OperateDataServiceImpl implements OperateDataService {
	
	@Autowired
	OperateDataDao operateDataDao;
	
	/**
	 * 鉴权接口
	 * POST http://180.101.149.100:30004/api/auth/login
	 */
	@Override
	public Map<String, String> getAuthResult() {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("username", "jsqxb@iot.com");
		param.put("password", "1qaz2wsx");
		
		String result = HttpUtil.sendPost(Constants.SMART_COVER_AUTH, param);
		JSONObject resObj = JSONObject.fromObject(result);
		
		Map<String, String> rs = new HashMap<String, String>();
		rs.put("token", resObj.get("token").toString());
		rs.put("refreshToken", resObj.get("refreshToken").toString());
		return rs;
	}
	
	/**
	 * 获取全部设备信息
	 * GET http://180.101.149.100:30004/api/tenant/devices
	 */
	@Override
	public Map<String, Object> getAllDevices(String limit, String textSearch, String textOffset, String idOffset) {
		
		String token = this.getAuthResult().get("token");
		//获取返回结果
		String result = this.sendGet(Constants.SMART_COVER_GET_ALL_DEVICES + "?limit=" + limit, token, "utf-8");
		JSONObject resObj = JSONObject.fromObject(result);
		/**
		 * 设备信息
		 */
		JSONArray dataArray =resObj.getJSONArray("data");
		for(int i = 0; i<dataArray.size(); i++){
			JSONObject dataObj = (JSONObject)dataArray.get(i);
			JSONObject idObj = dataObj.getJSONObject("id");//设备唯一编号
			JSONObject tenantIdObj = dataObj.getJSONObject("tenantId");//管理用户id
			JSONObject customerIdObj = dataObj.getJSONObject("customerId");//客户id
			//封装对象集合
			Map<String, String> param = new HashMap<String, String>();
			param.put("id", idObj.getString("id"));
			param.put("idEntityType", idObj.getString("entityType"));
			param.put("tenantId", tenantIdObj.getString("id"));
			param.put("tenantIdEntityType", tenantIdObj.getString("entityType"));
			param.put("customerId", customerIdObj.getString("id"));
			param.put("customerIdEntityType", customerIdObj.getString("entityType"));
			//日期转换
			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String createdTime = "";
			try {
				createdTime = sdf.format(dataObj.get("createdTime"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			param.put("createdTime", createdTime);
			param.put("name", dataObj.getString("name"));
			param.put("type", dataObj.getString("type"));
			param.put("additionalInfo", null==dataObj.get("additionalInfo")?"":dataObj.getString("additionalInfo"));
			//插入数据库
			operateDataDao.insertDevice(param);
		}
		/**
		 * 下一批页面参数信息
		 */
		Map<String, Object> nextPageLinkRs = new HashMap<String, Object>();
		
		JSONObject nextPageLinkObj = resObj.getJSONObject("nextPageLink");
		if(!nextPageLinkObj.isEmpty()){
			nextPageLinkRs.put("limit", null==nextPageLinkObj.get("limit")?"":nextPageLinkObj.get("limit").toString());//单次获取数量
			nextPageLinkRs.put("textSearch", null==nextPageLinkObj.get("textSearch")?"":nextPageLinkObj.get("textSearch").toString());//设备名称文本筛选
			nextPageLinkRs.put("textSearchBound", null==nextPageLinkObj.get("textSearchBound")?"":nextPageLinkObj.get("textSearchBound").toString());
			nextPageLinkRs.put("textOffset", null==nextPageLinkObj.get("limit")?"":nextPageLinkObj.get("textOffset").toString());//单次获取设备名称偏移量
			nextPageLinkRs.put("idOffset", null==nextPageLinkObj.get("idOffset")?"":nextPageLinkObj.get("idOffset").toString());//单次获取设备唯一编号偏移量
		}else{
			nextPageLinkRs.put("limit", "");
			nextPageLinkRs.put("textSearch", "");
			nextPageLinkRs.put("textSearchBound", "");
			nextPageLinkRs.put("textOffset", "");
			nextPageLinkRs.put("idOffset", "");
		}
		/**
		 * 是否有下一批页面
		 */
		if(null != resObj.get("hasNext")){
			nextPageLinkRs.put("hasNext", null==resObj.get("hasNext")?"":resObj.get("hasNext").toString());
		}else{
			nextPageLinkRs.put("hasNext", "");
		}
		
		return nextPageLinkRs;
	}
	
	/**
	 * 获取单个设备信息
	 * GET http://180.101.149.100:30004/api/device/{deviceId}
	 */
	@Override
	public Map<String, String> getOne(String deviceId) {
		
		String token = this.getAuthResult().get("token");
		String result = this.sendGet(Constants.SMART_COVER_GET_ONE_DEVICE + deviceId, token, "utf-8");
		
		JSONObject resObj = JSONObject.fromObject(result);
		JSONObject idObj = resObj.getJSONObject("id");
		JSONObject tenantIdObj = resObj.getJSONObject("tenantId");
		JSONObject customerIdObj = resObj.getJSONObject("customerId");
		
		String id = idObj.getString("id");//设备唯一编号
		String idEntityType = idObj.getString("entityType");
		String tenantId = tenantIdObj.getString("id");//管理用户id
		String tenantIdEntityType = tenantIdObj.getString("entityType");
		String customerId = customerIdObj.getString("id");//客户id
		String customerIdEntityType = customerIdObj.getString("entityType");
		String createdTime = resObj.getString("createdTime");//设备创建时间
		String name = resObj.getString("name");//设备名称
		String type = resObj.getString("type");
		String additionalInfo = null==resObj.get("additionalInfo")?"":resObj.get("additionalInfo").toString();//备注
		
		Map<String, String> rs = new HashMap<String, String>();
		rs.put("id", id);
		rs.put("idEntityType", idEntityType);
		rs.put("tenantId", tenantId);
		rs.put("tenantIdEntityType", tenantIdEntityType);
		rs.put("customerId", customerId);
		rs.put("customerIdEntityType", customerIdEntityType);
		rs.put("createdTime", createdTime);
		rs.put("name", name);
		rs.put("type", type);
		rs.put("additionalInfo", additionalInfo);
		
		return rs;
	}
	
	@Override
	public List<Map<String, String>> getKeyAndValue(String deviceId){
		
		String url = Constants.SMART_COVER_GET_DEVICE_KEY.replace("{deviceId}", deviceId);
		
		String result = this.sendGet(url, this.getAuthResult().get("token"), "utf-8");
		String[] keys = result.split(",");
		
		if(keys != null && "".equals(keys)){
			for(String key : keys){
				
			}
		}
		
		
		return null;
	}
	
	
	
	
	
	/**  
     * GET请求，字符串形式数据  
     * @param url 请求地址  
     * @param charset 编码方式  
     * 
     */
    public String sendGet(String url, String token, String charset) {  
        String result = "";
        BufferedReader in = null;  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection connection = realUrl.openConnection();  
            // 设置通用的请求属性  
            connection.setRequestProperty("accept", "*/*");  
            connection.setRequestProperty("connection", "Keep-Alive");  
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"); 
            //设置鉴权属性
            connection.setRequestProperty("X-Authorization", " Bearer " + token);
            // 建立实际的连接  
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("发送GET请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输入流  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (Exception e2) {  
                e2.printStackTrace();  
            }  
        }  
        return result;  
    }
	
	
    /**  
     * POST请求，字符串形式数据  
     * @param url 请求地址  
     * @param param 请求数据  
     */  
    public String sendPost(String url, Map<String,Object> param) {  
  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {
            URL realUrl = new URL(url); 
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type", "application/form-data");
            conn.setRequestProperty("Content-Length",  String.valueOf(JsonUtil.simpleMapToJsonStr(param).length()));
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //设置鉴权属性
            conn.setRequestProperty("Authorization", " Bearer " + String.valueOf(param.get("token")));
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            out.print(JsonUtil.simpleMapToJsonStr(param));  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;
            }
            System.out.println("url>>>>>>>>:"+url);
            System.out.println("param>>>>>>>>:"+JsonUtil.simpleMapToJsonStr(param));
            System.out.println("result>>>>>>>>:"+result);
        } catch (Exception e) {  
            System.out.println("发送 POST 请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }
    
    /**  
     * POST请求，Map形式数据  
     * @param url 请求地址
     * @param param 请求数据  
     * @param charset 编码方式
     */  
    public String sendPost(String url, Map<String, String> param, String charset) {  
  
        StringBuffer buffer = new StringBuffer();  
        if (param != null && !param.isEmpty()) {  
            for (Map.Entry<String, String> entry : param.entrySet()) {  
                buffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue())).append("&");  
            }
        }
        buffer.deleteCharAt(buffer.length() - 1);  
  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //设置鉴权属性
            conn.setRequestProperty("Authorization", " Bearer " + String.valueOf(param.get("token")));
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            out.print(buffer);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            } 
           
        } catch (Exception e) {  
            System.out.println("发送 POST 请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
    
	
	
	
	
	

}
