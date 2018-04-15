package icom.util.GISutil.action;

import icom.util.JsonUtil;
import icom.util.GISutil.service.GisService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;




@RequestMapping("/mobile/gis")
@Controller
public class GisControll {
	
	@Resource
	private GisService gisService;
	
	@RequestMapping("index.do")
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp){
		String busiNo = req.getParameter("busiNo");
		//busiId
		//String ossAreaId = "F1506150389";
		//String ossSonAreaId = "F1506150389";
		String ossAreaId  = req.getParameter("ossAreaId");
		
		String ossSonAreaId =  req.getParameter("ossSonAreaId");
		if("".equals(ossAreaId) || "".equals(busiNo))
		{
			Map map = new HashMap();
			map.put("error", "busiNo参数或ossAreaId参数缺失！");
			return new ModelAndView("util/gis/gis",map);
			
		}
		if("20".equals(ossAreaId) && "".equals(ossSonAreaId))
		{
			Map map = new HashMap();
			map.put("error", "苏州地市ossSonAreaId参数缺失！");
			return new ModelAndView("util/gis/gis",map);
			
		}
		String jndi = "";
		switch (Integer.valueOf(ossAreaId)) {
		case 15:jndi="ossbc_dev_wx";break;
		case 20:jndi="ossbc_dev_sz";break;
		}
		//获取光路上所有设备和光缆段
		List<Map> list = gisService.queryMap(busiNo,jndi);
		Map urlQueryMap = new HashMap();
		if(!"".equals(urlQueryMap) &&"20".equals(ossAreaId))
		{
			urlQueryMap.put("sonAreaId", ossSonAreaId);
		}
		else
		{
			urlQueryMap.put("areaId", ossAreaId);
		}
		Map urlMap = gisService.queryGisUrlByAreaId(urlQueryMap);

		Map map = new HashMap();
		map.put("result", JsonUtil.getJsonString4JavaList(list));
		map.put("sameSect", JsonUtil.getJsonString4JavaList(gisService.querySameSectMap(busiNo, jndi)));
		if(null != urlMap
				&& null != urlMap.get("AG_DYNAMIC_MSL_URL") 
				&& null != urlMap.get("AG_TILED_MSL_URL") 
				&& !"".equals(urlMap.get("AG_DYNAMIC_MSL_URL").toString())
				&& !"".equals(urlMap.get("AG_TILED_MSL_URL").toString())
				)
		{
			map.put("mapUrl", urlMap.get("AG_TILED_MSL_URL").toString());
			map.put("resUrl", urlMap.get("AG_DYNAMIC_MSL_URL").toString());
		}
		else
		{
			map.put("error", "GIS地图缺失！");
		}
		
		//map.put("mapUrl", "http://132.228.224.46:8399/arcgis/rest/services/wuxi_map_1304/MapServer");
		//map.put("resUrl", "http://132.228.224.46:9008/arcgis/rest/services/res/wxres/MapServer");
		return new ModelAndView("util/gis/gis",map);
	}
	
	@RequestMapping("test.do")
	public ModelAndView test(){
		Map map = new HashMap();
		return new ModelAndView("util/gis/test",map);
	}
}
