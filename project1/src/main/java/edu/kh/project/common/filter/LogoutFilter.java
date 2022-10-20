package edu.kh.project.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "logoutFilter", urlPatterns = {"/member/signUp"})
public class LogoutFilter extends HttpFilter implements Filter {
     
	public void destroy() {
		System.out.println("로그아웃 상태인지 검사하는 필터 파괴");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 로그인 상태 검사 == session에 loginMember가 null이 아닌지
		// Session 객체는 HttPServletRequest에서만 얻어 올 수 있다 -> 다운캐스팅
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		HttpSession session =  req.getSession();
		if(session.getAttribute("loginMember") !=null ) { //로그인 상태인 경우
			session.setAttribute("message", "로그인 상태로 이용할 수 없습니다.");
			resp.sendRedirect("/");
		} else {
			chain.doFilter(request, response);			
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("로그아웃 상태인지 검사하는 필터 초기화");
	}

}
