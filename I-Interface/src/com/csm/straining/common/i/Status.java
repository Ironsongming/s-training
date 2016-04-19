package com.csm.straining.common.i;


/**
 * @author chensongming
 */
public class Status {
	
	public static interface User {
		public static final byte NORMAL = 1;
		public static final byte OTHER = 2;
	}
	
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
}
