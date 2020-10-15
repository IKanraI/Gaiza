package management;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Token 
{
	private static String token = "";
	
	public Token() throws FileNotFoundException {
		setToken();
	}
	
	static public void setToken() throws FileNotFoundException {
		File tokenFile = new File("C:\\Users\\17244\\Documents\\JavaProjects\\Hidden\\Token.txt");
		Scanner getToken = new Scanner(tokenFile);
		
		token = getToken.nextLine();
		
		getToken.close();
	}
	
	public String getToken() {
		return token;
	}
}
