package spring.board.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import model.board.BoardModel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dao.board.BoardDAOImpl;

@Controller("boardWriteController")
public class BoardWriteController {
	@Resource(name="boardMyBatisDAO")
	private BoardDAOImpl boardDAO;
	
	@RequestMapping(value="/board/boardWriteServlet", method=RequestMethod.GET)
	public String writeForm(){
		//System.out.println("writeServlet Get");
		return "board/boardWrite";
	}
	
	@RequestMapping(value="/board/boardWriteServlet", method=RequestMethod.POST)
	public String insert(HttpServletRequest request, BoardModel boardModel){
		//System.out.println("writeServlet Post");
		String ip = request.getRemoteAddr();
		boardModel.setIp(ip);
		boardDAO.insert(boardModel);
		
		return "redirect:boardListServlet";
	}
}
