package dao.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.board.FileModel;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

public class DownLoadImple extends AbstractView {
	/*public DownLoadImple(){
		System.out.println("downloadimple create");
	}*/

	@Override
	protected void renderMergedOutputModel(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String fileName=null;
		File file = (File)map.get("fileName");
		FileModel fileModel = (FileModel)map.get("fileModel");
		
		response.setContentType("application/download;");
		int length = (int)file.length();
		response.setContentLength(length);
		
		String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MISE") > -1;
		
		if(ie) {
			fileName = URLEncoder.encode(file.getName(), "utf-8").replace("+", "%20");
		} else {
			fileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1").replace("+", "%20");
		}
		
		response.setHeader("Content-Disposition", "attachment;"+ "filename=\"" + fileModel.getOrigin_file_name() + "\";");
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		
		try{
			int temp;
			fis = new FileInputStream(file);
			while((temp = fis.read()) != -1){
				out.write(temp);
			}
		} catch(Exception err){
			err.printStackTrace();
		} finally {
			if(fis != null){
				try{
					fis.close();
				} catch(Exception err) {
					err.printStackTrace();
				}
			}
		}
	}
}
