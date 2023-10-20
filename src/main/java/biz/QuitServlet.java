package biz;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDAO;
import vo.MemberVO;

/**
 * Servlet implementation class QuitServlet
 */
@WebServlet("/quit")
public class QuitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		MemberVO user = (MemberVO)session.getAttribute("loginOK");
		int result = 0;
		
		if (user == null) {
			out.println("<script> alert('사용자 정보가 없어 회원탈퇴를 진행할 수 없습니다.'); history.back() </script>");
		} else {
			MemberDAO dao = new MemberDAO();
			result = dao.removeMember(user.getMemberId());
			String msg;
			
			if (result > 0) {
				msg = user.getMemberName() + "님의 회원정보가 삭제되었습니다.";
				session.removeAttribute("loginOK");
			} else {
				msg = user.getMemberName() + "님의 회원정보 삭제가 실패하였습니다.";
			}
			
			session.setAttribute("quit", msg);
			response.sendRedirect("/LibraryProject/member/quitResult.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
