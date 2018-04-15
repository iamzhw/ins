package icom.axx.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linePatrol.util.DateUtil;
import com.util.sendMessage.PropertiesUtil;

import icom.axx.service.LineSiteInterfaceService;
import icom.util.BaseServletTool;
import util.page.BaseAction;

@Controller
@RequestMapping("mobile/axxgps")
public class AxxGpsUploadInterfaceController extends BaseAction {

	@Resource
    private LineSiteInterfaceService lineSiteInterfaceService;
    
    /**
     * TODO 自动轨迹上传接口，新，不限制重复上传
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/autoUploadTrack2")
    public void autoUploadTrack2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jsonStr = BaseServletTool.getParam(request);
	
		printParam("入参信息:" + jsonStr);
		System.out.println(DateUtil.getDateAndTime()+":autoUploadTrack入参信息"+jsonStr);
		String result = lineSiteInterfaceService.saveAutoTrack2(jsonStr);
		System.out.println(DateUtil.getDateAndTime()+":出参信息"+result);
		printParam("出参信息:" + result);
		BaseServletTool.sendParam(response, result);
    }

    private void printParam(String param) {
		if (PropertiesUtil.getPropertyBoolean("printSwitch", true)) {
			System.out.println(param);
		}
	}
	
}
