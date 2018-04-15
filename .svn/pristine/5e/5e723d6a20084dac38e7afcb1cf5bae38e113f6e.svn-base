/**
 * @Description: TODO
 * @date 2014-11-26
 * @param
 */
package com.linePatrol.util;

import java.io.Reader;
import java.sql.Clob;

public class ClobUtil {
    public final static String toString(Clob clob) {
	if (clob == null) {
	    return null;
	}
	StringBuffer sb = new StringBuffer(65535);// 64K
	Reader clobStream = null;// 创建一个输入流对象
	try {
	    clobStream = clob.getCharacterStream();
	    char[] b = new char[60000];// 每次获取60K
	    int i = 0;
	    while ((i = clobStream.read(b)) != -1) {
		sb.append(b, 0, i);
	    }
	} catch (Exception ex) {
	    sb = null;
	} finally {
	    try {
		if (clobStream != null)
		    clobStream.close();
	    } catch (Exception e) {
	    }
	}
	if (sb == null)
	    return null;
	else
	    return sb.toString();
    }

}