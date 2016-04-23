package com.csm.straining.core.moment.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.csm.straining.common.i.moment.entity.MomentEntity;
import com.csm.straining.common.i.moment.entity.MomentReplyEntity;
import com.csm.straining.dataaccess.entity.moment.Moment;
import com.csm.straining.dataaccess.entity.moment.MomentLike;
import com.csm.straining.dataaccess.entity.moment.MomentReply;


/**
 * @author chensongming
 */
public class MomentHepler {
	
	public static MomentEntity domain2Entity(Moment domain) {
		if (domain == null) {
			return null;
		}
		
		MomentEntity entity = new MomentEntity();
		entity.setMomentID(domain.getId());
		entity.setUserID(domain.getUserID());
		entity.setContent(domain.getContent());
		entity.setTranferID(domain.getTranferID());
		entity.setCreateAt(domain.getCreateAt().getTime());
		entity.setStatus(domain.getStatus());
		entity.setLikeCount(domain.getLikeCount());
		entity.setReplyCount(domain.getReplyCount());
		entity.setTranferCount(domain.getTranferCount());

		return entity;
	}
	
	public static List<MomentEntity> domain2Entity(List<Moment> domains) {
		if (domains == null || domains.isEmpty()) {
			return new ArrayList<MomentEntity>();
		}
		
		List<MomentEntity> entities = new ArrayList<MomentEntity>();
		for (Moment moment : domains) {
			entities.add(domain2Entity(moment));
		} 

		return entities;
	}
	
	public static List<MomentReplyEntity> domain2ReplyEntity(List<MomentReply> domains) {
		if (domains == null || domains.isEmpty()) {
			return new ArrayList<MomentReplyEntity>();
		}
		
		List<MomentReplyEntity> entities = new ArrayList<MomentReplyEntity>();
		for (MomentReply reply : domains) {
			MomentReplyEntity entity = new MomentReplyEntity();
			entity.setId(reply.getId());
			entity.setMomentID(reply.getMomentID());
			entity.setUserID(reply.getUserID());
			entity.setContent(reply.getContent());
			entity.setReplyID(reply.getReplyID());
			entity.setCreateAt(reply.getCreateAt().getTime());
			entity.setStatus(reply.getStatus());
			
			entities.add(entity);
		}
		
		return entities;
	}
	
	public static Map<Long, Moment> momentlist2Map(List<Moment> moments) {
		if (moments == null || moments.isEmpty()) {
			return new HashMap<Long, Moment>();
		}
		
		Map<Long, Moment> res = new HashMap<Long, Moment>();
		for (Moment moment : moments) {
			res.put(moment.getId(), moment);
		}
		
		return res;
	}
	
	public static Map<Long, Set<MomentReply>> momentReplylist2Map(List<MomentReply> momentReplies) {
		if (momentReplies == null || momentReplies.isEmpty()) {
			return new HashMap<Long, Set<MomentReply>>();
		}
		Map<Long, Set<MomentReply>> res = new HashMap<Long, Set<MomentReply>>();
		for (MomentReply momentReply : momentReplies) {
			if (res.get(momentReply.getMomentID()) == null) {
				Set<MomentReply> resSet = new HashSet<MomentReply>();
				res.put(momentReply.getMomentID(), resSet);
			}
			res.get(momentReply.getMomentID()).add(momentReply);
		}
		return res;
	}
	
	public static Map<Long, Set<MomentLike>> momentLikelist2Map(List<MomentLike> momentLikes) {
		if (momentLikes == null || momentLikes.isEmpty()) {
			return new HashMap<Long, Set<MomentLike>>();
		}
		Map<Long, Set<MomentLike>> res = new HashMap<Long, Set<MomentLike>>();
		for (MomentLike momentLike : momentLikes) {
			if (res.get(momentLike.getMomentID()) == null) {
				Set<MomentLike> resSet = new HashSet<MomentLike>();
				res.put(momentLike.getMomentID(), resSet);
			}
			res.get(momentLike.getMomentID()).add(momentLike);
		}
		return res;
	}
		
}
