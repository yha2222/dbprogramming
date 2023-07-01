package dbprogramming;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBExample {
	public static Scanner sc = new Scanner(System.in);
	public static Connection conn = null;
	public static void main(String[] args) {
		DBExample db = new DBExample();
		db.connect();
		db.select();
//		db.insert();
//		db.update();
//		db.delete();

	}

	public void connect() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "pc22";
		String passwd = "java";
		
		try {
			//connection ��ü - ���Ḹ ���� ����
			conn = DriverManager.getConnection(url, user, passwd);
		}catch(Exception e) {
			System.out.println("DB ���� ����!");
			e.printStackTrace();
		}
		System.out.println("DB ���� ����!");
	}
	
	public void select() {
		//�������� - ������ ��
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("��ȸ�� ȸ����ȣ �Է� : ");
			String tid = sc.nextLine(); //��ȸ�� ȸ��
			
			String sql = "select * from tb1_member where mem_id = ?";
			pstmt = conn.prepareStatement(sql);//��ɾ� ��ü ���� -�̿ϼ�
			pstmt.setString(1, tid);//������-�Ķ���� ���� -�ϼ�
			
			rs = pstmt.executeQuery();	 // ?-select�� ����
			//��ɾ� ���� -select��(�˻�) ���� ���� �����ϴ� �޼ҵ�, ()�ȿ� ����� ���� ��=>sql
			//executeUpdate - update insert delete (dml) 
			
			while(rs.next()) { //������ ������ ������ �� ������ ����
				//sql�� String���θ� 
				String mid = rs.getString("mem_id"); //"����Ŭ�� ���� �÷���"/�÷� �ε���(1)->�ش� �÷� �� ������ �˱� ���� �÷��� ���� �� ���� ����"
				String name = rs.getString("mem_name");
				String mhp = rs.getString("mem_hp");
				//String madd = rs.getString("mem_add1");
				int mileage = rs.getInt("mem_mileage");
				
				System.out.print(mid + "  "+name+"  "+mhp);
				System.out.printf("%6s\n", mileage);
				System.out.println("---------------------------------");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {  //��� ���� �� �Ÿ� => �� �������� �� �ٸ� �ֵ�� ���� �� �Ÿ� �� ���� ����
			e.printStackTrace();
		}finally {
			if(rs != null) try {rs.close();}catch(Exception e) {} //���� �߻� �� ó�� ���� ����
			if(pstmt != null) try {pstmt.close();}catch(Exception e) {}
			if(conn != null) try {conn.close();}catch(Exception e) {}
		}
	}
	
	public void insert() {
		connect();
		//��ȣ ����
		
		System.out.print("�̸�: ");
		String tnm = sc.next();
		
		System.out.print("�̸�: ");
		String tnm1 = sc.next();
	}
}
