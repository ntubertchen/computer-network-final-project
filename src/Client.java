import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.io.InputStreamReader;
class Client{
	public static void main(String[] args) {
        ConcurrentLinkedQueue<Header> queue = new ConcurrentLinkedQueue<Header>();
        Header h = new Header();
        LoginFrame l = new LoginFrame();
        Socket socket = new Socket();
        String host = "127.0.0.1";
        int port = 8000;
        try{
            socket = new Socket(host,port);
        }catch(IOException e){}
        l.setSocket(socket);
        l.setqueue(queue);
        l.run();
        while(true){
            ObjectInputStream inputstream;
            Header temp_h = new Header();
            try{
                inputstream = new ObjectInputStream(socket.getInputStream());
                try{
                    temp_h = (Header) inputstream.readObject();
                }catch(ClassNotFoundException a){System.out.println("e");}
            }catch(IOException e){System.out.println("r");}
            queue.add(temp_h);
            l.reaction();
        }
	}
}