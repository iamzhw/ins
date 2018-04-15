package com.system.tool;

/**
 * 服务配置
 * 
 * @author 陈时航
 * @version 2012-8-6 下午02:14:08
 * @modify 2013-4-25
 */
public class GlobalValue {

    /* 发布配置 */
    // public static boolean showDebugLog = false; //是否输出调试日志，如sysout
    public static boolean isFormal = true; // 是否使用外系统正式服务接口

    /* 文件服务器地址，提供巡检、抽检的文件上传。下载地址在web.xml里配置 */
    public static final String FILE_SERVER = "http://132.228.237.107:18081/photoserver/uploadFile.ws?wsdl";
    //public static final String FILE_SERVER = "http://localhost:8080/photoserver/uploadFile.ws?wsdl";


    /* PAD系统中文件服务器地址，提供巡检、抽检的文件上传。下载地址在web.xml里配置 */
    public static final String PAD_FILE_SERVER = "http://132.232.5.57:8090/photoserver/uploadFile.ws?wsdl";

    public static final String FILE_SERVER_INTERNET = "http://61.160.128.47:18081/";

    // 中博推送webservice
//    public static final String ZBTS = "http://202.102.116.111:8081/ts/pushService.ws?wsdl";
     public static final String ZBTS = "http://132.228.176.114:8081/ts/pushService.ws";
     
	/**
	 * 调用综调短信发送接口
	 */
	public static String SEND_MESSAGE_URL = "http://132.228.169.145:8001/ida/axis/services/AsigAxisService";


    // public static final String FILE_SERVER =
    // "http://132.232.5.57:8090/photoserver/uploadFile.ws?wsdl";
    // public static final String FILE_SERVER =
    // "http://localhost:8080/photoServer/uploadFile.ws?wsdl";

    // public static final String CABLE_FILE_SERVER =
    // "http://132.228.125.46:8082/photoserver/uploadFile.ws?wsdl";
    //
    // public static final String CABLE_FILE_SERVER_INTERNET =
    // "http://61.160.128.22:8082/photoserver/uploadFile.ws?wsdl";
    // /*版本信息*/
    // public static final String VERSION_NO = "9";
    // public static final String VERSION_NAME = "V1.0.20130315";
    // public static final String VERSION_PUB_DATE = "2013-03-15";
    //
    // /*记录日志*/
    //
    // public static final int LOG_MODULE_DESIGN_ONLINE = 1;
    //
    // public static final int LOG_MODULE_INSPECTION = 2;
    //
    // public static final int LOG_LEVEL_INFO = 1;
    //
    // public static final int LOG_LEVEL_WARM = 2;
    //
    // public static final int LOG_LEVEL_EXCEPTION = 3;
    //
    // /**
    // * 巡检岗服务地址
    // */
    // public static final String INSPECTION_SERVER =
    // "http://132.228.213.25:8081/irec/";
    //
    // /**
    // * 巡检岗登陆接口
    // */
    // public static final String INPEC_LOGIN = INSPECTION_SERVER
    // + "check-staff!checkStaff?";
}
