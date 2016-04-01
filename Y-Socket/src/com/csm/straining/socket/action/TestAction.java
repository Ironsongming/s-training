package com.csm.straining.socket.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.netkit.exception.SessionException;
import com.csm.straining.common.socket.netkit.message.Message;
import com.csm.straining.common.socket.server.action.ActionSupport;
import com.csm.straining.socket.cons.MessageCode;
import com.lamfire.utils.JSON;


/**
 * @author chensongming
 */
public class TestAction extends ActionSupport {
	
	private static final Logger logger = LoggerFactory.getLogger(TestAction.class);

	@Override
	protected void execute() throws ActionException {
		String testStr = getParamsString("test");
		
		logger.info("[testAction] XXXXXXXXXXXXXXXXX" + testStr);
		System.out.println("[testAction] XXXXXXXXXXXXXXXXX" + testStr);
		
		Message message = new Message();
		message.setMessageID(MessageCode.TestPID.SUCCESS);
		message.setVersion(1);
		
		String text = "chensongming";
		message.setMessageContent(JSON.toJsonString(text).getBytes());
		
		try {
			getSession().sendMessage(message);
		} catch (SessionException e) {
			logger.debug(e.getMessage(), e);
		}
	}

}
