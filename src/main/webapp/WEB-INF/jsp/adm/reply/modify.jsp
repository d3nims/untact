<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.sbs.untact.util.Util" %>

<%@ include file="../part/mainLayoutHead.jspf"%>


<script>
ReplyModify__submited = false;
function ReplyModify__checkAndSubmit(form) {
	if ( ReplyModify__submited ) {
		alert('처리중입니다.');
		return;
	}
	

	form.body.value = form.body.value.trim();

	if ( form.body.value.length == 0 ) {
		alert('내용을 입력해주세요.');
		form.body.focus();

		return false;
	}

	ReplyModify__submited = true;

</script>

<section class="section-1">
	<div class="bg-white shadow-md rounded container mx-auto p-8 mt-8">
		<form onsubmit="ReplyModify__checkAndSubmit(this); return false;" action="doModify" method="POST" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${reply.id}" />

			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>내용</span>
				</div>
				<div class="lg:flex-grow">
					<textarea name="body" class="form-row-input w-full rounded-sm" placeholder="내용을 입력해주세요.">${reply.body}</textarea>
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