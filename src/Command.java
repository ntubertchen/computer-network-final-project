public class Command{

	static final int REGISTER = -10,
					 LOGIN = -11;	

	static final int KNOCKING = 0,
					 KNOCKING_ACK = 1,
					 SEND_MSG = 2,		//
					 SEND_MSG_ACK = 3,
					 SEND_FILE = 4,		//
					 SEND_FILE_ACK = 5,
					 SEND_MSG_CHAT = 6,
					 SEND_MSG_CHAT_ACK = 7,
					 SEND_FILE_CHAT = 8,
					 SEND_FILE_CHAT_ACK = 9;
	static final int FAILURE_REG = 10,	//ok
					 SUCCESS_REG = 11,	//ok
					 SUCCESS_LOG = 12,	//ok
					 FAILURE_LOG = 13,	//ok
					 SUCCESS_MSG = 14,
					 FAILURE_MSG = 15,
					 NOSUCHUSER = 16,
					 INIT_CHAT = 17,
					 CHAT_ADD = 18,
					 MSG_SYNC = 19,
					 CHAT_SYNC = 20;


	//chat room 0 for conversion
	//other number for multiple serverice
}