<%@page import="java.util.ArrayList"%>
<%@page import="model.board.BoardModel"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	List<BoardModel> boardList = (ArrayList)request.getAttribute("boardList");
	BoardModel boardModel = (BoardModel)request.getAttribute("boardModel");
	String searchType = boardModel.getSearchType();
	String searchText = boardModel.getSearchText();
	int pageNum = Integer.parseInt(boardModel.getPageNum());
	int listCount = boardModel.getListCount();
	int totalCount = (Integer)request.getAttribute("totalCount");
	String pageNavigator = (String)request.getAttribute("pageNavigator");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>게시판 목록</title>
<script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
<style type="text/css">
	* {font-size: 9pt;}
	p {width: 600px; text-align: right;}
	table thead tr th {background-color: gray;}
</style>
<script type="text/javascript">
	
	/* $("#list").on("click", function(){
		
	}); */
	
	function goUrl(url) {
		location.href=url;
	}
	// 검색 폼 체크
	function searchCheck() {
		var form = document.searchForm;
		if (form.searchText.value == '') {
			alert('검색어를 입력하세요.');
			form.searchText.focus();
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<form name="searchForm" action="boardList.jsp" method="get" onsubmit="return searchCheck();">
	<p>
		<select name="searchType">
			<option value="ALL" selected="selected">전체검색</option>
			<option value="SUBJECT" <%if ("SUBJECT".equals(searchType)) out.print("selected=\"selected\""); %>>제목</option>
			<option value="WRITER" <%if ("WRITER".equals(searchType)) out.print("selected=\"selected\""); %>>작성자</option>
			<option value="CONTENTS" <%if ("CONTENTS".equals(searchType)) out.print("selected=\"selected\""); %>>내용</option>
		</select>
		<input type="text" name="searchText" value="<%=searchText%>"/>
		<input type="submit" value="검색" />
	</p>
	</form>
	<table border="1" summary="게시판 목록">
		<caption>게시판 목록</caption>
		<colgroup>
			<col width="50"/>
			<col width="300" />
			<col width="80" />
			<col width="100" />
			<col width="70" />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>등록 일시</th>
				<th>조회수</th>
			</tr>
		</thead>
		<tbody>
			<% 
			if(totalCount == 0){
			%>
			<tr>
				<td align="center" colspan="5">등록된 게시물이 없습니다.</td>
			</tr>
			<%
			} else {
				//int i = 0;
				for(int i=0;i<boardList.size();i++){
					BoardModel boardModel2 = new BoardModel();
					boardModel2 = boardList.get(i);
					//i++;					
		    %>
			<tr>
				<td align="center"><%=totalCount - i +1 -(pageNum-1) *listCount%></td>
				<td><a href="boardViewServlet?num=<%=boardModel2.getNum()%>&amp;pageNum=<%=boardModel2.getPageNum()%>&amp;searchType=<%=searchType%>&amp;searchText=<%=searchText%>"><%=boardModel2.getSubject() %></a></td>
				<td align="center"><%=boardModel2.getWriter() %></td>
				<td align="center"><%=boardModel2.getReg_date().substring(0, 10) %></td>
				<td align="center"><%=boardModel2.getHit() %></td>
			</tr>
			<%
				}
			}
			%>
		</tbody>
		<tfoot>
			<tr>
				<td align="center" colspan="5">
				<!-- 페이지 네이 -->
				<%=pageNavigator%>
				</td>
			</tr>
		</tfoot>
	</table>
	<p>
		<!-- <input type="button" id="list" value="목록" />
		<input type="button" id="write" value="글쓰기" /> -->
		<input type="button" value="목록" onclick="goUrl('<%=request.getContextPath()%>/board/boardListServlet');" />
		<input type="button" value="글쓰기" onclick="goUrl('<%=request.getContextPath()%>/board/boardWriteServlet')" />
	</p>
</body>
</html>