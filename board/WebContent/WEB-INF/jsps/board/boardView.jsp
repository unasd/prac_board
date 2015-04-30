<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
	function goUrl(url) {
		location.href=url;
	}
	
	function bytesToSize(bytes){
		
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
				<td>${boardModel.subject }</td>
			</tr>
			<tr>
				<th align="center">작성자/조회수</th>
				<td>${boardModel.writer } / ${boardModel.hit }</td>
			</tr>
			<tr>
				<th align="center">등록 일시</th>
				<fmt:parseDate value="${boardModel.reg_date }" var="date" pattern="yyyy-MM-dd HH:mm:ss" />
				<td><fmt:formatDate value="${date}" pattern="yyyy년 MM월 dd일 HH:mm:ss"/></td>
			</tr>
			<tr>
				<th align="center">첨부파일</th>
			<c:choose>
				<c:when test="${fileList.size() == 1 }">
					<c:set var="fileModel" value="${fileList[0] }"/>
					<td><a href="/board/spring/download?file_name=${fileModel.file_name }&origin_file_name=${fileModel.origin_file_name}">${fileModel.origin_file_name }</a> (${fileModel.file_size }&nbsp;bytes)</td>
				</c:when>
				<c:when test="${fileList.size() > 1 }">
					<td>
						<c:forEach items="${fileList }" var="fileModel" varStatus="status">
							<a href="/board/spring/download?file_name=${fileModel.file_name }&origin_file_name=${fileModel.origin_file_name}">${fileModel.origin_file_name }</a> (${fileModel.file_size }&nbsp;bytes)<br/>
						</c:forEach>
					</td>
				</c:when>
				<c:otherwise>
					<td>없음</td>
				</c:otherwise>
			</c:choose>
			</tr>
			<tr>
				<td colspan="2">${boardModel.contents }</td>
			</tr>
		</tbody>
	</table>
	<p class="btn_align">
		<input type="button" value="목록" onclick="goUrl('${pageContext.request.contextPath }/spring/board/boardListServlet?pageNum=${boardModel.pageNum }&searchType=${boardModel.searchType }&searchText=${boardModel.searchText }');" />
		<input type="button" value="수정" onclick="goUrl('${pageContext.request.contextPath }/spring/board/boardModifyServlet?num=${boardModel.num }&amp;pageNum=${boardModel.pageNum }&amp;searchType=${boardModel.searchType }&amp;searchText=${boardModel.searchText }');" />
		<input type="button" value="삭제" onclick="goUrl('${pageContext.request.contextPath }/spring/board/boardDeleteServlet?num=${boardModel.num }&amp;pageNum=${boardModel.pageNum }&amp;searchType=${boardModel.searchType }&amp;searchText=${boardModel.searchText }');" />
	</p>
	
	<!-- ################################## 댓글코드 ########################################## -->
	<c:if test="${fn:length(commentList) > 0 }">
	<table border="1" class="commentView" style="width: 536px">
			<tr>
				<th colspan="4">댓글 ${fn:length(commentList) }</th>
			</tr>		
			<c:forEach items="${commentList }" var="commentModel">
			<tr>
				<td>${commentModel.comment_writer }</td>
				<td class="content" align="left">
					<div style="width: 300px;">${commentModel.comment_content }</div>
				</td>
				<td>
					<fmt:parseDate value="${commentModel.write_date }" var="commentDate" pattern="yyyy-MM-dd HH:mm:ss" />
					<fmt:formatDate value="${commentDate}" pattern="yy/MM/dd HH:mm:ss"/>
				</td>
				<td>
					<input type="button" name="delButton" value="삭제"/>	
					<form action="commentDelete" method="post" id="commentDelForm">
						<input type="hidden" name="idx" value="${commentModel.idx }"/>			
					 	<input type="hidden" id="num" name="num" value="${boardModel.num}" />
						<input type="hidden" id="pageNum" name="pageNum" value="${boardModel.pageNum}" />
						<input type="hidden" id="searchType" name="searchType" value="${boardModel.searchType}" />
						<input type="hidden" id="searchText" name="searchText" value="${boardModel.searchText}" />
					</form>
				</td>
			</tr>
		    </c:forEach>
	</table>
	</c:if>
	<br/>
	<table border="1" style="width: 536px">
		<tr>
			<th colspan="3">댓글쓰기</th>
		</tr>	
		<tr>
			<!-- <td class="content"> -->
				<form action="commentWrite" method="post" id="commentForm">
				<td style="width: 68px"><input type="text" id="writer" name="comment_writer" style="width: 68px; size: 68px;" value="작성자"/></td>
					<input type="hidden" id="num" name="num" value="${boardModel.num}" />
					<input type="hidden" id="pageNum" name="pageNum" value="${boardModel.pageNum}" />
					<input type="hidden" id="searchType" name="searchType" value="${boardModel.searchType}" />
					<input type="hidden" id="searchText" name="searchText" value="${boardModel.searchText}" />
					<input type="hidden" id="linkedArticleNum" name="linked_article_num" value="${boardModel.num}" />
					<td><textarea id="content" name="comment_content" style="width: 394px; height: 42px"></textarea></td>
					<td><input type="submit" value="확인" class="commentBt" align="middle"/></td>
				</form>
		<!-- 	</td> -->
		</tr>
	</table>
</body>
<script>
	/* $("#commentForm").submit(function(event){
		event.preventDefault();
	}) */
	
	$("#writer").focus(function(){
		$("#writer").val('');
	})
	
	$("[name=delButton]").on("click", function(e){
		e.preventDefault();
		if(confirm("댓글을 삭제하시겠습니까?")){
			$("#commentDelForm").submit();
		}
	})
</script>
</html>