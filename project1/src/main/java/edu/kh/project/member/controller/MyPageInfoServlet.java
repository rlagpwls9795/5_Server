package edu.kh.project.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.kh.project.member.model.service.MemberService;
import edu.kh.project.member.model.vo.Member;

@WebServlet("/member/myPage/info")
public class MyPageInfoServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher("/WEB-INF/views/member/myPage-info.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String memberNickname = req.getParameter("memberNickname");
		String memberTel = req.getParameter("memberTel");
		
		String[] arr=req.getParameterValues("memberAddress");
		String memberAddress=null;
		if(!arr[0].equals("")&&!arr[1].equals("")) {
			memberAddress=String.join(",,", arr);
		}
		
		Member member = new Member();
		
		member.setMemberNickname(memberNickname);
		member.setMemberTel(memberTel);
		member.setMemberAddress(memberAddress);
		
		//***** 로그인 한 회원의 정보 얻어오기 *****
		HttpSession session = req.getSession();
		//얕은 복사 == session에 저장된 Member 객체의 참조 주소 복사
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		member.setMemberNo(loginMember.getMemberNo());
		
		req.setAttribute("member", member);
		
		try {
			
			int result = new MemberService().updateMember(member);
			
			String message=null;
			
			if(result>0) {
				message="회원 정보 수정 성공";
				
				//!!!DB 데이터 수정 성공 시 session에 담긴 로그인 회원 정보도 같이 수정!!! (데이터 동기화)
				loginMember.setMemberNickname(memberNickname);
				loginMember.setMemberTel(memberTel);
				loginMember.setMemberAddress(memberAddress);
				
			} else {
				message="회원 정보 수정 실패";
			}
			session.setAttribute("message", message);
			resp.sendRedirect("/member/myPage/info");
			
		} catch (Exception e) {
			e.printStackTrace();
			
			String errorMessage = "정보 수정 중 문제가 발생했습니다.";
			req.setAttribute("errorMessage", errorMessage);
			req.setAttribute("e", e);
			
			String path = "/WEB-INF/views/common/error.jsp";
			req.getRequestDispatcher(path).forward(req, resp);
		}
		
		
	}

}
