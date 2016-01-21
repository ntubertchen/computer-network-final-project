import java.io.Serializable;
import java.util.ArrayList;
class Header implements Serializable{
	int type;
	private String owner,receiver;
	private String msg;
	private int room;
	private User u;
	ArrayList<User> userlist;
	Curinfo c;
	int filesize;
	public void setCurinfo(Curinfo c){
		this.c = c;
	}
	public Curinfo getCurinfo(){
		return this.c;
	}
	public void add(User u){
		this.userlist.add(u);
	}
	public int getType(){
		return this.type;
	}
	public void setUser(User u){
		this.u = u;
	}
	public User getUser(){
		return this.u;
	}
	public void setType(int i){
		this.type = i;
	}
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
	Header(){
		this.userlist = new ArrayList<User>();
	}
}