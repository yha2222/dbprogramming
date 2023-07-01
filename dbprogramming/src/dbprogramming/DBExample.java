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
			//connection 객체 - 연결만 해준 상태
			conn = DriverManager.getConnection(url, user, passwd);
		}catch(Exception e) {
			System.out.println("DB 연결 실패!");
			e.printStackTrace();
		}
		System.out.println("DB 연결 성공!");
	}
	
	public void select() {
		//정적쿼리 - 정해진 값
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("조회할 회원번호 입력 : ");
			String tid = sc.nextLine(); //조회할 회원
			
			String sql = "select * from tb1_member where mem_id = ?";
			pstmt = conn.prepareStatement(sql);//명령어 객체 생성 -미완성
			pstmt.setString(1, tid);//데이터-파라미터 연결 -완성
			
			rs = pstmt.executeQuery();	 // ?-select문 수행
			//명령어 실행 -select문(검색) 포함 쿼리 실행하는 메소드, ()안에 실행될 쿼리 들어감=>sql
			//executeUpdate - update insert delete (dml) 
			
			while(rs.next()) { //데이터 꺼내옴 있으면 참 없으면 거짓
				//sql은 String으로만 
				String mid = rs.getString("mem_id"); //"오라클에 사용된 컬럼명"/컬럼 인덱스(1)->해당 컬럼 내 데이터 알기 위해 컬럼명 쓰는 게 제일 좋음"
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
		}catch(Exception e) {  //모든 오류 다 거름 => 맨 마지막에 씀 다른 애들로 오류 못 거를 때 최후 보루
			e.printStackTrace();
		}finally {
			if(rs != null) try {rs.close();}catch(Exception e) {} //예외 발생 시 처리 내용 없음
			if(pstmt != null) try {pstmt.close();}catch(Exception e) {}
			if(conn != null) try {conn.close();}catch(Exception e) {}
		}
	}
	
	public void insert() {
		connect();
		//번호 생성
		
		System.out.print("이름: ");
		String tnm = sc.next();
		
		System.out.print("이름: ");
		String tnm1 = sc.next();
	}
}
