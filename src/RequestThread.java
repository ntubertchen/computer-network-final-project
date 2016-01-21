import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
public class RequestThread extends Thread{
		private Socket socket;
		HashMap<Integer,ArrayList<Header> > dataQueue;
		HashMap<String,Socket> socket_map;
		HashMap<String,User> userData;
		HashMap<Integer,ArrayList<String>> chatroom_list;
		RequestThread(Socket socket,HashMap<Integer,ArrayList<Header> > dataQueue,HashMap<String,Socket> socket_map,HashMap<String,User> userData,HashMap<Integer,ArrayList<String>> chatroom_list){
			this.socket = socket;
			this.dataQueue = dataQueue;
			this.socket_map = socket_map;
			this.userData = userData;
			this.chatroom_list = chatroom_list;
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
										objectOutput = new ObjectOutputStream(socket.getOutputStream());
										objectOutput.writeObject(failure);
									}catch(IOException reg){}
								}else{
									User temp_user = temp_h.getUser();
									userData.put(temp_user.getUsername(),temp_user);
									try{
										Header success;
										success.setType(Command.SUCCESS_REG);
										objectOutput = new ObjectOutputStream(socket.getOutputStream());
										objectOutput.writeObject(success);
									}catch(IOException regs){}
								}
							}else if(temp_h.getType() == Command.LOGIN){
								User u = temp_h.getUser();
								if(userData.containsKey(temp_h.getOwner()) == true && u.check(userData.get(temp_h.getOwner())) == true){
									try{
										//return user data
										Header success;
										u = userData.get(temp_h.getOwner());
										success.setUser(u);
										success.setType(Command.SUCCESS_LOG);
										Curinfo temp_info = new Curinfo();
										Iterator temp_it_hashmap = socket_map.entrySet().iterator();
										while(temp_it_hashmap.hasNext()){
											HashMap.Entry temp_pair = (HashMap.Entry)temp_it_hashmap.next();
											temp_socket = temp_pair.getValue();
											if(temp_socket.isClosed() == true){
												temp_info.curoffline.add(temp_pair.getKey());
											}else{
												temp_info.curonline.add(temp_pair.getKey());
											}
										}
										success.setCurinfo(temp_info);
										objectOutput = new ObjectOutputStream(socket.getOutputStream());
										objectOutput.writeObject(success);
									}catch(IOException logs){}
								}else{
									try{
										Header failure;
										failure.setType(Command.FAILURE_LOG);
										objectOutput = new ObjectOutputStream(socket.getOutputStream());
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
											objectOutput.writeObject(failure);
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
								ArrayList<String> chat_user;
								chat_user = chatroom_list.get(temp_h.getRoom());
								Iterator<String> temp_it_user_string = chat_user.iterator();
								while(temp_it_user_string.hasNext()){
									String temp_user_string;
									temp_user_string = temp_it_user_string.next();
									if(socket_map.containsKey(temp_user_string) == true){
										temp_socket = socket_map.get(temp_user_string);
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
								chatroom_list.put(chatroom,new ArrayList<String>());
								ArrayList<String> chat_user = chatroom_list.get(chatroom);
								dataQueue.put(temp_h.getRoom(),new ArrayList<Header>());
								chatroom++;
								ArrayList<User> temp_userlist;
								temp_userlist = temp_h.userlist;
								Iterator<User> temp_it_user = temp_userlist.iterator();
								while(temp_it_user.hasNext()){
									User temp_user;
									temp_user = temp_it_user.next();
									chat_user.add(temp_user.getUsername());
							/*		if(socket_map.containsKey(temp_user.getUsername()) == true){
										temp_socket = socket_map.get(temp_user.getUsername());
										if(temp_socket.isClosed() == false){
											try{
												objectOutput = new ObjectOutputStream(temp_socket.getOutputStream());
												objectOutput.writeObject(temp_h);
											}catch(IOException chatinit){}
										}else{
											//user not online
										}
									}*/ // init chat and do what?
								}
					/*			temp_list = dataQueue.get(temp_h.getRoom());
								temp_list.add(temp_h);*/
							}else if(temp_h.getType() == Command.CHAT_ADD){
								ArrayList<String> chat_user = chatroom_list.get(temp_h.getRoom());
								ArrayList<User> temp_userlist;
								temp_userlist = temp_h.userlist;
								Iterator<User> temp_it_user = temp_userlist.iterator();
								while(temp_it_user.hasNext()){
									User temp_user;
									temp_user = temp_it_user.next();
									chat_user.add(temp_user.getUsername());
								}
							}else if(temp_h.getType() == Command.MSG_SYNC){
								ArrayList<Header> temp_msg;
								if(dataQueue.containsKey(0) == true){
									temp_msg = dataQueue.get(0);
									Iterator<Header> temp_it_header = temp_msg.iterator();
									while(temp_it_header.hasNext()){
										Header temp_header = temp_it_header.next();
										String temp_string = temp_h.getOwner();
										if(temp_string.equals(temp_header.getOwner()) == true || temp_string.equals(temp_header.getReceiver()) == true){
											try{
												objectOutput = new ObjectOutputStream(socket.getOutputStream());
												objectOutput.writeObject(temp_header);
											}catch(IOException syncs){}
										}
									}
								}
							}else if(temp_h.getType() == Command.CHAT_SYNC){
								ArrayList<Header> temp_chat;
								if(dataQueue.containsKey(temp_h.getRoom()) == true){
									temp_chat = dataQueue.get(temp_h.getRoom());
									Iterator<Header> temp_it_header = temp_chat.iterator();
									while(temp_it_header.hasNext()){
										Header temp_header = temp_it_header.next();
										try{
											objectOutput = new ObjectOutputStream(socket.getOutputStream());
											objectOutput.writeObject(temp_header);
										}catch(IOException chatsyncs){}
									}
								}
							}else if(temp_h.getType() == Command.SEND_FILE){

							}else if(temp_h.getType() == Command.KNOCKING){
								try{
									Header success;
									success.setType(Command.KNOCKING_ACK);
									Curinfo temp_info = new Curinfo();
									Iterator temp_it_hashmap = socket_map.entrySet().iterator();
									while(temp_it_hashmap.hasNext()){
										HashMap.Entry temp_pair = (HashMap.Entry)temp_it_hashmap.next();
										temp_socket = temp_pair.getValue();
										if(temp_socket.isClosed() == true){
											temp_info.curoffline.add(temp_pair.getKey());
										}else{
											temp_info.curonline.add(temp_pair.getKey());
										}
									}
									success.setCurinfo(temp_info);
									objectOutput = new ObjectOutputStream(socket.getOutputStream());
									objectOutput.writeObject(success);
								}catch(IOException logs){}
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