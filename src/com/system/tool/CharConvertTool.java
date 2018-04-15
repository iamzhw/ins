package com.system.tool;

import java.io.IOException;
import java.io.Reader;

import oracle.sql.CLOB;

public class CharConvertTool{

	/**
	 * 将十六进制字符串转为字节数组
	 * @param pic
	 * @return
	 */
	public static byte[] convertHexStringToByte( String pic ){
		byte[] result = new byte[pic.length()/2];                    //数组中每两位存放一个十六进制码
		for(int i = 0 ; i < pic.length() ; ){ 
			int temp  = Integer.valueOf(pic.substring(i, i+2),16);
			if( temp > 127)
				temp -= 256;
			result[i/2] = (byte)temp;
			i += 2;
		}
		return result;
	}
	
	/**
	 * 将CLOB转为字节数组
	 * @param pic
	 * @return
	 */
	public static byte[] convertClobToByte(CLOB clob){
		byte[] result = null;
		Reader inStream = null;
		try{
			inStream = clob.getCharacterStream();
			char[] charArray = new char[(int)clob.length()];
			inStream.read(charArray);
			String str = new String(charArray);  //将照片信息转成字符串类型（十六进制）
			result = CharConvertTool.convertHexStringToByte(str);  //将照片信息转成字节类型（二进制）
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(inStream != null){
					inStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 将字节数组转为十六进制字符串
	 * @param pic
	 * @return
	 */
	public static String convertByteToHexString( byte[] src ){
		StringBuffer sb  = new StringBuffer();
		for(int i = 0 ; i < src.length ; i++ ){
			byte temp = src[i];
			if(temp < 0 )
				temp += 256 ;
			String hexString = Integer.toHexString(temp);
			if( hexString.length() == 1)                               //对小于15的数字就行出来，补0凑成两位
				hexString = "0" +hexString;
			else 
				hexString = hexString.substring(hexString.length() - 2);
			sb.append(hexString);
		}
		return sb.toString();
	}
	
	/**
	 * 将十进制转为十六进制
	 * @param decimal
	 * @return
	 */
	public static String converDecToHex( String decimal ){
		String hex = Integer.toHexString(Integer.parseInt(decimal));
		return hex ;
	}
	/**
	 * 将字符串数组拼接成字符串
	 * @param ioPropetyId
	 * @return
	 */
	public static String getParam(String[] ioPropetyId){
		String appSql = "(";
		for (int i = 0; i < ioPropetyId.length; i++) {
			if (i > 0) {
				appSql = appSql + ",";
			}
			appSql = appSql + ioPropetyId[i];

		}
		appSql = appSql + ")";
		return appSql;
	}
}
