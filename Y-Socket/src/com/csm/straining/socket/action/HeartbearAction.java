package com.csm.straining.socket.action;

import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.server.action.ActionSupport;
import com.csm.straining.socket.cons.MessageCode;


/**
 * @author chensongming
 */
public class HeartbearAction extends ActionSupport{
	
	@Override
	protected void execute() throws ActionException {
		sendMessage(MessageCode.HeartbeatPID.SUCCESS);
	}

}
