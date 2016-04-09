package com.csm.straining.core.user.util;

import com.csm.straining.common.i.user.entity.UserEntity;
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
	
	

}
