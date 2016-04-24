package com.csm.straining.core.user.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.common.i.user.info.UserInfo;
import com.csm.straining.common.i.user.params.UserParams;
import com.csm.straining.common.util.ImageUtil;
import com.csm.straining.dataaccess.entity.user.User;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class UserHelper {
	
	public static UserEntity domain2Entity(User domain) {
		
		if (null == domain) {
			return null;
		}
		
		UserEntity entity = new UserEntity();
		
		entity.setUserID(domain.getId());
		entity.setUsername(domain.getUsername());
		entity.setPhone(domain.getPhone());
		entity.setSignNature(domain.getSignNature());
		entity.setStatus(domain.getStatus());
		
		if (StringUtils.isNotBlank(domain.getAvatar())) {
			entity.setAvatar(ImageUtil.getLoadPath(domain.getAvatar()));
		} else {
			entity.setAvatar(ImageUtil.getDefaultAvatarLoadPath());
		}
		
		if (StringUtils.isNotBlank(domain.getTags())) {
			String[] tags = domain.getTags().split(",");
			for (String tag : tags) {
				entity.getTags().add(tag);
			}
		}
		
		// TODO 装载Loaction和occupation
		
		return entity;
	}
	
	public static List<UserEntity> domain2Entity(List<User> domains) {
		
		if (domains == null || domains.isEmpty()) {
			return new ArrayList<UserEntity>();
		}
		
		List<UserEntity> entities = new ArrayList<UserEntity>();
		 
		for (User domain : domains) {
			entities.add(domain2Entity(domain));
		} 
		return entities;
	}
	
	public static Map<Long, UserEntity> domain2EntityMap(List<User> domains) {
		
		Map<Long, UserEntity> entities = new HashMap<Long, UserEntity>();
		
		if (domains == null || domains.isEmpty()) {
			return entities;
		}
		 
		for (User domain : domains) {
			entities.put(domain.getId(), domain2Entity(domain));
		} 
		return entities;
	}
	
	public static User params2Domain(UserParams params) {
		if (params == null) {
			return null;
		}
		User domain = new User();
		
		domain.setId(params.getUserID());
		domain.setAvatar(params.getAvatar());
		domain.setSignNature(params.getSignNature());
		domain.setTags(params.getTags());
		domain.setUsername(params.getUsername());
		
		// TODO 装载Loaction和occupation
		
		return domain;
	}
	
	public static UserInfo entity2Info(UserEntity entity) {
		if (entity == null) {
			return null;
		}
		UserInfo info = new UserInfo();
		info.userID = entity.getUserID();
		info.username = entity.getUsername();
		info.phone = entity.getPhone();
		info.signNature = StringUtils.isBlank(entity.getSignNature()) ? "该用户很懒，什么也没留下" : entity.getSignNature();
		info.avatar = entity.getAvatar();
		info.status = entity.getStatus();
		return info;
	}
	

}
