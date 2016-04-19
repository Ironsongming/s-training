package com.csm.straining.dataaccess.mapper.article;

/**
 * 
 * @author chensongming
 *
 */

public interface ArticleCusMapper {

	void incrReadCount(long articleID);
	
	void incrLikeCount(long articleID);
	
	void incrDislikeCount(long articleID);
	
	void incrCommentCount(long articleID);
	
	void descCommentCount(long articleID);
	
}
