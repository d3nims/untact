<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.sbs.untact.util.Util"%>

<%@ include file="../part/mainLayoutHead.jspf"%>

<c:set var="fileInputMaxCount" value="10" />

<%@ include file="../part/head.jspf"%>
<script>

	const id = parseInt('${article.id}');
	const relTypeCode = String('${article.relTypeCode}');
</script>

<section class="section-1">
	<div class="bg-white shadow-md rounded container mx-auto p-8 mt-8">
		<div class="w-full">
			<div class="flex flex-row mt-2 py-3">
				<div class="rounded-full border-2 border-pink-500">
					<img class="w-12 h-12 object-cover rounded-full shadow cursor-pointer" alt="User avatar" src="${article.writerThumbImgUrl}">
				</div>
				<div class="flex flex-col mb-2 ml-4 mt-1">
					<div class="text-gray-600 text-sm font-semibold">${article.extra__writer}</div>
					<div class="flex w-full mt-1">
						${article.extra__boardName}
						<div class="text-gray-400 font-thin text-xs">${article.regDate}</div>
					</div>
				</div>
			</div>
			<div class="border-b border-gray-100"></div>
			<div class="text-gray-400 font-medium text-sm mb-7 mt-6">
				<c:forEach begin="1" end="${fileInputMaxCount}" var="inputNo">
					<c:set var="fileNo" value="${String.valueOf(inputNo)}" />
					<c:set var="file" value="${article.extra.file__common__attachment[fileNo]}" />
					${file.mediaHtml}
				</c:forEach>
			</div>
			<div class="text-gray-600 font-semibold text-lg mb-2">${article.title}</div>
			<div class="text-gray-500 font-thin text-sm mb-6">${article.body}</div>
			<div class="flex justify-start mb-4 border-t border-gray-100">
				<div class="flex w-full mt-1 pt-2">
					<span class="bg-white transition ease-out duration-300 hover:text-red-500 border w-8 h-8 px-2 pt-2 text-center rounded-full text-gray-400 cursor-pointer mr-2">
						<svg xmlns="http://www.w3.org/2000/svg" fill="none" width="14px" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 5a2 2 0 012-2h10a2 2 0 012 2v16l-7-3.5L5 21V5z"></path>
                  </svg>
					</span>
					<img class="inline-block object-cover w-8 h-8 text-white border-2 border-white rounded-full shadow-sm cursor-pointer" src="https://images.unsplash.com/photo-1491528323818-fdd1faba62cc?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=facearea&amp;facepad=2&amp;w=256&amp;h=256&amp;q=80" alt="">
					<img class="inline-block object-cover w-8 h-8 -ml-2 text-white border-2 border-white rounded-full shadow-sm cursor-pointer" src="https://images.unsplash.com/photo-1550525811-e5869dd03032?ixlib=rb-1.2.1&amp;auto=format&amp;fit=facearea&amp;facepad=2&amp;w=256&amp;h=256&amp;q=80" alt="">
					<img class="inline-block object-cover w-8 h-8 -ml-2 text-white border-2 border-white rounded-full shadow-sm cursor-pointer" src="https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=crop&amp;w=634&amp;q=80" alt="">
					<img class="inline-block object-cover w-8 h-8 -ml-2 text-white border-2 border-white rounded-full shadow-sm cursor-pointer" src="https://images.unsplash.com/photo-1500648767791-00dcc994a43e?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=facearea&amp;facepad=2.25&amp;w=256&amp;h=256&amp;q=80" alt="">
				</div>
				
				<div class="flex justify-end w-full mt-3 pt-2">
					<div class=mr-3>
					<span>${article.extra.likePoint}</span>
						<a onclick="if ( !confirm('?????????????????????????') ) return false;" href="/adm/article/doLike?id=${article.id}" class="ml-2 text-blue-500 hover:underline">
							<span>
								<i class="far fa-thumbs-up"></i>
								<span class="hidden sm:inline">??????</span>
							</span>
						</a>
					</div>
					<span class="transition ease-out duration-300 hover:bg-blue-50 bg-blue-100 h-8 px-2 py-2 text-center rounded-full text-blue-400 cursor-pointer mr-2">
						<svg xmlns="http://www.w3.org/2000/svg" fill="none" width="14px" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8.684 13.342C8.886 12.938 9 12.482 9 12c0-.482-.114-.938-.316-1.342m0 2.684a3 3 0 110-2.684m0 2.684l6.632 3.316m-6.632-6l6.632-3.316m0 0a3 3 0 105.367-2.684 3 3 0 00-5.367 2.684zm0 9.316a3 3 0 105.368 2.684 3 3 0 00-5.368-2.684z"></path>
                  </svg>
					</span>
					<span class="transition ease-out duration-300 hover:bg-blue-500 bg-blue-600 h-8 px-2 py-2 text-center rounded-full text-gray-100 cursor-pointer">
						<svg xmlns="http://www.w3.org/2000/svg" fill="none" width="14px" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4"></path>
                  </svg>
					</span>
				
					
				</div>
			</div>
			
			
				
					<div class="flex w-full border-t border-gray-100">
						<div class="mt-3 flex flex-row">
							<div class="flex text-gray-700 font-normal text-sm rounded-md mb-2 mr-4 items-center whitespace-nowrap">
								??????:
								<div class="ml-1 text-gray-400 font-thin text-ms">30</div>
							</div>
							<div class="flex text-gray-700 font-normal text-sm rounded-md mb-2 mr-4 items-center whitespace-nowrap">
								??????:
								<div class="ml-1 text-gray-400 font-thin text-ms">60k</div>
							</div>
						</div>
						<div class="mt-3 w-full flex justify-end">
							<div class="flex text-gray-700 font-normal text-sm rounded-md mb-2 items-center whitespace-nowrap">
								?????????:
								<div class="ml-1 text-gray-400 font-thin text-ms">120k</div>
							</div>
						</div>
					</div>
			<div class="relative flex items-center self-center text-gray-600 focus-within:text-gray-400">
				<img class="w-10 h-10 object-cover rounded-full shadow mr-2 cursor-pointer" alt="User avatar" src="https://images.unsplash.com/photo-1477118476589-bff2c5c4cfbb?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=crop&amp;w=200&amp;q=200">
				<span class="">
					<button type="submit" class="p-1 focus:outline-none focus:shadow-none hover:text-blue-500">

					</button>
				</span>
				<div class="w-full">
					<script>
					function doAddReply__submitForm(form) {
						form.body.value = form.body.value.trim();
						
						if (form.body.value.length == 0) {
							alert('????????? ??????????????????.');
							form.body.focus();
							
							return;
							
							}
							$.post('./doAddReplyAjax', {
									relId : param.id,
									relTypeCode : form.relTypeCode.value,
									body : form.body.value
								}, function(data) {
									if( data.msg ) {
										alert(data.msg);
									}
									
									if ( data.resultCode.substr(0, 2) == 'S-' ) {
										location.reload(); // ??????
									}
									
								}, 'json');
							
							form.body.value = '';
						}
					</script>
					<form action="" onsubmit="doAddReply__submitForm(this); return false;" class="relative flex py-4 text-gray-600 focus-within:text-gray-400">
			            <input type="hidden" name="relTypeCode" value="article"/>
			            
			            <span class="absolute inset-y-0 right-0 flex items-center pr-3">
			              <button type="submit" class="p-1 focus:outline-none focus:shadow-none hover:text-blue-500">
			                <i class="fas fa-comment-dots"></i>
			              </button>
			            </span>
			            
			            <input name="body" type="text" class="w-full py-2 pl-4 pr-10 text-sm bg-gray-100 border border-transparent appearance-none rounded-tg placeholder-gray-400 focus:bg-white focus:outline-none focus:border-blue-500 focus:text-gray-900 focus:shadow-outline-blue"
			            style="border-radius: 25px" placeholder="????????? ??????????????????." autocomplete="off">
			          </form>
			          
			          
			         <!-- 
					<form action="" onsubmit="doAddReply__submitForm(this); return false;">
					<input type="hidden" name="relTypeCode" value="article"/>
							
							<div>
								<textarea name="body" autofocus="autofocus" 
		           		 		class="w-full py-2 pl-4 pr-10 text-sm bg-gray-100 border border-transparent appearance-none rounded-tg placeholder-gray-400 focus:bg-white focus:outline-none focus:border-blue-500 focus:text-gray-900 focus:shadow-outline-blue" 
								style="border-radius: 25px"  placeholder="????????? ??????????????????." autocomplete="off"></textarea>
							</div>
							<div class="btns">
								<input type="submit" class="btn-primary mt-3 bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded" value="??????">
							</div>

					</form>
					-->
				</div>

			</div>

			<span>??????</span>
			<c:forEach items="${replies}" var="reply">
			<div class="flex-grow px-1">
                  <div class="flex text-gray-400 text-light text-sm mt-3 flex w-full border-t border-gray-100">
                    <div>${reply.extra__writer}</div>
                    <span class="mx-1">??</span>
                    <div>${reply.updateDate}</div>
                  </div>
                  <div class="break-all">
                    ${reply.body}
                  </div>
              </div>
              <div class="mt-3 flex flex-row">
	              <div class=mr-3>
	              		<div>
							<a href="/adm/reply/modify?id=${reply.id}" class="ml-2 text-black-500 hover:underline">
								<span>
									<i class="fas fa-edit" style="color:LimeGreen"></i>
									<span class="hidden sm:inline">??????</span>
								</span>
							</a>
						</div>
					</div>
					<div class=mr-3>
							<a onclick="if ( !confirm('?????????????????????????') ) return false;" href="/adm/reply/doLike?id=${reply.id}" class="ml-2 text-black-500 hover:underline">
								<span>
									<i class="far fa-heart" style="color:DodgerBlue"></i>
									<span class="hidden sm:inline">?????????</span>
								</span>
							</a>
					</div>
					<div>
						<a onclick="if ( !confirm('?????????????????????????') ) return false;" href="/adm/reply/doDelete?id=${reply.id}" class="ml-2 text-black-500 hover:underline">
							<span>
								<i class="fas fa-trash" style="color:red"></i>
								<span class="hidden sm:inline">??????</span>
							</span>
						</a>
					</div>
				</div>
	
				
			</c:forEach>
			
		</div>
		</div>
	
	<!--
		<div>
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>??????</span>
				</div>
				<div class="lg:flex-grow">
					${article.title}
				</div>
			</div>
			<div class="form-row flex flex-col lg:flex-row">
				<div class="lg:flex lg:items-center lg:w-28">
					<span>??????</span>
				</div>
				<div class="lg:flex-grow">
					<c:forEach begin="1" end="${fileInputMaxCount}" var="inputNo">
						<c:set var="fileNo" value="${String.valueOf(inputNo)}" />
						<c:set var="file" value="${article.extra.file__common__attachment[fileNo]}" />
						${file}
					</c:forEach>
					${article.body}
				</div>
			</div>
		</form>
		-->
</section>
<%@ include file="../part/mainLayoutFoot.jspf"%>