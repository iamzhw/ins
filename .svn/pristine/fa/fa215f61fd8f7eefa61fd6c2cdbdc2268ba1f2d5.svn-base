package icom.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServletTool {
	/**
	 * 从手机客户端传来的请求中获得请求参数
	 * @param request
	 * @return
	 */
	public static String getParam(HttpServletRequest request){
		String param = "";
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try{
			StringBuffer sb = new StringBuffer() ; 
			is = request.getInputStream(); 
			isr = new InputStreamReader(is, "UTF-8");   
			br = new BufferedReader(isr); 
			String s = "" ; 
			while((s=br.readLine())!=null){ 
			sb.append(s) ; 
			} 
			param =sb.toString(); 
			param = URLDecoder.decode(param, "UTF-8");
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e2){
			e2.printStackTrace();
		}finally{
			try {
				if(is != null){
					is.close();
				}
				if(isr != null){
					isr.close();
				}
				if(br != null){
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return param;
	}
	
	/**
	 * 向手机客户端发送参数
	 * @param response
	 * @param param
	 * @return
	 */
	public static boolean sendParam(HttpServletResponse response, String param){
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(param);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
