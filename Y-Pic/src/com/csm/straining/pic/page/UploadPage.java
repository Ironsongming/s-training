package com.csm.straining.pic.page;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.exception.AppException;
import com.csm.straining.common.exception.CoreException;
import com.csm.straining.common.exception.ParamVaildException;
import com.csm.straining.common.http.page.JsonViewPage;
import com.csm.straining.common.model.info.common.ErrorStatus;
import com.csm.straining.common.model.info.common.ParamErrorStatus;
import com.csm.straining.pic.service.PicService;


/**
 * @author chensongming
 */
public class UploadPage extends JsonViewPage {

	private static final Logger logger = LoggerFactory.getLogger(UploadPage.class);
	
	@Override
	protected Object doService() {
		
		try {
			String suffix = getParamStringRequired("suffix");
			
			InputStream is = null;
			try {
				is = req.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (is == null) {
				throw new ParamVaildException(101, "file");
			}
			
			return PicService.save(is, suffix);
			
		} catch (ParamVaildException e) {
			logger.debug("[UploadPage ParamVaildException]", e);
			return new ParamErrorStatus(e.getCode(), e.getMsg());
		} catch (AppException e) {
			logger.debug("[UploadPage AppException]", e);
			return new ErrorStatus(e);
		} catch (Exception e) {
			logger.debug("[UploadPage AppException]", e);
			return new ErrorStatus(100);
		} 
		
	}

}
