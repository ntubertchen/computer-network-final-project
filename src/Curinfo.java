import java.util.ArrayList;
import java.io.Serializable;
class Curinfo implements Serializable{
	public ArrayList<String> curonline;
	public ArrayList<String> curoffline;
	Curinfo(){
		this.curoffline = new ArrayList<String>();
		this.curonline = new ArrayList<String>();
	}
}