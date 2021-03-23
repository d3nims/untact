package com.sbs.untact.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.Board;

@Mapper
public interface BoardDao {
		
		Board getForPrintBoard(@Param("id") int id);
		
		List<Board> getForPrintBoards();
		
		Board getBoard(@Param("id") int id);
}

