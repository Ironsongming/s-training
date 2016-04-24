package com.csm.straining.user.page.training;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.ParamVaildException;
import com.csm.straining.common.i.training.params.TrainingItemParams;
import com.csm.straining.common.i.training.params.TrainingParams;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.common.model.info.common.ParamErrorStatus;
import com.csm.straining.framework.http.page.AuthJsonViewPage;
import com.csm.straining.user.service.TrainingService;
import com.csm.straining.user.service.UserService;
import com.lamfire.utils.NumberUtils;
import com.lamfire.utils.StringUtils;


/**
 * @author chensongming
 */
public class TrainingFinishPage extends AuthJsonViewPage{
	
	private static final Logger logger = LoggerFactory.getLogger(TrainingFinishPage.class);
	
	@Override
	protected Object doService() {
		try {
			int consumTime = NumberUtils.toInt(getParamStringRequired("consum_time"));
			long trainingID = NumberUtils.toLong(getParamStringRequired("training_id"));
			
			List<TrainingItemParams> trainingItems = new ArrayList<TrainingItemParams>();
			String trainingItemsStr = getParamString("training_items");
			if (!StringUtils.isBlank(trainingItemsStr)) {
				String[] CommaSplit = trainingItemsStr.split(",");
				for (String s : CommaSplit) {
					String[] barSplit = s.split("-");
					if (barSplit.length != 2) {
						throw new ParamVaildException(102, "training_items");
					}
					TrainingItemParams params = new TrainingItemParams();
					params.setTrainingID(trainingID);
					params.setCount(NumberUtils.toInt(barSplit[0]));
					params.setBreakTime(NumberUtils.toInt(barSplit[1]));
					trainingItems.add(params);
				}
			}
			
			
			return TrainingService.trainingFinishResp(currentUserID, trainingID, consumTime, trainingItems);
		} catch (ParamVaildException e) {
			logger.debug("[TrainingFinishPage] ParamVaildException :", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[TrainingFinishPage] AppException :", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[TrainingFinishPage] Exception :", e);
			return new ErrorStatus(100);
		}
		
		
	
	}

}
