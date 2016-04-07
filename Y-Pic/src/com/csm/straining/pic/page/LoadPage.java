package com.csm.straining.pic.page;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.ParamVaildException;
import com.csm.straining.common.http.page.JsonViewPage;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.common.model.info.common.ParamErrorStatus;
import com.csm.straining.common.model.info.common.ResponseStatus;
import com.csm.straining.pic.service.PicService;
import com.lamfire.utils.JSON;


/**
 * @author chensongming
 */
public class LoadPage extends JsonViewPage {
	
	private static final Logger logger = LoggerFactory.getLogger(LoadPage.class);

	@Override
	protected Object doService() {
				
		try {
			String fileName = getParamStringRequired("fileName");
			
			resp.setCharacterEncoding("UTF-8");  
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-disposition","attachment;filename=\"" + fileName + "\""); 
			
			PicService.load(fileName, resp.getOutputStream());
			
			return new ResponseStatus();
			
		} catch (ParamVaildException e) {
			logger.debug("[LoadPage Exception]", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (Exception e) {
			logger.debug("[LoadPage Exception]", e);
			return new ErrorStatus(100);
		}
		
	}
	
	

}
