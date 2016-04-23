package com.csm.straining.common.i.moment.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.csm.straining.common.i.user.entity.UserEntity;


/**
 * @author chensongming
 */
public class MomentEntity implements Serializable {

	private static final long serialVersionUID = 7897332061081952819L;
	
	// 基本字段
	private long momentID;

    private long userID;

    private String content;

    private long tranferID;

    private long createAt;

    private byte status;

    private int likeCount;

    private int replyCount;

    private int tranferCount;
    
    // 预加载字段
    private UserEntity user;
    private MomentEntity transferMomentEntity;
    private List<UserEntity> likeUsers = new ArrayList<UserEntity>();
    private List<MomentReplyEntity> replies = new ArrayList<MomentReplyEntity>();
    
    
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
	public long getTranferID() {
		return tranferID;
	}
	public void setTranferID(long tranferID) {
		this.tranferID = tranferID;
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
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public int getTranferCount() {
		return tranferCount;
	}
	public void setTranferCount(int tranferCount) {
		this.tranferCount = tranferCount;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public MomentEntity getTransferMomentEntity() {
		return transferMomentEntity;
	}
	public void setTransferMomentEntity(MomentEntity transferMomentEntity) {
		this.transferMomentEntity = transferMomentEntity;
	}
	public List<UserEntity> getLikeUsers() {
		return likeUsers;
	}
	public void setLikeUsers(List<UserEntity> likeUsers) {
		this.likeUsers = likeUsers;
	}
    public List<MomentReplyEntity> getReplies() {
		return replies;
	}
    public void setReplies(List<MomentReplyEntity> replies) {
		this.replies = replies;
	}
    
    
	

}
