package com.system.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.system.constant.SysSet;
import com.system.service.SysSetService;
import com.util.StringUtil;

public class SystemListener extends ContextLoaderListener implements ServletContextListener {

	private SysSetService sysSetService;

	private static String realPath;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		realPath = StringUtil.convertPath(event.getServletContext().getRealPath(""), false);

		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		sysSetService = ctx.getBean(SysSetService.class);
		// 读取系统配置
		List<Map<String, Object>> sysSet = sysSetService.getSysSet();
		String soft_id = null;
		Map<String, String> params = null;
		for (Map<String, Object> map : sysSet) {
			params = new HashMap<String, String>();
			soft_id = StringUtil.objectToString(map.get("SOFT_ID"));
			params.put("LOCATE_SPAN",StringUtil.objectToString(map.get("LOCATE_SPAN")));
			params.put("UPLOAD_SPAN",StringUtil.objectToString(map.get("UPLOAD_SPAN")));
			params.put("INACCURACY",StringUtil.objectToString(map.get("INACCURACY")));
			SysSet.CONFIG.put(soft_id, params);
		}
	}

	public static String getRealPath() {
		return realPath;
	}
}
