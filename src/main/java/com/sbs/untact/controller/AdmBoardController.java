package com.sbs.untact.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.untact.dto.Board;
import com.sbs.untact.service.ArticleService;

public class AdmBoardController extends BaseController{
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/adm/board/list")
	public String showList(HttpServletRequest req,  @RequestParam(defaultValue = "1") int boardId,
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
			searchKeywordType = "codeAndName";
		}

		if (searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}

		if (searchKeyword != null) {
			searchKeyword = searchKeyword.trim();
		}

		if (searchKeyword == null) {
			searchKeyword = null;
		}

		int itemsInAPage = 20;

		List<Board> boards = articleService.getForPrintBoards(boardId, page, itemsInAPage, searchKeywordType, searchKeyword);

		req.setAttribute("boards", boards);

		return "adm/board/list";
	}
	
}
