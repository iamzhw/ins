package com.system.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 拦截请求，匹配其是否拥有权限
 * 
 * @author 微笑风采
 * 
 */
public class SystemInterceptor extends HandlerInterceptorAdapter {

	private static final String loginUrl="/Login/login.do";
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("=======执行后=======");
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("=======执行=======");
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//1、请求到登录页面 放行  
//	    if(request.getServletPath().equals(loginUrl)){
//	    	return true;
//	    };
//	  //2、TODO 比如退出、首页等页面无需登录，即此处要放行 允许游客的请求  
////        if(request.getServletPath().startsWith("/logout.do")){
////        	return true;
////        }
//	    //3、如果用户已经登录 放行    
//	    if(request.getSession().getAttribute("staffId") != null) {  
//	        //更好的实现方式的使用cookie  
//	        return true;  
//	    }  
//	         System.out.println(request.getServletPath()); 
//	    //4、非法请求 即这些请求需要登录后才能访问  
//	    //重定向到登录页面  
//	    
//	   String url="<script>window.open ('/ins','_top')</script>";
//	   PrintWriter pw = response.getWriter();
//	   pw.print(url);
//	   pw.flush();
//	   pw.close();
	   return false;  
		// UserInfo userInfo = (UserInfo)
		// request.getSession().getAttribute("userinfo");
		// String proname = request.getRequestURI().substring(1);
		// proname = proname.substring(0, proname.indexOf("/"));
		// if (userInfo != null ||
		// request.getRequestURI().endsWith("login.action")) {
		// return super.preHandle(request, response, handler);
		// } else {
		// System.out.println("真是的，没有权限！");
		// response.getWriter().print("<script>location='http://" +
		// request.getHeader("host") + "/" + proname + "/index.jsp'</script>");
		// return false;
		// }
		//System.out.println("=======执行前=======");
		//return true;
	}

}
