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

//db���� ȿ�������� �� �� �ֵ���
public class JDBCUtill {
	//�� ��ü�� ó���Ǵ� �̱��� ��ü ����
	private static JDBCUtill instance = null; //��ü ����
	private JDBCUtill() {} //������
	public static JDBCUtill getInstance() {
		if(instance == null) instance = new JDBCUtill();
		return instance; 
	}
	//�̱��� ��
	
	//Ŀ�ؼ� ��ü ������ �� �ִ� ����
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "pc22";
	private String pw = "java";
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	public List<Map<String, Object>> selectAll(String sql){
		//��� �� ����� �̻� ��ȯ / ���ڿ��� ���� ���� => ? �ʿ����
		//"select * from tbl_member"
		//"select * from tbl_member where mid >= '23010'"
		List<Map<String, Object>> list = null;   //���� Ÿ�� �ƴϴϱ� Object
		try {
			conn = DriverManager.getConnection(url, user, pw);
			pstmt = conn.prepareStatement(sql); //�Ű����� ����
			rs = pstmt.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();  //MetaData=>�÷��� ���� ����
			int columncount = rsmd.getColumnCount();
			while(rs.next()) {  //�� ó�� �ڷ� �о����
				if(list == null) list = new ArrayList<>();
				Map<String, Object> row = new HashMap<>(); //�ڷ� �� ���� �����
				for(int i = 0; i < columncount; i++) {
					String key = rsmd.getColumnLabel(i + 1); //�÷���. rsmd=>MetaData�ϱ� �̵��ȵ�
					Object value = rs.getObject(i + 1); //���� �ڷḸ ����ִ�  rs
					row.put(key, value);
				}
				list.add(row);
			}
			//���� ���� ���� üũ
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
			//Mapping �ڵ����� �� �� �ְ�
			for(int i = 0; i < param.size(); i++) {
				pstmt.setObject(i+1,  param.get(i));
				//i+1��° '?' => ����Ŭ 0��° ���� / param.get(0) => param[0] => id !!
			}
			
			//rs => �� ��� ���� ����� - 5�� ��
			rs = pstmt.executeQuery(); //���� �ϼ� / select�� => executeQuery
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columncount = rsmd.getColumnCount(); //5��
			while(rs.next()) {
				row = new HashMap<>(); //���� �ڷ� ������ �����
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
	
	//�Ű����� ���� �� ������ ������ ��
	public int update(String sql) {
		//sql = "delete from tbl_member" - �Ű����� ���� ���
		//sql = "delete from tbl_member where mid = '23011' - ������ �����ϴ� ���
		//sql = "update tbl_member set mileage = mileage * 1.1"
		//sql = "update tbl_member set tel_num = '010-1234-5678"
		//			where mid = '23011'" - Ư�� �� ����
		//sql = "insert into tbl_member(mid, mname) values ('23030', '������')"
		
		int result  = 0;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			pstmt = conn.prepareStatement(sql);
			//�ϼ��� ���� ����
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try {rs.close();}catch(Exception e) {}
			if(pstmt != null) try {pstmt.close();}catch(Exception e) {}
			if(conn != null) try {conn.close();}catch(Exception e) {}
		}
		return result; //���� �� ��ȯ
	}
	
	public int update(String sql, List<Object>param) {
		//sql = "delete from tbl_member where mid = ?" - ������ �����ϴ� ���
		//sql = "update tbl_member set tel_num = '010-1234-5678"
		//			where mid = ?" - Ư�� �� ����
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
