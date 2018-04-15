package com.system.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import util.dataSource.SwitchDataSourceUtil;
import util.page.Query;
import util.page.UIPage;
import util.password.MD5;

import com.cableInspection.dao.PointDao;
import com.cableInspection.dao.PointManageDao;
import com.cableInspection.serviceimpl.PointManageServiceImpl;
import com.system.constant.RoleNo;
import com.system.dao.GeneralDao;
import com.system.dao.StaffDao;
import com.system.service.StaffService;
import com.util.ExcelUtil;
import com.util.StringUtil;

@SuppressWarnings("all")
@Service
public class StaffServiceImpl implements StaffService {
	@Resource
	private StaffDao staffDao;
	@Resource
	private GeneralDao generalDao;

	@Resource
	private PointManageDao pointManageDao;
	
	@Resource
	private PointDao pointDao;

	/**
	 * <p>
	 * Title: query
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @see com.zbiti.system.service.interfaces.StaffService#query(javax.servlet.http.HttpServletRequest)
	 */

	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String staff_name = request.getParameter("staff_name");
		String staff_no = request.getParameter("staff_no");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		String org_name = request.getParameter("org_name");
		String role_name = request.getParameter("role_name");
		String id_number = request.getParameter("id_number");
		map.put("STAFF_NAME", staff_name);
		map.put("STAFF_NO", staff_no);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", son_area);
		map.put("ORG_NAME", org_name);
		map.put("ROLE_NAME", role_name);
		map.put("ID_NUMBER", id_number);
		
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.HAS_LXXJ)) {
			map.put("hasLxxj", 1);
		}
		if ((Boolean) session.getAttribute(RoleNo.HAS_JFXJ)) {
			map.put("hasJfxj", 1);
		}
		if ((Boolean) session.getAttribute(RoleNo.HAS_ZYXJ)) {
			map.put("hasZyxj", 1);
		}
		if ((Boolean) session.getAttribute(RoleNo.HAS_AXX)) {
			map.put("hasAxx", 1);
		}
		if ((Boolean) session.getAttribute(RoleNo.HAS_CABLE)) {
			map.put("hasCable", 1);
		}

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map> olists = staffDao.query(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;

	}

	/**
	 * <p>
	 * Title: proveUniqueness
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param parameter
	 * @return
	 * @see com.zbiti.system.service.interfaces.StaffService#proveUniqueness(java.lang.String)
	 */
	@Override
	public Boolean proveUniqueness(HttpServletRequest request) {
		String staff_no = (String) request.getParameter("staff_no");
		String notstaff_no = (String) request.getParameter("notstaff_no");
		Map map = new HashMap();
		map.put("STAFF_NO", staff_no);
		map.put("NOTSTAFF_NO", notstaff_no);

		Integer count = staffDao.proveUniqueness(map);
		if (count == 0)
			return true;
		else
			return false;
	}

	@Override
	public Boolean validateIdCard(HttpServletRequest request) {
		String ID_NUMBER = (String) request.getParameter("id_number");
		String NEWID_NUMBER = (String) request.getParameter("newId_number");
		Map map = new HashMap();
		map.put("ID_NUMBER", ID_NUMBER);
		map.put("NEWID_NUMBER", NEWID_NUMBER);

		Integer count = staffDao.validateIdCard(map);
		if (count == 0)
			return true;
		else
			return false;
	}

	/**
	 * <p>
	 * Title: save
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @see com.zbiti.system.service.interfaces.StaffService#save(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void insert(HttpServletRequest request) throws Exception {
		String staff_name = request.getParameter("staff_name");
		String staff_no = request.getParameter("staff_no");
		String pwd = request.getParameter("password");
		String password = MD5.encrypt(MD5.md5s(pwd));
		String tel = request.getParameter("tel");
		String id_number = request.getParameter("id_number");
		String email = request.getParameter("email");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		String status = request.getParameter("status");
		String own_company = request.getParameter("own_company");
		String maintor_type = request.getParameter("maintor_type");
		Map map = new HashMap();
		map.put("STAFF_NAME", staff_name);
		map.put("STAFF_NO", staff_no);
		map.put("PASSWORD", pwd);
		map.put("PASSWORD_", password);
		map.put("TEL", tel);
		map.put("ID_NUMBER", id_number);
		map.put("EMAIL", email);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", son_area);
		map.put("STATUS", status);
		map.put("OWN_COMPANY", own_company);
		map.put("MAINTOR_TYPE", maintor_type);

		HttpSession session = request.getSession();
		map.put("CREATE_STAFF", session.getAttribute("staffId"));
		map.put("MODIFY_STAFF", session.getAttribute("staffId"));

		staffDao.insert(map);
	}

	/**
	 * <p>
	 * Title: edit
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param staffId
	 * @return
	 * @see com.zbiti.system.service.interfaces.StaffService#edit(java.lang.String)
	 */
	@Override
	public Map<String, Object> edit(HttpServletRequest request) {

		Map<String, Object> rs = new HashMap<String, Object>();
		/**
		 * 1、根据staffId查询员工信息 2、查询地市 3、根据员工的staffId查询区县列表
		 */
		String staff_id = String.valueOf(request.getParameter("staff_id"));
		String staffId = request.getSession().getAttribute("staffId").toString();
		int ifGly = staffDao.getifGly(staffId);
		rs.put("ifGly", ifGly);
		Map staff = staffDao.getStaff(staff_id);
		HttpSession session = request.getSession();
		List<Map<String, String>> areaList = generalDao.getAreaList();
		List<Map<String, String>> sonAreaList = generalDao.getSonAreaListByStaffId(staff_id);

		rs.put("staff", staff);
		rs.put("areaList", areaList);
		rs.put("sonAreaList", sonAreaList);

		// 获取公司列表
		String areaId = staff.get("AREA_ID").toString();
		List<Map<String, String>> companyList = generalDao.getOwnCompany(areaId);
		rs.put("companyList", companyList);
		return rs;
	}

	/**
	 * <p>
	 * Title: update
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @throws Exception
	 * @see com.zbiti.system.service.interfaces.StaffService#update(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void update(HttpServletRequest request) throws Exception {
		String staff_id = request.getParameter("staff_id");
		String staff_name = request.getParameter("staff_name");
		String staff_no = request.getParameter("staff_no");
		String password = request.getParameter("password");
		String password_ = MD5.encrypt(MD5.md5s(request.getParameter("password")));
		String tel = request.getParameter("tel");
		String id_number = request.getParameter("id_number");
		String email = request.getParameter("email");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		String status = request.getParameter("status");
		String own_company = request.getParameter("own_company");
		String maintor_type = request.getParameter("maintor_type");
		
		Map map = new HashMap();
		map.put("STAFF_ID", staff_id);
		map.put("STAFF_NAME", staff_name);
		map.put("STAFF_NO", staff_no);
		map.put("PASSWORD", password);
		map.put("PASSWORD_", password_);
		map.put("TEL", tel);
		map.put("ID_NUMBER", id_number);
		map.put("EMAIL", email);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", son_area);
		map.put("STATUS", status);
		map.put("OWN_COMPANY", own_company);
		map.put("MAINTOR_TYPE", maintor_type);
		
		HttpSession session = request.getSession();
		map.put("MODIFY_STAFF", session.getAttribute("staffId"));
		staffDao.update(map);
	}

	/**
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @see com.zbiti.system.service.interfaces.StaffService#delete(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void delete(HttpServletRequest request) {

		String selected = request.getParameter("selected");
		HttpSession session = request.getSession();
		Map map = new HashMap();
		map.put("MODIFY_STAFF", session.getAttribute("staffId"));
		String[] staffs = selected.split(",");
		for (int i = 0; i < staffs.length; i++) {
			map.put("STAFF_ID", staffs[i]);
			staffDao.delete(map);
		}

	}

	@Override
	public Map<String, Object> querySoftPermissions(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String staff_id = request.getParameter("staff_id");
		String software_name = request.getParameter("software_name");

		map.put("SOFTWARE_NAME", software_name);

		// Query query = new Query();
		// query.setPager(pager);
		// query.setQueryParams(map);
		// List<Map> olists = staffDao.querySoftPermissions(query);

		List<Map> olists = staffDao.querySoftPermission(map);

		Map<String, Object> pmap = new HashMap<String, Object>(0);
		// pmap.put("total", query.getPager().getRowcount());
		pmap.put("total", olists.size());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public void saveSoftPermissions(HttpServletRequest request) {
		String selected_softs = request.getParameter("selected_softs");
		String asp_staff_id = request.getParameter("asp_staff_id");
		String[] softs = selected_softs.split(",");
		staffDao.delSoftPermissions(asp_staff_id);
		for (int i = 0; i < softs.length; i++) {
			Map map = new HashMap();
			map.put("STAFF_ID", asp_staff_id);
			map.put("SOFTWARE_ID", softs[i]);
			// 执行插入操作
			staffDao.saveSoftPermissions(map);

		}
	}

	@Override
	public List getRoles(String staffId) {
		return staffDao.getRoles(staffId);
	}

	@Override
	public List<Map> queryStaffList(HttpServletRequest request) {
		Map map = new HashMap();
		String staff_name = request.getParameter("staff_name");
		String staff_no = request.getParameter("staff_no");
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		map.put("STAFF_NAME", staff_name);
		map.put("STAFF_NO", staff_no);
		map.put("AREA_ID", area);
		map.put("SON_AREA_ID", son_area);

		List<Map> staffList = staffDao.queryStaffList(map);
		return staffList;
	}

	@Override
	public List getSofts(String staffId) {
		return staffDao.getSofts(staffId);

	}

	@Override
	public Map<String, Object> queryRolePermissions(HttpServletRequest request, UIPage pager) {
		//1.查询出当前登录人员ID
		String staff_id = String.valueOf(request.getSession().getAttribute("staffId"));
		Map map = new HashMap();//条件map
		//3.查询当前登录人员角色并判断包含哪四种角色，有加入到map集合中。
		//3.1 超级管理员(GLY)全部角色 下面就不需要判断了
		int ifGly = staffDao.getifGly(staff_id);
		if(1 == ifGly){//如果是超级管理员查询出全部角色,在map集合中放入字段，sql中如果为空就加上id条件
			map.put("ifGly","ifGly");
		}else{
			Map paramMap = new HashMap();
			paramMap.put("staff_id", staff_id);

			//3.2 资源巡检(ZYXJ) 全部ZYXJ开头的角色
			paramMap.put("type", "ZYXJ");
			List<Map> zyxjList = staffDao.selRoleByID(paramMap);
			if(zyxjList.size() > 0){
				map.put("zyxj","ZYXJ");
			}
			//3.3 爱巡线(AXX) 全部AXX相关的角色
			paramMap.put("type", "AXX");
			List<Map> axxList = staffDao.selRoleByID(paramMap);
			if(axxList.size() > 0){
				map.put("axx","AXX");
			}
			//3.6 机房巡检(JFXJ) 查询所有机房巡检的角色
			paramMap.put("type", "JFXJ");
			List<Map> jfxjList = staffDao.selRoleByID(paramMap);
			if(jfxjList.size() > 0){
				map.put("jfxj","JFXJ");
			}
			//3.4 缆线看护(LXKH)、缆线巡检(LXXJ) LX的去进行查询所有。按省、市、区来划分，查询包含这三个字段的
			paramMap.put("type", "LX");
			List<Map> lxList = staffDao.selRoleByID(paramMap);
			if(lxList.size() > 0){
				map.put("lx","LX");
				Map lxMap = lxList.get(0);
				if(Integer.parseInt(lxMap.get("PROVINCE_COUNT").toString()) == 1){
					map.put("lx_province_count","1");
				}else if(Integer.parseInt(lxMap.get("CITY_COUNT").toString()) == 1){
					map.put("lx_city_count","1");
				}else if(Integer.parseInt(lxMap.get("AREA_COUNT").toString()) == 1){
					map.put("lx_area_count","1");
				}
			}
			//3.5 光网助手(CABLE) 查询所有。按省、市、区来划分，查询包含这三个字段的
			paramMap.put("type", "CABLE");
			List<Map> cableList = staffDao.selRoleByID(paramMap);
			if(cableList.size() > 0){
				map.put("cable","CABLE");
				Map cableMap = cableList.get(0);
				if(Integer.parseInt(cableMap.get("PROVINCE_COUNT").toString()) == 1){
					map.put("cable_province_count","1");
				}else if(Integer.parseInt(cableMap.get("CITY_COUNT").toString()) == 1){
					map.put("cable_city_count","1");
				}else if(Integer.parseInt(cableMap.get("AREA_COUNT").toString()) == 1){
					map.put("cable_area_count","1");
				}
			}
		}
		List<Map> olists = staffDao.queryRolePermission(map);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", olists.size());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public void saveRolePermissions(HttpServletRequest request) {
		String selected_roles = request.getParameter("selected_roles");
		String asp_staff_id = request.getParameter("asp_staff_id");
		String[] roles = selected_roles.split(",");
		staffDao.delRolePermissions(asp_staff_id);
		for (int i = 0; i < roles.length; i++) {
			Map map = new HashMap();
			map.put("STAFF_ID", asp_staff_id);
			map.put("ROLE_ID", roles[i]);
			// 执行插入操作
			staffDao.saveRolePermissions(map);

		}
	}

	@Override
	public String selectSelected(HttpServletRequest request) {
		String staffId = request.getSession().getAttribute("staffId").toString();
		int ifGly = staffDao.getifGly(staffId);
		Map map = new HashMap();
		map.put("ifGly", ifGly);
		JSONArray json = new JSONArray();
		String jsonString = json.fromObject(map).toString();
		return jsonString;
	}

	@Override
	public Map addRole(String staffId) {
		Map staff = staffDao.getStaff(staffId);
		return staff;

	}

	@Override
	public Map<String, Object> queryHandler(HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String staff_name = request.getParameter("staff_name");
		String staff_no = request.getParameter("staff_no");
		String son_area = request.getParameter("son_area");
		String operate = request.getParameter("operate");
		String billIds = request.getParameter("billIds");
		String grid_name=request.getParameter("grid_name");
		String[] billId=billIds.split(",");
		map.put("STAFF_NAME", staff_name);
		map.put("STAFF_NO", staff_no);
		map.put("GRID_NAME", grid_name);

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else if ((Boolean) session.getAttribute(RoleNo.LXXJ_AUDITOR)) {// 是否是审核员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					map.put("AREA_ID", -1);
				}
			}
		}

		if ("forward".equals(operate)) {
			map.put("ROLE_NO", "LXXJ_AUDITOR");
			map.put("AREA_ID", session.getAttribute("areaId"));
			if (StringUtils.isNotBlank(son_area)) {
				map.put("SON_AREA_ID", son_area);
			} else {
				map.remove("SON_AREA_ID");
			}
		} else {
			map.put("ROLE_NO", "LXXJ_MAINTOR");
		}
		String eqpId="";
		List<Map<String, Object>> olists = new ArrayList<Map<String, Object>>();
		if(billId.length>0){
			eqpId=pointDao.decodeEqyTypeByBill(billId[0]);
			if(null!=eqpId&&!eqpId.equals("0")){
				map.put("AREA_ID", pointDao.getSonAreaByBillId(billId[0]));
			}
		}
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		//集约化人员列表
		
		if(null!=eqpId&&!eqpId.equals("0")){
			try{
			SwitchDataSourceUtil.setCurrentDataSource("orcl153");
			olists = staffDao.queryHandlerFromJYH(query);
			SwitchDataSourceUtil.clearDataSource();
			} catch (Exception e) {
				SwitchDataSourceUtil.clearDataSource();
			}
		}else{
			olists = staffDao.queryHandler(query);
		}
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	//简单验证15和18位身份证号码
	public Boolean isIdNumber(String idNumber)
	{
		String matchString = "(^\\d{18}$)|(^\\d{15}$)";

		Pattern pattern = Pattern.compile(matchString);
		
		Matcher matcher = pattern.matcher(idNumber);

		boolean isTrue = matcher.matches();

		return isTrue;
	}
	
	@Override
	public Object importDo(HttpServletRequest request, MultipartFile file) {
		JSONObject result = new JSONObject();
		String repeatRows = "";
		String errorRows = "";
		try {

			String staffId = request.getSession().getAttribute("staffId").toString();
			ExcelUtil parse = new ExcelUtil(file.getInputStream());
			List<List<String>> datas = parse.getDatas(9);
			Map<String, Object> params = null;
			List<String> data = null;
			Integer point_type;
			Integer equip_type;
			for (int i = 0, j = datas.size(); i < j; i++) {
				params = new HashMap<String, Object>();
				data = datas.get(i);
				// 验证用户姓名
				if (StringUtil.isEmpty(data.get(0))) {
					continue;
				}
				params.put("STAFF_NAME", data.get(0));
				// 验证用户帐号
				params.put("STAFF_NO", data.get(1));
				if (StringUtil.isEmpty(data.get(1))) {
					errorRows += "," + (i + 2);
					continue;
				} else {
					int rows = staffDao.isRepeat(params);
					if (rows > 0) {
						repeatRows += "," + (i + 2);
						continue;
					}
				}
				// 验证密码
				if (StringUtil.isEmpty(data.get(2))) {
					errorRows += "," + (i + 2);
					continue;
				} else {
					params.put("PASSWORD", "");
					params.put("PASSWORD_", MD5.encrypt(MD5.md5s(data.get(2))));
				}
				params.put("TEL", data.get(3));
				
				// 身份证号码（非空，唯一）
				params.put("ID_NUMBER", data.get(4));
				if (StringUtil.isEmpty(data.get(4))) {
					errorRows += "," + (i + 2);
					continue;
				} 
				else{
					if(!this.isIdNumber(data.get(4))){
						errorRows += "," + (i + 2);
						continue;
					}
				}
				/*else {//唯一验证
					int count = staffDao.validateIdCard(params);
					
					if (count > 0) {
						repeatRows += "," + (i + 2);
						continue;
					}
				}*/
				
				params.put("EMAIL", data.get(5));

				// 验证地市
				if (StringUtil.isEmpty(data.get(6))) {
					errorRows += "," + (i + 2);
					continue;
				} else {
					params.put("AREA_ID", getAreaId(data.get(6)));
				}

				// 验证区县
				if (StringUtil.isEmpty(data.get(7))) {
					errorRows += "," + (i + 2);
					continue;
				} else {
					params.put("SON_AREA_ID", getAreaId(data.get(7)));
				}

				// 验证状态
				if ("不可用".equals(data.get(8))) {
					params.put("STATUS", 1);
				} else {
					params.put("STATUS", 0);
				}
				params.put("CREATE_STAFF", staffId);
				params.put("MODIFY_STAFF", staffId);
				// 保存点
				staffDao.insert(params);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", false);
			result.put("message", "文件格式错误！");
			return result;
		}
		if (!"".equals(repeatRows) || !"".equals(errorRows)) {
			result.put("status", false);
			String message = null;
			if (!"".equals(repeatRows)) {
				message = "编号 " + repeatRows.substring(1) + " 的记录重复：用户帐号已存在！";
			}
			if (!"".equals(errorRows)) {
				String str = "编号 " + errorRows.substring(1) + " 的记录数据有误：数据为空或数据不符合规范！";
				if (message == null) {
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

	private String getAreaId(String area_name) {
		if (PointManageServiceImpl.AREA.containsKey(area_name)) {
			return PointManageServiceImpl.AREA.get(area_name);
		}
		List<Map<String, String>> result = pointManageDao.getAreaNameById(area_name);
		if (result == null || result.get(0) == null) {
			return null;
		} else {
			String area_id = result.get(0).get("AREA_ID");
			PointManageServiceImpl.AREA.put(area_name, area_id);
			return area_id;
		}
	}

	@Override
	public void modifyPassword(HttpServletRequest request) {

		Map map = new HashMap();

		String staffNo = (String) request.getSession().getAttribute("staffNo");
		String password = request.getParameter("password");
		String password_ = password = MD5.encrypt(MD5.md5s(password));
		map.put("staffNo", staffNo);
		map.put("password", password);
		map.put("password_", password_);
		staffDao.modifyPassword(map);

		try {
			// 修改老系统的密码，如果失败不影响新系统密码修改
			staffDao.modifyPadPassword(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * TODO
	 */
	@Override
	public void modifyIdNumber(HttpServletRequest request) {
		Map map = new HashMap();

		//String staffNo = (String) request.getSession().getAttribute("staffNo");
		String staffId = (String) request.getSession().getAttribute("staffId");
		String ID_NUMBER = request.getParameter("ID_NUMBER");
		String mobileNumber = request.getParameter("mobileNumber");
		String real_name = request.getParameter("real_name");
		map.put("staff_id", staffId);
		map.put("ID_NUMBER", ID_NUMBER);
		map.put("mobileNumber", mobileNumber);
		map.put("real_name", real_name);
		try {
			staffDao.modifyIdNumber(map);
			staffDao.modifyMobileNumber(map);
			staffDao.modifyRealName(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Map<String, Object> findPersonalInfo(String staffId) {
		return staffDao.findPersonalInfo(staffId);
	}


	@Override
	public Map<String, Object> getOutSitePermissions(String staffId){
		return  staffDao.getOutSitePermissions(staffId);
	}

	@Override
	public void saveAssignOutSitePermissions(HttpServletRequest request) {
		String area_level = request.getParameter("area_level");
		String manage_level = request.getParameter("manage_level");
		String staff_id = request.getParameter("staff_id");
		Map map = new HashMap();
		map.put("STAFF_ID", staff_id);
		map.put("MANAGE_LEVEL", manage_level);
		map.put("AREAL_LEVEL", area_level);
		// 执行插入操作
		Map<String, Object>  roles = staffDao.getOutSitePermissions(staff_id);
		if(roles !=null ){
			staffDao.saveAssignOutSitePermissions(map);
		}else{
			staffDao.insertAssignOutSitePermissions(map);
		}


	}

	@Override
	public Map<String, Object> gridManage(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String staff_id = String.valueOf(request.getParameter("staff_id"));
		String staffId = request.getSession().getAttribute("staffId").toString();
		HttpSession session = request.getSession();
		List<Map<String, String>> areaList = generalDao.getAreaList();
		List<Map<String, String>> sonAreaList = generalDao.getSonAreaListByStaffId(staff_id);
		List<String> deptList = generalDao.getDeptListByStaff(staff_id);

		rs.put("asp_staff_id", staff_id);
		rs.put("deptList",deptList);
		return rs;
	}

	@Override
	public void updateDept(HttpServletRequest request) {
		String selected_depts = request.getParameter("selected_depts");
		String asp_staff_id = request.getParameter("asp_staff_id");
		String[] depts = selected_depts.split(",");
		staffDao.removeStaffDept(asp_staff_id);
		Map map = new HashMap();
		if (depts.length > 0) {
			for (int i = 0; i < depts.length; i++) {
				map.put("staff_id", asp_staff_id);
				map.put("dept_id", depts[i]);
				staffDao.addStaffDept(map);
			}
		}
	}

	@Override
	public Map<String, Object> queryDept(HttpServletRequest request,
			UIPage pager) {
		String staff_id = request.getParameter("staff_id");
		List<Map<String, String>> olists = generalDao.getDeptList(staff_id);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", olists.size());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public Map queryByStaffId(String STAFF_ID) {
		
		Map staff = staffDao.getStaff(STAFF_ID);
		
		return staff;
	}

	@Override
	public List<Map> findAllStaff() {
		
		List<Map> staffs = staffDao.findAllStaff();
		return staffs;
	}

}
