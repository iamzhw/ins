package com.system.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class RightFilter extends HttpServlet implements Filter {

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpSession session = request.getSession(true);
		String staffId = (String) session.getAttribute("staffId");//
		String url = request.getRequestURI();
		String sysName = request.getContextPath();
		if (staffId == null || staffId.equals("")) {
			
			if(url.indexOf("unified") == -1) {
				// 判断获取的路径不为空且不是访问登录页面或执行登录操作时跳转
				if (url.indexOf("mobile") == -1 && url != null && !url.equals("")
						&& !url.equals(sysName + "/Login/login.do")
						&& !url.equals(sysName + "/")
						&& !url.equals(sysName + "/jsp/frame/login_new/login.jsp")) {
					String url1 = "<script>window.open ('" + sysName
							+ "','_top')</script>";
					PrintWriter pw = response.getWriter();
					pw.print(url1);
					pw.flush();
					pw.close();
					return;
				} else if (url.indexOf("mobile") != -1) {

				}
			}
			
		}
		// 已通过验证，用户访问继续
		arg2.doFilter(arg0, arg1);
		return;
	}

	public void init(FilterConfig arg0) throws ServletException {

	}
}