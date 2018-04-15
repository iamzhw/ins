package com.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import util.page.UIPage;

@SuppressWarnings("all")
public interface StaffService {

    /**
     * @Title: query
     * @Description:
     * @param: @param request
     * @param: @return
     * @return: Page
     * @throws
     */

    public Map<String, Object> query(HttpServletRequest request, UIPage pager);
    /**
     * 查询全部帐号
     * @return
     */
    public List<Map> findAllStaff();

    /**
     * @Title: proveUniqueness
     * @Description:
     * @param: @param parameter
     * @param: @return
     * @return: Boolean
     * @throws
     */
    public Boolean proveUniqueness(HttpServletRequest request);
    
    /**
	 * 验证身份证是否唯一
	 * @param request
	 * @author linzhengcheng
	 * @return
	 */
	public Boolean validateIdCard(HttpServletRequest request);

    /**
     * @throws Exception
     * @Title: save
     * @Description:
     * @param: @param request
     * @param: @return
     * @return: Boolean
     * @throws
     */
    public void insert(HttpServletRequest request) throws Exception;

    /**
     * @Title: edit
     * @Description:
     * @param: @param staffId
     * @param: @return
     * @return: Map<String,Object>
     * @throws
     */
    public Map<String, Object> edit(HttpServletRequest request);

    /**
     * @throws Exception
     * @Title: update
     * @Description:
     * @param: @param request
     * @return: void
     * @throws
     */
    public void update(HttpServletRequest request) throws Exception;

    /**
     * @Title: delete
     * @Description:
     * @param: @param request
     * @return: void
     * @throws
     */
    public void delete(HttpServletRequest request);

    public Map<String, Object> querySoftPermissions(HttpServletRequest request, UIPage pager);

    public void saveSoftPermissions(HttpServletRequest request);

    public List getRoles(String staffId);

    public List<Map> queryStaffList(HttpServletRequest request);

    public List getSofts(String staffId);

    public Map<String, Object> queryRolePermissions(HttpServletRequest request, UIPage pager);

    public void saveRolePermissions(HttpServletRequest request);

    public String selectSelected(HttpServletRequest request);

    Map addRole(String staffId);
    
    public Map queryByStaffId(String staffId);

    public Map<String, Object> queryHandler(HttpServletRequest request, UIPage pager);

    public Object importDo(HttpServletRequest request, MultipartFile file);

    public void modifyPassword(HttpServletRequest request);

    /**
     * 实名认证
     * @param request
     * @author linzhengcheng
     */
	public void modifyIdNumber(HttpServletRequest request);

    /**
     * @param staffId
     * @return
     */
    public Map<String, Object> findPersonalInfo(String staffId);

	public Map getOutSitePermissions(String staffId);

	public void saveAssignOutSitePermissions(HttpServletRequest request);

	public Map<String, Object> gridManage(HttpServletRequest request);

	public void updateDept(HttpServletRequest request);

	public Map<String, Object> queryDept(HttpServletRequest request,
			UIPage pager);
	
}
