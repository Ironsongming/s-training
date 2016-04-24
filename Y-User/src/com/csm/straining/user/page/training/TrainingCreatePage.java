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
public class TrainingCreatePage extends AuthJsonViewPage{
	
	private static final Logger logger = LoggerFactory.getLogger(TrainingCreatePage.class);
	
	@Override
	protected Object doService() {
		try {
			TrainingParams params = parseTrainingParams();
			return TrainingService.trainingCreateResp(params);
		} catch (ParamVaildException e) {
			logger.debug("[TrainingCreatePage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[TrainingCreatePage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[TrainingCreatePage] Exception :", e);
			return new ErrorStatus(100);
		}
		
		
	
	}
	
	private TrainingParams parseTrainingParams() throws ParamVaildException {
		TrainingParams params = new TrainingParams();
		params.setUserID(currentUserID);
		params.setType(NumberUtils.toInt(getParamStringRequired("type")));
		params.setLocation(NumberUtils.toInt(getParamStringRequired("location")));
		params.setDrinking(NumberUtils.toInt(getParamStringRequired("drinking")));
		params.setGear(NumberUtils.toInt(getParamStringRequired("gear")));
		params.setBeginAt(NumberUtils.toLong(getParamStringRequired("begin_at")));
		params.setPresetCount(NumberUtils.toInt(getParamStringRequired("preset_count")));
		params.setPresetGroup(NumberUtils.toInt(getParamStringRequired("preset_group")));
		params.setPerBreakTime(NumberUtils.toInt(getParamStringRequired("per_breaktime")));
		
		return params;
	}

}
