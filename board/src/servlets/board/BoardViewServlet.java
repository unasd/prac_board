package servlets.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.board.BoardDAO;
import model.board.BoardModel;

/**
 * Servlet implementation class BoardViewServlet
 */
@WebServlet("/board/boardViewServlet")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardDAO boardDAO = null;

    public BoardViewServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		System.out.println(num);
		
		BoardModel boardModel = new BoardModel();
		boardModel.setNum(num);
		boardModel.setPageNum(pageNum);
		boardModel.setSearchType(searchType);
		boardModel.setSearchText(searchText);
		
		this.boardDAO = new BoardDAO();
		this.boardDAO.updateHit(boardModel);
		boardModel = this.boardDAO.select(boardModel);
		
		
		request.setAttribute("boardModel", boardModel);
		
		RequestDispatcher requestDispatcher 
			= request.getRequestDispatcher("/WEB-INF/jsps/board/boardView.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
