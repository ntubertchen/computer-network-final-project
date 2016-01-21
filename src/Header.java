import java.io.Serializable;
import java.util.ArrayList;
class Header implements Serializable{
	int type;
	private String owner,receiver;
	private String msg;
	private int room;
	public void setRoom(int i){
		this.room = i;
	}
	public int getRoom(){
		return this.room;
	}
	public void setOwner(String s){
		this.owner = s;
	}
	public void setReceiver(String s){
		this.receiver = s;
	}
	public String getOwner(){
		return this.owner;
	}
	public String getReceiver(){
		return this.receiver;
	}
	public void setMsg(String s){
		this.msg = s;
	}
	public String getMsg(){
		return this.msg;
	}
	Header(){}
}