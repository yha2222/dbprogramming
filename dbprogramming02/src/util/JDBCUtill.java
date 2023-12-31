package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//db관리 효율적으로 할 수 있도록
public class JDBCUtill {
	//한 객체만 처리되는 싱글톤 객체 시작
	private static JDBCUtill instance = null; //객체 변수
	private JDBCUtill() {} //생성자
	public static JDBCUtill getInstance() {
		if(instance == null) instance = new JDBCUtill();
		return instance; 
	}
	//싱글톤 끝
	
	//커넥션 객체 생성할 수 있는 설정
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "pc22";
	private String pw = "java";
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	public List<Map<String, Object>> selectAll(String sql){
		//결과 한 사람분 이상 반환 / 문자열로 쿼리 받음 => ? 필요없음
		//"select * from tbl_member"
		//"select * from tbl_member where mid >= '23010'"
		List<Map<String, Object>> list = null;   //같은 타입 아니니까 Object
		try {
			conn = DriverManager.getConnection(url, user, pw);
			pstmt = conn.prepareStatement(sql); //매개변수 없음
			rs = pstmt.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();  //MetaData=>컬럼의 개수 세기
			int columncount = rsmd.getColumnCount();
			while(rs.next()) {  //맨 처음 자료 읽어오기
				if(list == null) list = new ArrayList<>();
				Map<String, Object> row = new HashMap<>(); //자료 들어갈 공간 만들기
				for(int i = 0; i < columncount; i++) {
					String key = rsmd.getColumnLabel(i + 1); //컬럼명. rsmd=>MetaData니까 이동안됨
					Object value = rs.getObject(i + 1); //실제 자료만 들어있는  rs
					row.put(key, value);
				}
				list.add(row);
			}
			//쿼리 구문 오류 체크
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt != null) try {pstmt.close();}catch(Exception e) {}
			if(conn != null) try {conn.close();}catch(Exception e) {}
		}
		return list;
	}
	
	public Map<String, Object> selectOne(String sql, List<Object> param){
		Map<String, Object> row = null;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			pstmt = conn.prepareStatement(sql);
			//Mapping 자동으로 할 수 있게
			for(int i = 0; i < param.size(); i++) {
				pstmt.setObject(i+1,  param.get(i));
				//i+1번째 '?' => 오라클 0번째 없음 / param.get(0) => param[0] => id !!
			}
			
			//rs => 한 사람 분의 결과값 - 5개 열
			rs = pstmt.executeQuery(); //쿼리 완성 / select문 => executeQuery
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columncount = rsmd.getColumnCount(); //5개
			while(rs.next()) {
				row = new HashMap<>(); //다음 자료 있으면 만든다
				for(int i = 0; i < columncount; i++) {
					String key = rsmd.getColumnLabel(i+1);
					Object value = rs.getObject(i+1);
					row.put(key, value);
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt != null) try {pstmt.close();}catch(Exception e) {}
			if(conn != null) try {conn.close();}catch(Exception e) {}
		}
		return row;
	}
	
	//매개변수 없는 건 쿼리만 들어오면 됨
	public int update(String sql) {
		//sql = "delete from tbl_member" - 매개변수 없는 경우
		//sql = "delete from tbl_member where mid = '23011' - 데이터 포함하는 경우
		//sql = "update tbl_member set mileage = mileage * 1.1"
		//sql = "update tbl_member set tel_num = '010-1234-5678"
		//			where mid = '23011'" - 특정 값 변경
		//sql = "insert into tbl_member(mid, mname) values ('23030', '이정민')"
		
		int result  = 0;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			pstmt = conn.prepareStatement(sql);
			//완성된 쿼리 받음
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt != null) try {pstmt.close();}catch(Exception e) {}
			if(conn != null) try {conn.close();}catch(Exception e) {}
		}
		return result; //행의 수 반환
	}
	
	public int update(String sql, List<Object>param) {
		//sql = "delete from tbl_member where mid = ?" - 데이터 포함하는 경우
		//sql = "update tbl_member set tel_num = '010-1234-5678"
		//			where mid = ?" - 특정 값 변경
		//sql = "insert into tbl_member(mid, mname) values (?, ?)"
		int result  = 0;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			pstmt = conn.prepareStatement(sql);
			for(int i =0; i<param.size(); i++) {
				pstmt.setObject(i+1, param.get(i));
			}
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt != null) try {pstmt.close();}catch(Exception e) {}
			if(conn != null) try {conn.close();}catch(Exception e) {}
		}
		return result;
	}
}
