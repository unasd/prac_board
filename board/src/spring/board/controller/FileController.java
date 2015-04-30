package spring.board.controller;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import model.board.FileModel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dao.board.BoardDAOImpl;

@Controller("FileController")
public class FileController {
	
	@Resource(name="boardMyBatisDAO")
	private BoardDAOImpl boardDAO;

	@RequestMapping(value="/download")
	public ModelAndView downLoad(HttpServletRequest request, FileModel fileModel){
		String realFolder = "C:/JSH/prac_board/upFiles/";
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("fileName", new File(realFolder+fileModel.getFile_name()));
		mav.addObject("fileModel", fileModel);
		mav.setViewName("downloadView");
		//System.out.println(fileModel.getFile_name());
		return mav;
	}
}
