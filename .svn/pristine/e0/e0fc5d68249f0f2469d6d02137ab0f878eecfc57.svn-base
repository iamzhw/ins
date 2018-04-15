package com.system.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.service.MonitorService;
import com.util.StringUtil;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import util.dataSource.SwitchDataSourceUtil;
import util.page.BaseAction;


@Controller
@RequestMapping("/monitor")
@SuppressWarnings("all")
public class MonitorController extends BaseAction {
	
	private static List<String> commands = null;
	private static final String JNDI = "pn";//爱运维管理员权限数据源
	
	@Autowired
	MonitorService monitorService;
	
	@RequestMapping(value = "/index.do")
	public String index(HttpServletRequest request, Model model){
		
		String hostName = request.getParameter("hostName");
		
		List<Map<String, String>> serverConfigList = monitorService.getServerConfig(hostName);
		model.addAttribute("serverConfigList", serverConfigList);
		return "system/monitor/monitor";
	}
	
	/**
	 * 初始化
	 */
	private void initCommands() {

		commands = new ArrayList<String>();
		commands.add("top");
		commands.add("free");
		commands.add("df -hl");
		commands.add("ps -ef|grep tomcat");
		commands.add("ps aux|head -1;ps aux|grep -v PID|sort -rn -k +4|head");
	}
	
	/**
	 * http://localhost:8080/ins/monitor/getCreateShellSql.do
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findStatus.do")
	public void findStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取服务器配置信息
		String hostName = request.getParameter("hostName");
		List<Map<String, String>> serverConfigList = monitorService.getServerConfig(hostName);
		String userName = serverConfigList.get(0).get("USER_NAME").toString();
		String password = serverConfigList.get(0).get("PASSWORD").toString();
		//预定义结果集
		Map<String, Object> result = new HashMap<String, Object>();
		//预定义命令结果集
		List<Map<String, String>> commandResults = new ArrayList<Map<String, String>>();
		try {
			//连接服务器
			Connection conn = new Connection(hostName);
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(userName, password);
			if (isAuthenticated == false) {
				throw new IOException("Authentication failed.");
			}
			//初始化命令参数
			initCommands();
			for(String command : commands) {
				Session sess = conn.openSession();
				sess.execCommand(command);
				InputStream stdout = new StreamGobbler(sess.getStdout());
				BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
				StringBuffer details = new StringBuffer("");
				while (true){
					String line = br.readLine();
					if (line == null) {
						break;
					}
					details.append(line).append("<br/>");
					System.out.println(details);
				}
				//封装结果
				Map<String, String> commandResult = new HashMap<String, String>();
				commandResult.put("hostName", hostName);
				commandResult.put("command", command);
				commandResult.put("exitCode", null==sess.getExitStatus()?"无":sess.getExitStatus().toString());
				commandResult.put("details", (null==details)?"无返回结果":details.toString());
				commandResults.add(commandResult);
				//关闭流
				br.close();
				sess.close();
			}
			
			
			conn.close();
			result.put("rows", commandResults);
			
		}catch(IOException e) {
			e.printStackTrace(System.err);
		}
		write(response, result);
	}
	
	@RequestMapping("/executeCommand.do")
	public void executeCommand(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取服务器配置信息
		String hostName = request.getParameter("hostName");
		List<Map<String, String>> serverConfigList = monitorService.getServerConfig(hostName);
		String userName = serverConfigList.get(0).get("USER_NAME").toString();
		String password = serverConfigList.get(0).get("PASSWORD").toString();
		try {
			//连接服务器
			Connection conn = new Connection(hostName);
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(userName, password);
			if (isAuthenticated == false) {
				throw new IOException("Authentication failed.");
			}
			Session sess = conn.openSession();
			sess.execCommand("echo 1 > /proc/sys/vm/drop_caches");
			sess.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getDBStatus.do")
	public void getDBStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Map<String, String>> dbResult =  new ArrayList<Map<String,String>>();
		try {
			SwitchDataSourceUtil.setCurrentDataSource(JNDI);
			dbResult = monitorService.getDBStatus();
			SwitchDataSourceUtil.clearDataSource();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			SwitchDataSourceUtil.clearDataSource();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", dbResult);
		write(response, result);
	}
	
	@RequestMapping("/clearLogs.do")
	public void clearLogs(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			SwitchDataSourceUtil.setCurrentDataSource(JNDI);
			monitorService.clearLogs();
			SwitchDataSourceUtil.clearDataSource();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			SwitchDataSourceUtil.clearDataSource();
		}
	}
	
}
