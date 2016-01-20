import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
class Server extends Thread{
	public void run(){

	}
	public static void main(String[] args) {
		ServerSocket socket;
		try{
			socket = new ServerSocket(8000);
			while(true){
				System.out.println("try catch");
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
}