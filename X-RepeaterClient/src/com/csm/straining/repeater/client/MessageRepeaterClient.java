package com.csm.straining.repeater.client;


/**
 * @author chensongming
 */
public class MessageRepeaterClient extends RepeaterClient{
	
	private MessageRepeaterClient() {
		
	}
	
	private static MessageRepeaterClient instance = new MessageRepeaterClient();
	
	public static MessageRepeaterClient ins() {
		return instance;
	}
	
	@Override
	protected String getRepeaterHost() {
		return Config.getRepeaterHost();
	}
	
	@Override
	protected int getRepeaterPort() {
		return Config.getRepeaterPort();
	}

	
}
