package com.sbs.untact.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.Board;
import com.sbs.untact.dto.GenFile;
import com.sbs.untact.dto.Member;
import com.sbs.untact.dto.Reply;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.ArticleService;
import com.sbs.untact.service.GenFileService;
import com.sbs.untact.service.ReplyService;
import com.sbs.untact.util.Util;


@Controller
public class AdmArticleController extends BaseController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private GenFileService genFileService;
	@Autowired
	private ReplyService replyService;
	
	
	

	@RequestMapping("/adm/article/detail")
	public String showDetail(HttpServletRequest req, Integer id) {
		if (id == null) {
			return msgAndBack(req, "id를 입력해주세요.");
		}

		Article article = articleService.getForPrintArticle(id);

		List<GenFile> files = genFileService.getGenFiles("article", article.getId(), "common", "attachment");
		
		List<Reply> replies = replyService.getForPrintReplies("article",id);
		Map<String, Object> filesMap = new HashMap<>();
		
		for (GenFile file : files) {
			filesMap.put(file.getFileNo() + "", file);
			
		}
		
		article.getExtraNotNull().put("file__common__attachment", filesMap);
		req.setAttribute("article", article);
		req.setAttribute("replies", replies);
		

		return "adm/article/detail";
	}

	@RequestMapping("/adm/article/list")
	public String showList(HttpServletRequest req, @RequestParam(defaultValue = "1") int boardId,
			String searchKeywordType, String searchKeyword, @RequestParam(defaultValue = "1") int page) {

		Board board = articleService.getBoard(boardId);

		req.setAttribute("board", board);

		if (board == null) {
			return msgAndBack(req, "존재하지 않는 게시판 입니다.");
		}

		if (searchKeywordType != null) {
			searchKeywordType = searchKeywordType.trim();
		}

		if (searchKeywordType == null || searchKeywordType.length() == 0) {
			searchKeywordType = "titleAndBody";
		}

		if (searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}

		if (searchKeyword != null) {
			searchKeyword = searchKeyword.trim();
		}

		if (searchKeyword == null) {
			searchKeywordType = null;
		}

		int totalItemsCount = articleService.getArticlesTotalCount(boardId, searchKeywordType, searchKeyword);
		
		int itemsInAPage = 20;

		int totalPage = (int)Math.ceil(totalItemsCount / (double)itemsInAPage);
		int pageMenuArmSize = 10;
		int pageMenuStart = page - pageMenuArmSize;
		
		if (pageMenuStart < 1) {
			pageMenuStart = 1;
		}
		
		int pageMenuEnd = page + pageMenuArmSize;
		if (pageMenuEnd > totalPage) {
			pageMenuEnd = totalPage;
		}
		
		List<Article> articles = articleService.getForPrintArticles(boardId, searchKeywordType, searchKeyword, page,
				itemsInAPage);
		req.setAttribute("totalItemsCount", totalItemsCount);
		req.setAttribute("articles", articles);
		req.setAttribute("page", page);
		req.setAttribute("totalPage", totalPage);
		
		req.setAttribute("pageMenuArmSize", pageMenuArmSize);
		req.setAttribute("pageMenuStart", pageMenuStart);
		req.setAttribute("pageMenuEnd", pageMenuEnd);

		

		return "adm/article/list";
	}

	@RequestMapping("/adm/article/doAddReply")
	public String doAddReply(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		if (param.get("body") == null) {
			return msgAndBack(req, "body를 입력해주세요.");
		}

		if (param.get("relId") == null) {
			return msgAndBack(req, "relId를 입력해주세요.");
		}
		
		String relTypeCode = (String)param.get("relTypeCode");
		
		param.put("memberId", loginedMemberId);
		
		ResultData addReplyRd = articleService.addReply(param);

		
		int newReplyId = (int) addReplyRd.getBody().get("id");
		
		return msgAndReplace(req, String.format("%d번 댓글이 작성되었습니다.", newReplyId),
				"../article/detail?id=" + newReplyId);
		

	}

	@RequestMapping("/adm/article/add")
	public String showAdd(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		return "adm/article/add";
	}

	@RequestMapping("/adm/article/doAdd")
	public String doAdd(@RequestParam Map<String, Object> param, HttpServletRequest req,
			MultipartRequest multipartRequest) {
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		if (param.get("title") == null) {
			return msgAndBack(req, "title을 입력해주세요.");
		}

		if (param.get("body") == null) {
			return msgAndBack(req, "body를 입력해주세요.");
		}

		param.put("memberId", loginedMemberId);

		ResultData addArticleRd = articleService.addArticle(param);

		int newArticleId = (int) addArticleRd.getBody().get("id");

		return msgAndReplace(req, String.format("%d번 게시물이 작성되었습니다.", newArticleId),
				"../article/detail?id=" + newArticleId);
	}

	@RequestMapping("/adm/article/doDelete")
	public String doDelete(Integer id, HttpServletRequest req) {
		Member loginedMemberId = (Member) req.getAttribute("loginedMember");

		if (id == null) {
			return msgAndBack(req, "id를 입력해주세요.");
		}

		Article article = articleService.getArticle(id);

		if (article == null) {
			return msgAndBack(req, "해당 게시물은 존재하지 않습니다.");
			
		}

		ResultData actorCanDeleteRd = articleService.getActorCanDeleteRd(article, loginedMemberId);
		
		ResultData rd = articleService.deleteArticle(id);
		
		if (rd.isFail()) {
			return msgAndBack(req,rd.getMsg());
		}

		String redirectUrl = "../article/list?boardId=" + rd.getBody().get("boardId");
		
		return msgAndReplace(req,rd.getMsg(), redirectUrl);
	}



	@RequestMapping("/adm/article/modify")
	public String showModify(Integer id, HttpServletRequest req) {
		if (id == null) {
			return msgAndBack(req, "id를 입력해주세요.");
		}

		Article article = articleService.getForPrintArticle(id);

		List<GenFile> files = genFileService.getGenFiles("article", article.getId(), "common", "attachment");

		Map<String, GenFile> filesMap = new HashMap<>();

		for (GenFile file : files) {
			filesMap.put(file.getFileNo() + "", file);
		}

		article.getExtraNotNull().put("file__common__attachment", filesMap);
		req.setAttribute("article", article);

		if (article == null) {
			return msgAndBack(req, "존재하지 않는 게시물번호 입니다.");
		}

		return "adm/article/modify";
	}

	@RequestMapping("/adm/article/doModify")
	public String doModify(@RequestParam Map<String, Object> param, HttpServletRequest req, String title, String body) {
		Member loginedMemberId = (Member) req.getAttribute("loginedMember");
		
		int id = Util.getAsInt(param.get("id"), 0);

		if ( id == 0 ) {
			return msgAndBack(req, "id를 입력해주세요.");
		}

		if ( Util.isEmpty(param.get("title")) ) {
			return msgAndBack(req, "title을 입력해주세요.");

		}

		if ( Util.isEmpty(param.get("body")) ) {
			return msgAndBack(req, "body를 입력해주세요.");
		}

		Article article = articleService.getArticle(id);

		if (article == null) {
			return msgAndBack(req, "해당 게시물은 존재하지 않습니다.");
		}

		ResultData actorCanModifyRd = articleService.getActorCanModifyRd(article, loginedMemberId);

		ResultData rd = articleService.modifyArticle(id, title, body);
		
		if (rd.isFail()) {
			return msgAndBack(req,rd.getMsg());
		}

		String redirectUrl = "../article/list?boardId=" + rd.getBody().get("boardId");
		
		return msgAndReplace(req,rd.getMsg(), redirectUrl);
	}
}


