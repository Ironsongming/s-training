package com.csm.straining.common.i.article.entity;

import java.io.Serializable;
import java.util.Date;

import com.csm.straining.common.i.user.entity.UserEntity;


/**
 * @author chensongming
 */
public class ArticleCommentEntity implements Serializable{
	
	private static final long serialVersionUID = -6935780916959413252L;

	// 基本字段
	private Long id;

	private Long articleID;

    private long userID;

    private long replyUserID;

    private String content;

    private long createAt;

    private Byte status;

	// 可预加载字段
    private UserEntity user;
    private UserEntity replyUser;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getArticleID() {
		return articleID;
	}
	public void setArticleID(Long articleID) {
		this.articleID = articleID;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public long getReplyUserID() {
		return replyUserID;
	}
	public void setReplyUserID(long replyUserID) {
		this.replyUserID = replyUserID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getCreateAt() {
		return createAt;
	}
	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
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
