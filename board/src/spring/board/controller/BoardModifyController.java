package spring.board.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.board.BoardModel;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dao.board.BoardDAOImpl;
import dao.board.BoardMyBatisDAO;

@Controller("boardModifyController")
public class BoardModifyController {
	@Resource(name="boardMyBatisDAO")
	private BoardDAOImpl boardDAO;
	
	@RequestMapping(value="board/boardModifyServlet", method=RequestMethod.GET)
	public ModelAndView modiForm(HttpServletRequest request, BoardModel boardModel){
		//System.out.println("modifyGet");
		/*int num = boardModel.getNum();
		String pageNum = boardModel.getPageNum();
		String searchType = boardModel.getSearchType();
		String searchText = boardModel.getSearchText();*/
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/boardModify");
		mav.addObject("boardModel", boardDAO.select(boardModel));
		
		return mav;
	}
	
	@RequestMapping(value="board/boardModifyServlet", method=RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, BoardModel boardModel, Model model) throws IOException{
		
		System.out.println(request.getCharacterEncoding());
		System.out.println(request.getContentType());
		String ip = request.getRemoteAddr();
		boardModel.setIp(ip);
		boardDAO.update(boardModel);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/boardView");
		mav.addObject("boardModel", boardDAO.select(boardModel));
		//model.addAttribute("boardModel", boardModel);
		return mav;
	}
}
