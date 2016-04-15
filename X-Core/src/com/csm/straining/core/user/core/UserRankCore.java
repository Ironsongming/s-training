package com.csm.straining.core.user.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.dataaccess.caps.user.UserRankCaps;
import com.csm.straining.dataaccess.entity.user.UserRank;


/**
 * @author chensongming
 */
public class UserRankCore {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRankCore.class);
	
	public static void insertUserRank (long userID, int score) throws CoreException, AppException {
		
		if (userID < 0) {
			throw new AppException("该用户不存在");
		}
		
		UserRank domain = new UserRank();
		domain.setUserID(userID);
		domain.setScore(score);
		domain.setRank(score2rank(score));
		UserRankCaps.insertUserRank(domain);
	}
	
	public static void updateUserRank(long userID, int score) throws CoreException, AppException {
		UserRank domain = UserRankCaps.getUserRankByUserID(userID);
		UserRank userRank = new UserRank();
		if (domain == null) {
			insertUserRank(userID, score);
			return;
		} else {
			int oldScore = domain.getScore();
			userRank.setScore(oldScore + score);
			userRank.setRank(score2rank(oldScore + score));
			UserRankCaps.updateUserRank(userID, userRank);
		}
	}
	
	public static UserRank getUserRankByUserID(long userID) throws CoreException {
		return UserRankCaps.getUserRankByUserID(userID);
	}
	
	private static int score2rank(int score) {
		
		if (score <= 100) {
			return 0;
		} else if (score <= 200) {
			return 1;
		} else if (score <= 300) {
			return 2;
		} else if (score <= 400) {
			return 3;
		} else if (score <= 500) {
			return 4;
		} else if (score <= 600) {
			return 5;
		} else if (score <= 700) {
			return 6;
		} else if (score <= 800) {
			return 7;
		} else if (score <= 900) {
			return 8;
		} else if (score <= 1000) {
			return 9;
		} else if (score <= 1100) {
			return 10;
		} else {
			return 10;
		}
		
	}

}
