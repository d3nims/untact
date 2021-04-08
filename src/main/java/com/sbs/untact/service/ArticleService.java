package com.sbs.untact.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact.dao.ArticleDao;
import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.Board;
import com.sbs.untact.dto.Member;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.util.Util;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private MemberService memberService;
	@Autowired
	private GenFileService genFileService;
	
	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	public ResultData addArticle(Map<String, Object> param) {
		articleDao.addArticle(param);

		int id = Util.getAsInt(param.get("id"), 0);
		
		genFileService.changeInputFileRelIds(param, id);
		return new ResultData("S-1", "성공하였습니다.", "id", id);
	}


	public List<Article> getArticles(String searchKeywordType, String searchKeyword) {
		return articleDao.getArticles(searchKeywordType, searchKeyword);
	}

	public ResultData getActorCanModifyRd(Article article, Member actor) {
		if (article.getMemberId() == actor.getId()) {
			return new ResultData("S-1", "가능합니다.");
		}

		if (memberService.isAdmin(actor)) {
			return new ResultData("S-2", "가능합니다.");
		}

		return new ResultData("F-1", "권한이 없습니다.");
	}

	public ResultData deleteArticle(int id) {
		Article article = getForPrintArticle(id);
		articleDao.deleteArticle(id);

		return new ResultData("S-1", id + "번 게시물이 삭제되었습니다.", "id", id, "boardId", article.getBoardId());
	}
	
	public ResultData getActorCanDeleteRd(Article article, Member actor) {
		return getActorCanModifyRd(article, actor);
	}

	public Article getForPrintArticle(Integer id) {
		
		return articleDao.getForPrintArticle(id);
	}


	public Board getBoard(int id) {
		
		return articleDao.getBoard(id);
	}
	

	public ResultData addReply(Map<String, Object> param) {
		articleDao.addReply(param);

		int id = Util.getAsInt(param.get("id"), 0);

		return new ResultData("S-1", "성공하였습니다.", "id", id);
		
	}

	public ResultData modifyArticle(Map<String, Object> param) {
		articleDao.modifyArticle(param);
		
		int id = Util.getAsInt(param.get("id"), 0);
		
		return new ResultData("S-1", "게시물을 수정하였습니다.", "id", id);
	}
	
	public ResultData modifyArticle(int id, String title, String body) {
		articleDao.modifyArticle(id, title, body);

		return new ResultData("S-1", "게시물을 수정하였습니다.", "id", id);
	}
	
	

	public List<Article> getForPrintArticles(int boardId, String searchKeywordType, String searchKeyword, int page,
			int itemsInAPage) {
		
		int limitStart = (page -1) * itemsInAPage;
		int limitTake = itemsInAPage;
		return articleDao.getForPrintArticles(boardId, searchKeywordType, searchKeyword,limitStart,limitTake);
	}


	public int getArticlesTotalCount(int boardId, String searchKeywordType, String searchKeyword) {
		return articleDao.getArticlesTotalCount(boardId, searchKeywordType, searchKeyword);
	}
	
	
}
