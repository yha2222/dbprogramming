package controller;

import java.util.HashMap;
import java.util.Map;

import service.LoginService;
import service.MemberService;

public class Controller {
	//로그인할 때 아이디 비밀번호 저장될 곳
	public static Map<String, Object>sessionStorage = new HashMap<String, Object>();
	
	public static void main(String[] args) {
		new Controller().init();
		//new Controller()가 현재 시점으로 바꿔서 static 아니어도 얼마든지 씀
	}
	
	public void init() {
		//객체 생성시 new 연산자 안썼고 getInstance 씀 => 싱글톤 사용
//		LoginService loginService = LoginService.getInstance();
//		loginService.login();
		//쿼리 필요 자료 보내줌
		//=> dao.login(id, pw) => Map타입의 반환값
		// => loginDao - login()호출  => Query작성 - 완성된 상태 아님! '?'상태임..
		// => selectOne(sql -> '?'있는 쿼리문, param->입력받은 데이터(대응시킬 자료)[0]:id, [1]:pw) => 역시 Map 반환
		//    param.add(id/pw)로 데이터 받아서 로그인 비교할 수 있지만 그것뿐만 아니라 여러 종류 쿼리들 실행 가능
		//	=> JDBCUtil-selectOne() 호출 => 쿼리(다른 테이블도 조회 가능-외부 입력 데이터 의해서 조회되는 한) 작성되야 함
		//	=> 한 사람 분 데이터임 동시로그인 아니라 => selectAll 아니고 selectOne
		//	 => service는 데이터 입력받지 반환하지 않음
		
		MemberService memberService = MemberService.getInstance();
//		memberService.signUp();
		
		memberService.update();
	}

}
