public class Command{

	static final int REGISTER = -10,
					 LOGIN = -11;	

	static final int KNOCKING = 0,
					 KNOCKING_ACK = 1,
					 SEND_MSG = 2,
					 SEND_MSG_ACK = 3,
					 SEND_FILE = 4,
					 SEND_FILE_ACK = 5,
					 SEND_MSG_CHAT = 6,
					 SEND_MSG_CHAT_ACK = 7,
					 SEND_FILE_CHAT = 8,
					 SEND_FILE_CHAT_ACK = 9;
	static final int FAILURE_REG = 10,
					 SUCCESS_REG = 11,
					 SUCCESS_LOG = 12,
					 FAILURE_LOG = 13,
					 SUCCESS_MSG = 14,
					 FAILURE_MSG = 15,
					 NOSUCHUSER = 16,
					 INIT_CHAT = 17,


	//chat room 0 for conversion
	//other number for multiple serverice
}