package com.csm.straining.common.i.contact.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.csm.straining.common.i.user.info.UserInfo;


/**
 * @author chensongming
 */
public class GroupInfo implements Serializable{

	private static final long serialVersionUID = -747447017330090402L;
	
	public long groupID;

	public String groupName;

	public int memberCount;
	
	public List<UserInfo> members = new ArrayList<UserInfo>();

}
