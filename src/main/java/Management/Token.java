package Management;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Token 
{
	private static String token = "";
	
	public Token() {
		setToken();
	}
	@SneakyThrows
	static public void setToken() {
		token = Files.readAllLines(Paths.get("C:\\Users\\joelm\\IdeaProjects\\Hidden\\Token.txt")).get(0);
	}
	
	public String getToken() {
		return token;
	}
}
