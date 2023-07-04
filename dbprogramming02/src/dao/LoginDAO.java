package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtill;

public class LoginDAO {
	private static LoginDAO instance = null;
	private LoginDAO() {};
	public static LoginDAO getInstance() {
		if(instance == null) instance = new LoginDAO();
		return instance;
	}
	
	JDBCUtill jdbc = JDBCUtill.getInstance();
	
	//아이디 비밀번호 받아서 DB 처리
	public Map<String, Object> login(String id, String pw){
		String sql = "SELECT * FROM TBL_MEMBER ";
		sql = sql + " WHERE mid = ? ";
		sql = sql + "  AND PASSWD = ? ";
		
		List<Object> param = new ArrayList<Object>();
		param.add(id);
		param.add(pw);
		return jdbc.selectOne(sql, param); 
	}
	
	public Map<String, Object> select(String mid){
		String sql = "SELECT * FROM TBL_MEMBER ";
		sql = sql + " WHERE MID = ? ";
		List<Object> param = new ArrayList<Object>();
		param.add(mid);
		
		return jdbc.selectOne(sql, param);
	}
}
