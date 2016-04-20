package com.csm.straining.user.resp.contact;

import java.util.ArrayList;
import java.util.List;

import com.csm.straining.common.i.contact.info.GroupInfo;
import com.csm.straining.common.model.info.common.PagedResponseStatus;


/**
 * @author chensongming
 */
public class GroupListResp extends PagedResponseStatus{
	
	public List<GroupInfo> groups = new ArrayList<GroupInfo>();

}
