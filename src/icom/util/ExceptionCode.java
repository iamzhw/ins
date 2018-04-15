package icom.util;

public class ExceptionCode {
	public static final String SUCCESS 						= "000";  // 成功
	public static final String SERVER_ERROR 				= "1001"; // 服务器内部错误
	public static final String CLIENT_PARAM_ERROR 			= "1002"; // 客户端参数错误,或客户端参数错误或不完整
	public static final String USERNAME_PASSWORD_ERROR		= "1003"; // 用户不存在或者密码错误
	public static final String SESSION_ERROR 				= "1004"; // 会话不存在 session错误
	public static final String NETWORK_CONNECTION_ERROR 	= "1005"; // 网络连接失败
	public static final String NULL_DATA_ERROR 	= "1006"; //空数据
	public static final String ESB_ERROR 	= "1007"; //ESB错误
	public static final String OSS_ERROR 	= "1008"; //OSS资源错误
	public static final String PAD_ERROR 	= "1009"; //PAD错误
	public static final String JSON_ERROR 	= "1010"; //JSON错误
	public static final String CHANGE_PWD_ERROR 	= "1011"; //密码修改失败
	public static final String BD_FAILE_ERROR = "1012";//绑定失败
	
	
	public static final String SESSION_FORBIDDEN 			= "2008"; // 用户SESSION非法，需重新登录
	public static final String LOGIN_TIMEOUT 				= "2009"; // 用户登录超时，需重新登录
	public static final String SAVE_PHOTO_FAIL              = "2030"; // 照片保存失败
	
	public static final String SIGN_FAIL                  = "3001";//签到失败
	public static final String SAVE_TROUBLE_FAIL          = "3002";//上报问题失败
	public static final String CHECK_FAIL                  = "3003";//回单失败
	
	//光网助手错误返回码  17开头
	public static final String GWZS_RECEIPT_FAIL                  = "1701";//工单已回单
	
	
	//缆线巡检错误返回码  1020开始
	public static final String LXXJ_EQP_NOT_EXIST                  = "1020";//此设备编码不存在，请在OSS中添加
	
	

	
}
