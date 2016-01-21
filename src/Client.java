import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
class Client{
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 8000;
        Socket socket;
        Header h = new Header();
        String input = "";
        try{
        	socket = new Socket( host, port );
        	try{
                       /* try{
                                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                input = br.readLine();
                        }catch(IOException io){
                                io.printStackTrace();
                        }
                        System.out.println("owner");
                        h.setOwner(input);
                        try{
                                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                input = br.readLine();
                                System.out.println("receiver");
                        }catch(IOException io){
                                io.printStackTrace();
                        }*/
                        User u = new User("name","password");
                        h.setUser(u);
                        h.setType(Command.LOGIN);
                        ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
                        objectOutput.writeObject(h);
                        System.out.println("after send");
                        while(true){
                        }
        	}catch(IOException yee){
        		System.out.println("write fail");
        	}
        }catch(IOException e){
        	System.out.println("socket GG");
        }
	}
}