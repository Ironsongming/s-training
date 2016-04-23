package com.csm.straining.dataaccess.mapper.moment;

public interface MomentCusMapper {
	
	void incrTranferCount(long momentID);
	void incrReplyCount(long momentID);
	void incrLikeCount(long momentID);

	void descReplyCount(long momentID);
	void descLikeCount(long momentID);

}