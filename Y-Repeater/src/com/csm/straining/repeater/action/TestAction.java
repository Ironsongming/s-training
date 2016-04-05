package com.csm.straining.repeater.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.netkit.exception.SessionException;
import com.csm.straining.common.socket.netkit.message.Message;
import com.csm.straining.common.socket.server.action.ActionSupport;
import com.csm.straining.repeater.client.RepeaterCode;
import com.lamfire.utils.JSON;


/**
 * @author chensongming
 */
public class TestAction extends ActionSupport{
	
	private static final Logger logger = LoggerFactory.getLogger(TestAction.class);

	@Override
	protected void execute() throws ActionException {
		String testStr = getParamsString("test");
		
		logger.info("[repater testAction] XXXXXXXXXXXXXXXXX" + testStr);
		System.out.println("[repater testAction] XXXXXXXXXXXXXXXXX" + testStr);
		
		Message message = new Message();
		message.setMessageID(RepeaterCode.TestPID.SUCCESS);
		message.setVersion(1);
		
		String text = "chensongming client repaeater success";
		message.setMessageContent(JSON.toJsonString(text).getBytes());
		
		try {
			getSession().sendMessage(message);
		} catch (SessionException e) {
			logger.debug(e.getMessage(), e);
		}
	}

}
