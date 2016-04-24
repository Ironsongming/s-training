package com.csm.straining.repeater.client;


/**
 * @author chensongming
 */
public class RepeaterCode {
	
	public interface TestPID {
		public static final int REQUEST = 11001;
		public static final int SUCCESS = 12001;
	}
	
	public interface LoginPID {
		public static final int REQUEST = 11002;
		public static final int SUCCESS = 12002;
	}
	
	public interface HeartbeatPID {
		public static final int REQUEST = 11003;
		public static final int SUCCESS = 12003;
	}
	
	public interface ForceOfflineSendPID {
		public static final int REQUEST = 11004;
	}
	
	public interface ForceOfflinePushPID {
		public static final int REQUEST = 11005;
	}
	
	public interface UserChatSendPID {
		public static final int REQUEST = 11006;
	}
	
	public interface UserChatPushID {
		public static final int REQUEST = 11007;
	}

}
