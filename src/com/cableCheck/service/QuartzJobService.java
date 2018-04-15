package com.cableCheck.service;

public interface QuartzJobService {

	void syncOSSChangePorts();

	void synchronizeEquipmentInfo();
	
	void synchronizeEquipment();

	void syncOSSChangePortsSZ();

	void synchronizeElectronArchives();
	
	void synchronizeEqpContract();
	
	void synchronizeEqpWljb();

	void synchronizeOSSGXL();

	void synchronizeNoRes();

	void syncQJST();

	void syncODSO();

	void syncOSSChangePortsNJ();

	void syncOSSChangePortsZJ();

	void syncOSSChangePortsWX();

	void syncOSSChangePortsNT();

	void syncOSSChangePortsYZ();

	void syncOSSChangePortsYC();

	void syncOSSChangePortsXZ();

	void syncOSSChangePortsHA();

	void syncOSSChangePortsLYG();

	void syncOSSChangePortsSQ();

	void syncOSSChangePortsTZ();

	void syncOSSChangePortsCZ();

	void syncOSSOBD();

	void syncMONTH();
	
	void syncResRat();
	
	void syncCvs();
	void syncIom();

	void reviewTaskToStaff();
	
	void calOrderNum();
	void calOrderChangeNum();
	
	void calCheckError();
	void calTeamOrder();
	void calTeamCheck();
	void calGridOrder();

	void queryAreaPortNum();
	
	void queryFtpDir();
}
