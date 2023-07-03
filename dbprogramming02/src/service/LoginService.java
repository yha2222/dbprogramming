package service;

import java.util.Map;
import java.util.Scanner;

import dao.LoginDAO;

public class LoginService {
	private static LoginService instance = null;
	private LoginService() {}
	public static LoginService getInstance() {
		if(instance == null) instance = new LoginService(); //�ڱ��ڽ��� ��ü ����
		return instance;
	}
	
	Scanner sc = new Scanner(System.in);
	
	LoginDAO dao = LoginDAO.getInstance(); // �̱������� ����
	Map<String, Object> result = null;
	
	public void login() {
		System.out.println("[[�α���]]");
		//���̵� �Է�
		while(true) {
			System.out.print("���̵� : ");
			String id = sc.nextLine();
			
			System.out.print("password : ");
			String pw = sc.nextLine();
			
			result = dao.login(id, pw);
		}
	}
}
