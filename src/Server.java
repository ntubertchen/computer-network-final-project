import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.util.HashMap;
import java.util.ArrayList;
class Server extends Thread{
	HashMap<Integer,ArrayList<Header> > workQueue;
	HashMap<Integer,ArrayList<Header> >	dataQueue;

	public static void main(String[] args) {
		this.workQueue = new HashMap<Integer,ArrayList<Header> >();
		this.dataQueue = new HashMap<Integer,ArrayList<Header> >();
		ServerSocket socket;
		try{
			socket = new ServerSocket(8000);
			while(true){
				try{
					Socket temp_socket = socket.accept();
					try{
						System.out.println("accept success");
						ObjectInputStream inputstream = new ObjectInputStream(temp_socket.getInputStream());
						try{
							Header temp_h = (Header) inputstream.readObject();
							System.out.println(temp_h.type);
						}catch(ClassNotFoundException a){
							System.out.println(",....");
						}
					}catch(IOException ex){
						System.out.println("socket GG");
					}
				}catch(IOException e){
					System.out.println("shit happened");
				}
			}
		}catch(IOException eee){
			System.out.println("server failure");
		}
	}

	public ForwardThread implements Runnalbe{
		HashMap<Integer,ArrayList<Header> >	dataQueue;
		private Socket socket;
		ForwardThread(Socket socket,HashMap<Integer,ArrayList<Header> > dataQueue,HashMap<Integer,ArrayList<Header> > workQueue){
			this.socket = socket;
			this.dataQueue = dataQueue;
		}
		public void run(){
			while(true){
				
			}
		}
	}

	public RequestThread implements Runnalbe{
		private Socket socket;
		HashMap<Integer,ArrayList<Header> > workQueue;
		RequestThread(Socket socket,HashMap<Integer,ArrayList<Header> > workQueue){
			this.socket = socket;
			this.workQueue = workQueue;
		}
		public void run(){
			while(true){
				ObjectInputStream inputstream = new ObjectInputStream(temp_socket.getInputStream());
				try{
					Header temp_h = (Header) inputstream.readObject();
					ArrayList<Header> temp_list = workQueue.get(temp_h.room);
					temp_list.add(temp_h);
				}catch(ClassNotFoundException e){
					System.out.println("in thread,class not found");
				}
			}
		}
	}
}