package com.csm.straining.common.i.contact.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.csm.straining.common.i.user.entity.UserEntity;


/**
 * @author chensongming
 */
public class GroupEntity implements Serializable{

	private static final long serialVersionUID = 56793602935975217L;
	
    private long groupID;

    private String groupName;

    private int memberCount;
    
    // 预加载字段
    private List<UserEntity> userEntities = new ArrayList<UserEntity>();

	public long getGroupID() {
		return groupID;
	}

	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public List<UserEntity> getUserEntities() {
		return userEntities;
	}

	public void setUserEntities(List<UserEntity> userEntities) {
		this.userEntities = userEntities;
	}
	

}
