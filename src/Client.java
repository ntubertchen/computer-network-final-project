import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.util.ArrayList;
class Client{
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 8000;
        Socket socket;
        Header h = new Header();
        h.type = 100;
        h.i = new ArrayList<Integer>();
        h.i.add(0);
        h.i.add(40);
        h.i.add(30);
        h.i.add(20);
        h.i.add(10);
        try{
        	socket = new Socket( host, port );
        	try{
        		ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
        		objectOutput.writeObject(h);
        	}catch(IOException yee){
        		System.out.println("write fail");
        	}
        }catch(IOException e){
        	System.out.println("socket GG");
        }
	}
}