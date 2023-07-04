package dao;

import java.util.ArrayList;
import java.util.List;

import util.JDBCUtill;

public class MemberDAO {
	//�̱���
	private static MemberDAO instance = null;
	private MemberDAO() {}
	public static MemberDAO getInstance() {
		if(instance == null) instance = new MemberDAO();
		return instance;
	}
	
	JDBCUtill jdbc = JDBCUtill.getInstance();
	
	//�ű� �Է�(singUp)
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
		//��������(Update)
	public int update(String str, String mid) {
		int result = 0;
		String sql = "UPDATE tbl_member SET ";
		sql = sql + str;   //���� ���� str
		sql = sql + " WHERE MID = "+ "'" + mid + "'";   //�Ѱܹ��� mid ��
		
		return jdbc.update(sql);
		//UPDATE tbl_member SET + ������ ������ + WHERE~ => JDBCUtill.update(String sql)
	}
		//ȸ���������˻�(searchOne)
		//ȸ�������ȸ(searchAll)
		//ȸ������(delete)
}
