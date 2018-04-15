/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.service.impl;

import icom.util.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;


import com.linePatrol.dao.GaotieAutoTrackDao;
import com.linePatrol.dao.LineInfoDao;
import com.linePatrol.dao.SiteDao;
import com.linePatrol.service.GaotieAutoTrackService;
import com.linePatrol.util.StringUtil;
import com.system.sys.OnlineUserListener;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class GaotieAutoTrackServiceImpl implements GaotieAutoTrackService {

    @Resource
    private GaotieAutoTrackDao gaotieautoTrackDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.linePatrol.service.AutoTrackService#getTheTrack(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> getTheTrack(Map<String, Object> para) {
	// TODO Auto-generated method stub
	// my97 和oracle时间格式问题
	if (StringUtil.isNOtNullOrEmpty(para.get("start_time").toString())) {
	    String startTime = para.get("start_time").toString();
	    startTime = startTime.substring(0, startTime.indexOf(":"));
	    int st = Integer.parseInt(startTime);
	    if (st < 10) {
	    	startTime = "0" + para.get("start_time").toString();
	    }else{
	    	startTime = para.get("start_time").toString();
	    }
	    para.put("start_time", startTime);
	}
	if (StringUtil.isNOtNullOrEmpty(para.get("end_time").toString())) {
	    String endTime = para.get("end_time").toString();
	    endTime = endTime.substring(0, endTime.indexOf(":"));
	    int et = Integer.parseInt(endTime);
	    if (et < 10) {
	    	endTime = "0" + para.get("end_time").toString();
	    }else{
	    	endTime = para.get("end_time").toString();
	    }
	    para.put("end_time", endTime);
	}

	return gaotieautoTrackDao.getTheTrack(para);
    }

	@Override
	public List<Map<String, Object>> selTrackForDG(Map<String, Object> map) {
		return gaotieautoTrackDao.selTrackForDG(map);
	}

	/**
	 * 展示双击点修改界面
	 */
	@Override
	public Map<String, Object> selTrackPhoto(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		//根据轨迹点查看图片
		String track_id=request.getParameter("track_id").toString();
		
		List<Map<String, String>> photoList = gaotieautoTrackDao.getPhotoList(track_id);
		int photosize=0;
		if(photoList.size()>0){
			photosize=photoList.size();
			Map<String, String> photo=photoList.get(0);
			rs.put("photo", photo);
		}
		rs.put("photosize", photosize);
		rs.put("track", track_id);
		rs.put("photoList", photoList);
		return rs;
	}
}
