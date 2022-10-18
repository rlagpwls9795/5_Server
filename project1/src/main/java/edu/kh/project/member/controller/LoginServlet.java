package edu.kh.project.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.kh.project.member.model.service.MemberService;
import edu.kh.project.member.model.vo.Member;

@WebServlet("/member/login")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		MemberService service = new MemberService();
		
		try {
			String inputEmail = req.getParameter("inputEmail");
			String inputPw = req.getParameter("inputPw");
			
			Member member = new Member();
			member.setMemberEmail(inputEmail);
			member.setMemberPw(inputPw);
			
			Member loginMember = service.login(member);
			
			//forward 하는 경우
			//- 요청을 다른 Servlet/JSP로 위임
			//	-> 어떤 요청이 위임됐는지 알아야 하기 때문에 주소창에 요청 주소가 계속 남아 있다
//			req.setAttribute("loginMember", loginMember);
//			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/common/main.jsp");
//			dispatcher.forward(req, resp);
			
			
			
			
			// *** redirect(재요청) ***
			//- Servlet이 다른 주소를 요청함
			//- 요청에 대한 응답 화면을 직접 만드는 것이 아닌 다른 응답 화면을 구현하는 Servlet을 요청하여 대신 화면을 만들게 하는 것
			
			//request scope에 속성을 추가해도 redirect 하면 request가 다시 만들어져 유지되지 않기 때문에 화면에 나타나지 않음
//			req.setAttribute("loginMember", loginMember); 
			
			//해결 방법 : Session scope 이용
			//1) HttpSession 객체 얻어오기
			HttpSession session = req.getSession();
			
			if(loginMember!=null) { //로그인 성공
				
				//2) Session scope에 속성 추가하기
				session.setAttribute("loginMember", loginMember);
				//3) 세션 만료 시간 설정(초 단위로 지정)
				//	(클라이언트가 새로운 요청을 할 때마다 초기화)
				session.setMaxInactiveInterval(60*60);
				
				//-------------------------------------------------------------
				// 아이디 저장 (Cookie)
				
				
				/* Cookie란?
				 * - 클라이언트 측(브라우저)에서 관리하는 파일
				 * - 쿠키 파일에 등록된 주소를 요청할 때마다 자동으로 요청에 첨부되어 서버로 전달됨
				 * - 서버로 전달된 쿠키에 값 수정, 추가, 삭제 등 진행한 후 다시 클라이언트에게 반환
				 */ 
				
				
				// 2) 쿠키 객체 생성 
				// - 생성 된 쿠키 객체를 resp를 이용해서 클라이언트에게 전달하면 클라이언트 컴퓨터에 파일 형태로 저장됨
				Cookie cookie= new Cookie("saveId", inputEmail);
				
				// 1) 아이디 저장 체크박스 체크 여부 확인
				if(req.getParameter("saveId")!=null) { //체크 O
					
					// 3) 쿠키가 유지될 수 있는 유효기간 설정(초 단위)
					cookie.setMaxAge(30*24*60*60);
					
				} else { //체크 X
					
					// 4) 쿠키의 유효기간을 0초로 설정 == 클라이언트에 저장된 saveId 쿠키를 삭제
					//    (같은 키 값의 쿠키가 저장되면 덮어쓰기가 일어남)
					cookie.setMaxAge(0);
				}
				
				// 5) 생성된 쿠키가 적용되어질 요청 주소를 저장
				cookie.setPath("/"); // 메인 페이지 주소 (http://localhost/)
									 // == 메인 페이지의 하위 주소 모두 적용 
				
				// 6) 설정 완료된 쿠키 객체를 클라이언트에게 전달
				resp.addCookie(cookie);
				
				//-------------------------------------------------------------
				
			} else { //로그인 실패 
				session.setAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
			}
			
			
			//메인 페이지로 redirect
			//-> 메인 페이지를 요청한 것이기 때문에 주소창에 주소가 메인 페이지 주소(/)로 변함
			resp.sendRedirect("/");
			
			
			
			
			/* forward / redirect 차이점
			*
			* forward
			* - 주소창 변화 X
			* - JSP 경로 작성
			* - req, resp가 유지
			* 
			* redirect
			* - 주소창 변화 O
			* - 요청 주소 작성
			* - req, resp가 유지되지 않는다
			* 
			*/
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
