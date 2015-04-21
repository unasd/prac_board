<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<form name="searchForm" action="${pageContext.request.contextPath}/spring/board/boardListServlet" method="get" onsubmit="return searchCheck();">
	<p>
		<select name="searchType">
			<option value="ALL" selected="selected">전체검색</option>
			<c:if test="${boardModel.searchType eq 'SUBJECT' }"></c:if>
			<option value="SUBJECT" <c:if test="${boardModel.searchType eq 'SUBJECT' }">selected="selected"</c:if>>제목</option>
			<option value="WRITER" <c:if test="${boardModel.searchType eq 'WRITER' }">selected="selected"</c:if>>작성자</option>
			<option value="CONTENTS" <c:if test="${boardModel.searchType eq 'CONTENTS' }">selected="selected"</c:if>>내용</option>
		</select>
		<input type="text" name="searchText" value="${boardModel.searchText}"/>
		<input type="submit" value="검색" />
	</p>
	</form>
	<table border="1" summary="게시판 목록">
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
			<c:choose>
				<c:when test="${totalCount eq 0 }">
					<tr>
						<td align="center" colspan="5">등록된 게시물이 없습니다.</td>
					</tr>
				</c:when>
			    <c:otherwise>
			    	<c:forEach items="${boardList }" var="bm" varStatus="status">
					<tr>
						<td align="center"><c:out value="${totalCount - (status.index+1) +1 -(boardModel.pageNum-1) * boardModel.listCount}"></c:out></td>
						<td><a href="boardViewServlet?num=${bm.num }&pageNum=${boardModel.pageNum }&searchType=${boardModel.searchType }&searchText=${boardModel.searchText}">${bm.subject }</a></td>
						<td align="center">${bm.writer }</td>
						<fmt:parseDate value="${bm.reg_date }" var="date" pattern="yyyy-MM-dd HH:mm:ss" />
						<%-- <td align="center"><fmt:formatDate value="${date}" type="both" dateStyle="short" timeStyle="short"/></td> --%>
						<td align="center" width="100"><fmt:formatDate value="${date}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
						<td align="center">${bm.hit }</td>
					</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
		<tfoot>
			<tr>
				<td align="center" colspan="5">
				<!-- 페이지 네비게이터 -->
				${pageNavigator }
				</td>
			</tr>
		</tfoot>
	</table>
	<p>
		<input type="button" value="목록" onclick="goUrl('${pageContext.request.contextPath }/spring/board/boardListServlet');" />
		<input type="button" value="글쓰기" onclick="goUrl('${pageContext.request.contextPath }/spring/board/boardWriteServlet')" />
	</p>
</body>
</html>