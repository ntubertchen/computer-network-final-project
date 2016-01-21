import java.util.ArrayList;
import java.io.Serializable;
class Curinfo implements Serializable{
	ArrayList<String> curonline;
	ArrayList<String> curoffline;
	Curinfo(){
		this.curoffline = new ArrayList<String>();
		this.curonline = new ArrayList<String>();
	}
}