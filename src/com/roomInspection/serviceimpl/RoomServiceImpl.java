package com.roomInspection.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import util.page.Query;
import util.page.UIPage;

import com.roomInspection.dao.RoomDao;
import com.roomInspection.service.RoomService;
import com.util.ExcelUtil;
import com.util.StringUtil;

@SuppressWarnings("all")
@Service
public class RoomServiceImpl implements RoomService {

	@Resource
	private RoomDao roomDao;
	
	public static Map<String, String> ROOM_TYPE = new HashMap<String, String>();

	@Override
	public Map<String, Object> roomQuery(HttpServletRequest request,
			UIPage pager) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ROOM_NAME", request.getParameter("room_name"));//机房名称
		map.put("ROOM_TYPE_ID", request.getParameter("room_type_id"));//机房类型ID
		map.put("CHECK_STAFF", request.getParameter("check_staff"));//巡检人ID
		map.put("ADDRESS", request.getParameter("address"));//地址信息
		map.put("LONGITUDE", request.getParameter("longitude"));//经度
		map.put("LATITUDE", request.getParameter("latitude"));//纬度
		map.put("CREATE_BY", request.getParameter("create_by"));//创建人
		map.put("CREATE_DATE", request.getParameter("create_date"));//创建日期
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map> olists = roomDao.roomQuery(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public void addRoom(HttpServletRequest request) {
	
		//封装参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ROOM_NAME", request.getParameter("room_name"));//机房名称
		map.put("ROOM_TYPE_ID", request.getParameter("room_type_id"));//机房类型ID
		map.put("CHECK_STAFF_ID", request.getParameter("check_staff_id"));//巡检人ID
		map.put("ADDRESS", request.getParameter("address"));//地址信息
		map.put("LONGITUDE", request.getParameter("longitude"));//经度
		map.put("LATITUDE", request.getParameter("latitude"));//纬度
		map.put("CREATE_BY", request.getParameter("create_by"));//创建人
		map.put("CREATE_DATE", request.getParameter("create_date"));//创建日期
		map.put("ROOM_CODE", request.getParameter("room_code"));//机房
		map.put("AREA_ID", request.getParameter("area_id"));//地区ID
		map.put("SON_AREA_ID", request.getParameter("son_area_id"));//区县ID
		
		roomDao.insertRoom(map);
	}

	@Override
	public void deleteRoom(HttpServletRequest request) {
		
		//封装参数
		Map<String, Object> map = new HashMap<String, Object>();
		
		String selected = request.getParameter("selected");//获取要所有要删除机房的ID
		HttpSession session = request.getSession();
		String[] roomIds = selected.split(",");
		String room_ids = "";
		for (int i = 0; i < roomIds.length; i++) {
			room_ids +=roomIds[i]+",";
		}
		if(room_ids.endsWith(",")){
			room_ids=room_ids.substring(0,room_ids.length()-1);
		}
		
		map.put("ROOM_ID", room_ids);//机房ID
		roomDao.deleteRoom(map);
		
	}

	@Override
	public Map<String, Object> queryRoombyRoomId(HttpServletRequest request) {
		
		//获取机房ID
		int roomId = Integer.parseInt(request.getParameter("room_id"));
		
		return roomDao.queryRoombyRoomId(roomId);
	}

	@Override
	public void updateRoom(HttpServletRequest request) {
		
		//封装参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ROOM_ID", request.getParameter("room_id"));//机房名称
		map.put("ROOM_NAME", request.getParameter("room_name"));//机房名称
		map.put("ROOM_TYPE_ID", request.getParameter("room_type_id"));//机房类型ID
		map.put("CHECK_STAFF_ID", request.getParameter("check_staff_id"));//巡检人ID
		map.put("ADDRESS", request.getParameter("address"));//地址信息
		map.put("LONGITUDE", request.getParameter("longitude"));//经度
		map.put("LATITUDE", request.getParameter("latitude"));//纬度
		map.put("MODIFY_BY", request.getParameter("modify_by"));//修改人
		map.put("MODIFY_DATE", request.getParameter("modify_date"));//修改日期
		map.put("ROOM_CODE", request.getParameter("room_code"));//机房
		map.put("AREA_ID", request.getParameter("area_id"));//地区ID
		map.put("SON_AREA_ID", request.getParameter("son_area_id"));//区县ID
		
		roomDao.updateRoom(map);
		
	}
	
	@Override
	public JSONObject importRoom(HttpServletRequest request, MultipartFile file) {
		JSONObject result = new JSONObject();
		String repeatRows = "";
		String errorRows = "";
		try {
			
			//获取所有机房类型
			List<Map<String,Object>> roomTypes = roomDao.getRoomTypes();
			for(Map<String,Object> map : roomTypes)
			{
				ROOM_TYPE.put(String.valueOf(map.get("ROOM_TYPE_NAME")), String.valueOf(map.get("ROOM_TYPE_ID")));
			}
			
			
			String staffNo = request.getSession().getAttribute("staffNo").toString();
			ExcelUtil parse = new ExcelUtil(file.getInputStream());
			List<List<String>> datas = parse.getDatas(7);
			Map<String, Object> params = null;
			List<String> data = null;
			Integer point_type;
			Integer equip_type;
			for(int i = 0, j = datas.size(); i < j; i ++){
				params = new HashMap<String, Object>();
				data = datas.get(i);
				
				//第一列为机房名称
				params.put("ROOM_NAME", data.get(0));
				
				//第二列机房类型
				params.put("ROOM_TYPE_ID", ROOM_TYPE.get(data.get(1)));
				
				//第三列为巡检人名称
				String check_staff_name = data.get(2);
				if(!StringUtil.isEmpty(check_staff_name))
				{
					//通过名称查询用户staff_id
				}
				
				//第四列为经度
				params.put("LONGITUDE", data.get(3));
				
				//第五列为纬度
				params.put("LATITUDE", data.get(4));
				
				//第六列为公司地址
				params.put("ADDRESS", data.get(5));
				
				//第七列为机房编码
				params.put("ROOM_CODE", data.get(6));
				//保存机房信息
			}
		} catch (Exception e) {
			result.put("status", false);
			result.put("message", "文件格式错误！");
			return result;
		}
		
		if(! "".equals(repeatRows) || ! "".equals(errorRows)){
			result.put("status", false);
			String message = null;
			if(! "".equals(repeatRows)){
				message = "编号 " + repeatRows.substring(1) + " 的记录重复：存在相同编码的点！";
			}
			if(! "".equals(errorRows)){
				String str = "编号 " + errorRows.substring(1) + " 的记录数据有误：数据为空或数据不符合规范！";
				if(message == null){
					message = str;
				} else {
					message += str;
				}
			}
			result.put("message", message);
		} else {
			result.put("status", true);
		}
		return result;
	}
	
	/**
	 * 根据用户名称返回用户ID,如果找不到就插入用户
	 * @param staff_name 用户名称
	 * @return
	 */
	public int getCheckStaffId(String staff_name){
		
		return 0;
	}

}
