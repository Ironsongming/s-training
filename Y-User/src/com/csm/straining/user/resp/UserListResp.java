package com.csm.straining.user.resp;

import java.util.ArrayList;
import java.util.List;

import com.csm.straining.common.i.user.info.UserInfo;
import com.csm.straining.common.model.info.common.PagedResponseStatus;


/**
 * @author chensongming
 */
public class UserListResp extends PagedResponseStatus {
	
	public List<UserInfo> users = new ArrayList<UserInfo>();
}
