package com.csm.straining.common.socket.netkit;

import java.nio.ByteBuffer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.message.Message;


/**
 * @author chensongming
 */
public class NetKitProtocolEncoder extends SimpleChannelHandler {

	private static final Logger logger = LoggerFactory.getLogger(NetKitProtocolEncoder.class);
	
	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		
		System.out.println("NetKitProtocolEncoder.writeRequested");
		logger.info("NetKitProtocolEncoder.writeRequested");
		System.out.println(e.getChannel().getId());
		
		Object obj = e.getMessage();
		if (!(obj instanceof Message)) {
			return;
		}
		
		Message message = (Message) obj;
		
		ByteBuffer buffer = ByteBuffer.allocate(message.getPacketSize());
		buffer.putInt(message.getContentLength());
		buffer.putInt(message.getMessageID());
		buffer.putInt(message.getVersion());
		
		if (message.getMessageContent() != null) {
			buffer.put(message.getMessageContent());
		}
		
		ChannelBuffer channelBuffer = ChannelBuffers.wrappedBuffer(buffer.array());
		ChannelFuture future = e.getFuture();
		
		Channels.write(ctx, future, channelBuffer);
 	}

}
