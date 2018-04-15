package icom.axx.action;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import icom.axx.dao.AopAspectDao;

/**
 * 面向切面类
 * 用于保存干线巡检手机端调用接口的信息。
 * @author wangxiangyu
 *
 */
@Aspect
public class AopAspect {

	@Autowired
	AopAspectDao aopAspectDao;
	
//	public static final String EDP = "execution(* com.activemq.service.impl.ConsumerServiceImpl..*(..))";
//	public static final String EDP = "execution(* com.activemq.action.ConsumerController..*(..))";
//	public static final String EDP = "execution(* icom.axx.service.impl.*..*(..))";
	public static final String EDP = "execution(* icom.axx.action.AxxInterfaceController..*(..))";
	
	/**
	 * 前置通知：目标分方法调用之前执行
	 */
	@Before(EDP)
	public void doBefore(JoinPoint jp){
		
		
    }  
	/**
	 * 最终通知：
	 * 目标方法调用之后执行（无论目标方法是否出现异常均执行）
	 */
	@After(EDP)
    public void doAfter(JoinPoint jp){
		//获取类名
		String className = jp.getTarget().getClass().getName();
		//调用的方法名
		String methodName = jp.getSignature().getName();
		//调用方法的参数
		Object[] argArr = jp.getArgs();
		String arg = "";
		if(null != argArr && argArr.length>0) {
			for(Object argObj : argArr) {
				arg += argObj.toString();
			}
		}
		//若传递参数大于300字符，只截取前300个字符。
		if(arg.length()>300) {
			arg = arg.substring(0, 300);
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("className", className);
		param.put("methodName", methodName);
		param.put("param_list", arg);
		//插入tb_base_mobile_gxxj_record
		aopAspectDao.saveInvokeInfo(param);
    }

}
