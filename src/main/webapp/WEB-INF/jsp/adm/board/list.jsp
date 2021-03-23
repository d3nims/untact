<%@ page import="com.sbs.untact.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../part/mainLayoutHead.jspf"%>

<script>
	param.boardId = parseInt("${board.id}");
</script>

<section class="section-1">
	<div class="bg-white shadow-md rounded container mx-auto p-8 mt-8">
		<c:forEach items="${boards}" var="boards">
			<div>
				${board.id}
			</div>
		</c:forEach>
	</div>
	<div class="flex items-center mt-4">
		<a href="detail?id=${board.id}" class="text-blue-500 hover:underline">자세히 보기</a>
		<a href="modify?id=${board.id}" class="ml-2 text-blue-500 hover:underline">수정</a>
		<a onclick="if ( !confirm('삭제하시겠습니까?') ) return false;" href="doDelete?id=${board.id}" class="ml-2 text-blue-500 hover:underline">삭제</a>
		<div class="flex-grow"></div>
		<div>
			<a class="flex items-center">
							
				<img src="https://images.unsplash.com/photo-1492562080023-ab3db95bfbce?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=crop&amp;w=731&amp;q=80" alt="avatar" class="mx-4 w-10 h-10 object-cover rounded-full">
							
			</a>
		</div>
	</div>
</section>

<%@ include file="../part/mainLayoutFoot.jspf"%>