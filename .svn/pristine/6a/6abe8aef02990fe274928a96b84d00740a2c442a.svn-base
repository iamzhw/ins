package icom.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

/**
 * Jaxb工具类,xml与JavaBean相互转换
 */
public class JaxbUtil {

	/**
	 * 日志服务
	 */
	private static final Logger LOGGER = Logger.getLogger(JaxbUtil.class);

	/**
	 * 将JavaBean对象转换为xml格式报文
	 * 
	 * @param object
	 *            JavaBean对象
	 * @param encoding
	 *            编码格式
	 * @return
	 */
	public static String convertToXml(Object object, String encoding) {

		String xmlResult = null;

		try {
			// 获取一个关于Customer类的 JAXB 对象
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			// 由 Jaxbcontext 得到一个Marshaller
			Marshaller marshaller = context.createMarshaller();
			// 设置为格式化输出，XML自动格式化。
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// 设置编码格式
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

			StringWriter writer = new StringWriter();

			marshaller.marshal(object, writer);

			xmlResult = writer.toString();

		} catch (JAXBException e) {

			LOGGER.error("JaxbUtil.convertToXml() got exception. ", e);
		}

		return xmlResult;
	}

	/**
	 * 将xml格式报文转换为JavaBean对象
	 * 
	 * @param xml
	 *            xml报文
	 * @param clazz
	 *            JavaBean对象实体类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertToObject(String xml, Class<T> clazz) {

		T t = null;

		try {

			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));

		} catch (JAXBException e) {

			LOGGER.error("JaxbUtil.convertToObject() got exception. ", e);
		}

		return t;
	}
}
