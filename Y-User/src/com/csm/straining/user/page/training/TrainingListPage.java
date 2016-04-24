package com.csm.straining.user.page.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.ParamVaildException;
import com.csm.straining.common.i.training.params.TrainingParams;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.common.model.info.common.ParamErrorStatus;
import com.csm.straining.framework.http.page.AuthJsonViewPage;
import com.csm.straining.user.service.TrainingService;
import com.csm.straining.user.service.UserService;
import com.lamfire.utils.NumberUtils;


/**
 * @author chensongming
 */
public class TrainingListPage extends AuthJsonViewPage{
	
	private static final Logger logger = LoggerFactory.getLogger(TrainingListPage.class);
	
	@Override
	protected Object doService() {
		try {
			
			long start = NumberUtils.toLong(getParamString("start"), 0);
			int count = NumberUtils.toInt(getParamString("count"), 20);
			long targetUserID = NumberUtils.toLong(getParamStringRequired("target_user_id"));
			return TrainingService.trainingListResp(targetUserID, start, count);
		} catch (AppException e) {
			logger.debug("[TrainingListPage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[TrainingListPage] Exception :", e);
			return new ErrorStatus(100);
		}
		
		
	
	}
	

}
