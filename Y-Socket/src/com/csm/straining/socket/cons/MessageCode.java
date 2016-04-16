package com.csm.straining.socket.cons;


/**
 * @author chensongming
 */
public class MessageCode {
	
	public interface TestPID {
		public static final int REQUEST = 21001;
		public static final int SUCCESS = 22001;
		public static final int FAIL = 23001;
	}
	
	public interface LoginPID {
		public static final int REQUEST = 21002;
		public static final int SUCCESS = 22002;
		public static final int FAIL = 23002;	
	}
	
	public interface HeartbeatPID {
		public static final int REQUEST = 21003;
		public static final int SUCCESS = 22003;
		public static final int FAIL = 23003;	
	}

	public interface ForceOfflinePushPID {
		public static final int REQUEST = 21005;
	}
	
	public interface LogoutPID {
		public static final int REQUEST = 21006;
		public static final int SUCCESS = 22006;
		public static final int FAIL = 23006;	
	}
	
	

}
