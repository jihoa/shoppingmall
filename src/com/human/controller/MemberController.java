package com.human.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.human.command.member.CartListOrderCommand;
import com.human.command.member.FindIDCommand;
import com.human.command.member.FindPwCommand;
import com.human.command.member.IdCheckCommand;
import com.human.command.member.Index1By1PageCommand;
import com.human.command.member.Index9Command;
import com.human.command.member.IndexSearchQnaListDeleteCommand;
import com.human.command.member.IndexSearchQnaListPageCommand;
import com.human.command.member.IndexSearchQnaListUpdateCommand;
import com.human.command.member.MemberCommand;
import com.human.command.member.MyQnaPageCommand;
import com.human.command.member.NewJoinCommand;
import com.human.command.member.NewLoginCommand;
import com.human.command.member.NoticeCommand;
import com.human.command.member.OrderSelectCommand;
import com.human.command.member.QnaBoardListCommand;
import com.human.command.member.QnaBoardReplyCommand;
import com.human.command.member.QnaCommand;
import com.human.command.member.QnaInsertCommand;
import com.human.command.member.ReviewDeleteCommand;
import com.human.command.member.ReviewInsertCommand;
import com.human.command.member.ReviewUpdateCommand;
import com.human.command.member.ReviewUpdateOkCommand;
import com.human.command.member.User_selectCommand;
import com.human.mail.MailSend;

/**
 * Servlet implementation class idCheckServlet
 */
@WebServlet("*.so")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");

		MemberCommand bCom = null;

		// ??????????????? ????????? view ??????
		String viewPage = null;

		String uri = request.getRequestURI();
		System.out.println(uri);

		String conPath = request.getContextPath();
		System.out.println(conPath);

		String com = uri.substring(conPath.length());
		System.out.println(com);

		if (com.equals("/idCheck.so")) {
			//ID????????????
			viewPage = "member/signup_CheckId.jsp";
			bCom = new IdCheckCommand();
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		} else if (com.equals("/member/signupNew.so")) {
			//????????????
			viewPage = "main_Newjoin.jsp";
			bCom = new NewJoinCommand();
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		} else if (com.equals("/member/login.so")) {
			//?????????
			bCom = new NewLoginCommand();
			bCom.execute(request, response);
			String temp = (String) request.getAttribute("temp");

			if (temp == "1") {
				String name = (String) request.getAttribute("name");
				String rank = (String) request.getAttribute("rank");
				String id = (String) request.getAttribute("id");
				HttpSession session = request.getSession();

				session.setAttribute("login_Name", name);
				session.setAttribute("login_Id", id);
				session.setAttribute("login_Rank", rank);

				session.setMaxInactiveInterval(10000);// ?????? ?????? ?????? ?????? ????????? ??????????????? ????????????
				response.sendRedirect("../main/main.jsp");
			} else {
				viewPage = "../member/main_login_fail.jsp";
				response.sendRedirect("../member/main_login_fail.jsp");
			}
		} else if (com.equals("/logout.so")) {
			//????????????
			viewPage = "main.jsp";
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("main/main.jsp");

		} else if (com.equals("/manager/logout.so")) {
			//????????? ????????? ????????????
			viewPage = "main.jsp";
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("../main/main.jsp");

		} else if (com.equals("/member/mainPwdFindPage.so")) {
			// ???????????? ?????? 
			bCom = new FindPwCommand();
			bCom.execute(request, response);

			if (request.getAttribute("newPw") == null) {
				
				viewPage = "main_Pwd_Check_fail.jsp";
			} else {
				// ?????? ??????
				String address = (String) request.getAttribute("newEmail");
				String pwForSend = (String) request.getAttribute("newPw");
				MailSend mailSend = new MailSend();
				mailSend.PWmailSend(address, pwForSend);

				viewPage = "main_Pwd_Check_Succeed.jsp";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);

		} else if (com.equals("/member/mainIDFindPage.so")) {
			// ????????? ?????? 
			bCom = new FindIDCommand();
			bCom.execute(request, response);

			if (request.getAttribute("newID") == null) {
				viewPage = "main_Id_Check_fail.jsp";
			} else {
				// ?????? ??????
				String address = (String) request.getAttribute("newEmail");
				String IDForSend = (String) request.getAttribute("newID");
				System.out.println("???????????? \n" + address + "\n" + IDForSend);
				MailSend mailSend = new MailSend();
				mailSend.IDmailSend(address, IDForSend);

				viewPage = "main_Id_Check_Succeed.jsp";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);

		} else if (com.equals("/member/myqna_view.so")) {
			//???????????? - ??????????????????
			viewPage = "customer8_myqna.jsp";
			bCom = new MyQnaPageCommand();
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		} else if (com.equals("/cart/login.so")) {
			//???????????? ????????? ???????????????
			bCom = new NewLoginCommand();
			bCom.execute(request, response);
			String temp = (String) request.getAttribute("temp");

			if (temp == "1") {
				String name = (String) request.getAttribute("name");
				String rank = (String) request.getAttribute("rank");
				String id = (String) request.getAttribute("id");
				HttpSession session = request.getSession();
				session.setAttribute("login_Name", name);
				session.setAttribute("login_Rank", rank);
				session.setAttribute("login_Id", id);
				System.out.println("login_Id : " + id);
				session.setMaxInactiveInterval(10000);// ?????? ?????? ?????? ?????? ????????? ??????????????? ????????????
				response.sendRedirect("../cart/cartSelect.po");
			} else {
				viewPage = "../member/main_login_fail.jsp";
				response.sendRedirect("../member/main_login_fail.jsp");
			}
		}else if (com.equals("/member/notice_view.so")) {
			//footer ????????????
			viewPage = "notice.jsp";
			bCom = new NoticeCommand();
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}else if(com.equals("/myPage/index9.so")) {
			//myPage ???????????????
			viewPage="/myPage/index9.jsp";
			System.out.println("00");
			bCom=new Index9Command();
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);				
		}else if(com.equals("/myPage/index9_2.so")) {
			//myPage ???????????????
			viewPage="/myPage/index9.so";
			System.out.println("9-2");
			bCom=new ReviewInsertCommand();
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);	
		}else if(com.equals("/myPage/index9_update.so")) {
			//myPage ????????? ?????? 
			System.out.println("1");
			bCom=new ReviewUpdateCommand();
			viewPage="/myPage/index9_1.jsp";
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}else if(com.equals("/myPage/reviewUpdate.so")) {
			//myPage ????????? ????????????
			System.out.println("2");
			bCom=new ReviewUpdateOkCommand();
			viewPage="/myPage/index9.so";
			System.out.println("3");
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}else if(com.equals("/myPage/index9_delete.so")) {
			//myPage ???????????????
			System.out.println("delete");
			bCom=new ReviewDeleteCommand();
			viewPage="/myPage/index9.so";
			System.out.println("4");
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}else if(com.equals("/myPage/index8.so")) {
			//myPage ??????????????? ??????
			System.out.println("0");
			bCom=new OrderSelectCommand();
			viewPage="/myPage/index8.jsp";
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}else if(com.equals("/board/qnaBoard.so")){
			//productDetails ???????????? ?????????			
			bCom = new QnaCommand();
			bCom.execute(request, response);
			response.sendRedirect("askqna.jsp?check=1");
		}else if (com.equals("/myPage/indexSearchQnaList_view.so")) {
			//myPage ?????????????????? ??????
			viewPage = "indexSearch.jsp";
			bCom = new IndexSearchQnaListPageCommand();
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}else if (com.equals("/myPage/indexqna1by1_view.so")) {
			//myPage ????????????????????? ??????
			viewPage = "indexqna1by1.jsp";
			bCom = new Index1By1PageCommand();
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		} else if (com.equals("/myPage/qnaUpdate.so")) {
			//myPage ?????????????????? ??????
			viewPage = "indexSearchQnaList_view.so";
			bCom = new IndexSearchQnaListUpdateCommand();
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		} else if (com.equals("/myPage/qnaDelete.so")) {
			//myPage ?????????????????? ??????
			viewPage = "indexSearchQnaList_view.so";
			bCom = new IndexSearchQnaListDeleteCommand();
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}else if(com.equals("/member/select_goods.so")){
			//???????????? ??????????????? ??? ??????????????????
			viewPage = "select_goods.jsp";
			bCom = new User_selectCommand();
			System.out.println("select_goods.jsp??????");
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}else if(com.equals("/member/select_goods2.so")){
			//???????????? ??????????????? ??? ????????????->???????????? ???
			viewPage = "select_goods2.jsp";
			bCom = new CartListOrderCommand();
			System.out.println("select_goods2.jsp??????");
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}else if(com.equals("/member/customer7_1by1.so")){
			//???????????? ???????????????
			viewPage = "customer7_1by1.jsp";
			bCom = new User_selectCommand();
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);	
		}else if(com.equals("/member/customer_inquiry.so")) {
			//footer ???????????????
			viewPage= "myqna_view.so";
			bCom=new QnaInsertCommand();
			bCom.execute(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);			
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
