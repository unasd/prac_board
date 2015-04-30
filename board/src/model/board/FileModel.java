package model.board;

import org.springframework.web.multipart.MultipartFile;

public class FileModel {
	private MultipartFile uploadfile;
	private String file_name;
	private String origin_file_name;
	private int file_num;
	private long file_size;
	private int article_num;
	
	public MultipartFile getUploadFile() {
		return uploadfile;
	}
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadfile = uploadFile;
	}
	public MultipartFile getUploadfile() {
		return uploadfile;
	}
	public void setUploadfile(MultipartFile uploadfile) {
		this.uploadfile = uploadfile;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getOrigin_file_name() {
		return origin_file_name;
	}
	public void setOrigin_file_name(String origin_file_name) {
		this.origin_file_name = origin_file_name;
	}
	public int getFile_num() {
		return file_num;
	}
	public void setFile_num(int file_num) {
		this.file_num = file_num;
	}
	public long getFile_size() {
		return file_size;
	}
	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}
	public int getArticle_num() {
		return article_num;
	}
	public void setArticle_num(int article_num) {
		this.article_num = article_num;
	}
}
