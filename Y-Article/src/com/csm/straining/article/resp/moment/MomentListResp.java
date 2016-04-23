package com.csm.straining.article.resp.moment;

import java.util.ArrayList;
import java.util.List;

import com.csm.straining.common.i.moment.info.MomentInfo;
import com.csm.straining.common.model.info.common.PagedResponseStatus;


/**
 * @author chensongming
 */
public class MomentListResp extends PagedResponseStatus{
	
	public List<MomentInfo> moments = new ArrayList<MomentInfo>();

}
