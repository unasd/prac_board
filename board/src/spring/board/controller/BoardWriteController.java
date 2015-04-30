package spring.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import model.board.BoardModel;
import model.board.FileModel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
	public String insert(HttpServletRequest request, MultipartHttpServletRequest mrequest, BoardModel boardModel){
		//System.out.println("writeServlet Post");
		String ip = request.getRemoteAddr();
		
		boardModel.setIp(ip);
		boardDAO.insert(boardModel);
		
		List<MultipartFile> fileList = mrequest.getFiles("uploadfile");
		this.fileUpload(fileList, mrequest, boardModel);
		
		return "redirect:boardListServlet";
	}
	
	
	/*
	 *  업로드 파일의 정보를 fileModel에 매핑
	 */
	private void fileUpload(List<MultipartFile> fileList, MultipartHttpServletRequest mrequest, BoardModel boardModel){
		if(fileList.size()>0 && fileList.get(0).getOriginalFilename() != ""){
			for(MultipartFile uploadFile : fileList){
				FileModel fileModel = new FileModel();
				String fileName = uploadFile.getOriginalFilename();
				String genId = UUID.randomUUID().toString();
				String saveFileName = genId +"."+fileName;
				fileModel.setArticle_num(boardModel.getNum());
				fileModel.setOrigin_file_name(fileName);
				fileModel.setFile_name(saveFileName);
				fileModel.setFile_size(uploadFile.getSize());
				
				boardDAO.fileUpload(fileModel);
				try{
					File file = new File("C:/JSH/prac_board/upFiles/" + saveFileName);
					uploadFile.transferTo(file);
				} catch(Exception err){
					err.printStackTrace();
				}
			}
		}
	}
}
