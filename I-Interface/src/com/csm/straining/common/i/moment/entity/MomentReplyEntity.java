package com.csm.straining.common.i.moment.entity;

import java.io.Serializable;

import com.csm.straining.common.i.user.entity.UserEntity;


/**
 * @author chensongming
 */
public class MomentReplyEntity implements Serializable{
	
	private static final long serialVersionUID = -7767803765470706382L;
	
	// 基本字段
    private long id;

    private long momentID;

    private long userID;

    private String content;

    private long replyID;

    private long createAt;

    private byte status;
	
    // 预加载字段
    private UserEntity user;
    private UserEntity replyUser;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMomentID() {
		return momentID;
	}
	public void setMomentID(long momentID) {
		this.momentID = momentID;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getReplyID() {
		return replyID;
	}
	public void setReplyID(long replyID) {
		this.replyID = replyID;
	}
	public long getCreateAt() {
		return createAt;
	}
	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public UserEntity getReplyUser() {
		return replyUser;
	}
	public void setReplyUser(UserEntity replyUser) {
		this.replyUser = replyUser;
	}

    
    

}
