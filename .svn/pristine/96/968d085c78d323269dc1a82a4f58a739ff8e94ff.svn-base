package com.smartcover.service; 

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/** 
 * @author wangxiangyu
 * @date：2017年10月11日 上午8:44:00 
 * 类说明：智能井盖数据操作接口
 */
@Service
public interface OperateDataService {
	/**
	 * 
	 * @Title: getAuthResult
	 * @Description:鉴权接口，认证API
	 * @author wangxiangyu
	 */
	public Map<String, String> getAuthResult();
	/**
	 * 
	 * @Title: getAllDevices
	 * @Description:查询所有设备信息
	 * @author wangxiangyu
	 */
	public Map<String, Object> getAllDevices(String limit, String textSearch, String textOffset, String idOffset);
	/**
	 * 
	 * @Title: getOne
	 * @Description:查询单个设备信息
	 * @author wangxiangyu
	 */
	public Map<String, String> getOne(String deviceId);
	
	/**
	 * 
	 * @Title: getDeviceKey
	 * @Description:设备属性key和VALUE值
	 * @author wangxiangyu
	 */
	public List<Map<String, String>> getKeyAndValue(String deviceId);
	
	
	
	
	
}
