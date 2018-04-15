package com.cableCheck.serviceimpl;

import icom.cableCheck.serviceimpl.CheckOrderServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.dataSource.SwitchDataSourceUtil;

import com.cableCheck.dao.SyncOssDataTaskDao;
import com.cableCheck.service.SyncOssDataTaskService;

@Service
@SuppressWarnings("all")
public class SyncOssDataTaskServiceImpl implements SyncOssDataTaskService {

	@Autowired
	SyncOssDataTaskDao syncOssDataTaskDao;
	
	//数据源，该数据源可以直接访问13个本地网的数据
	private static final String JNDI = "cpf83";
	//13地市的dblinks
	private static List<String> GWZS_JNDI_LIST = null;
	
	Logger logger = LoggerFactory.getLogger(SyncOssDataTaskServiceImpl.class);
	
	private void initParams(){
		GWZS_JNDI_LIST = new ArrayList<String>();
		GWZS_JNDI_LIST.add("OSSBC_DEV_SQ");
		GWZS_JNDI_LIST.add("OSSBC_DEV_WX");
		GWZS_JNDI_LIST.add("OSSBC_DEV_CZ");
		GWZS_JNDI_LIST.add("OSSBC_DEV_ZJ");
		GWZS_JNDI_LIST.add("OSSBC_DEV_NT");
		GWZS_JNDI_LIST.add("OSSBC_DEV_TZ");
		GWZS_JNDI_LIST.add("OSSBC_DEV_YZ");
		GWZS_JNDI_LIST.add("OSSBC_DEV_HA");
		GWZS_JNDI_LIST.add("OSSBC_DEV_YC");
		GWZS_JNDI_LIST.add("OSSBC_DEV_LYG");
		GWZS_JNDI_LIST.add("OSSBC_DEV_XZ");
		GWZS_JNDI_LIST.add("OSSBC_DEV_NJ");
		GWZS_JNDI_LIST.add("OSSBC_DEV_SZ");
	}
	
	@Override
	public void syncEqpNoOBDLess24() {
		initParams();
		
		int count = 0;
		for(String jndi : GWZS_JNDI_LIST){
			logger.info("----------------开始同步-----【"+jndi+"】，第"+ ++count +"个地市设备信息---------------"+new Date());
			List<Map> eqpList = new ArrayList<Map>();
			try{
				Map<String,String> param = new HashMap<String, String>();
				param.put("jndi", jndi);

				//在OSS数据库中生成不含分光器且小于24芯的光分纤箱
				SwitchDataSourceUtil.setCurrentDataSource(JNDI);
				syncOssDataTaskDao.syncEqpNoOBDLess24(param);
				SwitchDataSourceUtil.clearDataSource();
				
				//查出上述分纤箱，存入List集合
				SwitchDataSourceUtil.setCurrentDataSource(JNDI);
				eqpList = syncOssDataTaskDao.queryEqpNoOBDLess24();
				SwitchDataSourceUtil.clearDataSource();
				
			}catch(Exception e){
				e.printStackTrace();
				logger.info("----------------同步失败-----【"+jndi+"】，第"+count+"个地市设备信息生成失败---------------");
			}finally{
				SwitchDataSourceUtil.clearDataSource();
			}
			
			//遍历集合，将数据插入光网助手数据库
			if(null != eqpList && eqpList.size()>0){
				for(Map eqp : eqpList){
					//判断INS数据库中是否已插入该分纤箱，如果没有，插入tb_eqp_no_obd_less_24表
					List<Map> eqpMap = syncOssDataTaskDao.queryEqpNoOBDLess24ByNo(eqp.get("PHY_EQP_NO").toString());
					if(eqpMap.size()<1){
					syncOssDataTaskDao.insertEqpNoOBDLess24(eqp);
					}
				}
				logger.info("----------------同步完成-----【"+jndi+"】，第"+count+"个地市设备信息同步至INS数据库完成---------------"+new Date());
			}
			//将设备表中存在的上述分纤箱剔除
			syncOssDataTaskDao.deleteEqpNoOBDLess24();
			//将动态端子表中上述设备的端子删除
			syncOssDataTaskDao.deleteDTSJEqpNoOBDLess24();
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
