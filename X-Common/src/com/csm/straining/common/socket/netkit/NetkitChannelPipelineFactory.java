package com.csm.straining.common.socket.netkit;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;


/**
 * @author chensongming
 */
public class NetkitChannelPipelineFactory implements ChannelPipelineFactory {

	public static final String ENCODER_HANDLER_NAME = "encoder";
	public static final String DECODER_HANDLER_NAME = "decoder";
	public static final String MESSAGE_HANDLER_NAME = "handler";
	private NetkitContext context = null;
	
	public NetkitChannelPipelineFactory(NetkitContext context) {
		this.context = context;
	}
	
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast(DECODER_HANDLER_NAME, new NetKitProtocolDecoder());
		pipeline.addLast(ENCODER_HANDLER_NAME, new NetKitProtocolEncoder());
		pipeline.addLast(MESSAGE_HANDLER_NAME, new NetkitChannelHandler(this.context));
		
		return pipeline;
	}

}
