package edu.kh.servlet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* @WebServlet("요청주소")
   : 해당 클래스는 Servlet으로 등록하고 (<servlet>)
     + 어떤 요청 주소를 처리할지 지정    (<servlet-mapping>)
     의 내용을 한 번에 작성할 수 있는 annotation
 */

@WebServlet("/signUp")
public class ServletEx3 extends HttpServlet{
	
	// doPost() : POST 방식 요청을 처리하는 메서드
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// POST 방식은 문자 데이터가 깨질 위험이 있음
		// 왜? 브라우저 서버 간의 문자 인코딩 차이 때문에
		// 해결방법 : 요청 정보에 담긴 데이터의 문자 인코딩을 UTF-8로 변경
		req.setCharacterEncoding("UTF-8");
		
		String inputId = req.getParameter("inputId");
		String[] inputPw =req.getParameterValues("inputPw"); //name 속성 값이 같은 input 태그의 값은 String[] 하나로 얻어올 수 있음
		String inputEmail = req.getParameter("inputEmail");
		String agree=req.getParameter("agree");
		
		System.out.println(inputId);
		System.out.println(inputPw[0]);
		System.out.println(inputPw[1]);
		System.out.println(inputEmail);
		System.out.println(agree);
		
		// 응답 데이터 문서 형식, 문자 인코딩 지정
		resp.setContentType("text/html; charset=UTF-8");
		
		// 응답용 스트림 얻어오기
		PrintWriter out = resp.getWriter();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head><title>회원가입 결과</title></head>");
		
		out.println("<body>");
		if(inputPw[0].equals(inputPw[1])) { //비밀번호가 같은 경우
			//가입 성공
			//user01님 환영합니다.
			//이메일 수신 동의 여부 : O/X
			//(동의 O인 경우)
			//수신할 이메일 : user01@naver.com
			out.printf("<h3>%s님 환영합니다.</h3>",inputId);
			
			//checkbox는 별도의 value가 없을 때
			//체크 O : on
			//체크 X : null
			if(agree==null) {
				agree="X";
				out.printf("<h3>이메일 수신 동의 여부 : %s</h3>",agree);
			} else {
				agree="O";
				out.printf("<h3>이메일 수신 동의 여부 : %s</h3>",agree);
				out.println("<h3>수신할 이메일 : "+inputEmail+"</h3>");
			}
			
		} else {
			//가입 실패
			out.println("<h1 style='color:red;'>비밀번호가 일치하지 않습니다.</h1>");
		}
		
		out.println("</body>");
		out.println("</html>");
		
		
	}

}
















