package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordUtil 
{
	public static boolean checkPassword(String passwordStr)
	{
		String re1 = "^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])|(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9])|(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])|(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{8,18}";
		// String re4="(\\d+)"; // Integer Number 2

		Pattern p = Pattern.compile(re1);
		Matcher mc = p.matcher(passwordStr);
		return mc.matches();
	}
	 /**
	 * 
	 * @Function: com.util.PasswordUtil.encryption
	 * @Description:md5 32位小写加密
	 *
	 * @param plain
	 * @return
	 *
	 * @date:2016-1-11 上午11:03:38
	 *
	 * @Modification History:
	 * @date:2016-1-11     @author:Administrator     create
	 */
	public static String encryption(String plain) {
			String re_md5 = new String();
			try {
				MessageDigest md = MessageDigest.getInstance("md5");
				md.update(plain.getBytes());
				byte b[] = md.digest();
				int i;
				StringBuffer buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}

				re_md5 = buf.toString();

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return re_md5;
		}
}
