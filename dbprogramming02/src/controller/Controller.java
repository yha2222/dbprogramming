package controller;

import java.util.HashMap;
import java.util.Map;

import service.LoginService;
import service.MemberService;

public class Controller {
	//�α����� �� ���̵� ��й�ȣ ����� ��
	public static Map<String, Object>sessionStorage = new HashMap<String, Object>();
	
	public static void main(String[] args) {
		new Controller().init();
		//new Controller()�� ���� �������� �ٲ㼭 static �ƴϾ �󸶵��� ��
	}
	
	public void init() {
		//��ü ������ new ������ �Ƚ�� getInstance �� => �̱��� ���
//		LoginService loginService = LoginService.getInstance();
//		loginService.login();
		//���� �ʿ� �ڷ� ������
		//=> dao.login(id, pw) => MapŸ���� ��ȯ��
		// => loginDao - login()ȣ��  => Query�ۼ� - �ϼ��� ���� �ƴ�! '?'������..
		// => selectOne(sql -> '?'�ִ� ������, param->�Է¹��� ������(������ų �ڷ�)[0]:id, [1]:pw) => ���� Map ��ȯ
		//    param.add(id/pw)�� ������ �޾Ƽ� �α��� ���� �� ������ �װͻӸ� �ƴ϶� ���� ���� ������ ���� ����
		//	=> JDBCUtil-selectOne() ȣ�� => ����(�ٸ� ���̺� ��ȸ ����-�ܺ� �Է� ������ ���ؼ� ��ȸ�Ǵ� ��) �ۼ��Ǿ� ��
		//	=> �� ��� �� �������� ���÷α��� �ƴ϶� => selectAll �ƴϰ� selectOne
		//	 => service�� ������ �Է¹��� ��ȯ���� ����
		
		MemberService memberService = MemberService.getInstance();
//		memberService.signUp();
		
		memberService.update();
	}

}
