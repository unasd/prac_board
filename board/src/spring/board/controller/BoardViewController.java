package spring.board.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import model.board.BoardModel;
import model.board.CommentModel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dao.board.BoardDAOImpl;

@Controller("boardViewContoller")
public class BoardViewController {
	
	@Resource(name="boardMyBatisDAO")
	private BoardDAOImpl boardDAO;
	
	@RequestMapping("/board/boardViewServlet")
	public String select(HttpServletRequest request, BoardModel boardModel, Model model, CommentModel commentModel){
		String pageNum = boardModel.getPageNum();
		String searchType = boardModel.getSearchType();
		String searchText = boardModel.getSearchText();
		
		boardDAO.updateHit(boardModel);
		 
		boardModel = boardDAO.select(boardModel);
		boardModel.setPageNum(pageNum);
		boardModel.setSearchType(searchType);
		boardModel.setSearchText(searchText);

		commentModel.setLinked_article_num(boardModel.getNum());
		List<CommentModel> commentList = boardDAO.commentSelect(commentModel);
		
		model.addAttribute("boardModel", boardModel);
		model.addAttribute("commentList", commentList);
		
		return "/board/boardView";
	}
}
