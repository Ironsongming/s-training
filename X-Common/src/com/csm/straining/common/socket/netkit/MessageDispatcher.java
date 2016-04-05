package com.csm.straining.common.socket.netkit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.exception.ActionException;
import com.csm.straining.common.socket.netkit.message.Message;
import com.lamfire.utils.ObjectFactory;


/**
 * @author chensongming
 */
public class MessageDispatcher {

	private static final Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);
	private final Map<Integer, Action> actionCache = new HashMap<Integer, Action>();
	private ActionChain chain = new ActionChain();
	
	public void dispatch(Session session, Message message) {
		dispatch(session.getContext(), session, message);
	}
	
	public void dispatch(NetkitContext context, Session session, Message message) {
		int messageID = message.getMessageID();
		try {
			Action action = getAction(context, messageID);
			
			if (action == null) {
				return;
			}
			
			action.setSession(session);
			Iterator<ActionFilter> it = null;
			List<ActionFilter> afs = context.getActionFilterList();
			
			if (afs != null) {
				it = afs.iterator();
			}
			
			executeAction(it, action, message);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	private Action getAction(NetkitContext context, int messageID) throws InstantiationException, IllegalAccessException {
		Action action = getCachedAction(messageID);
		if (action != null) {
			return action;
		}
		Class<Action> clazz = context.getRegisterAction(messageID);
		
		if (null == clazz) {
			return null;
		}
		
		ObjectFactory of = new ObjectFactory(clazz);
		action = of.newInstance();
	
		if (action != null) {
			setCachedAction(messageID, action);
		}
		
		return action;
	}
	
	private Action getCachedAction(int messageID) {
		return this.actionCache.get(messageID);
	}
	
	private synchronized void setCachedAction(int messageID, Action action) {
		this.actionCache.put(messageID, action);
	}
	
	private synchronized void executeAction(Iterator<ActionFilter> it, Action action, Message message) throws ActionException {
		this.chain.setIterator(it);
		this.chain.doChain(action, message);
	}
}
