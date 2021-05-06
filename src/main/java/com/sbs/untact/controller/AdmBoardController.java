package com.sbs.untact.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.Board;
import com.sbs.untact.dto.Member;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.ArticleService;
import com.sbs.untact.service.BoardService;
import com.sbs.untact.service.GenFileService;
import com.sbs.untact.util.Util;

@Controller
public class AdmBoardController extends BaseController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private GenFileService genFileService;

	@RequestMapping("/adm/board/detail")
	public String showDetail(HttpServletRequest req, @RequestParam(defaultValue = "1") int boardId,
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

		int totalPage = (int) Math.ceil(totalItemsCount / (double) itemsInAPage);
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

		return "adm/board/detail";
	}

	@RequestMapping("/adm/board/list")
	public String showList(HttpServletRequest req, String searchKeywordType, String searchKeyword,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "1") int boardId) {

		if (searchKeywordType != null) {
			searchKeywordType = searchKeywordType.trim();
		}

		if (searchKeywordType == null || searchKeywordType.length() == 0) {
			searchKeywordType = "nameAndCode";
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

		int totalItemsCount = boardService.getBoardsTotalCount(boardId, searchKeywordType, searchKeyword);

		int itemsInAPage = 20;

		int totalPage = (int) Math.ceil(totalItemsCount / (double) itemsInAPage);
		int pageMenuArmSize = 10;
		int pageMenuStart = page - pageMenuArmSize;

		if (pageMenuStart < 1) {
			pageMenuStart = 1;
		}

		int pageMenuEnd = page + pageMenuArmSize;
		if (pageMenuEnd > totalPage) {
			pageMenuEnd = totalPage;
		}

		List<Board> boards = boardService.getForPrintBoards(searchKeywordType, searchKeyword, boardId, page,
				itemsInAPage);

		req.setAttribute("totalItemsCount", totalItemsCount);
		req.setAttribute("boards", boards);
		req.setAttribute("page", page);
		req.setAttribute("totalPage", totalPage);

		req.setAttribute("pageMenuArmSize", pageMenuArmSize);
		req.setAttribute("pageMenuStart", pageMenuStart);
		req.setAttribute("pageMenuEnd", pageMenuEnd);

		return "adm/board/list";
	}

	@RequestMapping("/adm/board/add")
	public String showAdd(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		return "adm/board/add";
	}

	@RequestMapping("/adm/board/doAdd")
	public String doAdd(@RequestParam Map<String, Object> param, HttpServletRequest req, String name, String code) {
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		Board existingBoardN = boardService.getBoardByName(name);
		Board existingBoardC = boardService.getBoardByCode(code);
		
		
		if (Util.isEmpty(param.get("name"))) {
			return msgAndBack(req, "게시판 이름을 입력해주세요.");
		}
		
		if (Util.isEmpty(param.get("code"))) {
			return msgAndBack(req, "게시판 코드를 입력해주세요.");
		}

		
		if (existingBoardN != null) {
			return msgAndBack(req, String.format("%s(은)는 이미 사용중인 게시판이름 입니다.", name));
		}
		
		if (existingBoardC != null) {
			return msgAndBack(req, String.format("%s(은)는 이미 사용중인 게시판코드 입니다.", code));
		}
		

		param.put("memberId", loginedMemberId);

		ResultData addBoardRd = boardService.addBoard(param);

		return msgAndReplace(req, String.format("%s 게시판이 생성 되었습니다.", param.get("name")),
				"../board/list");
	}

	@RequestMapping("/adm/board/doDelete")
	public String doDelete(Integer id, HttpServletRequest req, String name) {
		Member loginedMemberId = (Member) req.getAttribute("loginedMember");

		Board board = boardService.getBoard(id);

		if (board == null) {
			return msgAndBack(req, "해당 게시판은 존재하지 않습니다.");
		}

		ResultData actorCanDeleteRd = boardService.getActorCanDeleteRd(board, loginedMemberId);

		if (actorCanDeleteRd.isFail()) {
			return msgAndBack(req,actorCanDeleteRd.getMsg());
		}
		
		String boardName = board.getName();
		
		ResultData rd = boardService.deleteBoard(id);

		
		return msgAndReplace(req, String.format("%s 게시판이 삭제되었습니다.", boardName),
				"../board/list");
	}

	@RequestMapping("/adm/board/modify")
	public String showModify(Integer id, HttpServletRequest req) {
		if (id == null) {
			return msgAndBack(req, "id를 입력해주세요.");
		}

		Board board = boardService.getForPrintBoard(id);

		req.setAttribute("board", board);

		if (board == null) {
			return msgAndBack(req, "존재하지 않는 게시판번호 입니다.");
		}

		return "adm/board/modify";
	}

	@RequestMapping("/adm/board/doModify")
	public String doModify(@RequestParam Map<String, Object> param, HttpServletRequest req, String name, String code) {
		Member loginedMemberId = (Member) req.getAttribute("loginedMember");

		int id = Util.getAsInt(param.get("id"), 0);

		if (Util.isEmpty(param.get("name"))) {
			return msgAndBack(req, "name을 입력해주세요.");
		}
		

		Board board = boardService.getBoard(id);

		Board existingBoard = boardService.getBoardByName(name);

		if (board == null) {
			return msgAndBack(req, "해당 게시판은 존재하지 않습니다.");
		}

		if (existingBoard != null) {
			return msgAndBack(req, String.format("%s(은)는 이미 사용중인 게시판이름 입니다.", (param.get("name"))));
		}


		ResultData actorCanModifyRd = boardService.getActorCanModifyRd(board, loginedMemberId);

		if (actorCanModifyRd.isFail()) {
			return msgAndBack(req,actorCanModifyRd.getMsg());
		}

		
		ResultData rd = boardService.modifyBoard(param);

		
		return msgAndReplace(req, String.format("%s 게시판이 수정되었습니다.", name),
				"../board/list");
		
	}


}
