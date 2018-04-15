package com.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface StaffDao {

    /**
     * @Title: proveUniqueness
     * @Description:
     * @param: @return
     * @return: Integer
     * @throws
     */
    public Integer proveUniqueness(Map map);
    
    /**
	 * 验证身份证是否唯一
	 * @param map
	 * @return
	 */
	public Integer validateIdCard(Map map);

    /**
     * @Title: save
     * @Description:
     * @param: @param map
     * @return: void
     * @throws
     */
    public void insert(Map map);

    /**
     * @Title: getStaff
     * @Description:
     * @param: @param staffId
     * @param: @return
     * @return: Map
     * @throws
     */
    public Map getStaff(String staffId);

    /**
     * @Title: update
     * @Description:
     * @param: @param map
     * @return: void
     * @throws
     */
    public void update(Map map);

    public void delete(Map map);

    public List<Map> query(Query query);
    /**
     * 查询全部帐号
     * @return
     */
    public List<Map> findAllStaff();

    public List<Map> querySoftPermissions(Query query);

    public void saveSoftPermissions(Map map);

    public void delSoftPermissions(String aspStaffId);

    public List getRoles(String staffId);

    public List<Map> querySoftPermission(Map map);

    public List<Map> queryStaffList(Map map);

    public List getSofts(String staffId);

    public void saveRolePermissions(Map map);

    public List<Map> queryRolePermission(Map map);

    // public List<Map> getRoleNo(int id);

    public int getifGly(String id);

    public List<Map<String, Object>> queryHandler(Query query);
    
    public List<Map<String, Object>>  queryHandlerFromJYH(Query query);

    public void delRolePermissions(String asp_staff_id);

    public int isRepeat(Map<String, Object> params);

    public void modifyPassword(Map map);
    
    /**
     * 实名认证
     * @param map
     * @author linzhengcheng
     */
	public void modifyIdNumber(Map map);
	/**
	 * 认证手机号
	 * @param map
	 * @author wangxiangyu
	 */
	public void modifyMobileNumber(Map map);
	/**
	 * 真实姓名
	 * @param map
	 */
	public void modifyRealName(Map map);

    public void modifyPadPassword(Map map);

    public int isHasRole(Map<String, Object> params);

    public int getUserRoleCount(Map<String, Object> params);

    List<Map<String, Object>> getAuditorNameBySonAreaId(String zoneid);

    List<Map<String, Object>> getStaffInfoById(String zoneid);

    /**
     * @param staffId
     * @return
     */
    public Map<String, Object> findPersonalInfo(String staffId);

    /**
     * 作用：根据id查询角色 　　*作者：
     * 
     * @param fiber_eponsible_by
     * @return
     */
    public List<Map<String, Object>> getRoleList(String user_id);

    public List<Map<String,Object>> getStaffSoftList(String STAFF_ID);
    /**
     * 作用： 　　*作者：
     * 
     * @param userId
     * @return
     */
    public String getStaffAreaId(String userId);
    
    public void updateLoginDate(String userId);

	public Map<String, Object> getOutSitePermissions(String staffId);

	public void saveAssignOutSitePermissions(Map map);

	public void insertAssignOutSitePermissions(Map map);
	
	public List<Map> selRoleByID(Map map);
	
	public int ifExitsByStaffDept(Map m);
	public void updateDept(Map m);
	public void addStaffDept(Map m);
	public void removeStaffDept(String s);

}
