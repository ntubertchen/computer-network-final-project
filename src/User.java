class User{
	private String username;
	private String password;
	int lastshowup;

	User(String username){
		this.username = username;
	}
	User(String username,String password){
		this.username = username;
		this.password = password;
	}
}