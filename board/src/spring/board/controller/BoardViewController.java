package spring.board.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import model.board.BoardModel;

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
	public String select(HttpServletRequest request, BoardModel boardModel, Model model){
		String searchType = boardModel.getSearchType();
		String searchText = boardModel.getSearchText();
		
		boardDAO.updateHit(boardModel);
		 
		boardModel = boardDAO.select(boardModel);
		boardModel.setSearchType(searchType);
		boardModel.setSearchText(searchText);
		
		model.addAttribute("boardModel", boardModel);
		return "/board/boardView";
	}
}
