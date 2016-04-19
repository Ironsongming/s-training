package com.csm.straining.common.i.article.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csm.straining.common.i.user.entity.UserEntity;


/**
 * @author chensongming
 */
public class ArticleEntity implements Serializable{

	private static final long serialVersionUID = -6098319049674532227L;
	
	
	// 基本字段
	private long id;

	private long userID;

    private String title;

    private String content;

    private long createAt;

    private Byte status;

    private int readCount;

    private int likeCount;

    private int dislikeCount;

    private int commentCount;
    
    // 可预加载字段
    private UserEntity user;
    private List<ArticleCommentEntity> comments = new ArrayList<ArticleCommentEntity>();
    
    public UserEntity getUser() {
		return user;
	}
    
    public void setUser(UserEntity user) {
		this.user = user;
	}
    
    public List<ArticleCommentEntity> getComments() {
		return this.comments;
	}
    
    public void setComments(List<ArticleCommentEntity> comments) {
    	if (comments != null) {
    		this.comments = comments;    		
    	} else {
    		this.comments = new ArrayList<ArticleCommentEntity>();
    	}
	}
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getDislikeCount() {
		return dislikeCount;
	}

	public void setDislikeCount(int dislikeCount) {
		this.dislikeCount = dislikeCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
    
    

}
