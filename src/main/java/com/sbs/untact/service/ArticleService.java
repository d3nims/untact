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

	public ResultData getActorCanModifyRd(Article article, Member actor) {
		if (article.getMemberId() == actor.getId()) {
			return new ResultData("S-1", "가능합니다.");
		}

		if (memberService.isAdmin(actor)) {
			return new ResultData("S-2", "가능합니다.");
		}

		return new ResultData("F-1", "권한이 없습니다.");
	}

	public ResultData modifyArticle(int id, String title, String body) {
		Article article = getForPrintArticle(id);
		articleDao.modifyArticle(id, title, body);

		return new ResultData("S-1", id + "번 게시물이 수정되었습니다.", "id", id, "boardId", article.getBoardId());
	}

	public ResultData modifyArticle(Map<String, Object> param) {
		articleDao.modifyArticle(param);

		int id = Util.getAsInt(param.get("id"), 0);

		return new ResultData("S-1", "게시물을 수정하였습니다.", "id", id);
	}

	public List<Article> getForPrintArticles(int boardId, String searchKeywordType, String searchKeyword, int page,
			int itemsInAPage) {

		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		return articleDao.getForPrintArticles(boardId, searchKeywordType, searchKeyword, limitStart, limitTake);
	}

	public int getArticlesTotalCount(int boardId, String searchKeywordType, String searchKeyword) {
		return articleDao.getArticlesTotalCount(boardId, searchKeywordType, searchKeyword);
	}

	public ResultData getActorCanLike(int id, int actor) {
		Article article = getArticle(id);

		if (article.getMemberId() == actor) {
			return new ResultData("F-1", "본인은 추천할 수 없습니다.");

		}
		// likePoint는 articleDao에 getLikePointByMemberId의 게시물id와 작성자actor의 값
		int likePoint = articleDao.getLikePointByMemberId(id, actor);

		// likePoint가 0보다 크면 "F-2", "이미 추천한 게시물입니다." 출력
		if (likePoint > 0) {
			return new ResultData("F-2", "이미 추천한 게시물입니다.");
		}

		return new ResultData("S-1", String.format("%d번 게시물을 추천하였습니다.", id));
	}

//	public Map<String, Object> getActorCanLike(Integer id, int actor) {
//		Article article = getArticle(id);
//		Map<String, Object> rs = new HashMap<>();
//
//		if (article.getMemberId() == actor) {
//			rs.put("resultCode", "F-1");
//			rs.put("msg", "본인은 추천 할 수 없습니다.");
//
//			return rs;
//		}
//
//		int likePoint = articleDao.getLikePointByMemberId(id, actor);
//
//		if (likePoint > 0) {
//			rs.put("resultCode", "F-2");
//			rs.put("msg", "이미 좋아요를 하셨습니다.");
//
//			return rs;
//		}
//
//		rs.put("resultCode", "S-1");
//		rs.put("msg", "가능합니다.");
//
//		return rs;
//	}

	public ResultData likeArticle(int id, int actor) {
		articleDao.likeArticle(id, actor);

		return new ResultData("S-1", String.format("%d번 게시물을 추천하였습니다.", id), "id", id);
	}

}
