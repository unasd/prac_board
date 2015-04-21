package servlets.board;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.PageNavigator;
import model.board.BoardModel;
import dao.board.BoardDAO;
import dao.board.BoardDAOImpl;
import dao.board.BoardMyBatisDAO;

/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/board/boardListServlet")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private BoardDAO boardDAO = null;
	private BoardDAOImpl boardDAO = null;
	 
    public BoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("boardList Get");
		String pageNum = request.getParameter("pageNum");
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		
		if(pageNum==null){
			pageNum = "1";
		}
		
		// null 값일 때는 빈칸입력
		if(searchText == null){
			searchType = "";
			searchText = "";
		}
		
		BoardModel boardModel = new BoardModel();
		boardModel.setPageNum(pageNum);
		boardModel.setSearchType(searchType);
		boardModel.setSearchText(searchText);
		
		//this.boardDAO = new BoardDAO();
		this.boardDAO = new BoardMyBatisDAO();
		
		int totalCount = this.boardDAO.selectCount(boardModel);
		//System.out.println("boardListServlet totalCount : "+totalCount);
		List<BoardModel> boardList = this.boardDAO.selectList(boardModel);
		
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("pageNavigator", new PageNavigator().getPageNavigator(
				totalCount, boardModel.getListCount(), boardModel.getPagePerBlock(),
				Integer.parseInt(pageNum), searchType, searchText));
		request.setAttribute("boardList", boardList);
		request.setAttribute("boardModel", boardModel);
		
		RequestDispatcher requestDispatcher = 
				request.getRequestDispatcher("/WEB-INF/jsps/board/boardList.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
