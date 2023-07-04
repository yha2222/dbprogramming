package service;

import java.util.Map;
import java.util.Scanner;

//import dao.LoginDAO;
import dao.MemberDAO;

public class MemberService {
	//�̱���
	//�ű� �Է�(singUp)
	private static MemberService instance = null;
	private MemberService() {}
	public static MemberService getInstance() {
		if(instance == null) instance = new MemberService();
		return instance;
	}
	
	private static Scanner sc = new Scanner(System.in);
	MemberDAO dao = MemberDAO.getInstance();
	
	public void signUp() {
		int result = 0;
		System.out.println("[[�ű� ȸ�� ���]]");
		
		System.out.print("ȸ���� : ");
		String name = sc.nextLine();
	
		System.out.print("��ȭ��ȣ : ");
		String telNum = sc.nextLine();
		
		System.out.print("���ϸ��� : ");
		int mile = Integer.parseInt(sc.nextLine());
		
		System.out.print("��ȣ : ");
		String pw = sc.nextLine();
		
		System.out.println("�� �ڷḦ �����ϰڽ��ϱ�? (y/n)");
		String flag = sc.nextLine();
		if(flag.equalsIgnoreCase("y")) {
			result = dao.signUp(name, telNum, mile, pw);
		} 
		
		if(result != 0) {
			System.out.println(name + "ȸ���� �ڷ� �Է��� ���� ó����");
		}else {
			System.out.println(name + "ȸ���� �ڷ� �Է��� ��ҵ�");
		}
	}
	
	//��������(Update)
	public void update() {
		int result = 0;
		String mid = "";
		String name = "";
		String telNum = "";
		int mile = 0;
		String passwd = "";
		String flag = "";
		String str = "";
		
		System.out.println("[[ȸ�� ���� ����]]");
		
		while(true) {
			System.out.print("ȸ����ȣ: ");
			mid = sc.nextLine();
			LoginService loginService = LoginService.getInstance();
			Map<String, Object> map = loginService.isDuplicate(mid);  //�ߺ� Ȯ��
			//������ �ڷ� ������ null�� ����
			if(map == null) {
				System.out.println(mid + "ȸ�������� ���� ������ �� �����ϴ�.");
			}else {
				break;
			}
		}
		System.out.print("�̸��� �����ϰڽ��ϱ�? (y/n) :");
		flag = sc.next();
		if(flag.equalsIgnoreCase("y")) {
			System.out.print("�� �� : ");
			name = sc.next();
			str = str + " MNAME = '" + name + "' ,"; //update set��
		}
		
		System.out.print("��ȭ��ȣ�� �����ϰڽ��ϱ�? (y/n) :");
		flag = sc.next();
		if(flag.equalsIgnoreCase("y")) {
			System.out.print("��ȭ��ȣ : ");
			telNum = sc.next();
			str = str + " TEL_NUM = '" + telNum + "' ,";
		}
		
		System.out.print("���ϸ����� �����ϰڽ��ϱ�? (y/n) :");
		flag = sc.next();
		if(flag.equalsIgnoreCase("y")) {
			System.out.print("���ϸ��� : ");
			mile = sc.nextInt();
			str = str + " MILEAGE = '" + mile + "' ,";
		}
		
		System.out.print("��й�ȣ�� �����ϰڽ��ϱ�? (y/n) :");
		flag = sc.next();
		if(flag.equalsIgnoreCase("y")) {
			System.out.print("��й�ȣ : ");
			passwd = sc.next();
			str = str + " PASSWD = '" + passwd + "' ,";  //��� �� ������ ���� �𸣴ϱ� �ϴ� �� , ����
		}
		
		int len = str.length();
		str = str.substring(0,len-2); //�ϴ� �ٿ��� �� ����� 
		
		result = dao.update(str, mid);  //str, mid ������ dao.update() �θ�
		//��ȯ�� �� ���⼭ �ٽ� ���� 
		//0 => ó�� �� �� ��
		if(result != 0) {
			System.out.println("ȸ������ ������ ���� ó����");
		}else {
			System.out.println("ȸ������ ������ ��ҵ�");
		}
	}
		
		//ȸ���������˻�(searchOne)
		//ȸ�������ȸ(searchAll)
		//ȸ������(delete)
}
