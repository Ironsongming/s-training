package com.csm.straining.common.socket.netkit;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csm.straining.common.socket.netkit.message.Message;


/**
 * @author chensongming
 */
public class NetKitProtocolDecoder extends FrameDecoder {
	
	private static final Logger logger = LoggerFactory.getLogger(NetKitProtocolDecoder.class);

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer source) throws Exception {

		if (source.readableBytes() < 12) {
			logger.debug("source.readableBytes < Message.headerSize");
			return null;
		}
		
		source.markReaderIndex();
		
		try {
			int contentLen = source.readInt();
			int messageID = source.readInt();
			int version = source.readInt();
			
			if (source.readableBytes() < contentLen) {
				logger.debug("source.readableBytes < contentLen");
				source.resetReaderIndex();
				return null;
			}
			
			byte[] data = new byte[contentLen];
			source.readBytes(data);
			
			return new Message(messageID, version, data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
		
		
	}

}
