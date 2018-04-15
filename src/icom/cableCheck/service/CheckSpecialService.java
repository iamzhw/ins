package icom.cableCheck.service;

public interface CheckSpecialService {

	String getSpecialEqp(String jsonStr);

	String getPort(String jsonStr);

	String getDelLink(String jsonStr);

	String getDelEqp(String jsonStr);

	String getKxOrder(String jsonStr);

	String getSonarea(String jsonStr);

	String getKxOrderDetail(String jsonStr);

	String submitKxContent(String jsonStr);

	String getKxOrderEqpInfo(String jsonStr);



}
