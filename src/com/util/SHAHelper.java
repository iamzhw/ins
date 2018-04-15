package com.util; 
/** 
 * @author wangxiangyu
 * @date：2017年10月16日 上午9:00:47 
 * 类说明：SHA签名算法
 */
public class SHAHelper {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5","6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
	
	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * SHA摘要计算(byte[]).
	 * 
	 * @param src byte[]
	 * @throws Exception
	 * @return byte[] 16 bit digest
	 */
	public static byte[] SHADigest(byte[] src) throws Exception {
		java.security.MessageDigest alg = java.security.MessageDigest
				.getInstance("SHA");
		return alg.digest(src);
	}
	
	/**
	 * SHA摘要计算(String).
	 * 
	 * @param src String型的目标串
	 * @throws Exception
	 * @return String
	 */
	public static String SHADigest(String src) throws Exception {
		return byteArrayToHexString(SHADigest(src.getBytes("UTF-8")));
	}

	
	/** Test crypt */
	public static void main(String[] args) {
		try {
			// 获得的明文数据
			String serialNumber = "0101102010031723101273807F00179E";
			String reqBizArgs = "<ReqBizArgs version='1'><test>test</test></ReqBizArgs>";
			String key = "1DD25F68E99C4D1C94A5807827B4EC77";
			String desStr = serialNumber + reqBizArgs + key;
			System.out.println("原文串 desStr = '" + desStr + "'");
			// 生成Signature
			String signature = SHADigest(desStr);
			System.out.println("签名串Signature = '" + signature + "'");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
