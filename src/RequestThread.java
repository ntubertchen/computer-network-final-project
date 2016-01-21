import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.util.HashMap;
import java.util.ArrayList;
public class RequestThread extends Thread{
		private Socket socket;
		HashMap<Integer,ArrayList<Header> > dataQueue;
		HashMap<String,Socket> socket_map;
		HashMap<String,User> userData;
		RequestThread(Socket socket,HashMap<Integer,ArrayList<Header> > dataQueue,HashMap<String,Socket> socket_map,HashMap<String,User> userData){
			this.socket = socket;
			this.dataQueue = dataQueue;
			this.socket_map = socket_map;
			this.userData = userData;
		}
		public void run(){
			ObjectInputStream inputstream;
			System.out.println("start");
			ArrayList<Header> temp_list;
			Socket temp_socket;
			int chatroom = 10;
			try{
				Header temp_h;
				inputstream = new ObjectInputStream(socket.getInputStream());
				while(true && socket.isClosed() == false){
					try{
							temp_h = (Header) inputstream.readObject();
							ObjectOutputStream objectOutput;
							if(temp_h.getType() == Command.REGISTER){
								if(userData.containsKey(temp_h.getOwner()) == true){//re no
									try{
										Header failure;
										failure.setType(Command.FAILURE_REG);
										objectOutput = new ObjectOutputStream(temp_socket.getOutputStream());
										objectOutput.writeObject(failure);
									}catch(IOException reg){}
								}else{
									userData.put(temp_h.getUsername(),temp_h.getUserpassword());
									try{
										Header success;
										success.setType(Command.SUCCESS_REG);
										objectOutput = new ObjectOutputStream(temp_socket.getOutputStream());
										objectOutput.writeObject(success);
									}catch(IOException regs){}
								}
							}else if(temp_h.getType() == Command.LOGIN){
								User u = temp_h.getUser();
								if(userData.containsKey(temp_h.getOwner()) == true && u.check(userData.get(temp_h.getOwner())) == true){
									try{
										Header success;
										success.setType(Command.SUCCESS_LOG);
										objectOutput = new ObjectOutputStream(temp_socket.getOutputStream());
										objectOutput.writeObject(success);
									}catch(IOException logs){}
								}else{
									try{
										Header failure;
										failure.setType(Command.FAILURE_LOG);
										objectOutput = new ObjectOutputStream(temp_socket.getOutputStream());
										objectOutput.writeObject(failure);
									}catch(IOException log){}
								}
							}else if(temp_h.getType() == Command.SEND_MSG){
								if(socket_map.containsKey(temp_h.getReceiver()) == true){
									temp_socket = socket_map.get(temp_h.getReceiver());
									if(temp_socket.isClosed() == false){
										try{
											objectOutput = new ObjectOutputStream(temp_socket.getOutputStream());
											objectOutput.writeObject(temp_h);
										}catch(IOException msg){}
										Header success;
										success.setType(Command.SUCCESS_MSG);
										try{
											objectOutput = new ObjectOutputStream(socket.getOutputStream());
											objectOutput.writeObject(success);
										}catch(IOException msgacks){}
									}else{
										Header failure;
										failure.setType(Command.FAILURE_MSG);
										try{
											objectOutput = new ObjectOutputStream(socket.getOutputStream());
											objectOutput.writeObject(success);
										}catch(IOException msgack){}
									}
									temp_list = dataQueue.get(0);
									temp_list.add(temp_h);
								}else{
									Header failure;
									failure.setType(Command.NOSUCHUSER);
									try{
										objectOutput = new ObjectOutputStream(socket.getOutputStream());
										objectOutput.writeObject(failure);
									}catch(IOException msgnouser){}
								}
							}else if(temp_h.getType() == Command.SEND_MSG_CHAT){
								ArrayList<User> temp_userlist;
								temp_userlist = temp_h.userlist;
								Iterator temp_it_user = temp_userlist.iterator();
								while(temp_it_user.hasNext()){
									User temp_user;
									temp_user = temp_it_user.next();
									if(socket_map.containsKey(temp_user.getUsername()) == true){
										temp_socket = socket_map.get(temp_user.getUsername());
										if(temp_socket.isClosed() == false){
											try{
												objectOutput = new ObjectOutputStream(temp_socket.getOutputStream());
												objectOutput.writeObject(temp_h);
											}catch(IOException chats){}
										}else{
											//user not online
										}
									}else{
										//user not exist(not happening)
									}
								}
								//save data
								temp_list = dataQueue.get(temp_h.getRoom());
								temp_list.add(temp_h);
							}else if(temp_h.getType() == Command.INIT_CHAT){
								temp_h.setRoom(chatroom);
								dataQueue.put(temp_h.getRoom(),new ArrayList<Header>());
								chatroom++;
								ArrayList<User> temp_userlist;
								temp_userlist = temp_h.userlist;
								Iterator temp_it_user = temp_userlist.iterator();
								while(temp_it_user.hasNext()){
									User temp_user;
									temp_user = temp_it_user.next();
									if(socket_map.containsKey(temp_user.getUsername()) == true){
										temp_socket = socket_map.get(temp_user.getUsername());
										if(temp_socket.isClosed() == false){
											try{
												objectOutput = new ObjectOutputStream(temp_socket.getOutputStream());
												objectOutput.writeObject(temp_h);
											}catch(IOException chatinit){}
										}else{
											//user not online
										}
									}
								}
								temp_list = dataQueue.get(temp_h.getRoom());
								temp_list.add(temp_h);
							}
					}catch(ClassNotFoundException e){
						System.out.println("in thread,class not found");
					}
				}
			}catch(IOException a){
				System.out.println("z");
			}
			System.out.println("connection terminate");
		}
	}