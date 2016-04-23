package com.csm.straining.core.moment.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.common.i.moment.entity.MomentEntity;
import com.csm.straining.common.i.moment.entity.MomentReplyEntity;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.core.contact.core.ContactCore;
import com.csm.straining.core.moment.util.MomentHepler;
import com.csm.straining.core.user.core.UserCore;
import com.csm.straining.dataaccess.caps.moment.MomentCaps;
import com.csm.straining.dataaccess.caps.moment.MomentLikeCaps;
import com.csm.straining.dataaccess.caps.moment.MomentReplyCaps;
import com.csm.straining.dataaccess.entity.moment.Moment;
import com.csm.straining.dataaccess.entity.moment.MomentLike;
import com.csm.straining.dataaccess.entity.moment.MomentReply;
import com.lamfire.utils.JSON;
import com.lamfire.utils.StringUtils;



/**
 * @author chensongming
 */
public class MomentCore {

	private static final Logger logger = LoggerFactory.getLogger(MomentCore.class);
	
	/** -----------------------动态相关---------------------- **/
	private static void createMoment(long userID, String content, long transferID) throws CoreException, AppException {
		
		if (userID <= 0 || !UserCore.existUserID(userID)) {
			throw new AppException("该用户不存在");
		}
		
		if (StringUtils.isBlank(content)) {
			throw new AppException("发布内容不可为空");
		}
		
		Moment domain = new Moment();
		domain.setContent(content);
		domain.setUserID(userID);
		
		if (transferID > 0) {
			Moment moment = MomentCaps.getMomentByID(transferID);
			if (moment == null) {
				throw new AppException("转发动态不存在");
			}
			
			if (moment.getTranferID() > 0 && !existMomentNormal(moment.getTranferID())) {
				throw new AppException("转发动态不存在");
			} else if (moment.getTranferID() > 0) {
				domain.setTranferID(moment.getTranferID());
				MomentCaps.incrTranferCount(moment.getTranferID());
			} else {
				domain.setTranferID(transferID);
			}
			
			MomentCaps.incrTranferCount(transferID);
		}
		
		
		domain.setCreateAt(new Date());
		
		MomentCaps.insertMoment(domain);
	}
	
	public static void createMoment(long userID, String content) throws CoreException, AppException {
		createMoment(userID, content, 0);
	}
	
	public static void transferMoment(long userID, String content, long transferID) throws CoreException, AppException {
		createMoment(userID, content, transferID);
	}
	
	public static boolean existMomentNormal(long momentID) throws CoreException {
		return MomentCaps.existMomentNormal(momentID);
	}
	
	public static void delMomentByID(long optUserID, long momentID) throws CoreException, AppException {
		
		Moment moment = MomentCaps.getMomentByID(momentID);
		if (moment == null) {
			throw new AppException("该动态不存在"); 
		}
		
		if (moment.getUserID() != optUserID) {
			throw new AppException("你无权删除该动态"); 
		}
		
		MomentCaps.delMomentByID(momentID);
	}
	
	public static MomentEntity getRichMomentEntityByID(long momentID) throws CoreException, AppException {
		Moment moment = MomentCaps.getMomentByID(momentID);
		
		if (moment == null) {
			throw new AppException("动态不存在");
		}
		
		List<MomentLike> momentLikes = MomentLikeCaps.getMomentLikesByMomentID(momentID);
		List<MomentReply> momentReplies = MomentReplyCaps.getMomentRepliesByMomentID(momentID);
		
		MomentEntity transferMomentEntity = getSimpleMomentEntityByIDNoExp(moment.getTranferID());
		
		Set<Long> preLoadUserIDs = new HashSet<Long>();
		
		preLoadUserIDs.add(moment.getUserID());
		if (transferMomentEntity != null) {
			preLoadUserIDs.add(transferMomentEntity.getUserID());
		}
		
		for (MomentLike like : momentLikes) {
			preLoadUserIDs.add(like.getUserID());
		}
		
		for (MomentReply reply : momentReplies) {
			preLoadUserIDs.add(reply.getUserID());
			if (reply.getReplyID() > 0) {
				preLoadUserIDs.add(reply.getReplyID());
			}
		}
		
		Map<Long, UserEntity> users = UserCore.getSimpleUserMapByIDs(new ArrayList<Long>(preLoadUserIDs));
		
		
		MomentEntity momentEntity = MomentHepler.domain2Entity(moment);
		if (momentEntity == null) {
			throw new AppException("动态不存在");
		}
		
		if (users.get(momentEntity.getUserID()) == null) {
			logger.debug(String.format("[data error] momentID : %d , userID : %d", momentEntity.getMomentID(), momentEntity.getUserID()));
			throw new AppException("数据错误");
		}
		
		momentEntity.setUser(users.get(momentEntity.getUserID()));
		
		
		if (transferMomentEntity != null) {
			UserEntity userEntity = users.get(transferMomentEntity.getUserID());
			if (userEntity != null) {
				transferMomentEntity.setUser(userEntity);
				momentEntity.setTransferMomentEntity(transferMomentEntity);
			}
		}
		
		for (MomentLike like : momentLikes) {
			UserEntity userEntity = users.get(like.getUserID());
			if (userEntity != null) {
				momentEntity.getLikeUsers().add(userEntity);
			}
		}

		List<MomentReplyEntity> replyEntities = MomentHepler.domain2ReplyEntity(momentReplies);
		for (MomentReplyEntity momentReplyEntity : replyEntities) {
			UserEntity userEntity = users.get(momentReplyEntity.getUserID());
			if (userEntity == null) {
				continue;
			}
			
			momentReplyEntity.setUser(userEntity);
			momentReplyEntity.setReplyUser(users.get(momentReplyEntity.getReplyID()));
			momentEntity.getReplies().add(momentReplyEntity);
		}
		
		
		return momentEntity;
	}
	
	public static MomentEntity getSimpleMomentEntityByIDNoExp(long momentID) throws CoreException, AppException {
		if (momentID == 0) {
			return null;
		}
		
		Moment moment = MomentCaps.getMomentByID(momentID);
		
		if (moment == null) {
			return null;
		}
		
		return MomentHepler.domain2Entity(moment);
	}
	
	
	public static List<MomentEntity> getUserHomeRichMoments(long userID, long start, int count) throws CoreException, AppException {
		
		List<Moment> moments = MomentCaps.getMomentsByUserID(userID, start, count);
		return parseMomentEntities(moments);
	}
	
	public static List<MomentEntity> getUserRichMoments(long userID, long start, int count) throws CoreException, AppException {
		
		List<Long> followUserIDs = ContactCore.getFollowByUser(userID);
		followUserIDs.add(userID);
		List<Moment> moments = MomentCaps.getMomentsByUserIDs(followUserIDs, start, count);
		return parseMomentEntities(moments);
		
	}
 
	
	private static List<MomentEntity> parseMomentEntities(List<Moment> moments) throws CoreException, AppException {
		
		List<MomentEntity> res = new ArrayList<MomentEntity>();
		
		if (moments == null || moments.isEmpty()) {
			return res;
		}
		
		Set<Long> preLoadMomentIDs = new HashSet<Long>();
		Set<Long> preLoadTransferMomentIDs = new HashSet<Long>();
		Set<Long> preLoadUserIDs = new HashSet<Long>();
		for (Moment moment : moments) {
			preLoadMomentIDs.add(moment.getId());
			preLoadUserIDs.add(moment.getUserID());
			if (moment.getTranferID() > 0) {
				preLoadTransferMomentIDs.add(moment.getTranferID());
			}
		}
		
		logger.debug("songming here preLoadTransferMomentIDs : " + JSON.toJsonString(preLoadTransferMomentIDs));

		
		List<Moment> transferMoments = MomentCaps.getMomentsByIDs(new ArrayList<Long>(preLoadTransferMomentIDs));
		Map<Long, Moment> transferMomentsMap = MomentHepler.momentlist2Map(transferMoments);
		List<MomentLike> momentLikesAll = MomentLikeCaps.getMomentLikesByMomentIDs(new ArrayList<Long>(preLoadMomentIDs));
		Map<Long, Set<MomentLike>> momentLikesAllMap = MomentHepler.momentLikelist2Map(momentLikesAll);
		List<MomentReply> momentRepliesAll = MomentReplyCaps.getMomentRepliesByMomentIDs(new ArrayList<Long>(preLoadMomentIDs));
		Map<Long, Set<MomentReply>> momentRepliesAllMap = MomentHepler.momentReplylist2Map(momentRepliesAll);
		
		
		for (MomentLike like : momentLikesAll) {
			preLoadUserIDs.add(like.getUserID());
		}
		
		for (MomentReply reply : momentRepliesAll) {
			preLoadUserIDs.add(reply.getUserID());
			if (reply.getReplyID() > 0) {
				preLoadUserIDs.add(reply.getReplyID());
			}
		}
		
		for (Moment moment : transferMoments) {
			preLoadUserIDs.add(moment.getUserID());
		}
		
		Map<Long, UserEntity> users = UserCore.getSimpleUserMapByIDs(new ArrayList<Long>(preLoadUserIDs));
		
		
		logger.debug("songming here transferMomentsMap : " + JSON.toJsonString(transferMomentsMap));
		
		for (Moment moment : moments) {
			
			long momentID = moment.getId();
			
			List<MomentLike> momentLikes = momentLikesAllMap.get(momentID) == null ? 
												new ArrayList<MomentLike>() : new ArrayList<MomentLike>(momentLikesAllMap.get(momentID));
			List<MomentReply> momentReplies = momentRepliesAllMap.get(momentID) == null ? 
												new ArrayList<MomentReply>() : new ArrayList<MomentReply>(momentRepliesAllMap.get(momentID));
			
			MomentEntity transferMomentEntity = MomentHepler.domain2Entity(transferMomentsMap.get(moment.getTranferID()));
			
			logger.debug("songming here transferMomentEntity : " + JSON.toJsonString(transferMomentEntity));
			
			MomentEntity momentEntity = MomentHepler.domain2Entity(moment);
			if (momentEntity == null) {
				throw new AppException("动态不存在");
			}
			
			if (users.get(momentEntity.getUserID()) == null) {
				logger.debug(String.format("[data error] momentID : %d , userID : %d", momentEntity.getMomentID(), momentEntity.getUserID()));
				throw new AppException("数据错误");
			}
			
			
			momentEntity.setUser(users.get(momentEntity.getUserID()));
			
			if (transferMomentEntity != null) {
				logger.debug("songming here1");
				UserEntity userEntity = users.get(transferMomentEntity.getUserID());
				if (userEntity != null) {
					logger.debug("songming here2");
					transferMomentEntity.setUser(userEntity);
					momentEntity.setTransferMomentEntity(transferMomentEntity);
				}
			}
			
			for (MomentLike like : momentLikes) {
				UserEntity userEntity = users.get(like.getUserID());
				if (userEntity != null) {
					momentEntity.getLikeUsers().add(userEntity);
				}
			}

			List<MomentReplyEntity> replyEntities = MomentHepler.domain2ReplyEntity(momentReplies);
			for (MomentReplyEntity momentReplyEntity : replyEntities) {
				UserEntity userEntity = users.get(momentReplyEntity.getUserID());
				if (userEntity == null) {
					continue;
				}
				
				momentReplyEntity.setUser(userEntity);
				momentReplyEntity.setReplyUser(users.get(momentReplyEntity.getReplyID()));
				momentEntity.getReplies().add(momentReplyEntity);
			}
			
			res.add(momentEntity);
		}
		
		return res;
	}
	
	/** -----------------------动态评论相关---------------------- **/
	
	public static void createMomentReply(long userID, long momentID, long replyUserID, String content) throws AppException, CoreException {
		if (momentID <= 0) {
			throw new AppException("该动态不存在");
		}
		
		if (userID <= 0 || !UserCore.existUserID(userID)) {
			throw new AppException("该用户不存在");
		}
		
		if (replyUserID > 0 && !UserCore.existUserID(userID)) {
			throw new AppException("该用户不存在");
		}
		
		if (StringUtils.isBlank(content)) {
			throw new AppException("评论内容不能为空");
		}
		
		MomentReply domain = new MomentReply();
		domain.setContent(content);
		domain.setMomentID(momentID);
		domain.setCreateAt(new Date());
		domain.setUserID(userID);
		
		if (replyUserID > 0) {
			domain.setReplyID(replyUserID);
		}
		MomentReplyCaps.insertMomentReply(domain);
		MomentCaps.incrReplyCount(momentID);
	}
	
	public static void delMomentReplyByID(long userID, long replyID) throws CoreException, AppException {
		
		MomentReply momentReply = MomentReplyCaps.getMomentReplyByID(replyID);
		if (momentReply == null) {
			throw new AppException("该评论不存在");
		}
		Moment moment = MomentCaps.getMomentByID(momentReply.getMomentID());
		if (moment == null) {
			throw new AppException("该动态不存在");
		}
		
		if (moment.getUserID() != userID || momentReply.getUserID() != userID) {
			throw new AppException("你无权删除该评论");
		}
		
		MomentReplyCaps.delMomentReplyByID(replyID);
		MomentCaps.descReplyCount(momentReply.getMomentID());
	}
	
	/** -----------------------动态点赞相关---------------------- **/
	public static void createMomentLike(long userID, long momentID, byte type) throws AppException, CoreException {
		if (momentID <= 0) {
			throw new AppException("该动态不存在");
		}
		
		if (userID <= 0 || !UserCore.existUserID(userID)) {
			throw new AppException("该用户不存在");
		}
		
		if (type != Status.MomentLike.CANCEL && type != Status.MomentLike.NORMAL) {
			throw new AppException("参数错误");
		}
		
		MomentLike momentLike = MomentLikeCaps.getMomentLike(userID, momentID);
		if (momentLike == null) {
			if (type == Status.MomentLike.CANCEL) {
				throw new AppException("你还没为该动态点赞");
			}
			MomentLike domain = new MomentLike();
			domain.setCreateAt(new Date());
			domain.setMomentID(momentID);
			domain.setUserID(userID);
			domain.setStatus(type);
			MomentLikeCaps.insertMomentLike(domain);
			MomentCaps.incrLikeCount(momentID);
		} else {
			if (type == Status.MomentLike.NORMAL) {
				if (momentLike.getStatus() != type) {
					MomentLikeCaps.like(userID, momentID);
					MomentCaps.incrLikeCount(momentID);
				}
			} else {
				if (momentLike.getStatus() != type) {
					MomentLikeCaps.cancel(userID, momentID);
					MomentCaps.descLikeCount(momentID);
				}
			}
		}
		
		
	}
	
}
