package icom.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface InitDao {

	Map getAppInfo(String appType);

	Map validate(Map map);

	List<Map> getAllAppInfo(String user);
	List<Map> getAllAppInfoByuserIdNum(String user);

	List<Map> getUrl(Map map);

	int validateByStaffId(Map map);

	void changePwd(Map map);

	void feedbackAdvice(Map map);
	
	String getSjxjUrl();
	
	List<Map> getLoginStaff(Map map);

	Map singleLoginValidate(Map map);

	Map singleLoginValidateByStaffNo(Map map);
	Map singleLoginValidateByuserIdNum(Map map);
	
	List<Map> judgeIsAdmin(String userid);

}
