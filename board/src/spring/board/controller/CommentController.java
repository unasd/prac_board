package spring.board.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import model.board.BoardModel;
import model.board.CommentModel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dao.board.BoardDAOImpl;

@Controller("commentController")
public class CommentController {

	@Resource(name="boardMyBatisDAO")
	private BoardDAOImpl boardDAO;
	
	@RequestMapping(value="/board/commentWrite", method=RequestMethod.POST)
	public String commentWrite(HttpServletRequest request, BoardModel boardModel , @ModelAttribute CommentModel commentModel){
		String pageNum = boardModel.getPageNum();
		String searchType = boardModel.getSearchType();
		String searchText = boardModel.getSearchText();
		int num = boardModel.getNum();
		
		boardDAO.commentWrite(commentModel);
		
		//ModelAndView mav = new ModelAndView();
		//mav.setViewName("redirect:boardViewServlet?num="+num+"&searchType="+searchType+"&searchText="+searchText);
		return "redirect:boardViewServlet?num="+num+"&pageNum="+pageNum+"&searchType="+searchType+"&searchText="+searchText;
	}
	
	@RequestMapping("/board/commentDelete")
	public String commentDelete(HttpServletRequest request, BoardModel boardModel, CommentModel commentModel){
		String pageNum = boardModel.getPageNum();
		String searchType = boardModel.getSearchType();
		String searchText = boardModel.getSearchText();
		int num = boardModel.getNum();
		String idx = commentModel.getIdx();
		
		boardDAO.commentDelete(commentModel);
		return "redirect:boardViewServlet?num="+num+"&pageNum="+pageNum+"&searchType="+searchType+"&searchText="+searchText;
	}
}
