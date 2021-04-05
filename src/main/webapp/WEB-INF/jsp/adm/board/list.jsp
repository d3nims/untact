<%@ page import="com.sbs.untact.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../part/mainLayoutHead.jspf"%>

<section class="section-1">
	<div class="bg-white shadow-md rounded container mx-auto p-8 mt-8">

		<div class="container mx-auto h-full flex m-0">
			<a class="text-4xl text-gray-700 font-bold">게시판 관리</a>
		</div>
		<div class="flex items-center">


			<div class="flex-grow"></div>

			<a href="add?boardId=${board.id}"
				class="btn-primary bg-green-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded">게시판
				생성</a>
		</div>


		<form class="flex mt-3">
			<select name="searchKeywordType">
				<option value="nameAndCode">전체</option>
				<option value="name">게시판 이름</option>
				<option value="code">게시판 유형</option>
			</select>
			<script>
				if (param.searchKeywordType) {
					$('.section-1 select[name="searchKeywordType"]').val(
							param.searchKeywordType);
				}
			</script>
			<input
				class="ml-3 shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker"
				name="searchKeyword" type="text" placeholder="검색어를 입력해주세요."
				value="${param.searchKeyword}" />
			<input
				class="ml-3 btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded"
				type="submit" value="검색" />
		</form>

		<div>
			<c:forEach items="${boards}" var="board">
				<c:set var="detailUrl" value="../article/list?boardId=${board.id}" />
				<c:set var="listUrl" value="../article/list?boardId=${board.id}" />
				<c:set var="modifyUrl" value="modify?id=${board.id}" />
				<c:set var="doDeleteUrl" value="doDelete?id=${board.id}" />
				<div>
					<div class="flex items-center mt-10">
						<a href="${listUrl}" class="font-bold">NO. ${board.id}</a>
						<a href="${listUrl}" class="ml-2 font-light text-gray-600">${board.regDate}</a>
						<div class="flex-grow"></div>
					</div>
					
					<div class="mt-2">
					<a href="${detailUrl}" class="text-2xl text-gray-700 font-bold hover:underline">${board.name}</a>
					<c:if test="${thumbUrl != null}">

						<a class="block" href="${detailUrl}">
							<img class="max-w-sm" src="${thumbUrl}" alt="" />
						</a>
					</c:if>
					<a href="${detailUrl}" class="mt-2 text-gray-600 block">${board.code}</a>
				</div>
					
					<div class="flex items-center mt-4">
						<a href="${detailUrl}" class="text-blue-500 hover:underline">
							<span>
								<i class="fas fa-info"></i>
								<span class="hidden sm:inline">자세히 보기</span>
							</span>
						</a>
						<a href="${modifyUrl}" class="ml-2 text-green-500 hover:underline">
							<span>
								<i class="fas fa-edit"></i>
								<span class="hidden sm:inline">수정</span>
							</span>
						</a>
						<a onclick="if ( !confirm('삭제하시겠습니까?') ) return false;" href="${doDeleteUrl}" class="ml-2 text-red-500 hover:underline">
							<span>
								<i class="fas fa-trash"></i>
								<span class="hidden sm:inline">삭제</span>
							</span>
						</a>
						<div class="flex-grow"></div>
					</div>
				</div>
			</c:forEach>
		</div>

		<nav class="flex justify-center rounded-md shadow-sm mt-3"
			aria-label="Pagination">
			<c:if test="${pageMenuStart != 1}">
				<a href="${Util.getNewUrl(requestUrl, 'page', 1)}"
					class="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
					<span class="sr-only">Previous</span>
					<i class="fas fa-chevron-left"></i>
				</a>
			</c:if>

			<c:forEach var="i" begin="${pageMenuStart}" end="${pageMenuEnd}">
				<c:set var="aClassStr"
					value="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-white text-sm font-medium" />
				<c:if test="${i == page}">
					<c:set var="aClassStr"
						value="${aClassStr} text-red-700 hover:bg-red-50" />
				</c:if>
				<c:if test="${i != page}">
					<c:set var="aClassStr"
						value="${aClassStr} text-gray-700 hover:bg-gray-50" />
				</c:if>
				<a href="${Util.getNewUrl(requestUrl, 'page', i)}"
					class="${aClassStr}">${i}</a>
			</c:forEach>

			<c:if test="${pageMenuEnd != totalPage}">
				<a href="${Util.getNewUrl(requestUrl, 'page', totalPage)}"
					class="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">

					<span class="sr-only">Next</span>
					<i class="fas fa-chevron-right"></i>
				</a>
			</c:if>
		</nav>
	</div>

</section>

<%@ include file="../part/mainLayoutFoot.jspf"%>