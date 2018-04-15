package com.system.action;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;

public class TelnetUtil {

	private TelnetClient telnet = new TelnetClient();
    private InputStream in;
    private PrintStream out;
    private static final String DEFAULT_AIX_PROMPT = "#";
    
    private String port;
    private String user;
    private String password;   
    private String ip;
    private static final int DEFAULT_TELNET_PORT = 22;//默认端口号
    
    public TelnetUtil(String ip, String user, String password) {
        this.ip = ip;
        this.port = String.valueOf(TelnetUtil.DEFAULT_TELNET_PORT);
        this.user = user;
        this.password = password;
    }
    
    public TelnetUtil(String ip, String port, String user, String password) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
    }
    
    public static void main(String[] args) {
        try {
        	
            TelnetUtil telnet = new TelnetUtil("192.168.1.181", "admin", "admin");
            String nowDate = telnet.getNowDate();
            System.out.println(nowDate);
            telnet.disconnect();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String getNowDate() {
        this.connect();
        
        String nowDate = this.sendCommand("date|awk '{print $2,$3,$4}'");
        String[] temp = nowDate.split("\r\n");
        // 去除命令字符串
        if (temp.length > 1) {
            nowDate = temp[0];
        } else {
            nowDate = "";
        }
        this.disconnect();
        return nowDate;
    }

    private boolean connect() {
    	
        boolean isConnect = true;
        
        try {
            telnet.connect(ip, Integer.parseInt(port));
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
            
            readUntil("login: ");
            write(user);
  
            readUntil("Password: ");
            write(password);
  
            /** Advance to a prompt */
            readUntil(DEFAULT_AIX_PROMPT);
        } catch (Exception e) {
            isConnect = false;
            e.printStackTrace();
            return isConnect;
        }
        return isConnect;
    }
    
    public void su(String user, String password) {
        try {
            write("su" + " - " + user);
            readUntil("Password:");
            write(password);
            readUntil(DEFAULT_AIX_PROMPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //判断是否读取到最后
    public String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            char ch = (char) in.read();
            while (true) {
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(String value) {
        try {
            out.println(value);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendCommand(String command) {
        try {
            write(command);
            return readUntil(DEFAULT_AIX_PROMPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
  
    private void disconnect() {
        try {
            telnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
  
    
}
