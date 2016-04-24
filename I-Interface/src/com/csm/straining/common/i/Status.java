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
	
	/** -------------动态相关----------------- **/
	public static interface Moment {
		public static final byte NORMAL = 1;
		public static final byte DELETE = 2;
	}
	
	public static interface MomentReply {
		public static final byte NORMAL = 1;
		public static final byte DELETE = 2;
	}
	
	public static interface MomentLike {
		public static final byte NORMAL = 1;
		public static final byte CANCEL = 2;
	}
	
	/** -------------健身记录相关----------------- **/
	public static interface Training {
		public static final byte CREATE = 1;
		public static final byte START = 2;
		public static final byte FINISH = 3;
	}
}
