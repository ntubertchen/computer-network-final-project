import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.util.HashMap;
import java.util.ArrayList;
import java.lang.Thread;
import java.util.concurrent.ConcurrentHashMap;
class Server extends Thread{
	
	public static void main(String[] args) {
		ConcurrentHashMap<String,User> userData;
		ConcurrentHashMap<Integer,ArrayList<Header> >	dataQueue;
		ConcurrentHashMap<String,Socket> socket_map;
		ConcurrentHashMap<Integer,ArrayList<String>> chatroom_list;
		userData = new ConcurrentHashMap<String,User>();
		dataQueue = new ConcurrentHashMap<Integer,ArrayList<Header> >();
		socket_map = new ConcurrentHashMap<String,Socket>();
		dataQueue.put(0,new ArrayList<Header>());
		chatroom_list = new ConcurrentHashMap<Integer,ArrayList<String>>();
		ServerSocket socket;
		try{
			socket = new ServerSocket(8000);
			while(true){
				System.out.println("try");
				try{
					Socket temp_socket = socket.accept();
					RequestThread r = new RequestThread(temp_socket,dataQueue,socket_map,userData,chatroom_list);
					r.start();
				}catch(IOException e){
					System.out.println("shit happened");
				}
			}
		}catch(IOException eee){
			System.out.println("server failure");
		}
	}
}