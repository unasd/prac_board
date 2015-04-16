<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%
	request.setCharacterEncoding("UTF-8");

	Connection conn = null;
	PreparedStatement pstmt = null;

	String mode = request.getParameter("mode");
	String subject = request.getParameter("subject");
	String writer = request.getParameter("writer");
	String contents = request.getParameter("contents");
	String num = request.getParameter("num");
	String ip = request.getRemoteAddr();
	// 리스트페이지로 되돌아 갈 때 사용될 값
	String pageNum = request.getParameter("pageNum");
	String searchType = request.getParameter("searchType");
	String searchText = request.getParameter("searchText");
	
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jshboard", "root", "into20");
		
		// 처리 (W:등록, M:수정, D:삭제)
		if("W".equals(mode)){
			pstmt = conn.prepareStatement("INSERT INTO board (subject, writer, contents, ip, hit, reg_date, mod_date) "
														+"VALUES(?, ?, ?, ?, 0, now(), now())");
			pstmt.setString(1, subject);
			pstmt.setString(2, writer);
			pstmt.setString(3, contents);
			pstmt.setString(4, ip);
			pstmt.executeUpdate();
			
			response.sendRedirect("boardList.jsp");
		} else if("M".equals(mode)){
			//System.out.println("process "+num);
			pstmt = conn.prepareStatement("UPDATE board SET subject=?, writer=?, contents=?, ip=?, mod_date=now() WHERE num=?");
			
			pstmt.setString(1, subject);
			pstmt.setString(2, writer);
			pstmt.setString(3, contents);
			pstmt.setString(4, ip);
			pstmt.setString(5, num);
			pstmt.executeUpdate();
			
			response.sendRedirect("boardView.jsp?num="+num+"&pageNum="+pageNum
									+"&searchType="+searchType+"&searchText="+searchText);
		} else if("D".equals(mode)){
			//System.out.println("del "+searchText);
			pstmt = conn.prepareStatement("DELETE FROM board WHERE NUM=?");
			pstmt.setString(1, num);
			pstmt.executeUpdate();
			
			response.sendRedirect("boardList.jsp?pageNum="+pageNum+"&searchType="
									+searchType+"&searchText="+searchText);
		}
		
		
	}catch(Exception err){
		err.printStackTrace();
	}finally {
		if(pstmt!=null) pstmt.close();
		if(conn!=null) conn.close();
	}
%>

