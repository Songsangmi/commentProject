package co.yedam.comment;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CommentServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().create();
		
		String cmd = request.getParameter("cmd");
		CommentDAO dao = CommentDAO.getInstance();
		
		if (cmd == null) {
			out.println("<h1>빈페이지입니다</h1>");
			
		} else if (cmd.equals("list")) {
			System.out.println("<h1>리스트페이지입니다</h1>");
			List<Comment> list = dao.getCommentList();
			out.println(gson.toJson(list));
			
			
		} else if (cmd.equals("add")) {
			System.out.println("<h1>추가페이지입니다</h1>");
			String name =  request.getParameter("name");
			String content = request.getParameter("content");
			
			Comment comment = new Comment();
			comment.setName(name);
			comment.setContent(content);
			
			dao.insertComment(comment);
			
			out.println(gson.toJson(comment));
			
			
		} else if (cmd.equals("mod")) {
			System.out.println("<h1>수정페이지입니다</h1>");
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String content = request.getParameter("content");
			
			Comment comment = new Comment();
			comment.setId(id);
			comment.setName(name);
			comment.setContent(content);
			
			dao.updateComment(comment);
			
			out.println(gson.toJson(comment));
			
			
		} else if (cmd.equals("del")) {
			System.out.println("<h1>삭제페이지입니다</h1>");
			String id = request.getParameter("id");
			
			if (dao.deleteComment(id) ==null) {
				//{"retCode":"fail"}
				out.println("{\"retCode\":\"fail\"}");
			}
			out.println("{\"retCode\":\"success\"}");
			return;
		} else {
			out.println("<h1>" + cmd + "</h1>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}