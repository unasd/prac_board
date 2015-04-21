package model.board;

public class CommentModel {
	String comment_writer;
	String comment_content;
	String write_date;
	int linked_article_num;
	String idx;
	
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getComment_writer() {
		return comment_writer;
	}
	public void setComment_writer(String comment_writer) {
		this.comment_writer = comment_writer;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public int getLinked_article_num() {
		return linked_article_num;
	}
	public void setLinked_article_num(int linked_article_num) {
		this.linked_article_num = linked_article_num;
	}
}
