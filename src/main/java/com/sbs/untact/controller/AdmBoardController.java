package com.sbs.untact.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.dto.Board;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.BoardService;

public class AdmBoardController extends BaseController{
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/adm/board/detail")

	public String showDetail(HttpServletRequest req, Integer id) {
		Board boards = boardService.getForPrintBoard(id);

		req.setAttribute("boards", boards);

		return "adm/board/detail";
	}
	
	@RequestMapping("/adm/board/list")
	public String showList(HttpServletRequest req) {

		List<Board> boards = boardService.getForPrintBoards();

		req.setAttribute("boards", boards);

		return "adm/board/list";
	}
	
}
