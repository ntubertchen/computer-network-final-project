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
		RequestThread(Socket socket,HashMap<Integer,ArrayList<Header> > dataQueue,HashMap<String,Socket> socket_map){
			this.socket = socket;
			this.dataQueue = dataQueue;
			this.socket_map = socket_map;
		}
		public void run(){
			ObjectInputStream inputstream;
			System.out.println("start");
			try{
				Header temp_h;
				inputstream = new ObjectInputStream(socket.getInputStream());
				while(true && socket.isClosed() == false){
					try{
							temp_h = (Header) inputstream.readObject();	
							if(socket_map.containsKey(temp_h.getOwner()) == false){
								this.socket_map.put(temp_h.getOwner(),this.socket);
							}
							if(socket_map.containsKey(temp_h.getReceiver()) == true){
								Socket temp_socket = socket_map.get(temp_h.getReceiver());
								if(temp_socket.isClosed() == false){
									try{
										ObjectOutputStream objectOutput = new ObjectOutputStream(temp_socket.getOutputStream());
										objectOutput.writeObject(temp_h);
									}catch(IOException ex){
										System.out.println("write to " + temp_h.getReceiver() + "fail");
									}
								}
								ArrayList<Header> temp_list = dataQueue.get(temp_h.getRoom());
								temp_list.add(temp_h);
							}else{
								System.out.println("player" + temp_h.getReceiver() + "not init");
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