<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.sbs.untact.util.Util" %>

<%@ include file="../part/mainLayoutHead.jspf"%>

<script>
const boardId = parseInt("${board.id}");
</script>
<script>

	BoardModify__submited = false;
		form.name.value = form.name.value.trim();
	
		if ( form.name.value.length == 0 ) {
			alert('게시판 이름을 입력해주세요.');
			form.name.focus();
	
			return false;
		}
	
	
		BoardModify__submited = true;
	
	}
	

</script>

<section class="section-1">
	<div class="bg-white shadow-md rounded container mx-auto p-8 mt-8">
		<form onsubmit="BoardModify__checkAndSubmit(this); return false;" action="doModify" method="POST" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${board.id}" />
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>게시판 제목</span>
				</div>
				<div class="lg:flex-grow">
					<input value="${board.name}" type="text" name="name" autofocus="autofocus"
						class="form-row-input w-full rounded-sm" placeholder="게시판 이름을 입력해주세요." />
				</div>
			</div>
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>게시판 코드</span>
				</div>
				<div class="lg:flex-grow">
					${board.code}
				</div>
			</div>
			<div class="form-row mt-2 flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>수정</span>
				</div>
				<div class="lg:flex-grow">
					<div class="btns">
						<input type="submit" class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded" value="수정">
						<input onclick="history.back();" type="button" class="btn-info bg-red-500 hover:bg-red-dark text-white font-bold py-2 px-4 rounded" value="취소">
					</div>
				</div>
			</div>
		</form>
	</div>
</section>

<%@ include file="../part/mainLayoutFoot.jspf"%>