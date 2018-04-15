package com.system.action;

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

import com.system.service.AreaService;

/**
 * 
 * @ClassName: StaffController
 * @Description: 员工管理
 * @author: SongYuanchen
 * @date: 2014-1-16
 * 
 */
@SuppressWarnings("all")
@RequestMapping(value = "/areaController")
@Controller
public class AreaController extends BaseAction {
    @Resource
    private AreaService areaService;

    @RequestMapping("getSonArea")
    public void getSonArea(HttpServletRequest request, HttpServletResponse response, String areaId)
	    throws IOException {
	List<Map<String, Object>> sonAreaList = areaService.getSonArea(areaId);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("sonAreaList", sonAreaList);
	write(response, map);
    }
}
