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
//			if(result > 0) {   //result 받아야되니까 try catch => record 0개 이상이면-입력되면
//				System.out.println("자료가 정상적으로 입력");
//			}else {
//				System.out.println("자료입력 실패");
//			}
			int rs = db.update();
			if(rs > 0) {
				System.out.println("회원정보 갱신 성공");
			}else {
				System.out.println("회원정보 갱신 실패");
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
	
	public int insert() throws Exception {
		connect();
		PreparedStatement pstmt = null;  //PreparedStatement 객체 생성

		//번호 생성
		System.out.print("회원번호: ");
		String tid = sc.nextLine();
		
		System.out.print("이름: ");
		String tnm = sc.nextLine();
		
		System.out.print("전화번호(핸드폰): ");
		String thp = sc.nextLine();
		
		String sql = "insert into tb1_member(mem_id, mem_name, mem_hp, mem_mileage) ";
		sql = sql + " values(?, ?, ?, 0)";  //회원번호, 이름, 전화번호, 마일리지(아직 구매활동 안했으니까 0 세팅)
		
		//? 있는 상태로 먼저
		pstmt = conn.prepareStatement(sql);		//동적쿼리 

		//맵핑
		pstmt.setString(1, tid);
		pstmt.setString(2, tnm);
		pstmt.setString(3, thp);  //sql문 완성
		
		//실행
		int rs = pstmt.executeUpdate();
		//executeUpdate<=insert update delete
		//명령 실행되면 입력된 행의 수 반환 => 정수 //select 일때만 => result set
		return rs;
	}
	
	public int update() {
		PreparedStatement pstmt = null;
		int result = 0;
		
		System.out.println("갱신할 회원번호: ");
		String tid = sc.nextLine();
		
		System.out.println("전화번호: ");
		String thp = sc.nextLine();
		
		System.out.println("마일리지: ");
		int tmile = sc.nextInt();
		
		try {
			// 갱신할 회원번호 입력 받기
			StringBuffer sql = new StringBuffer("update tb1_member \n") ;
			sql.append("      set mem_hp = ? \n");
			sql.append("        , mem_mileage = ? \n");
			sql.append("   where mem_id = ? ");
			
			//문자열 객체로 만듦
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
