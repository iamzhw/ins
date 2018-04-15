/**
 * IOSSInterfaceForPHONE.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.webservice.oss;

public interface IOSSInterfaceForPHONE extends java.rmi.Remote {
    public java.lang.String qryPrejump(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String initalWaitGUIInfo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String genFakeAddr(java.lang.String arg0) throws java.rmi.RemoteException, com.webservice.oss.JSONException;
    public java.lang.String checkAddress(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryOptRoadInfoByNo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryPsListByWoId(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryNextEqpInfo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryEqpByAccessNum(java.lang.String arg0) throws java.rmi.RemoteException, com.webservice.oss.Exception;
    public java.lang.String qryDeviceFreePortByAccessNo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryWorkOrderDetailInfo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryOltInfo(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String qryAddressByAccNum(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryTache(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String changeRouteOnFttb(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryTacheByDevice(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryOptLinkRoute(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryLinkRouteByAccessNo(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String qryOptRoadByAccessNum(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String getAddrCoverEqpInfo(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String positiveOBD(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryDeviceRouterInfo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryOutsideRouterInfo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String getOssStaffInfo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String changeUDEqp(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qrySameAddr(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryOptRoadExistByNo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String getDeviceType(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String deviceFreePort(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryEqpPointInfo(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String qryResInfo(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String qryBuildTypeByAccessNo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String delEqpAddr(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryEqpListByAccessNo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String spliteAddressWithStaff(java.lang.String arg0) throws java.rmi.RemoteException, com.webservice.oss.JSONException;
    public java.lang.String turnPanelToPort(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryFreePortByEqpAndAccessPortSpec(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String changeTransPort(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String reDispatch(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryFxhDeviceInfo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String createBalkOrder(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String resCheck(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryFOptRoute(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryWorkOrderInfoList(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryOperationInfoByDevice(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String chgUD(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryFreePortByLink(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String changeBurDir(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String modifyLink(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String qryCanChgUD(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String workOrderAutomatic(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String turnPortToPanel(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryMaxRateByAccessNbr(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String qryRoInfoByWoId(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qrySumbitRouterInfo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryOptRoadByNo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String addOBDAddr(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String addressOperate(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String getAccessMode(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String qryPsInfoByAddressId(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String tryAndAssignOptRoad(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String getAddrEqpList(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String addOrderNotes(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryAbilityEqpByEqp(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String getOptRouteByDeviceCode(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryEqpInfoByEqpNo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryCoverAddrByEqp(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryTacheInfo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryAddressByPhyEqpId(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryPSOInfoByWoId(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryCheckRouterInfo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryEqpInfoByCable(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String createDeviceByWoId(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String waitInstallAutomatic(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String getResOrderInfoByEqpId(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String checkWOisOnOpr(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String initalRollBackInfo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String boundWireHarness(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String judgeFreePoint(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String judgeOptRoadAutoAssign(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String checkOssLoginAccount(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String qryEqpByNameOrNo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String submitForFTTB(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String addEqpToAddrWithWoAuto(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryCableInfo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String getSN(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String workRollBack(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryLinkInfoByPsId(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String canCreateEqpTypeByAccessNum(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String getEqpInfoByNo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryUserEqpInfo(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String qryNextAddr(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String getPSInfo(java.lang.String arg0) throws java.rmi.RemoteException, com.webservice.oss.Exception;
    public java.lang.String updateUserAddr(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryEqpInfo(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String qryCoverDevicesByAccessNo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryWorkOrderById(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String configLink(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryDeviceExist(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String addEqpToAddr(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryEqpInfoByEqp(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String formateRecallOrder(java.lang.String reqXML) throws java.rmi.RemoteException;
    public java.lang.String qryLinkInfoByWoId(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryEqpInfoByEqpNoorName(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String getAddrAndEqpByAccount(java.lang.String arg0) throws java.rmi.RemoteException, com.webservice.oss.Exception;
    public java.lang.String qryAddrList(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String canCreateEqpType(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryEqpByAddr(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryPsInfoByWoId(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String checkZtByDevice(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String qryLinkRoute(java.lang.String reqXML) throws java.rmi.RemoteException;
}
