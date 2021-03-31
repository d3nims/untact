package com.sbs.untact.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact.dao.BoardDao;
import com.sbs.untact.dto.Board;
import com.sbs.untact.dto.Member;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.util.Util;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private MemberService memberService;
	
	public Board getForPrintBoard(Integer id) {
		
		return boardDao.getForPrintBoard(id);
		
	}
	
	public List<Board> getForPrintBoards(String searchKeywordType, String searchKeyword,
			int boardId, int page, int itemsInAPage) {
		
		int limitStart = (page -1) * itemsInAPage;
		int limitTake = itemsInAPage;
		
		return boardDao.getForPrintBoards(searchKeywordType, searchKeyword, boardId, page,
				itemsInAPage);
		
	}
	
	public Board getBoard(int id) {
		
		return boardDao.getBoard(id);
	}

	public ResultData deleteBoard(int id) {
		boardDao.deleteBoard(id);

		return new ResultData("S-1", "삭제하였습니다.", "id", id);
	
	}

	public ResultData getActorCanDeleteRd(Board board, Member actor) {
		return getActorCanModifyRd(board, actor);
	}

	public ResultData getActorCanModifyRd(Board board, Member actor) {
		if ( board.getMemberId() == actor.getId()) {
			return new ResultData("S-1", "가능합니다.");
		}
		
		if( memberService.isAdmin (actor)) {
			return new ResultData("S-2", "가능합니다.");
		}
		
		return new ResultData("F-1", "권한이 없습니다.");
	}

	public ResultData modifyBoard(Map<String, Object> param) {
		boardDao.modifyBoard(param);

		int id = Util.getAsInt(param.get("id"),0);
		
		return new ResultData("S-1", "게시판을 수정하였습니다.", "id", id);
	}
	
	
	public ResultData modifyBoard(int id, String name, String code) {
		boardDao.modifyBoard(id, name, code);

		return new ResultData("S-1", "게시판을 수정하였습니다.", "id", id);
	}

	public int getBoardsTotalCount(int boardId, String searchKeywordType, String searchKeyword) {
		return boardDao.getBoardsTotalCount(boardId, searchKeywordType, searchKeyword);
		
	}

	public ResultData addBoard(Map<String, Object> param) {
		
		boardDao.addBoard(param);
		
		int id = Util.getAsInt(param.get("id"), 0);
		
		return new ResultData("S-1", "성공하였습니다.", "id", id);
			
	}

	public Board getBoardByName(String name) {
		
		return boardDao.getBoardByName(name);
	}

	
}
