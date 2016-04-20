package com.csm.straining.dataaccess.mapper.contact;

import java.util.List;

import com.csm.straining.dataaccess.entity.contact.GroupUser;

/**
 * 
 * @author chensongming
 *
 */

public interface GroupCusMapper {

	void incrGroupMemberCount(long groupID);
	
	void descGroupMemberCount(long groupID);
	
	void multiInsertGroupUser(List<GroupUser> groupUsers);
	
}
