package com.csm.straining.common.socket.netkit;

import java.nio.ByteBuffer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.csm.straining.common.socket.netkit.message.Message;


/**
 * @author chensongming
 */
public class NetKitProtocolEncoder extends SimpleChannelHandler {

	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
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
		
		ChannelBuffer channelBuffer = ChannelBuffers.wrappedBuffer(buffer);
		ChannelFuture future = e.getFuture();
		
		Channels.write(ctx, future, channelBuffer);
 	}

}
