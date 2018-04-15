package com.system.tool;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.telnet.TelnetClient;

import com.system.constant.Constants;
import com.system.tool.StringUtil;

/**
 * Telnet工具类
 * @author Administrator
 *
 */
public class TelnetOperator {

	private String prompt = "#"; // 结束标识字符串,Windows中是>,Linux中是#
	private String loginTimeOut = "Login timed out.";

	private TelnetClient telnet;
	private InputStream in; // 输入流,接收返回信息
	private PrintStream out; // 向服务器写入 命令

	/**
	 * @param termtype
	 *            协议类型：VT100、VT52、VT220、VTNT、ANSI
	 * @param prompt
	 *            结果结束标识
	 */
	public TelnetOperator(String termtype, String prompt) {
		telnet = new TelnetClient(termtype);
		setPrompt(prompt);
	}

	public TelnetOperator(String termtype) {
		telnet = new TelnetClient(termtype);
	}

	public TelnetOperator() {
		telnet = new TelnetClient();
	}

	/**
	 * 登录到目标主机
	 * 
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 */
	public void login(String ip, int port, String username, String password) {
		try {
			telnet.connect(ip, port);
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());

			// 如果有登录口令，判断登录口令是否正确
			if (!StringUtil.isNullOrEmpty(username)) {
				System.out.println(readUntil(null, null, "Login:", 1, 0));
				write(username);
				System.out.println(readUntil(null, null, "Password:", 1, 0));
				write(password);
				String rs = readUntil(null, null, null, 1, 0);
				if (rs != null && rs.contains("Login Failed")) {
					throw new RuntimeException("登录失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 发送连接命令,返回执行结果
	 * 
	 * @param command
	 * @return
	 */
	public String sendCommand(String command) {
		return sendCommand(prompt, command, null, 1, 0);
	}

	/**
	 * 发送连接命令,返回执行结果
	 * 
	 * @param command
	 * @return
	 */
	public String sendCommand(String command, String appender) {
		return sendCommand(prompt, command, appender, 1, 0);
	}

	/**
	 * 发送连接命令,返回执行结果
	 * 
	 * @param command
	 * @return
	 */
	public String sendCommand(String pattern, String command, String appender) {
		return sendCommand(pattern, command, appender, 1, 0);
	}

	/**
	 * 发送连接命令,返回执行结果
	 * 
	 * @param command
	 * @return
	 */
	public String sendCommand(String command, String appender, int time,
			int preTime) {
		return sendCommand(prompt, command, appender, time, preTime);
	}

	/**
	 * 发送命令,返回执行结果
	 * 
	 * @param command
	 * @return
	 */
	public String sendCommand(String pattern, String command, String appender, int time, int preTime) {
		try {
			System.out.println("开始发送命令==》》" + StringUtil.isNullOrEmpty(appender));
			if (StringUtil.isNullOrEmpty(appender)) {
				write(command);
			} else {
				write(appender);
			}
			// 如果command是空格，需要判断两个#
			// if (" ".equals(command)) {
			// return readUntil(pattern, 2, 0);
			// } else {
			// return readUntil(pattern,time,preTime);
			// }
			return readUntil(command, appender, pattern, time, preTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取连接分析结果
	 * 
	 * @param pattern
	 *            结束符
	 * @param time
	 *            结束符要求出现次数
	 * @param preTime
	 *            结束符已经出现次数
	 * @return
	 * @throws Exception
	 */
	public String readUntil(String command, String appender, String pattern, int time, int preTime) throws Exception {
		StringBuffer sb = new StringBuffer();
		try {
			if (StringUtil.isNullOrEmpty(pattern)) {
				pattern = this.prompt;
			}
			char ch;
			int code = -1;
			int cnt = preTime;
			
			// 登出标识符
			Pattern r = Pattern.compile(Constants.LOG_OUT_REGEX);
			
			while ((code = in.read()) != -1) {
				// 去除ASCII码中的控制字符
				// 保留回车和换行
				// 回车是13，换行是10，先敲13，再敲10
				if ((code >= 1 && code <= 31 && code != 13 && code != 10)
						|| code == 127) {
					continue;
				}
				ch = (char) code;
				sb.append(ch);

				// 匹配到结束标识时返回结果
				if (sb.toString().endsWith(pattern)) {
					if (!StringUtil.isNullOrEmpty(command)) {
						if(command.startsWith("display current-configuration section epon-")) {
							if (sb.toString().contains("return")) {
								return new String(sb.toString().trim().getBytes(
										"ISO8859-1"), "UTF-8")
										.replaceFirst(Constants.MORE_STRING_REGEX1, "")
										.replace(
												"[37D                                     [37D",
												"");
							}
						} else if (command.startsWith("service-port vlan")
								|| command.startsWith("igmp cascade-port")
								|| command.startsWith("ont-lineprofile epon profile-name")) {
							if (sb.toString().contains("Command")) {
								return new String(sb.toString().trim().getBytes(
										"ISO8859-1"), "UTF-8")
										.replaceFirst(Constants.MORE_STRING_REGEX1, "")
										.replace(
												"[37D                                     [37D",
												"");
							}
						} else if (command.startsWith("btv")) {
							if (sb.toString().endsWith("-btv)#")) {
								return new String(sb.toString().trim().getBytes(
										"ISO8859-1"), "UTF-8")
										.replaceFirst(Constants.MORE_STRING_REGEX1, "")
										.replace(
												"[37D                                     [37D",
												"");
							}
						} else if (command.startsWith("config")) {
							if (sb.toString().endsWith("(config)#")) {
								return new String(sb.toString().trim().getBytes(
										"ISO8859-1"), "UTF-8")
										.replaceFirst(Constants.MORE_STRING_REGEX1, "")
										.replace(
												"[37D                                     [37D",
												"");
							}
						} else if (command.startsWith("interface epon-olt_") || command.startsWith("interface epon-onu_")) {
							if (sb.toString().endsWith("(config-if)#")) {
								return new String(sb.toString().trim().getBytes(
										"ISO8859-1"), "UTF-8")
										.replaceFirst(Constants.MORE_STRING_REGEX1, "")
										.replace(
												"[37D                                     [37D",
												"");
							}
						} else if (command.startsWith("quit") && sb.toString().endsWith("(config-btv)#")) {
							continue;
						} else {
							cnt++;
							if (cnt == time) {
								return new String(sb.toString().trim().getBytes(
										"ISO8859-1"), "UTF-8")
										.replaceFirst(Constants.MORE_STRING_REGEX1, "")
										.replace(
												"[37D                                     [37D",
												"");
							}
						}
					} else {
						cnt++;
						if (cnt == time) {
							return new String(sb.toString().trim().getBytes(
									"ISO8859-1"), "UTF-8")
									.replaceFirst(Constants.MORE_STRING_REGEX1, "")
									.replace(
											"[37D                                     [37D",
											"");
						}
					}

				}
				// 匹配到冒号结束符的时候返回
				if (sb.toString().endsWith(Constants.PROMPT_COLON1) || sb.toString().endsWith(Constants.PROMPT_COLON2)) {
					return new String(sb.toString().trim()
							.getBytes("ISO8859-1"), "UTF-8");
				}
				// 如果是空格请求，并且不是登录请求，需要碰到第二个More才返回，并且返回值需要去掉第一个More
				if (!StringUtil.isNullOrEmpty(command)){
					if(command.startsWith("display current-configuration section epon-") && " ".equals(appender)) {
						if (sb.toString().replace(Constants.moreString1, "#").length()
								- sb.toString().replace(Constants.moreString1, "").length() >= 2) {
							return new String(sb.toString().trim().getBytes(
									"ISO8859-1"), "UTF-8").replaceFirst(
									Constants.moreString1, "");
						}
						if (sb.toString().replace(Constants.moreString2, "#").length()
								- sb.toString().replace(Constants.moreString2, "").length() >= 2) {
							return new String(sb.toString().trim().getBytes(
									"ISO8859-1"), "UTF-8")
									.replaceFirst(Constants.MORE_STRING_REGEX1, "")
									.replace(
											"[37D                                     [37D",
											"");
						}
					} else {// 否则碰到More返回
						if (sb.toString().endsWith(Constants.moreString1)
								|| sb.toString().endsWith(Constants.moreString2)) {
							return new String(sb.toString().trim().getBytes(
									"ISO8859-1"), "UTF-8");
						}
					}
				}
				// 匹配到确认结束符的时候返回
				if (sb.toString().endsWith(Constants.confirmString1) || sb.toString().endsWith(Constants.confirmString2)) {
					return new String(sb.toString().trim()
							.getBytes("ISO8859-1"), "UTF-8");
				}
				// 匹配到登陆超时结束符时返回
				if (sb.toString().endsWith(loginTimeOut)) {
					return new String(sb.toString().trim()
							.getBytes("ISO8859-1"), "UTF-8");
				}
				// 登录失败时返回
				if (sb.toString().contains("Login Failed")) {
					return new String(sb.toString().trim()
							.getBytes("ISO8859-1"), "UTF-8");
				}
				//匹配到登出标识符时返回
				Matcher m = r.matcher(sb.toString());
				if (m.matches()) {
					return new String(sb.toString().trim()
							.getBytes("ISO8859-1"), "UTF-8");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(sb.toString().trim().getBytes("ISO8859-1"), "UTF-8");
	}

	/**
	 * 发送命令
	 * 
	 * @param value
	 */
	public void write(String value) {
		try {
			out.println(value);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭连接
	 */
	public void distinct() {
		try {
			if (telnet != null && !telnet.isConnected())
				telnet.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPrompt(String prompt) {
		if (prompt != null) {
			this.prompt = prompt;
		}
	}

	public static void main(String[] args) {
		TelnetOperator telnet = new TelnetOperator("VT100", ">"); // Windows,用VT220,否则会乱码
		// 连接跳板机 端口号必须为23 否则不能连接
		telnet.login("120.78.144.82", 23, "root", "991359");
		// 执行语句操作
		String rs1 = telnet.sendCommand("ipconfig");
		try {
			rs1 = new String(rs1.getBytes("ISO-8859-1"),"GBK");
			System.out.println(rs1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		/*
		 * for(int i=0;i<size;i++){ if(i==0){ rs1 = telnet.sendCommand("ls");
		 * try { rs1 = new String(rs1.getBytes("ISO-8859-1"),"GBK"); //转一下编码
		 * 
		 * } catch (UnsupportedEncodingException e) { e.printStackTrace(); }
		 * }else{ if(rs1 != null || !rs1.equals("")){ rs1 =
		 * telnet.sendCommand("ls");
		 * 
		 * try { rs1 = new String(rs1.getBytes("ISO-8859-1"),"GBK"); //转一下编码 }
		 * catch (UnsupportedEncodingException e) { e.printStackTrace(); }
		 * }else{ break; } } }
		 */

		// ！！！！！！！！！！！！！！！！！！！
		// 切记 ！！ 每次执行一条命令一定要获取上一条命令的返回值后才能执行新的命令行 否则指令报错！！
		// ！！！！！！！！！！！！！！！！！！！
		// 关闭连接
		telnet.distinct();
	}

}
