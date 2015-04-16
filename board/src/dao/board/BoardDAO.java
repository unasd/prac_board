package dao.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.Closeable;

import model.board.BoardModel;

public class BoardDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	String jdbcDriver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://127.0.0.1:3306/jshboard";
	String user = "root";
	String password = "into20";
	
	/***********************게시판 리스트*******************************/
	public List<BoardModel> selectList (BoardModel boardModel){
		List<BoardModel> boardList = null;
		int pageNum = Integer.parseInt(boardModel.getPageNum());
		int listCount = boardModel.getListCount();
		// BoardListServlet에서 셋팅된 값
		String searchType = boardModel.getSearchType();
		String searchText = boardModel.getSearchText();
		String whereSQL = "";
		
		if(!searchText.equals("")){  // 검색어가 빈 값이 아닐 때
			if(searchType.equals("ALL")){
				whereSQL = " WHERE subject LIKE CONCAT('%', ?, '%')"
							+" OR writer LIKE CONCAT('%', ? '%')"
							+" OR contents LIKE CONCAT('%', ? '%') ";
			} else if (searchType.equals("SUBJECT")){
				whereSQL = " WHERE subject LIKE CONCAT('%', ? '%') ";
			} else if (searchType.equals("WRITER")){
				whereSQL = " WHERE writer LIKE CONCAT('%', ? '%') ";
			} else if (searchType.equals("CONTENTS")){
				whereSQL = " WHERE contents LIKE CONCAT('%', ? '%') ";
			} 
		}
		
		try{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(url, user, password);
			
			pstmt = conn.prepareStatement("SELECT num, subject, writer, reg_date, hit FROM board "+whereSQL+" ORDER BY num DESC LIMIT ?, ?");
			if(!whereSQL.equals("")){
				if(searchType.equals("ALL")){
					pstmt.setString(1, searchText); // 제목에 검색어 포함된것
					pstmt.setString(2, searchText); // 글쓴이에 검색어 포함된것
					pstmt.setString(3, searchText);
					pstmt.setInt(4, listCount * (pageNum-1));
					pstmt.setInt(5, listCount);
				} else {
					pstmt.setString(1, searchText);
					pstmt.setInt(2, listCount * (pageNum-1));
					pstmt.setInt(3, listCount);				
				} 
			} else {
				pstmt.setInt(1, listCount * (pageNum-1));
				pstmt.setInt(2, listCount);	
			}
			rs = pstmt.executeQuery();
			boardList = new ArrayList<BoardModel>();
			while(rs.next()){
				boardModel = new BoardModel();
				boardModel.setNum(rs.getInt("num"));
				boardModel.setSubject(rs.getString("subject"));
				boardModel.setWriter(rs.getString("writer"));
				boardModel.setReg_date(rs.getString("reg_date"));
				boardModel.setHit(rs.getInt("hit"));
				boardList.add(boardModel);
			}
		}catch(Exception err){
			err.printStackTrace();
		}finally{
			close(this.rs, this.pstmt, this.conn);
		}
		return boardList;
	}
	
	/***********************전체게시글 개수*******************************/
	public int selectCount (BoardModel boardModel){
		int totalCount=0;
		String searchType = boardModel.getSearchType();
		String searchText = boardModel.getSearchText();
		String whereSQL ="";
		
		if(!searchText.equals("")){
			if(searchType.equals("ALL")){
				whereSQL = " WHERE subject LIKE CONCAT('%', ?, '%')"
							+" OR writer LIKE CONCAT('%', ?, '%')"
							+" OR contents LIKE CONCAT('%', ?, '%') ";
			} else if(searchType.equals("subject")){
				whereSQL = " WHERE subject LIKE CONCAT('%', ?, '%')";
			} else if(searchType.equals("writer")){
				whereSQL = " WHERE writer LIKE CONCAT('%', ?, '%')";
			} else if(searchType.equals("contents")){
				whereSQL = " WHERE contents LIKE CONCAT('%', ?, '%')";
			}
		}
		
		try{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(url, user, password);
			
			pstmt = conn.prepareStatement("SELECT COUNT(num) AS total FROM board "+whereSQL);
			if(!whereSQL.equals("")){
				if(searchType.equals("ALL")){
				pstmt.setString(1, searchText);
				pstmt.setString(2, searchText);
				pstmt.setString(3, searchText);
				} else {
					pstmt.setString(1, searchText);
				}
			}
			rs = pstmt.executeQuery();
			if(rs.next()){
				totalCount = rs.getInt("total");
			};
		} catch(Exception err){
			err.printStackTrace();
		} finally{
			close(rs, pstmt, conn);
		}
		return totalCount;
	}
	
	/***********************게시글 한 개 조회*******************************/
	public BoardModel select (BoardModel boardModel) {
		try{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(url, user, password);
			
			pstmt = conn.prepareStatement("SELECT * FROM board WHERE num=?");
			pstmt.setInt(1, boardModel.getNum());
			rs = pstmt.executeQuery();
			rs.next();
			boardModel.setSubject(rs.getString("subject"));
			boardModel.setWriter(rs.getString("writer"));
			boardModel.setContents(rs.getString("contents"));
			boardModel.setHit(Integer.parseInt(rs.getString("hit")));
			boardModel.setIp(rs.getString("ip"));
			boardModel.setContents(rs.getString("contents"));
			boardModel.setReg_date(rs.getString("reg_date"));
			boardModel.setMod_date(rs.getString("mod_date"));
		}catch(Exception err){
			err.printStackTrace();
		}finally{
			close(this.rs, this.pstmt, this.conn);
		}
		return boardModel;
	}

	public void insert (BoardModel boardModel){
		try{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(url, user, password);
			
			pstmt = conn.prepareStatement("INSERT INTO board(subject, writer, contents, hit, ip, reg_date, mod_date)"
					+ " VALUES(?, ?, ?, 0, ?, now(), now())");
			pstmt.setString(1, boardModel.getSubject());
			pstmt.setString(2, boardModel.getWriter());
			pstmt.setString(3,  boardModel.getContents());
			pstmt.setString(4, boardModel.getIp());
			
			pstmt.executeUpdate();
		} catch (Exception err){
			err.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
	}
	
	public void update (BoardModel boardModel){
		try{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(url, user, password);
			
			pstmt = conn.prepareStatement("UPDATE board SET subject=?, writer=?, contents=?,"
					+ " ip=?, mod_date=now() WHERE num=?");
			pstmt.setString(1, boardModel.getSubject());
			pstmt.setString(2, boardModel.getWriter());
			pstmt.setString(3, boardModel.getContents());
			pstmt.setString(4, boardModel.getIp());
			pstmt.setInt(5, boardModel.getNum());
			
			pstmt.executeUpdate();
		} catch(Exception err){
			err.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
	}
	
	public void updateHit (BoardModel boardModel){
		try{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(url, user, password);
			
			pstmt = conn.prepareStatement("UPDATE board SET hit=hit+1 WHERE num=?");
			pstmt.setInt(1, boardModel.getNum());
			
			pstmt.executeUpdate();
		} catch(Exception err){
			err.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
	}
	
	public void delete (BoardModel boardModel){
		try{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(url, user, password);
			
			pstmt = conn.prepareStatement("DELETE FROM board WHERE num=?");
			pstmt.setInt(1, boardModel.getNum());
			pstmt.executeUpdate();
			
		} catch(Exception err){
			err.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
	}
	
	private void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		if(rs!=null)try{rs.close();}catch(SQLException err){err.printStackTrace();}
		if(pstmt!=null)try{pstmt.close();}catch(SQLException err){err.printStackTrace();}
		if(conn!=null)try{conn.close();}catch(SQLException err){err.printStackTrace();}
	}
}
