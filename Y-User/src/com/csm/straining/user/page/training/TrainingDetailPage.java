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
public class TrainingDetailPage extends AuthJsonViewPage{
	
	private static final Logger logger = LoggerFactory.getLogger(TrainingDetailPage.class);
	
	@Override
	protected Object doService() {
		try {
			long trainingID = NumberUtils.toLong(getParamStringRequired("training_id"));
			return TrainingService.trainingDetailResp(trainingID);
		} catch (ParamVaildException e) {
			logger.debug("[TrainingDetailPage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[TrainingDetailPage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[TrainingDetailPage] Exception :", e);
			return new ErrorStatus(100);
		}
		
		
	
	}
	

}
