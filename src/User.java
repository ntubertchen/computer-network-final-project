import java.util.ArrayList;
class User{
	private String username;
	private String password;
	int lastshowup;
	ArrayList<Integer> roomlist;
	
	public void addroom(int i){
		this.roomlist.add(i);
	}

	public void setUsername(String s){
		this.username = s;
	}

	public void setUserpassword(String s){
		this.password = s;
	}
	public String getUsername(){
		return this.username;
	}
	public String getUserpassword(){
		return this.password;
	}
	public Boolean check(User u){
		if(this.username.equals(u.getUsername()) == true && this.password.equals(u.getUserpassword()) == true){
			return true;
		}else{
			return false;
		}
	}
	User(String username){
		this.username = username;
		this.roomlist = new ArrayList<Integer>();
	}
	User(String username,String password){
		this.username = username;
		this.password = password;
		this.roomlist = new ArrayList<Integer>();
	}
}