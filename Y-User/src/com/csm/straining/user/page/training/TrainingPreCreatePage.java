package com.csm.straining.user.page.training;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.ParamVaildException;
import com.csm.straining.common.i.training.cons.TrainingDrinkingType;
import com.csm.straining.common.i.training.cons.TrainingGearType;
import com.csm.straining.common.i.training.cons.TrainingLocationType;
import com.csm.straining.common.i.training.cons.TrainingType;
import com.csm.straining.common.i.training.params.TrainingParams;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.common.model.info.common.ParamErrorStatus;
import com.csm.straining.common.model.info.common.ResponseStatus;
import com.csm.straining.framework.http.page.AuthJsonViewPage;
import com.csm.straining.user.service.TrainingService;
import com.csm.straining.user.service.UserService;
import com.lamfire.utils.NumberUtils;


/**
 * @author chensongming
 */
public class TrainingPreCreatePage extends AuthJsonViewPage{
	
	private static final Logger logger = LoggerFactory.getLogger(TrainingPreCreatePage.class);
	
	@Override
	protected Object doService() {
		try {
			TrainingPreCreateResp resp = new TrainingPreCreateResp();
			
			
			
			for (TrainingType type : TrainingType.values()) {
				resp.types.put(type.getKey(), type.getVal());
			}
			
			for (TrainingLocationType type : TrainingLocationType.values()) {
				resp.locations.put(type.getKey(), type.getVal());
			}
			
			for (TrainingDrinkingType type : TrainingDrinkingType.values()) {
				resp.drinkings.put(type.getKey(), type.getVal());
			}
			
			for (TrainingGearType type : TrainingGearType.values()) {
				resp.gears.put(type.getKey(), type.getVal());
			}
			
			return resp;
		} catch (Exception e) {
			logger.debug("[TrainingCreatePage] Exception :", e);
			return new ErrorStatus(100);
		}
		
		
	
	}
	
	private class TrainingPreCreateResp extends ResponseStatus {
		
		public Map<Integer, String> types = new HashMap<Integer, String>();
		public Map<Integer, String> locations = new HashMap<Integer, String>() ;
		public Map<Integer, String> drinkings = new HashMap<Integer, String>();
		public Map<Integer, String> gears = new HashMap<Integer, String>();
 		
	}


}
