package com.sbs.untact.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact.dao.ReplyDao;
import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.Reply;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.util.Util;

@Service
public class ReplyService {
	@Autowired
	private ReplyDao replyDao;
	
	@Autowired
	private MemberService memberService;

	public List<Reply> getForPrintReplies(String relTypeCode, int relId) {
		
		return replyDao.getForPrintReplies(relTypeCode, relId);
	}

	public Reply getReply(Integer id) {
	
		return replyDao.getReply(id);
	}

	public ResultData getActorCanDeleteRd(Reply reply, int actorId) {
		if ( reply.getMemberId() == actorId) {
			return new ResultData("S-1", "가능합니다.");
		}
		
		if( memberService.isAdmin(actorId) ) {
			return new ResultData("S-2", "가능합니다.");
		}
		
		return new ResultData("F-1", "권한이 없습니다.");
		
	}

	public ResultData deleteReply(int id) {
		replyDao.deleteReply(id);

		return new ResultData("S-1", "삭제하였습니다.", "id", id);

	}
	


	public ResultData getActorCanModifyRd(Reply reply, int actor) {
		
		if (reply.getMemberId() != actor) {
			return new ResultData("F-1", "권한이 없습니다.");

		}
		
		return getActorCanDeleteRd(reply, actor);
	}

	public ResultData modifyReply(int id, String body) {

		replyDao.modifyReply(id, body);

		return new ResultData("S-1", id + "번 댓글을 수정하였습니다.", "id", id);
	}

	public ResultData getActorCanReplyLike(int id, int actor) {
		Reply reply = getReply(id);

		if (reply.getMemberId() == actor) {
			return new ResultData("F-1", "본인은 추천할 수 없습니다.");

		}
		// likePoint는 articleDao에 getLikePointByMemberId의 게시물id와 작성자actor의 값
		int likePoint = replyDao.getLikePointByMemberId(id, actor);

		// likePoint가 0보다 크면 "F-2", "이미 추천한 게시물입니다." 출력
		if (likePoint > 0) {
			return new ResultData("F-2", "이미 추천한 게시물입니다.");
		}

		return new ResultData("S-1", String.format("%d번 댓글을 추천하였습니다.", id));
	
	}

	public ResultData likeReply(int id, int actor) {
		replyDao.likeReply(id, actor);

		return new ResultData("S-1", String.format("%d번 댓글을 추천하였습니다.", id), "id", id);

	}

}


