package com.linePatrol.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import util.page.BaseAction;
import util.page.UIPage;

import com.linePatrol.dao.DotConvertDao;
import com.linePatrol.dao.LineConvertDao;
import com.system.service.ParamService;
import com.util.Gps2BaiDu;

/**
 * 巡线测试类
 * 
 * @author ht
 *
 */
@Controller
@RequestMapping(value = "/lineTest")
public class lineSiteConvertController extends BaseAction {
	
	@Resource
	private ParamService paramService;
	
	@Resource
	private LineConvertDao lineConvertDao;
	
	@Resource
	private DotConvertDao dotConvertDao;
	
	public void  execute(){
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("AREA_ID", "0");
			param.put("SITE_IDS", "转换数据开始");
			paramService.saveInvalidSites(param);
			
			String[] areaList ={"3","4","15","20","26","33","39","48","60","63","69","79","84"};
			for(String areaId : areaList){
				convert(areaId);
			}
			param.put("AREA_ID", "0");
			param.put("SITE_IDS", "转换数据结束");
			paramService.saveInvalidSites(param);
			
		}catch(Exception e){
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("SITE_IDS", "更新数据异常");
			param.put("AREA_ID", "0");
			paramService.saveInvalidSites(param);
		}
	}
	
	private void convert(String areaId){
		
		String invlaidSites="";
		String siteId="";
		Map<String,Object> param = new HashMap<String,Object>();
		try{
			param.put("AREA_ID", areaId);
			List<Map<String,Object>> sites = paramService.getSitesByUser(param);
			for(Map<String,Object> site : sites){
				siteId = site.get("SITE_ID").toString();
				Gps2BaiDu.convert(site);
				if("0".equals(site.get("status").toString())){
					paramService.updateSites(site);
					System.out.println("this site convert success:" + siteId);
				}else{
					invlaidSites+=siteId + ",";
				}
			}
			param.put("SITE_IDS", invlaidSites);
			paramService.saveInvalidSites(param);
			
			param.put("AREA_ID", areaId);
			param.put("SITE_IDS", "区域:"+areaId + " 转换完成");
			paramService.saveInvalidSites(param);
		}catch(Exception e){
			param.put("AREA_ID", areaId);
			param.put("SITE_IDS", "点:"+siteId + "转换失败");
			paramService.saveInvalidSites(param);
		}
	}
	//需要首先把步巡设施表加个status状态，默认值设置为0,修改成功置为1
	public void convertTempSite() throws IOException {
		/*List<Map<String, Object>> sites = lineConvertDao.getTempSites();

		String site_code = "";
		for (Map<String, Object> site : sites) {
			site_code = site.get("SITE_CODE").toString();
			Gps2BaiDu.convert(site);
			if ("0".equals(site.get("status").toString())) {
				site.put("SITE_ID", "1");
				lineConvertDao.updateTempSite(site);
				System.out.println("this site convert success:" + site_code);
			} else {
				lineConvertDao.updateTempSite(site);
				System.out.println("this site convert failed:" + site_code);
			}
		}*/
		
		/*List<Map<String, Object>> dots = dotConvertDao.getAllEquip();
		int num=1;
		String dotid = "";
		for (Map<String, Object> dot : dots) {
			dotid = dot.get("EQUIP_ID").toString();
			Gps2BaiDu.convert(dot);
			String status= dot.get("status").toString();
			if ("0".equals(status)) {
				dot.put("STATUS", "1");
				dotConvertDao.updatedDotXY(dot);
				System.out.println("this dot convert success:" + dotid);
			} else {
				System.out.println("this dot convert failed:" + dotid);
			}
			num++;
			System.out.println("第"+num+"次");
		}
		
		System.out.println("this is over!");*/
		
		//下面是数据量过大分页处理的
		int cotdots=dotConvertDao.getCountEquip();
		int pagenum=10000;
		int page=cotdots%pagenum==0?cotdots/pagenum:cotdots/pagenum+1;
		for(int i=0;i<page;i++)
		{
			List<Map<String, Object>> dots = dotConvertDao.getPageCountEquip();
			int num=1;
			String dotid = "";
			for (Map<String, Object> dot : dots) {
				dotid = dot.get("EQUIP_ID").toString();
				Gps2BaiDu.convert(dot);
				String status= dot.get("status").toString();
				if ("0".equals(status)) {
					dot.put("STATUS", "1");
					dotConvertDao.updatedDotXY(dot);
					System.out.println("this dot convert success:" + dotid);
				} else {
					System.out.println("this dot convert failed:" + dotid);
				}
				num++;
				System.out.println("第"+num+"次");
			}
			
			System.out.println("this is over!");	
		}	
	}
	
	
    @RequestMapping(value = "/index.do")
    public void index(HttpServletRequest request, HttpServletResponse response, UIPage pager)
	    throws IOException {
    	convertTempSite();
    }
	
}
