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

		logger.info("NetKitProtocolDecoder.decode");
		
		if (source.readableBytes() < 12) {
			logger.info("source.readableBytes < Message.headerSize");
			return null;
		}
		
		source.markReaderIndex();
		
		logger.info("source.readableBytes" +  source.readableBytes());
		
		try {
			int contentLen = source.readInt();
			int messageID = source.readInt();
			int version = source.readInt();
			
			logger.info("contentLen : " + contentLen);
			logger.info("messageID : " + messageID);
			logger.info("version : " + version);
			logger.info("source.readableBytes" +  source.readableBytes());
			
			if (source.readableBytes() < contentLen) {
				logger.info("source.readableBytes < contentLen");
				source.resetReaderIndex();
				return null;
			}
			
			byte[] data = new byte[contentLen];
			source.readBytes(data);
			
			return new Message(messageID, version, data);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		
		return null;
		
		
	}

}
