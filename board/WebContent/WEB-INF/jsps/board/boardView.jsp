<%@page import="model.board.BoardModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	BoardModel boardModel = (BoardModel)request.getAttribute("boardModel");
	//System.out.println("boardView.jsp searchText : "+boardModel.getSearchText());
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
				<td><%=boardModel.getSubject() %></td>
			</tr>
			<tr>
				<th align="center">작성자/조회수</th>
				<td><%=boardModel.getWriter()%> / <%=boardModel.getHit() %></td>
			</tr>
			<tr>
				<th align="center">등록 일시</th>
				<td><%=boardModel.getReg_date() %></td>
			</tr>
			<tr>
				<td colspan="2"><%=boardModel.getContents() %></td>
			</tr>
		</tbody>
	</table>
	<p class="btn_align">
		<input type="button" value="목록" onclick="goUrl('<%=request.getContextPath()%>/spring/board/boardListServlet?pageNum=<%=boardModel.getPageNum()%>&searchType=<%=boardModel.getSearchType()%>&searchText=<%=boardModel.getSearchText() %>');" />
		<input type="button" value="수정" onclick="goUrl('<%=request.getContextPath()%>/spring/board/boardModifyServlet?num=<%=boardModel.getNum()%>&amp;pageNum=<%=boardModel.getPageNum()%>&amp;searchType=<%=boardModel.getSearchType()%>&amp;searchText=<%=boardModel.getSearchText() %>');" />
		<input type="button" value="삭제" onclick="goUrl('<%=request.getContextPath()%>/spring/board/boardDeleteServlet?num=<%=boardModel.getNum()%>&amp;pageNum=<%=boardModel.getPageNum()%>&amp;searchType=<%=boardModel.getSearchType()%>&amp;searchText=<%=boardModel.getSearchText() %>');" />
	</p>
</body>
</html>