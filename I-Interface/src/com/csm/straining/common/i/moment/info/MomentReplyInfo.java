package com.csm.straining.common.i.moment.info;

import java.io.Serializable;

import com.csm.straining.common.i.user.info.UserInfo;


/**
 * @author chensongming
 */
public class MomentReplyInfo implements Serializable{

	private static final long serialVersionUID = 5235296676635050866L;
	
	public long replyID;

	public long momentID;

	public UserInfo user;

    public String text;

    public UserInfo replyUser;

    public long createAt;
    
    public int canDelete;
    
}
