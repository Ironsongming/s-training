package com.csm.straining.article.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.article.refer.ArticleServiceReference;
import com.csm.straining.article.resp.moment.MomentDetailResp;
import com.csm.straining.article.resp.moment.MomentListResp;
import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.common.i.moment.entity.MomentEntity;
import com.csm.straining.common.i.moment.entity.MomentReplyEntity;
import com.csm.straining.common.i.moment.info.MomentInfo;
import com.csm.straining.common.i.moment.info.MomentReplyInfo;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.common.model.info.common.ResponseStatus;
import com.csm.straining.core.user.util.UserHelper;
import com.lamfire.utils.JSON;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class MomentService {
	
	private static final Logger logger = LoggerFactory.getLogger(MomentService.class);
	
	public static ResponseStatus createMoment(long optUserID, String content) throws AppException, CoreException {
		
		if(StringUtils.isBlank(content)) {
			throw new AppException("动态内容不能为空");
		}
		
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("text", content);
		
		ArticleServiceReference.sharedService().createMoment(optUserID, JSON.toJsonString(contentMap));
		
		return new ResponseStatus();
	}
	
	public static ResponseStatus transferMoment(long optUserID, String content, long transferID) throws AppException, CoreException {
		
		if(StringUtils.isBlank(content)) {
			throw new AppException("动态内容不能为空");
		}
		
		if (transferID <= 0) {
			throw new AppException("转发动态ID不为0");
		}
		
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("text", content);
		
		ArticleServiceReference.sharedService().transferMoment(optUserID, JSON.toJsonString(contentMap), transferID);
		return new ResponseStatus();
	}
	
	public static ResponseStatus delMomentByID(long optUserID, long momentID) throws AppException, CoreException {
		
		if (momentID <= 0) {
			throw new AppException("该动态不存在");
		}
		
		ArticleServiceReference.sharedService().delMomentByID(optUserID, momentID);
		return new ResponseStatus();
	}
	
	public static ResponseStatus createMomentReply(long optUserID, long momentID, long replyUserID, String content) throws AppException, CoreException {
		if (momentID <= 0) {
			throw new AppException("该动态不存在");
		}
		
		if (replyUserID < 0) {
			throw new AppException("用户不存在");
		}
		
		if (StringUtils.isBlank(content)) {
			throw new AppException("评论内容不能为空");
		}
		
		ArticleServiceReference.sharedService().createMomentReply(optUserID, momentID, replyUserID, content);
		return new ResponseStatus();
	}
	
	public static ResponseStatus delMomentReplyByID(long optUserID, long replyID) throws CoreException, AppException {
		if (optUserID <= 0) {
			throw new AppException("该用户不存在");
		}
		
		if (replyID < 0) {
			throw new AppException("该评论不存在");
		}
		
		ArticleServiceReference.sharedService().delMomentReplyByID(optUserID, replyID);
		return new ResponseStatus();
	}
	
	public static ResponseStatus createMomentLike(long optUserID, long momentID, byte type) throws AppException, CoreException {
		
		if (momentID <= 0) {
			throw new AppException("该动态不存在");
		}
		
		if (optUserID < 0) {
			throw new AppException("用户不存在");
		}
		
		if (type != Status.MomentLike.NORMAL && type != Status.MomentLike.CANCEL) {
			throw new AppException("请检查type参数");
		}
		
		ArticleServiceReference.sharedService().createMomentLike(optUserID, momentID, type);
		return new ResponseStatus();
	}
	
	public static MomentDetailResp getMomentDetailResp(long optUserID, long momentID) throws CoreException, AppException {
		MomentEntity momentEntity = ArticleServiceReference.sharedService().getRichMomentEntityByID(momentID);
		
		if (momentEntity == null) {
			throw new AppException("该动态不存在");
		}
		
		MomentDetailResp resp = new MomentDetailResp();
		resp.moment = parseMomentInfo(optUserID, momentEntity);
		return resp;
	}
	
	public static MomentListResp getMomentListResp(long userID, long start, int count) throws CoreException, AppException {
		MomentListResp resp = new MomentListResp();
		List<MomentEntity> momentEntities = ArticleServiceReference.sharedService().getUserRichMoments(userID, start, count + 1);
		for (MomentEntity momentEntity : momentEntities) {
			MomentInfo momentInfo = parseMomentInfo(userID, momentEntity);
			if (momentInfo != null) {
				resp.moments.add(momentInfo);
			}
		}
		
		if (resp.moments.size() > count) {
			resp.setMore(1);
		} else {
			resp.setMore(0);
			
			if (resp.moments.size() > 0) {
				resp.setStart(Long.toString(resp.moments.get(resp.moments.size() - 1).momentID));
			}
		}
		return resp;
	}
	
	public static MomentListResp getTargetMomentListResp(long optUserID, long targetID, long start, int count) throws CoreException, AppException {
		MomentListResp resp = new MomentListResp();
		List<MomentEntity> momentEntities = ArticleServiceReference.sharedService().getUserHomeRichMoments(targetID, start, count + 1);
		for (MomentEntity momentEntity : momentEntities) {
			MomentInfo momentInfo = parseMomentInfo(optUserID, momentEntity);
			if (momentInfo != null) {
				resp.moments.add(momentInfo);
			}
		}
		
		if (resp.moments.size() > count) {
			resp.setMore(1);
		} else {
			resp.setMore(0);
			
			if (resp.moments.size() > 0) {
				resp.setStart(Long.toString(resp.moments.get(resp.moments.size() - 1).momentID));
			}
		}
		return resp;
	}
	
	public static MomentInfo parseMomentInfo(long optUserID, MomentEntity momentEntity) {
		
		if (momentEntity == null) {
			return null;
		}
		
		MomentInfo momentInfo = new MomentInfo();
		momentInfo.momentID = momentEntity.getMomentID();
		momentInfo.user = UserHelper.entity2Info(momentEntity.getUser());
		momentInfo.text = (String) JSON.toMap(momentEntity.getContent()).get("text");
		momentInfo.createAt = momentEntity.getCreateAt();
		momentInfo.likeCount = momentEntity.getLikeCount();
		momentInfo.replyCount = momentEntity.getReplyCount();
		momentInfo.tranferCount = momentEntity.getTranferCount();
		
		for (UserEntity userEntity : momentEntity.getLikeUsers()) {
			momentInfo.likeUsers.add(UserHelper.entity2Info(userEntity));
		}
		
		for (MomentReplyEntity momentReplyEntity : momentEntity.getReplies()) {
			MomentReplyInfo momentReplyInfo = new MomentReplyInfo();
			momentReplyInfo.replyID = momentReplyEntity.getId();
			momentReplyInfo.momentID = momentReplyEntity.getMomentID();
			momentReplyInfo.user = UserHelper.entity2Info(momentReplyEntity.getUser());
			momentReplyInfo.text = momentReplyEntity.getContent();
			momentReplyInfo.replyUser = UserHelper.entity2Info(momentReplyEntity.getReplyUser());
			momentReplyInfo.createAt = momentReplyEntity.getCreateAt();
			
			if (optUserID == momentInfo.user.userID || momentReplyInfo.user.userID == optUserID) {
				momentReplyInfo.canDelete = 1;
			}
			
			momentInfo.replys.add(momentReplyInfo);
		}
		
		if (momentEntity.getTransferMomentEntity() != null) {
			MomentEntity transferMomentEntity = momentEntity.getTransferMomentEntity();
			MomentInfo transferMomentInfo = new MomentInfo();
			transferMomentInfo.momentID = transferMomentEntity.getMomentID();
			transferMomentInfo.user = UserHelper.entity2Info(transferMomentEntity.getUser());
			transferMomentInfo.text = (String) JSON.toMap(transferMomentEntity.getContent()).get("text");
			transferMomentInfo.createAt = transferMomentEntity.getCreateAt();
			transferMomentInfo.likeCount = transferMomentEntity.getLikeCount();
			transferMomentInfo.replyCount = transferMomentEntity.getReplyCount();
			transferMomentInfo.tranferCount = transferMomentEntity.getTranferCount();
			momentInfo.tranferMoment = transferMomentInfo;
		}
		
		if (optUserID == momentInfo.user.userID) {
			momentInfo.canDelete = 1;
		}
		return momentInfo;
	}

}
