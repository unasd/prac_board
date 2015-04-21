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
		String pageNum = boardModel.getPageNum();
		String searchType = boardModel.getSearchType();
		String searchText = boardModel.getSearchText();
		
		//mav.addObject("boardModel", boardDAO.select(boardModel));
		boardModel = boardDAO.select(boardModel);
		boardModel.setPageNum(pageNum);
		boardModel.setSearchType(searchType);
		boardModel.setSearchText(searchText);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("boardModel", boardModel);
		
		mav.setViewName("/board/boardModify");
		return mav;
	}
	
	@RequestMapping(value="board/boardModifyServlet", method=RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, BoardModel boardModel) throws IOException{
		String pageNum = boardModel.getPageNum();
		String searchType = boardModel.getSearchType();
		String searchText = boardModel.getSearchText();
		String ip = request.getRemoteAddr();
		
		boardModel.setIp(ip);
		boardDAO.update(boardModel);
		
		boardModel = boardDAO.select(boardModel);
		boardModel.setPageNum(pageNum);
		boardModel.setSearchType(searchType);
		boardModel.setSearchText(searchText);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("boardModel", boardModel);
		
		mav.setViewName("/board/boardView");
		return mav;
	}
}
