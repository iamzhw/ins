package icom.system.action;

import icom.system.service.TaskInterfaceService;
import icom.util.BaseServletTool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import util.page.BaseAction;

/**
 * 提供给客户端的任务查询控制层
 * 
 * @author huliubing
 * @since 2014-07-23
 *
 */
@Controller
@RequestMapping("mobile/hajfxj")
public class TaskInterfaceController extends BaseAction {
	
	@Resource
	private TaskInterfaceService taskInterfaceService;
	
	@RequestMapping("/getAllTask")
	public void getAllTask(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String jsonStr=BaseServletTool.getParam(request);
		
		System.out.println("入参信息:"+jsonStr);
		String result=taskInterfaceService.getAllTask(jsonStr);
		System.out.println("出参信息:"+result);
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/getInspItems")
	public void getInspItems(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String jsonStr=BaseServletTool.getParam(request);
		
		System.out.println("入参信息:"+jsonStr);
		String result=taskInterfaceService.getInspItems(jsonStr);
		System.out.println("出参信息:"+result);
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/saveTroubles")
	public void saveTroubles(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String jsonStr=BaseServletTool.getParam(request);
		
		System.out.println("入参信息:"+jsonStr);
		String result=taskInterfaceService.saveTroubles(jsonStr);
		System.out.println("出参信息:"+result);
		BaseServletTool.sendParam(response, result);
	}
	
	@RequestMapping("/uploadPhoto")
	public void uploadPhoto(HttpServletRequest request,
			HttpServletResponse response){
		String result = taskInterfaceService.uploadPhoto(request);
		BaseServletTool.sendParam(response, result);
	}

}
