package com.system.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import util.password.MD5;

import com.system.dao.LoginDao;
import com.system.model.ZTreeNode;
import com.system.service.LoginService;
import com.util.DESUtil;
import com.util.EncryptUtils;

@SuppressWarnings("all")
@Service
public class LoginServiceImpl implements LoginService {
	@Resource
	private LoginDao loginDao;

	public Map login(HttpServletRequest request) throws Exception {
		Map hm = new HashMap();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		/**
		 * aes解密
		 */
		username=EncryptUtils.aesDecrypt(username, "abcdefgabcdefg12");
		password=EncryptUtils.aesDecrypt(password, "abcdefgabcdefg12");
		
		hm.put("STAFF_NO", username);
		hm.put("PASSWORD", MD5.encrypt(MD5.md5s(password)).trim());
		List<Map> l = loginDao.login(hm);
		if (l.size() == 0) {
			return null;
		} else {
			return l.get(0);
		}
	}

	public List<ZTreeNode> getGns(int id, String staffNo) {
		Map hm = new HashMap();
		hm.put("ID", id);
		hm.put("STAFF_NO", staffNo);
		List<Map> l = loginDao.getGns(hm);
		List<ZTreeNode> nodes = new ArrayList();
		// 循环数据，拼接返回数据
		for (int i = 0; i < l.size(); i++) {
			Map gns = l.get(i);
			// true需要判断
			nodes.add(new ZTreeNode(gns.get("ID").toString(), (String) gns
					.get("NAME"), gns.get("PARENTID").toString(), false,
					((String) gns.get("ISPARENT")).equals("1") ? true : false,
					(String) gns.get("ACTIONNAME")));
		}
		return nodes;
	}
	
	
	public Map singleLogin(Map parms) throws Exception {
		Map hm = new HashMap();
		String account_no = (String) parms.get("account_no");
		hm.put("STAFF_NO", account_no);
		List<Map> l = loginDao.singleLogin(hm);
		if (l.size() == 0) {
			return null;
		} else {
			return l.get(0);
		}
	}

}
