<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../part/mainLayoutHead.jspf"%>


<script>
BoardAdd__submited = false;
function BoardAdd__checkAndSubmit(form) {
	if ( BoardAdd__submited ) {
		alert('처리중입니다.');
		return;
	}
	
	form.name.value = form.name.value.trim();

	if ( form.name.value.length == 0 ) {
		alert('게시판 이름을 입력해주세요.');
		form.name.focus();

		return false;
	}

	form.code.value = form.code.value.trim();

	if ( form.code.value.length == 0 ) {
		alert('게시판 코드를 입력해주세요.');
		form.code.focus();

		return false;
	}

}



	BoardAdd__submited = true;

}
</script>

<section class="section-1">
	<div class="bg-white shadow-md rounded container mx-auto p-8 mt-8">
		<form onsubmit="BoardAdd__checkAndSubmit(this); return false;" action="doAdd" method="POST" enctype="multipart/form-data">
			<input type="hidden" name="boardId" value="${param.boardId}" />
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>게시판 이름</span>
				</div>
				<div class="lg:flex-grow">
					<input type="text" name="name" autofocus="autofocus"
						class="form-row-input w-full rounded-sm" placeholder="게시판 이름을 입력해주세요." />
				</div>
			</div>
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>게시판 코드</span>
				</div>
				<div class="lg:flex-grow">
					<input type="text" name="code" autofocus="autofocus"
						class="form-row-input w-full rounded-sm" placeholder="게시판 코드를 입력해주세요." />
				</div>
			</div>
			
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>작성</span>
				</div>
				<div class="lg:flex-grow">
					<div class="btns">
						<input type="submit" class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded" value="작성">
						<input onclick="history.back();" type="button" class="btn-info bg-red-500 hover:bg-red-dark text-white font-bold py-2 px-4 rounded" value="취소">
					</div>
				</div>
			</div>
		</form>
	</div>
</section>

<%@ include file="../part/mainLayoutFoot.jspf"%>