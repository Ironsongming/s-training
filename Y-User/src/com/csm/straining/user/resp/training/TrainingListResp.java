package com.csm.straining.user.resp.training;

import java.util.ArrayList;
import java.util.List;

import com.csm.straining.common.i.training.info.TrainingInfo;
import com.csm.straining.common.model.info.common.PagedResponseStatus;
import com.csm.straining.common.model.info.common.ResponseStatus;


/**
 * @author chensongming
 */
public class TrainingListResp extends PagedResponseStatus {
	
	public List<TrainingInfo> trainings = new ArrayList<TrainingInfo>();

}
