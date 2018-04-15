package com.activemq.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.activemq.service.ConsumerService;
/**
 * JMS消息中间件
 * 消费者Controller
 * @author wangxiangyu
 *
 */
@SuppressWarnings("all")
@RequestMapping(value = "/activemq")
@Controller
public class ConsumerController {
	
	@Resource
	ConsumerService consumerService;
	
	@RequestMapping("/index.do")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "/activemq/index";
	}
	
	/**
	 * http://localhost:8080/ins/activemq/receive.do
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/receive.do")
	@ResponseBody
	public String receive(HttpServletRequest request, HttpServletResponse response) {
		
		String result = consumerService.receive();
		System.out.println(result);
		return result;
	}
	
	/**
	 * 测试接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/test.do")
	@ResponseBody
	public void test(HttpServletRequest request, HttpServletResponse response) {
		
		 consumerService.test("张三", "lisi");
	}
		
}
