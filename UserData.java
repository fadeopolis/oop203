import java.io.Serializable;

public class UserData implements Serializable {

	private static final long serialVersionUID = 2538086094901129676L;
	
	private String password;
	private UserType type;
	
	public UserData( String pwd, UserType typ ) {
		this.password = pwd;
		this.type = typ;
	}
	
	public String getPw(){
		return this.password;
	}
	
	public String getTyp(){
		return this.type.toString();
	}
}
