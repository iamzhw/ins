package icom.cableCheck.service;

public interface CheckOrderService {

	
	public String getOrder(String jsonStr);

	public String cancelBill(String jsonStr);

	public String forwardBill(String jsonStr);

	public String partCommitGd(String jsonStr);
	
	public String query(String jsonStr);
	
	public String getLongLati(String jsonStr);
	
	public String checkAgainSubmit(String jsonStr);
	
	public String saveJtGd(String jsonStr);

	public String getUserOreder(String jsonStr);

	public String commitCheckTask(String jsonStr);
	/**
	 * 整改工单回单接口
	 * @param jsonStr
	 * @return
	 */
	public String receiptReformTask(String jsonStr);

	/**
	 *检查工单提交接口
	 * @param jsonStr
	 * @return
	 */
	public String submitCheckOrder(String jsonStr);
	
	/**
	 *通过不预告检查工单提交接口
	 * @param jsonStr
	 * @return
	 */
	public String submitWorkOrder(String jsonStr);
	
	/**
	 *检查工单提交接口
	 * @param jsonStr
	 * @return
	 */
	public String submitWorkOrder2(String jsonStr);

}
