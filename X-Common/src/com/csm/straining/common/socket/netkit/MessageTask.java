package com.csm.straining.common.socket.netkit;

import javax.xml.ws.Dispatch;

import com.csm.straining.common.socket.netkit.message.Message;


/**
 * @author chensongming
 */
public class MessageTask implements Runnable {

	private MessageDispatcher dispatcher;
	private Session session;
	private Message message;
	
	public MessageTask(MessageDispatcher dispatcher, Session session,
			Message message) {
		this.dispatcher = dispatcher;
		this.session = session;
		this.message = message;
	}
	
	@Override
	public void run() {
		execute();
	}
	
	public void execute() {
		synchronized (this.dispatcher) {
			this.dispatcher.dispatch(session, message);
		}
	}

}
