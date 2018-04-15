package com.webservice.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import org.springframework.stereotype.Service;
/** 
 * @author wangxy 
 * @version 创建时间：2016年10月20日 上午11:25:21 
 * 类说明：电子健康档案库提供接口
 */
@Service
public interface ElectronArchivesService extends Remote {
	/**
	 * 通过光路编号查询FTTH装机照片详情
	 * @param jParam json字符串
	 * @return
	 * @throws RemoteException
	 */
    public String queryPhotoDetail(String jParam) throws RemoteException;
    /**
     * 根据设备no查询健康电子档案
     * @param jParam
     * @return
     * @throws RemoteException
     */
    public String queryEqpInfo(String jParam) throws RemoteException;
}
