package com.cn.teacher;

import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport{
	public String login() throws Exception{
		
		return "login2";
	}
	
	
	public String execute() throws Exception {
		String uname;
    	String pwd;
    	uname = username;
    	pwd = password;
    	//һ�������û����Ե�½���ԣ�0001:0001    0002:0002    0003:0003��
    	System.out.println("uname:" + uname + ",pwd:" + pwd);
    	if("0001".equals(uname)||"0002".equals(uname)||"0003".equals(uname)){
    		if(uname.equals(pwd)){
    			return SUCCESS;
    		}
    		else{
    			setMessage("�û��������벻ƥ�䣡");
    			return "loginerron";
    		}
    	}else{
    		setMessage("��û��Ȩ�޵�½��ϵͳ��");
    		return "loginerron";
    	}
	}
	private String message;
	private String username;
	private String password;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
