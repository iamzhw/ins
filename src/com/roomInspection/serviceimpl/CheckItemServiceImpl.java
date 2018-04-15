package com.roomInspection.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.roomInspection.dao.CheckItemDao;
import com.roomInspection.service.CheckItemService;
import com.util.StringUtil;

/**
 * 
 * @ClassName: CheckItemServiceImpl
 * @Description: 机房巡检检查项业务层实现类
 * 
 * @author huliubing
 * @since: 2014-8-15
 *
 */
@Service
@Transactional
public class CheckItemServiceImpl implements CheckItemService{

	@Resource
	private CheckItemDao checkItemDao;
	
	/**
	 * 查询检查项列表
	 * 
	 * @param request 请求消息
	 * @param pager 分页信息
	 * @return
	 */
	public Map<String,Object> queryCheckItem(HttpServletRequest request,UIPage pager){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("CHECK_ITEM_NAME", request.getParameter("check_item_name"));
		map.put("ROOM_TYPE_ID", request.getParameter("room_type_id"));
		
		//封装查询条件
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map<String,Object>> olists = checkItemDao.queryCheckItem(query);
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	
	/**
	 * 增加检查项
	 * 
	 * @param request 请求消息
	 */
	public void insertCheckItem(HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object>();
		long checkItemId = checkItemDao.getCheckItemId();
		map.put("CHECK_ITEM_ID", checkItemId);
		map.put("CHECK_ITEM_NAME", request.getParameter("check_item_name"));
		map.put("ROOM_TYPE_ID", request.getParameter("room_type_id"));
		map.put("CHECK_ITEM_CONTENT", request.getParameter("check_item_content"));
		map.put("CREATE_DATE", request.getParameter("create_date"));
		map.put("CREATE_BY", request.getParameter("create_by"));
		map.put("CHECK_TYPE", request.getParameter("check_type"));
		checkItemDao.insertCheckItem(map);
		
		//检查项插入后插入隐患类型
		String troubleTypeNames = request.getParameter("trouble_type_name");
		if(!StringUtil.isEmpty(troubleTypeNames))
		{
			String[] troubleTypeArray = troubleTypeNames.split(",");
			for(String troubleType : troubleTypeArray)
			{
				Map<String,Object> typeMap =  new HashMap<String,Object>();
				typeMap.put("CHECK_ITEM_ID", checkItemId);
				typeMap.put("TROUBLE_TYPE_NAME", troubleType);
				checkItemDao.insertTroubleType(typeMap);
			}
		}
		
		
	}
	
	/**
	 * 修改检查项
	 * 
	 * @param request 请求消息
	 */
	public void updateCheckItem(HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object>();
		String checkItemId = request.getParameter("check_item_id");
		map.put("CHECK_ITEM_ID", checkItemId);
		map.put("CHECK_ITEM_NAME", request.getParameter("check_item_name"));
		map.put("ROOM_TYPE_ID", request.getParameter("room_type_id"));
		map.put("CHECK_ITEM_CONTENT", request.getParameter("check_item_content"));
		map.put("MODIFY_DATE", request.getParameter("modify_date"));
		map.put("MODIFY_BY", request.getParameter("modify_by"));
		map.put("CHECK_TYPE", request.getParameter("check_type"));
		checkItemDao.updateCheckItem(map);
		
		//检查项更新后更新隐患类型
		String troubleTypeNames = request.getParameter("trouble_type_name");
		String troubleTypeIds = request.getParameter("trouble_type_id");
		
		//如果名称和ID有一个为空，删除该检查项下所有隐患类型
		if(StringUtil.isEmpty(troubleTypeNames) || StringUtil.isEmpty(troubleTypeIds))
		{
			Map<String,Object> deleteTypeMap =  new HashMap<String,Object>();
			deleteTypeMap.put("CHECK_ITEM_ID", checkItemId);
			deleteTypeMap.put("TROUBLE_TYPE_ID", troubleTypeIds);
			checkItemDao.deleteTroubleType(deleteTypeMap);
		}
		
		//如果名称不为空
		if(!StringUtil.isEmpty(troubleTypeNames))
		{
			int index=0;
			
			//如果隐患ID不为空，处理隐患类型
			if(!StringUtil.isEmpty(troubleTypeIds))
			{
				//获取ID个数
				String[] troubleTypeIdArray = troubleTypeIds.split(",");
				index = troubleTypeIdArray.length;
				
				//删除没有在ID中的隐患类型
				Map<String,Object> deleteTypeMap =  new HashMap<String,Object>();
				deleteTypeMap.put("CHECK_ITEM_ID", checkItemId);
				deleteTypeMap.put("TROUBLE_TYPE_ID", troubleTypeIds);
				checkItemDao.deleteTroubleType(deleteTypeMap);
			}
			
			// 如果名称长度大于ID长度，超过部分即为新增的隐患类型
			String[] troubleTypeNameArray = troubleTypeNames.split(",");
			for(int i=index;i<troubleTypeNameArray.length;i++)
			{
				Map<String,Object> typeMap =  new HashMap<String,Object>();
				typeMap.put("CHECK_ITEM_ID", checkItemId);
				typeMap.put("TROUBLE_TYPE_NAME", troubleTypeNameArray[i]);
				checkItemDao.insertTroubleType(typeMap);
			}
		}
		
	}
	
	/**
	 * 删除检查项
	 * 
	 * @param request 请求消息
	 */
	public void deleteCheckItem(HttpServletRequest request){
		
		//删除检查项
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("CHECK_ITEM_ID",  request.getParameter("selected"));
		checkItemDao.deleteCheckItem(map);
	}
	
	/**
	 * 根据检查项ID查询检查项详情
	 * 
	 * @param request 请求消息
	 * @return
	 */
	public Map<String,Object> queryCheckItemById(HttpServletRequest request){
		
		//TODO
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("CHECK_ITEM_ID", request.getParameter("check_item_id"));
		Map<String,Object> map = checkItemDao.queryCheckItemById(queryMap);
		
		//获取隐患类型
		map.put("troubleTypes", checkItemDao.queryTroubleTypeList(queryMap));
		return map;
	}
}
