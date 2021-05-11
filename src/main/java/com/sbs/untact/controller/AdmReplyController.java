package com.sbs.untact.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.GenFile;
import com.sbs.untact.dto.Member;
import com.sbs.untact.dto.Reply;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.ArticleService;
import com.sbs.untact.service.ReplyService;
import com.sbs.untact.util.Util;


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
	
	
	

	
	@RequestMapping("/adm/reply/modify")
    public String showModify(HttpServletRequest req, int id, String redirectUri) {
        Reply reply = replyService.getReply(id);

        int loginedMemberId = (int) req.getAttribute("loginedMemberId");
        
        if ( reply == null ) {
            return msgAndBack(req, "존재하지 않는 댓글입니다.");
        }

        ResultData actorCanReplyReply = replyService.getActorCanModifyRd(reply, loginedMemberId);
		if (((ResultData) actorCanReplyReply).isFail()) {
			return msgAndBack(req,actorCanReplyReply.getMsg());
		}

        req.setAttribute("reply", reply);

        return "adm/reply/modify";
    }

    @RequestMapping("/adm/reply/doModify")
    public String doModify(@RequestParam Map<String, Object> param, HttpServletRequest req, int id, String body) {
        Reply reply = replyService.getReply(id);
        
        int loginedMemberId = (int) req.getAttribute("loginedMemberId");

        if ( reply == null ) {
            return msgAndBack(req, "존재하지 않는 댓글입니다.");
        }
        ResultData modifyResultData = replyService.modifyReply(id, body);
        
        ResultData actorCanReply = replyService.getActorCanModifyRd(reply, loginedMemberId);
		if (((ResultData) actorCanReply).isFail()) {
			return msgAndBack(req,actorCanReply.getMsg());
		}
        
        String redirectUrl = "/adm/article/detail?id=" + reply.getRelId();

        return msgAndReplace(req,modifyResultData.getMsg(), redirectUrl);
       
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




