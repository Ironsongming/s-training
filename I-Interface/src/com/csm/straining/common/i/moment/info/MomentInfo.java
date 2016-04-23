package com.csm.straining.common.i.moment.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.csm.straining.common.i.user.info.UserInfo;


/**
 * @author chensongming
 */
public class MomentInfo implements Serializable{

	private static final long serialVersionUID = 4674359000442311381L;
	
	public long momentID;

    public UserInfo user;

    public String text;

    public MomentInfo tranferMoment;

    public long createAt;

    public int likeCount;

    public int replyCount;

    public int tranferCount;
    
    public List<UserInfo> likeUsers = new ArrayList<UserInfo>();
    
    public List<MomentReplyInfo> replys = new ArrayList<MomentReplyInfo>();
    
    public int canDelete = 0;

}
