package com.sbs.untact.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.Board;
import com.sbs.untact.dto.ResultData;

@Mapper
public interface BoardDao {
		
		Board getForPrintBoard(@Param("id") int id);
		
		List<Board> getForPrintBoards(@Param("searchKeywordType") String searchKeywordType,
				@Param("searchKeyword") String searchKeyword, @Param("boardId") int boardId,
				@Param("limitStart") int limitStart, @Param("limitTake") int limitTake);
		
		Board getBoard(@Param("id") int id);

		void deleteBoard(@Param("id")int id);
		
		void modifyBoard(@Param("id")int id, @Param(value = "name") String name,
				@Param(value = "code") String code);
		
		void modifyBoard(Map<String, Object> param);

		int getBoardsTotalCount(@Param("boardId") int boardId,
			@Param("searchKeywordType") String searchKeywordType,
			@Param("searchKeyword") String searchKeyword);

		void addBoard(Map<String, Object> param);

		Board getBoardBync(@Param ("name") String name, @Param ("code") String code);


}

