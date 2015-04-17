package spring.board.controller;

import javax.annotation.Resource;

import model.board.BoardModel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dao.board.BoardDAOImpl;

@Controller("boardDeleteController")
public class BoardDeleteController {
	@Resource(name="boardMyBatisDAO")
	private BoardDAOImpl boardDAO;
	
	@RequestMapping("/board/boardDeleteServlet")
	public ModelAndView delete(BoardModel boardModel){
		boardDAO.delete(boardModel);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:boardListServlet");
		return mav;
	}
}
