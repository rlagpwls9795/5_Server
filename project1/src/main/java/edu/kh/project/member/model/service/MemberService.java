package edu.kh.project.member.model.service;

import static edu.kh.project.common.JDBCTemplate.*;

import java.sql.Connection;

import edu.kh.project.member.model.dao.MemberDAO;
import edu.kh.project.member.model.vo.Member;

/** 회원 전용 기능 제공 서비스
 * @author user1
 *
 */
public class MemberService {
	
	private MemberDAO dao = new MemberDAO();

	/** 로그인 서비스
	 * @param member
	 * @return loginMember
	 * @throws Exception
	 */
	public Member login(Member member) throws Exception{
		
		Connection conn = getConnection();
		
		Member loginMember = dao.login(member, conn);
		
		close(conn);
		
		return loginMember;
	}

}
