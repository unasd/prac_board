<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.board.BoardModel"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>게시판 수정 폼</title>
<script src="//cdn.ckeditor.com/4.4.7/standard/ckeditor.js"></script>
<style type="text/css">
	* {font-size: 9pt;}
	p {width: 600px; text-align: right;}
	table tbody tr th {background-color: gray;}
</style>
<script type="text/javascript">
	function goUrl(url) {
		location.href=url;
	}
	function boardModifyCheck() {
		var form = document.boardModifyForm;
		return true;
	}
</script>
</head>
<body>
	<form name="boardModifyForm" action="boardModifyServlet" method="post" onsubmit="return boardModifyCheck();">
	<!-- <input type="hidden" name="mode" value="M" /> -->
	<input type="hidden" name="num" value="${boardModel.num}" />
	<input type="hidden" name="pageNum" value="${boardModel.pageNum}" />
	<input type="hidden" name="searchType" value="${boardModel.searchType}" />
	<input type="hidden" name="searchText" value="${boardModel.searchText}" />
	<table border="1" summary="게시판 수정 폼">
		<caption>게시판 수정 폼</caption>
		<colgroup>
			<col width="100" />
			<col width="500" />
		</colgroup>
		<tbody>
			<tr>
				<th align="center">제목</th>
				<td><input type="text" name="subject" size="80" maxlength="100" value="${boardModel.subject}"/></td>
			</tr>
			<tr>
				<th align="center">작성자</th>
				<td><input type="text" name="writer" maxlength="20" value="${boardModel.writer}"/></td>
			</tr>
			<tr>
				<td colspan="2">
					<textarea name="contents" rows="10" cols="80">${boardModel.contents }</textarea>
					<script>
					CKEDITOR.replace('contents');
					</script>
				</td>
			</tr>
		</tbody>
	</table>
	<p>
		<input type="button" value="목록" onclick="goUrl('${pageContext.request.contextPath }/spring/board/boardListServlet?pageNum=${boardModel.pageNum }&searchType=${boardModel.searchType }&searchText=${boardModel.searchText }');" />
		<input type="submit" value="글수정" />
	</p>
	</form>
</body>
</html>
