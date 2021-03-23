package com.sbs.untact.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sbs.untact.dao.BoardDao;
import com.sbs.untact.dto.Board;

public class BoardService {
	@Autowired
	private BoardDao boardDao;
	
	public Board getForPrintBoard(Integer id) {
		
		return boardDao.getForPrintBoard(id);
		
	}
	
	public List<Board> getForPrintBoards() {
		
		return boardDao.getForPrintBoards();
		
	}
	
	public Board getBoard(int id) {
		
		return boardDao.getBoard(id);
	}
}
