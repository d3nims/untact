package com.sbs.untact.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.Member;
import com.sbs.untact.dto.Reply;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.ArticleService;
import com.sbs.untact.service.ReplyService;


@Controller
public class AdmReplyController extends BaseController{
	@Autowired
	private ReplyService replyService;
	@Autowired
	private ArticleService articleService;



	@RequestMapping("/adm/reply/list")
	@ResponseBody
	public ResultData showList(String relTypeCode, Integer relId) {
		
		if ( relTypeCode == null ) {
			return new ResultData("F-1", "relTypeCode를 입력해주세요.");
		}
		
		if ( relId == null ) {
			return new ResultData("F-1", "relId를 입력해주세요.");
		}
		
		if (relTypeCode.equals("article")) {
			Article article = articleService.getArticle(relId);
			
			if ( article == null ) {
				return new ResultData("F-1", "존재하지 않는 게시물입니다.");
			}

		}
		

		List<Reply> replies = replyService.getForPrintReplies(relTypeCode, relId);
	
		return new ResultData("S-1", "성공", "replies", replies);
	}
	
	
	
	@RequestMapping("/adm/reply/doDelete")
	public String doDelete(@RequestParam Map<String, Object> param, int id, HttpServletRequest req) {
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		
		
		if (param.get("id") == null) {
			return msgAndBack(req, "id를 입력해주세요.");
		}

		
		Reply reply = replyService.getReply(id);

		if (reply == null) {
			return msgAndBack(req, "해당 댓글은 존재하지 않습니다.");
		}
		
		
		ResultData actorCanDeleteRd = replyService.getActorCanDeleteRd(reply, loginedMemberId);
		
		ResultData rd = replyService.deleteReply(id);
		
	
		if ( actorCanDeleteRd.isFail() ) {
			return msgAndBack(req,actorCanDeleteRd.getMsg());
		}

	
		
		return msgAndReplace(req, String.format("%d번 댓글이 삭제되었습니다.", id),
				"../article/detail?id=" + reply.getRelId());
	}
	
	
	
	@RequestMapping("/adm/reply/doModify")
	@ResponseBody
	public ResultData doModify(Integer id, String title, String body, HttpServletRequest req) {
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");


		if (id == null) {
			return new ResultData("F-1", "id를 입력해주세요.");
		}

		if (body == null) {
			return new ResultData("F-1", "body를 입력해주세요.");
		}

		Reply reply = replyService.getReply(id);

		if (reply == null) {
			return new ResultData("F-1", "해당 댓글은 존재하지 않습니다.");
		}

		ResultData actorCanModifyRd = replyService.getActorCanModifyRd(reply, loginedMemberId);

		if ( actorCanModifyRd.isFail() ) {
			return actorCanModifyRd;
		}


		return replyService.modifyReply(id, body);
	}
	
	@RequestMapping("/adm/reply/doLike")
	public String doLike(@RequestParam Map<String, Object> param, int id, HttpServletRequest req) {

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		ResultData actorCanReplyLike = replyService.getActorCanReplyLike(id, loginedMemberId);
		if (((ResultData) actorCanReplyLike).isFail()) {
			return msgAndBack(req,actorCanReplyLike.getMsg());
		}
		
		ResultData likeReplyResultData = replyService.likeReply(id, loginedMemberId);
		String msg = likeReplyResultData.getMsg();
		

		req.setAttribute("alertMsg", msg);
		String redirectUrl = "/adm/article/detail?id=" + param.get("id");
		
		return msgAndReplace(req,actorCanReplyLike.getMsg(), redirectUrl);
	}

}




