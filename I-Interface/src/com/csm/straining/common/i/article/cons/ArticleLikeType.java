package com.csm.straining.common.i.article.cons;


/**
 * @author chensongming
 */
public enum ArticleLikeType {
	
	LIKE((byte)1),
	DISLIKE((byte)2);
	
	
	private byte val;
	
	private ArticleLikeType(byte val) {
		this.val = val;
	}

	public byte getVal() {
		return this.val;
	}
	
	public static ArticleLikeType valueof(byte val) {
		for (ArticleLikeType articleLikeType : ArticleLikeType.values()) {
			if (articleLikeType.getVal() == val) {
				return articleLikeType;
			}
		}
		
		return null;
	}
	
	public static boolean isValueof(byte val) {
		for (ArticleLikeType articleLikeType : ArticleLikeType.values()) {
			if (articleLikeType.getVal() == val) {
				return true;
			}
		}
		
		return false;
	}
	
	
}
