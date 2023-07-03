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
//		try {
//			//db.connect();
//			//db.select();
//			int result = db.insert();
//			if(result > 0) {   //result �޾ƾߵǴϱ� try catch => record 0�� �̻��̸�-�ԷµǸ�
//				System.out.println("�ڷᰡ ���������� �Է�");
//			}else {
//				System.out.println("�ڷ��Է� ����");
//			}
			int rs = db.update();
			if(rs > 0) {
				System.out.println("ȸ������ ���� ����");
			}else {
				System.out.println("ȸ������ ���� ����");
			}
			//db.delete();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
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
	
	public int insert() throws Exception {
		connect();
		PreparedStatement pstmt = null;  //PreparedStatement ��ü ����

		//��ȣ ����
		System.out.print("ȸ����ȣ: ");
		String tid = sc.nextLine();
		
		System.out.print("�̸�: ");
		String tnm = sc.nextLine();
		
		System.out.print("��ȭ��ȣ(�ڵ���): ");
		String thp = sc.nextLine();
		
		String sql = "insert into tb1_member(mem_id, mem_name, mem_hp, mem_mileage) ";
		sql = sql + " values(?, ?, ?, 0)";  //ȸ����ȣ, �̸�, ��ȭ��ȣ, ���ϸ���(���� ����Ȱ�� �������ϱ� 0 ����)
		
		//? �ִ� ���·� ����
		pstmt = conn.prepareStatement(sql);		//�������� 

		//����
		pstmt.setString(1, tid);
		pstmt.setString(2, tnm);
		pstmt.setString(3, thp);  //sql�� �ϼ�
		
		//����
		int rs = pstmt.executeUpdate();
		//executeUpdate<=insert update delete
		//��� ����Ǹ� �Էµ� ���� �� ��ȯ => ���� //select �϶��� => result set
		return rs;
	}
	
	public int update() {
		PreparedStatement pstmt = null;
		int result = 0;
		
		System.out.println("������ ȸ����ȣ: ");
		String tid = sc.nextLine();
		
		System.out.println("��ȭ��ȣ: ");
		String thp = sc.nextLine();
		
		System.out.println("���ϸ���: ");
		int tmile = sc.nextInt();
		
		try {
			// ������ ȸ����ȣ �Է� �ޱ�
			StringBuffer sql = new StringBuffer("update tb1_member \n") ;
			sql.append("      set mem_hp = ? \n");
			sql.append("        , mem_mileage = ? \n");
			sql.append("   where mem_id = ? ");
			
			//���ڿ� ��ü�� ����
			String sqlStr = sql.toString();
			pstmt = conn.prepareStatement(sqlStr);	
	
			pstmt.setString(1, thp);
			pstmt.setInt(2, tmile);
			pstmt.setString(3, tid);
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
