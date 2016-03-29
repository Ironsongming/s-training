package com.csm.straining.common.socket.netkit.event;

import java.util.EventListener;

import com.csm.straining.common.socket.netkit.Session;


/**
 * @author chensongming
 */
public interface NetkitSessionListener extends EventListener {
	
	void onEvent(Session session);

}
