package com.csm.straining.socket.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.Session;
import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.netkit.message.Message;
import com.csm.straining.common.socket.server.action.ActionSupport;
import com.csm.straining.common.socket.server.util.MessageUtil;
import com.csm.straining.socket.cons.MessageCode;


/**
 * @author chensongming
 */
public class LogoutAction extends ActionSupport{

	private static final Logger logger = LoggerFactory.getLogger(LogoutAction.class);
	
	@Override
	protected void execute() throws ActionException {
		
		logger.info("enter LogoutAction");
		
		Session session = getSession();
		if (session.isClosed()) {
			logger.debug("session has been closed");
			return;
		}
		
		Message message = MessageUtil.getMessage(MessageCode.LogoutPID.SUCCESS);
		session.sendMessageAndClose(message);
		logger.info("complete LogoutAction");
	}
	

}
