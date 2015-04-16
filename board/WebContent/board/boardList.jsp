<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int pageNumTemp = 1;
	int listCount = 10;
	int pagePerBlock = 10;
	String whereSQL = "";
	
	String pageNum = request.getParameter("pageNum");
	String searchType = request.getParameter("searchType");
	String searchText = request.getParameter("searchText");
	//System.out.println("listST "+searchText);
		
		// 파라미터 초기화
		if(searchText == null) {
			searchType = "";
			searchText = ""; 
		}
		if(pageNum != null){
			pageNumTemp = Integer.parseInt(pageNum);
		}
		
		// 입력한 검색어를 utf-8인코딩
		//String searchTextUTF8 = new String(searchText.getBytes("ISO-8859-1"), "UTF-8");
		//System.out.println("searchTextUTF8 "+searchTextUTF8);
		
		if(!"".equals(searchText)){
			if("ALL".equals(searchType)){
				whereSQL = " WHERE SUBJECT LIKE CONCAT('%',?,'%') OR WRITER LIKE CONCAT('%',?,'%') OR CONTENTS LIKE CONCAT('%',?,'%') ";
			} else if("SUBJECT".equals(searchType)){
				whereSQL = " WHERE SUBJECT LIKE CONCAT('%',?,'%') ";
			} else if("WRITER".equals(searchType)){
				whereSQL = " WHERE WRITER LIKE CONCAT('%',?,'%') ";
			} else if("CONTENTS".equals(searchType)){
				whereSQL = " WHERE CONTENTS LIKE CONCAT('%',?,'%') ";
			}
			//System.out.println(whereSQL);
			//System.out.println(searchType +" / "+ searchText);
		}
		
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jshboard", "root", "into20");
		
		// DB에 저장된 게시글의 총 갯수를 구하는 쿼리문
		pstmt = conn.prepareStatement("SELECT COUNT(num) AS TOTAL FROM board" + whereSQL);
		if(!"".equals(whereSQL)){
			if("ALL".equals(searchType)){
				pstmt.setString(1, searchText);
				pstmt.setString(2, searchText);
				pstmt.setString(3, searchText);
			} else {
				pstmt.setString(1, searchText);
			}
		}
		rs = pstmt.executeQuery();
		rs.next();
		// 쿼리문에서 리턴된 TOTAL 값
		int totalCount = rs.getInt("TOTAL");
		
		// 게시물 목록을 얻는 쿼리
		pstmt = conn.prepareStatement("SELECT num, subject, writer, reg_date, hit FROM board "+whereSQL+" ORDER BY num DESC LIMIT ?, ?");
		if(!"".equals(whereSQL)){
			if("ALL".equals(searchType)){
				pstmt.setString(1, searchText);
				pstmt.setString(2, searchText);
				pstmt.setString(3, searchText);
				pstmt.setInt(4, listCount*(pageNumTemp-1));
				pstmt.setInt(5, listCount);
			} else {
				pstmt.setString(1, searchText);
				pstmt.setInt(2, listCount*(pageNumTemp-1));
				pstmt.setInt(3, listCount);
			}
		} else {
			//System.out.println(listCount * (pageNumTemp-1));
			pstmt.setInt(1, listCount * (pageNumTemp-1));
			pstmt.setInt(2, listCount);
		}
		rs = pstmt.executeQuery();
		
		/* while(rs.next()){
			rs.getString("num");
			rs.getString("subject");
			rs.getString("writer");
		}
		
	} catch(Exception err){
		err.printStackTrace();
	} finally{
		if(rs!=null){rs.close();}
		if(pstmt!=null){pstmt.close();}
		if(conn!=null){conn.close();}
	} */
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
				int i = 0;
				while(rs.next()){
					i++;					
		    %>
			<tr>
				<td align="center"><%=totalCount - i +1 -(pageNumTemp-1) *listCount%></td>
				<td><a href="boardView.jsp?num=<%=rs.getInt("num")%>&amp;pageNum=<%=pageNumTemp%>&amp;searchType=<%=searchType%>&amp;searchText=<%=searchText%>"><%=rs.getString("subject") %></a></td>
				<td align="center"><%=rs.getString("writer") %></td>
				<td align="center"><%=rs.getString("reg_date").substring(0, 10) %></td>
				<td align="center"><%=rs.getString("hit") %></td>
			</tr>
			<%
				}
			}
			%>
		</tbody>
		<tfoot>
			<tr>
				<td align="center" colspan="5">
				<% 
				// 페이지 네비게이터
				if(totalCount > 0){
					// 할당할 곳 = 비교문? 참일때값 : 거짓일때 값
					int totalNumOfPage = (totalCount % listCount == 0) ? 
							totalCount/listCount : 
							totalCount/listCount +1;
					
					int totalNumOfBlock = (totalNumOfPage % pagePerBlock == 0) ? 
							totalNumOfPage/pagePerBlock : 
							totalNumOfPage/pagePerBlock +1;
					
					int currentBlock = (pageNumTemp % pagePerBlock == 0) ?
							pageNumTemp/pagePerBlock :
							pageNumTemp/pagePerBlock +1;
					
					int startPage = (currentBlock-1) *pagePerBlock +1;
					int endPage = startPage + pagePerBlock -1;
					
					if(endPage > totalNumOfPage)
						endPage = totalNumOfPage;
					boolean isNext = false;
					boolean isPrev = false;
					if(currentBlock < totalNumOfBlock)
						isNext = true;
					if(currentBlock > 1)
						isPrev = true;
					if(totalNumOfBlock == 1){
						isNext = false;
						isPrev = false; 
					}
					StringBuffer sb = new StringBuffer();
					if(pageNumTemp > 1){
						sb.append("<a href=\"").append("boardList.jsp?pageNum=1&amp;searchType="+searchType+"&amp;searchText="+searchText);
						sb.append("\" title=\"<<\"><<</a>&nbsp;");
					} 
					if (isPrev){
						int goPrevPage = startPage - pagePerBlock;
						sb.append("&nbsp;&nbsp;<a href=\"").append("boardList.jsp?pageNum="+goPrevPage+"&amp;searchType="+searchType+"&amp;searchText="+searchText);
						sb.append("\" title==\"<\"><</a>");
					} else {
						
					}
					for(int i=startPage; i<=endPage; i++){
						if(i==pageNumTemp){
							sb.append("<a href=\"#\"><strong>").append(i).append("</strong></a>&nbsp;&nbsp;");
						} else {
							sb.append("<a href=\"").append("boardList.jsp?pageNum="+i+"&amp;searchType="+searchType+"&amp;searchText="+searchText);
							sb.append("\" title=\""+i+"\">").append(i).append("</a>&nbsp;&nbsp;");
						}
					}
					if(isNext){
						int goNextPage = startPage + pagePerBlock;
						
						sb.append("<a href=\"").append("boardLisr.jsp?pageNum="+goNextPage+"&amp;searchType="+searchType+"&amp;searchText="+searchText);
						sb.append("\" title=\">\">></a>");
					} else {
						
					}
					if(totalNumOfPage > pageNumTemp) {
						sb.append("&nbsp;&nbsp;<a href=\"").append("boardList.jsp?pageNum="+totalNumOfPage+"&amp;searchType="+searchType+"&amp;searchText="+searchText);
						sb.append("\" title=\">>\">>></a>");
					}
					out.print(sb.toString());
				}
				%>
				</td>
			</tr>
		</tfoot>
	</table>
	<p>
		<!-- <input type="button" id="list" value="목록" />
		<input type="button" id="write" value="글쓰기" /> -->
		<input type="button" value="목록" onclick="goUrl('boardList.jsp');" />
		<input type="button" value="글쓰기" onclick="goUrl('boardWriteForm.jsp');" />
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