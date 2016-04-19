package com.csm.straining.common.i.article.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.csm.straining.common.i.user.info.UserInfo;


/**
 * @author chensongming
 */
public class ArticleInfo implements Serializable{

	private static final long serialVersionUID = 8683817837923012804L;
	
	public long articleID;
	public UserInfo user;
	public String title;
	public String content;
	public long createAt;
	public int readCount;
	public int likeCount;
	public int dislikeCount;
	public int commentCount;
	
	public List<ArticleCommentInfo> comments = new ArrayList<ArticleCommentInfo>();
	
	// 页面逻辑字段 0:否 1:是
	public byte canDelete = 0;

}
