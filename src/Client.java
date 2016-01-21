import java.net.Socket;
import java.net.ServerSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ClassNotFoundException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.io.InputStreamReader;
class Client{
	public static void main(String[] args) {
        ConcurrentLinkedQueue<Header> queue = new ConcurrentLinkedQueue<Header>();
        ConcurrentLinkedQueue<byte[]> filequeue = new ConcurrentLinkedQueue<byte[]>();
        Header h = new Header();
        LoginFrame l = new LoginFrame();
        Socket socket = new Socket();
        String host = "127.0.0.1";
        int port = 8000;
        try{
            socket = new Socket(host,port);
        }catch(IOException e){}
        l.setfile(filequeue);
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
            if(temp_h.getType() == Command.SEND_FILE_CHAT || temp_h.getType() == Command.SEND_FILE){
                byte [] bytearray  = new byte [temp_h.filesize];
                try{    
                    InputStream is = socket.getInputStream();
                    int current,bytesRead;
                    try{
                        bytesRead = is.read(bytearray,0,bytearray.length);
                        current = bytesRead;
                        do{
                            try{
                                bytesRead = is.read(bytearray, current, (bytearray.length-current));
                            }catch(IOException aer){}
                        if(bytesRead >= 0) current += bytesRead;
                        }while(bytesRead > -1);
                    }catch(IOException ae){}
                }catch(IOException a){}
                filequeue.add(bytearray);
            }
            l.reaction();
        }
	}
}