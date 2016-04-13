package com.csm.straining.repeater.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.Session;
import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.server.action.ActionSupport;
import com.csm.straining.repeater.RepeaterNetServer;
import com.csm.straining.repeater.client.RepeaterClientType;
import com.csm.straining.repeater.client.RepeaterCode;
import com.csm.straining.repeater.cons.Names;


/**
 * @author chensongming
 */
public class LoginAction extends ActionSupport {

	private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);
	
	@Override
	protected void execute() throws ActionException {
		int clientID = getParamsInt("repeaterClientID");
		String clientType = getParamsString("repeaterClientType");
		
		logger.debug("clientID is " + clientID);
		logger.debug("clientType is " + clientType);
		
		RepeaterClientType repeaterClientType = RepeaterClientType.valueOf(clientType);
		if (repeaterClientType == null) {
			logger.debug("LoginAction unknown client type");
		}
		
		Session session = getSession();
		session.setAttribute(Names.KEY_CLIENT_ID, clientID);
		
		if (repeaterClientType == RepeaterClientType.MESSAGE) {
			RepeaterNetServer.ins().addMessageSession(clientID, session);			
		}
		
		logger.info("login success clientID : " + clientID);
		
		sendMessage(RepeaterCode.LoginPID.SUCCESS);
	}

}
