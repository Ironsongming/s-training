package com.csm.straining.repeater.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.server.action.ActionSupport;
import com.csm.straining.repeater.client.RepeaterCode;


/**
 * @author chensongming
 */
public class HeartbeatAction extends ActionSupport{

	private static final Logger logger = LoggerFactory.getLogger(HeartbeatAction.class);

	@Override
	protected void execute() throws ActionException {
		logger.info("HeartbeatAction success");
		sendMessage(RepeaterCode.HeartbeatPID.SUCCESS);
		logger.info("HeartbeatAction success send");
	}

}
