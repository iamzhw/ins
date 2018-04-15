package icom.cableCheck.action;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import icom.cableCheck.model.SensitiveLog;
import icom.cableCheck.service.SensitiveLogService;
import icom.util.BaseServletTool;
import net.sf.json.JSONObject;

public class SensitiveLogInte implements HandlerInterceptor{

	
	@Resource
	private SensitiveLogService sensitiveLogService;
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception arg3)
			throws Exception {
		
        String urlString = request.getRequestURI();		//请求的url地址
        List<SensitiveLog> list = SensitiveLog.getList();
        
        for (SensitiveLog sensitiveLog : list) {
        	Map<String, String> map = new HashMap<String, String>();
    		map.put("staff_id", sensitiveLog.getStaffId()==null?"0":sensitiveLog.getStaffId());
    		map.put("sensitive_staff_id", sensitiveLog.getSensitive_staff_id()==null?"":sensitiveLog.getSensitive_staff_id());
    		map.put("sensitive_staff_no", sensitiveLog.getSensitive_staff_no()==null?"":sensitiveLog.getSensitive_staff_no());
    		map.put("sensitive_staff_name", sensitiveLog.getSensitive_staff_name()==null?"":sensitiveLog.getSensitive_staff_name());
    		map.put("url_path", urlString);
    		map.put("parame", sensitiveLog.getParame()==null?"":sensitiveLog.getParame());
    		map.put("operate", sensitiveLog.getOperate()==null?"":sensitiveLog.getOperate());
    		map.put("sensitive_address", sensitiveLog.getSensitive_address()==null?"":sensitiveLog.getSensitive_address());
    		map.put("sn", sensitiveLog.getSn()==null?"0":sensitiveLog.getSn());
    		try {
    			sensitiveLogService.insertSensitiveLog(map);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        list.clear();
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		// TODO Auto-generated method stub
        return true;
	}
}
