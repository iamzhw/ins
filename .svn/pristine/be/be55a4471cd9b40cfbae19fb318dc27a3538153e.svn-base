package icom.system.action;

import icom.system.service.InitService;
import icom.util.BaseServletTool;
import icom.util.ExceptionCode;
import icom.util.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mobile/init")
public class InitController {
	@Resource
	private InitService initService;

	@RequestMapping("/getAppInfo.do")
	public void getAppInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String result = "";
		try {
			String json = BaseServletTool.getParam(request);
			// json = "{\"appType\":\"3\"}";
			result = initService.getAppInfo(json);
		} catch (Exception e) {
			e.printStackTrace();
			result = Result.returnCode(ExceptionCode.SERVER_ERROR);
		} finally {
			BaseServletTool.sendParam(response, result);
		}
	}

	@RequestMapping("/login.do")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			String json = BaseServletTool.getParam(request);
			// json = "{\"user\":\"admin\",\"pwd\":\"123\"}";
			result = initService.login(json, request);
		} catch (Exception e) {
			result = Result.returnCode(ExceptionCode.SERVER_ERROR,"程序异常，请联系管理员");
		} finally {
			BaseServletTool.sendParam(response, result);
		}
	}

	@RequestMapping("/getUrl.do")
	public void getUrl(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			String json = BaseServletTool.getParam(request);
			// json = "{\"appType\":\"3\"}";
			result = initService.getUrl(json);
		} catch (Exception e) {
			result = Result.returnCode(ExceptionCode.SERVER_ERROR);
		} finally {
			BaseServletTool.sendParam(response, result);
		}
	}

	@RequestMapping("/changePwd.do")
	public void changePwd(HttpServletRequest request,
			HttpServletResponse response) {
		String result = "";
		try {
			String json = BaseServletTool.getParam(request);
			// json = "{\"appType\":\"3\"}";
			result = initService.changePwd(json);
		} catch (Exception e) {
			result = Result.returnCode(ExceptionCode.SERVER_ERROR);
		} finally {
			BaseServletTool.sendParam(response, result);
		}
	}

	@RequestMapping("/feedbackAdvice.do")
	public void feedbackAdvice(HttpServletRequest request,
			HttpServletResponse response) {
		String result = "";
		try {
			String json = BaseServletTool.getParam(request);
			// json = "{\"appType\":\"3\"}";
			result = initService.feedbackAdvice(json);
		} catch (Exception e) {
			result = Result.returnCode(ExceptionCode.SERVER_ERROR);
		} finally {
			BaseServletTool.sendParam(response, result);
		}
	}
	@RequestMapping("/getSjxjUrl.do")
	public void getSjxjUrl(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			result = initService.getSjxjUrl(request);
		} catch (Exception e) {
			result = Result.returnCode(ExceptionCode.SERVER_ERROR);
		} finally {
			BaseServletTool.sendParam(response, result);
		}
	}
	
	
	@RequestMapping("/logout.do")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			String json = BaseServletTool.getParam(request);
			// json = "{\"user\":\"admin\",\"pwd\":\"123\"}";
			result = initService.logoutUser(json,request);
			
		} catch (Exception e) {
			result = Result.returnCode(ExceptionCode.SERVER_ERROR);
		} finally {
			BaseServletTool.sendParam(response, result);
		}
	}
	
	
	@RequestMapping("/getLoginUser.do")
	public void getLoginUser(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			String json = BaseServletTool.getParam(request);
			// json = "{\"user\":\"admin\",\"pwd\":\"123\"}";
			result = initService.getLoginUser(json);
		} catch (Exception e) {
			result = Result.returnCode(ExceptionCode.SERVER_ERROR);
		} finally {
			BaseServletTool.sendParam(response, result);
		}
	}
	
	/**
	 * 
	 * @Function: icom.system.action.InitController.singleLogin
	 * @Description:单点登录
	 *
	 * @param request
	 * @param response
	 *
	 * @date:2016-1-11 上午10:17:44
	 *
	 * @Modification History:
	 * @date:2016-1-11     @author:Administrator     create
	 */
	@RequestMapping("/singleLogin.do")
	public void singleLogin(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			String json = BaseServletTool.getParam(request);
			// json = "{\"user\":\"admin\",\"pwd\":\"123\"}";
			result = initService.singleLogin(json, request);
		} catch (Exception e) {
			result = Result.returnCode(ExceptionCode.SERVER_ERROR);
		} finally {
			BaseServletTool.sendParam(response, result);
		}
	}
}
