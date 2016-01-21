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
        h.type = 100;
        try{
        	socket = new Socket( host, port );
        	try{
                        try{
                                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                input = br.readLine();
                        }catch(IOException io){
                                io.printStackTrace();
                        }
                        h.setOwner(input);
                        try{
                                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                input = br.readLine();
                        }catch(IOException io){
                                io.printStackTrace();
                        }
                        h.setReceiver(input);
        		ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
        		objectOutput.writeObject(h);
                        try{
                             ObjectInputStream inputstream;
                             inputstream = new ObjectInputStream(socket.getInputStream());
                             try{
                                Header temp = (Header) inputstream.readObject();
                                System.out.println(temp.getOwner() + " " + temp.getReceiver());
                             }catch(IOException ee){}
                        }catch(ClassNotFoundException aaaa){

                        }
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