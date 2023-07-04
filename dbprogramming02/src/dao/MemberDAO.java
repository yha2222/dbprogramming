package dao;

import java.util.ArrayList;
import java.util.List;

import util.JDBCUtill;

public class MemberDAO {
	//싱글톤
	private static MemberDAO instance = null;
	private MemberDAO() {}
	public static MemberDAO getInstance() {
		if(instance == null) instance = new MemberDAO();
		return instance;
	}
	
	JDBCUtill jdbc = JDBCUtill.getInstance();
	
	//신규 입력(singUp)
	public int signUp(String name, String telNum, int mile, String pw) {
		String sql = "INSERT INTO TBL_MEMBER(MID, MNAME, TEL_NUM, MILEAGE, PASSWD) ";
		sql = sql + " VALUES(fn_create_new_mid('2023'),?, ?, ?, ? )";
		
		List<Object> param = new ArrayList<Object>();
		param.add(name);
		param.add(telNum);
		param.add(mile);
		param.add(pw);
		
		return jdbc.update(sql, param);
	}
		//정보변경(Update)
	public int update(String str, String mid) {
		int result = 0;
		String sql = "UPDATE tbl_member SET ";
		sql = sql + str;   //여태 만든 str
		sql = sql + " WHERE MID = "+ "'" + mid + "'";   //넘겨받은 mid 값
		
		return jdbc.update(sql);
		//UPDATE tbl_member SET + 변경할 데이터 + WHERE~ => JDBCUtill.update(String sql)
	}
		//회원별정보검색(searchOne)
		//회원모두조회(searchAll)
		//회원삭제(delete)
}
