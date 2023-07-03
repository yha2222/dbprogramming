package controller;

import java.util.HashMap;
import java.util.Map;

import service.LoginService;

public class Controller {
	//로그인할 때 아이디 비밀번호 저장될 곳
	public static Map<String, Object>sessionStorage = new HashMap<String, Object>();
	public static void main(String[] args) {
		new Controller().init();
		//new Controller()가 현재 시점으로 바꿔서 static 아니어도 얼마든지 씀
	}
	
	public void init() {
		//객체 생성시 new 연산자 안썼고 getInstance 씀 => 싱글톤
		LoginService loginService = LoginService.getInstance();
		loginService.login();
	}

}
