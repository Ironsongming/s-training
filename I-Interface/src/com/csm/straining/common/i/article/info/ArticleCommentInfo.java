package com.csm.straining.common.i.article.info;

import java.io.Serializable;

import com.csm.straining.common.i.user.info.UserInfo;


/**
 * @author chensongming
 */
public class ArticleCommentInfo implements Serializable{

	private static final long serialVersionUID = -5719236582146824719L;
	
	public long articleCommentID;
	
	public UserInfo user;
	
	public UserInfo replyUser;
	
	public String content;
	
	// 页面逻辑字段 0:否 1:是
	public byte canDelete = 0;
	
}
