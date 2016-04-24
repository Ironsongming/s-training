package com.csm.straining.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.strainging.cache.impl.session.SessionKeyCache;
import com.csm.strainging.cache.impl.session.SessionOfflineKeyCache;
import com.csm.strainging.cache.impl.session.SessionServerCache;
import com.csm.strainging.cache.impl.user.UserInfoCache;
import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.i.Status;
import com.csm.straining.common.i.user.entity.UserEntity;
import com.csm.straining.common.i.user.info.UserInfo;
import com.csm.straining.common.i.user.params.UserParams;
import com.csm.straining.common.model.info.common.ResponseStatus;
import com.csm.straining.common.socket.netkit.message.Message;
import com.csm.straining.common.socket.server.util.MessageUtil;
import com.csm.straining.common.util.CommonUtil;
import com.csm.straining.common.util.ImageUtil;
import com.csm.straining.core.user.util.UserHelper;
import com.csm.straining.repeater.client.MessageRepeaterClient;
import com.csm.straining.repeater.client.RepeaterCode;
import com.csm.straining.repeater.client.RepeaterMessage;
import com.csm.straining.user.refer.UserServiceReference;
import com.csm.straining.user.resp.PhoneLoginResp;
import com.csm.straining.user.resp.UserCreateResp;
import com.csm.straining.user.resp.UserDetailResp;
import com.csm.straining.user.resp.UserListResp;
import com.lamfire.utils.JSON;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	
	public static UserListResp getFollowByUserID(long userID) throws CoreException {
		List<UserEntity> users = UserServiceReference.sharedService().getFollowEntityByUser(userID);
		
		UserListResp resp = new UserListResp();
		for (UserEntity userEntity : users) {
			resp.users.add(UserHelper.entity2Info(userEntity));
		}
		
		return resp;
	}
		
	public static UserCreateResp userCreateResp(String phone, String password) throws CoreException, AppException {
		
		UserCreateResp resp = new UserCreateResp();
		
		UserEntity userEntity = UserServiceReference.sharedService().createUserAccount(phone, password);
		
		// 添加Session
		String key = assignAndLogin(userEntity.getUserID());
		resp.key = key;
		resp.userID = userEntity.getUserID();
		
		return resp;
	}
	
	public static PhoneLoginResp phoneLoginResp(String phone, String password) throws CoreException, AppException {
		PhoneLoginResp resp = new PhoneLoginResp();
		
		long userID = UserServiceReference.sharedService().loginByPhonePwd(phone, password);
		
		// 添加Session
		String key = assignAndLogin(userID);
		
		UserEntity user = UserServiceReference.sharedService().getUserByID(userID);
		
		if (user == null || user.getStatus() != Status.User.NORMAL) {
			logger.debug("data error loginByPhonePwd & getUserByID from user Status: " + user == null ? "user is null" : user.getStatus() + "");
		}
		
		resp.key = key;
		resp.userID = user.getUserID();
		
		return resp;
	}
	
	private static String assignAndLogin(long userID) {
		
		String sessionKey = CommonUtil.getUUID(userID);
		
		SessionKeyCache.setAndExpire(sessionKey, userID);
		
		String oldKey = UserInfoCache.getSessionKey(userID);
		if (null != oldKey) {
			SessionKeyCache.del(oldKey);
			SessionOfflineKeyCache.setAndExpire(oldKey, userID);
			
			// forceOffline
			logger.debug("assignAndLogin forceOffline begin");
			
			Map<String , Object> data = new HashMap<String, Object>();
			data.put("msg:", "你的账号在别处登录");
			
			int serverID = SessionServerCache.getServerID(userID);
			if (serverID <= 0) {
				logger.debug("assignAndLogin forceOffline error");
			} else {
				RepeaterMessage repeaterMessage = new RepeaterMessage();
				repeaterMessage.setMsg(data);
				repeaterMessage.setTargetClientID(serverID);
				repeaterMessage.setTargetID(userID);	
				Message message = MessageUtil.getMessage(RepeaterCode.ForceOfflineSendPID.REQUEST, JSON.toJsonString(repeaterMessage));
				MessageRepeaterClient.ins().send(message);
			}
			
			logger.debug("assignAndLogin forceOffline finish");		
		}
		
		UserInfoCache.setSessionKey(userID, sessionKey);
		
		return sessionKey;
	}
	
	public static UserDetailResp userDetailResp(long optUserID, long targetUserID) throws CoreException, AppException {
		UserDetailResp resp = new UserDetailResp();
		UserInfo user = new UserInfo();
		resp.user = user;

		UserEntity userEntity = UserServiceReference.sharedService().getUserByID(targetUserID);
		if (userEntity == null) {
			throw new AppException("用户不存在");
		}
		
		user.userID = userEntity.getUserID();
		user.username = userEntity.getUsername();
		user.phone = userEntity.getPhone();
		user.signNature = StringUtils.isBlank(userEntity.getSignNature()) ? "该用户很懒，什么也没留下" : userEntity.getSignNature();
		user.avatar = StringUtils.isBlank(userEntity.getAvatar()) ? "" : ImageUtil.getLoadPath(userEntity.getAvatar());
		user.status = userEntity.getStatus();
		user.rank = userEntity.getRank();
		user.score = userEntity.getScore();
		
		user.canEdit = optUserID == targetUserID ? 1 : 0;
		
		return resp;
	}
	
	public static ResponseStatus userUpdateDetailResp(long optUserID, UserParams params) throws CoreException, AppException {
		UserServiceReference.sharedService().updateUserDetail(optUserID, params);
		return new ResponseStatus();
	}

	public static UserListResp userRankTop20Resp() throws CoreException, AppException {
		List<UserEntity> users = UserServiceReference.sharedService().getUserEntitiesByScoreTop20();
			
		UserListResp resp = new UserListResp();
		for (UserEntity userEntity : users) {
			resp.users.add(UserHelper.entity2Info(userEntity));
		}
		
		return resp;
	}
	
	
}
