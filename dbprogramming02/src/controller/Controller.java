package controller;

import java.util.HashMap;
import java.util.Map;

import service.LoginService;

public class Controller {
	//�α����� �� ���̵� ��й�ȣ ����� ��
	public static Map<String, Object>sessionStorage = new HashMap<String, Object>();
	public static void main(String[] args) {
		new Controller().init();
		//new Controller()�� ���� �������� �ٲ㼭 static �ƴϾ �󸶵��� ��
	}
	
	public void init() {
		//��ü ������ new ������ �Ƚ�� getInstance �� => �̱���
		LoginService loginService = LoginService.getInstance();
		loginService.login();
	}

}
