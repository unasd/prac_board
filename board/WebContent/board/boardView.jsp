<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String num = request.getParameter("num");
	//System.out.println(num);
	String pageNum = request.getParameter("pageNum");
	String searchType = request.getParameter("searchType");
	String searchText = request.getParameter("searchText");
	//System.out.println("viewST "+searchText);
	
	
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jshboard", "root", "into20");
		
		pstmt = conn.prepareStatement("UPDATE board SET hit=hit+1 WHERE num=?");
		pstmt.setString(1, num);
		pstmt.executeUpdate();
		
		pstmt = conn.prepareStatement("SELECT * FROM board WHERE num=?");
		pstmt.setString(1, num);
		rs = pstmt.executeQuery();
		rs.next();
		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>게시판 상세보기</title>
<style type="text/css">
	* {font-size: 9pt;}
	p {width: 600px; text-align: right;}
	table tbody tr th {background-color: gray;}
</style>
<script type="text/javascript">
	function goUrl(url) {
		location.href=url;
	}
</script>
</head>
<body>
	<table border="1" summary="게시판 상세조회">
		<caption>게시판 상세조회</caption>
		<colgroup>
			<col width="100" />
			<col width="500" />
		</colgroup>
		<tbody>
			<tr>
				<th align="center">제목</th>
				<td><%=rs.getString("subject") %></td>
			</tr>
			<tr>
				<th align="center">작성자/조회수</th>
				<td><%=rs.getString("writer")%> / <%=rs.getString("hit") %></td>
			</tr>
			<tr>
				<th align="center">등록 일시</th>
				<td><%=rs.getString("REG_DATE") %></td>
			</tr>
			<tr>
				<td colspan="2"><%=rs.getString("contents") %></td>
			</tr>
		</tbody>
	</table>
	<p class="btn_align">
		<input type="button" value="목록" onclick="goUrl('boardList.jsp?pageNum=<%=pageNum%>&searchType=<%=searchType%>&searchText=<%=searchText %>');" />
		<input type="button" value="수정" onclick="goUrl('boardModifyForm.jsp?num=<%=num%>&amp;pageNum=<%=pageNum%>&amp;searchType=<%=searchType%>&amp;searchText=<%=searchText %>');" />
		<input type="button" value="삭제" onclick="goUrl('boardProcess.jsp?num=<%=num%>&amp;pageNum=<%=pageNum%>&amp;searchType=<%=searchType%>&amp;searchText=<%=searchText %>&amp;mode=D');" />
	</p>
</body>
</html>

<%
	} catch(Exception err){
		err.printStackTrace();
	} finally{
		if(rs!=null){rs.close();}
		if(pstmt!=null){pstmt.close();}
		if(conn!=null){conn.close();}
	}
%>