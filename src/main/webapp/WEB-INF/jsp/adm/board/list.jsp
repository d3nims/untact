<%@ page import="com.sbs.untact.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../part/mainLayoutHead.jspf"%>


<section class="section-1">
	<div class="bg-white shadow-md rounded container mx-auto p-8 mt-8">
		<c:forEach items="${boards}" var="boards">
			<c:set var="detailUrl" value="detail?id=${board.id }" />
			<div class=flex items-center mt-10>
				<a href="${detailUrl}" class="font-bold">NO. ${board.id}</a>
				<a href="${detailUrl}" class="ml-2 font-light text-gray-600">${board.regDate}</a>
			</div>
			<div class="mt-2">
				<a href="${detailUrl}" class="text-2xl text-gray-700 font-bold hover:underline">${board.code}</a>
				<c:if test="${thumbUrl != null}">
					<a class="block" href="${detailUrl}">
						<img class="max-w-sm" src="${thumbUrl}" alt="" />
					</a>
				</c:if>
					<a href="${detailUrl}" class="mt-2 text-gray-600 block">${board.name}</a>
			</div>
				<div class="flex items-center mt-4">
					<a href="detail?id=${board.id}" class="text-blue-500 hover:underline">자세히 보기</a>
				</div>
		</c:forEach>
	</div>
	
</section>

<%@ include file="../part/mainLayoutFoot.jspf"%>