package com.csm.straining.common.i;


/**
 * @author chensongming
 */
public class Status {
	
	/** -------------用户相关----------------- **/
	public static interface User {
		public static final byte NORMAL = 1;
		public static final byte OTHER = 2;
	}
	
	/** -------------文章相关----------------- **/
	public static interface Article {
		public static final byte NORMAL = 1;
		public static final byte DELETED = 2;
	}
	
	public static interface ArticleComment {
		public static final byte NORMAL = 1;
		public static final byte DELETED = 2;
	}

	public static interface ArticleLike {
		public static final byte NORMAL = 1;
		public static final byte OTHER = 2;
	}
	
	/** -------------好友相关----------------- **/
	public static interface Follow {
		public static final byte NORMAL = 1;
		public static final byte DELETE = 2;
	}
	
	public static interface GroupUser {
		public static final byte NORMAL = 1;
		public static final byte QUIT = 2;
	}
	
	
}
