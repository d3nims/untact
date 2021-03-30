package com.sbs.untact.controller;

import java.util.HashMap;
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
import com.sbs.untact.dto.GenFile;
import com.sbs.untact.dto.Member;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.BoardService;
import com.sbs.untact.service.GenFileService;
import com.sbs.untact.util.Util;

@Controller
public class AdmBoardController extends BaseController{
	@Autowired
	private BoardService boardService;
	@Autowired
	private GenFileService genFileService;
	
	
	
	@RequestMapping("/adm/board/detail")

	public String showDetail(HttpServletRequest req, Integer id) {
		Board boards = boardService.getForPrintBoard(id);

		req.setAttribute("boards", boards);

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
	public String doAdd(@RequestParam Map<String, Object> param, HttpServletRequest req,
			MultipartRequest multipartRequest) {
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		if (param.get("name") == null) {
			return msgAndBack(req, "name을 입력해주세요.");
		}

		if (param.get("code") == null) {
			return msgAndBack(req, "code를 입력해주세요.");
		}

		param.put("memberId", loginedMemberId);

		ResultData addBoardRd = boardService.addBoard(param);

		int newBoardId = (int) addBoardRd.getBody().get("id");

		return msgAndReplace(req, String.format("%d번 게시판이 생성 되었습니다.", newBoardId),
				"../board/detail?id=" + newBoardId);
	}
	
	@RequestMapping("/adm/board/doDelete")
	@ResponseBody
	public ResultData doDelete(Integer id, HttpServletRequest req) {
		Member loginedMemberId = (Member) req.getAttribute("loginedMemberId");

		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		Board board = boardService.getBoard(id);

		if (board == null) {
			return new ResultData("F-1", "해당 게시판은 존재하지 않습니다.");
		}

		ResultData actorCanDeleteRd = boardService.getActorCanDeleteRd(board, loginedMemberId);

		if (actorCanDeleteRd.isFail()) {
			return actorCanDeleteRd;
		}

		return boardService.deleteBoard(id);
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
	@ResponseBody
	public ResultData doModify(@RequestParam Map<String, Object> param, HttpServletRequest req,
			String name, String code) {
		Member loginedMemberId = (Member) req.getAttribute("loginedMember");
		
		int id = Util.getAsInt(param.get("id"), 0);

		if ( id == 0 ) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		if ( Util.isEmpty(param.get("name")) ) {
			return new ResultData("F-1", "name을 입력해주세요.");
		}

		if ( Util.isEmpty(param.get("code")) ) {
			return new ResultData("F-1", "code를 입력해주세요.");
		}

		Board board = boardService.getBoard(id);
		
		Board existingBoard = boardService.getBoardBync("name","code");

		if (board == null) {
			return new ResultData("F-1", "해당 게시판은 존재하지 않습니다.");
		}
		
		if (existingBoard != null) {
			return new ResultData("F-2",String.format("%s(은)는 이미 사용중인 게시판이름 입니다.", name));
		}
		

		ResultData actorCanModifyRd = boardService.getActorCanModifyRd(board, loginedMemberId);

		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}
		
		

		return boardService.modifyBoard(param);
	}
	
}
