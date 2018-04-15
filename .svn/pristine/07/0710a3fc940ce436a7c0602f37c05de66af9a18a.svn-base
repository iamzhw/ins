package com.cableCheck.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

import com.cableInspection.model.EquipmentModel;
import com.cableInspection.model.OSSEquipmentModel;

@SuppressWarnings("all")
@Repository
public interface QuartzJobDao {

	public void createOssChangePorts(Map map) ;

	public List<Map> queryOssChangePorts() ;

	public void insertDtsj(Map portMap);
	
//	List<EquipmentModel> queryEquipmentList(Query query);
	
	
	void saveEquipmentInfo(Map map);
	
	List<Map> isEquipmentExist(Map map);
	List<Map> isEquipmentExistNew(Map map);
	

	/**
	 * 更新设备点等级字段
	 */
	void updateEquipmentLevel(Map map);
	/**
	 * 管理区中间表
	 */
	void truncateTableArea(Map map);
	/**
	 * 管理区中间表
	 */
	void insertTableArea(String jndi);

	void createOssEquipment(Map map);
	/**
	 * 同步OSS某一个地市的五大类设备
	 * @param map
	 */
	void createDynamicOssEquipment(Map map);
	
	/**
	 * 同步OSS某一个地市的五大类设备，切换数据源
	 */
	void createDynamicEquipment();
	/**
	 * 获取OSS某一地市五大类设备
	 * @param query
	 * @return
	 */
	List<OSSEquipmentModel> queryDynamicOssEquipments(Query query);

	public List<Map> queryObdsData();

	public void updateObds(Map map);

	public void createOssChangePortsSZ(Map map);

	public List<Map> queryEquipments(Query query);

	public void updateEqp(Map resultMap);

	public List<Map> queryOssGxl(Query query);

	public void insertList(Map eqp);

	public void truncateOSSGxl();

	public void backupOSSGxl();

	public void deleteBackup();

	public void insertNoResList(Map eqp);


	public void backupNoRes();

	public void truncateNoRes();

	public void deleteResBackup();

	public List<Map> queryNoRes(Query query);

	public void createQJST();

	public void createQJODSO();

	public void synchronizeElectronArchives();
	
	public void synchronizeEqpContract();
	
	public void synchronizeEqpWljb();

	public void createOssChangePortsNJ(Map map);

	public void createOssChangePortsZJ(Map map);

	public void createOssChangePortsWX(Map map);

	public void createOssChangePortsNT(Map map);

	public void createOssChangePortsYZ(Map map);

	public void createOssChangePortsYC(Map map);

	public void createOssChangePortsXZ(Map map);

	public void createOssChangePortsHA(Map map);

	public void createOssChangePortsLYG(Map map);

	public void createOssChangePortsSQ(Map map);

	public void createOssChangePortsTZ(Map map);

	public void createOssChangePortsCZ(Map map);

	public void insertBOD(Map portMap);

	public List<Map> queryOssOBD(Query query);

	public void truncateOBD();

	public List<Map> queryOssOBD(String jndi, Query query, boolean b);

	public void createOssOBD();

	public void createWANGGE();

	public void createMONTH();
	
	public void syncResRat();
	
	public void syncCvs();
	
	public void syncIom();

	public void createSYNC_APP_IOM_ORDER_OSS_INFO();

	public void createSYNC_odso_eqp_daily();

	public void createSYNC_APP_PROD_ACCSNO_EQP();
	
	
	public List<Map> selectExpireTask(int dayNum);
	
	public void calOrderNum(Map map);
	
	public void calOrderChange(Map map);
	public void calCheckError(Map map);
	public void calTeamOrder(Map map);

	public void calGridOrder(Map map);

	public List<Map> queryAreaPortNum(Map map);
	
	public List<Map<String, String>> getFtpDir();
	
	/**
	 * 启动存储过程计算，班组班组检查整改统计
	 * @param map
	 */
	public void calTeamCheck(Map map);
}
