package com.csm.straining.article.resp;

import java.util.ArrayList;
import java.util.List;

import com.csm.straining.common.i.article.info.ArticleInfo;
import com.csm.straining.common.model.info.common.PagedResponseStatus;


/**
 * @author chensongming
 */
public class ArticleListResp extends PagedResponseStatus{
	
	public List<ArticleInfo> articles = new ArrayList<ArticleInfo>();

}
