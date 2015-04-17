package spring.board.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import model.board.BoardModel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import util.PageNavigator;
import dao.board.BoardDAOImpl;

@Controller("boardListController")
public class BoardListController {
	
	@Resource(name="boardMyBatisDAO")
	private BoardDAOImpl boardDAO;
	
	@RequestMapping("/board/boardListServlet")
	public ModelAndView selectList(HttpServletRequest request, BoardModel boardModel){
		//System.out.println("selectList");
		String pageNum = boardModel.getPageNum();
		String searchType = boardModel.getSearchType();
		String searchText = boardModel.getSearchText();
		
		if(pageNum==null){
			pageNum = "1";
		}
		
		// null 값일 때는 빈칸입력
		if(searchText == null){
			searchType = "";
			searchText = "";
		}
		
		int totalCount = boardDAO.selectCount(boardModel);
		List<BoardModel> boardList = boardDAO.selectList(boardModel);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("totalCount", totalCount);
		mav.addObject("boardModel", boardModel);
		mav.addObject("pageNavigator", new PageNavigator().getNavigator(totalCount, boardModel.getListCount(), 
					boardModel.getPagePerBlock(), Integer.parseInt(boardModel.getPageNum()), searchType, searchText));
		mav.addObject("boardList", boardList);
		mav.setViewName("/board/boardList");
		return mav;
	}
}
