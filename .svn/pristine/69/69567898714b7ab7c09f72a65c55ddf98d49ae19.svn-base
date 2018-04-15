package icom.util;

import org.apache.commons.lang.StringUtils;

public class SensitiveUtil {
	
	public static String hideSensitive(String str,String type){
		String result = "";
		if(StringUtils.isNotBlank(type)){	
			if("name".equals(type)){
				result=returnName(str);
			}else if("address".equals(type)){
				result=returnAddress(str);
			}else{
				result=returnName(str);
			}
		}else{
			result=returnName(str);
		}
		return result;
	}
	
	public static String returnAddress(String str){
		if(StringUtils.isNotBlank(str)){
			String sensitiveAddress = "*";
//			int len = str.length();
//			for (int i = 0; i < len; i++) {
//				sensitiveAddress = sensitiveAddress+"*";
//			}			
			return sensitiveAddress;
		}else{
			return "";		
		}
	}
		
	public static String returnName(String str){
		if(StringUtils.isNotBlank(str)){
			if(str.length()>1){
				int len = str.length();
				String firstName = str.substring(0,1);
				String sensitiveName = "";
				for (int i = 0; i < len-1; i++) {
					sensitiveName = sensitiveName+"*";
				}
				return firstName+sensitiveName;
			}else{
				//名字只有一个字的情况
				return "*";	
			}
		}else{
			return "";		
		}
	}
}
