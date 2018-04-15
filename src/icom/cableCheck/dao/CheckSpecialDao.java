package icom.cableCheck.dao;

import java.util.List;
import java.util.Map;

public interface CheckSpecialDao {

	List<Map> getTaskList(Map param);

	List<Map> getDistance(Map param);

	Map getzgMessageByTaskId(String taskId);

	List<Map<String, Object>> getzgEqpPhoto(Map zgparamMap);

	List<Map<String, Object>> getPortMessage(Map portParamMap);

	List<Map> getGDList(Map gDmap);

	List<Map> getGlList(Map param);

	
	List<Map> getDelEqpList(Map param);

	Map getEqpDetail(Map eqppara);

	List<Map> getIOMList(Map param);

	List<Map> getIOMSonareaList(String areaId);

	List<Map> getOSSSonareaList(String areaId);

	List<Map> getKxOrder(Map map);

	String queryAbr(Map paramap);

	String queryResno(Map paramap);

	String queryDes(Map paramap);

	String queryNotes(Map paramap);

	String queryResnoAddr(Map paramap);

	void saveKxTask(Map map);

	void updateKxRecord(Map map);

	int getDetalId();

	void updateKxTask(Map map);

	List<Map> getEQPInfo(String aCCNBR);

	int getDelPort(Map a);


	



}
